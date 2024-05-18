package net.minecraft.client.renderer;

import com.google.common.base.*;
import net.minecraft.entity.*;

class EntityRenderer1 implements Predicate
{
    final EntityRenderer field_90032_a;
    
    public boolean apply(final Entity entity) {
        return entity.canBeCollidedWith();
    }
    
    EntityRenderer1(final EntityRenderer field_90032_a) {
        this.field_90032_a = field_90032_a;
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
            if (0 < 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public boolean apply(final Object o) {
        return this.apply((Entity)o);
    }
}
