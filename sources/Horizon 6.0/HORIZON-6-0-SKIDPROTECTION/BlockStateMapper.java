package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import java.util.IdentityHashMap;
import com.google.common.base.Objects;
import java.util.Collection;
import java.util.Collections;
import com.google.common.collect.Sets;
import com.google.common.collect.Maps;
import java.util.Set;
import java.util.Map;

public class BlockStateMapper
{
    private Map HorizonCode_Horizon_È;
    private Set Â;
    private static final String Ý = "CL_00002478";
    
    public BlockStateMapper() {
        this.HorizonCode_Horizon_È = Maps.newIdentityHashMap();
        this.Â = Sets.newIdentityHashSet();
    }
    
    public void HorizonCode_Horizon_È(final Block p_178447_1_, final IStateMapper p_178447_2_) {
        this.HorizonCode_Horizon_È.put(p_178447_1_, p_178447_2_);
    }
    
    public void HorizonCode_Horizon_È(final Block... p_178448_1_) {
        Collections.addAll(this.Â, p_178448_1_);
    }
    
    public Map HorizonCode_Horizon_È() {
        final IdentityHashMap var1 = Maps.newIdentityHashMap();
        for (final Block var3 : Block.HorizonCode_Horizon_È) {
            if (!this.Â.contains(var3)) {
                var1.putAll(((IStateMapper)Objects.firstNonNull(this.HorizonCode_Horizon_È.get(var3), (Object)new DefaultStateMapper())).HorizonCode_Horizon_È(var3));
            }
        }
        return var1;
    }
}
