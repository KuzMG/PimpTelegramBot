package org.example

import org.example.commands.Commands
import org.example.ext.execute
import org.example.manager.BotManager
import org.example.session.SessionStore
import org.example.session.UserState
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.CallbackQuery
import org.telegram.telegrambots.meta.api.objects.Update

class MasterBot(
    token: String,
    private val botManager: BotManager,
) : LongPollingSingleThreadUpdateConsumer {

    private val telegramClient = OkHttpTelegramClient(token)

    override fun consume(update: Update?) {
        update ?: return

        update.callbackQuery?.let {
            handleCallback(it)
            return
        }

        val message = update.message ?: return
        val chatId = message.chatId
        val text = message.text ?: return

        when {
            SessionStore.get(chatId) == UserState.AWAITING_BOT_TOKEN -> handleTokenInput(chatId, text)
            else -> Commands.map[text]?.invoke(chatId, telegramClient)
        }
    }

    private fun handleCallback(callback: CallbackQuery) {
        val chatId = callback.message?.chatId ?: return

        if (callback.data == Commands.ADD_BOT) {
            SessionStore.set(chatId, UserState.AWAITING_BOT_TOKEN)
            sendText(chatId, "Введите token бота из BotFather.\nДля отмены отправьте /cancel")
        }

        AnswerCallbackQuery.builder()
            .callbackQueryId(callback.id)
            .build()
            .execute(telegramClient)
    }

    private fun handleTokenInput(chatId: Long, text: String) {
        if (text == "/cancel") {
            SessionStore.clear(chatId)
            sendText(chatId, "Добавление отменено.")
            return
        }

        val result = botManager.register(text.trim(), chatId)
        SessionStore.clear(chatId)
        sendText(chatId, result.message)
    }

    private fun sendText(chatId: Long, text: String) {
        SendMessage.builder()
            .chatId(chatId)
            .text(text)
            .build()
            .execute(telegramClient)
    }
}
