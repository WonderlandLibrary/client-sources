package HORIZON-6-0-SKIDPROTECTION;

import java.util.Collection;
import java.util.Set;
import java.util.Iterator;
import com.google.common.collect.Maps;
import java.util.Map;

public class LowerStringMap implements Map
{
    private final Map HorizonCode_Horizon_È;
    private static final String Â = "CL_00001488";
    
    public LowerStringMap() {
        this.HorizonCode_Horizon_È = Maps.newLinkedHashMap();
    }
    
    @Override
    public int size() {
        return this.HorizonCode_Horizon_È.size();
    }
    
    @Override
    public boolean isEmpty() {
        return this.HorizonCode_Horizon_È.isEmpty();
    }
    
    @Override
    public boolean containsKey(final Object p_containsKey_1_) {
        return this.HorizonCode_Horizon_È.containsKey(p_containsKey_1_.toString().toLowerCase());
    }
    
    @Override
    public boolean containsValue(final Object p_containsValue_1_) {
        return this.HorizonCode_Horizon_È.containsKey(p_containsValue_1_);
    }
    
    @Override
    public Object get(final Object p_get_1_) {
        return this.HorizonCode_Horizon_È.get(p_get_1_.toString().toLowerCase());
    }
    
    public Object HorizonCode_Horizon_È(final String p_put_1_, final Object p_put_2_) {
        return this.HorizonCode_Horizon_È.put(p_put_1_.toLowerCase(), p_put_2_);
    }
    
    @Override
    public Object remove(final Object p_remove_1_) {
        return this.HorizonCode_Horizon_È.remove(p_remove_1_.toString().toLowerCase());
    }
    
    @Override
    public void putAll(final Map p_putAll_1_) {
        for (final Entry var3 : p_putAll_1_.entrySet()) {
            this.HorizonCode_Horizon_È(var3.getKey(), var3.getValue());
        }
    }
    
    @Override
    public void clear() {
        this.HorizonCode_Horizon_È.clear();
    }
    
    @Override
    public Set keySet() {
        return this.HorizonCode_Horizon_È.keySet();
    }
    
    @Override
    public Collection values() {
        return this.HorizonCode_Horizon_È.values();
    }
    
    @Override
    public Set entrySet() {
        return this.HorizonCode_Horizon_È.entrySet();
    }
    
    @Override
    public Object put(final Object p_put_1_, final Object p_put_2_) {
        return this.HorizonCode_Horizon_È((String)p_put_1_, p_put_2_);
    }
}
