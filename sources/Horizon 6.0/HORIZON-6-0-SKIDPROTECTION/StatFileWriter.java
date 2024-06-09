package HORIZON-6-0-SKIDPROTECTION;

import com.google.common.collect.Maps;
import java.util.Map;

public class StatFileWriter
{
    protected final Map HorizonCode_Horizon_È;
    private static final String Â = "CL_00001481";
    
    public StatFileWriter() {
        this.HorizonCode_Horizon_È = Maps.newConcurrentMap();
    }
    
    public boolean HorizonCode_Horizon_È(final Achievement p_77443_1_) {
        return this.HorizonCode_Horizon_È((StatBase)p_77443_1_) > 0;
    }
    
    public boolean Â(final Achievement p_77442_1_) {
        return p_77442_1_.Âµá€ == null || this.HorizonCode_Horizon_È(p_77442_1_.Âµá€);
    }
    
    public int Ý(final Achievement p_150874_1_) {
        if (this.HorizonCode_Horizon_È(p_150874_1_)) {
            return 0;
        }
        int var2 = 0;
        for (Achievement var3 = p_150874_1_.Âµá€; var3 != null && !this.HorizonCode_Horizon_È(var3); var3 = var3.Âµá€, ++var2) {}
        return var2;
    }
    
    public void HorizonCode_Horizon_È(final EntityPlayer p_150871_1_, final StatBase p_150871_2_, final int p_150871_3_) {
        if (!p_150871_2_.Ø­áŒŠá() || this.Â((Achievement)p_150871_2_)) {
            this.Â(p_150871_1_, p_150871_2_, this.HorizonCode_Horizon_È(p_150871_2_) + p_150871_3_);
        }
    }
    
    public void Â(final EntityPlayer p_150873_1_, final StatBase p_150873_2_, final int p_150873_3_) {
        TupleIntJsonSerializable var4 = this.HorizonCode_Horizon_È.get(p_150873_2_);
        if (var4 == null) {
            var4 = new TupleIntJsonSerializable();
            this.HorizonCode_Horizon_È.put(p_150873_2_, var4);
        }
        var4.HorizonCode_Horizon_È(p_150873_3_);
    }
    
    public int HorizonCode_Horizon_È(final StatBase p_77444_1_) {
        final TupleIntJsonSerializable var2 = this.HorizonCode_Horizon_È.get(p_77444_1_);
        return (var2 == null) ? 0 : var2.HorizonCode_Horizon_È();
    }
    
    public IJsonSerializable Â(final StatBase p_150870_1_) {
        final TupleIntJsonSerializable var2 = this.HorizonCode_Horizon_È.get(p_150870_1_);
        return (var2 != null) ? var2.Â() : null;
    }
    
    public IJsonSerializable HorizonCode_Horizon_È(final StatBase p_150872_1_, final IJsonSerializable p_150872_2_) {
        TupleIntJsonSerializable var3 = this.HorizonCode_Horizon_È.get(p_150872_1_);
        if (var3 == null) {
            var3 = new TupleIntJsonSerializable();
            this.HorizonCode_Horizon_È.put(p_150872_1_, var3);
        }
        var3.HorizonCode_Horizon_È(p_150872_2_);
        return p_150872_2_;
    }
}
