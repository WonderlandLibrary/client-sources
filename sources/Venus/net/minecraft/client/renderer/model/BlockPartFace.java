/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.model;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.model.BlockFaceUV;
import net.minecraft.util.Direction;
import net.minecraft.util.JSONUtils;

public class BlockPartFace {
    public final Direction cullFace;
    public final int tintIndex;
    public final String texture;
    public final BlockFaceUV blockFaceUV;

    public BlockPartFace(@Nullable Direction direction, int n, String string, BlockFaceUV blockFaceUV) {
        this.cullFace = direction;
        this.tintIndex = n;
        this.texture = string;
        this.blockFaceUV = blockFaceUV;
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Deserializer
    implements JsonDeserializer<BlockPartFace> {
        protected Deserializer() {
        }

        @Override
        public BlockPartFace deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            Direction direction = this.parseCullFace(jsonObject);
            int n = this.parseTintIndex(jsonObject);
            String string = this.parseTexture(jsonObject);
            BlockFaceUV blockFaceUV = (BlockFaceUV)jsonDeserializationContext.deserialize(jsonObject, (Type)((Object)BlockFaceUV.class));
            return new BlockPartFace(direction, n, string, blockFaceUV);
        }

        protected int parseTintIndex(JsonObject jsonObject) {
            return JSONUtils.getInt(jsonObject, "tintindex", -1);
        }

        private String parseTexture(JsonObject jsonObject) {
            return JSONUtils.getString(jsonObject, "texture");
        }

        @Nullable
        private Direction parseCullFace(JsonObject jsonObject) {
            String string = JSONUtils.getString(jsonObject, "cullface", "");
            return Direction.byName(string);
        }

        @Override
        public Object deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return this.deserialize(jsonElement, type, jsonDeserializationContext);
        }
    }
}

