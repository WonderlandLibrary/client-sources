/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.palette;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import java.util.EnumSet;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.StemBlock;
import net.minecraft.block.StemGrownBlock;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.ChestType;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Direction8;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.palette.PalettedContainer;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkSection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UpgradeData {
    private static final Logger LOGGER = LogManager.getLogger();
    public static final UpgradeData EMPTY = new UpgradeData();
    private static final Direction8[] field_208832_b = Direction8.values();
    private final EnumSet<Direction8> field_196995_b = EnumSet.noneOf(Direction8.class);
    private final int[][] field_196996_c = new int[16][];
    private static final Map<Block, IBlockFixer> field_196997_d = new IdentityHashMap<Block, IBlockFixer>();
    private static final Set<IBlockFixer> FIXERS = Sets.newHashSet();

    private UpgradeData() {
    }

    public UpgradeData(CompoundNBT compoundNBT) {
        this();
        if (compoundNBT.contains("Indices", 1)) {
            CompoundNBT compoundNBT2 = compoundNBT.getCompound("Indices");
            for (int i = 0; i < this.field_196996_c.length; ++i) {
                String string = String.valueOf(i);
                if (!compoundNBT2.contains(string, 0)) continue;
                this.field_196996_c[i] = compoundNBT2.getIntArray(string);
            }
        }
        int n = compoundNBT.getInt("Sides");
        for (Direction8 direction8 : Direction8.values()) {
            if ((n & 1 << direction8.ordinal()) == 0) continue;
            this.field_196995_b.add(direction8);
        }
    }

    public void postProcessChunk(Chunk chunk) {
        this.func_196989_a(chunk);
        for (Direction8 direction8 : field_208832_b) {
            UpgradeData.func_196991_a(chunk, direction8);
        }
        World world = chunk.getWorld();
        FIXERS.forEach(arg_0 -> UpgradeData.lambda$postProcessChunk$0(world, arg_0));
    }

    private static void func_196991_a(Chunk chunk, Direction8 direction8) {
        World world = chunk.getWorld();
        if (chunk.getUpgradeData().field_196995_b.remove((Object)direction8)) {
            Set<Direction> set = direction8.getDirections();
            boolean bl = false;
            int n = 15;
            boolean bl2 = set.contains(Direction.EAST);
            boolean bl3 = set.contains(Direction.WEST);
            boolean bl4 = set.contains(Direction.SOUTH);
            boolean bl5 = set.contains(Direction.NORTH);
            boolean bl6 = set.size() == 1;
            ChunkPos chunkPos = chunk.getPos();
            int n2 = chunkPos.getXStart() + (!bl6 || !bl5 && !bl4 ? (bl3 ? 0 : 15) : 1);
            int n3 = chunkPos.getXStart() + (!bl6 || !bl5 && !bl4 ? (bl3 ? 0 : 15) : 14);
            int n4 = chunkPos.getZStart() + (!bl6 || !bl2 && !bl3 ? (bl5 ? 0 : 15) : 1);
            int n5 = chunkPos.getZStart() + (!bl6 || !bl2 && !bl3 ? (bl5 ? 0 : 15) : 14);
            Direction[] directionArray = Direction.values();
            BlockPos.Mutable mutable = new BlockPos.Mutable();
            for (BlockPos blockPos : BlockPos.getAllInBoxMutable(n2, 0, n4, n3, world.getHeight() - 1, n5)) {
                BlockState blockState;
                BlockState blockState2 = blockState = world.getBlockState(blockPos);
                for (Direction direction : directionArray) {
                    mutable.setAndMove(blockPos, direction);
                    blockState2 = UpgradeData.func_196987_a(blockState2, direction, world, blockPos, mutable);
                }
                Block.replaceBlock(blockState, blockState2, world, blockPos, 18);
            }
        }
    }

    private static BlockState func_196987_a(BlockState blockState, Direction direction, IWorld iWorld, BlockPos blockPos, BlockPos blockPos2) {
        return field_196997_d.getOrDefault(blockState.getBlock(), BlockFixers.DEFAULT).func_196982_a(blockState, direction, iWorld.getBlockState(blockPos2), iWorld, blockPos, blockPos2);
    }

    private void func_196989_a(Chunk chunk) {
        int n;
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        BlockPos.Mutable mutable2 = new BlockPos.Mutable();
        ChunkPos chunkPos = chunk.getPos();
        World world = chunk.getWorld();
        for (n = 0; n < 16; ++n) {
            ChunkSection chunkSection = chunk.getSections()[n];
            int[] nArray = this.field_196996_c[n];
            this.field_196996_c[n] = null;
            if (chunkSection == null || nArray == null || nArray.length <= 0) continue;
            Direction[] directionArray = Direction.values();
            PalettedContainer<BlockState> palettedContainer = chunkSection.getData();
            for (int n2 : nArray) {
                BlockState blockState;
                int n3 = n2 & 0xF;
                int n4 = n2 >> 8 & 0xF;
                int n5 = n2 >> 4 & 0xF;
                mutable.setPos(chunkPos.getXStart() + n3, (n << 4) + n4, chunkPos.getZStart() + n5);
                BlockState blockState2 = blockState = palettedContainer.get(n2);
                for (Direction direction : directionArray) {
                    mutable2.setAndMove(mutable, direction);
                    if (mutable.getX() >> 4 != chunkPos.x || mutable.getZ() >> 4 != chunkPos.z) continue;
                    blockState2 = UpgradeData.func_196987_a(blockState2, direction, world, mutable, mutable2);
                }
                Block.replaceBlock(blockState, blockState2, world, mutable, 18);
            }
        }
        for (n = 0; n < this.field_196996_c.length; ++n) {
            if (this.field_196996_c[n] != null) {
                LOGGER.warn("Discarding update data for section {} for chunk ({} {})", (Object)n, (Object)chunkPos.x, (Object)chunkPos.z);
            }
            this.field_196996_c[n] = null;
        }
    }

    public boolean isEmpty() {
        for (int[] nArray : this.field_196996_c) {
            if (nArray == null) continue;
            return true;
        }
        return this.field_196995_b.isEmpty();
    }

    public CompoundNBT write() {
        int n;
        CompoundNBT compoundNBT = new CompoundNBT();
        CompoundNBT compoundNBT2 = new CompoundNBT();
        for (n = 0; n < this.field_196996_c.length; ++n) {
            String string = String.valueOf(n);
            if (this.field_196996_c[n] == null || this.field_196996_c[n].length == 0) continue;
            compoundNBT2.putIntArray(string, this.field_196996_c[n]);
        }
        if (!compoundNBT2.isEmpty()) {
            compoundNBT.put("Indices", compoundNBT2);
        }
        n = 0;
        for (Direction8 direction8 : this.field_196995_b) {
            n |= 1 << direction8.ordinal();
        }
        compoundNBT.putByte("Sides", (byte)n);
        return compoundNBT;
    }

    private static void lambda$postProcessChunk$0(World world, IBlockFixer iBlockFixer) {
        iBlockFixer.func_208826_a(world);
    }

    /*
     * Uses 'sealed' constructs - enablewith --sealed true
     */
    static enum BlockFixers implements IBlockFixer
    {
        BLACKLIST(new Block[]{Blocks.OBSERVER, Blocks.NETHER_PORTAL, Blocks.WHITE_CONCRETE_POWDER, Blocks.ORANGE_CONCRETE_POWDER, Blocks.MAGENTA_CONCRETE_POWDER, Blocks.LIGHT_BLUE_CONCRETE_POWDER, Blocks.YELLOW_CONCRETE_POWDER, Blocks.LIME_CONCRETE_POWDER, Blocks.PINK_CONCRETE_POWDER, Blocks.GRAY_CONCRETE_POWDER, Blocks.LIGHT_GRAY_CONCRETE_POWDER, Blocks.CYAN_CONCRETE_POWDER, Blocks.PURPLE_CONCRETE_POWDER, Blocks.BLUE_CONCRETE_POWDER, Blocks.BROWN_CONCRETE_POWDER, Blocks.GREEN_CONCRETE_POWDER, Blocks.RED_CONCRETE_POWDER, Blocks.BLACK_CONCRETE_POWDER, Blocks.ANVIL, Blocks.CHIPPED_ANVIL, Blocks.DAMAGED_ANVIL, Blocks.DRAGON_EGG, Blocks.GRAVEL, Blocks.SAND, Blocks.RED_SAND, Blocks.OAK_SIGN, Blocks.SPRUCE_SIGN, Blocks.BIRCH_SIGN, Blocks.ACACIA_SIGN, Blocks.JUNGLE_SIGN, Blocks.DARK_OAK_SIGN, Blocks.OAK_WALL_SIGN, Blocks.SPRUCE_WALL_SIGN, Blocks.BIRCH_WALL_SIGN, Blocks.ACACIA_WALL_SIGN, Blocks.JUNGLE_WALL_SIGN, Blocks.DARK_OAK_WALL_SIGN}){

            @Override
            public BlockState func_196982_a(BlockState blockState, Direction direction, BlockState blockState2, IWorld iWorld, BlockPos blockPos, BlockPos blockPos2) {
                return blockState;
            }
        }
        ,
        DEFAULT(new Block[0]){

            @Override
            public BlockState func_196982_a(BlockState blockState, Direction direction, BlockState blockState2, IWorld iWorld, BlockPos blockPos, BlockPos blockPos2) {
                return blockState.updatePostPlacement(direction, iWorld.getBlockState(blockPos2), iWorld, blockPos, blockPos2);
            }
        }
        ,
        CHEST(new Block[]{Blocks.CHEST, Blocks.TRAPPED_CHEST}){

            @Override
            public BlockState func_196982_a(BlockState blockState, Direction direction, BlockState blockState2, IWorld iWorld, BlockPos blockPos, BlockPos blockPos2) {
                if (blockState2.isIn(blockState.getBlock()) && direction.getAxis().isHorizontal() && blockState.get(ChestBlock.TYPE) == ChestType.SINGLE && blockState2.get(ChestBlock.TYPE) == ChestType.SINGLE) {
                    Direction direction2 = blockState.get(ChestBlock.FACING);
                    if (direction.getAxis() != direction2.getAxis() && direction2 == blockState2.get(ChestBlock.FACING)) {
                        ChestType chestType = direction == direction2.rotateY() ? ChestType.LEFT : ChestType.RIGHT;
                        iWorld.setBlockState(blockPos2, (BlockState)blockState2.with(ChestBlock.TYPE, chestType.opposite()), 18);
                        if (direction2 == Direction.NORTH || direction2 == Direction.EAST) {
                            TileEntity tileEntity = iWorld.getTileEntity(blockPos);
                            TileEntity tileEntity2 = iWorld.getTileEntity(blockPos2);
                            if (tileEntity instanceof ChestTileEntity && tileEntity2 instanceof ChestTileEntity) {
                                ChestTileEntity.swapContents((ChestTileEntity)tileEntity, (ChestTileEntity)tileEntity2);
                            }
                        }
                        return (BlockState)blockState.with(ChestBlock.TYPE, chestType);
                    }
                }
                return blockState;
            }
        }
        ,
        LEAVES(true, new Block[]{Blocks.ACACIA_LEAVES, Blocks.BIRCH_LEAVES, Blocks.DARK_OAK_LEAVES, Blocks.JUNGLE_LEAVES, Blocks.OAK_LEAVES, Blocks.SPRUCE_LEAVES}){
            private final ThreadLocal<List<ObjectSet<BlockPos>>> field_208828_g = ThreadLocal.withInitial(4::lambda$$0);

            @Override
            public BlockState func_196982_a(BlockState blockState, Direction direction, BlockState blockState2, IWorld iWorld, BlockPos blockPos, BlockPos blockPos2) {
                BlockState blockState3 = blockState.updatePostPlacement(direction, iWorld.getBlockState(blockPos2), iWorld, blockPos, blockPos2);
                if (blockState != blockState3) {
                    int n = blockState3.get(BlockStateProperties.DISTANCE_1_7);
                    List<ObjectSet<BlockPos>> list = this.field_208828_g.get();
                    if (list.isEmpty()) {
                        for (int i = 0; i < 7; ++i) {
                            list.add(new ObjectOpenHashSet());
                        }
                    }
                    list.get(n).add(blockPos.toImmutable());
                }
                return blockState;
            }

            @Override
            public void func_208826_a(IWorld iWorld) {
                BlockPos.Mutable mutable = new BlockPos.Mutable();
                List<ObjectSet<BlockPos>> list = this.field_208828_g.get();
                for (int i = 2; i < list.size(); ++i) {
                    int n = i - 1;
                    ObjectSet<BlockPos> objectSet = list.get(n);
                    ObjectSet<BlockPos> objectSet2 = list.get(i);
                    for (BlockPos blockPos : objectSet) {
                        BlockState blockState = iWorld.getBlockState(blockPos);
                        if (blockState.get(BlockStateProperties.DISTANCE_1_7) < n) continue;
                        iWorld.setBlockState(blockPos, (BlockState)blockState.with(BlockStateProperties.DISTANCE_1_7, n), 18);
                        if (i == 7) continue;
                        for (Direction direction : field_208827_f) {
                            mutable.setAndMove(blockPos, direction);
                            BlockState blockState2 = iWorld.getBlockState(mutable);
                            if (!blockState2.hasProperty(BlockStateProperties.DISTANCE_1_7) || blockState.get(BlockStateProperties.DISTANCE_1_7) <= i) continue;
                            objectSet2.add(mutable.toImmutable());
                        }
                    }
                }
                list.clear();
            }

            private static List lambda$$0() {
                return Lists.newArrayListWithCapacity(7);
            }
        }
        ,
        STEM_BLOCK(new Block[]{Blocks.MELON_STEM, Blocks.PUMPKIN_STEM}){

            @Override
            public BlockState func_196982_a(BlockState blockState, Direction direction, BlockState blockState2, IWorld iWorld, BlockPos blockPos, BlockPos blockPos2) {
                StemGrownBlock stemGrownBlock;
                if (blockState.get(StemBlock.AGE) == 7 && blockState2.isIn(stemGrownBlock = ((StemBlock)blockState.getBlock()).getCrop())) {
                    return (BlockState)stemGrownBlock.getAttachedStem().getDefaultState().with(HorizontalBlock.HORIZONTAL_FACING, direction);
                }
                return blockState;
            }
        };

        public static final Direction[] field_208827_f;

        private BlockFixers(Block ... blockArray) {
            this(false, blockArray);
        }

        private BlockFixers(boolean bl, Block ... blockArray) {
            for (Block block : blockArray) {
                field_196997_d.put(block, this);
            }
            if (bl) {
                FIXERS.add(this);
            }
        }

        static {
            field_208827_f = Direction.values();
        }
    }

    public static interface IBlockFixer {
        public BlockState func_196982_a(BlockState var1, Direction var2, BlockState var3, IWorld var4, BlockPos var5, BlockPos var6);

        default public void func_208826_a(IWorld iWorld) {
        }
    }
}

