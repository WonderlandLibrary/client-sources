/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.data;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.util.List;
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
import net.minecraft.tags.ITag;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ShapelessRecipeBuilder {
    private static final Logger LOGGER = LogManager.getLogger();
    private final Item result;
    private final int count;
    private final List<Ingredient> ingredients = Lists.newArrayList();
    private final Advancement.Builder advancementBuilder = Advancement.Builder.builder();
    private String group;

    public ShapelessRecipeBuilder(IItemProvider iItemProvider, int n) {
        this.result = iItemProvider.asItem();
        this.count = n;
    }

    public static ShapelessRecipeBuilder shapelessRecipe(IItemProvider iItemProvider) {
        return new ShapelessRecipeBuilder(iItemProvider, 1);
    }

    public static ShapelessRecipeBuilder shapelessRecipe(IItemProvider iItemProvider, int n) {
        return new ShapelessRecipeBuilder(iItemProvider, n);
    }

    public ShapelessRecipeBuilder addIngredient(ITag<Item> iTag) {
        return this.addIngredient(Ingredient.fromTag(iTag));
    }

    public ShapelessRecipeBuilder addIngredient(IItemProvider iItemProvider) {
        return this.addIngredient(iItemProvider, 1);
    }

    public ShapelessRecipeBuilder addIngredient(IItemProvider iItemProvider, int n) {
        for (int i = 0; i < n; ++i) {
            this.addIngredient(Ingredient.fromItems(iItemProvider));
        }
        return this;
    }

    public ShapelessRecipeBuilder addIngredient(Ingredient ingredient) {
        return this.addIngredient(ingredient, 1);
    }

    public ShapelessRecipeBuilder addIngredient(Ingredient ingredient, int n) {
        for (int i = 0; i < n; ++i) {
            this.ingredients.add(ingredient);
        }
        return this;
    }

    public ShapelessRecipeBuilder addCriterion(String string, ICriterionInstance iCriterionInstance) {
        this.advancementBuilder.withCriterion(string, iCriterionInstance);
        return this;
    }

    public ShapelessRecipeBuilder setGroup(String string) {
        this.group = string;
        return this;
    }

    public void build(Consumer<IFinishedRecipe> consumer) {
        this.build(consumer, Registry.ITEM.getKey(this.result));
    }

    public void build(Consumer<IFinishedRecipe> consumer, String string) {
        ResourceLocation resourceLocation = Registry.ITEM.getKey(this.result);
        if (new ResourceLocation(string).equals(resourceLocation)) {
            throw new IllegalStateException("Shapeless Recipe " + string + " should remove its 'save' argument");
        }
        this.build(consumer, new ResourceLocation(string));
    }

    public void build(Consumer<IFinishedRecipe> consumer, ResourceLocation resourceLocation) {
        this.validate(resourceLocation);
        this.advancementBuilder.withParentId(new ResourceLocation("recipes/root")).withCriterion("has_the_recipe", RecipeUnlockedTrigger.create(resourceLocation)).withRewards(AdvancementRewards.Builder.recipe(resourceLocation)).withRequirementsStrategy(IRequirementsStrategy.OR);
        consumer.accept(new Result(resourceLocation, this.result, this.count, this.group == null ? "" : this.group, this.ingredients, this.advancementBuilder, new ResourceLocation(resourceLocation.getNamespace(), "recipes/" + this.result.getGroup().getPath() + "/" + resourceLocation.getPath())));
    }

    private void validate(ResourceLocation resourceLocation) {
        if (this.advancementBuilder.getCriteria().isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + resourceLocation);
        }
    }

    public static class Result
    implements IFinishedRecipe {
        private final ResourceLocation id;
        private final Item result;
        private final int count;
        private final String group;
        private final List<Ingredient> ingredients;
        private final Advancement.Builder advancementBuilder;
        private final ResourceLocation advancementId;

        public Result(ResourceLocation resourceLocation, Item item, int n, String string, List<Ingredient> list, Advancement.Builder builder, ResourceLocation resourceLocation2) {
            this.id = resourceLocation;
            this.result = item;
            this.count = n;
            this.group = string;
            this.ingredients = list;
            this.advancementBuilder = builder;
            this.advancementId = resourceLocation2;
        }

        @Override
        public void serialize(JsonObject jsonObject) {
            if (!this.group.isEmpty()) {
                jsonObject.addProperty("group", this.group);
            }
            JsonArray jsonArray = new JsonArray();
            for (Ingredient ingredient : this.ingredients) {
                jsonArray.add(ingredient.serialize());
            }
            jsonObject.add("ingredients", jsonArray);
            JsonObject jsonObject2 = new JsonObject();
            jsonObject2.addProperty("item", Registry.ITEM.getKey(this.result).toString());
            if (this.count > 1) {
                jsonObject2.addProperty("count", this.count);
            }
            jsonObject.add("result", jsonObject2);
        }

        @Override
        public IRecipeSerializer<?> getSerializer() {
            return IRecipeSerializer.CRAFTING_SHAPELESS;
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

