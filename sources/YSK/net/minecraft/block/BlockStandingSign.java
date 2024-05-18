package net.minecraft.block;

import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.state.*;

public class BlockStandingSign extends BlockSign
{
    private static final String[] I;
    public static final PropertyInteger ROTATION;
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("\u0010\u0000:\n%\u000b\u0000 ", "boNkQ");
    }
    
    @Override
    public void onNeighborBlockChange(final World world, final BlockPos blockToAir, final IBlockState blockState, final Block block) {
        if (!world.getBlockState(blockToAir.down()).getBlock().getMaterial().isSolid()) {
            this.dropBlockAsItem(world, blockToAir, blockState, "".length());
            world.setBlockToAir(blockToAir);
        }
        super.onNeighborBlockChange(world, blockToAir, blockState, block);
    }
    
    public BlockStandingSign() {
        this.setDefaultState(this.blockState.getBaseState().withProperty((IProperty<Comparable>)BlockStandingSign.ROTATION, "".length()));
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return blockState.getValue((IProperty<Integer>)BlockStandingSign.ROTATION);
    }
    
    @Override
    protected BlockState createBlockState() {
        final IProperty[] array = new IProperty[" ".length()];
        array["".length()] = BlockStandingSign.ROTATION;
        return new BlockState(this, array);
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty((IProperty<Comparable>)BlockStandingSign.ROTATION, n);
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
            if (2 < 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    static {
        I();
        ROTATION = PropertyInteger.create(BlockStandingSign.I["".length()], "".length(), 0x18 ^ 0x17);
    }
}
