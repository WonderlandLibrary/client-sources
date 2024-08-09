/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.loot.conditions;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSyntaxException;
import java.util.Set;
import net.minecraft.advancements.criterion.StatePropertiesPredicate;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.loot.ILootSerializer;
import net.minecraft.loot.LootConditionType;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameter;
import net.minecraft.loot.LootParameters;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.loot.conditions.LootConditionManager;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public class BlockStateProperty
implements ILootCondition {
    private final Block block;
    private final StatePropertiesPredicate properties;

    private BlockStateProperty(Block block, StatePropertiesPredicate statePropertiesPredicate) {
        this.block = block;
        this.properties = statePropertiesPredicate;
    }

    @Override
    public LootConditionType func_230419_b_() {
        return LootConditionManager.BLOCK_STATE_PROPERTY;
    }

    @Override
    public Set<LootParameter<?>> getRequiredParameters() {
        return ImmutableSet.of(LootParameters.BLOCK_STATE);
    }

    @Override
    public boolean test(LootContext lootContext) {
        BlockState blockState = lootContext.get(LootParameters.BLOCK_STATE);
        return blockState != null && this.block == blockState.getBlock() && this.properties.matches(blockState);
    }

    public static Builder builder(Block block) {
        return new Builder(block);
    }

    @Override
    public boolean test(Object object) {
        return this.test((LootContext)object);
    }

    public static class Builder
    implements ILootCondition.IBuilder {
        private final Block block;
        private StatePropertiesPredicate desiredProperties = StatePropertiesPredicate.EMPTY;

        public Builder(Block block) {
            this.block = block;
        }

        public Builder fromProperties(StatePropertiesPredicate.Builder builder) {
            this.desiredProperties = builder.build();
            return this;
        }

        @Override
        public ILootCondition build() {
            return new BlockStateProperty(this.block, this.desiredProperties);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Serializer
    implements ILootSerializer<BlockStateProperty> {
        @Override
        public void serialize(JsonObject jsonObject, BlockStateProperty blockStateProperty, JsonSerializationContext jsonSerializationContext) {
            jsonObject.addProperty("block", Registry.BLOCK.getKey(blockStateProperty.block).toString());
            jsonObject.add("properties", blockStateProperty.properties.toJsonElement());
        }

        @Override
        public BlockStateProperty deserialize(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext) {
            ResourceLocation resourceLocation = new ResourceLocation(JSONUtils.getString(jsonObject, "block"));
            Block block = Registry.BLOCK.getOptional(resourceLocation).orElseThrow(() -> Serializer.lambda$deserialize$0(resourceLocation));
            StatePropertiesPredicate statePropertiesPredicate = StatePropertiesPredicate.deserializeProperties(jsonObject.get("properties"));
            statePropertiesPredicate.forEachNotPresent(block.getStateContainer(), arg_0 -> Serializer.lambda$deserialize$1(block, arg_0));
            return new BlockStateProperty(block, statePropertiesPredicate);
        }

        @Override
        public Object deserialize(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext) {
            return this.deserialize(jsonObject, jsonDeserializationContext);
        }

        @Override
        public void serialize(JsonObject jsonObject, Object object, JsonSerializationContext jsonSerializationContext) {
            this.serialize(jsonObject, (BlockStateProperty)object, jsonSerializationContext);
        }

        private static void lambda$deserialize$1(Block block, String string) {
            throw new JsonSyntaxException("Block " + block + " has no property " + string);
        }

        private static IllegalArgumentException lambda$deserialize$0(ResourceLocation resourceLocation) {
            return new IllegalArgumentException("Can't find block " + resourceLocation);
        }
    }
}

