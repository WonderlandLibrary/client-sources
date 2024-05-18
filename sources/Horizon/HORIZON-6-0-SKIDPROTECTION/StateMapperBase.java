package HORIZON-6-0-SKIDPROTECTION;

import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Iterator;
import com.google.common.collect.Maps;
import java.util.Map;

public abstract class StateMapperBase implements IStateMapper
{
    protected Map Â;
    private static final String HorizonCode_Horizon_È = "CL_00002479";
    
    public StateMapperBase() {
        this.Â = Maps.newLinkedHashMap();
    }
    
    public String HorizonCode_Horizon_È(final Map p_178131_1_) {
        final StringBuilder var2 = new StringBuilder();
        for (final Map.Entry var4 : p_178131_1_.entrySet()) {
            if (var2.length() != 0) {
                var2.append(",");
            }
            final IProperty var5 = var4.getKey();
            final Comparable var6 = var4.getValue();
            var2.append(var5.HorizonCode_Horizon_È());
            var2.append("=");
            var2.append(var5.HorizonCode_Horizon_È(var6));
        }
        if (var2.length() == 0) {
            var2.append("normal");
        }
        return var2.toString();
    }
    
    @Override
    public Map HorizonCode_Horizon_È(final Block p_178130_1_) {
        for (final IBlockState var3 : p_178130_1_.ŠÂµà().HorizonCode_Horizon_È()) {
            this.Â.put(var3, this.HorizonCode_Horizon_È(var3));
        }
        return this.Â;
    }
    
    protected abstract ModelResourceLocation HorizonCode_Horizon_È(final IBlockState p0);
}
