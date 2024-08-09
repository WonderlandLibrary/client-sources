/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viabackwards.protocol.protocol1_15_2to1_16.chat;

import com.google.common.base.Preconditions;
import com.viaversion.viaversion.libs.gson.JsonArray;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.libs.gson.JsonPrimitive;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import java.util.Map;
import java.util.regex.Pattern;

@Deprecated
public class TagSerializer {
    private static final Pattern PLAIN_TEXT = Pattern.compile("[A-Za-z0-9._+-]+");

    public static String toString(JsonObject jsonObject) {
        StringBuilder stringBuilder = new StringBuilder("{");
        for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
            Preconditions.checkArgument(entry.getValue().isJsonPrimitive());
            if (stringBuilder.length() != 1) {
                stringBuilder.append(',');
            }
            String string = TagSerializer.escape(entry.getValue().getAsString());
            stringBuilder.append(entry.getKey()).append(':').append(string);
        }
        return stringBuilder.append('}').toString();
    }

    public static JsonObject toJson(CompoundTag compoundTag) {
        JsonObject jsonObject = new JsonObject();
        for (Map.Entry<String, Tag> entry : compoundTag.entrySet()) {
            jsonObject.add(entry.getKey(), TagSerializer.toJson(entry.getValue()));
        }
        return jsonObject;
    }

    private static JsonElement toJson(Tag tag) {
        if (tag instanceof CompoundTag) {
            return TagSerializer.toJson((CompoundTag)tag);
        }
        if (tag instanceof ListTag) {
            ListTag listTag = (ListTag)tag;
            JsonArray jsonArray = new JsonArray();
            for (Tag tag2 : listTag) {
                jsonArray.add(TagSerializer.toJson(tag2));
            }
            return jsonArray;
        }
        return new JsonPrimitive(tag.getValue().toString());
    }

    public static String escape(String string) {
        if (PLAIN_TEXT.matcher(string).matches()) {
            return string;
        }
        StringBuilder stringBuilder = new StringBuilder(" ");
        int n = 0;
        for (int i = 0; i < string.length(); ++i) {
            int n2 = string.charAt(i);
            if (n2 == 92) {
                stringBuilder.append('\\');
            } else if (n2 == 34 || n2 == 39) {
                if (n == 0) {
                    int n3 = n = n2 == 34 ? 39 : 34;
                }
                if (n == n2) {
                    stringBuilder.append('\\');
                }
            }
            stringBuilder.append((char)n2);
        }
        if (n == 0) {
            n = 34;
        }
        stringBuilder.setCharAt(0, (char)n);
        stringBuilder.append((char)n);
        return stringBuilder.toString();
    }
}

