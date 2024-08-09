/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.advancements.criterion;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.JSONUtils;

public class EntityFlagsPredicate {
    public static final EntityFlagsPredicate ALWAYS_TRUE = new Builder().build();
    @Nullable
    private final Boolean onFire;
    @Nullable
    private final Boolean sneaking;
    @Nullable
    private final Boolean sprinting;
    @Nullable
    private final Boolean swimming;
    @Nullable
    private final Boolean baby;

    public EntityFlagsPredicate(@Nullable Boolean bl, @Nullable Boolean bl2, @Nullable Boolean bl3, @Nullable Boolean bl4, @Nullable Boolean bl5) {
        this.onFire = bl;
        this.sneaking = bl2;
        this.sprinting = bl3;
        this.swimming = bl4;
        this.baby = bl5;
    }

    public boolean test(Entity entity2) {
        if (this.onFire != null && entity2.isBurning() != this.onFire.booleanValue()) {
            return true;
        }
        if (this.sneaking != null && entity2.isCrouching() != this.sneaking.booleanValue()) {
            return true;
        }
        if (this.sprinting != null && entity2.isSprinting() != this.sprinting.booleanValue()) {
            return true;
        }
        if (this.swimming != null && entity2.isSwimming() != this.swimming.booleanValue()) {
            return true;
        }
        return this.baby == null || !(entity2 instanceof LivingEntity) || ((LivingEntity)entity2).isChild() == this.baby.booleanValue();
    }

    @Nullable
    private static Boolean getBoolean(JsonObject jsonObject, String string) {
        return jsonObject.has(string) ? Boolean.valueOf(JSONUtils.getBoolean(jsonObject, string)) : null;
    }

    public static EntityFlagsPredicate deserialize(@Nullable JsonElement jsonElement) {
        if (jsonElement != null && !jsonElement.isJsonNull()) {
            JsonObject jsonObject = JSONUtils.getJsonObject(jsonElement, "entity flags");
            Boolean bl = EntityFlagsPredicate.getBoolean(jsonObject, "is_on_fire");
            Boolean bl2 = EntityFlagsPredicate.getBoolean(jsonObject, "is_sneaking");
            Boolean bl3 = EntityFlagsPredicate.getBoolean(jsonObject, "is_sprinting");
            Boolean bl4 = EntityFlagsPredicate.getBoolean(jsonObject, "is_swimming");
            Boolean bl5 = EntityFlagsPredicate.getBoolean(jsonObject, "is_baby");
            return new EntityFlagsPredicate(bl, bl2, bl3, bl4, bl5);
        }
        return ALWAYS_TRUE;
    }

    private void putBoolean(JsonObject jsonObject, String string, @Nullable Boolean bl) {
        if (bl != null) {
            jsonObject.addProperty(string, bl);
        }
    }

    public JsonElement serialize() {
        if (this == ALWAYS_TRUE) {
            return JsonNull.INSTANCE;
        }
        JsonObject jsonObject = new JsonObject();
        this.putBoolean(jsonObject, "is_on_fire", this.onFire);
        this.putBoolean(jsonObject, "is_sneaking", this.sneaking);
        this.putBoolean(jsonObject, "is_sprinting", this.sprinting);
        this.putBoolean(jsonObject, "is_swimming", this.swimming);
        this.putBoolean(jsonObject, "is_baby", this.baby);
        return jsonObject;
    }

    public static class Builder {
        @Nullable
        private Boolean onFire;
        @Nullable
        private Boolean sneaking;
        @Nullable
        private Boolean sprinting;
        @Nullable
        private Boolean swimming;
        @Nullable
        private Boolean baby;

        public static Builder create() {
            return new Builder();
        }

        public Builder onFire(@Nullable Boolean bl) {
            this.onFire = bl;
            return this;
        }

        public Builder isBaby(@Nullable Boolean bl) {
            this.baby = bl;
            return this;
        }

        public EntityFlagsPredicate build() {
            return new EntityFlagsPredicate(this.onFire, this.sneaking, this.sprinting, this.swimming, this.baby);
        }
    }
}

