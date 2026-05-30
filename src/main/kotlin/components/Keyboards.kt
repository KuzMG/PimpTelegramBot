package org.example.components

import org.example.commands.Commands
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
    .text(Commands.ADD_BOT).callbackData(Commands.ADD_BOT)
    .build()

val inlineKeyboard = InlineKeyboardMarkup.builder()
    .keyboardRow(InlineKeyboardRow(addBotInlineButton)).build()

/*
    Клавиатура показывается под строкой ввода
*/
private val addBotButton = KeyboardButton.builder()
    .text(Commands.ADD_BOT)
    .build()

val keyboard = ReplyKeyboardMarkup.builder()
    .keyboardRow(KeyboardRow(addBotButton)).build()