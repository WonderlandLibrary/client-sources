package dev.excellent.client.friend;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.excellent.Excellent;
import dev.excellent.impl.util.file.AbstractFile;
import dev.excellent.impl.util.file.FileType;
import lombok.NonNull;

import java.io.*;
import java.util.Map;

public class FriendFile extends AbstractFile {
    public FriendFile(File file) {
        super(file, FileType.FRIEND);
    }

    @Override
    public boolean read() {
        if (!this.getFile().exists()) {
            return false;
        }

        try {

            final FileReader fileReader = new FileReader(this.getFile());
            final BufferedReader bufferedReader = new BufferedReader(fileReader);
            final JsonObject jsonObject = GSON.fromJson(bufferedReader, JsonObject.class);

            bufferedReader.close();
            fileReader.close();

            if (jsonObject == null) {
                return false;
            }
            int i = 0;
            for (Map.Entry<String, JsonElement> jsonElement : jsonObject.entrySet()) {
                i++;
                if (!jsonElement.getKey().equalsIgnoreCase("id-" + i))
                    continue;

                JsonObject friendJSONElement = jsonElement.getValue().getAsJsonObject();
                String name = friendJSONElement.get("name").getAsString();
                Friend friend = new Friend(name);
                Excellent.getInst().getFriendManager().add(friend);
            }

        } catch (final IOException ignored) {
            return false;
        }

        return true;
    }

    @Override
    public boolean write() {
        try {
            if (!this.getFile().exists()) {
                if (this.getFile().createNewFile()) {
                    System.out.println("Файл с списком друзей успешно создана.");
                } else {
                    System.out.println("Произошла ошибка при создании файла с списком друзей.");
                }
            }

            final JsonObject jsonObject = getJsonObject();

            final FileWriter fileWriter = new FileWriter(getFile());
            final BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            GSON.toJson(jsonObject, bufferedWriter);

            bufferedWriter.flush();
            bufferedWriter.close();
            fileWriter.flush();
            fileWriter.close();
        } catch (final IOException ignored) {
            return false;
        }

        return true;
    }

    @NonNull
    private static JsonObject getJsonObject() {
        final JsonObject jsonObject = new JsonObject();

        int i = 0;
        for (Friend friend : Excellent.getInst().getFriendManager()) {
            i++;
            final JsonObject friendJsonObject = new JsonObject();
            friendJsonObject.addProperty("name", friend.getName());
            jsonObject.add("id-" + i, friendJsonObject);
        }

        return jsonObject;
    }
}
