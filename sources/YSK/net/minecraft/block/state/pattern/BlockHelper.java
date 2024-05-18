package net.minecraft.block.state.pattern;

import com.google.common.base.*;
import net.minecraft.block.state.*;
import net.minecraft.block.*;

public class BlockHelper implements Predicate<IBlockState>
{
    private final Block block;
    
    private BlockHelper(final Block block) {
        this.block = block;
    }
    
    public boolean apply(final IBlockState blockState) {
        if (blockState != null && blockState.getBlock() == this.block) {
            return " ".length() != 0;
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
            if (2 <= -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public boolean apply(final Object o) {
        return this.apply((IBlockState)o);
    }
    
    public static BlockHelper forBlock(final Block block) {
        return new BlockHelper(block);
    }
}
