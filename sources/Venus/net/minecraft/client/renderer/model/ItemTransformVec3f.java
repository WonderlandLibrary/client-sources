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
import com.mojang.blaze3d.matrix.MatrixStack;
import java.lang.reflect.Type;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;

public class ItemTransformVec3f {
    public static final ItemTransformVec3f DEFAULT = new ItemTransformVec3f(new Vector3f(), new Vector3f(), new Vector3f(1.0f, 1.0f, 1.0f));
    public final Vector3f rotation;
    public final Vector3f translation;
    public final Vector3f scale;

    public ItemTransformVec3f(Vector3f vector3f, Vector3f vector3f2, Vector3f vector3f3) {
        this.rotation = vector3f.copy();
        this.translation = vector3f2.copy();
        this.scale = vector3f3.copy();
    }

    public void apply(boolean bl, MatrixStack matrixStack) {
        if (this != DEFAULT) {
            float f = this.rotation.getX();
            float f2 = this.rotation.getY();
            float f3 = this.rotation.getZ();
            if (bl) {
                f2 = -f2;
                f3 = -f3;
            }
            int n = bl ? -1 : 1;
            matrixStack.translate((float)n * this.translation.getX(), this.translation.getY(), this.translation.getZ());
            matrixStack.rotate(new Quaternion(f, f2, f3, true));
            matrixStack.scale(this.scale.getX(), this.scale.getY(), this.scale.getZ());
        }
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (this.getClass() != object.getClass()) {
            return true;
        }
        ItemTransformVec3f itemTransformVec3f = (ItemTransformVec3f)object;
        return this.rotation.equals(itemTransformVec3f.rotation) && this.scale.equals(itemTransformVec3f.scale) && this.translation.equals(itemTransformVec3f.translation);
    }

    public int hashCode() {
        int n = this.rotation.hashCode();
        n = 31 * n + this.translation.hashCode();
        return 31 * n + this.scale.hashCode();
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Deserializer
    implements JsonDeserializer<ItemTransformVec3f> {
        private static final Vector3f ROTATION_DEFAULT = new Vector3f(0.0f, 0.0f, 0.0f);
        private static final Vector3f TRANSLATION_DEFAULT = new Vector3f(0.0f, 0.0f, 0.0f);
        private static final Vector3f SCALE_DEFAULT = new Vector3f(1.0f, 1.0f, 1.0f);

        protected Deserializer() {
        }

        @Override
        public ItemTransformVec3f deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            Vector3f vector3f = this.parseVector(jsonObject, "rotation", ROTATION_DEFAULT);
            Vector3f vector3f2 = this.parseVector(jsonObject, "translation", TRANSLATION_DEFAULT);
            vector3f2.mul(0.0625f);
            vector3f2.clamp(-5.0f, 5.0f);
            Vector3f vector3f3 = this.parseVector(jsonObject, "scale", SCALE_DEFAULT);
            vector3f3.clamp(-4.0f, 4.0f);
            return new ItemTransformVec3f(vector3f, vector3f2, vector3f3);
        }

        private Vector3f parseVector(JsonObject jsonObject, String string, Vector3f vector3f) {
            if (!jsonObject.has(string)) {
                return vector3f;
            }
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

