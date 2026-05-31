package org.example.session

import java.util.concurrent.ConcurrentHashMap

enum class UserState {
    IDLE,
    AWAITING_BOT_TOKEN,
}

object SessionStore {

    private val states = ConcurrentHashMap<Long, UserState>()

    fun get(chatId: Long): UserState = states.getOrDefault(chatId, UserState.IDLE)

    fun set(chatId: Long, state: UserState) {
        states[chatId] = state
    }

    fun clear(chatId: Long) {
        states.remove(chatId)
    }
}
