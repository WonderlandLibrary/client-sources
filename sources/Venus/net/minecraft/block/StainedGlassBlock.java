/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.AbstractGlassBlock;
import net.minecraft.block.IBeaconBeamColorProvider;
import net.minecraft.item.DyeColor;

public class StainedGlassBlock
extends AbstractGlassBlock
implements IBeaconBeamColorProvider {
    private final DyeColor color;

    public StainedGlassBlock(DyeColor dyeColor, AbstractBlock.Properties properties) {
        super(properties);
        this.color = dyeColor;
    }

    @Override
    public DyeColor getColor() {
        return this.color;
    }
}

