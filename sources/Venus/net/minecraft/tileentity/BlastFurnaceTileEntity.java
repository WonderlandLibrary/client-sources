/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.tileentity;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.BlastFurnaceContainer;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.tileentity.AbstractFurnaceTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class BlastFurnaceTileEntity
extends AbstractFurnaceTileEntity {
    public BlastFurnaceTileEntity() {
        super(TileEntityType.BLAST_FURNACE, IRecipeType.BLASTING);
    }

    @Override
    protected ITextComponent getDefaultName() {
        return new TranslationTextComponent("container.blast_furnace");
    }

    @Override
    protected int getBurnTime(ItemStack itemStack) {
        return super.getBurnTime(itemStack) / 2;
    }

    @Override
    protected Container createMenu(int n, PlayerInventory playerInventory) {
        return new BlastFurnaceContainer(n, playerInventory, this, this.furnaceData);
    }
}

