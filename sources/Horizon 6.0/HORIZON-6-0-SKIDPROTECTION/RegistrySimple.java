package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import java.util.Collections;
import java.util.Set;
import org.apache.commons.lang3.Validate;
import com.google.common.collect.Maps;
import org.apache.logging.log4j.LogManager;
import java.util.Map;
import org.apache.logging.log4j.Logger;

public class RegistrySimple implements IRegistry
{
    private static final Logger HorizonCode_Horizon_È;
    protected final Map Ý;
    private static final String Â = "CL_00001210";
    
    static {
        HorizonCode_Horizon_È = LogManager.getLogger();
    }
    
    public RegistrySimple() {
        this.Ý = this.HorizonCode_Horizon_È();
    }
    
    protected Map HorizonCode_Horizon_È() {
        return Maps.newHashMap();
    }
    
    @Override
    public Object HorizonCode_Horizon_È(final Object p_82594_1_) {
        return this.Ý.get(p_82594_1_);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final Object p_82595_1_, final Object p_82595_2_) {
        Validate.notNull(p_82595_1_);
        Validate.notNull(p_82595_2_);
        if (this.Ý.containsKey(p_82595_1_)) {
            RegistrySimple.HorizonCode_Horizon_È.debug("Adding duplicate key '" + p_82595_1_ + "' to registry");
        }
        this.Ý.put(p_82595_1_, p_82595_2_);
    }
    
    public Set Ý() {
        return Collections.unmodifiableSet(this.Ý.keySet());
    }
    
    public boolean Ý(final Object p_148741_1_) {
        return this.Ý.containsKey(p_148741_1_);
    }
    
    @Override
    public Iterator iterator() {
        return this.Ý.values().iterator();
    }
}
