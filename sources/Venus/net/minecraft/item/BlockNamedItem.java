/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.item;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;

public class BlockNamedItem
extends BlockItem {
    public BlockNamedItem(Block block, Item.Properties properties) {
        super(block, properties);
    }

    @Override
    public String getTranslationKey() {
        return this.getDefaultTranslationKey();
    }
}

