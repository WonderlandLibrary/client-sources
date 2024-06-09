package HORIZON-6-0-SKIDPROTECTION;

public class LoadingScreenRenderer implements IProgressUpdate
{
    private String HorizonCode_Horizon_È;
    private Minecraft Â;
    private String Ý;
    private long Ø­áŒŠá;
    private boolean Âµá€;
    private ScaledResolution Ó;
    private Framebuffer à;
    private static final String Ø = "CL_00000655";
    
    public LoadingScreenRenderer(final Minecraft mcIn) {
        this.HorizonCode_Horizon_È = "";
        this.Ý = "";
        this.Ø­áŒŠá = Minecraft.áƒ();
        this.Â = mcIn;
        this.Ó = new ScaledResolution(mcIn, mcIn.Ó, mcIn.à);
        (this.à = new Framebuffer(mcIn.Ó, mcIn.à, false)).HorizonCode_Horizon_È(9728);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final String p_73721_1_) {
        this.Âµá€ = false;
        this.Ø­áŒŠá(p_73721_1_);
    }
    
    @Override
    public void Â(final String message) {
        this.Âµá€ = true;
        this.Ø­áŒŠá(message);
    }
    
    private void Ø­áŒŠá(final String p_73722_1_) {
        this.Ý = p_73722_1_;
        if (!this.Â.áƒ) {
            if (!this.Âµá€) {
                throw new MinecraftError();
            }
        }
        else {
            GlStateManager.ÂµÈ(256);
            GlStateManager.á(5889);
            GlStateManager.ŒÏ();
            if (OpenGlHelper.áŒŠÆ()) {
                final int var2 = this.Ó.Âµá€();
                GlStateManager.HorizonCode_Horizon_È(0.0, this.Ó.HorizonCode_Horizon_È() * var2, this.Ó.Â() * var2, 0.0, 100.0, 300.0);
            }
            else {
                final ScaledResolution var3 = new ScaledResolution(this.Â, this.Â.Ó, this.Â.à);
                GlStateManager.HorizonCode_Horizon_È(0.0, var3.Ý(), var3.Ø­áŒŠá(), 0.0, 100.0, 300.0);
            }
            GlStateManager.á(5888);
            GlStateManager.ŒÏ();
            GlStateManager.Â(0.0f, 0.0f, -200.0f);
        }
    }
    
    @Override
    public void Ý(final String message) {
        if (!this.Â.áƒ) {
            if (!this.Âµá€) {
                throw new MinecraftError();
            }
        }
        else {
            this.Ø­áŒŠá = 0L;
            this.HorizonCode_Horizon_È = message;
            this.HorizonCode_Horizon_È(-1);
            this.Ø­áŒŠá = 0L;
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int progress) {
        if (!this.Â.áƒ) {
            if (!this.Âµá€) {
                throw new MinecraftError();
            }
        }
        else {
            final long var2 = Minecraft.áƒ();
            if (var2 - this.Ø­áŒŠá >= 100L) {
                this.Ø­áŒŠá = var2;
                final ScaledResolution var3 = new ScaledResolution(this.Â, this.Â.Ó, this.Â.à);
                final int var4 = var3.Âµá€();
                final int var5 = var3.HorizonCode_Horizon_È();
                final int var6 = var3.Â();
                if (OpenGlHelper.áŒŠÆ()) {
                    this.à.Ó();
                }
                else {
                    GlStateManager.ÂµÈ(256);
                }
                this.à.HorizonCode_Horizon_È(false);
                GlStateManager.á(5889);
                GlStateManager.ŒÏ();
                GlStateManager.HorizonCode_Horizon_È(0.0, var3.Ý(), var3.Ø­áŒŠá(), 0.0, 100.0, 300.0);
                GlStateManager.á(5888);
                GlStateManager.ŒÏ();
                GlStateManager.Â(0.0f, 0.0f, -200.0f);
                if (!OpenGlHelper.áŒŠÆ()) {
                    GlStateManager.ÂµÈ(16640);
                }
                final Tessellator var7 = Tessellator.HorizonCode_Horizon_È();
                final WorldRenderer var8 = var7.Ý();
                this.Â.¥à().HorizonCode_Horizon_È(Gui_1808253012.Šáƒ);
                final float var9 = 32.0f;
                var8.Â();
                var8.Ý(4210752);
                var8.HorizonCode_Horizon_È(0.0, var6, 0.0, 0.0, var6 / var9);
                var8.HorizonCode_Horizon_È(var5, var6, 0.0, var5 / var9, var6 / var9);
                var8.HorizonCode_Horizon_È(var5, 0.0, 0.0, var5 / var9, 0.0);
                var8.HorizonCode_Horizon_È(0.0, 0.0, 0.0, 0.0, 0.0);
                var7.Â();
                if (progress >= 0) {
                    final byte var10 = 100;
                    final byte var11 = 2;
                    final int var12 = var5 / 2 - var10 / 2;
                    final int var13 = var6 / 2 + 16;
                    GlStateManager.Æ();
                    var8.Â();
                    var8.Ý(8421504);
                    var8.Â(var12, var13, 0.0);
                    var8.Â(var12, var13 + var11, 0.0);
                    var8.Â(var12 + var10, var13 + var11, 0.0);
                    var8.Â(var12 + var10, var13, 0.0);
                    var8.Ý(8454016);
                    var8.Â(var12, var13, 0.0);
                    var8.Â(var12, var13 + var11, 0.0);
                    var8.Â(var12 + progress, var13 + var11, 0.0);
                    var8.Â(var12 + progress, var13, 0.0);
                    var7.Â();
                    GlStateManager.µÕ();
                }
                GlStateManager.á();
                GlStateManager.HorizonCode_Horizon_È(770, 771, 1, 0);
                this.Â.µà.HorizonCode_Horizon_È(this.Ý, (var5 - this.Â.µà.HorizonCode_Horizon_È(this.Ý)) / 2, (float)(var6 / 2 - 4 - 16), 16777215);
                this.Â.µà.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È, (var5 - this.Â.µà.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È)) / 2, (float)(var6 / 2 - 4 + 8), 16777215);
                this.à.Âµá€();
                if (OpenGlHelper.áŒŠÆ()) {
                    this.à.Ý(var5 * var4, var6 * var4);
                }
                this.Â.áŒŠÆ();
                try {
                    Thread.yield();
                }
                catch (Exception ex) {}
            }
        }
    }
    
    @Override
    public void p_() {
    }
}
