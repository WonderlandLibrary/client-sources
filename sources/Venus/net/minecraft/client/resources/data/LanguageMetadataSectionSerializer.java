/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.resources.data;

import com.google.common.collect.Sets;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.util.HashSet;
import java.util.Map;
import net.minecraft.client.resources.Language;
import net.minecraft.client.resources.data.LanguageMetadataSection;
import net.minecraft.resources.data.IMetadataSectionSerializer;
import net.minecraft.util.JSONUtils;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class LanguageMetadataSectionSerializer
implements IMetadataSectionSerializer<LanguageMetadataSection> {
    @Override
    public LanguageMetadataSection deserialize(JsonObject jsonObject) {
        HashSet<Language> hashSet = Sets.newHashSet();
        for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
            String string = entry.getKey();
            if (string.length() > 16) {
                throw new JsonParseException("Invalid language->'" + string + "': language code must not be more than 16 characters long");
            }
            JsonObject jsonObject2 = JSONUtils.getJsonObject(entry.getValue(), "language");
            String string2 = JSONUtils.getString(jsonObject2, "region");
            String string3 = JSONUtils.getString(jsonObject2, "name");
            boolean bl = JSONUtils.getBoolean(jsonObject2, "bidirectional", false);
            if (string2.isEmpty()) {
                throw new JsonParseException("Invalid language->'" + string + "'->region: empty value");
            }
            if (string3.isEmpty()) {
                throw new JsonParseException("Invalid language->'" + string + "'->name: empty value");
            }
            if (hashSet.add(new Language(string, string2, string3, bl))) continue;
            throw new JsonParseException("Duplicate language->'" + string + "' defined");
        }
        return new LanguageMetadataSection(hashSet);
    }

    @Override
    public String getSectionName() {
        return "language";
    }

    @Override
    public Object deserialize(JsonObject jsonObject) {
        return this.deserialize(jsonObject);
    }
}

