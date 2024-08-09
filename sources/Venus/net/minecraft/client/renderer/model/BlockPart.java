/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.model;

import com.google.common.collect.Maps;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import java.util.EnumMap;
import java.util.Locale;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.model.BlockPartFace;
import net.minecraft.client.renderer.model.BlockPartRotation;
import net.minecraft.util.Direction;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;

public class BlockPart {
    public final Vector3f positionFrom;
    public final Vector3f positionTo;
    public final Map<Direction, BlockPartFace> mapFaces;
    public final BlockPartRotation partRotation;
    public final boolean shade;

    public BlockPart(Vector3f vector3f, Vector3f vector3f2, Map<Direction, BlockPartFace> map, @Nullable BlockPartRotation blockPartRotation, boolean bl) {
        this.positionFrom = vector3f;
        this.positionTo = vector3f2;
        this.mapFaces = map;
        this.partRotation = blockPartRotation;
        this.shade = bl;
        this.setDefaultUvs();
    }

    private void setDefaultUvs() {
        for (Map.Entry<Direction, BlockPartFace> entry : this.mapFaces.entrySet()) {
            float[] fArray = this.getFaceUvs(entry.getKey());
            entry.getValue().blockFaceUV.setUvs(fArray);
        }
    }

    private float[] getFaceUvs(Direction direction) {
        switch (1.$SwitchMap$net$minecraft$util$Direction[direction.ordinal()]) {
            case 1: {
                return new float[]{this.positionFrom.getX(), 16.0f - this.positionTo.getZ(), this.positionTo.getX(), 16.0f - this.positionFrom.getZ()};
            }
            case 2: {
                return new float[]{this.positionFrom.getX(), this.positionFrom.getZ(), this.positionTo.getX(), this.positionTo.getZ()};
            }
            default: {
                return new float[]{16.0f - this.positionTo.getX(), 16.0f - this.positionTo.getY(), 16.0f - this.positionFrom.getX(), 16.0f - this.positionFrom.getY()};
            }
            case 4: {
                return new float[]{this.positionFrom.getX(), 16.0f - this.positionTo.getY(), this.positionTo.getX(), 16.0f - this.positionFrom.getY()};
            }
            case 5: {
                return new float[]{this.positionFrom.getZ(), 16.0f - this.positionTo.getY(), this.positionTo.getZ(), 16.0f - this.positionFrom.getY()};
            }
            case 6: 
        }
        return new float[]{16.0f - this.positionTo.getZ(), 16.0f - this.positionTo.getY(), 16.0f - this.positionFrom.getZ(), 16.0f - this.positionFrom.getY()};
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Deserializer
    implements JsonDeserializer<BlockPart> {
        protected Deserializer() {
        }

        @Override
        public BlockPart deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            Vector3f vector3f = this.validateFromVector(jsonObject);
            Vector3f vector3f2 = this.validateToVector(jsonObject);
            BlockPartRotation blockPartRotation = this.parseRotation(jsonObject);
            Map<Direction, BlockPartFace> map = this.parseFacesCheck(jsonDeserializationContext, jsonObject);
            if (jsonObject.has("shade") && !JSONUtils.isBoolean(jsonObject, "shade")) {
                throw new JsonParseException("Expected shade to be a Boolean");
            }
            boolean bl = JSONUtils.getBoolean(jsonObject, "shade", true);
            return new BlockPart(vector3f, vector3f2, map, blockPartRotation, bl);
        }

        @Nullable
        private BlockPartRotation parseRotation(JsonObject jsonObject) {
            BlockPartRotation blockPartRotation = null;
            if (jsonObject.has("rotation")) {
                JsonObject jsonObject2 = JSONUtils.getJsonObject(jsonObject, "rotation");
                Vector3f vector3f = this.deserializeVec3f(jsonObject2, "origin");
                vector3f.mul(0.0625f);
                Direction.Axis axis = this.parseAxis(jsonObject2);
                float f = this.parseAngle(jsonObject2);
                boolean bl = JSONUtils.getBoolean(jsonObject2, "rescale", false);
                blockPartRotation = new BlockPartRotation(vector3f, axis, f, bl);
            }
            return blockPartRotation;
        }

        private float parseAngle(JsonObject jsonObject) {
            float f = JSONUtils.getFloat(jsonObject, "angle");
            if (f != 0.0f && MathHelper.abs(f) != 22.5f && MathHelper.abs(f) != 45.0f) {
                throw new JsonParseException("Invalid rotation " + f + " found, only -45/-22.5/0/22.5/45 allowed");
            }
            return f;
        }

        private Direction.Axis parseAxis(JsonObject jsonObject) {
            String string = JSONUtils.getString(jsonObject, "axis");
            Direction.Axis axis = Direction.Axis.byName(string.toLowerCase(Locale.ROOT));
            if (axis == null) {
                throw new JsonParseException("Invalid rotation axis: " + string);
            }
            return axis;
        }

        private Map<Direction, BlockPartFace> parseFacesCheck(JsonDeserializationContext jsonDeserializationContext, JsonObject jsonObject) {
            Map<Direction, BlockPartFace> map = this.parseFaces(jsonDeserializationContext, jsonObject);
            if (map.isEmpty()) {
                throw new JsonParseException("Expected between 1 and 6 unique faces, got 0");
            }
            return map;
        }

        private Map<Direction, BlockPartFace> parseFaces(JsonDeserializationContext jsonDeserializationContext, JsonObject jsonObject) {
            EnumMap<Direction, BlockPartFace> enumMap = Maps.newEnumMap(Direction.class);
            JsonObject jsonObject2 = JSONUtils.getJsonObject(jsonObject, "faces");
            for (Map.Entry<String, JsonElement> entry : jsonObject2.entrySet()) {
                Direction direction = this.parseEnumFacing(entry.getKey());
                enumMap.put(direction, (BlockPartFace)jsonDeserializationContext.deserialize(entry.getValue(), (Type)((Object)BlockPartFace.class)));
            }
            return enumMap;
        }

        private Direction parseEnumFacing(String string) {
            Direction direction = Direction.byName(string);
            if (direction == null) {
                throw new JsonParseException("Unknown facing: " + string);
            }
            return direction;
        }

        private Vector3f validateToVector(JsonObject jsonObject) {
            Vector3f vector3f = this.deserializeVec3f(jsonObject, "to");
            if (!(vector3f.getX() < -16.0f || vector3f.getY() < -16.0f || vector3f.getZ() < -16.0f || vector3f.getX() > 32.0f || vector3f.getY() > 32.0f || vector3f.getZ() > 32.0f)) {
                return vector3f;
            }
            throw new JsonParseException("'to' specifier exceeds the allowed boundaries: " + vector3f);
        }

        private Vector3f validateFromVector(JsonObject jsonObject) {
            Vector3f vector3f = this.deserializeVec3f(jsonObject, "from");
            if (!(vector3f.getX() < -16.0f || vector3f.getY() < -16.0f || vector3f.getZ() < -16.0f || vector3f.getX() > 32.0f || vector3f.getY() > 32.0f || vector3f.getZ() > 32.0f)) {
                return vector3f;
            }
            throw new JsonParseException("'from' specifier exceeds the allowed boundaries: " + vector3f);
        }

        private Vector3f deserializeVec3f(JsonObject jsonObject, String string) {
            JsonArray jsonArray = JSONUtils.getJsonArray(jsonObject, string);
            if (jsonArray.size() != 3) {
                throw new JsonParseException("Expected 3 " + string + " values, found: " + jsonArray.size());
            }
            float[] fArray = new float[3];
            for (int i = 0; i < fArray.length; ++i) {
                fArray[i] = JSONUtils.getFloat(jsonArray.get(i), string + "[" + i + "]");
            }
            return new Vector3f(fArray[0], fArray[1], fArray[2]);
        }

        @Override
        public Object deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return this.deserialize(jsonElement, type, jsonDeserializationContext);
        }
    }
}

