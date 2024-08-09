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
import net.minecraft.advancements.criterion.StatePropertiesPredicate;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.loot.ConditionArrayParser;
import net.minecraft.loot.ConditionArraySerializer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class EnterBlockTrigger
extends AbstractCriterionTrigger<Instance> {
    private static final ResourceLocation ID = new ResourceLocation("enter_block");

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    @Override
    public Instance deserializeTrigger(JsonObject jsonObject, EntityPredicate.AndPredicate andPredicate, ConditionArrayParser conditionArrayParser) {
        Block block = EnterBlockTrigger.deserializeBlock(jsonObject);
        StatePropertiesPredicate statePropertiesPredicate = StatePropertiesPredicate.deserializeProperties(jsonObject.get("state"));
        if (block != null) {
            statePropertiesPredicate.forEachNotPresent(block.getStateContainer(), arg_0 -> EnterBlockTrigger.lambda$deserializeTrigger$0(block, arg_0));
        }
        return new Instance(andPredicate, block, statePropertiesPredicate);
    }

    @Nullable
    private static Block deserializeBlock(JsonObject jsonObject) {
        if (jsonObject.has("block")) {
            ResourceLocation resourceLocation = new ResourceLocation(JSONUtils.getString(jsonObject, "block"));
            return Registry.BLOCK.getOptional(resourceLocation).orElseThrow(() -> EnterBlockTrigger.lambda$deserializeBlock$1(resourceLocation));
        }
        return null;
    }

    public void trigger(ServerPlayerEntity serverPlayerEntity, BlockState blockState) {
        this.triggerListeners(serverPlayerEntity, arg_0 -> EnterBlockTrigger.lambda$trigger$2(blockState, arg_0));
    }

    @Override
    public CriterionInstance deserializeTrigger(JsonObject jsonObject, EntityPredicate.AndPredicate andPredicate, ConditionArrayParser conditionArrayParser) {
        return this.deserializeTrigger(jsonObject, andPredicate, conditionArrayParser);
    }

    private static boolean lambda$trigger$2(BlockState blockState, Instance instance) {
        return instance.test(blockState);
    }

    private static JsonSyntaxException lambda$deserializeBlock$1(ResourceLocation resourceLocation) {
        return new JsonSyntaxException("Unknown block type '" + resourceLocation + "'");
    }

    private static void lambda$deserializeTrigger$0(Block block, String string) {
        throw new JsonSyntaxException("Block " + block + " has no property " + string);
    }

    public static class Instance
    extends CriterionInstance {
        private final Block block;
        private final StatePropertiesPredicate properties;

        public Instance(EntityPredicate.AndPredicate andPredicate, @Nullable Block block, StatePropertiesPredicate statePropertiesPredicate) {
            super(ID, andPredicate);
            this.block = block;
            this.properties = statePropertiesPredicate;
        }

        public static Instance forBlock(Block block) {
            return new Instance(EntityPredicate.AndPredicate.ANY_AND, block, StatePropertiesPredicate.EMPTY);
        }

        @Override
        public JsonObject serialize(ConditionArraySerializer conditionArraySerializer) {
            JsonObject jsonObject = super.serialize(conditionArraySerializer);
            if (this.block != null) {
                jsonObject.addProperty("block", Registry.BLOCK.getKey(this.block).toString());
            }
            jsonObject.add("state", this.properties.toJsonElement());
            return jsonObject;
        }

        public boolean test(BlockState blockState) {
            if (this.block != null && !blockState.isIn(this.block)) {
                return true;
            }
            return this.properties.matches(blockState);
        }
    }
}

