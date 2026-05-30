package org.example

import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow
import org.telegram.telegrambots.meta.exceptions.TelegramApiException

class Bot(token: String) : LongPollingSingleThreadUpdateConsumer {

    private val telegramClient = OkHttpTelegramClient(token)
    private val next = InlineKeyboardButton.builder()
        .text("Добавить бота").callbackData("add_bot")
        .build();

    private val next2 = KeyboardButton.builder()
        .text("Добавить бота")
        .build();

    private val keyboardM1 = InlineKeyboardMarkup.builder()
        .keyboardRow(InlineKeyboardRow(next)).build()

    private val keyboardM2 = ReplyKeyboardMarkup.builder()
        .keyboardRow(KeyboardRow(next2)).build()

    override fun consume(p0: Update?) {
        println(p0)
        println(p0?.message?.chatId)
        println(p0?.message?.isCommand)
        println(p0?.message?.text)

        if (p0?.message?.isCommand == true) {
            when (p0.message?.text) {
                "/start_1" -> sendMenu(p0.message.chatId, "ds", keyboardM1)
                "/start_2" -> sendMenu(p0.message.chatId, "ds", keyboardM2)
            }
        }
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

    fun sendMenu(who: Long, txt: String, kb: ReplyKeyboardMarkup?) {
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