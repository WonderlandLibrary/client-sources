/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.data;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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

public class ShapedRecipeBuilder {
    private static final Logger LOGGER = LogManager.getLogger();
    private final Item result;
    private final int count;
    private final List<String> pattern = Lists.newArrayList();
    private final Map<Character, Ingredient> key = Maps.newLinkedHashMap();
    private final Advancement.Builder advancementBuilder = Advancement.Builder.builder();
    private String group;

    public ShapedRecipeBuilder(IItemProvider iItemProvider, int n) {
        this.result = iItemProvider.asItem();
        this.count = n;
    }

    public static ShapedRecipeBuilder shapedRecipe(IItemProvider iItemProvider) {
        return ShapedRecipeBuilder.shapedRecipe(iItemProvider, 1);
    }

    public static ShapedRecipeBuilder shapedRecipe(IItemProvider iItemProvider, int n) {
        return new ShapedRecipeBuilder(iItemProvider, n);
    }

    public ShapedRecipeBuilder key(Character c, ITag<Item> iTag) {
        return this.key(c, Ingredient.fromTag(iTag));
    }

    public ShapedRecipeBuilder key(Character c, IItemProvider iItemProvider) {
        return this.key(c, Ingredient.fromItems(iItemProvider));
    }

    public ShapedRecipeBuilder key(Character c, Ingredient ingredient) {
        if (this.key.containsKey(c)) {
            throw new IllegalArgumentException("Symbol '" + c + "' is already defined!");
        }
        if (c.charValue() == ' ') {
            throw new IllegalArgumentException("Symbol ' ' (whitespace) is reserved and cannot be defined");
        }
        this.key.put(c, ingredient);
        return this;
    }

    public ShapedRecipeBuilder patternLine(String string) {
        if (!this.pattern.isEmpty() && string.length() != this.pattern.get(0).length()) {
            throw new IllegalArgumentException("Pattern must be the same width on every line!");
        }
        this.pattern.add(string);
        return this;
    }

    public ShapedRecipeBuilder addCriterion(String string, ICriterionInstance iCriterionInstance) {
        this.advancementBuilder.withCriterion(string, iCriterionInstance);
        return this;
    }

    public ShapedRecipeBuilder setGroup(String string) {
        this.group = string;
        return this;
    }

    public void build(Consumer<IFinishedRecipe> consumer) {
        this.build(consumer, Registry.ITEM.getKey(this.result));
    }

    public void build(Consumer<IFinishedRecipe> consumer, String string) {
        ResourceLocation resourceLocation = Registry.ITEM.getKey(this.result);
        if (new ResourceLocation(string).equals(resourceLocation)) {
            throw new IllegalStateException("Shaped Recipe " + string + " should remove its 'save' argument");
        }
        this.build(consumer, new ResourceLocation(string));
    }

    public void build(Consumer<IFinishedRecipe> consumer, ResourceLocation resourceLocation) {
        this.validate(resourceLocation);
        this.advancementBuilder.withParentId(new ResourceLocation("recipes/root")).withCriterion("has_the_recipe", RecipeUnlockedTrigger.create(resourceLocation)).withRewards(AdvancementRewards.Builder.recipe(resourceLocation)).withRequirementsStrategy(IRequirementsStrategy.OR);
        consumer.accept(new Result(this, resourceLocation, this.result, this.count, this.group == null ? "" : this.group, this.pattern, this.key, this.advancementBuilder, new ResourceLocation(resourceLocation.getNamespace(), "recipes/" + this.result.getGroup().getPath() + "/" + resourceLocation.getPath())));
    }

    private void validate(ResourceLocation resourceLocation) {
        if (this.pattern.isEmpty()) {
            throw new IllegalStateException("No pattern is defined for shaped recipe " + resourceLocation + "!");
        }
        HashSet<Character> hashSet = Sets.newHashSet(this.key.keySet());
        hashSet.remove(Character.valueOf(' '));
        for (String string : this.pattern) {
            for (int i = 0; i < string.length(); ++i) {
                char c = string.charAt(i);
                if (!this.key.containsKey(Character.valueOf(c)) && c != ' ') {
                    throw new IllegalStateException("Pattern in recipe " + resourceLocation + " uses undefined symbol '" + c + "'");
                }
                hashSet.remove(Character.valueOf(c));
            }
        }
        if (!hashSet.isEmpty()) {
            throw new IllegalStateException("Ingredients are defined but not used in pattern for recipe " + resourceLocation);
        }
        if (this.pattern.size() == 1 && this.pattern.get(0).length() == 1) {
            throw new IllegalStateException("Shaped recipe " + resourceLocation + " only takes in a single item - should it be a shapeless recipe instead?");
        }
        if (this.advancementBuilder.getCriteria().isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + resourceLocation);
        }
    }

    class Result
    implements IFinishedRecipe {
        private final ResourceLocation id;
        private final Item result;
        private final int count;
        private final String group;
        private final List<String> pattern;
        private final Map<Character, Ingredient> key;
        private final Advancement.Builder advancementBuilder;
        private final ResourceLocation advancementId;
        final ShapedRecipeBuilder this$0;

        public Result(ShapedRecipeBuilder shapedRecipeBuilder, ResourceLocation resourceLocation, Item item, int n, String string, List<String> list, Map<Character, Ingredient> map, Advancement.Builder builder, ResourceLocation resourceLocation2) {
            this.this$0 = shapedRecipeBuilder;
            this.id = resourceLocation;
            this.result = item;
            this.count = n;
            this.group = string;
            this.pattern = list;
            this.key = map;
            this.advancementBuilder = builder;
            this.advancementId = resourceLocation2;
        }

        @Override
        public void serialize(JsonObject jsonObject) {
            if (!this.group.isEmpty()) {
                jsonObject.addProperty("group", this.group);
            }
            JsonArray jsonArray = new JsonArray();
            for (String object2 : this.pattern) {
                jsonArray.add(object2);
            }
            jsonObject.add("pattern", jsonArray);
            JsonObject jsonObject2 = new JsonObject();
            for (Map.Entry<Character, Ingredient> entry : this.key.entrySet()) {
                jsonObject2.add(String.valueOf(entry.getKey()), entry.getValue().serialize());
            }
            jsonObject.add("key", jsonObject2);
            JsonObject jsonObject3 = new JsonObject();
            jsonObject3.addProperty("item", Registry.ITEM.getKey(this.result).toString());
            if (this.count > 1) {
                jsonObject3.addProperty("count", this.count);
            }
            jsonObject.add("result", jsonObject3);
        }

        @Override
        public IRecipeSerializer<?> getSerializer() {
            return IRecipeSerializer.CRAFTING_SHAPED;
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

