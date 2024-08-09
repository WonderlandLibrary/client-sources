/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.advancements.criterion;

import com.google.gson.JsonObject;
import net.minecraft.advancements.criterion.AbstractCriterionTrigger;
import net.minecraft.advancements.criterion.CriterionInstance;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.advancements.criterion.LocationPredicate;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.ConditionArrayParser;
import net.minecraft.loot.ConditionArraySerializer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class RightClickBlockWithItemTrigger
extends AbstractCriterionTrigger<Instance> {
    private static final ResourceLocation ID = new ResourceLocation("item_used_on_block");

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    @Override
    public Instance deserializeTrigger(JsonObject jsonObject, EntityPredicate.AndPredicate andPredicate, ConditionArrayParser conditionArrayParser) {
        LocationPredicate locationPredicate = LocationPredicate.deserialize(jsonObject.get("location"));
        ItemPredicate itemPredicate = ItemPredicate.deserialize(jsonObject.get("item"));
        return new Instance(andPredicate, locationPredicate, itemPredicate);
    }

    public void test(ServerPlayerEntity serverPlayerEntity, BlockPos blockPos, ItemStack itemStack) {
        BlockState blockState = serverPlayerEntity.getServerWorld().getBlockState(blockPos);
        this.triggerListeners(serverPlayerEntity, arg_0 -> RightClickBlockWithItemTrigger.lambda$test$0(blockState, serverPlayerEntity, blockPos, itemStack, arg_0));
    }

    @Override
    public CriterionInstance deserializeTrigger(JsonObject jsonObject, EntityPredicate.AndPredicate andPredicate, ConditionArrayParser conditionArrayParser) {
        return this.deserializeTrigger(jsonObject, andPredicate, conditionArrayParser);
    }

    private static boolean lambda$test$0(BlockState blockState, ServerPlayerEntity serverPlayerEntity, BlockPos blockPos, ItemStack itemStack, Instance instance) {
        return instance.test(blockState, serverPlayerEntity.getServerWorld(), blockPos, itemStack);
    }

    public static class Instance
    extends CriterionInstance {
        private final LocationPredicate location;
        private final ItemPredicate stack;

        public Instance(EntityPredicate.AndPredicate andPredicate, LocationPredicate locationPredicate, ItemPredicate itemPredicate) {
            super(ID, andPredicate);
            this.location = locationPredicate;
            this.stack = itemPredicate;
        }

        public static Instance create(LocationPredicate.Builder builder, ItemPredicate.Builder builder2) {
            return new Instance(EntityPredicate.AndPredicate.ANY_AND, builder.build(), builder2.build());
        }

        public boolean test(BlockState blockState, ServerWorld serverWorld, BlockPos blockPos, ItemStack itemStack) {
            return !this.location.test(serverWorld, (double)blockPos.getX() + 0.5, (double)blockPos.getY() + 0.5, (double)blockPos.getZ() + 0.5) ? false : this.stack.test(itemStack);
        }

        @Override
        public JsonObject serialize(ConditionArraySerializer conditionArraySerializer) {
            JsonObject jsonObject = super.serialize(conditionArraySerializer);
            jsonObject.add("location", this.location.serialize());
            jsonObject.add("item", this.stack.serialize());
            return jsonObject;
        }
    }
}

