package net.minecraft.block.properties;

import net.minecraft.util.*;
import com.google.common.collect.*;
import java.util.*;
import com.google.common.base.*;

public class PropertyDirection extends PropertyEnum<EnumFacing>
{
    public static PropertyDirection create(final String s, final Predicate<EnumFacing> predicate) {
        return create(s, Collections2.filter((Collection)Lists.newArrayList((Object[])EnumFacing.values()), (Predicate)predicate));
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
            if (-1 >= 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    protected PropertyDirection(final String s, final Collection<EnumFacing> collection) {
        super(s, EnumFacing.class, collection);
    }
    
    public static PropertyDirection create(final String s, final Collection<EnumFacing> collection) {
        return new PropertyDirection(s, collection);
    }
    
    public static PropertyDirection create(final String s) {
        return create(s, (Predicate<EnumFacing>)Predicates.alwaysTrue());
    }
}
