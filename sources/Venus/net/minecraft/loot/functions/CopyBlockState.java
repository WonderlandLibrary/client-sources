/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.loot.functions;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.HashSet;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootFunction;
import net.minecraft.loot.LootFunctionType;
import net.minecraft.loot.LootParameter;
import net.minecraft.loot.LootParameters;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.loot.functions.ILootFunction;
import net.minecraft.loot.functions.LootFunctionManager;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.Property;
import net.minecraft.state.StateContainer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public class CopyBlockState
extends LootFunction {
    private final Block field_227543_a_;
    private final Set<Property<?>> field_227544_c_;

    private CopyBlockState(ILootCondition[] iLootConditionArray, Block block, Set<Property<?>> set) {
        super(iLootConditionArray);
        this.field_227543_a_ = block;
        this.field_227544_c_ = set;
    }

    @Override
    public LootFunctionType getFunctionType() {
        return LootFunctionManager.COPY_STATE;
    }

    @Override
    public Set<LootParameter<?>> getRequiredParameters() {
        return ImmutableSet.of(LootParameters.BLOCK_STATE);
    }

    @Override
    protected ItemStack doApply(ItemStack itemStack, LootContext lootContext) {
        BlockState blockState = lootContext.get(LootParameters.BLOCK_STATE);
        if (blockState != null) {
            CompoundNBT compoundNBT;
            CompoundNBT compoundNBT2 = itemStack.getOrCreateTag();
            if (compoundNBT2.contains("BlockStateTag", 1)) {
                compoundNBT = compoundNBT2.getCompound("BlockStateTag");
            } else {
                compoundNBT = new CompoundNBT();
                compoundNBT2.put("BlockStateTag", compoundNBT);
            }
            this.field_227544_c_.stream().filter(blockState::hasProperty).forEach(arg_0 -> CopyBlockState.lambda$doApply$0(compoundNBT, blockState, arg_0));
        }
        return itemStack;
    }

    public static Builder func_227545_a_(Block block) {
        return new Builder(block);
    }

    private static <T extends Comparable<T>> String func_227546_a_(BlockState blockState, Property<T> property) {
        T t = blockState.get(property);
        return property.getName(t);
    }

    private static void lambda$doApply$0(CompoundNBT compoundNBT, BlockState blockState, Property property) {
        compoundNBT.putString(property.getName(), CopyBlockState.func_227546_a_(blockState, property));
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Builder
    extends LootFunction.Builder<Builder> {
        private final Block field_227550_a_;
        private final Set<Property<?>> field_227551_b_ = Sets.newHashSet();

        private Builder(Block block) {
            this.field_227550_a_ = block;
        }

        public Builder func_227552_a_(Property<?> property) {
            if (!this.field_227550_a_.getStateContainer().getProperties().contains(property)) {
                throw new IllegalStateException("Property " + property + " is not present on block " + this.field_227550_a_);
            }
            this.field_227551_b_.add(property);
            return this;
        }

        @Override
        protected Builder doCast() {
            return this;
        }

        @Override
        public ILootFunction build() {
            return new CopyBlockState(this.getConditions(), this.field_227550_a_, this.field_227551_b_);
        }

        @Override
        protected LootFunction.Builder doCast() {
            return this.doCast();
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Serializer
    extends LootFunction.Serializer<CopyBlockState> {
        @Override
        public void serialize(JsonObject jsonObject, CopyBlockState copyBlockState, JsonSerializationContext jsonSerializationContext) {
            super.serialize(jsonObject, copyBlockState, jsonSerializationContext);
            jsonObject.addProperty("block", Registry.BLOCK.getKey(copyBlockState.field_227543_a_).toString());
            JsonArray jsonArray = new JsonArray();
            copyBlockState.field_227544_c_.forEach(arg_0 -> Serializer.lambda$serialize$0(jsonArray, arg_0));
            jsonObject.add("properties", jsonArray);
        }

        @Override
        public CopyBlockState deserialize(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext, ILootCondition[] iLootConditionArray) {
            ResourceLocation resourceLocation = new ResourceLocation(JSONUtils.getString(jsonObject, "block"));
            Block block = Registry.BLOCK.getOptional(resourceLocation).orElseThrow(() -> Serializer.lambda$deserialize$1(resourceLocation));
            StateContainer<Block, BlockState> stateContainer = block.getStateContainer();
            HashSet<Property<?>> hashSet = Sets.newHashSet();
            JsonArray jsonArray = JSONUtils.getJsonArray(jsonObject, "properties", null);
            if (jsonArray != null) {
                jsonArray.forEach(arg_0 -> Serializer.lambda$deserialize$2(hashSet, stateContainer, arg_0));
            }
            return new CopyBlockState(iLootConditionArray, block, hashSet);
        }

        @Override
        public LootFunction deserialize(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext, ILootCondition[] iLootConditionArray) {
            return this.deserialize(jsonObject, jsonDeserializationContext, iLootConditionArray);
        }

        @Override
        public void serialize(JsonObject jsonObject, LootFunction lootFunction, JsonSerializationContext jsonSerializationContext) {
            this.serialize(jsonObject, (CopyBlockState)lootFunction, jsonSerializationContext);
        }

        @Override
        public void serialize(JsonObject jsonObject, Object object, JsonSerializationContext jsonSerializationContext) {
            this.serialize(jsonObject, (CopyBlockState)object, jsonSerializationContext);
        }

        private static void lambda$deserialize$2(Set set, StateContainer stateContainer, JsonElement jsonElement) {
            set.add(stateContainer.getProperty(JSONUtils.getString(jsonElement, "property")));
        }

        private static IllegalArgumentException lambda$deserialize$1(ResourceLocation resourceLocation) {
            return new IllegalArgumentException("Can't find block " + resourceLocation);
        }

        private static void lambda$serialize$0(JsonArray jsonArray, Property property) {
            jsonArray.add(property.getName());
        }
    }
}

