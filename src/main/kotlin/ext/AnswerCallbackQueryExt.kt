package org.example.ext

import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery
import org.telegram.telegrambots.meta.exceptions.TelegramApiException

fun AnswerCallbackQuery.execute(client: OkHttpTelegramClient) {
    try {
        client.execute(this)
    } catch (e: TelegramApiException) {
        throw RuntimeException(e)
    }
}
