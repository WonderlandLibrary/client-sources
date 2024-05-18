package HORIZON-6-0-SKIDPROTECTION;

public class GuiStreamIndicator
{
    private static final ResourceLocation_1975012498 HorizonCode_Horizon_È;
    private final Minecraft Â;
    private float Ý;
    private int Ø­áŒŠá;
    private static final String Âµá€ = "CL_00001849";
    
    static {
        HorizonCode_Horizon_È = new ResourceLocation_1975012498("textures/gui/stream_indicator.png");
    }
    
    public GuiStreamIndicator(final Minecraft mcIn) {
        this.Ý = 1.0f;
        this.Ø­áŒŠá = 1;
        this.Â = mcIn;
    }
    
    public void HorizonCode_Horizon_È(final int p_152437_1_, final int p_152437_2_) {
        if (this.Â.Ä().ÂµÈ()) {
            GlStateManager.á();
            final int var3 = this.Â.Ä().Šáƒ();
            if (var3 > 0) {
                final String var4 = new StringBuilder().append(var3).toString();
                final int var5 = this.Â.µà.HorizonCode_Horizon_È(var4);
                final boolean var6 = true;
                final int var7 = p_152437_1_ - var5 - 1;
                final int var8 = p_152437_2_ + 20 - 1;
                final int var9 = p_152437_2_ + 20 + this.Â.µà.HorizonCode_Horizon_È - 1;
                GlStateManager.Æ();
                final Tessellator var10 = Tessellator.HorizonCode_Horizon_È();
                final WorldRenderer var11 = var10.Ý();
                GlStateManager.Ý(0.0f, 0.0f, 0.0f, (0.65f + 0.35000002f * this.Ý) / 2.0f);
                var11.Â();
                var11.Â(var7, var9, 0.0);
                var11.Â(p_152437_1_, var9, 0.0);
                var11.Â(p_152437_1_, var8, 0.0);
                var11.Â(var7, var8, 0.0);
                var10.Â();
                GlStateManager.µÕ();
                this.Â.µà.HorizonCode_Horizon_È(var4, p_152437_1_ - var5, p_152437_2_ + 20, 16777215);
            }
            this.HorizonCode_Horizon_È(p_152437_1_, p_152437_2_, this.Â(), 0);
            this.HorizonCode_Horizon_È(p_152437_1_, p_152437_2_, this.Ý(), 17);
        }
    }
    
    private void HorizonCode_Horizon_È(final int p_152436_1_, final int p_152436_2_, final int p_152436_3_, final int p_152436_4_) {
        GlStateManager.Ý(1.0f, 1.0f, 1.0f, 0.65f + 0.35000002f * this.Ý);
        this.Â.¥à().HorizonCode_Horizon_È(GuiStreamIndicator.HorizonCode_Horizon_È);
        final float var5 = 150.0f;
        final float var6 = 0.0f;
        final float var7 = p_152436_3_ * 0.015625f;
        final float var8 = 1.0f;
        final float var9 = (p_152436_3_ + 16) * 0.015625f;
        final Tessellator var10 = Tessellator.HorizonCode_Horizon_È();
        final WorldRenderer var11 = var10.Ý();
        var11.Â();
        var11.HorizonCode_Horizon_È(p_152436_1_ - 16 - p_152436_4_, p_152436_2_ + 16, var5, var6, var9);
        var11.HorizonCode_Horizon_È(p_152436_1_ - p_152436_4_, p_152436_2_ + 16, var5, var8, var9);
        var11.HorizonCode_Horizon_È(p_152436_1_ - p_152436_4_, p_152436_2_ + 0, var5, var8, var7);
        var11.HorizonCode_Horizon_È(p_152436_1_ - 16 - p_152436_4_, p_152436_2_ + 0, var5, var6, var7);
        var10.Â();
        GlStateManager.Ý(1.0f, 1.0f, 1.0f, 1.0f);
    }
    
    private int Â() {
        return this.Â.Ä().á() ? 16 : 0;
    }
    
    private int Ý() {
        return this.Â.Ä().Çªà¢() ? 48 : 32;
    }
    
    public void HorizonCode_Horizon_È() {
        if (this.Â.Ä().ÂµÈ()) {
            this.Ý += 0.025f * this.Ø­áŒŠá;
            if (this.Ý < 0.0f) {
                this.Ø­áŒŠá *= -1;
                this.Ý = 0.0f;
            }
            else if (this.Ý > 1.0f) {
                this.Ø­áŒŠá *= -1;
                this.Ý = 1.0f;
            }
        }
        else {
            this.Ý = 1.0f;
            this.Ø­áŒŠá = 1;
        }
    }
}
