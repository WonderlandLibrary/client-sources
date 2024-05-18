package net.minecraft.block;

import net.minecraft.block.properties.*;
import net.minecraft.block.material.*;
import net.minecraft.block.state.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;

public class BlockPressurePlateWeighted extends BlockBasePressurePlate
{
    private static final String[] I;
    private final int field_150068_a;
    public static final PropertyInteger POWER;
    
    @Override
    protected int getRedstoneStrength(final IBlockState blockState) {
        return blockState.getValue((IProperty<Integer>)BlockPressurePlateWeighted.POWER);
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return blockState.getValue((IProperty<Integer>)BlockPressurePlateWeighted.POWER);
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
            if (true != true) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty((IProperty<Comparable>)BlockPressurePlateWeighted.POWER, n);
    }
    
    @Override
    protected IBlockState setRedstoneStrength(final IBlockState blockState, final int n) {
        return blockState.withProperty((IProperty<Comparable>)BlockPressurePlateWeighted.POWER, n);
    }
    
    protected BlockPressurePlateWeighted(final Material material, final int field_150068_a, final MapColor mapColor) {
        super(material, mapColor);
        this.setDefaultState(this.blockState.getBaseState().withProperty((IProperty<Comparable>)BlockPressurePlateWeighted.POWER, "".length()));
        this.field_150068_a = field_150068_a;
    }
    
    protected BlockPressurePlateWeighted(final Material material, final int n) {
        this(material, n, material.getMaterialMapColor());
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("\u0018\u001b?+\u0018", "htHNj");
    }
    
    @Override
    protected BlockState createBlockState() {
        final IProperty[] array = new IProperty[" ".length()];
        array["".length()] = BlockPressurePlateWeighted.POWER;
        return new BlockState(this, array);
    }
    
    @Override
    public int tickRate(final World world) {
        return 0x7B ^ 0x71;
    }
    
    static {
        I();
        POWER = PropertyInteger.create(BlockPressurePlateWeighted.I["".length()], "".length(), 0x23 ^ 0x2C);
    }
    
    @Override
    protected int computeRedstoneStrength(final World world, final BlockPos blockPos) {
        final int min = Math.min(world.getEntitiesWithinAABB((Class<? extends Entity>)Entity.class, this.getSensitiveAABB(blockPos)).size(), this.field_150068_a);
        if (min > 0) {
            return MathHelper.ceiling_float_int(Math.min(this.field_150068_a, min) / this.field_150068_a * 15.0f);
        }
        return "".length();
    }
}
