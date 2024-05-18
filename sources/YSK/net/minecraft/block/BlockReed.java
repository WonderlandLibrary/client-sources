package net.minecraft.block;

import net.minecraft.world.*;
import net.minecraft.item.*;
import net.minecraft.block.properties.*;
import net.minecraft.init.*;
import net.minecraft.block.material.*;
import net.minecraft.block.state.*;
import net.minecraft.util.*;
import java.util.*;

public class BlockReed extends Block
{
    public static final PropertyInteger AGE;
    private static final String[] I;
    
    @Override
    public int colorMultiplier(final IBlockAccess blockAccess, final BlockPos blockPos, final int n) {
        return blockAccess.getBiomeGenForCoords(blockPos).getGrassColorAtPos(blockPos);
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBox(final World world, final BlockPos blockPos, final IBlockState blockState) {
        return null;
    }
    
    @Override
    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.CUTOUT;
    }
    
    @Override
    public void onNeighborBlockChange(final World world, final BlockPos blockPos, final IBlockState blockState, final Block block) {
        this.checkForDrop(world, blockPos, blockState);
    }
    
    static {
        I();
        AGE = PropertyInteger.create(BlockReed.I["".length()], "".length(), 0x55 ^ 0x5A);
    }
    
    @Override
    public Item getItem(final World world, final BlockPos blockPos) {
        return Items.reeds;
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty((IProperty<Comparable>)BlockReed.AGE, n);
    }
    
    protected final boolean checkForDrop(final World world, final BlockPos blockToAir, final IBlockState blockState) {
        if (this.canBlockStay(world, blockToAir)) {
            return " ".length() != 0;
        }
        this.dropBlockAsItem(world, blockToAir, blockState, "".length());
        world.setBlockToAir(blockToAir);
        return "".length() != 0;
    }
    
    public boolean canBlockStay(final World world, final BlockPos blockPos) {
        return this.canPlaceBlockAt(world, blockPos);
    }
    
    @Override
    public void updateTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
        if ((world.getBlockState(blockPos.down()).getBlock() == Blocks.reeds || this.checkForDrop(world, blockPos, blockState)) && world.isAirBlock(blockPos.up())) {
            int length = " ".length();
            "".length();
            if (-1 < -1) {
                throw null;
            }
            while (world.getBlockState(blockPos.down(length)).getBlock() == this) {
                ++length;
            }
            if (length < "   ".length()) {
                final int intValue = blockState.getValue((IProperty<Integer>)BlockReed.AGE);
                if (intValue == (0x6A ^ 0x65)) {
                    world.setBlockState(blockPos.up(), this.getDefaultState());
                    world.setBlockState(blockPos, blockState.withProperty((IProperty<Comparable>)BlockReed.AGE, "".length()), 0x7F ^ 0x7B);
                    "".length();
                    if (3 <= 1) {
                        throw null;
                    }
                }
                else {
                    world.setBlockState(blockPos, blockState.withProperty((IProperty<Comparable>)BlockReed.AGE, intValue + " ".length()), 0xB1 ^ 0xB5);
                }
            }
        }
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I(",\u0012=", "MuXxr");
    }
    
    protected BlockReed() {
        super(Material.plants);
        this.setDefaultState(this.blockState.getBaseState().withProperty((IProperty<Comparable>)BlockReed.AGE, "".length()));
        final float n = 0.375f;
        this.setBlockBounds(0.5f - n, 0.0f, 0.5f - n, 0.5f + n, 1.0f, 0.5f + n);
        this.setTickRandomly(" ".length() != 0);
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return blockState.getValue((IProperty<Integer>)BlockReed.AGE);
    }
    
    @Override
    public Item getItemDropped(final IBlockState blockState, final Random random, final int n) {
        return Items.reeds;
    }
    
    @Override
    protected BlockState createBlockState() {
        final IProperty[] array = new IProperty[" ".length()];
        array["".length()] = BlockReed.AGE;
        return new BlockState(this, array);
    }
    
    @Override
    public boolean isFullCube() {
        return "".length() != 0;
    }
    
    @Override
    public boolean isOpaqueCube() {
        return "".length() != 0;
    }
    
    @Override
    public boolean canPlaceBlockAt(final World world, final BlockPos blockPos) {
        final Block block = world.getBlockState(blockPos.down()).getBlock();
        if (block == this) {
            return " ".length() != 0;
        }
        if (block != Blocks.grass && block != Blocks.dirt && block != Blocks.sand) {
            return "".length() != 0;
        }
        final Iterator iterator = EnumFacing.Plane.HORIZONTAL.iterator();
        "".length();
        if (2 == 1) {
            throw null;
        }
        while (iterator.hasNext()) {
            if (world.getBlockState(blockPos.offset(iterator.next()).down()).getBlock().getMaterial() == Material.water) {
                return " ".length() != 0;
            }
        }
        return "".length() != 0;
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
            if (-1 < -1) {
                throw null;
            }
        }
        return sb.toString();
    }
}
