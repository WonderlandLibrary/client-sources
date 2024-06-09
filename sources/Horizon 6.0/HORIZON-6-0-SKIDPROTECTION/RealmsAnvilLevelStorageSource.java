package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import java.util.ArrayList;
import com.google.common.collect.Lists;
import java.util.List;

public class RealmsAnvilLevelStorageSource
{
    private ISaveFormat HorizonCode_Horizon_È;
    private static final String Â = "CL_00001856";
    
    public RealmsAnvilLevelStorageSource(final ISaveFormat p_i1106_1_) {
        this.HorizonCode_Horizon_È = p_i1106_1_;
    }
    
    public String HorizonCode_Horizon_È() {
        return this.HorizonCode_Horizon_È.HorizonCode_Horizon_È();
    }
    
    public boolean HorizonCode_Horizon_È(final String p_levelExists_1_) {
        return this.HorizonCode_Horizon_È.Ó(p_levelExists_1_);
    }
    
    public boolean HorizonCode_Horizon_È(final String p_convertLevel_1_, final IProgressUpdate p_convertLevel_2_) {
        return this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(p_convertLevel_1_, p_convertLevel_2_);
    }
    
    public boolean Â(final String p_requiresConversion_1_) {
        return this.HorizonCode_Horizon_È.Â(p_requiresConversion_1_);
    }
    
    public boolean Ý(final String p_isNewLevelIdAcceptable_1_) {
        return this.HorizonCode_Horizon_È.Ø­áŒŠá(p_isNewLevelIdAcceptable_1_);
    }
    
    public boolean Ø­áŒŠá(final String p_deleteLevel_1_) {
        return this.HorizonCode_Horizon_È.Âµá€(p_deleteLevel_1_);
    }
    
    public boolean Âµá€(final String p_isConvertible_1_) {
        return this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(p_isConvertible_1_);
    }
    
    public void HorizonCode_Horizon_È(final String p_renameLevel_1_, final String p_renameLevel_2_) {
        this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(p_renameLevel_1_, p_renameLevel_2_);
    }
    
    public void Â() {
        this.HorizonCode_Horizon_È.Ø­áŒŠá();
    }
    
    public List Ý() throws AnvilConverterException {
        final ArrayList var1 = Lists.newArrayList();
        for (final SaveFormatComparator var3 : this.HorizonCode_Horizon_È.Â()) {
            var1.add(new RealmsLevelSummary(var3));
        }
        return var1;
    }
}
