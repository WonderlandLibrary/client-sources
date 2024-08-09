/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world;

import java.util.Optional;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.fluid.FluidState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.ExplosionContext;
import net.minecraft.world.IBlockReader;

public class EntityExplosionContext
extends ExplosionContext {
    private final Entity entity;

    public EntityExplosionContext(Entity entity2) {
        this.entity = entity2;
    }

    @Override
    public Optional<Float> getExplosionResistance(Explosion explosion, IBlockReader iBlockReader, BlockPos blockPos, BlockState blockState, FluidState fluidState) {
        return super.getExplosionResistance(explosion, iBlockReader, blockPos, blockState, fluidState).map(arg_0 -> this.lambda$getExplosionResistance$0(explosion, iBlockReader, blockPos, blockState, fluidState, arg_0));
    }

    @Override
    public boolean canExplosionDestroyBlock(Explosion explosion, IBlockReader iBlockReader, BlockPos blockPos, BlockState blockState, float f) {
        return this.entity.canExplosionDestroyBlock(explosion, iBlockReader, blockPos, blockState, f);
    }

    private Float lambda$getExplosionResistance$0(Explosion explosion, IBlockReader iBlockReader, BlockPos blockPos, BlockState blockState, FluidState fluidState, Float f) {
        return Float.valueOf(this.entity.getExplosionResistance(explosion, iBlockReader, blockPos, blockState, fluidState, f.floatValue()));
    }
}

