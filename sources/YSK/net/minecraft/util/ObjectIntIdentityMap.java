package net.minecraft.util;

import java.util.*;
import com.google.common.base.*;
import com.google.common.collect.*;

public class ObjectIntIdentityMap implements IObjectIntIterable
{
    private static final String[] I;
    private final List objectList;
    private static final String __OBFID;
    private final IdentityHashMap identityMap;
    
    public void put(final Object o, final int n) {
        this.identityMap.put(o, n);
        "".length();
        if (1 >= 3) {
            throw null;
        }
        while (this.objectList.size() <= n) {
            this.objectList.add(null);
        }
        this.objectList.set(n, o);
    }
    
    public final Object getByValue(final int n) {
        Object value;
        if (n >= 0 && n < this.objectList.size()) {
            value = this.objectList.get(n);
            "".length();
            if (4 <= 3) {
                throw null;
            }
        }
        else {
            value = null;
        }
        return value;
    }
    
    public List getObjectList() {
        return this.objectList;
    }
    
    public int get(final Object o) {
        final Integer n = this.identityMap.get(o);
        int intValue;
        if (n == null) {
            intValue = -" ".length();
            "".length();
            if (3 == 2) {
                throw null;
            }
        }
        else {
            intValue = n;
        }
        return intValue;
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("\u0006$\u0016SbuXxQbv", "EhIcR");
    }
    
    public ObjectIntIdentityMap() {
        this.identityMap = new IdentityHashMap(185 + 139 - 36 + 224);
        this.objectList = Lists.newArrayList();
    }
    
    static {
        I();
        __OBFID = ObjectIntIdentityMap.I["".length()];
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
            if (4 < -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public Iterator iterator() {
        return (Iterator)Iterators.filter((Iterator)this.objectList.iterator(), Predicates.notNull());
    }
}
