package HORIZON-6-0-SKIDPROTECTION;

public class GuiSlider extends GuiButton
{
    private float Â;
    public boolean HorizonCode_Horizon_È;
    private String Ý;
    private final float Ø­áŒŠá;
    private final float Âµá€;
    private final GuiPageButtonList.Ó Ó;
    private HorizonCode_Horizon_È à;
    private static final String Ø = "CL_00001954";
    
    public GuiSlider(final GuiPageButtonList.Ó p_i45541_1_, final int p_i45541_2_, final int p_i45541_3_, final int p_i45541_4_, final String p_i45541_5_, final float p_i45541_6_, final float p_i45541_7_, final float p_i45541_8_, final HorizonCode_Horizon_È p_i45541_9_) {
        super(p_i45541_2_, p_i45541_3_, p_i45541_4_, 150, 20, "");
        this.Â = 1.0f;
        this.Ý = p_i45541_5_;
        this.Ø­áŒŠá = p_i45541_6_;
        this.Âµá€ = p_i45541_7_;
        this.Â = (p_i45541_8_ - p_i45541_6_) / (p_i45541_7_ - p_i45541_6_);
        this.à = p_i45541_9_;
        this.Ó = p_i45541_1_;
        this.Å = this.Âµá€();
    }
    
    public float HorizonCode_Horizon_È() {
        return this.Ø­áŒŠá + (this.Âµá€ - this.Ø­áŒŠá) * this.Â;
    }
    
    public void HorizonCode_Horizon_È(final float p_175218_1_, final boolean p_175218_2_) {
        this.Â = (p_175218_1_ - this.Ø­áŒŠá) / (this.Âµá€ - this.Ø­áŒŠá);
        this.Å = this.Âµá€();
        if (p_175218_2_) {
            this.Ó.HorizonCode_Horizon_È(this.£à, this.HorizonCode_Horizon_È());
        }
    }
    
    public float Â() {
        return this.Â;
    }
    
    private String Âµá€() {
        return (this.à == null) ? (String.valueOf(I18n.HorizonCode_Horizon_È(this.Ý, new Object[0])) + ": " + this.HorizonCode_Horizon_È()) : this.à.HorizonCode_Horizon_È(this.£à, I18n.HorizonCode_Horizon_È(this.Ý, new Object[0]), this.HorizonCode_Horizon_È());
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
                if (this.Â < 0.0f) {
                    this.Â = 0.0f;
                }
                if (this.Â > 1.0f) {
                    this.Â = 1.0f;
                }
                this.Å = this.Âµá€();
                this.Ó.HorizonCode_Horizon_È(this.£à, this.HorizonCode_Horizon_È());
            }
            GlStateManager.Ý(1.0f, 1.0f, 1.0f, 1.0f);
            this.Â(this.ˆÏ­ + (int)(this.Â * (this.ÂµÈ - 8)), this.£á, 0, 66, 4, 20);
            this.Â(this.ˆÏ­ + (int)(this.Â * (this.ÂµÈ - 8)) + 4, this.£á, 196, 66, 4, 20);
        }
    }
    
    public void HorizonCode_Horizon_È(final float p_175219_1_) {
        this.Â = p_175219_1_;
        this.Å = this.Âµá€();
        this.Ó.HorizonCode_Horizon_È(this.£à, this.HorizonCode_Horizon_È());
    }
    
    @Override
    public boolean Â(final Minecraft mc, final int mouseX, final int mouseY) {
        if (super.Â(mc, mouseX, mouseY)) {
            this.Â = (mouseX - (this.ˆÏ­ + 4)) / (this.ÂµÈ - 8);
            if (this.Â < 0.0f) {
                this.Â = 0.0f;
            }
            if (this.Â > 1.0f) {
                this.Â = 1.0f;
            }
            this.Å = this.Âµá€();
            this.Ó.HorizonCode_Horizon_È(this.£à, this.HorizonCode_Horizon_È());
            return this.HorizonCode_Horizon_È = true;
        }
        return false;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int mouseX, final int mouseY) {
        this.HorizonCode_Horizon_È = false;
    }
    
    public interface HorizonCode_Horizon_È
    {
        String HorizonCode_Horizon_È(final int p0, final String p1, final float p2);
    }
}
