/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class BlockBone
extends BlockRotatedPillar {
    public BlockBone() {
        super(Material.ROCK, MapColor.SAND);
        this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
        this.setHardness(2.0f);
        this.setSoundType(SoundType.STONE);
    }
}

