/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BushBlock;
import net.minecraft.potion.Effect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;

public class FlowerBlock
extends BushBlock {
    protected static final VoxelShape SHAPE = Block.makeCuboidShape(5.0, 0.0, 5.0, 11.0, 10.0, 11.0);
    private final Effect stewEffect;
    private final int stewEffectDuration;

    public FlowerBlock(Effect effect, int n, AbstractBlock.Properties properties) {
        super(properties);
        this.stewEffect = effect;
        this.stewEffectDuration = effect.isInstant() ? n : n * 20;
    }

    @Override
    public VoxelShape getShape(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, ISelectionContext iSelectionContext) {
        Vector3d vector3d = blockState.getOffset(iBlockReader, blockPos);
        return SHAPE.withOffset(vector3d.x, vector3d.y, vector3d.z);
    }

    @Override
    public AbstractBlock.OffsetType getOffsetType() {
        return AbstractBlock.OffsetType.XZ;
    }

    public Effect getStewEffect() {
        return this.stewEffect;
    }

    public int getStewEffectDuration() {
        return this.stewEffectDuration;
    }
}

