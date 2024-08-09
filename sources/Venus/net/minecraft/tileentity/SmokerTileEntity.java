/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.tileentity;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.SmokerContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.tileentity.AbstractFurnaceTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class SmokerTileEntity
extends AbstractFurnaceTileEntity {
    public SmokerTileEntity() {
        super(TileEntityType.SMOKER, IRecipeType.SMOKING);
    }

    @Override
    protected ITextComponent getDefaultName() {
        return new TranslationTextComponent("container.smoker");
    }

    @Override
    protected int getBurnTime(ItemStack itemStack) {
        return super.getBurnTime(itemStack) / 2;
    }

    @Override
    protected Container createMenu(int n, PlayerInventory playerInventory) {
        return new SmokerContainer(n, playerInventory, this, this.furnaceData);
    }
}

