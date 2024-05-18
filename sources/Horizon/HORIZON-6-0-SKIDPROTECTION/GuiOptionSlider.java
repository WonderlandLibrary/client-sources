package HORIZON-6-0-SKIDPROTECTION;

public class GuiOptionSlider extends GuiButton
{
    private float Â;
    public boolean HorizonCode_Horizon_È;
    private GameSettings.HorizonCode_Horizon_È Ý;
    private final float Ø­áŒŠá;
    private final float Âµá€;
    private static final String Ó = "CL_00000680";
    
    public GuiOptionSlider(final int p_i45016_1_, final int p_i45016_2_, final int p_i45016_3_, final GameSettings.HorizonCode_Horizon_È p_i45016_4_) {
        this(p_i45016_1_, p_i45016_2_, p_i45016_3_, p_i45016_4_, 0.0f, 1.0f);
    }
    
    public GuiOptionSlider(final int p_i45017_1_, final int p_i45017_2_, final int p_i45017_3_, final GameSettings.HorizonCode_Horizon_È p_i45017_4_, final float p_i45017_5_, final float p_i45017_6_) {
        super(p_i45017_1_, p_i45017_2_, p_i45017_3_, 150, 20, "");
        this.Â = 1.0f;
        this.Ý = p_i45017_4_;
        this.Ø­áŒŠá = p_i45017_5_;
        this.Âµá€ = p_i45017_6_;
        final Minecraft var7 = Minecraft.áŒŠà();
        this.Â = p_i45017_4_.Â(var7.ŠÄ.HorizonCode_Horizon_È(p_i45017_4_));
        this.Å = var7.ŠÄ.Ý(p_i45017_4_);
    }
    
    @Override
    protected int HorizonCode_Horizon_È(final boolean mouseOver) {
        return 0;
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final Minecraft mc, final int mouseX, final int mouseY) {
        if (this.ˆà) {
            if (this.HorizonCode_Horizon_È) {
                this.Â = (mouseX - (this.ˆÏ­ + 4)) / (this.ÂµÈ - 8);
                this.Â = MathHelper.HorizonCode_Horizon_È(this.Â, 0.0f, 1.0f);
                final float var4 = this.Ý.Ý(this.Â);
                mc.ŠÄ.HorizonCode_Horizon_È(this.Ý, var4);
                this.Â = this.Ý.Â(var4);
                this.Å = mc.ŠÄ.Ý(this.Ý);
            }
            mc.¥à().HorizonCode_Horizon_È(GuiOptionSlider.áˆºÑ¢Õ);
            GlStateManager.Ý(1.0f, 1.0f, 1.0f, 1.0f);
            this.Â(this.ˆÏ­ + (int)(this.Â * (this.ÂµÈ - 8)), this.£á, 0, 66, 4, 20);
            this.Â(this.ˆÏ­ + (int)(this.Â * (this.ÂµÈ - 8)) + 4, this.£á, 196, 66, 4, 20);
        }
    }
    
    @Override
    public boolean Â(final Minecraft mc, final int mouseX, final int mouseY) {
        if (super.Â(mc, mouseX, mouseY)) {
            this.Â = (mouseX - (this.ˆÏ­ + 4)) / (this.ÂµÈ - 8);
            this.Â = MathHelper.HorizonCode_Horizon_È(this.Â, 0.0f, 1.0f);
            mc.ŠÄ.HorizonCode_Horizon_È(this.Ý, this.Ý.Ý(this.Â));
            this.Å = mc.ŠÄ.Ý(this.Ý);
            return this.HorizonCode_Horizon_È = true;
        }
        return false;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int mouseX, final int mouseY) {
        this.HorizonCode_Horizon_È = false;
    }
}
