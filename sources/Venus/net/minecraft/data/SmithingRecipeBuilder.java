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
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public class SmithingRecipeBuilder {
    private final Ingredient base;
    private final Ingredient addition;
    private final Item output;
    private final Advancement.Builder advancementBuilder = Advancement.Builder.builder();
    private final IRecipeSerializer<?> serializer;

    public SmithingRecipeBuilder(IRecipeSerializer<?> iRecipeSerializer, Ingredient ingredient, Ingredient ingredient2, Item item) {
        this.serializer = iRecipeSerializer;
        this.base = ingredient;
        this.addition = ingredient2;
        this.output = item;
    }

    public static SmithingRecipeBuilder smithingRecipe(Ingredient ingredient, Ingredient ingredient2, Item item) {
        return new SmithingRecipeBuilder(IRecipeSerializer.SMITHING, ingredient, ingredient2, item);
    }

    public SmithingRecipeBuilder addCriterion(String string, ICriterionInstance iCriterionInstance) {
        this.advancementBuilder.withCriterion(string, iCriterionInstance);
        return this;
    }

    public void build(Consumer<IFinishedRecipe> consumer, String string) {
        this.build(consumer, new ResourceLocation(string));
    }

    public void build(Consumer<IFinishedRecipe> consumer, ResourceLocation resourceLocation) {
        this.validate(resourceLocation);
        this.advancementBuilder.withParentId(new ResourceLocation("recipes/root")).withCriterion("has_the_recipe", RecipeUnlockedTrigger.create(resourceLocation)).withRewards(AdvancementRewards.Builder.recipe(resourceLocation)).withRequirementsStrategy(IRequirementsStrategy.OR);
        consumer.accept(new Result(resourceLocation, this.serializer, this.base, this.addition, this.output, this.advancementBuilder, new ResourceLocation(resourceLocation.getNamespace(), "recipes/" + this.output.getGroup().getPath() + "/" + resourceLocation.getPath())));
    }

    private void validate(ResourceLocation resourceLocation) {
        if (this.advancementBuilder.getCriteria().isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + resourceLocation);
        }
    }

    public static class Result
    implements IFinishedRecipe {
        private final ResourceLocation id;
        private final Ingredient base;
        private final Ingredient addition;
        private final Item output;
        private final Advancement.Builder advancementBuilder;
        private final ResourceLocation advancementId;
        private final IRecipeSerializer<?> serializer;

        public Result(ResourceLocation resourceLocation, IRecipeSerializer<?> iRecipeSerializer, Ingredient ingredient, Ingredient ingredient2, Item item, Advancement.Builder builder, ResourceLocation resourceLocation2) {
            this.id = resourceLocation;
            this.serializer = iRecipeSerializer;
            this.base = ingredient;
            this.addition = ingredient2;
            this.output = item;
            this.advancementBuilder = builder;
            this.advancementId = resourceLocation2;
        }

        @Override
        public void serialize(JsonObject jsonObject) {
            jsonObject.add("base", this.base.serialize());
            jsonObject.add("addition", this.addition.serialize());
            JsonObject jsonObject2 = new JsonObject();
            jsonObject2.addProperty("item", Registry.ITEM.getKey(this.output).toString());
            jsonObject.add("result", jsonObject2);
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

