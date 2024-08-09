/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.item.crafting;

import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.FilledMapItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapData;
import net.minecraft.world.storage.MapDecoration;

public class MapExtendingRecipe
extends ShapedRecipe {
    public MapExtendingRecipe(ResourceLocation resourceLocation) {
        super(resourceLocation, "", 3, 3, NonNullList.from(Ingredient.EMPTY, Ingredient.fromItems(Items.PAPER), Ingredient.fromItems(Items.PAPER), Ingredient.fromItems(Items.PAPER), Ingredient.fromItems(Items.PAPER), Ingredient.fromItems(Items.FILLED_MAP), Ingredient.fromItems(Items.PAPER), Ingredient.fromItems(Items.PAPER), Ingredient.fromItems(Items.PAPER), Ingredient.fromItems(Items.PAPER)), new ItemStack(Items.MAP));
    }

    @Override
    public boolean matches(CraftingInventory craftingInventory, World world) {
        if (!super.matches(craftingInventory, world)) {
            return true;
        }
        ItemStack itemStack = ItemStack.EMPTY;
        for (int i = 0; i < craftingInventory.getSizeInventory() && itemStack.isEmpty(); ++i) {
            ItemStack itemStack2 = craftingInventory.getStackInSlot(i);
            if (itemStack2.getItem() != Items.FILLED_MAP) continue;
            itemStack = itemStack2;
        }
        if (itemStack.isEmpty()) {
            return true;
        }
        MapData mapData = FilledMapItem.getMapData(itemStack, world);
        if (mapData == null) {
            return true;
        }
        if (this.isExplorationMap(mapData)) {
            return true;
        }
        return mapData.scale < 4;
    }

    private boolean isExplorationMap(MapData mapData) {
        if (mapData.mapDecorations != null) {
            for (MapDecoration mapDecoration : mapData.mapDecorations.values()) {
                if (mapDecoration.getType() != MapDecoration.Type.MANSION && mapDecoration.getType() != MapDecoration.Type.MONUMENT) continue;
                return false;
            }
        }
        return true;
    }

    @Override
    public ItemStack getCraftingResult(CraftingInventory craftingInventory) {
        ItemStack itemStack = ItemStack.EMPTY;
        for (int i = 0; i < craftingInventory.getSizeInventory() && itemStack.isEmpty(); ++i) {
            ItemStack itemStack2 = craftingInventory.getStackInSlot(i);
            if (itemStack2.getItem() != Items.FILLED_MAP) continue;
            itemStack = itemStack2;
        }
        itemStack = itemStack.copy();
        itemStack.setCount(1);
        itemStack.getOrCreateTag().putInt("map_scale_direction", 1);
        return itemStack;
    }

    @Override
    public boolean isDynamic() {
        return false;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return IRecipeSerializer.CRAFTING_SPECIAL_MAPEXTENDING;
    }

    @Override
    public ItemStack getCraftingResult(IInventory iInventory) {
        return this.getCraftingResult((CraftingInventory)iInventory);
    }

    @Override
    public boolean matches(IInventory iInventory, World world) {
        return this.matches((CraftingInventory)iInventory, world);
    }
}

