package net.minecraft.block;

import net.minecraft.block.properties.*;
import net.minecraft.creativetab.*;
import net.minecraft.world.*;
import net.minecraft.block.state.*;
import net.minecraft.init.*;
import net.minecraft.world.gen.feature.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.util.*;

public class BlockSapling extends BlockBush implements IGrowable
{
    public static final PropertyEnum<BlockPlanks.EnumType> TYPE;
    private static int[] $SWITCH_TABLE$net$minecraft$block$BlockPlanks$EnumType;
    public static final PropertyInteger STAGE;
    private static final String[] I;
    
    static {
        I();
        TYPE = PropertyEnum.create(BlockSapling.I["".length()], BlockPlanks.EnumType.class);
        STAGE = PropertyInteger.create(BlockSapling.I[" ".length()], "".length(), " ".length());
    }
    
    protected BlockSapling() {
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockSapling.TYPE, BlockPlanks.EnumType.OAK).withProperty((IProperty<Comparable>)BlockSapling.STAGE, "".length()));
        final float n = 0.4f;
        this.setBlockBounds(0.5f - n, 0.0f, 0.5f - n, 0.5f + n, n * 2.0f, 0.5f + n);
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }
    
    @Override
    public void updateTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
        if (!world.isRemote) {
            super.updateTick(world, blockPos, blockState, random);
            if (world.getLightFromNeighbors(blockPos.up()) >= (0x77 ^ 0x7E) && random.nextInt(0x66 ^ 0x61) == 0) {
                this.grow(world, blockPos, blockState, random);
            }
        }
    }
    
    @Override
    public boolean canGrow(final World world, final BlockPos blockPos, final IBlockState blockState, final boolean b) {
        return " ".length() != 0;
    }
    
    @Override
    protected BlockState createBlockState() {
        final IProperty[] array = new IProperty["  ".length()];
        array["".length()] = BlockSapling.TYPE;
        array[" ".length()] = BlockSapling.STAGE;
        return new BlockState(this, array);
    }
    
    private boolean func_181624_a(final World world, final BlockPos blockPos, final int n, final int n2, final BlockPlanks.EnumType enumType) {
        if (this.isTypeAt(world, blockPos.add(n, "".length(), n2), enumType) && this.isTypeAt(world, blockPos.add(n + " ".length(), "".length(), n2), enumType) && this.isTypeAt(world, blockPos.add(n, "".length(), n2 + " ".length()), enumType) && this.isTypeAt(world, blockPos.add(n + " ".length(), "".length(), n2 + " ".length()), enumType)) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    static int[] $SWITCH_TABLE$net$minecraft$block$BlockPlanks$EnumType() {
        final int[] $switch_TABLE$net$minecraft$block$BlockPlanks$EnumType = BlockSapling.$SWITCH_TABLE$net$minecraft$block$BlockPlanks$EnumType;
        if ($switch_TABLE$net$minecraft$block$BlockPlanks$EnumType != null) {
            return $switch_TABLE$net$minecraft$block$BlockPlanks$EnumType;
        }
        final int[] $switch_TABLE$net$minecraft$block$BlockPlanks$EnumType2 = new int[BlockPlanks.EnumType.values().length];
        try {
            $switch_TABLE$net$minecraft$block$BlockPlanks$EnumType2[BlockPlanks.EnumType.ACACIA.ordinal()] = (0x1B ^ 0x1E);
            "".length();
            if (1 == -1) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            $switch_TABLE$net$minecraft$block$BlockPlanks$EnumType2[BlockPlanks.EnumType.BIRCH.ordinal()] = "   ".length();
            "".length();
            if (4 == 2) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError2) {}
        try {
            $switch_TABLE$net$minecraft$block$BlockPlanks$EnumType2[BlockPlanks.EnumType.DARK_OAK.ordinal()] = (0x8D ^ 0x8B);
            "".length();
            if (1 >= 4) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError3) {}
        try {
            $switch_TABLE$net$minecraft$block$BlockPlanks$EnumType2[BlockPlanks.EnumType.JUNGLE.ordinal()] = (0xA5 ^ 0xA1);
            "".length();
            if (1 == 3) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError4) {}
        try {
            $switch_TABLE$net$minecraft$block$BlockPlanks$EnumType2[BlockPlanks.EnumType.OAK.ordinal()] = " ".length();
            "".length();
            if (4 <= 3) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError5) {}
        try {
            $switch_TABLE$net$minecraft$block$BlockPlanks$EnumType2[BlockPlanks.EnumType.SPRUCE.ordinal()] = "  ".length();
            "".length();
            if (0 <= -1) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError6) {}
        return BlockSapling.$SWITCH_TABLE$net$minecraft$block$BlockPlanks$EnumType = $switch_TABLE$net$minecraft$block$BlockPlanks$EnumType2;
    }
    
    public boolean isTypeAt(final World world, final BlockPos blockPos, final BlockPlanks.EnumType enumType) {
        final IBlockState blockState = world.getBlockState(blockPos);
        if (blockState.getBlock() == this && blockState.getValue(BlockSapling.TYPE) == enumType) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public void generateTree(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
        WorldGenAbstractTree worldGenAbstractTree;
        if (random.nextInt(0x7D ^ 0x77) == 0) {
            worldGenAbstractTree = new WorldGenBigTree(" ".length() != 0);
            "".length();
            if (3 != 3) {
                throw null;
            }
        }
        else {
            worldGenAbstractTree = new WorldGenTrees(" ".length() != 0);
        }
        WorldGenAbstractTree worldGenAbstractTree2 = worldGenAbstractTree;
        int i = "".length();
        int j = "".length();
        int n = "".length();
        switch ($SWITCH_TABLE$net$minecraft$block$BlockPlanks$EnumType()[blockState.getValue(BlockSapling.TYPE).ordinal()]) {
            case 2: {
                i = "".length();
                "".length();
                if (3 <= 0) {
                    throw null;
                }
            Label_0255:
                while (i >= -" ".length()) {
                    j = "".length();
                    "".length();
                    if (-1 >= 0) {
                        throw null;
                    }
                    while (j >= -" ".length()) {
                        if (this.func_181624_a(world, blockPos, i, j, BlockPlanks.EnumType.SPRUCE)) {
                            worldGenAbstractTree2 = new WorldGenMegaPineTree("".length() != 0, random.nextBoolean());
                            n = " ".length();
                            "".length();
                            if (0 == 2) {
                                throw null;
                            }
                            break Label_0255;
                        }
                        else {
                            --j;
                        }
                    }
                    --i;
                }
                if (n != 0) {
                    break;
                }
                j = "".length();
                i = "".length();
                worldGenAbstractTree2 = new WorldGenTaiga2(" ".length() != 0);
                "".length();
                if (3 < 3) {
                    throw null;
                }
                break;
            }
            case 3: {
                worldGenAbstractTree2 = new WorldGenForest(" ".length() != 0, "".length() != 0);
                "".length();
                if (false) {
                    throw null;
                }
                break;
            }
            case 4: {
                final IBlockState withProperty = Blocks.log.getDefaultState().withProperty(BlockOldLog.VARIANT, BlockPlanks.EnumType.JUNGLE);
                final IBlockState withProperty2 = Blocks.leaves.getDefaultState().withProperty(BlockOldLeaf.VARIANT, BlockPlanks.EnumType.JUNGLE).withProperty((IProperty<Comparable>)BlockLeaves.CHECK_DECAY, "".length() != 0);
                i = "".length();
                "".length();
                if (1 <= -1) {
                    throw null;
                }
            Label_0518:
                while (i >= -" ".length()) {
                    j = "".length();
                    "".length();
                    if (1 >= 4) {
                        throw null;
                    }
                    while (j >= -" ".length()) {
                        if (this.func_181624_a(world, blockPos, i, j, BlockPlanks.EnumType.JUNGLE)) {
                            worldGenAbstractTree2 = new WorldGenMegaJungle(" ".length() != 0, 0x1C ^ 0x16, 0x0 ^ 0x14, withProperty, withProperty2);
                            n = " ".length();
                            "".length();
                            if (3 >= 4) {
                                throw null;
                            }
                            break Label_0518;
                        }
                        else {
                            --j;
                        }
                    }
                    --i;
                }
                if (n != 0) {
                    break;
                }
                j = "".length();
                i = "".length();
                worldGenAbstractTree2 = new WorldGenTrees(" ".length() != 0, (0x4 ^ 0x0) + random.nextInt(0x9 ^ 0xE), withProperty, withProperty2, "".length() != 0);
                "".length();
                if (-1 != -1) {
                    throw null;
                }
                break;
            }
            case 5: {
                worldGenAbstractTree2 = new WorldGenSavannaTree(" ".length() != 0);
                "".length();
                if (1 == 2) {
                    throw null;
                }
                break;
            }
            case 6: {
                i = "".length();
                "".length();
                if (0 < -1) {
                    throw null;
                }
            Label_0732:
                while (i >= -" ".length()) {
                    j = "".length();
                    "".length();
                    if (4 < 3) {
                        throw null;
                    }
                    while (j >= -" ".length()) {
                        if (this.func_181624_a(world, blockPos, i, j, BlockPlanks.EnumType.DARK_OAK)) {
                            worldGenAbstractTree2 = new WorldGenCanopyTree(" ".length() != 0);
                            n = " ".length();
                            "".length();
                            if (1 >= 4) {
                                throw null;
                            }
                            break Label_0732;
                        }
                        else {
                            --j;
                        }
                    }
                    --i;
                }
                if (n == 0) {
                    return;
                }
                break;
            }
        }
        final IBlockState defaultState = Blocks.air.getDefaultState();
        if (n != 0) {
            world.setBlockState(blockPos.add(i, "".length(), j), defaultState, 0x2F ^ 0x2B);
            world.setBlockState(blockPos.add(i + " ".length(), "".length(), j), defaultState, 0x10 ^ 0x14);
            world.setBlockState(blockPos.add(i, "".length(), j + " ".length()), defaultState, 0x58 ^ 0x5C);
            world.setBlockState(blockPos.add(i + " ".length(), "".length(), j + " ".length()), defaultState, 0x30 ^ 0x34);
            "".length();
            if (1 == 4) {
                throw null;
            }
        }
        else {
            world.setBlockState(blockPos, defaultState, 0x5E ^ 0x5A);
        }
        if (!worldGenAbstractTree2.generate(world, random, blockPos.add(i, "".length(), j))) {
            if (n != 0) {
                world.setBlockState(blockPos.add(i, "".length(), j), blockState, 0x15 ^ 0x11);
                world.setBlockState(blockPos.add(i + " ".length(), "".length(), j), blockState, 0xB1 ^ 0xB5);
                world.setBlockState(blockPos.add(i, "".length(), j + " ".length()), blockState, 0x82 ^ 0x86);
                world.setBlockState(blockPos.add(i + " ".length(), "".length(), j + " ".length()), blockState, 0x75 ^ 0x71);
                "".length();
                if (1 == 4) {
                    throw null;
                }
            }
            else {
                world.setBlockState(blockPos, blockState, 0xB4 ^ 0xB0);
            }
        }
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (0 >= 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void getSubBlocks(final Item item, final CreativeTabs creativeTabs, final List<ItemStack> list) {
        final BlockPlanks.EnumType[] values;
        final int length = (values = BlockPlanks.EnumType.values()).length;
        int i = "".length();
        "".length();
        if (-1 == 4) {
            throw null;
        }
        while (i < length) {
            list.add(new ItemStack(item, " ".length(), values[i].getMetadata()));
            ++i;
        }
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return "".length() | blockState.getValue(BlockSapling.TYPE).getMetadata() | blockState.getValue((IProperty<Integer>)BlockSapling.STAGE) << "   ".length();
    }
    
    @Override
    public boolean canUseBonemeal(final World world, final Random random, final BlockPos blockPos, final IBlockState blockState) {
        if (world.rand.nextFloat() < 0.45) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty(BlockSapling.TYPE, BlockPlanks.EnumType.byMetadata(n & (0xA7 ^ 0xA0))).withProperty((IProperty<Comparable>)BlockSapling.STAGE, (n & (0x60 ^ 0x68)) >> "   ".length());
    }
    
    @Override
    public String getLocalizedName() {
        return StatCollector.translateToLocal(String.valueOf(this.getUnlocalizedName()) + BlockSapling.I["  ".length()] + BlockPlanks.EnumType.OAK.getUnlocalizedName() + BlockSapling.I["   ".length()]);
    }
    
    @Override
    public void grow(final World world, final Random random, final BlockPos blockPos, final IBlockState blockState) {
        this.grow(world, blockPos, blockState, random);
    }
    
    public void grow(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
        if (blockState.getValue((IProperty<Integer>)BlockSapling.STAGE) == 0) {
            world.setBlockState(blockPos, blockState.cycleProperty((IProperty<Comparable>)BlockSapling.STAGE), 0x7D ^ 0x79);
            "".length();
            if (1 == 2) {
                throw null;
            }
        }
        else {
            this.generateTree(world, blockPos, blockState, random);
        }
    }
    
    private static void I() {
        (I = new String[0x90 ^ 0x94])["".length()] = I("\u0011\u0003\u0017\u0004", "ezgac");
        BlockSapling.I[" ".length()] = I("5\u001d3\u00003", "FiRgV");
        BlockSapling.I["  ".length()] = I("x", "VYSyD");
        BlockSapling.I["   ".length()] = I("F\u001b\u001b\n\u0016", "huzgs");
    }
    
    @Override
    public int damageDropped(final IBlockState blockState) {
        return blockState.getValue(BlockSapling.TYPE).getMetadata();
    }
}
