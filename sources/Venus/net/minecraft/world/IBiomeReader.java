/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world;

import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.IEntityReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.IWorldGenerationReader;

public interface IBiomeReader
extends IEntityReader,
IWorldReader,
IWorldGenerationReader {
    @Override
    default public Stream<VoxelShape> func_230318_c_(@Nullable Entity entity2, AxisAlignedBB axisAlignedBB, Predicate<Entity> predicate) {
        return IEntityReader.super.func_230318_c_(entity2, axisAlignedBB, predicate);
    }

    @Override
    default public boolean checkNoEntityCollision(@Nullable Entity entity2, VoxelShape voxelShape) {
        return IEntityReader.super.checkNoEntityCollision(entity2, voxelShape);
    }

    @Override
    default public BlockPos getHeight(Heightmap.Type type, BlockPos blockPos) {
        return IWorldReader.super.getHeight(type, blockPos);
    }

    public DynamicRegistries func_241828_r();

    default public Optional<RegistryKey<Biome>> func_242406_i(BlockPos blockPos) {
        return this.func_241828_r().getRegistry(Registry.BIOME_KEY).getOptionalKey(this.getBiome(blockPos));
    }
}

