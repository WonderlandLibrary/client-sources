/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature.structure;

import com.google.common.collect.ImmutableSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.fluid.FluidState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.DispenserTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import net.minecraft.world.gen.feature.structure.StructureManager;

public abstract class StructurePiece {
    protected static final BlockState CAVE_AIR = Blocks.CAVE_AIR.getDefaultState();
    protected MutableBoundingBox boundingBox;
    @Nullable
    private Direction coordBaseMode;
    private Mirror mirror;
    private Rotation rotation;
    protected int componentType;
    private final IStructurePieceType structurePieceType;
    private static final Set<Block> BLOCKS_NEEDING_POSTPROCESSING = ((ImmutableSet.Builder)((ImmutableSet.Builder)((ImmutableSet.Builder)((ImmutableSet.Builder)((ImmutableSet.Builder)((ImmutableSet.Builder)((ImmutableSet.Builder)((ImmutableSet.Builder)((ImmutableSet.Builder)((ImmutableSet.Builder)((ImmutableSet.Builder)ImmutableSet.builder().add(Blocks.NETHER_BRICK_FENCE)).add(Blocks.TORCH)).add(Blocks.WALL_TORCH)).add(Blocks.OAK_FENCE)).add(Blocks.SPRUCE_FENCE)).add(Blocks.DARK_OAK_FENCE)).add(Blocks.ACACIA_FENCE)).add(Blocks.BIRCH_FENCE)).add(Blocks.JUNGLE_FENCE)).add(Blocks.LADDER)).add(Blocks.IRON_BARS)).build();

    protected StructurePiece(IStructurePieceType iStructurePieceType, int n) {
        this.structurePieceType = iStructurePieceType;
        this.componentType = n;
    }

    public StructurePiece(IStructurePieceType iStructurePieceType, CompoundNBT compoundNBT) {
        this(iStructurePieceType, compoundNBT.getInt("GD"));
        int n;
        if (compoundNBT.contains("BB")) {
            this.boundingBox = new MutableBoundingBox(compoundNBT.getIntArray("BB"));
        }
        this.setCoordBaseMode((n = compoundNBT.getInt("O")) == -1 ? null : Direction.byHorizontalIndex(n));
    }

    public final CompoundNBT write() {
        CompoundNBT compoundNBT = new CompoundNBT();
        compoundNBT.putString("id", Registry.STRUCTURE_PIECE.getKey(this.getStructurePieceType()).toString());
        compoundNBT.put("BB", this.boundingBox.toNBTTagIntArray());
        Direction direction = this.getCoordBaseMode();
        compoundNBT.putInt("O", direction == null ? -1 : direction.getHorizontalIndex());
        compoundNBT.putInt("GD", this.componentType);
        this.readAdditional(compoundNBT);
        return compoundNBT;
    }

    protected abstract void readAdditional(CompoundNBT var1);

    public void buildComponent(StructurePiece structurePiece, List<StructurePiece> list, Random random2) {
    }

    public abstract boolean func_230383_a_(ISeedReader var1, StructureManager var2, ChunkGenerator var3, Random var4, MutableBoundingBox var5, ChunkPos var6, BlockPos var7);

    public MutableBoundingBox getBoundingBox() {
        return this.boundingBox;
    }

    public int getComponentType() {
        return this.componentType;
    }

    public boolean func_214810_a(ChunkPos chunkPos, int n) {
        int n2 = chunkPos.x << 4;
        int n3 = chunkPos.z << 4;
        return this.boundingBox.intersectsWith(n2 - n, n3 - n, n2 + 15 + n, n3 + 15 + n);
    }

    public static StructurePiece findIntersecting(List<StructurePiece> list, MutableBoundingBox mutableBoundingBox) {
        for (StructurePiece structurePiece : list) {
            if (structurePiece.getBoundingBox() == null || !structurePiece.getBoundingBox().intersectsWith(mutableBoundingBox)) continue;
            return structurePiece;
        }
        return null;
    }

    protected boolean isLiquidInStructureBoundingBox(IBlockReader iBlockReader, MutableBoundingBox mutableBoundingBox) {
        int n;
        int n2;
        int n3 = Math.max(this.boundingBox.minX - 1, mutableBoundingBox.minX);
        int n4 = Math.max(this.boundingBox.minY - 1, mutableBoundingBox.minY);
        int n5 = Math.max(this.boundingBox.minZ - 1, mutableBoundingBox.minZ);
        int n6 = Math.min(this.boundingBox.maxX + 1, mutableBoundingBox.maxX);
        int n7 = Math.min(this.boundingBox.maxY + 1, mutableBoundingBox.maxY);
        int n8 = Math.min(this.boundingBox.maxZ + 1, mutableBoundingBox.maxZ);
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        for (n2 = n3; n2 <= n6; ++n2) {
            for (n = n5; n <= n8; ++n) {
                if (iBlockReader.getBlockState(mutable.setPos(n2, n4, n)).getMaterial().isLiquid()) {
                    return false;
                }
                if (!iBlockReader.getBlockState(mutable.setPos(n2, n7, n)).getMaterial().isLiquid()) continue;
                return false;
            }
        }
        for (n2 = n3; n2 <= n6; ++n2) {
            for (n = n4; n <= n7; ++n) {
                if (iBlockReader.getBlockState(mutable.setPos(n2, n, n5)).getMaterial().isLiquid()) {
                    return false;
                }
                if (!iBlockReader.getBlockState(mutable.setPos(n2, n, n8)).getMaterial().isLiquid()) continue;
                return false;
            }
        }
        for (n2 = n5; n2 <= n8; ++n2) {
            for (n = n4; n <= n7; ++n) {
                if (iBlockReader.getBlockState(mutable.setPos(n3, n, n2)).getMaterial().isLiquid()) {
                    return false;
                }
                if (!iBlockReader.getBlockState(mutable.setPos(n6, n, n2)).getMaterial().isLiquid()) continue;
                return false;
            }
        }
        return true;
    }

    protected int getXWithOffset(int n, int n2) {
        Direction direction = this.getCoordBaseMode();
        if (direction == null) {
            return n;
        }
        switch (1.$SwitchMap$net$minecraft$util$Direction[direction.ordinal()]) {
            case 1: 
            case 2: {
                return this.boundingBox.minX + n;
            }
            case 3: {
                return this.boundingBox.maxX - n2;
            }
            case 4: {
                return this.boundingBox.minX + n2;
            }
        }
        return n;
    }

    protected int getYWithOffset(int n) {
        return this.getCoordBaseMode() == null ? n : n + this.boundingBox.minY;
    }

    protected int getZWithOffset(int n, int n2) {
        Direction direction = this.getCoordBaseMode();
        if (direction == null) {
            return n2;
        }
        switch (1.$SwitchMap$net$minecraft$util$Direction[direction.ordinal()]) {
            case 1: {
                return this.boundingBox.maxZ - n2;
            }
            case 2: {
                return this.boundingBox.minZ + n2;
            }
            case 3: 
            case 4: {
                return this.boundingBox.minZ + n;
            }
        }
        return n2;
    }

    protected void setBlockState(ISeedReader iSeedReader, BlockState blockState, int n, int n2, int n3, MutableBoundingBox mutableBoundingBox) {
        BlockPos blockPos = new BlockPos(this.getXWithOffset(n, n3), this.getYWithOffset(n2), this.getZWithOffset(n, n3));
        if (mutableBoundingBox.isVecInside(blockPos)) {
            if (this.mirror != Mirror.NONE) {
                blockState = blockState.mirror(this.mirror);
            }
            if (this.rotation != Rotation.NONE) {
                blockState = blockState.rotate(this.rotation);
            }
            iSeedReader.setBlockState(blockPos, blockState, 2);
            FluidState fluidState = iSeedReader.getFluidState(blockPos);
            if (!fluidState.isEmpty()) {
                iSeedReader.getPendingFluidTicks().scheduleTick(blockPos, fluidState.getFluid(), 0);
            }
            if (BLOCKS_NEEDING_POSTPROCESSING.contains(blockState.getBlock())) {
                iSeedReader.getChunk(blockPos).markBlockForPostprocessing(blockPos);
            }
        }
    }

    protected BlockState getBlockStateFromPos(IBlockReader iBlockReader, int n, int n2, int n3, MutableBoundingBox mutableBoundingBox) {
        int n4;
        int n5;
        int n6 = this.getXWithOffset(n, n3);
        BlockPos blockPos = new BlockPos(n6, n5 = this.getYWithOffset(n2), n4 = this.getZWithOffset(n, n3));
        return !mutableBoundingBox.isVecInside(blockPos) ? Blocks.AIR.getDefaultState() : iBlockReader.getBlockState(blockPos);
    }

    protected boolean getSkyBrightness(IWorldReader iWorldReader, int n, int n2, int n3, MutableBoundingBox mutableBoundingBox) {
        int n4;
        int n5;
        int n6 = this.getXWithOffset(n, n3);
        BlockPos blockPos = new BlockPos(n6, n5 = this.getYWithOffset(n2 + 1), n4 = this.getZWithOffset(n, n3));
        if (!mutableBoundingBox.isVecInside(blockPos)) {
            return true;
        }
        return n5 < iWorldReader.getHeight(Heightmap.Type.OCEAN_FLOOR_WG, n6, n4);
    }

    protected void fillWithAir(ISeedReader iSeedReader, MutableBoundingBox mutableBoundingBox, int n, int n2, int n3, int n4, int n5, int n6) {
        for (int i = n2; i <= n5; ++i) {
            for (int j = n; j <= n4; ++j) {
                for (int k = n3; k <= n6; ++k) {
                    this.setBlockState(iSeedReader, Blocks.AIR.getDefaultState(), j, i, k, mutableBoundingBox);
                }
            }
        }
    }

    protected void fillWithBlocks(ISeedReader iSeedReader, MutableBoundingBox mutableBoundingBox, int n, int n2, int n3, int n4, int n5, int n6, BlockState blockState, BlockState blockState2, boolean bl) {
        for (int i = n2; i <= n5; ++i) {
            for (int j = n; j <= n4; ++j) {
                for (int k = n3; k <= n6; ++k) {
                    if (bl && this.getBlockStateFromPos(iSeedReader, j, i, k, mutableBoundingBox).isAir()) continue;
                    if (i != n2 && i != n5 && j != n && j != n4 && k != n3 && k != n6) {
                        this.setBlockState(iSeedReader, blockState2, j, i, k, mutableBoundingBox);
                        continue;
                    }
                    this.setBlockState(iSeedReader, blockState, j, i, k, mutableBoundingBox);
                }
            }
        }
    }

    protected void fillWithRandomizedBlocks(ISeedReader iSeedReader, MutableBoundingBox mutableBoundingBox, int n, int n2, int n3, int n4, int n5, int n6, boolean bl, Random random2, BlockSelector blockSelector) {
        for (int i = n2; i <= n5; ++i) {
            for (int j = n; j <= n4; ++j) {
                for (int k = n3; k <= n6; ++k) {
                    if (bl && this.getBlockStateFromPos(iSeedReader, j, i, k, mutableBoundingBox).isAir()) continue;
                    blockSelector.selectBlocks(random2, j, i, k, i == n2 || i == n5 || j == n || j == n4 || k == n3 || k == n6);
                    this.setBlockState(iSeedReader, blockSelector.getBlockState(), j, i, k, mutableBoundingBox);
                }
            }
        }
    }

    protected void generateMaybeBox(ISeedReader iSeedReader, MutableBoundingBox mutableBoundingBox, Random random2, float f, int n, int n2, int n3, int n4, int n5, int n6, BlockState blockState, BlockState blockState2, boolean bl, boolean bl2) {
        for (int i = n2; i <= n5; ++i) {
            for (int j = n; j <= n4; ++j) {
                for (int k = n3; k <= n6; ++k) {
                    if (random2.nextFloat() > f || bl && this.getBlockStateFromPos(iSeedReader, j, i, k, mutableBoundingBox).isAir() || bl2 && !this.getSkyBrightness(iSeedReader, j, i, k, mutableBoundingBox)) continue;
                    if (i != n2 && i != n5 && j != n && j != n4 && k != n3 && k != n6) {
                        this.setBlockState(iSeedReader, blockState2, j, i, k, mutableBoundingBox);
                        continue;
                    }
                    this.setBlockState(iSeedReader, blockState, j, i, k, mutableBoundingBox);
                }
            }
        }
    }

    protected void randomlyPlaceBlock(ISeedReader iSeedReader, MutableBoundingBox mutableBoundingBox, Random random2, float f, int n, int n2, int n3, BlockState blockState) {
        if (random2.nextFloat() < f) {
            this.setBlockState(iSeedReader, blockState, n, n2, n3, mutableBoundingBox);
        }
    }

    protected void randomlyRareFillWithBlocks(ISeedReader iSeedReader, MutableBoundingBox mutableBoundingBox, int n, int n2, int n3, int n4, int n5, int n6, BlockState blockState, boolean bl) {
        float f = n4 - n + 1;
        float f2 = n5 - n2 + 1;
        float f3 = n6 - n3 + 1;
        float f4 = (float)n + f / 2.0f;
        float f5 = (float)n3 + f3 / 2.0f;
        for (int i = n2; i <= n5; ++i) {
            float f6 = (float)(i - n2) / f2;
            for (int j = n; j <= n4; ++j) {
                float f7 = ((float)j - f4) / (f * 0.5f);
                for (int k = n3; k <= n6; ++k) {
                    float f8;
                    float f9 = ((float)k - f5) / (f3 * 0.5f);
                    if (bl && this.getBlockStateFromPos(iSeedReader, j, i, k, mutableBoundingBox).isAir() || !((f8 = f7 * f7 + f6 * f6 + f9 * f9) <= 1.05f)) continue;
                    this.setBlockState(iSeedReader, blockState, j, i, k, mutableBoundingBox);
                }
            }
        }
    }

    protected void replaceAirAndLiquidDownwards(ISeedReader iSeedReader, BlockState blockState, int n, int n2, int n3, MutableBoundingBox mutableBoundingBox) {
        int n4;
        int n5;
        int n6 = this.getXWithOffset(n, n3);
        if (mutableBoundingBox.isVecInside(new BlockPos(n6, n5 = this.getYWithOffset(n2), n4 = this.getZWithOffset(n, n3)))) {
            while ((iSeedReader.isAirBlock(new BlockPos(n6, n5, n4)) || iSeedReader.getBlockState(new BlockPos(n6, n5, n4)).getMaterial().isLiquid()) && n5 > 1) {
                iSeedReader.setBlockState(new BlockPos(n6, n5, n4), blockState, 2);
                --n5;
            }
        }
    }

    protected boolean generateChest(ISeedReader iSeedReader, MutableBoundingBox mutableBoundingBox, Random random2, int n, int n2, int n3, ResourceLocation resourceLocation) {
        BlockPos blockPos = new BlockPos(this.getXWithOffset(n, n3), this.getYWithOffset(n2), this.getZWithOffset(n, n3));
        return this.generateChest(iSeedReader, mutableBoundingBox, random2, blockPos, resourceLocation, null);
    }

    public static BlockState correctFacing(IBlockReader iBlockReader, BlockPos blockPos, BlockState blockState) {
        Object object3;
        Object object2 = null;
        for (Object object3 : Direction.Plane.HORIZONTAL) {
            BlockPos blockPos2 = blockPos.offset((Direction)object3);
            BlockState blockState2 = iBlockReader.getBlockState(blockPos2);
            if (blockState2.isIn(Blocks.CHEST)) {
                return blockState;
            }
            if (!blockState2.isOpaqueCube(iBlockReader, blockPos2)) continue;
            if (object2 != null) {
                object2 = null;
                break;
            }
            object2 = object3;
        }
        if (object2 != null) {
            return (BlockState)blockState.with(HorizontalBlock.HORIZONTAL_FACING, ((Direction)object2).getOpposite());
        }
        Object object4 = blockState.get(HorizontalBlock.HORIZONTAL_FACING);
        object3 = blockPos.offset((Direction)object4);
        if (iBlockReader.getBlockState((BlockPos)object3).isOpaqueCube(iBlockReader, (BlockPos)object3)) {
            object4 = ((Direction)object4).getOpposite();
            object3 = blockPos.offset((Direction)object4);
        }
        if (iBlockReader.getBlockState((BlockPos)object3).isOpaqueCube(iBlockReader, (BlockPos)object3)) {
            object4 = ((Direction)object4).rotateY();
            object3 = blockPos.offset((Direction)object4);
        }
        if (iBlockReader.getBlockState((BlockPos)object3).isOpaqueCube(iBlockReader, (BlockPos)object3)) {
            object4 = ((Direction)object4).getOpposite();
            blockPos.offset((Direction)object4);
        }
        return (BlockState)blockState.with(HorizontalBlock.HORIZONTAL_FACING, object4);
    }

    protected boolean generateChest(IServerWorld iServerWorld, MutableBoundingBox mutableBoundingBox, Random random2, BlockPos blockPos, ResourceLocation resourceLocation, @Nullable BlockState blockState) {
        if (mutableBoundingBox.isVecInside(blockPos) && !iServerWorld.getBlockState(blockPos).isIn(Blocks.CHEST)) {
            if (blockState == null) {
                blockState = StructurePiece.correctFacing(iServerWorld, blockPos, Blocks.CHEST.getDefaultState());
            }
            iServerWorld.setBlockState(blockPos, blockState, 2);
            TileEntity tileEntity = iServerWorld.getTileEntity(blockPos);
            if (tileEntity instanceof ChestTileEntity) {
                ((ChestTileEntity)tileEntity).setLootTable(resourceLocation, random2.nextLong());
            }
            return false;
        }
        return true;
    }

    protected boolean createDispenser(ISeedReader iSeedReader, MutableBoundingBox mutableBoundingBox, Random random2, int n, int n2, int n3, Direction direction, ResourceLocation resourceLocation) {
        BlockPos blockPos = new BlockPos(this.getXWithOffset(n, n3), this.getYWithOffset(n2), this.getZWithOffset(n, n3));
        if (mutableBoundingBox.isVecInside(blockPos) && !iSeedReader.getBlockState(blockPos).isIn(Blocks.DISPENSER)) {
            this.setBlockState(iSeedReader, (BlockState)Blocks.DISPENSER.getDefaultState().with(DispenserBlock.FACING, direction), n, n2, n3, mutableBoundingBox);
            TileEntity tileEntity = iSeedReader.getTileEntity(blockPos);
            if (tileEntity instanceof DispenserTileEntity) {
                ((DispenserTileEntity)tileEntity).setLootTable(resourceLocation, random2.nextLong());
            }
            return false;
        }
        return true;
    }

    public void offset(int n, int n2, int n3) {
        this.boundingBox.offset(n, n2, n3);
    }

    @Nullable
    public Direction getCoordBaseMode() {
        return this.coordBaseMode;
    }

    public void setCoordBaseMode(@Nullable Direction direction) {
        this.coordBaseMode = direction;
        if (direction == null) {
            this.rotation = Rotation.NONE;
            this.mirror = Mirror.NONE;
        } else {
            switch (1.$SwitchMap$net$minecraft$util$Direction[direction.ordinal()]) {
                case 2: {
                    this.mirror = Mirror.LEFT_RIGHT;
                    this.rotation = Rotation.NONE;
                    break;
                }
                case 3: {
                    this.mirror = Mirror.LEFT_RIGHT;
                    this.rotation = Rotation.CLOCKWISE_90;
                    break;
                }
                case 4: {
                    this.mirror = Mirror.NONE;
                    this.rotation = Rotation.CLOCKWISE_90;
                    break;
                }
                default: {
                    this.mirror = Mirror.NONE;
                    this.rotation = Rotation.NONE;
                }
            }
        }
    }

    public Rotation getRotation() {
        return this.rotation;
    }

    public IStructurePieceType getStructurePieceType() {
        return this.structurePieceType;
    }

    public static abstract class BlockSelector {
        protected BlockState blockstate = Blocks.AIR.getDefaultState();

        protected BlockSelector() {
        }

        public abstract void selectBlocks(Random var1, int var2, int var3, int var4, boolean var5);

        public BlockState getBlockState() {
            return this.blockstate;
        }
    }
}

