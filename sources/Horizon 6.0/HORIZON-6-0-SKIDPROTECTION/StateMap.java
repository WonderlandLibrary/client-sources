package HORIZON-6-0-SKIDPROTECTION;

import java.util.Collection;
import java.util.Collections;
import com.google.common.collect.Lists;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import com.google.common.collect.Maps;
import java.util.List;

public class StateMap extends StateMapperBase
{
    private final IProperty HorizonCode_Horizon_È;
    private final String Ý;
    private final List Ø­áŒŠá;
    private static final String Âµá€ = "CL_00002476";
    
    private StateMap(final IProperty p_i46210_1_, final String p_i46210_2_, final List p_i46210_3_) {
        this.HorizonCode_Horizon_È = p_i46210_1_;
        this.Ý = p_i46210_2_;
        this.Ø­áŒŠá = p_i46210_3_;
    }
    
    @Override
    protected ModelResourceLocation HorizonCode_Horizon_È(final IBlockState p_178132_1_) {
        final LinkedHashMap var2 = Maps.newLinkedHashMap((Map)p_178132_1_.Â());
        String var3;
        if (this.HorizonCode_Horizon_È == null) {
            var3 = ((ResourceLocation_1975012498)Block.HorizonCode_Horizon_È.Â(p_178132_1_.Ý())).toString();
        }
        else {
            var3 = this.HorizonCode_Horizon_È.HorizonCode_Horizon_È((Comparable)var2.remove(this.HorizonCode_Horizon_È));
        }
        if (this.Ý != null) {
            var3 = String.valueOf(var3) + this.Ý;
        }
        for (final IProperty var5 : this.Ø­áŒŠá) {
            var2.remove(var5);
        }
        return new ModelResourceLocation(var3, this.HorizonCode_Horizon_È(var2));
    }
    
    StateMap(final IProperty p_i46211_1_, final String p_i46211_2_, final List p_i46211_3_, final Object p_i46211_4_) {
        this(p_i46211_1_, p_i46211_2_, p_i46211_3_);
    }
    
    public static class HorizonCode_Horizon_È
    {
        private IProperty HorizonCode_Horizon_È;
        private String Â;
        private final List Ý;
        private static final String Ø­áŒŠá = "CL_00002474";
        
        public HorizonCode_Horizon_È() {
            this.Ý = Lists.newArrayList();
        }
        
        public HorizonCode_Horizon_È HorizonCode_Horizon_È(final IProperty p_178440_1_) {
            this.HorizonCode_Horizon_È = p_178440_1_;
            return this;
        }
        
        public HorizonCode_Horizon_È HorizonCode_Horizon_È(final String p_178439_1_) {
            this.Â = p_178439_1_;
            return this;
        }
        
        public HorizonCode_Horizon_È HorizonCode_Horizon_È(final IProperty... p_178442_1_) {
            Collections.addAll(this.Ý, p_178442_1_);
            return this;
        }
        
        public StateMap HorizonCode_Horizon_È() {
            return new StateMap(this.HorizonCode_Horizon_È, this.Â, this.Ý, null);
        }
    }
}
