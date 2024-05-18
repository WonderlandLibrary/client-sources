/*
 * This file is part of Clientbase - https://github.com/DietrichPaul/Clientbase
 * by DietrichPaul, FlorianMichael and contributors
 *
 * To the extent possible under law, the person who associated CC0 with
 * Clientbase has waived all copyright and related or neighboring rights
 * to Clientbase.
 *
 * You should have received a copy of the CC0 legalcode along with this
 * work.  If not, see <http://creativecommons.org/publicdomain/zero/1.0/>.
 */
package de.dietrichpaul.clientbase.util.jvm;

import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class IOUtil {
    private final static Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @SuppressWarnings("unchecked")
    public static <E extends JsonElement> E readGsonOr(File file, E fallback) {
        try {
            return (E) JsonParser.parseReader(new FileReader(file));
        } catch (Exception ignored) {
        }
        return fallback;
    }

    public static void writePrettyGson(File file, JsonElement elem) {
        try {
            Files.asCharSink(file, StandardCharsets.UTF_8).write(GSON.toJson(elem));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
