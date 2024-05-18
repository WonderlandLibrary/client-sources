package net.minecraft.block;

import net.minecraft.block.properties.*;
import net.minecraft.util.*;
import net.minecraft.block.material.*;

public abstract class BlockRotatedPillar extends Block
{
    private static final String[] I;
    public static final PropertyEnum<EnumFacing.Axis> AXIS;
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I(" 7;8", "AORKW");
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
            if (2 >= 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    protected BlockRotatedPillar(final Material material) {
        super(material, material.getMaterialMapColor());
    }
    
    static {
        I();
        AXIS = PropertyEnum.create(BlockRotatedPillar.I["".length()], EnumFacing.Axis.class);
    }
    
    protected BlockRotatedPillar(final Material material, final MapColor mapColor) {
        super(material, mapColor);
    }
}
