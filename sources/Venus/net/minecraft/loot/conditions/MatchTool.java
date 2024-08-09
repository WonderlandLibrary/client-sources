/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.loot.conditions;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Set;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.ILootSerializer;
import net.minecraft.loot.LootConditionType;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameter;
import net.minecraft.loot.LootParameters;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.loot.conditions.LootConditionManager;

public class MatchTool
implements ILootCondition {
    private final ItemPredicate predicate;

    public MatchTool(ItemPredicate itemPredicate) {
        this.predicate = itemPredicate;
    }

    @Override
    public LootConditionType func_230419_b_() {
        return LootConditionManager.MATCH_TOOL;
    }

    @Override
    public Set<LootParameter<?>> getRequiredParameters() {
        return ImmutableSet.of(LootParameters.TOOL);
    }

    @Override
    public boolean test(LootContext lootContext) {
        ItemStack itemStack = lootContext.get(LootParameters.TOOL);
        return itemStack != null && this.predicate.test(itemStack);
    }

    public static ILootCondition.IBuilder builder(ItemPredicate.Builder builder) {
        return () -> MatchTool.lambda$builder$0(builder);
    }

    @Override
    public boolean test(Object object) {
        return this.test((LootContext)object);
    }

    private static ILootCondition lambda$builder$0(ItemPredicate.Builder builder) {
        return new MatchTool(builder.build());
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Serializer
    implements ILootSerializer<MatchTool> {
        @Override
        public void serialize(JsonObject jsonObject, MatchTool matchTool, JsonSerializationContext jsonSerializationContext) {
            jsonObject.add("predicate", matchTool.predicate.serialize());
        }

        @Override
        public MatchTool deserialize(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext) {
            ItemPredicate itemPredicate = ItemPredicate.deserialize(jsonObject.get("predicate"));
            return new MatchTool(itemPredicate);
        }

        @Override
        public Object deserialize(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext) {
            return this.deserialize(jsonObject, jsonDeserializationContext);
        }

        @Override
        public void serialize(JsonObject jsonObject, Object object, JsonSerializationContext jsonSerializationContext) {
            this.serialize(jsonObject, (MatchTool)object, jsonSerializationContext);
        }
    }
}

