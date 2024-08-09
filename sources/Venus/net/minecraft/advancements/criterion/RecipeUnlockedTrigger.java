/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.advancements.criterion;

import com.google.gson.JsonObject;
import net.minecraft.advancements.criterion.AbstractCriterionTrigger;
import net.minecraft.advancements.criterion.CriterionInstance;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.loot.ConditionArrayParser;
import net.minecraft.loot.ConditionArraySerializer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class RecipeUnlockedTrigger
extends AbstractCriterionTrigger<Instance> {
    private static final ResourceLocation ID = new ResourceLocation("recipe_unlocked");

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    @Override
    public Instance deserializeTrigger(JsonObject jsonObject, EntityPredicate.AndPredicate andPredicate, ConditionArrayParser conditionArrayParser) {
        ResourceLocation resourceLocation = new ResourceLocation(JSONUtils.getString(jsonObject, "recipe"));
        return new Instance(andPredicate, resourceLocation);
    }

    public void trigger(ServerPlayerEntity serverPlayerEntity, IRecipe<?> iRecipe) {
        this.triggerListeners(serverPlayerEntity, arg_0 -> RecipeUnlockedTrigger.lambda$trigger$0(iRecipe, arg_0));
    }

    public static Instance create(ResourceLocation resourceLocation) {
        return new Instance(EntityPredicate.AndPredicate.ANY_AND, resourceLocation);
    }

    @Override
    public CriterionInstance deserializeTrigger(JsonObject jsonObject, EntityPredicate.AndPredicate andPredicate, ConditionArrayParser conditionArrayParser) {
        return this.deserializeTrigger(jsonObject, andPredicate, conditionArrayParser);
    }

    private static boolean lambda$trigger$0(IRecipe iRecipe, Instance instance) {
        return instance.test(iRecipe);
    }

    public static class Instance
    extends CriterionInstance {
        private final ResourceLocation recipe;

        public Instance(EntityPredicate.AndPredicate andPredicate, ResourceLocation resourceLocation) {
            super(ID, andPredicate);
            this.recipe = resourceLocation;
        }

        @Override
        public JsonObject serialize(ConditionArraySerializer conditionArraySerializer) {
            JsonObject jsonObject = super.serialize(conditionArraySerializer);
            jsonObject.addProperty("recipe", this.recipe.toString());
            return jsonObject;
        }

        public boolean test(IRecipe<?> iRecipe) {
            return this.recipe.equals(iRecipe.getId());
        }
    }
}

