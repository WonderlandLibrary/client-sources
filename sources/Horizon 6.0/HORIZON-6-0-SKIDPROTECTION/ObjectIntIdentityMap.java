package HORIZON-6-0-SKIDPROTECTION;

import com.google.common.collect.Iterators;
import com.google.common.base.Predicates;
import java.util.Iterator;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.IdentityHashMap;

public class ObjectIntIdentityMap implements IObjectIntIterable
{
    private final IdentityHashMap HorizonCode_Horizon_È;
    private final List Â;
    private static final String Ý = "CL_00001203";
    
    public ObjectIntIdentityMap() {
        this.HorizonCode_Horizon_È = new IdentityHashMap(512);
        this.Â = Lists.newArrayList();
    }
    
    public void HorizonCode_Horizon_È(final Object key, final int value) {
        this.HorizonCode_Horizon_È.put(key, value);
        while (this.Â.size() <= value) {
            this.Â.add(null);
        }
        this.Â.set(value, key);
    }
    
    public int HorizonCode_Horizon_È(final Object key) {
        final Integer var2 = this.HorizonCode_Horizon_È.get(key);
        return (var2 == null) ? -1 : var2;
    }
    
    public final Object HorizonCode_Horizon_È(final int value) {
        return (value >= 0 && value < this.Â.size()) ? this.Â.get(value) : null;
    }
    
    @Override
    public Iterator iterator() {
        return (Iterator)Iterators.filter((Iterator)this.Â.iterator(), Predicates.notNull());
    }
}
