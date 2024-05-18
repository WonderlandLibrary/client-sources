package HORIZON-6-0-SKIDPROTECTION;

import org.lwjgl.opengl.GL11;

public abstract class AbstractTexture implements ITextureObject
{
    protected int HorizonCode_Horizon_È;
    protected boolean Â;
    protected boolean Ý;
    protected boolean Ø­áŒŠá;
    protected boolean Âµá€;
    private static final String Ó = "CL_00001047";
    
    public AbstractTexture() {
        this.HorizonCode_Horizon_È = -1;
    }
    
    public void HorizonCode_Horizon_È(final boolean p_174937_1_, final boolean p_174937_2_) {
        this.Â = p_174937_1_;
        this.Ý = p_174937_2_;
        final boolean var3 = true;
        final boolean var4 = true;
        int var5;
        short var6;
        if (p_174937_1_) {
            var5 = (p_174937_2_ ? 9987 : 9729);
            var6 = 9729;
        }
        else {
            var5 = (p_174937_2_ ? 9986 : 9728);
            var6 = 9728;
        }
        GL11.glTexParameteri(3553, 10241, var5);
        GL11.glTexParameteri(3553, 10240, (int)var6);
    }
    
    @Override
    public void Â(final boolean p_174936_1_, final boolean p_174936_2_) {
        this.Ø­áŒŠá = this.Â;
        this.Âµá€ = this.Ý;
        this.HorizonCode_Horizon_È(p_174936_1_, p_174936_2_);
    }
    
    @Override
    public void Ø­áŒŠá() {
        this.HorizonCode_Horizon_È(this.Ø­áŒŠá, this.Âµá€);
    }
    
    @Override
    public int HorizonCode_Horizon_È() {
        if (this.HorizonCode_Horizon_È == -1) {
            this.HorizonCode_Horizon_È = TextureUtil.HorizonCode_Horizon_È();
        }
        return this.HorizonCode_Horizon_È;
    }
    
    public void Âµá€() {
        if (this.HorizonCode_Horizon_È != -1) {
            TextureUtil.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È);
            this.HorizonCode_Horizon_È = -1;
        }
    }
}
