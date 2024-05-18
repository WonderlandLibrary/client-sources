package HORIZON-6-0-SKIDPROTECTION;

import java.nio.ByteBuffer;
import org.lwjgl.opengl.GL11;

public class Framebuffer
{
    public int HorizonCode_Horizon_È;
    public int Â;
    public int Ý;
    public int Ø­áŒŠá;
    public boolean Âµá€;
    public int Ó;
    public int à;
    public int Ø;
    public float[] áŒŠÆ;
    public int áˆºÑ¢Õ;
    private static final String ÂµÈ = "CL_00000959";
    
    public Framebuffer(final int p_i45078_1_, final int p_i45078_2_, final boolean p_i45078_3_) {
        this.Âµá€ = p_i45078_3_;
        this.Ó = -1;
        this.à = -1;
        this.Ø = -1;
        (this.áŒŠÆ = new float[4])[0] = 1.0f;
        this.áŒŠÆ[1] = 1.0f;
        this.áŒŠÆ[2] = 1.0f;
        this.áŒŠÆ[3] = 0.0f;
        this.HorizonCode_Horizon_È(p_i45078_1_, p_i45078_2_);
    }
    
    public void HorizonCode_Horizon_È(final int p_147613_1_, final int p_147613_2_) {
        if (!OpenGlHelper.áŒŠÆ()) {
            this.Ý = p_147613_1_;
            this.Ø­áŒŠá = p_147613_2_;
        }
        else {
            GlStateManager.áˆºÑ¢Õ();
            if (this.Ó >= 0) {
                this.HorizonCode_Horizon_È();
            }
            this.Â(p_147613_1_, p_147613_2_);
            this.Â();
            OpenGlHelper.Ø(OpenGlHelper.Â, 0);
        }
    }
    
    public void HorizonCode_Horizon_È() {
        if (OpenGlHelper.áŒŠÆ()) {
            this.Ø­áŒŠá();
            this.Âµá€();
            if (this.Ø > -1) {
                OpenGlHelper.Ø(this.Ø);
                this.Ø = -1;
            }
            if (this.à > -1) {
                TextureUtil.HorizonCode_Horizon_È(this.à);
                this.à = -1;
            }
            if (this.Ó > -1) {
                OpenGlHelper.Ø(OpenGlHelper.Â, 0);
                OpenGlHelper.áŒŠÆ(this.Ó);
                this.Ó = -1;
            }
        }
    }
    
    public void Â(final int p_147605_1_, final int p_147605_2_) {
        this.Ý = p_147605_1_;
        this.Ø­áŒŠá = p_147605_2_;
        this.HorizonCode_Horizon_È = p_147605_1_;
        this.Â = p_147605_2_;
        if (!OpenGlHelper.áŒŠÆ()) {
            this.Ó();
        }
        else {
            this.Ó = OpenGlHelper.à();
            this.à = TextureUtil.HorizonCode_Horizon_È();
            if (this.Âµá€) {
                this.Ø = OpenGlHelper.Ø();
            }
            this.HorizonCode_Horizon_È(9728);
            GlStateManager.áŒŠÆ(this.à);
            GL11.glTexImage2D(3553, 0, 32856, this.HorizonCode_Horizon_È, this.Â, 0, 6408, 5121, (ByteBuffer)null);
            OpenGlHelper.Ø(OpenGlHelper.Â, this.Ó);
            OpenGlHelper.HorizonCode_Horizon_È(OpenGlHelper.Â, OpenGlHelper.Ø­áŒŠá, 3553, this.à, 0);
            if (this.Âµá€) {
                OpenGlHelper.áŒŠÆ(OpenGlHelper.Ý, this.Ø);
                OpenGlHelper.HorizonCode_Horizon_È(OpenGlHelper.Ý, 33190, this.HorizonCode_Horizon_È, this.Â);
                OpenGlHelper.Â(OpenGlHelper.Â, OpenGlHelper.Âµá€, OpenGlHelper.Ý, this.Ø);
            }
            this.Ó();
            this.Ø­áŒŠá();
        }
    }
    
    public void HorizonCode_Horizon_È(final int p_147607_1_) {
        if (OpenGlHelper.áŒŠÆ()) {
            this.áˆºÑ¢Õ = p_147607_1_;
            GlStateManager.áŒŠÆ(this.à);
            GL11.glTexParameterf(3553, 10241, (float)p_147607_1_);
            GL11.glTexParameterf(3553, 10240, (float)p_147607_1_);
            GL11.glTexParameterf(3553, 10242, 10496.0f);
            GL11.glTexParameterf(3553, 10243, 10496.0f);
            GlStateManager.áŒŠÆ(0);
        }
    }
    
    public void Â() {
        final int var1 = OpenGlHelper.áˆºÑ¢Õ(OpenGlHelper.Â);
        if (var1 == OpenGlHelper.Ó) {
            return;
        }
        if (var1 == OpenGlHelper.à) {
            throw new RuntimeException("GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT");
        }
        if (var1 == OpenGlHelper.Ø) {
            throw new RuntimeException("GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT");
        }
        if (var1 == OpenGlHelper.áŒŠÆ) {
            throw new RuntimeException("GL_FRAMEBUFFER_INCOMPLETE_DRAW_BUFFER");
        }
        if (var1 == OpenGlHelper.áˆºÑ¢Õ) {
            throw new RuntimeException("GL_FRAMEBUFFER_INCOMPLETE_READ_BUFFER");
        }
        throw new RuntimeException("glCheckFramebufferStatus returned unknown status:" + var1);
    }
    
    public void Ý() {
        if (OpenGlHelper.áŒŠÆ()) {
            GlStateManager.áŒŠÆ(this.à);
        }
    }
    
    public void Ø­áŒŠá() {
        if (OpenGlHelper.áŒŠÆ()) {
            GlStateManager.áŒŠÆ(0);
        }
    }
    
    public void HorizonCode_Horizon_È(final boolean p_147610_1_) {
        if (OpenGlHelper.áŒŠÆ()) {
            OpenGlHelper.Ø(OpenGlHelper.Â, this.Ó);
            if (p_147610_1_) {
                GlStateManager.Â(0, 0, this.Ý, this.Ø­áŒŠá);
            }
        }
    }
    
    public void Âµá€() {
        if (OpenGlHelper.áŒŠÆ()) {
            OpenGlHelper.Ø(OpenGlHelper.Â, 0);
        }
    }
    
    public void HorizonCode_Horizon_È(final float p_147604_1_, final float p_147604_2_, final float p_147604_3_, final float p_147604_4_) {
        this.áŒŠÆ[0] = p_147604_1_;
        this.áŒŠÆ[1] = p_147604_2_;
        this.áŒŠÆ[2] = p_147604_3_;
        this.áŒŠÆ[3] = p_147604_4_;
    }
    
    public void Ý(final int p_147615_1_, final int p_147615_2_) {
        this.HorizonCode_Horizon_È(p_147615_1_, p_147615_2_, true);
    }
    
    public void HorizonCode_Horizon_È(final int p_178038_1_, final int p_178038_2_, final boolean p_178038_3_) {
        if (OpenGlHelper.áŒŠÆ()) {
            GlStateManager.HorizonCode_Horizon_È(true, true, true, false);
            GlStateManager.áŒŠÆ();
            GlStateManager.HorizonCode_Horizon_È(false);
            GlStateManager.á(5889);
            GlStateManager.ŒÏ();
            GlStateManager.HorizonCode_Horizon_È(0.0, p_178038_1_, p_178038_2_, 0.0, 1000.0, 3000.0);
            GlStateManager.á(5888);
            GlStateManager.ŒÏ();
            GlStateManager.Â(0.0f, 0.0f, -2000.0f);
            GlStateManager.Â(0, 0, p_178038_1_, p_178038_2_);
            GlStateManager.µÕ();
            GlStateManager.Ó();
            GlStateManager.Ý();
            if (p_178038_3_) {
                GlStateManager.ÂµÈ();
                GlStateManager.à();
            }
            GlStateManager.Ý(1.0f, 1.0f, 1.0f, 1.0f);
            this.Ý();
            final float var4 = p_178038_1_;
            final float var5 = p_178038_2_;
            final float var6 = this.Ý / this.HorizonCode_Horizon_È;
            final float var7 = this.Ø­áŒŠá / this.Â;
            final Tessellator var8 = Tessellator.HorizonCode_Horizon_È();
            final WorldRenderer var9 = var8.Ý();
            var9.Â();
            var9.Ý(-1);
            var9.HorizonCode_Horizon_È(0.0, var5, 0.0, 0.0, 0.0);
            var9.HorizonCode_Horizon_È(var4, var5, 0.0, var6, 0.0);
            var9.HorizonCode_Horizon_È(var4, 0.0, 0.0, var6, var7);
            var9.HorizonCode_Horizon_È(0.0, 0.0, 0.0, 0.0, var7);
            var8.Â();
            this.Ø­áŒŠá();
            GlStateManager.HorizonCode_Horizon_È(true);
            GlStateManager.HorizonCode_Horizon_È(true, true, true, true);
        }
    }
    
    public void Ó() {
        this.HorizonCode_Horizon_È(true);
        GlStateManager.HorizonCode_Horizon_È(this.áŒŠÆ[0], this.áŒŠÆ[1], this.áŒŠÆ[2], this.áŒŠÆ[3]);
        int var1 = 16384;
        if (this.Âµá€) {
            GlStateManager.HorizonCode_Horizon_È(1.0);
            var1 |= 0x100;
        }
        GlStateManager.ÂµÈ(var1);
        this.Âµá€();
    }
}
