/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.item;

import net.minecraft.block.BlockLeaves;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemLeaves
extends ItemBlock {
    private final BlockLeaves leaves;

    public ItemLeaves(BlockLeaves block) {
        super(block);
        this.leaves = block;
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }

    @Override
    public int getMetadata(int damage) {
        return damage | 4;
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return super.getUnlocalizedName() + "." + this.leaves.getWoodType(stack.getMetadata()).getUnlocalizedName();
    }
}

