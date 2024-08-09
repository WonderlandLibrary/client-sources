package dev.excellent.client.macros;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.excellent.Excellent;
import dev.excellent.impl.util.file.AbstractFile;
import dev.excellent.impl.util.file.FileType;
import lombok.NonNull;

import java.io.*;
import java.util.Map;

public class MacrosFile extends AbstractFile {
    public MacrosFile(File file) {
        super(file, FileType.MACROS);
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

                JsonObject macrosJSONElement = jsonElement.getValue().getAsJsonObject();
                String name = macrosJSONElement.get("name").getAsString();
                int keycode = macrosJSONElement.get("keycode").getAsInt();
                String message = macrosJSONElement.get("message").getAsString();
                Macro macros = new Macro(name, keycode, message);
                Excellent.getInst().getMacrosManager().add(macros);
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
                    System.out.println("Файл с списком макросов успешно создана.");
                } else {
                    System.out.println("Произошла ошибка при создании файла с списком макросов.");
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
        for (Macro macros : Excellent.getInst().getMacrosManager()) {
            i++;
            final JsonObject macrosJsonObject = new JsonObject();
            macrosJsonObject.addProperty("name", macros.getName());
            macrosJsonObject.addProperty("keycode", macros.getKeyCode());
            macrosJsonObject.addProperty("message", macros.getMessage());
            jsonObject.add("id-" + i, macrosJsonObject);
        }
        return jsonObject;
    }
}
