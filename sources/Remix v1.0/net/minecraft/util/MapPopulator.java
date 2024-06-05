package net.minecraft.util;

import com.google.common.collect.*;
import java.util.*;

public class MapPopulator
{
    public static Map createMap(final Iterable keys, final Iterable values) {
        return populateMap(keys, values, Maps.newLinkedHashMap());
    }
    
    public static Map populateMap(final Iterable keys, final Iterable values, final Map map) {
        final Iterator var3 = values.iterator();
        for (final Object var5 : keys) {
            map.put(var5, var3.next());
        }
        if (var3.hasNext()) {
            throw new NoSuchElementException();
        }
        return map;
    }
}
