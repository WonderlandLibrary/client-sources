package HORIZON-6-0-SKIDPROTECTION;

public class GuiAchievement extends Gui_1808253012
{
    private static final ResourceLocation_1975012498 HorizonCode_Horizon_È;
    private Minecraft Â;
    private int Ý;
    private int Ø­áŒŠá;
    private String Âµá€;
    private String Ó;
    private Achievement à;
    private long Ø;
    private RenderItem áŒŠÆ;
    private boolean áˆºÑ¢Õ;
    private static final String ÂµÈ = "CL_00000721";
    
    static {
        HorizonCode_Horizon_È = new ResourceLocation_1975012498("textures/gui/achievement/achievement_background.png");
    }
    
    public GuiAchievement(final Minecraft mc) {
        this.Â = mc;
        this.áŒŠÆ = mc.áˆºÏ();
    }
    
    public void HorizonCode_Horizon_È(final Achievement p_146256_1_) {
        this.Âµá€ = I18n.HorizonCode_Horizon_È("achievement.get", new Object[0]);
        this.Ó = p_146256_1_.Âµá€().Ø();
        this.Ø = Minecraft.áƒ();
        this.à = p_146256_1_;
        this.áˆºÑ¢Õ = false;
    }
    
    public void Â(final Achievement p_146255_1_) {
        this.Âµá€ = p_146255_1_.Âµá€().Ø();
        this.Ó = p_146255_1_.Ó();
        this.Ø = Minecraft.áƒ() + 2500L;
        this.à = p_146255_1_;
        this.áˆºÑ¢Õ = true;
    }
    
    private void Ý() {
        GlStateManager.Â(0, 0, this.Â.Ó, this.Â.à);
        GlStateManager.á(5889);
        GlStateManager.ŒÏ();
        GlStateManager.á(5888);
        GlStateManager.ŒÏ();
        this.Ý = this.Â.Ó;
        this.Ø­áŒŠá = this.Â.à;
        final ScaledResolution var1 = new ScaledResolution(this.Â, this.Â.Ó, this.Â.à);
        this.Ý = var1.HorizonCode_Horizon_È();
        this.Ø­áŒŠá = var1.Â();
        GlStateManager.ÂµÈ(256);
        GlStateManager.á(5889);
        GlStateManager.ŒÏ();
        GlStateManager.HorizonCode_Horizon_È(0.0, this.Ý, this.Ø­áŒŠá, 0.0, 1000.0, 3000.0);
        GlStateManager.á(5888);
        GlStateManager.ŒÏ();
        GlStateManager.Â(0.0f, 0.0f, -2000.0f);
    }
    
    public void HorizonCode_Horizon_È() {
        if (this.à != null && this.Ø != 0L && Minecraft.áŒŠà().á != null) {
            double var1 = (Minecraft.áƒ() - this.Ø) / 3000.0;
            if (!this.áˆºÑ¢Õ) {
                if (var1 < 0.0 || var1 > 1.0) {
                    this.Ø = 0L;
                    return;
                }
            }
            else if (var1 > 0.5) {
                var1 = 0.5;
            }
            this.Ý();
            GlStateManager.áŒŠÆ();
            GlStateManager.HorizonCode_Horizon_È(false);
            double var2 = var1 * 2.0;
            if (var2 > 1.0) {
                var2 = 2.0 - var2;
            }
            var2 *= 4.0;
            var2 = 1.0 - var2;
            if (var2 < 0.0) {
                var2 = 0.0;
            }
            var2 *= var2;
            var2 *= var2;
            final int var3 = this.Ý - 160;
            final int var4 = 0 - (int)(var2 * 36.0);
            GlStateManager.Ý(1.0f, 1.0f, 1.0f, 1.0f);
            GlStateManager.µÕ();
            this.Â.¥à().HorizonCode_Horizon_È(GuiAchievement.HorizonCode_Horizon_È);
            GlStateManager.Ó();
            this.Â(var3, var4, 96, 202, 160, 32);
            if (this.áˆºÑ¢Õ) {
                this.Â.µà.HorizonCode_Horizon_È(this.Ó, var3 + 30, var4 + 7, 120, -1);
            }
            else {
                this.Â.µà.HorizonCode_Horizon_È(this.Âµá€, var3 + 30, var4 + 7, -256);
                this.Â.µà.HorizonCode_Horizon_È(this.Ó, var3 + 30, var4 + 18, -1);
            }
            RenderHelper.Ý();
            GlStateManager.Ó();
            GlStateManager.ŠÄ();
            GlStateManager.à();
            GlStateManager.Âµá€();
            this.áŒŠÆ.Â(this.à.Ó, var3 + 8, var4 + 8);
            GlStateManager.Ó();
            GlStateManager.HorizonCode_Horizon_È(true);
            GlStateManager.áˆºÑ¢Õ();
        }
    }
    
    public void Â() {
        this.à = null;
        this.Ø = 0L;
    }
}
