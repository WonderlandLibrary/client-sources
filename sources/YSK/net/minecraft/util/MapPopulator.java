package net.minecraft.util;

import java.util.*;
import com.google.common.collect.*;

public class MapPopulator
{
    public static <K, V> Map<K, V> populateMap(final Iterable<K> iterable, final Iterable<V> iterable2, final Map<K, V> map) {
        final Iterator<V> iterator = iterable2.iterator();
        final Iterator<K> iterator2 = iterable.iterator();
        "".length();
        if (3 != 3) {
            throw null;
        }
        while (iterator2.hasNext()) {
            map.put(iterator2.next(), iterator.next());
        }
        if (iterator.hasNext()) {
            throw new NoSuchElementException();
        }
        return map;
    }
    
    public static <K, V> Map<K, V> createMap(final Iterable<K> iterable, final Iterable<V> iterable2) {
        return populateMap(iterable, iterable2, Maps.newLinkedHashMap());
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
            if (3 < 0) {
                throw null;
            }
        }
        return sb.toString();
    }
}
