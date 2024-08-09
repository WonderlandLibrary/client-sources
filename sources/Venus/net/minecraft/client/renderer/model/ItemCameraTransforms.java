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
import net.minecraft.client.renderer.model.ItemTransformVec3f;

public class ItemCameraTransforms {
    public static final ItemCameraTransforms DEFAULT = new ItemCameraTransforms();
    public final ItemTransformVec3f thirdperson_left;
    public final ItemTransformVec3f thirdperson_right;
    public final ItemTransformVec3f firstperson_left;
    public final ItemTransformVec3f firstperson_right;
    public final ItemTransformVec3f head;
    public final ItemTransformVec3f gui;
    public final ItemTransformVec3f ground;
    public final ItemTransformVec3f fixed;

    private ItemCameraTransforms() {
        this(ItemTransformVec3f.DEFAULT, ItemTransformVec3f.DEFAULT, ItemTransformVec3f.DEFAULT, ItemTransformVec3f.DEFAULT, ItemTransformVec3f.DEFAULT, ItemTransformVec3f.DEFAULT, ItemTransformVec3f.DEFAULT, ItemTransformVec3f.DEFAULT);
    }

    public ItemCameraTransforms(ItemCameraTransforms itemCameraTransforms) {
        this.thirdperson_left = itemCameraTransforms.thirdperson_left;
        this.thirdperson_right = itemCameraTransforms.thirdperson_right;
        this.firstperson_left = itemCameraTransforms.firstperson_left;
        this.firstperson_right = itemCameraTransforms.firstperson_right;
        this.head = itemCameraTransforms.head;
        this.gui = itemCameraTransforms.gui;
        this.ground = itemCameraTransforms.ground;
        this.fixed = itemCameraTransforms.fixed;
    }

    public ItemCameraTransforms(ItemTransformVec3f itemTransformVec3f, ItemTransformVec3f itemTransformVec3f2, ItemTransformVec3f itemTransformVec3f3, ItemTransformVec3f itemTransformVec3f4, ItemTransformVec3f itemTransformVec3f5, ItemTransformVec3f itemTransformVec3f6, ItemTransformVec3f itemTransformVec3f7, ItemTransformVec3f itemTransformVec3f8) {
        this.thirdperson_left = itemTransformVec3f;
        this.thirdperson_right = itemTransformVec3f2;
        this.firstperson_left = itemTransformVec3f3;
        this.firstperson_right = itemTransformVec3f4;
        this.head = itemTransformVec3f5;
        this.gui = itemTransformVec3f6;
        this.ground = itemTransformVec3f7;
        this.fixed = itemTransformVec3f8;
    }

    public ItemTransformVec3f getTransform(TransformType transformType) {
        switch (1.$SwitchMap$net$minecraft$client$renderer$model$ItemCameraTransforms$TransformType[transformType.ordinal()]) {
            case 1: {
                return this.thirdperson_left;
            }
            case 2: {
                return this.thirdperson_right;
            }
            case 3: {
                return this.firstperson_left;
            }
            case 4: {
                return this.firstperson_right;
            }
            case 5: {
                return this.head;
            }
            case 6: {
                return this.gui;
            }
            case 7: {
                return this.ground;
            }
            case 8: {
                return this.fixed;
            }
        }
        return ItemTransformVec3f.DEFAULT;
    }

    public boolean hasCustomTransform(TransformType transformType) {
        return this.getTransform(transformType) != ItemTransformVec3f.DEFAULT;
    }

    public static enum TransformType {
        NONE,
        THIRD_PERSON_LEFT_HAND,
        THIRD_PERSON_RIGHT_HAND,
        FIRST_PERSON_LEFT_HAND,
        FIRST_PERSON_RIGHT_HAND,
        HEAD,
        GUI,
        GROUND,
        FIXED;


        public boolean isFirstPerson() {
            return this == FIRST_PERSON_LEFT_HAND || this == FIRST_PERSON_RIGHT_HAND;
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Deserializer
    implements JsonDeserializer<ItemCameraTransforms> {
        protected Deserializer() {
        }

        @Override
        public ItemCameraTransforms deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            ItemTransformVec3f itemTransformVec3f = this.getTransform(jsonDeserializationContext, jsonObject, "thirdperson_righthand");
            ItemTransformVec3f itemTransformVec3f2 = this.getTransform(jsonDeserializationContext, jsonObject, "thirdperson_lefthand");
            if (itemTransformVec3f2 == ItemTransformVec3f.DEFAULT) {
                itemTransformVec3f2 = itemTransformVec3f;
            }
            ItemTransformVec3f itemTransformVec3f3 = this.getTransform(jsonDeserializationContext, jsonObject, "firstperson_righthand");
            ItemTransformVec3f itemTransformVec3f4 = this.getTransform(jsonDeserializationContext, jsonObject, "firstperson_lefthand");
            if (itemTransformVec3f4 == ItemTransformVec3f.DEFAULT) {
                itemTransformVec3f4 = itemTransformVec3f3;
            }
            ItemTransformVec3f itemTransformVec3f5 = this.getTransform(jsonDeserializationContext, jsonObject, "head");
            ItemTransformVec3f itemTransformVec3f6 = this.getTransform(jsonDeserializationContext, jsonObject, "gui");
            ItemTransformVec3f itemTransformVec3f7 = this.getTransform(jsonDeserializationContext, jsonObject, "ground");
            ItemTransformVec3f itemTransformVec3f8 = this.getTransform(jsonDeserializationContext, jsonObject, "fixed");
            return new ItemCameraTransforms(itemTransformVec3f2, itemTransformVec3f, itemTransformVec3f4, itemTransformVec3f3, itemTransformVec3f5, itemTransformVec3f6, itemTransformVec3f7, itemTransformVec3f8);
        }

        private ItemTransformVec3f getTransform(JsonDeserializationContext jsonDeserializationContext, JsonObject jsonObject, String string) {
            return jsonObject.has(string) ? (ItemTransformVec3f)jsonDeserializationContext.deserialize(jsonObject.get(string), (Type)((Object)ItemTransformVec3f.class)) : ItemTransformVec3f.DEFAULT;
        }

        @Override
        public Object deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return this.deserialize(jsonElement, type, jsonDeserializationContext);
        }
    }
}

