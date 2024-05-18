package net.minecraft.block;

import net.minecraft.block.properties.*;
import net.minecraft.block.material.*;
import net.minecraft.util.*;
import com.google.common.base.*;

public abstract class BlockDirectional extends Block
{
    private static final String[] I;
    public static final PropertyDirection FACING;
    
    protected BlockDirectional(final Material material, final MapColor mapColor) {
        super(material, mapColor);
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
            if (1 == 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("\u001e\u0016\u0011\u001a\u0016\u001f", "xwrsx");
    }
    
    static {
        I();
        FACING = PropertyDirection.create(BlockDirectional.I["".length()], (Predicate<EnumFacing>)EnumFacing.Plane.HORIZONTAL);
    }
    
    protected BlockDirectional(final Material material) {
        super(material);
    }
}
