package org.example.manager;

import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.meta.api.methods.GetMe;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Optional;

public final class TokenValidator {

    public Optional<BotInfo> validate(String token) {
        var client = new OkHttpTelegramClient(token);
        try {
            User user = client.execute(new GetMe());
            if (user == null) {
                return Optional.empty();
            }
            String username = user.getUserName() != null ? user.getUserName() : "";
            return Optional.of(new BotInfo(user.getId(), username));
        } catch (TelegramApiException e) {
            return Optional.empty();
        }
    }

    public record BotInfo(long id, String username) {
    }
}
