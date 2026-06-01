package org.example

import org.example.manager.BotManager
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication
import java.nio.file.Path

fun main(args: Array<String>) {
    require(args.isNotEmpty()) { "Укажите token master-бота: java -jar pimp.jar <TOKEN>" }

    val masterToken = args[0]
    val botsApplication = TelegramBotsLongPollingApplication()
    val storagePath = Path.of("data", "bots.json")
    val botManager = BotManager(botsApplication, storagePath, masterToken, DefaultWorkerBotFactory())

    botManager.loadAll()
    botsApplication.registerBot(masterToken, MasterBot(masterToken, botManager))
}
