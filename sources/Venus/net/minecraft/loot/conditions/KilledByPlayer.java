/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.loot.conditions;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Set;
import net.minecraft.loot.ILootSerializer;
import net.minecraft.loot.LootConditionType;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameter;
import net.minecraft.loot.LootParameters;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.loot.conditions.LootConditionManager;

public class KilledByPlayer
implements ILootCondition {
    private static final KilledByPlayer INSTANCE = new KilledByPlayer();

    private KilledByPlayer() {
    }

    @Override
    public LootConditionType func_230419_b_() {
        return LootConditionManager.KILLED_BY_PLAYER;
    }

    @Override
    public Set<LootParameter<?>> getRequiredParameters() {
        return ImmutableSet.of(LootParameters.LAST_DAMAGE_PLAYER);
    }

    @Override
    public boolean test(LootContext lootContext) {
        return lootContext.has(LootParameters.LAST_DAMAGE_PLAYER);
    }

    public static ILootCondition.IBuilder builder() {
        return KilledByPlayer::lambda$builder$0;
    }

    @Override
    public boolean test(Object object) {
        return this.test((LootContext)object);
    }

    private static ILootCondition lambda$builder$0() {
        return INSTANCE;
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Serializer
    implements ILootSerializer<KilledByPlayer> {
        @Override
        public void serialize(JsonObject jsonObject, KilledByPlayer killedByPlayer, JsonSerializationContext jsonSerializationContext) {
        }

        @Override
        public KilledByPlayer deserialize(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext) {
            return INSTANCE;
        }

        @Override
        public Object deserialize(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext) {
            return this.deserialize(jsonObject, jsonDeserializationContext);
        }

        @Override
        public void serialize(JsonObject jsonObject, Object object, JsonSerializationContext jsonSerializationContext) {
            this.serialize(jsonObject, (KilledByPlayer)object, jsonSerializationContext);
        }
    }
}

