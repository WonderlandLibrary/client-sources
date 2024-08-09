/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.advancements.criterion;

import com.google.gson.JsonObject;
import net.minecraft.advancements.criterion.AbstractCriterionTrigger;
import net.minecraft.advancements.criterion.CriterionInstance;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.loot.ConditionArrayParser;
import net.minecraft.loot.ConditionArraySerializer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class PlayerGeneratesContainerLootTrigger
extends AbstractCriterionTrigger<Instance> {
    private static final ResourceLocation ID = new ResourceLocation("player_generates_container_loot");

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    @Override
    protected Instance deserializeTrigger(JsonObject jsonObject, EntityPredicate.AndPredicate andPredicate, ConditionArrayParser conditionArrayParser) {
        ResourceLocation resourceLocation = new ResourceLocation(JSONUtils.getString(jsonObject, "loot_table"));
        return new Instance(andPredicate, resourceLocation);
    }

    public void test(ServerPlayerEntity serverPlayerEntity, ResourceLocation resourceLocation) {
        this.triggerListeners(serverPlayerEntity, arg_0 -> PlayerGeneratesContainerLootTrigger.lambda$test$0(resourceLocation, arg_0));
    }

    @Override
    protected CriterionInstance deserializeTrigger(JsonObject jsonObject, EntityPredicate.AndPredicate andPredicate, ConditionArrayParser conditionArrayParser) {
        return this.deserializeTrigger(jsonObject, andPredicate, conditionArrayParser);
    }

    private static boolean lambda$test$0(ResourceLocation resourceLocation, Instance instance) {
        return instance.test(resourceLocation);
    }

    public static class Instance
    extends CriterionInstance {
        private final ResourceLocation generatedLoot;

        public Instance(EntityPredicate.AndPredicate andPredicate, ResourceLocation resourceLocation) {
            super(ID, andPredicate);
            this.generatedLoot = resourceLocation;
        }

        public static Instance create(ResourceLocation resourceLocation) {
            return new Instance(EntityPredicate.AndPredicate.ANY_AND, resourceLocation);
        }

        public boolean test(ResourceLocation resourceLocation) {
            return this.generatedLoot.equals(resourceLocation);
        }

        @Override
        public JsonObject serialize(ConditionArraySerializer conditionArraySerializer) {
            JsonObject jsonObject = super.serialize(conditionArraySerializer);
            jsonObject.addProperty("loot_table", this.generatedLoot.toString());
            return jsonObject;
        }
    }
}

