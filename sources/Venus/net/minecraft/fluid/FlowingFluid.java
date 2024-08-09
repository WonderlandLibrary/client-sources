/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.fluid;

import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.Object2ByteLinkedOpenHashMap;
import it.unimi.dsi.fastutil.shorts.Short2BooleanMap;
import it.unimi.dsi.fastutil.shorts.Short2BooleanOpenHashMap;
import it.unimi.dsi.fastutil.shorts.Short2ObjectMap;
import it.unimi.dsi.fastutil.shorts.Short2ObjectOpenHashMap;
import java.util.EnumMap;
import java.util.Map;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.ILiquidContainer;
import net.minecraft.block.material.Material;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public abstract class FlowingFluid
extends Fluid {
    public static final BooleanProperty FALLING = BlockStateProperties.FALLING;
    public static final IntegerProperty LEVEL_1_8 = BlockStateProperties.LEVEL_1_8;
    private static final ThreadLocal<Object2ByteLinkedOpenHashMap<Block.RenderSideCacheKey>> field_212756_e = ThreadLocal.withInitial(FlowingFluid::lambda$static$0);
    private final Map<FluidState, VoxelShape> field_215669_f = Maps.newIdentityHashMap();

    @Override
    protected void fillStateContainer(StateContainer.Builder<Fluid, FluidState> builder) {
        builder.add(FALLING);
    }

    @Override
    public Vector3d getFlow(IBlockReader iBlockReader, BlockPos blockPos, FluidState fluidState) {
        double d = 0.0;
        double d2 = 0.0;
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        for (Object object : Direction.Plane.HORIZONTAL) {
            mutable.setAndMove(blockPos, (Direction)object);
            Object object2 = iBlockReader.getFluidState(mutable);
            if (!this.isSameOrEmpty((FluidState)object2)) continue;
            float f = ((FluidState)object2).getHeight();
            float f2 = 0.0f;
            if (f == 0.0f) {
                Vector3i vector3i;
                FluidState fluidState2;
                if (!iBlockReader.getBlockState(mutable).getMaterial().blocksMovement() && this.isSameOrEmpty(fluidState2 = iBlockReader.getFluidState((BlockPos)(vector3i = mutable.down()))) && (f = fluidState2.getHeight()) > 0.0f) {
                    f2 = fluidState.getHeight() - (f - 0.8888889f);
                }
            } else if (f > 0.0f) {
                f2 = fluidState.getHeight() - f;
            }
            if (f2 == 0.0f) continue;
            d += (double)((float)((Direction)object).getXOffset() * f2);
            d2 += (double)((float)((Direction)object).getZOffset() * f2);
        }
        Object object = new Vector3d(d, 0.0, d2);
        if (fluidState.get(FALLING).booleanValue()) {
            for (Object object2 : Direction.Plane.HORIZONTAL) {
                mutable.setAndMove(blockPos, (Direction)object2);
                if (!this.causesDownwardCurrent(iBlockReader, mutable, (Direction)object2) && !this.causesDownwardCurrent(iBlockReader, (BlockPos)mutable.up(), (Direction)object2)) continue;
                object = ((Vector3d)object).normalize().add(0.0, -6.0, 0.0);
                break;
            }
        }
        return ((Vector3d)object).normalize();
    }

    private boolean isSameOrEmpty(FluidState fluidState) {
        return fluidState.isEmpty() || fluidState.getFluid().isEquivalentTo(this);
    }

    protected boolean causesDownwardCurrent(IBlockReader iBlockReader, BlockPos blockPos, Direction direction) {
        BlockState blockState = iBlockReader.getBlockState(blockPos);
        FluidState fluidState = iBlockReader.getFluidState(blockPos);
        if (fluidState.getFluid().isEquivalentTo(this)) {
            return true;
        }
        if (direction == Direction.UP) {
            return false;
        }
        return blockState.getMaterial() == Material.ICE ? false : blockState.isSolidSide(iBlockReader, blockPos, direction);
    }

    protected void flowAround(IWorld iWorld, BlockPos blockPos, FluidState fluidState) {
        if (!fluidState.isEmpty()) {
            BlockState blockState = iWorld.getBlockState(blockPos);
            BlockPos blockPos2 = blockPos.down();
            BlockState blockState2 = iWorld.getBlockState(blockPos2);
            FluidState fluidState2 = this.calculateCorrectFlowingState(iWorld, blockPos2, blockState2);
            if (this.canFlow(iWorld, blockPos, blockState, Direction.DOWN, blockPos2, blockState2, iWorld.getFluidState(blockPos2), fluidState2.getFluid())) {
                this.flowInto(iWorld, blockPos2, blockState2, Direction.DOWN, fluidState2);
                if (this.getNumHorizontallyAdjacentSources(iWorld, blockPos) >= 3) {
                    this.func_207937_a(iWorld, blockPos, fluidState, blockState);
                }
            } else if (fluidState.isSource() || !this.func_211759_a(iWorld, fluidState2.getFluid(), blockPos, blockState, blockPos2, blockState2)) {
                this.func_207937_a(iWorld, blockPos, fluidState, blockState);
            }
        }
    }

    private void func_207937_a(IWorld iWorld, BlockPos blockPos, FluidState fluidState, BlockState blockState) {
        int n = fluidState.getLevel() - this.getLevelDecreasePerBlock(iWorld);
        if (fluidState.get(FALLING).booleanValue()) {
            n = 7;
        }
        if (n > 0) {
            Map<Direction, FluidState> map = this.func_205572_b(iWorld, blockPos, blockState);
            for (Map.Entry<Direction, FluidState> entry : map.entrySet()) {
                BlockState blockState2;
                Direction direction = entry.getKey();
                FluidState fluidState2 = entry.getValue();
                BlockPos blockPos2 = blockPos.offset(direction);
                if (!this.canFlow(iWorld, blockPos, blockState, direction, blockPos2, blockState2 = iWorld.getBlockState(blockPos2), iWorld.getFluidState(blockPos2), fluidState2.getFluid())) continue;
                this.flowInto(iWorld, blockPos2, blockState2, direction, fluidState2);
            }
        }
    }

    protected FluidState calculateCorrectFlowingState(IWorldReader iWorldReader, BlockPos blockPos, BlockState blockState) {
        Object object;
        Object object2;
        int n = 0;
        int n2 = 0;
        Object object3 = Direction.Plane.HORIZONTAL.iterator();
        while (object3.hasNext()) {
            object2 = object3.next();
            object = blockPos.offset((Direction)object2);
            BlockState blockState2 = iWorldReader.getBlockState((BlockPos)object);
            FluidState fluidState = blockState2.getFluidState();
            if (!fluidState.getFluid().isEquivalentTo(this) || !this.doesSideHaveHoles((Direction)object2, iWorldReader, blockPos, blockState, (BlockPos)object, blockState2)) continue;
            if (fluidState.isSource()) {
                ++n2;
            }
            n = Math.max(n, fluidState.getLevel());
        }
        if (this.canSourcesMultiply() && n2 >= 2) {
            object3 = iWorldReader.getBlockState(blockPos.down());
            object2 = ((AbstractBlock.AbstractBlockState)object3).getFluidState();
            if (((AbstractBlock.AbstractBlockState)object3).getMaterial().isSolid() || this.isSameAs((FluidState)object2)) {
                return this.getStillFluidState(true);
            }
        }
        if (!((FluidState)(object = ((AbstractBlock.AbstractBlockState)(object2 = iWorldReader.getBlockState((BlockPos)(object3 = blockPos.up())))).getFluidState())).isEmpty() && ((FluidState)object).getFluid().isEquivalentTo(this) && this.doesSideHaveHoles(Direction.UP, iWorldReader, blockPos, blockState, (BlockPos)object3, (BlockState)object2)) {
            return this.getFlowingFluidState(8, false);
        }
        int n3 = n - this.getLevelDecreasePerBlock(iWorldReader);
        return n3 <= 0 ? Fluids.EMPTY.getDefaultState() : this.getFlowingFluidState(n3, true);
    }

    private boolean doesSideHaveHoles(Direction direction, IBlockReader iBlockReader, BlockPos blockPos, BlockState blockState, BlockPos blockPos2, BlockState blockState2) {
        VoxelShape voxelShape;
        VoxelShape voxelShape2;
        boolean bl;
        Block.RenderSideCacheKey renderSideCacheKey;
        Object2ByteLinkedOpenHashMap<Block.RenderSideCacheKey> object2ByteLinkedOpenHashMap = !blockState.getBlock().isVariableOpacity() && !blockState2.getBlock().isVariableOpacity() ? field_212756_e.get() : null;
        if (object2ByteLinkedOpenHashMap != null) {
            renderSideCacheKey = new Block.RenderSideCacheKey(blockState, blockState2, direction);
            byte by = object2ByteLinkedOpenHashMap.getAndMoveToFirst(renderSideCacheKey);
            if (by != 127) {
                return by != 0;
            }
        } else {
            renderSideCacheKey = null;
        }
        boolean bl2 = bl = !VoxelShapes.doAdjacentCubeSidesFillSquare(voxelShape2 = blockState.getCollisionShape(iBlockReader, blockPos), voxelShape = blockState2.getCollisionShape(iBlockReader, blockPos2), direction);
        if (object2ByteLinkedOpenHashMap != null) {
            if (object2ByteLinkedOpenHashMap.size() == 200) {
                object2ByteLinkedOpenHashMap.removeLastByte();
            }
            object2ByteLinkedOpenHashMap.putAndMoveToFirst(renderSideCacheKey, (byte)(bl ? 1 : 0));
        }
        return bl;
    }

    public abstract Fluid getFlowingFluid();

    public FluidState getFlowingFluidState(int n, boolean bl) {
        return (FluidState)((FluidState)this.getFlowingFluid().getDefaultState().with(LEVEL_1_8, n)).with(FALLING, bl);
    }

    public abstract Fluid getStillFluid();

    public FluidState getStillFluidState(boolean bl) {
        return (FluidState)this.getStillFluid().getDefaultState().with(FALLING, bl);
    }

    protected abstract boolean canSourcesMultiply();

    protected void flowInto(IWorld iWorld, BlockPos blockPos, BlockState blockState, Direction direction, FluidState fluidState) {
        if (blockState.getBlock() instanceof ILiquidContainer) {
            ((ILiquidContainer)((Object)blockState.getBlock())).receiveFluid(iWorld, blockPos, blockState, fluidState);
        } else {
            if (!blockState.isAir()) {
                this.beforeReplacingBlock(iWorld, blockPos, blockState);
            }
            iWorld.setBlockState(blockPos, fluidState.getBlockState(), 3);
        }
    }

    protected abstract void beforeReplacingBlock(IWorld var1, BlockPos var2, BlockState var3);

    private static short func_212752_a(BlockPos blockPos, BlockPos blockPos2) {
        int n = blockPos2.getX() - blockPos.getX();
        int n2 = blockPos2.getZ() - blockPos.getZ();
        return (short)((n + 128 & 0xFF) << 8 | n2 + 128 & 0xFF);
    }

    protected int func_205571_a(IWorldReader iWorldReader, BlockPos blockPos, int n, Direction direction, BlockState blockState, BlockPos blockPos2, Short2ObjectMap<Pair<BlockState, FluidState>> short2ObjectMap, Short2BooleanMap short2BooleanMap) {
        int n2 = 1000;
        for (Direction direction2 : Direction.Plane.HORIZONTAL) {
            int n3;
            if (direction2 == direction) continue;
            BlockPos blockPos3 = blockPos.offset(direction2);
            short s = FlowingFluid.func_212752_a(blockPos2, blockPos3);
            Pair pair = short2ObjectMap.computeIfAbsent(s, arg_0 -> FlowingFluid.lambda$func_205571_a$1(iWorldReader, blockPos3, arg_0));
            BlockState blockState2 = (BlockState)pair.getFirst();
            FluidState fluidState = (FluidState)pair.getSecond();
            if (!this.func_211760_a(iWorldReader, this.getFlowingFluid(), blockPos, blockState, direction2, blockPos3, blockState2, fluidState)) continue;
            boolean bl = short2BooleanMap.computeIfAbsent(s, arg_0 -> this.lambda$func_205571_a$2(blockPos3, iWorldReader, blockState2, arg_0));
            if (bl) {
                return n;
            }
            if (n >= this.getSlopeFindDistance(iWorldReader) || (n3 = this.func_205571_a(iWorldReader, blockPos3, n + 1, direction2.getOpposite(), blockState2, blockPos2, short2ObjectMap, short2BooleanMap)) >= n2) continue;
            n2 = n3;
        }
        return n2;
    }

    private boolean func_211759_a(IBlockReader iBlockReader, Fluid fluid, BlockPos blockPos, BlockState blockState, BlockPos blockPos2, BlockState blockState2) {
        if (!this.doesSideHaveHoles(Direction.DOWN, iBlockReader, blockPos, blockState, blockPos2, blockState2)) {
            return true;
        }
        return blockState2.getFluidState().getFluid().isEquivalentTo(this) ? true : this.isBlocked(iBlockReader, blockPos2, blockState2, fluid);
    }

    private boolean func_211760_a(IBlockReader iBlockReader, Fluid fluid, BlockPos blockPos, BlockState blockState, Direction direction, BlockPos blockPos2, BlockState blockState2, FluidState fluidState) {
        return !this.isSameAs(fluidState) && this.doesSideHaveHoles(direction, iBlockReader, blockPos, blockState, blockPos2, blockState2) && this.isBlocked(iBlockReader, blockPos2, blockState2, fluid);
    }

    private boolean isSameAs(FluidState fluidState) {
        return fluidState.getFluid().isEquivalentTo(this) && fluidState.isSource();
    }

    protected abstract int getSlopeFindDistance(IWorldReader var1);

    private int getNumHorizontallyAdjacentSources(IWorldReader iWorldReader, BlockPos blockPos) {
        int n = 0;
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            BlockPos blockPos2 = blockPos.offset(direction);
            FluidState fluidState = iWorldReader.getFluidState(blockPos2);
            if (!this.isSameAs(fluidState)) continue;
            ++n;
        }
        return n;
    }

    protected Map<Direction, FluidState> func_205572_b(IWorldReader iWorldReader, BlockPos blockPos, BlockState blockState) {
        int n = 1000;
        EnumMap<Direction, FluidState> enumMap = Maps.newEnumMap(Direction.class);
        Short2ObjectOpenHashMap<Pair<BlockState, FluidState>> short2ObjectOpenHashMap = new Short2ObjectOpenHashMap<Pair<BlockState, FluidState>>();
        Short2BooleanOpenHashMap short2BooleanOpenHashMap = new Short2BooleanOpenHashMap();
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            BlockPos blockPos2 = blockPos.offset(direction);
            short s = FlowingFluid.func_212752_a(blockPos, blockPos2);
            Pair pair = short2ObjectOpenHashMap.computeIfAbsent(s, arg_0 -> FlowingFluid.lambda$func_205572_b$3(iWorldReader, blockPos2, arg_0));
            BlockState blockState2 = (BlockState)pair.getFirst();
            FluidState fluidState = (FluidState)pair.getSecond();
            FluidState fluidState2 = this.calculateCorrectFlowingState(iWorldReader, blockPos2, blockState2);
            if (!this.func_211760_a(iWorldReader, fluidState2.getFluid(), blockPos, blockState, direction, blockPos2, blockState2, fluidState)) continue;
            BlockPos blockPos3 = blockPos2.down();
            boolean bl = short2BooleanOpenHashMap.computeIfAbsent(s, arg_0 -> this.lambda$func_205572_b$4(iWorldReader, blockPos3, blockPos2, blockState2, arg_0));
            int n2 = bl ? 0 : this.func_205571_a(iWorldReader, blockPos2, 1, direction.getOpposite(), blockState2, blockPos, short2ObjectOpenHashMap, short2BooleanOpenHashMap);
            if (n2 < n) {
                enumMap.clear();
            }
            if (n2 > n) continue;
            enumMap.put(direction, fluidState2);
            n = n2;
        }
        return enumMap;
    }

    private boolean isBlocked(IBlockReader iBlockReader, BlockPos blockPos, BlockState blockState, Fluid fluid) {
        Block block = blockState.getBlock();
        if (block instanceof ILiquidContainer) {
            return ((ILiquidContainer)((Object)block)).canContainFluid(iBlockReader, blockPos, blockState, fluid);
        }
        if (!(block instanceof DoorBlock) && !block.isIn(BlockTags.SIGNS) && block != Blocks.LADDER && block != Blocks.SUGAR_CANE && block != Blocks.BUBBLE_COLUMN) {
            Material material = blockState.getMaterial();
            if (material != Material.PORTAL && material != Material.STRUCTURE_VOID && material != Material.OCEAN_PLANT && material != Material.SEA_GRASS) {
                return !material.blocksMovement();
            }
            return true;
        }
        return true;
    }

    protected boolean canFlow(IBlockReader iBlockReader, BlockPos blockPos, BlockState blockState, Direction direction, BlockPos blockPos2, BlockState blockState2, FluidState fluidState, Fluid fluid) {
        return fluidState.canDisplace(iBlockReader, blockPos2, fluid, direction) && this.doesSideHaveHoles(direction, iBlockReader, blockPos, blockState, blockPos2, blockState2) && this.isBlocked(iBlockReader, blockPos2, blockState2, fluid);
    }

    protected abstract int getLevelDecreasePerBlock(IWorldReader var1);

    protected int func_215667_a(World world, BlockPos blockPos, FluidState fluidState, FluidState fluidState2) {
        return this.getTickRate(world);
    }

    @Override
    public void tick(World world, BlockPos blockPos, FluidState fluidState) {
        if (!fluidState.isSource()) {
            FluidState fluidState2 = this.calculateCorrectFlowingState(world, blockPos, world.getBlockState(blockPos));
            int n = this.func_215667_a(world, blockPos, fluidState, fluidState2);
            if (fluidState2.isEmpty()) {
                fluidState = fluidState2;
                world.setBlockState(blockPos, Blocks.AIR.getDefaultState(), 0);
            } else if (!fluidState2.equals(fluidState)) {
                fluidState = fluidState2;
                BlockState blockState = fluidState2.getBlockState();
                world.setBlockState(blockPos, blockState, 1);
                world.getPendingFluidTicks().scheduleTick(blockPos, fluidState2.getFluid(), n);
                world.notifyNeighborsOfStateChange(blockPos, blockState.getBlock());
            }
        }
        this.flowAround(world, blockPos, fluidState);
    }

    protected static int getLevelFromState(FluidState fluidState) {
        return fluidState.isSource() ? 0 : 8 - Math.min(fluidState.getLevel(), 8) + (fluidState.get(FALLING) != false ? 8 : 0);
    }

    private static boolean isFullHeight(FluidState fluidState, IBlockReader iBlockReader, BlockPos blockPos) {
        return fluidState.getFluid().isEquivalentTo(iBlockReader.getFluidState(blockPos.up()).getFluid());
    }

    @Override
    public float getActualHeight(FluidState fluidState, IBlockReader iBlockReader, BlockPos blockPos) {
        return FlowingFluid.isFullHeight(fluidState, iBlockReader, blockPos) ? 1.0f : fluidState.getHeight();
    }

    @Override
    public float getHeight(FluidState fluidState) {
        return (float)fluidState.getLevel() / 9.0f;
    }

    @Override
    public VoxelShape func_215664_b(FluidState fluidState, IBlockReader iBlockReader, BlockPos blockPos) {
        return fluidState.getLevel() == 9 && FlowingFluid.isFullHeight(fluidState, iBlockReader, blockPos) ? VoxelShapes.fullCube() : this.field_215669_f.computeIfAbsent(fluidState, arg_0 -> FlowingFluid.lambda$func_215664_b$5(iBlockReader, blockPos, arg_0));
    }

    private static VoxelShape lambda$func_215664_b$5(IBlockReader iBlockReader, BlockPos blockPos, FluidState fluidState) {
        return VoxelShapes.create(0.0, 0.0, 0.0, 1.0, fluidState.getActualHeight(iBlockReader, blockPos), 1.0);
    }

    private boolean lambda$func_205572_b$4(IWorldReader iWorldReader, BlockPos blockPos, BlockPos blockPos2, BlockState blockState, int n) {
        BlockState blockState2 = iWorldReader.getBlockState(blockPos);
        return this.func_211759_a(iWorldReader, this.getFlowingFluid(), blockPos2, blockState, blockPos, blockState2);
    }

    private static Pair lambda$func_205572_b$3(IWorldReader iWorldReader, BlockPos blockPos, int n) {
        BlockState blockState = iWorldReader.getBlockState(blockPos);
        return Pair.of(blockState, blockState.getFluidState());
    }

    private boolean lambda$func_205571_a$2(BlockPos blockPos, IWorldReader iWorldReader, BlockState blockState, int n) {
        BlockPos blockPos2 = blockPos.down();
        BlockState blockState2 = iWorldReader.getBlockState(blockPos2);
        return this.func_211759_a(iWorldReader, this.getFlowingFluid(), blockPos, blockState, blockPos2, blockState2);
    }

    private static Pair lambda$func_205571_a$1(IWorldReader iWorldReader, BlockPos blockPos, int n) {
        BlockState blockState = iWorldReader.getBlockState(blockPos);
        return Pair.of(blockState, blockState.getFluidState());
    }

    private static Object2ByteLinkedOpenHashMap lambda$static$0() {
        Object2ByteLinkedOpenHashMap<Block.RenderSideCacheKey> object2ByteLinkedOpenHashMap = new Object2ByteLinkedOpenHashMap<Block.RenderSideCacheKey>(200){

            @Override
            protected void rehash(int n) {
            }
        };
        object2ByteLinkedOpenHashMap.defaultReturnValue((byte)127);
        return object2ByteLinkedOpenHashMap;
    }
}

