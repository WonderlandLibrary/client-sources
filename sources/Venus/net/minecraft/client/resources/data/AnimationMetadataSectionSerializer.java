/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.resources.data;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.util.ArrayList;
import net.minecraft.client.resources.data.AnimationFrame;
import net.minecraft.client.resources.data.AnimationMetadataSection;
import net.minecraft.resources.data.IMetadataSectionSerializer;
import net.minecraft.util.JSONUtils;
import org.apache.commons.lang3.Validate;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class AnimationMetadataSectionSerializer
implements IMetadataSectionSerializer<AnimationMetadataSection> {
    @Override
    public AnimationMetadataSection deserialize(JsonObject jsonObject) {
        int n;
        ArrayList<AnimationFrame> arrayList = Lists.newArrayList();
        int n2 = JSONUtils.getInt(jsonObject, "frametime", 1);
        if (n2 != 1) {
            Validate.inclusiveBetween(1L, Integer.MAX_VALUE, n2, "Invalid default frame time");
        }
        if (jsonObject.has("frames")) {
            try {
                JsonArray jsonArray = JSONUtils.getJsonArray(jsonObject, "frames");
                for (n = 0; n < jsonArray.size(); ++n) {
                    JsonElement jsonElement = jsonArray.get(n);
                    AnimationFrame animationFrame = this.parseAnimationFrame(n, jsonElement);
                    if (animationFrame == null) continue;
                    arrayList.add(animationFrame);
                }
            } catch (ClassCastException classCastException) {
                throw new JsonParseException("Invalid animation->frames: expected array, was " + jsonObject.get("frames"), classCastException);
            }
        }
        int n3 = JSONUtils.getInt(jsonObject, "width", -1);
        n = JSONUtils.getInt(jsonObject, "height", -1);
        if (n3 != -1) {
            Validate.inclusiveBetween(1L, Integer.MAX_VALUE, n3, "Invalid width");
        }
        if (n != -1) {
            Validate.inclusiveBetween(1L, Integer.MAX_VALUE, n, "Invalid height");
        }
        boolean bl = JSONUtils.getBoolean(jsonObject, "interpolate", false);
        return new AnimationMetadataSection(arrayList, n3, n, n2, bl);
    }

    private AnimationFrame parseAnimationFrame(int n, JsonElement jsonElement) {
        if (jsonElement.isJsonPrimitive()) {
            return new AnimationFrame(JSONUtils.getInt(jsonElement, "frames[" + n + "]"));
        }
        if (jsonElement.isJsonObject()) {
            JsonObject jsonObject = JSONUtils.getJsonObject(jsonElement, "frames[" + n + "]");
            int n2 = JSONUtils.getInt(jsonObject, "time", -1);
            if (jsonObject.has("time")) {
                Validate.inclusiveBetween(1L, Integer.MAX_VALUE, n2, "Invalid frame time");
            }
            int n3 = JSONUtils.getInt(jsonObject, "index");
            Validate.inclusiveBetween(0L, Integer.MAX_VALUE, n3, "Invalid frame index");
            return new AnimationFrame(n3, n2);
        }
        return null;
    }

    @Override
    public String getSectionName() {
        return "animation";
    }

    @Override
    public Object deserialize(JsonObject jsonObject) {
        return this.deserialize(jsonObject);
    }
}

