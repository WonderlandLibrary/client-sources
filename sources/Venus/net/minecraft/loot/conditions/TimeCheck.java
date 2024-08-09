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
import net.minecraft.loot.RandomValueRange;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.loot.conditions.LootConditionManager;
import net.minecraft.util.JSONUtils;
import net.minecraft.world.server.ServerWorld;

public class TimeCheck
implements ILootCondition {
    @Nullable
    private final Long field_227570_a_;
    private final RandomValueRange field_227571_b_;

    private TimeCheck(@Nullable Long l, RandomValueRange randomValueRange) {
        this.field_227570_a_ = l;
        this.field_227571_b_ = randomValueRange;
    }

    @Override
    public LootConditionType func_230419_b_() {
        return LootConditionManager.TIME_CHECK;
    }

    @Override
    public boolean test(LootContext lootContext) {
        ServerWorld serverWorld = lootContext.getWorld();
        long l = serverWorld.getDayTime();
        if (this.field_227570_a_ != null) {
            l %= this.field_227570_a_.longValue();
        }
        return this.field_227571_b_.isInRange((int)l);
    }

    @Override
    public boolean test(Object object) {
        return this.test((LootContext)object);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Serializer
    implements ILootSerializer<TimeCheck> {
        @Override
        public void serialize(JsonObject jsonObject, TimeCheck timeCheck, JsonSerializationContext jsonSerializationContext) {
            jsonObject.addProperty("period", timeCheck.field_227570_a_);
            jsonObject.add("value", jsonSerializationContext.serialize(timeCheck.field_227571_b_));
        }

        @Override
        public TimeCheck deserialize(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext) {
            Long l = jsonObject.has("period") ? Long.valueOf(JSONUtils.getLong(jsonObject, "period")) : null;
            RandomValueRange randomValueRange = JSONUtils.deserializeClass(jsonObject, "value", jsonDeserializationContext, RandomValueRange.class);
            return new TimeCheck(l, randomValueRange);
        }

        @Override
        public Object deserialize(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext) {
            return this.deserialize(jsonObject, jsonDeserializationContext);
        }

        @Override
        public void serialize(JsonObject jsonObject, Object object, JsonSerializationContext jsonSerializationContext) {
            this.serialize(jsonObject, (TimeCheck)object, jsonSerializationContext);
        }
    }
}

