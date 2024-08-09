/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.inventory.container;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.AbstractFurnaceContainer;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeBookCategory;
import net.minecraft.util.IIntArray;

public class FurnaceContainer
extends AbstractFurnaceContainer {
    public FurnaceContainer(int n, PlayerInventory playerInventory) {
        super(ContainerType.FURNACE, IRecipeType.SMELTING, RecipeBookCategory.FURNACE, n, playerInventory);
    }

    public FurnaceContainer(int n, PlayerInventory playerInventory, IInventory iInventory, IIntArray iIntArray) {
        super(ContainerType.FURNACE, IRecipeType.SMELTING, RecipeBookCategory.FURNACE, n, playerInventory, iInventory, iIntArray);
    }
}

