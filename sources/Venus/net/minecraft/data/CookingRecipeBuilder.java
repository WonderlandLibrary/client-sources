/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.data;

import com.google.gson.JsonObject;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.ICriterionInstance;
import net.minecraft.advancements.IRequirementsStrategy;
import net.minecraft.advancements.criterion.RecipeUnlockedTrigger;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.AbstractCookingRecipe;
import net.minecraft.item.crafting.CookingRecipeSerializer;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public class CookingRecipeBuilder {
    private final Item result;
    private final Ingredient ingredient;
    private final float experience;
    private final int cookingTime;
    private final Advancement.Builder advancementBuilder = Advancement.Builder.builder();
    private String group;
    private final CookingRecipeSerializer<?> recipeSerializer;

    private CookingRecipeBuilder(IItemProvider iItemProvider, Ingredient ingredient, float f, int n, CookingRecipeSerializer<?> cookingRecipeSerializer) {
        this.result = iItemProvider.asItem();
        this.ingredient = ingredient;
        this.experience = f;
        this.cookingTime = n;
        this.recipeSerializer = cookingRecipeSerializer;
    }

    public static CookingRecipeBuilder cookingRecipe(Ingredient ingredient, IItemProvider iItemProvider, float f, int n, CookingRecipeSerializer<?> cookingRecipeSerializer) {
        return new CookingRecipeBuilder(iItemProvider, ingredient, f, n, cookingRecipeSerializer);
    }

    public static CookingRecipeBuilder blastingRecipe(Ingredient ingredient, IItemProvider iItemProvider, float f, int n) {
        return CookingRecipeBuilder.cookingRecipe(ingredient, iItemProvider, f, n, IRecipeSerializer.BLASTING);
    }

    public static CookingRecipeBuilder smeltingRecipe(Ingredient ingredient, IItemProvider iItemProvider, float f, int n) {
        return CookingRecipeBuilder.cookingRecipe(ingredient, iItemProvider, f, n, IRecipeSerializer.SMELTING);
    }

    public CookingRecipeBuilder addCriterion(String string, ICriterionInstance iCriterionInstance) {
        this.advancementBuilder.withCriterion(string, iCriterionInstance);
        return this;
    }

    public void build(Consumer<IFinishedRecipe> consumer) {
        this.build(consumer, Registry.ITEM.getKey(this.result));
    }

    public void build(Consumer<IFinishedRecipe> consumer, String string) {
        ResourceLocation resourceLocation = new ResourceLocation(string);
        ResourceLocation resourceLocation2 = Registry.ITEM.getKey(this.result);
        if (resourceLocation.equals(resourceLocation2)) {
            throw new IllegalStateException("Recipe " + resourceLocation + " should remove its 'save' argument");
        }
        this.build(consumer, resourceLocation);
    }

    public void build(Consumer<IFinishedRecipe> consumer, ResourceLocation resourceLocation) {
        this.validate(resourceLocation);
        this.advancementBuilder.withParentId(new ResourceLocation("recipes/root")).withCriterion("has_the_recipe", RecipeUnlockedTrigger.create(resourceLocation)).withRewards(AdvancementRewards.Builder.recipe(resourceLocation)).withRequirementsStrategy(IRequirementsStrategy.OR);
        consumer.accept(new Result(resourceLocation, this.group == null ? "" : this.group, this.ingredient, this.result, this.experience, this.cookingTime, this.advancementBuilder, new ResourceLocation(resourceLocation.getNamespace(), "recipes/" + this.result.getGroup().getPath() + "/" + resourceLocation.getPath()), this.recipeSerializer));
    }

    private void validate(ResourceLocation resourceLocation) {
        if (this.advancementBuilder.getCriteria().isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + resourceLocation);
        }
    }

    public static class Result
    implements IFinishedRecipe {
        private final ResourceLocation id;
        private final String group;
        private final Ingredient ingredient;
        private final Item result;
        private final float experience;
        private final int cookingTime;
        private final Advancement.Builder advancementBuilder;
        private final ResourceLocation advancementId;
        private final IRecipeSerializer<? extends AbstractCookingRecipe> serializer;

        public Result(ResourceLocation resourceLocation, String string, Ingredient ingredient, Item item, float f, int n, Advancement.Builder builder, ResourceLocation resourceLocation2, IRecipeSerializer<? extends AbstractCookingRecipe> iRecipeSerializer) {
            this.id = resourceLocation;
            this.group = string;
            this.ingredient = ingredient;
            this.result = item;
            this.experience = f;
            this.cookingTime = n;
            this.advancementBuilder = builder;
            this.advancementId = resourceLocation2;
            this.serializer = iRecipeSerializer;
        }

        @Override
        public void serialize(JsonObject jsonObject) {
            if (!this.group.isEmpty()) {
                jsonObject.addProperty("group", this.group);
            }
            jsonObject.add("ingredient", this.ingredient.serialize());
            jsonObject.addProperty("result", Registry.ITEM.getKey(this.result).toString());
            jsonObject.addProperty("experience", Float.valueOf(this.experience));
            jsonObject.addProperty("cookingtime", this.cookingTime);
        }

        @Override
        public IRecipeSerializer<?> getSerializer() {
            return this.serializer;
        }

        @Override
        public ResourceLocation getID() {
            return this.id;
        }

        @Override
        @Nullable
        public JsonObject getAdvancementJson() {
            return this.advancementBuilder.serialize();
        }

        @Override
        @Nullable
        public ResourceLocation getAdvancementID() {
            return this.advancementId;
        }
    }
}

