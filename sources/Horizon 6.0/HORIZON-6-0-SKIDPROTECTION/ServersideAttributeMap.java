package HORIZON-6-0-SKIDPROTECTION;

import java.util.HashSet;
import java.util.Collection;
import java.util.Iterator;
import com.google.common.collect.Sets;
import java.util.Map;
import java.util.Set;

public class ServersideAttributeMap extends BaseAttributeMap
{
    private final Set Âµá€;
    protected final Map Ø­áŒŠá;
    private static final String Ó = "CL_00001569";
    
    public ServersideAttributeMap() {
        this.Âµá€ = Sets.newHashSet();
        this.Ø­áŒŠá = new LowerStringMap();
    }
    
    public ModifiableAttributeInstance Ø­áŒŠá(final IAttribute p_180795_1_) {
        return (ModifiableAttributeInstance)super.HorizonCode_Horizon_È(p_180795_1_);
    }
    
    public ModifiableAttributeInstance Â(final String p_180796_1_) {
        IAttributeInstance var2 = super.HorizonCode_Horizon_È(p_180796_1_);
        if (var2 == null) {
            var2 = this.Ø­áŒŠá.get(p_180796_1_);
        }
        return (ModifiableAttributeInstance)var2;
    }
    
    @Override
    public IAttributeInstance Â(final IAttribute p_111150_1_) {
        final IAttributeInstance var2 = super.Â(p_111150_1_);
        if (p_111150_1_ instanceof RangedAttribute && ((RangedAttribute)p_111150_1_).Âµá€() != null) {
            this.Ø­áŒŠá.put(((RangedAttribute)p_111150_1_).Âµá€(), var2);
        }
        return var2;
    }
    
    @Override
    protected IAttributeInstance Ý(final IAttribute p_180376_1_) {
        return new ModifiableAttributeInstance(this, p_180376_1_);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final IAttributeInstance p_180794_1_) {
        if (p_180794_1_.HorizonCode_Horizon_È().Ý()) {
            this.Âµá€.add(p_180794_1_);
        }
        for (final IAttribute var3 : this.Ý.get((Object)p_180794_1_.HorizonCode_Horizon_È())) {
            final ModifiableAttributeInstance var4 = this.Ø­áŒŠá(var3);
            if (var4 != null) {
                var4.Ó();
            }
        }
    }
    
    public Set Â() {
        return this.Âµá€;
    }
    
    public Collection Ý() {
        final HashSet var1 = Sets.newHashSet();
        for (final IAttributeInstance var3 : this.HorizonCode_Horizon_È()) {
            if (var3.HorizonCode_Horizon_È().Ý()) {
                var1.add(var3);
            }
        }
        return var1;
    }
    
    @Override
    public IAttributeInstance HorizonCode_Horizon_È(final String p_111152_1_) {
        return this.Â(p_111152_1_);
    }
    
    @Override
    public IAttributeInstance HorizonCode_Horizon_È(final IAttribute p_111151_1_) {
        return this.Ø­áŒŠá(p_111151_1_);
    }
}
