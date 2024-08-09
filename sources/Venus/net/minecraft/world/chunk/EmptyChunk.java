/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.chunk;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Util;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeContainer;
import net.minecraft.world.biome.BiomeRegistry;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.lighting.WorldLightManager;
import net.minecraft.world.server.ChunkHolder;

public class EmptyChunk
extends Chunk {
    private static final Biome[] BIOMES = Util.make(new Biome[BiomeContainer.BIOMES_SIZE], EmptyChunk::lambda$static$0);

    public EmptyChunk(World world, ChunkPos chunkPos) {
        super(world, chunkPos, new BiomeContainer(world.func_241828_r().getRegistry(Registry.BIOME_KEY), BIOMES));
    }

    @Override
    public BlockState getBlockState(BlockPos blockPos) {
        return Blocks.VOID_AIR.getDefaultState();
    }

    @Override
    @Nullable
    public BlockState setBlockState(BlockPos blockPos, BlockState blockState, boolean bl) {
        return null;
    }

    @Override
    public FluidState getFluidState(BlockPos blockPos) {
        return Fluids.EMPTY.getDefaultState();
    }

    @Override
    @Nullable
    public WorldLightManager getWorldLightManager() {
        return null;
    }

    @Override
    public int getLightValue(BlockPos blockPos) {
        return 1;
    }

    @Override
    public void addEntity(Entity entity2) {
    }

    @Override
    public void removeEntity(Entity entity2) {
    }

    @Override
    public void removeEntityAtIndex(Entity entity2, int n) {
    }

    @Override
    @Nullable
    public TileEntity getTileEntity(BlockPos blockPos, Chunk.CreateEntityType createEntityType) {
        return null;
    }

    @Override
    public void addTileEntity(TileEntity tileEntity) {
    }

    @Override
    public void addTileEntity(BlockPos blockPos, TileEntity tileEntity) {
    }

    @Override
    public void removeTileEntity(BlockPos blockPos) {
    }

    @Override
    public void markDirty() {
    }

    @Override
    public void getEntitiesWithinAABBForEntity(@Nullable Entity entity2, AxisAlignedBB axisAlignedBB, List<Entity> list, Predicate<? super Entity> predicate) {
    }

    @Override
    public <T extends Entity> void getEntitiesOfTypeWithinAABB(Class<? extends T> clazz, AxisAlignedBB axisAlignedBB, List<T> list, Predicate<? super T> predicate) {
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean isEmptyBetween(int n, int n2) {
        return false;
    }

    @Override
    public ChunkHolder.LocationType getLocationType() {
        return ChunkHolder.LocationType.BORDER;
    }

    private static void lambda$static$0(Biome[] biomeArray) {
        Arrays.fill(biomeArray, BiomeRegistry.PLAINS);
    }
}

