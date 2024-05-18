package HORIZON-6-0-SKIDPROTECTION;

public class GuiFlatSlider extends GuiMenuButtonNoSlide
{
    public float HorizonCode_Horizon_È;
    public static boolean Â;
    public GameSettings.HorizonCode_Horizon_È Ý;
    public final float Ø­áŒŠá;
    public final float Âµá€;
    private static final String Ø­à = "CL_00000680";
    
    public GuiFlatSlider(final int p_i45016_1_, final int p_i45016_2_, final int p_i45016_3_, final GameSettings.HorizonCode_Horizon_È p_i45016_4_) {
        this(p_i45016_1_, p_i45016_2_, p_i45016_3_, p_i45016_4_, 0.0f, 1.0f);
    }
    
    public GuiFlatSlider(final int p_i45017_1_, final int p_i45017_2_, final int p_i45017_3_, final GameSettings.HorizonCode_Horizon_È p_i45017_4_, final float p_i45017_5_, final float p_i45017_6_) {
        super(p_i45017_1_, p_i45017_2_, p_i45017_3_, 150, 20, "");
        this.HorizonCode_Horizon_È = 1.0f;
        this.Ý = p_i45017_4_;
        this.Ø­áŒŠá = p_i45017_5_;
        this.Âµá€ = p_i45017_6_;
        final Minecraft var7 = Minecraft.áŒŠà();
        this.HorizonCode_Horizon_È = p_i45017_4_.Â(var7.ŠÄ.HorizonCode_Horizon_È(p_i45017_4_));
        this.Å = var7.ŠÄ.Ý(p_i45017_4_);
    }
    
    @Override
    protected int HorizonCode_Horizon_È(final boolean mouseOver) {
        return 0;
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final Minecraft mc, final int mouseX, final int mouseY) {
        if (this.ˆà) {
            if (GuiFlatSlider.Â) {
                this.HorizonCode_Horizon_È = (mouseX - (this.ˆÏ­ + 4)) / (this.ÂµÈ - 8);
                this.HorizonCode_Horizon_È = MathHelper.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È, 0.0f, 1.0f);
                final float var4 = this.Ý.Ý(this.HorizonCode_Horizon_È);
                mc.ŠÄ.HorizonCode_Horizon_È(this.Ý, var4);
                this.HorizonCode_Horizon_È = this.Ý.Â(var4);
                this.Å = mc.ŠÄ.Ý(this.Ý);
            }
            mc.¥à().HorizonCode_Horizon_È(GuiFlatSlider.áˆºÑ¢Õ);
            GlStateManager.Ý(1.0f, 1.0f, 1.0f, 1.0f);
            if (this.¥Æ || GuiFlatSlider.Â) {
                Gui_1808253012.HorizonCode_Horizon_È(this.ˆÏ­ + (int)(this.HorizonCode_Horizon_È * (this.ÂµÈ - 8)), this.£á, this.ˆÏ­ + (int)(this.HorizonCode_Horizon_È * (this.ÂµÈ - 8)) + 8, this.£á + 20, 2130706431);
            }
            else {
                Gui_1808253012.HorizonCode_Horizon_È(this.ˆÏ­ + (int)(this.HorizonCode_Horizon_È * (this.ÂµÈ - 8)), this.£á, this.ˆÏ­ + (int)(this.HorizonCode_Horizon_È * (this.ÂµÈ - 8)) + 8, this.£á + 20, Integer.MIN_VALUE);
            }
        }
    }
    
    @Override
    public boolean Â(final Minecraft mc, final int mouseX, final int mouseY) {
        if (super.Â(mc, mouseX, mouseY)) {
            this.HorizonCode_Horizon_È = (mouseX - (this.ˆÏ­ + 4)) / (this.ÂµÈ - 8);
            this.HorizonCode_Horizon_È = MathHelper.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È, 0.0f, 1.0f);
            mc.ŠÄ.HorizonCode_Horizon_È(this.Ý, this.Ý.Ý(this.HorizonCode_Horizon_È));
            this.Å = mc.ŠÄ.Ý(this.Ý);
            return GuiFlatSlider.Â = true;
        }
        return false;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int mouseX, final int mouseY) {
        GuiFlatSlider.Â = false;
    }
}
