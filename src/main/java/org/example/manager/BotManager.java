package org.example.manager;

import org.example.storage.BotTokenStorage;
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class BotManager {

    private final TelegramBotsLongPollingApplication application;
    private final BotTokenStorage storage;
    private final WorkerBotFactory workerBotFactory;
    private final String masterToken;
    private final TokenValidator tokenValidator = new TokenValidator();
    private final Set<String> registeredTokens = new HashSet<>();

    public BotManager(
            TelegramBotsLongPollingApplication application,
            Path storageFile,
            String masterToken,
            WorkerBotFactory workerBotFactory
    ) {
        this.application = application;
        this.storage = new BotTokenStorage(storageFile);
        this.masterToken = masterToken;
        this.workerBotFactory = workerBotFactory;
        registeredTokens.add(masterToken);
    }

    /**
     * Поднимает worker-ботов из файла после перезапуска
     */
    public void loadAll() {
        List<BotRecord> records = storage.load();
        for (BotRecord record : records) {
            if (record.token().equals(masterToken)) {
                continue;
            }
            try {
                registerInternal(record, false);
            } catch (Exception e) {
                System.err.println("Не удалось загрузить бота @" + record.username() + ": " + e.getMessage());
            }
        }
    }

    /**
     * Регистрирует нового worker-бота по токену от пользователя
     */
    public RegisterResult register(String token, long ownerChatId) {
        String normalized = token == null ? "" : token.trim();
        if (normalized.isEmpty()) {
            return RegisterResult.fail("Токен не может быть пустым.");
        }
        if (normalized.equals(masterToken)) {
            return RegisterResult.fail("Это токен master-бота. Нужен токен нового бота из BotFather.");
        }
        if (registeredTokens.contains(normalized)) {
            return RegisterResult.fail("Этот бот уже добавлен.");
        }

        var botInfo = tokenValidator.validate(normalized);
        if (botInfo.isEmpty()) {
            return RegisterResult.fail("Неверный токен или нет доступа к Telegram API.");
        }

        String username = botInfo.get().username();
        var record = new BotRecord(normalized, ownerChatId, username);
        try {
            registerInternal(record, true);
        } catch (TelegramApiException e) {
            return RegisterResult.fail("Ошибка регистрации: " + e.getMessage());
        }

        String displayName = username.isEmpty() ? "бот" : "@" + username;
        return RegisterResult.ok("Бот " + displayName + " добавлен и запущен.");
    }

    private void registerInternal(BotRecord record, boolean persist) throws TelegramApiException {
        if (registeredTokens.contains(record.token())) {
            return;
        }
        application.registerBot(record.token(), workerBotFactory.create(record.token()));
        registeredTokens.add(record.token());
        if (persist) {
            storage.upsert(record);
        }
    }
}
