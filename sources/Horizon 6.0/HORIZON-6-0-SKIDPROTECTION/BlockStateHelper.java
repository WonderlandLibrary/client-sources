package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import com.google.common.collect.Maps;
import java.util.Map;
import com.google.common.base.Predicate;

public class BlockStateHelper implements Predicate
{
    private final BlockState HorizonCode_Horizon_È;
    private final Map Â;
    private static final String Ý = "CL_00002019";
    
    private BlockStateHelper(final BlockState p_i45653_1_) {
        this.Â = Maps.newHashMap();
        this.HorizonCode_Horizon_È = p_i45653_1_;
    }
    
    public static BlockStateHelper HorizonCode_Horizon_È(final Block p_177638_0_) {
        return new BlockStateHelper(p_177638_0_.ŠÂµà());
    }
    
    public boolean HorizonCode_Horizon_È(final IBlockState p_177639_1_) {
        if (p_177639_1_ != null && p_177639_1_.Ý().equals(this.HorizonCode_Horizon_È.Ý())) {
            for (final Map.Entry var3 : this.Â.entrySet()) {
                final Comparable var4 = p_177639_1_.HorizonCode_Horizon_È(var3.getKey());
                if (!var3.getValue().apply((Object)var4)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
    
    public BlockStateHelper HorizonCode_Horizon_È(final IProperty p_177637_1_, final Predicate p_177637_2_) {
        if (!this.HorizonCode_Horizon_È.Ø­áŒŠá().contains(p_177637_1_)) {
            throw new IllegalArgumentException(this.HorizonCode_Horizon_È + " cannot support property " + p_177637_1_);
        }
        this.Â.put(p_177637_1_, p_177637_2_);
        return this;
    }
    
    public boolean apply(final Object p_apply_1_) {
        return this.HorizonCode_Horizon_È((IBlockState)p_apply_1_);
    }
}
