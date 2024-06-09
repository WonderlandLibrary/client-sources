package HORIZON-6-0-SKIDPROTECTION;

import java.util.Collections;
import java.util.List;

public class TextureMetadataSection implements IMetadataSection
{
    private final boolean HorizonCode_Horizon_È;
    private final boolean Â;
    private final List Ý;
    private static final String Ø­áŒŠá = "CL_00001114";
    
    public TextureMetadataSection(final boolean p_i45102_1_, final boolean p_i45102_2_, final List p_i45102_3_) {
        this.HorizonCode_Horizon_È = p_i45102_1_;
        this.Â = p_i45102_2_;
        this.Ý = p_i45102_3_;
    }
    
    public boolean HorizonCode_Horizon_È() {
        return this.HorizonCode_Horizon_È;
    }
    
    public boolean Â() {
        return this.Â;
    }
    
    public List Ý() {
        return Collections.unmodifiableList((List<?>)this.Ý);
    }
}
