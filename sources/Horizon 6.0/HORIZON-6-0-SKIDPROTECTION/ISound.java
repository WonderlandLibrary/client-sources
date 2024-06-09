package HORIZON-6-0-SKIDPROTECTION;

public interface ISound
{
    ResourceLocation_1975012498 Â();
    
    boolean Ý();
    
    int Ø­áŒŠá();
    
    float Âµá€();
    
    float Ó();
    
    float à();
    
    float Ø();
    
    float áŒŠÆ();
    
    HorizonCode_Horizon_È áˆºÑ¢Õ();
    
    public enum HorizonCode_Horizon_È
    {
        HorizonCode_Horizon_È("NONE", 0, "NONE", 0, 0), 
        Â("LINEAR", 1, "LINEAR", 1, 2);
        
        private final int Ý;
        private static final HorizonCode_Horizon_È[] Ø­áŒŠá;
        private static final String Âµá€ = "CL_00001126";
        
        static {
            Ó = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â };
            Ø­áŒŠá = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â };
        }
        
        private HorizonCode_Horizon_È(final String s, final int n, final String p_i45110_1_, final int p_i45110_2_, final int p_i45110_3_) {
            this.Ý = p_i45110_3_;
        }
        
        public int HorizonCode_Horizon_È() {
            return this.Ý;
        }
    }
}
