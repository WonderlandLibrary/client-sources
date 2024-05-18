package HORIZON-6-0-SKIDPROTECTION;

public class Tessellator
{
    private WorldRenderer Â;
    private WorldVertexBufferUploader Ý;
    public static final Tessellator HorizonCode_Horizon_È;
    private static final String Ø­áŒŠá = "CL_00000960";
    
    static {
        HorizonCode_Horizon_È = new Tessellator(2097152);
    }
    
    public static Tessellator HorizonCode_Horizon_È() {
        return Tessellator.HorizonCode_Horizon_È;
    }
    
    public Tessellator(final int p_i1250_1_) {
        this.Ý = new WorldVertexBufferUploader();
        this.Â = new WorldRenderer(p_i1250_1_);
    }
    
    public int Â() {
        return this.Ý.HorizonCode_Horizon_È(this.Â, this.Â.Ø­áŒŠá());
    }
    
    public WorldRenderer Ý() {
        return this.Â;
    }
}
