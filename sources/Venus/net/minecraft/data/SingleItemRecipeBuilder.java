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
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public class SingleItemRecipeBuilder {
    private final Item result;
    private final Ingredient ingredient;
    private final int count;
    private final Advancement.Builder advancementBuilder = Advancement.Builder.builder();
    private String group;
    private final IRecipeSerializer<?> serializer;

    public SingleItemRecipeBuilder(IRecipeSerializer<?> iRecipeSerializer, Ingredient ingredient, IItemProvider iItemProvider, int n) {
        this.serializer = iRecipeSerializer;
        this.result = iItemProvider.asItem();
        this.ingredient = ingredient;
        this.count = n;
    }

    public static SingleItemRecipeBuilder stonecuttingRecipe(Ingredient ingredient, IItemProvider iItemProvider) {
        return new SingleItemRecipeBuilder(IRecipeSerializer.STONECUTTING, ingredient, iItemProvider, 1);
    }

    public static SingleItemRecipeBuilder stonecuttingRecipe(Ingredient ingredient, IItemProvider iItemProvider, int n) {
        return new SingleItemRecipeBuilder(IRecipeSerializer.STONECUTTING, ingredient, iItemProvider, n);
    }

    public SingleItemRecipeBuilder addCriterion(String string, ICriterionInstance iCriterionInstance) {
        this.advancementBuilder.withCriterion(string, iCriterionInstance);
        return this;
    }

    public void build(Consumer<IFinishedRecipe> consumer, String string) {
        ResourceLocation resourceLocation = Registry.ITEM.getKey(this.result);
        if (new ResourceLocation(string).equals(resourceLocation)) {
            throw new IllegalStateException("Single Item Recipe " + string + " should remove its 'save' argument");
        }
        this.build(consumer, new ResourceLocation(string));
    }

    public void build(Consumer<IFinishedRecipe> consumer, ResourceLocation resourceLocation) {
        this.validate(resourceLocation);
        this.advancementBuilder.withParentId(new ResourceLocation("recipes/root")).withCriterion("has_the_recipe", RecipeUnlockedTrigger.create(resourceLocation)).withRewards(AdvancementRewards.Builder.recipe(resourceLocation)).withRequirementsStrategy(IRequirementsStrategy.OR);
        consumer.accept(new Result(resourceLocation, this.serializer, this.group == null ? "" : this.group, this.ingredient, this.result, this.count, this.advancementBuilder, new ResourceLocation(resourceLocation.getNamespace(), "recipes/" + this.result.getGroup().getPath() + "/" + resourceLocation.getPath())));
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
        private final int count;
        private final Advancement.Builder advancementBuilder;
        private final ResourceLocation advancementId;
        private final IRecipeSerializer<?> serializer;

        public Result(ResourceLocation resourceLocation, IRecipeSerializer<?> iRecipeSerializer, String string, Ingredient ingredient, Item item, int n, Advancement.Builder builder, ResourceLocation resourceLocation2) {
            this.id = resourceLocation;
            this.serializer = iRecipeSerializer;
            this.group = string;
            this.ingredient = ingredient;
            this.result = item;
            this.count = n;
            this.advancementBuilder = builder;
            this.advancementId = resourceLocation2;
        }

        @Override
        public void serialize(JsonObject jsonObject) {
            if (!this.group.isEmpty()) {
                jsonObject.addProperty("group", this.group);
            }
            jsonObject.add("ingredient", this.ingredient.serialize());
            jsonObject.addProperty("result", Registry.ITEM.getKey(this.result).toString());
            jsonObject.addProperty("count", this.count);
        }

        @Override
        public ResourceLocation getID() {
            return this.id;
        }

        @Override
        public IRecipeSerializer<?> getSerializer() {
            return this.serializer;
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

