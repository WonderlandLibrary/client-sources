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
import java.util.Objects;
import net.minecraft.client.renderer.model.IModelTransform;
import net.minecraft.client.renderer.model.ModelRotation;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.TransformationMatrix;

public class Variant
implements IModelTransform {
    private final ResourceLocation modelLocation;
    private final TransformationMatrix rotation;
    private final boolean uvLock;
    private final int weight;

    public Variant(ResourceLocation resourceLocation, TransformationMatrix transformationMatrix, boolean bl, int n) {
        this.modelLocation = resourceLocation;
        this.rotation = transformationMatrix;
        this.uvLock = bl;
        this.weight = n;
    }

    public ResourceLocation getModelLocation() {
        return this.modelLocation;
    }

    @Override
    public TransformationMatrix getRotation() {
        return this.rotation;
    }

    @Override
    public boolean isUvLock() {
        return this.uvLock;
    }

    public int getWeight() {
        return this.weight;
    }

    public String toString() {
        return "Variant{modelLocation=" + this.modelLocation + ", rotation=" + this.rotation + ", uvLock=" + this.uvLock + ", weight=" + this.weight + "}";
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (!(object instanceof Variant)) {
            return true;
        }
        Variant variant = (Variant)object;
        return this.modelLocation.equals(variant.modelLocation) && Objects.equals(this.rotation, variant.rotation) && this.uvLock == variant.uvLock && this.weight == variant.weight;
    }

    public int hashCode() {
        int n = this.modelLocation.hashCode();
        n = 31 * n + this.rotation.hashCode();
        n = 31 * n + Boolean.valueOf(this.uvLock).hashCode();
        return 31 * n + this.weight;
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Deserializer
    implements JsonDeserializer<Variant> {
        @Override
        public Variant deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            ResourceLocation resourceLocation = this.getStringModel(jsonObject);
            ModelRotation modelRotation = this.parseModelRotation(jsonObject);
            boolean bl = this.parseUvLock(jsonObject);
            int n = this.parseWeight(jsonObject);
            return new Variant(resourceLocation, modelRotation.getRotation(), bl, n);
        }

        private boolean parseUvLock(JsonObject jsonObject) {
            return JSONUtils.getBoolean(jsonObject, "uvlock", false);
        }

        protected ModelRotation parseModelRotation(JsonObject jsonObject) {
            int n;
            int n2 = JSONUtils.getInt(jsonObject, "x", 0);
            ModelRotation modelRotation = ModelRotation.getModelRotation(n2, n = JSONUtils.getInt(jsonObject, "y", 0));
            if (modelRotation == null) {
                throw new JsonParseException("Invalid BlockModelRotation x: " + n2 + ", y: " + n);
            }
            return modelRotation;
        }

        protected ResourceLocation getStringModel(JsonObject jsonObject) {
            return new ResourceLocation(JSONUtils.getString(jsonObject, "model"));
        }

        protected int parseWeight(JsonObject jsonObject) {
            int n = JSONUtils.getInt(jsonObject, "weight", 1);
            if (n < 1) {
                throw new JsonParseException("Invalid weight " + n + " found, expected integer >= 1");
            }
            return n;
        }

        @Override
        public Object deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return this.deserialize(jsonElement, type, jsonDeserializationContext);
        }
    }
}

