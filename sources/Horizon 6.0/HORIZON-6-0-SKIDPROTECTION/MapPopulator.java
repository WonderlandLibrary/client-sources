package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import java.util.NoSuchElementException;
import com.google.common.collect.Maps;
import java.util.Map;

public class MapPopulator
{
    private static final String HorizonCode_Horizon_È = "CL_00002318";
    
    public static Map HorizonCode_Horizon_È(final Iterable keys, final Iterable values) {
        return HorizonCode_Horizon_È(keys, values, Maps.newLinkedHashMap());
    }
    
    public static Map HorizonCode_Horizon_È(final Iterable keys, final Iterable values, final Map map) {
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
