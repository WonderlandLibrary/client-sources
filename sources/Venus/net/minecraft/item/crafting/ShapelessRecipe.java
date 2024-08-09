/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.item.crafting;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ICraftingRecipe;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.RecipeItemHelper;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class ShapelessRecipe
implements ICraftingRecipe {
    private final ResourceLocation id;
    private final String group;
    private final ItemStack recipeOutput;
    private final NonNullList<Ingredient> recipeItems;

    public ShapelessRecipe(ResourceLocation resourceLocation, String string, ItemStack itemStack, NonNullList<Ingredient> nonNullList) {
        this.id = resourceLocation;
        this.group = string;
        this.recipeOutput = itemStack;
        this.recipeItems = nonNullList;
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return IRecipeSerializer.CRAFTING_SHAPELESS;
    }

    @Override
    public String getGroup() {
        return this.group;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return this.recipeOutput;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return this.recipeItems;
    }

    @Override
    public boolean matches(CraftingInventory craftingInventory, World world) {
        RecipeItemHelper recipeItemHelper = new RecipeItemHelper();
        int n = 0;
        for (int i = 0; i < craftingInventory.getSizeInventory(); ++i) {
            ItemStack itemStack = craftingInventory.getStackInSlot(i);
            if (itemStack.isEmpty()) continue;
            ++n;
            recipeItemHelper.func_221264_a(itemStack, 1);
        }
        return n == this.recipeItems.size() && recipeItemHelper.canCraft(this, null);
    }

    @Override
    public ItemStack getCraftingResult(CraftingInventory craftingInventory) {
        return this.recipeOutput.copy();
    }

    @Override
    public boolean canFit(int n, int n2) {
        return n * n2 >= this.recipeItems.size();
    }

    @Override
    public ItemStack getCraftingResult(IInventory iInventory) {
        return this.getCraftingResult((CraftingInventory)iInventory);
    }

    @Override
    public boolean matches(IInventory iInventory, World world) {
        return this.matches((CraftingInventory)iInventory, world);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Serializer
    implements IRecipeSerializer<ShapelessRecipe> {
        @Override
        public ShapelessRecipe read(ResourceLocation resourceLocation, JsonObject jsonObject) {
            String string = JSONUtils.getString(jsonObject, "group", "");
            NonNullList<Ingredient> nonNullList = Serializer.readIngredients(JSONUtils.getJsonArray(jsonObject, "ingredients"));
            if (nonNullList.isEmpty()) {
                throw new JsonParseException("No ingredients for shapeless recipe");
            }
            if (nonNullList.size() > 9) {
                throw new JsonParseException("Too many ingredients for shapeless recipe");
            }
            ItemStack itemStack = ShapedRecipe.deserializeItem(JSONUtils.getJsonObject(jsonObject, "result"));
            return new ShapelessRecipe(resourceLocation, string, itemStack, nonNullList);
        }

        private static NonNullList<Ingredient> readIngredients(JsonArray jsonArray) {
            NonNullList<Ingredient> nonNullList = NonNullList.create();
            for (int i = 0; i < jsonArray.size(); ++i) {
                Ingredient ingredient = Ingredient.deserialize(jsonArray.get(i));
                if (ingredient.hasNoMatchingItems()) continue;
                nonNullList.add(ingredient);
            }
            return nonNullList;
        }

        @Override
        public ShapelessRecipe read(ResourceLocation resourceLocation, PacketBuffer packetBuffer) {
            String string = packetBuffer.readString(Short.MAX_VALUE);
            int n = packetBuffer.readVarInt();
            NonNullList<Ingredient> nonNullList = NonNullList.withSize(n, Ingredient.EMPTY);
            for (int i = 0; i < nonNullList.size(); ++i) {
                nonNullList.set(i, Ingredient.read(packetBuffer));
            }
            ItemStack itemStack = packetBuffer.readItemStack();
            return new ShapelessRecipe(resourceLocation, string, itemStack, nonNullList);
        }

        @Override
        public void write(PacketBuffer packetBuffer, ShapelessRecipe shapelessRecipe) {
            packetBuffer.writeString(shapelessRecipe.group);
            packetBuffer.writeVarInt(shapelessRecipe.recipeItems.size());
            for (Ingredient ingredient : shapelessRecipe.recipeItems) {
                ingredient.write(packetBuffer);
            }
            packetBuffer.writeItemStack(shapelessRecipe.recipeOutput);
        }

        @Override
        public void write(PacketBuffer packetBuffer, IRecipe iRecipe) {
            this.write(packetBuffer, (ShapelessRecipe)iRecipe);
        }

        @Override
        public IRecipe read(ResourceLocation resourceLocation, PacketBuffer packetBuffer) {
            return this.read(resourceLocation, packetBuffer);
        }

        @Override
        public IRecipe read(ResourceLocation resourceLocation, JsonObject jsonObject) {
            return this.read(resourceLocation, jsonObject);
        }
    }
}

