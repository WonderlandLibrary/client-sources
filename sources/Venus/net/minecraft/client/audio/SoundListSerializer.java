/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.audio;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.audio.Sound;
import net.minecraft.client.audio.SoundList;
import net.minecraft.util.JSONUtils;
import org.apache.commons.lang3.Validate;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class SoundListSerializer
implements JsonDeserializer<SoundList> {
    @Override
    public SoundList deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = JSONUtils.getJsonObject(jsonElement, "entry");
        boolean bl = JSONUtils.getBoolean(jsonObject, "replace", false);
        String string = JSONUtils.getString(jsonObject, "subtitle", null);
        List<Sound> list = this.deserializeSounds(jsonObject);
        return new SoundList(list, bl, string);
    }

    private List<Sound> deserializeSounds(JsonObject jsonObject) {
        ArrayList<Sound> arrayList = Lists.newArrayList();
        if (jsonObject.has("sounds")) {
            JsonArray jsonArray = JSONUtils.getJsonArray(jsonObject, "sounds");
            for (int i = 0; i < jsonArray.size(); ++i) {
                JsonElement jsonElement = jsonArray.get(i);
                if (JSONUtils.isString(jsonElement)) {
                    String string = JSONUtils.getString(jsonElement, "sound");
                    arrayList.add(new Sound(string, 1.0f, 1.0f, 1, Sound.Type.FILE, false, false, 16));
                    continue;
                }
                arrayList.add(this.deserializeSound(JSONUtils.getJsonObject(jsonElement, "sound")));
            }
        }
        return arrayList;
    }

    private Sound deserializeSound(JsonObject jsonObject) {
        String string = JSONUtils.getString(jsonObject, "name");
        Sound.Type type = this.deserializeType(jsonObject, Sound.Type.FILE);
        float f = JSONUtils.getFloat(jsonObject, "volume", 1.0f);
        Validate.isTrue(f > 0.0f, "Invalid volume", new Object[0]);
        float f2 = JSONUtils.getFloat(jsonObject, "pitch", 1.0f);
        Validate.isTrue(f2 > 0.0f, "Invalid pitch", new Object[0]);
        int n = JSONUtils.getInt(jsonObject, "weight", 1);
        Validate.isTrue(n > 0, "Invalid weight", new Object[0]);
        boolean bl = JSONUtils.getBoolean(jsonObject, "preload", false);
        boolean bl2 = JSONUtils.getBoolean(jsonObject, "stream", false);
        int n2 = JSONUtils.getInt(jsonObject, "attenuation_distance", 16);
        return new Sound(string, f, f2, n, type, bl2, bl, n2);
    }

    private Sound.Type deserializeType(JsonObject jsonObject, Sound.Type type) {
        Sound.Type type2 = type;
        if (jsonObject.has("type")) {
            type2 = Sound.Type.getByName(JSONUtils.getString(jsonObject, "type"));
            Validate.notNull(type2, "Invalid type", new Object[0]);
        }
        return type2;
    }

    @Override
    public Object deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return this.deserialize(jsonElement, type, jsonDeserializationContext);
    }
}

