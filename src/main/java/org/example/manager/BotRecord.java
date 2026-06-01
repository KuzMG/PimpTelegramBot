package org.example.manager;

/**
 * Сохранённый worker-бот (не master).
 */
public record BotRecord(String token, long ownerChatId, String username) {
}
