package HORIZON-6-0-SKIDPROTECTION;

public class GuiSpectator extends Gui_1808253012 implements ISpectatorMenuReciepient
{
    private static final ResourceLocation_1975012498 Â;
    public static final ResourceLocation_1975012498 HorizonCode_Horizon_È;
    private final Minecraft Ý;
    private long Ø­áŒŠá;
    private SpectatorMenu Âµá€;
    private static final String Ó = "CL_00001940";
    
    static {
        Â = new ResourceLocation_1975012498("textures/gui/widgets.png");
        HorizonCode_Horizon_È = new ResourceLocation_1975012498("textures/gui/spectator_widgets.png");
    }
    
    public GuiSpectator(final Minecraft mcIn) {
        this.Ý = mcIn;
    }
    
    public void HorizonCode_Horizon_È(final int p_175260_1_) {
        this.Ø­áŒŠá = Minecraft.áƒ();
        if (this.Âµá€ != null) {
            this.Âµá€.Â(p_175260_1_);
        }
        else {
            this.Âµá€ = new SpectatorMenu(this);
        }
    }
    
    private float Ý() {
        final long var1 = this.Ø­áŒŠá - Minecraft.áƒ() + 5000L;
        return MathHelper.HorizonCode_Horizon_È(var1 / 2000.0f, 0.0f, 1.0f);
    }
    
    public void HorizonCode_Horizon_È(final ScaledResolution p_175264_1_, final float p_175264_2_) {
        if (this.Âµá€ != null) {
            final float var3 = this.Ý();
            if (var3 <= 0.0f) {
                this.Âµá€.Ø­áŒŠá();
            }
            else {
                final int var4 = p_175264_1_.HorizonCode_Horizon_È() / 2;
                final float var5 = GuiSpectator.ŠÄ;
                GuiSpectator.ŠÄ = -90.0f;
                final float var6 = p_175264_1_.Â() - 22.0f * var3;
                final SpectatorDetails var7 = this.Âµá€.Ó();
                this.HorizonCode_Horizon_È(p_175264_1_, var3, var4, var6, var7);
                GuiSpectator.ŠÄ = var5;
            }
        }
    }
    
    protected void HorizonCode_Horizon_È(final ScaledResolution p_175258_1_, final float p_175258_2_, final int p_175258_3_, final float p_175258_4_, final SpectatorDetails p_175258_5_) {
        GlStateManager.ŠÄ();
        GlStateManager.á();
        GlStateManager.HorizonCode_Horizon_È(770, 771, 1, 0);
        GlStateManager.Ý(1.0f, 1.0f, 1.0f, p_175258_2_);
        this.Ý.¥à().HorizonCode_Horizon_È(GuiSpectator.Â);
        this.HorizonCode_Horizon_È(p_175258_3_ - 91, p_175258_4_, 0, 0, 182, 22);
        if (p_175258_5_.HorizonCode_Horizon_È() >= 0) {
            this.HorizonCode_Horizon_È(p_175258_3_ - 91 - 1 + p_175258_5_.HorizonCode_Horizon_È() * 20, p_175258_4_ - 1.0f, 0, 22, 24, 22);
        }
        RenderHelper.Ý();
        for (int var6 = 0; var6 < 9; ++var6) {
            this.HorizonCode_Horizon_È(var6, p_175258_1_.HorizonCode_Horizon_È() / 2 - 90 + var6 * 20 + 2, p_175258_4_ + 3.0f, p_175258_2_, p_175258_5_.HorizonCode_Horizon_È(var6));
        }
        RenderHelper.HorizonCode_Horizon_È();
        GlStateManager.Ñ¢á();
        GlStateManager.ÂµÈ();
    }
    
    private void HorizonCode_Horizon_È(final int p_175266_1_, final int p_175266_2_, final float p_175266_3_, final float p_175266_4_, final ISpectatorMenuObject p_175266_5_) {
        this.Ý.¥à().HorizonCode_Horizon_È(GuiSpectator.HorizonCode_Horizon_È);
        if (p_175266_5_ != SpectatorMenu.HorizonCode_Horizon_È) {
            final int var6 = (int)(p_175266_4_ * 255.0f);
            GlStateManager.Çªà¢();
            GlStateManager.Â(p_175266_2_, p_175266_3_, 0.0f);
            final float var7 = p_175266_5_.Ø­áŒŠá() ? 1.0f : 0.25f;
            GlStateManager.Ý(var7, var7, var7, p_175266_4_);
            p_175266_5_.HorizonCode_Horizon_È(var7, var6);
            GlStateManager.Ê();
            final String var8 = String.valueOf(GameSettings.HorizonCode_Horizon_È(this.Ý.ŠÄ.áŒŠÉ[p_175266_1_].áŒŠÆ()));
            if (var6 > 3 && p_175266_5_.Ø­áŒŠá()) {
                this.Ý.µà.HorizonCode_Horizon_È(var8, p_175266_2_ + 19 - 2 - this.Ý.µà.HorizonCode_Horizon_È(var8), p_175266_3_ + 6.0f + 3.0f, 16777215 + (var6 << 24));
            }
        }
    }
    
    public void HorizonCode_Horizon_È(final ScaledResolution p_175263_1_) {
        final int var2 = (int)(this.Ý() * 255.0f);
        if (var2 > 3 && this.Âµá€ != null) {
            final ISpectatorMenuObject var3 = this.Âµá€.Â();
            final String var4 = (var3 != SpectatorMenu.HorizonCode_Horizon_È) ? var3.Ý().áŒŠÆ() : this.Âµá€.Ý().Â().áŒŠÆ();
            if (var4 != null) {
                final int var5 = (p_175263_1_.HorizonCode_Horizon_È() - this.Ý.µà.HorizonCode_Horizon_È(var4)) / 2;
                final int var6 = p_175263_1_.Â() - 35;
                GlStateManager.Çªà¢();
                GlStateManager.á();
                GlStateManager.HorizonCode_Horizon_È(770, 771, 1, 0);
                this.Ý.µà.HorizonCode_Horizon_È(var4, var5, (float)var6, 16777215 + (var2 << 24));
                GlStateManager.ÂµÈ();
                GlStateManager.Ê();
            }
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final SpectatorMenu p_175257_1_) {
        this.Âµá€ = null;
        this.Ø­áŒŠá = 0L;
    }
    
    public boolean HorizonCode_Horizon_È() {
        return this.Âµá€ != null;
    }
    
    public void Â(final int p_175259_1_) {
        int var2;
        for (var2 = this.Âµá€.Âµá€() + p_175259_1_; var2 >= 0 && var2 <= 8 && (this.Âµá€.HorizonCode_Horizon_È(var2) == SpectatorMenu.HorizonCode_Horizon_È || !this.Âµá€.HorizonCode_Horizon_È(var2).Ø­áŒŠá()); var2 += p_175259_1_) {}
        if (var2 >= 0 && var2 <= 8) {
            this.Âµá€.Â(var2);
            this.Ø­áŒŠá = Minecraft.áƒ();
        }
    }
    
    public void Â() {
        this.Ø­áŒŠá = Minecraft.áƒ();
        if (this.HorizonCode_Horizon_È()) {
            final int var1 = this.Âµá€.Âµá€();
            if (var1 != -1) {
                this.Âµá€.Â(var1);
            }
        }
        else {
            this.Âµá€ = new SpectatorMenu(this);
        }
    }
}
