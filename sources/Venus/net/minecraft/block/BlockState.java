/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import java.util.concurrent.atomic.AtomicInteger;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.state.Property;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.extensions.IForgeBlockState;
import net.optifine.Config;
import net.optifine.util.BlockUtils;

public class BlockState
extends AbstractBlock.AbstractBlockState
implements IForgeBlockState {
    public static final Codec<BlockState> CODEC = BlockState.func_235897_a_(Registry.BLOCK, Block::getDefaultState).stable();
    private int blockId = -1;
    private int metadata = -1;
    private ResourceLocation blockLocation;
    private int blockStateId = -1;
    private static final AtomicInteger blockStateIdCounter = new AtomicInteger(0);

    public int getBlockId() {
        if (this.blockId < 0) {
            this.blockId = Registry.BLOCK.getId(this.getBlock());
        }
        return this.blockId;
    }

    public int getMetadata() {
        if (this.metadata < 0) {
            this.metadata = BlockUtils.getMetadata(this);
            if (this.metadata < 0) {
                Config.warn("Metadata not found, block: " + this.getBlockLocation());
                this.metadata = 0;
            }
        }
        return this.metadata;
    }

    public ResourceLocation getBlockLocation() {
        if (this.blockLocation == null) {
            this.blockLocation = Registry.BLOCK.getKey(this.getBlock());
        }
        return this.blockLocation;
    }

    public int getBlockStateId() {
        if (this.blockStateId < 0) {
            this.blockStateId = blockStateIdCounter.incrementAndGet();
        }
        return this.blockStateId;
    }

    public int getLightValue(IBlockReader iBlockReader, BlockPos blockPos) {
        return this.getLightValue();
    }

    public boolean isCacheOpaqueCube() {
        return this.cache != null && this.cache.opaqueCube;
    }

    public boolean isCacheOpaqueCollisionShape() {
        return this.cache != null && this.cache.opaqueCollisionShape;
    }

    public BlockState(Block block, ImmutableMap<Property<?>, Comparable<?>> immutableMap, MapCodec<BlockState> mapCodec) {
        super(block, immutableMap, mapCodec);
    }

    @Override
    protected BlockState getSelf() {
        return this;
    }
}

