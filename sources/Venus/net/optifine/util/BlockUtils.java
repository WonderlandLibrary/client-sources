/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.util;

import com.google.common.collect.ImmutableList;
import it.unimi.dsi.fastutil.longs.Long2ByteLinkedOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ByteLinkedOpenHashMap;
import java.util.Collection;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.Property;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.IBlockReader;
import net.optifine.render.RenderEnv;

public class BlockUtils {
    private static final ThreadLocal<RenderSideCacheKey> threadLocalKey = ThreadLocal.withInitial(BlockUtils::lambda$static$0);
    private static final ThreadLocal<Object2ByteLinkedOpenHashMap<RenderSideCacheKey>> threadLocalMap = ThreadLocal.withInitial(BlockUtils::lambda$static$1);

    public static boolean shouldSideBeRendered(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, Direction direction, RenderEnv renderEnv) {
        BlockPos blockPos2 = blockPos.offset(direction);
        BlockState blockState2 = iBlockReader.getBlockState(blockPos2);
        if (blockState2.isCacheOpaqueCube()) {
            return true;
        }
        if (blockState.isSideInvisible(blockState2, direction)) {
            return true;
        }
        return blockState2.isSolid() ? BlockUtils.shouldSideBeRenderedCached(blockState, iBlockReader, blockPos, direction, renderEnv, blockState2, blockPos2) : true;
    }

    public static boolean shouldSideBeRenderedCached(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, Direction direction, RenderEnv renderEnv, BlockState blockState2, BlockPos blockPos2) {
        long l = (long)blockState.getBlockStateId() << 36 | (long)blockState2.getBlockStateId() << 4 | (long)direction.ordinal();
        Long2ByteLinkedOpenHashMap long2ByteLinkedOpenHashMap = renderEnv.getRenderSideMap();
        byte by = long2ByteLinkedOpenHashMap.getAndMoveToFirst(l);
        if (by != 0) {
            return by > 0;
        }
        VoxelShape voxelShape = blockState.getFaceOcclusionShape(iBlockReader, blockPos, direction);
        VoxelShape voxelShape2 = blockState2.getFaceOcclusionShape(iBlockReader, blockPos2, direction.getOpposite());
        boolean bl = VoxelShapes.compare(voxelShape, voxelShape2, IBooleanFunction.ONLY_FIRST);
        if (long2ByteLinkedOpenHashMap.size() > 400) {
            long2ByteLinkedOpenHashMap.removeLastByte();
        }
        long2ByteLinkedOpenHashMap.putAndMoveToFirst(l, (byte)(bl ? 1 : -1));
        return bl;
    }

    public static int getBlockId(Block block) {
        return Registry.BLOCK.getId(block);
    }

    public static Block getBlock(ResourceLocation resourceLocation) {
        return !Registry.BLOCK.containsKey(resourceLocation) ? null : Registry.BLOCK.getOrDefault(resourceLocation);
    }

    public static int getMetadata(BlockState blockState) {
        Block block = blockState.getBlock();
        StateContainer<Block, BlockState> stateContainer = block.getStateContainer();
        ImmutableList<BlockState> immutableList = stateContainer.getValidStates();
        return immutableList.indexOf(blockState);
    }

    public static int getMetadataCount(Block block) {
        StateContainer<Block, BlockState> stateContainer = block.getStateContainer();
        ImmutableList<BlockState> immutableList = stateContainer.getValidStates();
        return immutableList.size();
    }

    public static BlockState getBlockState(Block block, int n) {
        StateContainer<Block, BlockState> stateContainer = block.getStateContainer();
        ImmutableList<BlockState> immutableList = stateContainer.getValidStates();
        return n >= 0 && n < immutableList.size() ? (BlockState)immutableList.get(n) : null;
    }

    public static List<BlockState> getBlockStates(Block block) {
        StateContainer<Block, BlockState> stateContainer = block.getStateContainer();
        ImmutableList<BlockState> immutableList = stateContainer.getValidStates();
        return immutableList;
    }

    public static boolean isFullCube(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos) {
        return blockState.isCacheOpaqueCollisionShape();
    }

    public static Collection<Property> getProperties(BlockState blockState) {
        return blockState.getProperties();
    }

    private static Object2ByteLinkedOpenHashMap lambda$static$1() {
        Object2ByteLinkedOpenHashMap<RenderSideCacheKey> object2ByteLinkedOpenHashMap = new Object2ByteLinkedOpenHashMap<RenderSideCacheKey>(200){

            @Override
            protected void rehash(int n) {
            }
        };
        object2ByteLinkedOpenHashMap.defaultReturnValue((byte)127);
        return object2ByteLinkedOpenHashMap;
    }

    private static RenderSideCacheKey lambda$static$0() {
        return new RenderSideCacheKey(null, null, null);
    }

    public static final class RenderSideCacheKey {
        private BlockState blockState1;
        private BlockState blockState2;
        private Direction facing;
        private int hashCode;

        private RenderSideCacheKey(BlockState blockState, BlockState blockState2, Direction direction) {
            this.blockState1 = blockState;
            this.blockState2 = blockState2;
            this.facing = direction;
        }

        private void init(BlockState blockState, BlockState blockState2, Direction direction) {
            this.blockState1 = blockState;
            this.blockState2 = blockState2;
            this.facing = direction;
            this.hashCode = 0;
        }

        public RenderSideCacheKey duplicate() {
            return new RenderSideCacheKey(this.blockState1, this.blockState2, this.facing);
        }

        public boolean equals(Object object) {
            if (this == object) {
                return false;
            }
            if (!(object instanceof RenderSideCacheKey)) {
                return true;
            }
            RenderSideCacheKey renderSideCacheKey = (RenderSideCacheKey)object;
            return this.blockState1 == renderSideCacheKey.blockState1 && this.blockState2 == renderSideCacheKey.blockState2 && this.facing == renderSideCacheKey.facing;
        }

        public int hashCode() {
            if (this.hashCode == 0) {
                this.hashCode = 31 * this.hashCode + this.blockState1.hashCode();
                this.hashCode = 31 * this.hashCode + this.blockState2.hashCode();
                this.hashCode = 31 * this.hashCode + this.facing.hashCode();
            }
            return this.hashCode;
        }
    }
}

