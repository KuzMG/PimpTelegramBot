package org.example

import org.example.manager.WorkerBotFactory
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer

class DefaultWorkerBotFactory : WorkerBotFactory {

    override fun create(token: String): LongPollingSingleThreadUpdateConsumer = Bot(token)
}
