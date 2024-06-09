package HORIZON-6-0-SKIDPROTECTION;

public class StitcherException extends RuntimeException
{
    private final Stitcher.HorizonCode_Horizon_È HorizonCode_Horizon_È;
    private static final String Â = "CL_00001057";
    
    public StitcherException(final Stitcher.HorizonCode_Horizon_È p_i2344_1_, final String p_i2344_2_) {
        super(p_i2344_2_);
        this.HorizonCode_Horizon_È = p_i2344_1_;
    }
}
