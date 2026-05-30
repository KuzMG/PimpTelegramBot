package org.example

import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main(args: Array<String>) {
    val botsApplication = TelegramBotsLongPollingApplication()
    val bot = Bot(args[0])
    botsApplication.registerBot(args[0], bot)
}