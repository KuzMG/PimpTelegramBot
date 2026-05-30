package org.example

import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.exceptions.TelegramApiException

class Bot(token: String): LongPollingSingleThreadUpdateConsumer {

    private val telegramClient = OkHttpTelegramClient(token)


    override fun consume(p0: Update?) {
        println(p0)
        println(p0?.message?.chatId)
        println(p0?.message?.isCommand)
        println(p0?.message?.text)
    }

    fun sendMenu(who: Long, txt: String, kb: InlineKeyboardMarkup?) {
        val sm: SendMessage? = SendMessage.builder().chatId(who.toString())
            .parseMode("HTML").text(txt)
            .replyMarkup(kb).build()

        try {
            telegramClient.execute(sm)
        } catch (e: TelegramApiException) {
            throw RuntimeException(e)
        }
    }
}