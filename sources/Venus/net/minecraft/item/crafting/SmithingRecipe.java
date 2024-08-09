/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.item.crafting;

import com.google.gson.JsonObject;
import net.minecraft.block.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class SmithingRecipe
implements IRecipe<IInventory> {
    private final Ingredient base;
    private final Ingredient addition;
    private final ItemStack result;
    private final ResourceLocation recipeId;

    public SmithingRecipe(ResourceLocation resourceLocation, Ingredient ingredient, Ingredient ingredient2, ItemStack itemStack) {
        this.recipeId = resourceLocation;
        this.base = ingredient;
        this.addition = ingredient2;
        this.result = itemStack;
    }

    @Override
    public boolean matches(IInventory iInventory, World world) {
        return this.base.test(iInventory.getStackInSlot(0)) && this.addition.test(iInventory.getStackInSlot(1));
    }

    @Override
    public ItemStack getCraftingResult(IInventory iInventory) {
        ItemStack itemStack = this.result.copy();
        CompoundNBT compoundNBT = iInventory.getStackInSlot(0).getTag();
        if (compoundNBT != null) {
            itemStack.setTag(compoundNBT.copy());
        }
        return itemStack;
    }

    @Override
    public boolean canFit(int n, int n2) {
        return n * n2 >= 2;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return this.result;
    }

    public boolean isValidAdditionItem(ItemStack itemStack) {
        return this.addition.test(itemStack);
    }

    @Override
    public ItemStack getIcon() {
        return new ItemStack(Blocks.SMITHING_TABLE);
    }

    @Override
    public ResourceLocation getId() {
        return this.recipeId;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return IRecipeSerializer.SMITHING;
    }

    @Override
    public IRecipeType<?> getType() {
        return IRecipeType.SMITHING;
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Serializer
    implements IRecipeSerializer<SmithingRecipe> {
        @Override
        public SmithingRecipe read(ResourceLocation resourceLocation, JsonObject jsonObject) {
            Ingredient ingredient = Ingredient.deserialize(JSONUtils.getJsonObject(jsonObject, "base"));
            Ingredient ingredient2 = Ingredient.deserialize(JSONUtils.getJsonObject(jsonObject, "addition"));
            ItemStack itemStack = ShapedRecipe.deserializeItem(JSONUtils.getJsonObject(jsonObject, "result"));
            return new SmithingRecipe(resourceLocation, ingredient, ingredient2, itemStack);
        }

        @Override
        public SmithingRecipe read(ResourceLocation resourceLocation, PacketBuffer packetBuffer) {
            Ingredient ingredient = Ingredient.read(packetBuffer);
            Ingredient ingredient2 = Ingredient.read(packetBuffer);
            ItemStack itemStack = packetBuffer.readItemStack();
            return new SmithingRecipe(resourceLocation, ingredient, ingredient2, itemStack);
        }

        @Override
        public void write(PacketBuffer packetBuffer, SmithingRecipe smithingRecipe) {
            smithingRecipe.base.write(packetBuffer);
            smithingRecipe.addition.write(packetBuffer);
            packetBuffer.writeItemStack(smithingRecipe.result);
        }

        @Override
        public void write(PacketBuffer packetBuffer, IRecipe iRecipe) {
            this.write(packetBuffer, (SmithingRecipe)iRecipe);
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

