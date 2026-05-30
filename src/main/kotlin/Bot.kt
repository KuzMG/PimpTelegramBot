package org.example

import org.example.commands.Commands
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer
import org.telegram.telegrambots.meta.api.objects.Update

class Bot(token: String) : LongPollingSingleThreadUpdateConsumer {

    private val telegramClient = OkHttpTelegramClient(token)

    override fun consume(p0: Update?) {
        println(p0?.message?.text)

        p0?.message?.text?.let { text ->
            Commands.map[text]?.invoke(p0.message.chatId, telegramClient)
        }
    }
}