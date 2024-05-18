package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.BiMap;
import java.util.Map;

public class RegistryNamespaced extends RegistrySimple implements IObjectIntIterable
{
    protected final ObjectIntIdentityMap HorizonCode_Horizon_È;
    protected final Map Â;
    private static final String Ø­áŒŠá = "CL_00001206";
    
    public RegistryNamespaced() {
        this.HorizonCode_Horizon_È = new ObjectIntIdentityMap();
        this.Â = (Map)((BiMap)this.Ý).inverse();
    }
    
    public void HorizonCode_Horizon_È(final int p_177775_1_, final Object p_177775_2_, final Object p_177775_3_) {
        this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(p_177775_3_, p_177775_1_);
        this.HorizonCode_Horizon_È(p_177775_2_, p_177775_3_);
    }
    
    @Override
    protected Map HorizonCode_Horizon_È() {
        return (Map)HashBiMap.create();
    }
    
    @Override
    public Object HorizonCode_Horizon_È(final Object p_82594_1_) {
        return super.HorizonCode_Horizon_È(p_82594_1_);
    }
    
    public Object Â(final Object p_177774_1_) {
        return this.Â.get(p_177774_1_);
    }
    
    @Override
    public boolean Ý(final Object p_148741_1_) {
        return super.Ý(p_148741_1_);
    }
    
    public int Ø­áŒŠá(final Object p_148757_1_) {
        return this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(p_148757_1_);
    }
    
    public Object HorizonCode_Horizon_È(final int p_148754_1_) {
        return this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(p_148754_1_);
    }
    
    @Override
    public Iterator iterator() {
        return this.HorizonCode_Horizon_È.iterator();
    }
}
