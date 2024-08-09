/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.advancements.criterion;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import javax.annotation.Nullable;
import net.minecraft.advancements.criterion.DamageSourcePredicate;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.JSONUtils;

public class DamagePredicate {
    public static final DamagePredicate ANY = Builder.create().build();
    private final MinMaxBounds.FloatBound dealt;
    private final MinMaxBounds.FloatBound taken;
    private final EntityPredicate sourceEntity;
    private final Boolean blocked;
    private final DamageSourcePredicate type;

    public DamagePredicate() {
        this.dealt = MinMaxBounds.FloatBound.UNBOUNDED;
        this.taken = MinMaxBounds.FloatBound.UNBOUNDED;
        this.sourceEntity = EntityPredicate.ANY;
        this.blocked = null;
        this.type = DamageSourcePredicate.ANY;
    }

    public DamagePredicate(MinMaxBounds.FloatBound floatBound, MinMaxBounds.FloatBound floatBound2, EntityPredicate entityPredicate, @Nullable Boolean bl, DamageSourcePredicate damageSourcePredicate) {
        this.dealt = floatBound;
        this.taken = floatBound2;
        this.sourceEntity = entityPredicate;
        this.blocked = bl;
        this.type = damageSourcePredicate;
    }

    public boolean test(ServerPlayerEntity serverPlayerEntity, DamageSource damageSource, float f, float f2, boolean bl) {
        if (this == ANY) {
            return false;
        }
        if (!this.dealt.test(f)) {
            return true;
        }
        if (!this.taken.test(f2)) {
            return true;
        }
        if (!this.sourceEntity.test(serverPlayerEntity, damageSource.getTrueSource())) {
            return true;
        }
        if (this.blocked != null && this.blocked != bl) {
            return true;
        }
        return this.type.test(serverPlayerEntity, damageSource);
    }

    public static DamagePredicate deserialize(@Nullable JsonElement jsonElement) {
        if (jsonElement != null && !jsonElement.isJsonNull()) {
            JsonObject jsonObject = JSONUtils.getJsonObject(jsonElement, "damage");
            MinMaxBounds.FloatBound floatBound = MinMaxBounds.FloatBound.fromJson(jsonObject.get("dealt"));
            MinMaxBounds.FloatBound floatBound2 = MinMaxBounds.FloatBound.fromJson(jsonObject.get("taken"));
            Boolean bl = jsonObject.has("blocked") ? Boolean.valueOf(JSONUtils.getBoolean(jsonObject, "blocked")) : null;
            EntityPredicate entityPredicate = EntityPredicate.deserialize(jsonObject.get("source_entity"));
            DamageSourcePredicate damageSourcePredicate = DamageSourcePredicate.deserialize(jsonObject.get("type"));
            return new DamagePredicate(floatBound, floatBound2, entityPredicate, bl, damageSourcePredicate);
        }
        return ANY;
    }

    public JsonElement serialize() {
        if (this == ANY) {
            return JsonNull.INSTANCE;
        }
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("dealt", this.dealt.serialize());
        jsonObject.add("taken", this.taken.serialize());
        jsonObject.add("source_entity", this.sourceEntity.serialize());
        jsonObject.add("type", this.type.serialize());
        if (this.blocked != null) {
            jsonObject.addProperty("blocked", this.blocked);
        }
        return jsonObject;
    }

    public static class Builder {
        private MinMaxBounds.FloatBound dealt = MinMaxBounds.FloatBound.UNBOUNDED;
        private MinMaxBounds.FloatBound taken = MinMaxBounds.FloatBound.UNBOUNDED;
        private EntityPredicate sourceEntity = EntityPredicate.ANY;
        private Boolean blocked;
        private DamageSourcePredicate type = DamageSourcePredicate.ANY;

        public static Builder create() {
            return new Builder();
        }

        public Builder blocked(Boolean bl) {
            this.blocked = bl;
            return this;
        }

        public Builder type(DamageSourcePredicate.Builder builder) {
            this.type = builder.build();
            return this;
        }

        public DamagePredicate build() {
            return new DamagePredicate(this.dealt, this.taken, this.sourceEntity, this.blocked, this.type);
        }
    }
}

