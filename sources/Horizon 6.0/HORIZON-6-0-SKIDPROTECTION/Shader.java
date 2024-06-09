package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import com.google.common.collect.Lists;
import javax.vecmath.Matrix4f;
import java.util.List;

public class Shader
{
    private final ShaderManager Ý;
    public final Framebuffer HorizonCode_Horizon_È;
    public final Framebuffer Â;
    private final List Ø­áŒŠá;
    private final List Âµá€;
    private final List Ó;
    private final List à;
    private Matrix4f Ø;
    private static final String áŒŠÆ = "CL_00001042";
    
    public Shader(final IResourceManager p_i45089_1_, final String p_i45089_2_, final Framebuffer p_i45089_3_, final Framebuffer p_i45089_4_) throws JsonException {
        this.Ø­áŒŠá = Lists.newArrayList();
        this.Âµá€ = Lists.newArrayList();
        this.Ó = Lists.newArrayList();
        this.à = Lists.newArrayList();
        this.Ý = new ShaderManager(p_i45089_1_, p_i45089_2_);
        this.HorizonCode_Horizon_È = p_i45089_3_;
        this.Â = p_i45089_4_;
    }
    
    public void HorizonCode_Horizon_È() {
        this.Ý.HorizonCode_Horizon_È();
    }
    
    public void HorizonCode_Horizon_È(final String p_148041_1_, final Object p_148041_2_, final int p_148041_3_, final int p_148041_4_) {
        this.Âµá€.add(this.Âµá€.size(), p_148041_1_);
        this.Ø­áŒŠá.add(this.Ø­áŒŠá.size(), p_148041_2_);
        this.Ó.add(this.Ó.size(), p_148041_3_);
        this.à.add(this.à.size(), p_148041_4_);
    }
    
    private void Ý() {
        GlStateManager.Ý(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.ÂµÈ();
        GlStateManager.áŒŠÆ();
        GlStateManager.Ý();
        GlStateManager.£á();
        GlStateManager.Ó();
        GlStateManager.Ø();
        GlStateManager.µÕ();
        GlStateManager.áŒŠÆ(0);
    }
    
    public void HorizonCode_Horizon_È(final Matrix4f p_148045_1_) {
        this.Ø = p_148045_1_;
    }
    
    public void HorizonCode_Horizon_È(final float p_148042_1_) {
        this.Ý();
        this.HorizonCode_Horizon_È.Âµá€();
        final float var2 = this.Â.HorizonCode_Horizon_È;
        final float var3 = this.Â.Â;
        GlStateManager.Â(0, 0, (int)var2, (int)var3);
        this.Ý.HorizonCode_Horizon_È("DiffuseSampler", this.HorizonCode_Horizon_È);
        for (int var4 = 0; var4 < this.Ø­áŒŠá.size(); ++var4) {
            this.Ý.HorizonCode_Horizon_È(this.Âµá€.get(var4), this.Ø­áŒŠá.get(var4));
            this.Ý.Â("AuxSize" + var4).HorizonCode_Horizon_È(this.Ó.get(var4), this.à.get(var4));
        }
        this.Ý.Â("ProjMat").HorizonCode_Horizon_È(this.Ø);
        this.Ý.Â("InSize").HorizonCode_Horizon_È(this.HorizonCode_Horizon_È.HorizonCode_Horizon_È, this.HorizonCode_Horizon_È.Â);
        this.Ý.Â("OutSize").HorizonCode_Horizon_È(var2, var3);
        this.Ý.Â("Time").HorizonCode_Horizon_È(p_148042_1_);
        final Minecraft var5 = Minecraft.áŒŠà();
        this.Ý.Â("ScreenSize").HorizonCode_Horizon_È(var5.Ó, var5.à);
        this.Ý.Ý();
        this.Â.Ó();
        this.Â.HorizonCode_Horizon_È(false);
        GlStateManager.HorizonCode_Horizon_È(false);
        GlStateManager.HorizonCode_Horizon_È(true, true, true, true);
        final Tessellator var6 = Tessellator.HorizonCode_Horizon_È();
        final WorldRenderer var7 = var6.Ý();
        var7.Â();
        var7.Ý(-1);
        var7.Â(0.0, var3, 500.0);
        var7.Â(var2, var3, 500.0);
        var7.Â(var2, 0.0, 500.0);
        var7.Â(0.0, 0.0, 500.0);
        var6.Â();
        GlStateManager.HorizonCode_Horizon_È(true);
        GlStateManager.HorizonCode_Horizon_È(true, true, true, true);
        this.Ý.Â();
        this.Â.Âµá€();
        this.HorizonCode_Horizon_È.Ø­áŒŠá();
        for (final Object var9 : this.Ø­áŒŠá) {
            if (var9 instanceof Framebuffer) {
                ((Framebuffer)var9).Ø­áŒŠá();
            }
        }
    }
    
    public ShaderManager Â() {
        return this.Ý;
    }
}
