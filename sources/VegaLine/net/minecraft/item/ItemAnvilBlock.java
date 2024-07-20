/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.item;

import net.minecraft.block.Block;
import net.minecraft.item.ItemMultiTexture;

public class ItemAnvilBlock
extends ItemMultiTexture {
    public ItemAnvilBlock(Block block) {
        super(block, block, new String[]{"intact", "slightlyDamaged", "veryDamaged"});
    }

    @Override
    public int getMetadata(int damage) {
        return damage << 2;
    }
}

