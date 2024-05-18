package net.minecraft.block;

import net.minecraft.block.properties.*;
import net.minecraft.block.state.*;
import net.minecraft.world.*;
import net.minecraft.util.*;

public class BlockRail extends BlockRailBase
{
    private static final String[] I;
    public static final PropertyEnum<EnumRailDirection> SHAPE;
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return blockState.getValue(BlockRail.SHAPE).getMetadata();
    }
    
    protected BlockRail() {
        super("".length() != 0);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockRail.SHAPE, EnumRailDirection.NORTH_SOUTH));
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty(BlockRail.SHAPE, EnumRailDirection.byMetadata(n));
    }
    
    @Override
    protected BlockState createBlockState() {
        final IProperty[] array = new IProperty[" ".length()];
        array["".length()] = BlockRail.SHAPE;
        return new BlockState(this, array);
    }
    
    static {
        I();
        SHAPE = PropertyEnum.create(BlockRail.I["".length()], EnumRailDirection.class);
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("\u00148\u0010\u0003-", "gPqsH");
    }
    
    @Override
    public IProperty<EnumRailDirection> getShapeProperty() {
        return BlockRail.SHAPE;
    }
    
    @Override
    protected void onNeighborChangedInternal(final World world, final BlockPos blockPos, final IBlockState blockState, final Block block) {
        if (block.canProvidePower() && new Rail(world, blockPos, blockState).countAdjacentRails() == "   ".length()) {
            this.func_176564_a(world, blockPos, blockState, "".length() != 0);
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
            if (1 < 0) {
                throw null;
            }
        }
        return sb.toString();
    }
}
