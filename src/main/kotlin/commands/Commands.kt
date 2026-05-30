package org.example.commands

import org.example.components.inlineKeyboard
import org.example.components.keyboard
import org.example.ext.execute
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient
import org.telegram.telegrambots.meta.api.methods.send.SendMessage

object Commands {
    const val ADD_BOT = "Добавить бота"
    const val START_1 = "/start_1"
    const val START_2 = "/start_2"

    val map = mapOf(
        (ADD_BOT to ::addBot),
        (START_1 to ::sendMenuV1),
        (START_2 to ::sendMenuV2),
    )

    private fun addBot(who: Long, telegramClient: OkHttpTelegramClient) {
        SendMessage.builder()
            .text("Введите token")
            .chatId(who)
            .build()
            .execute(telegramClient)
    }

    private fun sendMenuV1(who: Long, telegramClient: OkHttpTelegramClient) {
        SendMessage.builder()
            .chatId(who.toString())
            .parseMode("HTML")
            .text("Выберете действие")
            .replyMarkup(keyboard)
            .build()
            .execute(telegramClient)
    }

    private fun sendMenuV2(who: Long, telegramClient: OkHttpTelegramClient) {
        SendMessage.builder()
            .chatId(who.toString())
            .parseMode("HTML")
            .text("Выберете действие")
            .replyMarkup(inlineKeyboard)
            .build()
            .execute(telegramClient)
    }
}