package HORIZON-6-0-SKIDPROTECTION;

public class NibbleArrayReader
{
    public final byte[] HorizonCode_Horizon_È;
    private final int Â;
    private final int Ý;
    private static final String Ø­áŒŠá = "CL_00000376";
    
    public NibbleArrayReader(final byte[] dataIn, final int depthBitsIn) {
        this.HorizonCode_Horizon_È = dataIn;
        this.Â = depthBitsIn;
        this.Ý = depthBitsIn + 4;
    }
    
    public int HorizonCode_Horizon_È(final int p_76686_1_, final int p_76686_2_, final int p_76686_3_) {
        final int var4 = p_76686_1_ << this.Ý | p_76686_3_ << this.Â | p_76686_2_;
        final int var5 = var4 >> 1;
        final int var6 = var4 & 0x1;
        return (var6 == 0) ? (this.HorizonCode_Horizon_È[var5] & 0xF) : (this.HorizonCode_Horizon_È[var5] >> 4 & 0xF);
    }
}
