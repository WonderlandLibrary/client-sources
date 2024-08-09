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
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.block.Block;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.ConditionArrayParser;
import net.minecraft.loot.ConditionArraySerializer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class BeeNestDestroyedTrigger
extends AbstractCriterionTrigger<Instance> {
    private static final ResourceLocation ID = new ResourceLocation("bee_nest_destroyed");

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    @Override
    public Instance deserializeTrigger(JsonObject jsonObject, EntityPredicate.AndPredicate andPredicate, ConditionArrayParser conditionArrayParser) {
        Block block = BeeNestDestroyedTrigger.deserializeBlock(jsonObject);
        ItemPredicate itemPredicate = ItemPredicate.deserialize(jsonObject.get("item"));
        MinMaxBounds.IntBound intBound = MinMaxBounds.IntBound.fromJson(jsonObject.get("num_bees_inside"));
        return new Instance(andPredicate, block, itemPredicate, intBound);
    }

    @Nullable
    private static Block deserializeBlock(JsonObject jsonObject) {
        if (jsonObject.has("block")) {
            ResourceLocation resourceLocation = new ResourceLocation(JSONUtils.getString(jsonObject, "block"));
            return Registry.BLOCK.getOptional(resourceLocation).orElseThrow(() -> BeeNestDestroyedTrigger.lambda$deserializeBlock$0(resourceLocation));
        }
        return null;
    }

    public void test(ServerPlayerEntity serverPlayerEntity, Block block, ItemStack itemStack, int n) {
        this.triggerListeners(serverPlayerEntity, arg_0 -> BeeNestDestroyedTrigger.lambda$test$1(block, itemStack, n, arg_0));
    }

    @Override
    public CriterionInstance deserializeTrigger(JsonObject jsonObject, EntityPredicate.AndPredicate andPredicate, ConditionArrayParser conditionArrayParser) {
        return this.deserializeTrigger(jsonObject, andPredicate, conditionArrayParser);
    }

    private static boolean lambda$test$1(Block block, ItemStack itemStack, int n, Instance instance) {
        return instance.test(block, itemStack, n);
    }

    private static JsonSyntaxException lambda$deserializeBlock$0(ResourceLocation resourceLocation) {
        return new JsonSyntaxException("Unknown block type '" + resourceLocation + "'");
    }

    public static class Instance
    extends CriterionInstance {
        @Nullable
        private final Block block;
        private final ItemPredicate itemPredicate;
        private final MinMaxBounds.IntBound beesContained;

        public Instance(EntityPredicate.AndPredicate andPredicate, @Nullable Block block, ItemPredicate itemPredicate, MinMaxBounds.IntBound intBound) {
            super(ID, andPredicate);
            this.block = block;
            this.itemPredicate = itemPredicate;
            this.beesContained = intBound;
        }

        public static Instance createNewInstance(Block block, ItemPredicate.Builder builder, MinMaxBounds.IntBound intBound) {
            return new Instance(EntityPredicate.AndPredicate.ANY_AND, block, builder.build(), intBound);
        }

        public boolean test(Block block, ItemStack itemStack, int n) {
            if (this.block != null && block != this.block) {
                return true;
            }
            return !this.itemPredicate.test(itemStack) ? false : this.beesContained.test(n);
        }

        @Override
        public JsonObject serialize(ConditionArraySerializer conditionArraySerializer) {
            JsonObject jsonObject = super.serialize(conditionArraySerializer);
            if (this.block != null) {
                jsonObject.addProperty("block", Registry.BLOCK.getKey(this.block).toString());
            }
            jsonObject.add("item", this.itemPredicate.serialize());
            jsonObject.add("num_bees_inside", this.beesContained.serialize());
            return jsonObject;
        }
    }
}

