package org.example.components

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow

/*
    Инлайн клавитура показывается в чате
*/
private val addBotInlineButton = InlineKeyboardButton.builder()
    .text("Добавить бота").callbackData("/add_bot")
    .build()

val inlineKeyboard = InlineKeyboardMarkup.builder()
    .keyboardRow(InlineKeyboardRow(addBotInlineButton)).build()

/*
    Клавиатура показывается под строкой ввода
*/
private val addBotButton = KeyboardButton.builder()
    .text("Добавить бота")
    .build()

val keyboard = ReplyKeyboardMarkup.builder()
    .keyboardRow(KeyboardRow(addBotButton)).build()