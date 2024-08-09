/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.advancements.criterion;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import javax.annotation.Nullable;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.server.ServerWorld;

public class DamageSourcePredicate {
    public static final DamageSourcePredicate ANY = Builder.damageType().build();
    private final Boolean isProjectile;
    private final Boolean isExplosion;
    private final Boolean bypassesArmor;
    private final Boolean bypassesInvulnerability;
    private final Boolean bypassesMagic;
    private final Boolean isFire;
    private final Boolean isMagic;
    private final Boolean isLightning;
    private final EntityPredicate directEntity;
    private final EntityPredicate sourceEntity;

    public DamageSourcePredicate(@Nullable Boolean bl, @Nullable Boolean bl2, @Nullable Boolean bl3, @Nullable Boolean bl4, @Nullable Boolean bl5, @Nullable Boolean bl6, @Nullable Boolean bl7, @Nullable Boolean bl8, EntityPredicate entityPredicate, EntityPredicate entityPredicate2) {
        this.isProjectile = bl;
        this.isExplosion = bl2;
        this.bypassesArmor = bl3;
        this.bypassesInvulnerability = bl4;
        this.bypassesMagic = bl5;
        this.isFire = bl6;
        this.isMagic = bl7;
        this.isLightning = bl8;
        this.directEntity = entityPredicate;
        this.sourceEntity = entityPredicate2;
    }

    public boolean test(ServerPlayerEntity serverPlayerEntity, DamageSource damageSource) {
        return this.test(serverPlayerEntity.getServerWorld(), serverPlayerEntity.getPositionVec(), damageSource);
    }

    public boolean test(ServerWorld serverWorld, Vector3d vector3d, DamageSource damageSource) {
        if (this == ANY) {
            return false;
        }
        if (this.isProjectile != null && this.isProjectile.booleanValue() != damageSource.isProjectile()) {
            return true;
        }
        if (this.isExplosion != null && this.isExplosion.booleanValue() != damageSource.isExplosion()) {
            return true;
        }
        if (this.bypassesArmor != null && this.bypassesArmor.booleanValue() != damageSource.isUnblockable()) {
            return true;
        }
        if (this.bypassesInvulnerability != null && this.bypassesInvulnerability.booleanValue() != damageSource.canHarmInCreative()) {
            return true;
        }
        if (this.bypassesMagic != null && this.bypassesMagic.booleanValue() != damageSource.isDamageAbsolute()) {
            return true;
        }
        if (this.isFire != null && this.isFire.booleanValue() != damageSource.isFireDamage()) {
            return true;
        }
        if (this.isMagic != null && this.isMagic.booleanValue() != damageSource.isMagicDamage()) {
            return true;
        }
        if (this.isLightning != null && this.isLightning != (damageSource == DamageSource.LIGHTNING_BOLT)) {
            return true;
        }
        if (!this.directEntity.test(serverWorld, vector3d, damageSource.getImmediateSource())) {
            return true;
        }
        return this.sourceEntity.test(serverWorld, vector3d, damageSource.getTrueSource());
    }

    public static DamageSourcePredicate deserialize(@Nullable JsonElement jsonElement) {
        if (jsonElement != null && !jsonElement.isJsonNull()) {
            JsonObject jsonObject = JSONUtils.getJsonObject(jsonElement, "damage type");
            Boolean bl = DamageSourcePredicate.optionalBoolean(jsonObject, "is_projectile");
            Boolean bl2 = DamageSourcePredicate.optionalBoolean(jsonObject, "is_explosion");
            Boolean bl3 = DamageSourcePredicate.optionalBoolean(jsonObject, "bypasses_armor");
            Boolean bl4 = DamageSourcePredicate.optionalBoolean(jsonObject, "bypasses_invulnerability");
            Boolean bl5 = DamageSourcePredicate.optionalBoolean(jsonObject, "bypasses_magic");
            Boolean bl6 = DamageSourcePredicate.optionalBoolean(jsonObject, "is_fire");
            Boolean bl7 = DamageSourcePredicate.optionalBoolean(jsonObject, "is_magic");
            Boolean bl8 = DamageSourcePredicate.optionalBoolean(jsonObject, "is_lightning");
            EntityPredicate entityPredicate = EntityPredicate.deserialize(jsonObject.get("direct_entity"));
            EntityPredicate entityPredicate2 = EntityPredicate.deserialize(jsonObject.get("source_entity"));
            return new DamageSourcePredicate(bl, bl2, bl3, bl4, bl5, bl6, bl7, bl8, entityPredicate, entityPredicate2);
        }
        return ANY;
    }

    @Nullable
    private static Boolean optionalBoolean(JsonObject jsonObject, String string) {
        return jsonObject.has(string) ? Boolean.valueOf(JSONUtils.getBoolean(jsonObject, string)) : null;
    }

    public JsonElement serialize() {
        if (this == ANY) {
            return JsonNull.INSTANCE;
        }
        JsonObject jsonObject = new JsonObject();
        this.addProperty(jsonObject, "is_projectile", this.isProjectile);
        this.addProperty(jsonObject, "is_explosion", this.isExplosion);
        this.addProperty(jsonObject, "bypasses_armor", this.bypassesArmor);
        this.addProperty(jsonObject, "bypasses_invulnerability", this.bypassesInvulnerability);
        this.addProperty(jsonObject, "bypasses_magic", this.bypassesMagic);
        this.addProperty(jsonObject, "is_fire", this.isFire);
        this.addProperty(jsonObject, "is_magic", this.isMagic);
        this.addProperty(jsonObject, "is_lightning", this.isLightning);
        jsonObject.add("direct_entity", this.directEntity.serialize());
        jsonObject.add("source_entity", this.sourceEntity.serialize());
        return jsonObject;
    }

    private void addProperty(JsonObject jsonObject, String string, @Nullable Boolean bl) {
        if (bl != null) {
            jsonObject.addProperty(string, bl);
        }
    }

    public static class Builder {
        private Boolean isProjectile;
        private Boolean isExplosion;
        private Boolean bypassesArmor;
        private Boolean bypassesInvulnerability;
        private Boolean bypassesMagic;
        private Boolean isFire;
        private Boolean isMagic;
        private Boolean isLightning;
        private EntityPredicate directEntity = EntityPredicate.ANY;
        private EntityPredicate sourceEntity = EntityPredicate.ANY;

        public static Builder damageType() {
            return new Builder();
        }

        public Builder isProjectile(Boolean bl) {
            this.isProjectile = bl;
            return this;
        }

        public Builder isLightning(Boolean bl) {
            this.isLightning = bl;
            return this;
        }

        public Builder direct(EntityPredicate.Builder builder) {
            this.directEntity = builder.build();
            return this;
        }

        public DamageSourcePredicate build() {
            return new DamageSourcePredicate(this.isProjectile, this.isExplosion, this.bypassesArmor, this.bypassesInvulnerability, this.bypassesMagic, this.isFire, this.isMagic, this.isLightning, this.directEntity, this.sourceEntity);
        }
    }
}

