/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.advancements.criterion;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import javax.annotation.Nullable;
import net.minecraft.advancements.criterion.AbstractCriterionTrigger;
import net.minecraft.advancements.criterion.CriterionInstance;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.advancements.criterion.LocationPredicate;
import net.minecraft.advancements.criterion.StatePropertiesPredicate;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.ConditionArrayParser;
import net.minecraft.loot.ConditionArraySerializer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.server.ServerWorld;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class PlacedBlockTrigger
extends AbstractCriterionTrigger<Instance> {
    private static final ResourceLocation ID = new ResourceLocation("placed_block");

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    @Override
    public Instance deserializeTrigger(JsonObject jsonObject, EntityPredicate.AndPredicate andPredicate, ConditionArrayParser conditionArrayParser) {
        Block block = PlacedBlockTrigger.deserializeBlock(jsonObject);
        StatePropertiesPredicate statePropertiesPredicate = StatePropertiesPredicate.deserializeProperties(jsonObject.get("state"));
        if (block != null) {
            statePropertiesPredicate.forEachNotPresent(block.getStateContainer(), arg_0 -> PlacedBlockTrigger.lambda$deserializeTrigger$0(block, arg_0));
        }
        LocationPredicate locationPredicate = LocationPredicate.deserialize(jsonObject.get("location"));
        ItemPredicate itemPredicate = ItemPredicate.deserialize(jsonObject.get("item"));
        return new Instance(andPredicate, block, statePropertiesPredicate, locationPredicate, itemPredicate);
    }

    @Nullable
    private static Block deserializeBlock(JsonObject jsonObject) {
        if (jsonObject.has("block")) {
            ResourceLocation resourceLocation = new ResourceLocation(JSONUtils.getString(jsonObject, "block"));
            return Registry.BLOCK.getOptional(resourceLocation).orElseThrow(() -> PlacedBlockTrigger.lambda$deserializeBlock$1(resourceLocation));
        }
        return null;
    }

    public void trigger(ServerPlayerEntity serverPlayerEntity, BlockPos blockPos, ItemStack itemStack) {
        BlockState blockState = serverPlayerEntity.getServerWorld().getBlockState(blockPos);
        this.triggerListeners(serverPlayerEntity, arg_0 -> PlacedBlockTrigger.lambda$trigger$2(blockState, blockPos, serverPlayerEntity, itemStack, arg_0));
    }

    @Override
    public CriterionInstance deserializeTrigger(JsonObject jsonObject, EntityPredicate.AndPredicate andPredicate, ConditionArrayParser conditionArrayParser) {
        return this.deserializeTrigger(jsonObject, andPredicate, conditionArrayParser);
    }

    private static boolean lambda$trigger$2(BlockState blockState, BlockPos blockPos, ServerPlayerEntity serverPlayerEntity, ItemStack itemStack, Instance instance) {
        return instance.test(blockState, blockPos, serverPlayerEntity.getServerWorld(), itemStack);
    }

    private static JsonSyntaxException lambda$deserializeBlock$1(ResourceLocation resourceLocation) {
        return new JsonSyntaxException("Unknown block type '" + resourceLocation + "'");
    }

    private static void lambda$deserializeTrigger$0(Block block, String string) {
        throw new JsonSyntaxException("Block " + block + " has no property " + string + ":");
    }

    public static class Instance
    extends CriterionInstance {
        private final Block block;
        private final StatePropertiesPredicate properties;
        private final LocationPredicate location;
        private final ItemPredicate item;

        public Instance(EntityPredicate.AndPredicate andPredicate, @Nullable Block block, StatePropertiesPredicate statePropertiesPredicate, LocationPredicate locationPredicate, ItemPredicate itemPredicate) {
            super(ID, andPredicate);
            this.block = block;
            this.properties = statePropertiesPredicate;
            this.location = locationPredicate;
            this.item = itemPredicate;
        }

        public static Instance placedBlock(Block block) {
            return new Instance(EntityPredicate.AndPredicate.ANY_AND, block, StatePropertiesPredicate.EMPTY, LocationPredicate.ANY, ItemPredicate.ANY);
        }

        public boolean test(BlockState blockState, BlockPos blockPos, ServerWorld serverWorld, ItemStack itemStack) {
            if (this.block != null && !blockState.isIn(this.block)) {
                return true;
            }
            if (!this.properties.matches(blockState)) {
                return true;
            }
            if (!this.location.test(serverWorld, blockPos.getX(), blockPos.getY(), blockPos.getZ())) {
                return true;
            }
            return this.item.test(itemStack);
        }

        @Override
        public JsonObject serialize(ConditionArraySerializer conditionArraySerializer) {
            JsonObject jsonObject = super.serialize(conditionArraySerializer);
            if (this.block != null) {
                jsonObject.addProperty("block", Registry.BLOCK.getKey(this.block).toString());
            }
            jsonObject.add("state", this.properties.toJsonElement());
            jsonObject.add("location", this.location.serialize());
            jsonObject.add("item", this.item.serialize());
            return jsonObject;
        }
    }
}

