/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.ContainerBlock;
import net.minecraft.block.SkullBlock;
import net.minecraft.enchantment.IArmorVanishable;
import net.minecraft.pathfinding.PathType;
import net.minecraft.tileentity.SkullTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public abstract class AbstractSkullBlock
extends ContainerBlock
implements IArmorVanishable {
    private final SkullBlock.ISkullType skullType;

    public AbstractSkullBlock(SkullBlock.ISkullType iSkullType, AbstractBlock.Properties properties) {
        super(properties);
        this.skullType = iSkullType;
    }

    @Override
    public TileEntity createNewTileEntity(IBlockReader iBlockReader) {
        return new SkullTileEntity();
    }

    public SkullBlock.ISkullType getSkullType() {
        return this.skullType;
    }

    @Override
    public boolean allowsMovement(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, PathType pathType) {
        return true;
    }
}

