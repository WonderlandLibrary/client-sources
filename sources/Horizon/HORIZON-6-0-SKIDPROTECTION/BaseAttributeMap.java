package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import java.util.Collection;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import java.util.Map;

public abstract class BaseAttributeMap
{
    protected final Map HorizonCode_Horizon_È;
    protected final Map Â;
    protected final Multimap Ý;
    private static final String Ø­áŒŠá = "CL_00001566";
    
    public BaseAttributeMap() {
        this.HorizonCode_Horizon_È = Maps.newHashMap();
        this.Â = new LowerStringMap();
        this.Ý = (Multimap)HashMultimap.create();
    }
    
    public IAttributeInstance HorizonCode_Horizon_È(final IAttribute p_111151_1_) {
        return this.HorizonCode_Horizon_È.get(p_111151_1_);
    }
    
    public IAttributeInstance HorizonCode_Horizon_È(final String p_111152_1_) {
        return this.Â.get(p_111152_1_);
    }
    
    public IAttributeInstance Â(final IAttribute p_111150_1_) {
        if (this.Â.containsKey(p_111150_1_.HorizonCode_Horizon_È())) {
            throw new IllegalArgumentException("Attribute is already registered!");
        }
        final IAttributeInstance var2 = this.Ý(p_111150_1_);
        this.Â.put(p_111150_1_.HorizonCode_Horizon_È(), var2);
        this.HorizonCode_Horizon_È.put(p_111150_1_, var2);
        for (IAttribute var3 = p_111150_1_.Ø­áŒŠá(); var3 != null; var3 = var3.Ø­áŒŠá()) {
            this.Ý.put((Object)var3, (Object)p_111150_1_);
        }
        return var2;
    }
    
    protected abstract IAttributeInstance Ý(final IAttribute p0);
    
    public Collection HorizonCode_Horizon_È() {
        return this.Â.values();
    }
    
    public void HorizonCode_Horizon_È(final IAttributeInstance p_180794_1_) {
    }
    
    public void HorizonCode_Horizon_È(final Multimap p_111148_1_) {
        for (final Map.Entry var3 : p_111148_1_.entries()) {
            final IAttributeInstance var4 = this.HorizonCode_Horizon_È(var3.getKey());
            if (var4 != null) {
                var4.Ý(var3.getValue());
            }
        }
    }
    
    public void Â(final Multimap p_111147_1_) {
        for (final Map.Entry var3 : p_111147_1_.entries()) {
            final IAttributeInstance var4 = this.HorizonCode_Horizon_È(var3.getKey());
            if (var4 != null) {
                var4.Ý(var3.getValue());
                var4.Â(var3.getValue());
            }
        }
    }
}
