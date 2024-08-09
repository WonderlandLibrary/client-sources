/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.loot.conditions;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import javax.annotation.Nullable;
import net.minecraft.loot.ILootSerializer;
import net.minecraft.loot.LootConditionType;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.loot.conditions.LootConditionManager;
import net.minecraft.util.JSONUtils;
import net.minecraft.world.server.ServerWorld;

public class WeatherCheck
implements ILootCondition {
    @Nullable
    private final Boolean raining;
    @Nullable
    private final Boolean thundering;

    private WeatherCheck(@Nullable Boolean bl, @Nullable Boolean bl2) {
        this.raining = bl;
        this.thundering = bl2;
    }

    @Override
    public LootConditionType func_230419_b_() {
        return LootConditionManager.WEATHER_CHECK;
    }

    @Override
    public boolean test(LootContext lootContext) {
        ServerWorld serverWorld = lootContext.getWorld();
        if (this.raining != null && this.raining.booleanValue() != serverWorld.isRaining()) {
            return true;
        }
        return this.thundering == null || this.thundering.booleanValue() == serverWorld.isThundering();
    }

    @Override
    public boolean test(Object object) {
        return this.test((LootContext)object);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Serializer
    implements ILootSerializer<WeatherCheck> {
        @Override
        public void serialize(JsonObject jsonObject, WeatherCheck weatherCheck, JsonSerializationContext jsonSerializationContext) {
            jsonObject.addProperty("raining", weatherCheck.raining);
            jsonObject.addProperty("thundering", weatherCheck.thundering);
        }

        @Override
        public WeatherCheck deserialize(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext) {
            Boolean bl = jsonObject.has("raining") ? Boolean.valueOf(JSONUtils.getBoolean(jsonObject, "raining")) : null;
            Boolean bl2 = jsonObject.has("thundering") ? Boolean.valueOf(JSONUtils.getBoolean(jsonObject, "thundering")) : null;
            return new WeatherCheck(bl, bl2);
        }

        @Override
        public Object deserialize(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext) {
            return this.deserialize(jsonObject, jsonDeserializationContext);
        }

        @Override
        public void serialize(JsonObject jsonObject, Object object, JsonSerializationContext jsonSerializationContext) {
            this.serialize(jsonObject, (WeatherCheck)object, jsonSerializationContext);
        }
    }
}

