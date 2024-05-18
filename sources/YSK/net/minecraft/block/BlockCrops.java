package net.minecraft.block;

import net.minecraft.world.*;
import net.minecraft.block.properties.*;
import net.minecraft.util.*;
import net.minecraft.creativetab.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.block.state.*;

public class BlockCrops extends BlockBush implements IGrowable
{
    public static final PropertyInteger AGE;
    private static final String[] I;
    
    @Override
    public void dropBlockAsItemWithChance(final World world, final BlockPos blockPos, final IBlockState blockState, final float n, final int n2) {
        super.dropBlockAsItemWithChance(world, blockPos, blockState, n, "".length());
        if (!world.isRemote) {
            final int intValue = blockState.getValue((IProperty<Integer>)BlockCrops.AGE);
            if (intValue >= (0x7E ^ 0x79)) {
                final int n3 = "   ".length() + n2;
                int i = "".length();
                "".length();
                if (false == true) {
                    throw null;
                }
                while (i < n3) {
                    if (world.rand.nextInt(0xBE ^ 0xB1) <= intValue) {
                        Block.spawnAsEntity(world, blockPos, new ItemStack(this.getSeed(), " ".length(), "".length()));
                    }
                    ++i;
                }
            }
        }
    }
    
    public void grow(final World world, final BlockPos blockPos, final IBlockState blockState) {
        int n = blockState.getValue((IProperty<Integer>)BlockCrops.AGE) + MathHelper.getRandomIntegerInRange(world.rand, "  ".length(), 0xE ^ 0xB);
        if (n > (0x65 ^ 0x62)) {
            n = (0x8B ^ 0x8C);
        }
        world.setBlockState(blockPos, blockState.withProperty((IProperty<Comparable>)BlockCrops.AGE, n), "  ".length());
    }
    
    protected BlockCrops() {
        this.setDefaultState(this.blockState.getBaseState().withProperty((IProperty<Comparable>)BlockCrops.AGE, "".length()));
        this.setTickRandomly(" ".length() != 0);
        final float n = 0.5f;
        this.setBlockBounds(0.5f - n, 0.0f, 0.5f - n, 0.5f + n, 0.25f, 0.5f + n);
        this.setCreativeTab(null);
        this.setHardness(0.0f);
        this.setStepSound(BlockCrops.soundTypeGrass);
        this.disableStats();
    }
    
    @Override
    public void grow(final World world, final Random random, final BlockPos blockPos, final IBlockState blockState) {
        this.grow(world, blockPos, blockState);
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("\u000e=,", "oZIqh");
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
            if (1 <= -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    protected Item getCrop() {
        return Items.wheat;
    }
    
    @Override
    protected boolean canPlaceBlockOn(final Block block) {
        if (block == Blocks.farmland) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return blockState.getValue((IProperty<Integer>)BlockCrops.AGE);
    }
    
    @Override
    public boolean canBlockStay(final World world, final BlockPos blockPos, final IBlockState blockState) {
        if ((world.getLight(blockPos) >= (0x91 ^ 0x99) || world.canSeeSky(blockPos)) && this.canPlaceBlockOn(world.getBlockState(blockPos.down()).getBlock())) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    public boolean canUseBonemeal(final World world, final Random random, final BlockPos blockPos, final IBlockState blockState) {
        return " ".length() != 0;
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty((IProperty<Comparable>)BlockCrops.AGE, n);
    }
    
    @Override
    public Item getItem(final World world, final BlockPos blockPos) {
        return this.getSeed();
    }
    
    @Override
    public Item getItemDropped(final IBlockState blockState, final Random random, final int n) {
        Item item;
        if (blockState.getValue((IProperty<Integer>)BlockCrops.AGE) == (0x57 ^ 0x50)) {
            item = this.getCrop();
            "".length();
            if (false == true) {
                throw null;
            }
        }
        else {
            item = this.getSeed();
        }
        return item;
    }
    
    @Override
    public void updateTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
        super.updateTick(world, blockPos, blockState, random);
        if (world.getLightFromNeighbors(blockPos.up()) >= (0x5C ^ 0x55)) {
            final int intValue = blockState.getValue((IProperty<Integer>)BlockCrops.AGE);
            if (intValue < (0xB6 ^ 0xB1) && random.nextInt((int)(25.0f / getGrowthChance(this, world, blockPos)) + " ".length()) == 0) {
                world.setBlockState(blockPos, blockState.withProperty((IProperty<Comparable>)BlockCrops.AGE, intValue + " ".length()), "  ".length());
            }
        }
    }
    
    protected static float getGrowthChance(final Block block, final World world, final BlockPos blockPos) {
        float n = 1.0f;
        final BlockPos down = blockPos.down();
        int i = -" ".length();
        "".length();
        if (-1 >= 0) {
            throw null;
        }
        while (i <= " ".length()) {
            int j = -" ".length();
            "".length();
            if (1 >= 2) {
                throw null;
            }
            while (j <= " ".length()) {
                float n2 = 0.0f;
                final IBlockState blockState = world.getBlockState(down.add(i, "".length(), j));
                if (blockState.getBlock() == Blocks.farmland) {
                    n2 = 1.0f;
                    if (blockState.getValue((IProperty<Integer>)BlockFarmland.MOISTURE) > 0) {
                        n2 = 3.0f;
                    }
                }
                if (i != 0 || j != 0) {
                    n2 /= 4.0f;
                }
                n += n2;
                ++j;
            }
            ++i;
        }
        final BlockPos north = blockPos.north();
        final BlockPos south = blockPos.south();
        final BlockPos west = blockPos.west();
        final BlockPos east = blockPos.east();
        int n3;
        if (block != world.getBlockState(west).getBlock() && block != world.getBlockState(east).getBlock()) {
            n3 = "".length();
            "".length();
            if (4 <= 1) {
                throw null;
            }
        }
        else {
            n3 = " ".length();
        }
        final int n4 = n3;
        int n5;
        if (block != world.getBlockState(north).getBlock() && block != world.getBlockState(south).getBlock()) {
            n5 = "".length();
            "".length();
            if (4 != 4) {
                throw null;
            }
        }
        else {
            n5 = " ".length();
        }
        final int n6 = n5;
        if (n4 != 0 && n6 != 0) {
            n /= 2.0f;
            "".length();
            if (2 < -1) {
                throw null;
            }
        }
        else {
            int n7;
            if (block != world.getBlockState(west.north()).getBlock() && block != world.getBlockState(east.north()).getBlock() && block != world.getBlockState(east.south()).getBlock() && block != world.getBlockState(west.south()).getBlock()) {
                n7 = "".length();
                "".length();
                if (3 < 1) {
                    throw null;
                }
            }
            else {
                n7 = " ".length();
            }
            if (n7 != 0) {
                n /= 2.0f;
            }
        }
        return n;
    }
    
    @Override
    protected BlockState createBlockState() {
        final IProperty[] array = new IProperty[" ".length()];
        array["".length()] = BlockCrops.AGE;
        return new BlockState(this, array);
    }
    
    static {
        I();
        AGE = PropertyInteger.create(BlockCrops.I["".length()], "".length(), 0x1E ^ 0x19);
    }
    
    @Override
    public boolean canGrow(final World world, final BlockPos blockPos, final IBlockState blockState, final boolean b) {
        if (blockState.getValue((IProperty<Integer>)BlockCrops.AGE) < (0x62 ^ 0x65)) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    protected Item getSeed() {
        return Items.wheat_seeds;
    }
}
