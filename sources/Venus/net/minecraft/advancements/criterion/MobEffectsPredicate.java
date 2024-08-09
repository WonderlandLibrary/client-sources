/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.advancements.criterion;

import com.google.common.collect.Maps;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public class MobEffectsPredicate {
    public static final MobEffectsPredicate ANY = new MobEffectsPredicate(Collections.emptyMap());
    private final Map<Effect, InstancePredicate> effects;

    public MobEffectsPredicate(Map<Effect, InstancePredicate> map) {
        this.effects = map;
    }

    public static MobEffectsPredicate any() {
        return new MobEffectsPredicate(Maps.newLinkedHashMap());
    }

    public MobEffectsPredicate addEffect(Effect effect) {
        this.effects.put(effect, new InstancePredicate());
        return this;
    }

    public boolean test(Entity entity2) {
        if (this == ANY) {
            return false;
        }
        return entity2 instanceof LivingEntity ? this.test(((LivingEntity)entity2).getActivePotionMap()) : false;
    }

    public boolean test(LivingEntity livingEntity) {
        return this == ANY ? true : this.test(livingEntity.getActivePotionMap());
    }

    public boolean test(Map<Effect, EffectInstance> map) {
        if (this == ANY) {
            return false;
        }
        for (Map.Entry<Effect, InstancePredicate> entry : this.effects.entrySet()) {
            EffectInstance effectInstance = map.get(entry.getKey());
            if (entry.getValue().test(effectInstance)) continue;
            return true;
        }
        return false;
    }

    public static MobEffectsPredicate deserialize(@Nullable JsonElement jsonElement) {
        if (jsonElement != null && !jsonElement.isJsonNull()) {
            JsonObject jsonObject = JSONUtils.getJsonObject(jsonElement, "effects");
            LinkedHashMap<Effect, InstancePredicate> linkedHashMap = Maps.newLinkedHashMap();
            for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
                ResourceLocation resourceLocation = new ResourceLocation(entry.getKey());
                Effect effect = Registry.EFFECTS.getOptional(resourceLocation).orElseThrow(() -> MobEffectsPredicate.lambda$deserialize$0(resourceLocation));
                InstancePredicate instancePredicate = InstancePredicate.deserialize(JSONUtils.getJsonObject(entry.getValue(), entry.getKey()));
                linkedHashMap.put(effect, instancePredicate);
            }
            return new MobEffectsPredicate(linkedHashMap);
        }
        return ANY;
    }

    public JsonElement serialize() {
        if (this == ANY) {
            return JsonNull.INSTANCE;
        }
        JsonObject jsonObject = new JsonObject();
        for (Map.Entry<Effect, InstancePredicate> entry : this.effects.entrySet()) {
            jsonObject.add(Registry.EFFECTS.getKey(entry.getKey()).toString(), entry.getValue().serialize());
        }
        return jsonObject;
    }

    private static JsonSyntaxException lambda$deserialize$0(ResourceLocation resourceLocation) {
        return new JsonSyntaxException("Unknown effect '" + resourceLocation + "'");
    }

    public static class InstancePredicate {
        private final MinMaxBounds.IntBound amplifier;
        private final MinMaxBounds.IntBound duration;
        @Nullable
        private final Boolean ambient;
        @Nullable
        private final Boolean visible;

        public InstancePredicate(MinMaxBounds.IntBound intBound, MinMaxBounds.IntBound intBound2, @Nullable Boolean bl, @Nullable Boolean bl2) {
            this.amplifier = intBound;
            this.duration = intBound2;
            this.ambient = bl;
            this.visible = bl2;
        }

        public InstancePredicate() {
            this(MinMaxBounds.IntBound.UNBOUNDED, MinMaxBounds.IntBound.UNBOUNDED, null, null);
        }

        public boolean test(@Nullable EffectInstance effectInstance) {
            if (effectInstance == null) {
                return true;
            }
            if (!this.amplifier.test(effectInstance.getAmplifier())) {
                return true;
            }
            if (!this.duration.test(effectInstance.getDuration())) {
                return true;
            }
            if (this.ambient != null && this.ambient.booleanValue() != effectInstance.isAmbient()) {
                return true;
            }
            return this.visible == null || this.visible.booleanValue() == effectInstance.doesShowParticles();
        }

        public JsonElement serialize() {
            JsonObject jsonObject = new JsonObject();
            jsonObject.add("amplifier", this.amplifier.serialize());
            jsonObject.add("duration", this.duration.serialize());
            jsonObject.addProperty("ambient", this.ambient);
            jsonObject.addProperty("visible", this.visible);
            return jsonObject;
        }

        public static InstancePredicate deserialize(JsonObject jsonObject) {
            MinMaxBounds.IntBound intBound = MinMaxBounds.IntBound.fromJson(jsonObject.get("amplifier"));
            MinMaxBounds.IntBound intBound2 = MinMaxBounds.IntBound.fromJson(jsonObject.get("duration"));
            Boolean bl = jsonObject.has("ambient") ? Boolean.valueOf(JSONUtils.getBoolean(jsonObject, "ambient")) : null;
            Boolean bl2 = jsonObject.has("visible") ? Boolean.valueOf(JSONUtils.getBoolean(jsonObject, "visible")) : null;
            return new InstancePredicate(intBound, intBound2, bl, bl2);
        }
    }
}

