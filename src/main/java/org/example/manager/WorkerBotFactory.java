package org.example.manager;

import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;

@FunctionalInterface
public interface WorkerBotFactory {

    LongPollingSingleThreadUpdateConsumer create(String token);
}
