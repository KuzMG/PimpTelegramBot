package org.example.storage;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.manager.BotRecord;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public final class BotTokenStorage {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final TypeReference<List<BotRecord>> LIST_TYPE = new TypeReference<>() {};

    private final Path filePath;

    public BotTokenStorage(Path filePath) {
        this.filePath = filePath;
    }

    public List<BotRecord> load() {
        if (!Files.exists(filePath)) {
            return List.of();
        }
        try {
            byte[] bytes = Files.readAllBytes(filePath);
            if (bytes.length == 0) {
                return List.of();
            }
            List<BotRecord> records = MAPPER.readValue(bytes, LIST_TYPE);
            return records != null ? List.copyOf(records) : List.of();
        } catch (IOException e) {
            throw new IllegalStateException("Не удалось прочитать " + filePath, e);
        }
    }

    public void save(List<BotRecord> records) {
        try {
            Path parent = filePath.getParent();
            if (parent != null) {
                Files.createDirectories(parent);
            }
            MAPPER.writerWithDefaultPrettyPrinter().writeValue(filePath.toFile(), records);
        } catch (IOException e) {
            throw new IllegalStateException("Не удалось записать " + filePath, e);
        }
    }

    public void upsert(BotRecord record) {
        List<BotRecord> records = new ArrayList<>(load());
        records.removeIf(r -> r.token().equals(record.token()));
        records.add(record);
        save(records);
    }
}
