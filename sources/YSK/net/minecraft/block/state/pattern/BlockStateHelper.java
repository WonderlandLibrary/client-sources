package net.minecraft.block.state.pattern;

import com.google.common.base.*;
import net.minecraft.block.state.*;
import net.minecraft.block.properties.*;
import java.util.*;
import com.google.common.collect.*;
import net.minecraft.block.*;

public class BlockStateHelper implements Predicate<IBlockState>
{
    private static final String[] I;
    private final BlockState blockstate;
    private final Map<IProperty, Predicate> propertyPredicates;
    
    public boolean apply(final IBlockState blockState) {
        if (blockState == null || !blockState.getBlock().equals(this.blockstate.getBlock())) {
            return "".length() != 0;
        }
        final Iterator<Map.Entry<IProperty, Predicate>> iterator = this.propertyPredicates.entrySet().iterator();
        "".length();
        if (2 == 0) {
            throw null;
        }
        while (iterator.hasNext()) {
            final Map.Entry<IProperty, Predicate> entry = iterator.next();
            if (!entry.getValue().apply((Object)blockState.getValue((IProperty<Comparable>)entry.getKey()))) {
                return "".length() != 0;
            }
        }
        return " ".length() != 0;
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("B$\u0013\u0019\u001c\r3R\u0004\u0007\u00127\u001d\u0005\u0006B7\u0000\u0018\u0002\u00075\u0006\u000eR", "bGrwr");
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
            if (4 != 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public boolean apply(final Object o) {
        return this.apply((IBlockState)o);
    }
    
    public <V extends Comparable<V>> BlockStateHelper where(final IProperty<V> property, final Predicate<? extends V> predicate) {
        if (!this.blockstate.getProperties().contains(property)) {
            throw new IllegalArgumentException(this.blockstate + BlockStateHelper.I["".length()] + property);
        }
        this.propertyPredicates.put(property, predicate);
        return this;
    }
    
    static {
        I();
    }
    
    private BlockStateHelper(final BlockState blockstate) {
        this.propertyPredicates = (Map<IProperty, Predicate>)Maps.newHashMap();
        this.blockstate = blockstate;
    }
    
    public static BlockStateHelper forBlock(final Block block) {
        return new BlockStateHelper(block.getBlockState());
    }
}
