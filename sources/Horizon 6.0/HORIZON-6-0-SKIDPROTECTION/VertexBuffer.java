package HORIZON-6-0-SKIDPROTECTION;

import org.lwjgl.opengl.GL11;
import java.nio.ByteBuffer;

public class VertexBuffer
{
    private int HorizonCode_Horizon_È;
    private final VertexFormat Â;
    private int Ý;
    private static final String Ø­áŒŠá = "CL_00002402";
    
    public VertexBuffer(final VertexFormat p_i46098_1_) {
        this.Â = p_i46098_1_;
        this.HorizonCode_Horizon_È = OpenGlHelper.Âµá€();
    }
    
    public void HorizonCode_Horizon_È() {
        OpenGlHelper.à(OpenGlHelper.Ç, this.HorizonCode_Horizon_È);
    }
    
    public void HorizonCode_Horizon_È(final ByteBuffer p_177360_1_, final int p_177360_2_) {
        this.HorizonCode_Horizon_È();
        OpenGlHelper.HorizonCode_Horizon_È(OpenGlHelper.Ç, p_177360_1_, 35044);
        this.Â();
        this.Ý = p_177360_2_ / this.Â.Ó();
    }
    
    public void HorizonCode_Horizon_È(final int p_177358_1_) {
        GL11.glDrawArrays(p_177358_1_, 0, this.Ý);
    }
    
    public void Â() {
        OpenGlHelper.à(OpenGlHelper.Ç, 0);
    }
    
    public void Ý() {
        if (this.HorizonCode_Horizon_È >= 0) {
            OpenGlHelper.à(this.HorizonCode_Horizon_È);
            this.HorizonCode_Horizon_È = -1;
        }
    }
}
