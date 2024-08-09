/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import javax.annotation.Nullable;
import net.minecraft.util.JSONUtils;

public class BlockFaceUV {
    public float[] uvs;
    public final int rotation;

    public BlockFaceUV(@Nullable float[] fArray, int n) {
        this.uvs = fArray;
        this.rotation = n;
    }

    public float getVertexU(int n) {
        if (this.uvs == null) {
            throw new NullPointerException("uvs");
        }
        int n2 = this.getVertexRotated(n);
        return this.uvs[n2 != 0 && n2 != 1 ? 2 : 0];
    }

    public float getVertexV(int n) {
        if (this.uvs == null) {
            throw new NullPointerException("uvs");
        }
        int n2 = this.getVertexRotated(n);
        return this.uvs[n2 != 0 && n2 != 3 ? 3 : 1];
    }

    private int getVertexRotated(int n) {
        return (n + this.rotation / 90) % 4;
    }

    public int getVertexRotatedRev(int n) {
        return (n + 4 - this.rotation / 90) % 4;
    }

    public void setUvs(float[] fArray) {
        if (this.uvs == null) {
            this.uvs = fArray;
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Deserializer
    implements JsonDeserializer<BlockFaceUV> {
        protected Deserializer() {
        }

        @Override
        public BlockFaceUV deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            float[] fArray = this.parseUV(jsonObject);
            int n = this.parseRotation(jsonObject);
            return new BlockFaceUV(fArray, n);
        }

        protected int parseRotation(JsonObject jsonObject) {
            int n = JSONUtils.getInt(jsonObject, "rotation", 0);
            if (n >= 0 && n % 90 == 0 && n / 90 <= 3) {
                return n;
            }
            throw new JsonParseException("Invalid rotation " + n + " found, only 0/90/180/270 allowed");
        }

        @Nullable
        private float[] parseUV(JsonObject jsonObject) {
            if (!jsonObject.has("uv")) {
                return null;
            }
            JsonArray jsonArray = JSONUtils.getJsonArray(jsonObject, "uv");
            if (jsonArray.size() != 4) {
                throw new JsonParseException("Expected 4 uv values, found: " + jsonArray.size());
            }
            float[] fArray = new float[4];
            for (int i = 0; i < fArray.length; ++i) {
                fArray[i] = JSONUtils.getFloat(jsonArray.get(i), "uv[" + i + "]");
            }
            return fArray;
        }

        @Override
        public Object deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return this.deserialize(jsonElement, type, jsonDeserializationContext);
        }
    }
}

