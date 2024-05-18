package HORIZON-6-0-SKIDPROTECTION;

public class RealmsSliderButton extends RealmsButton
{
    public float Â;
    public boolean Ý;
    private final float Ø­áŒŠá;
    private final float Âµá€;
    private int Ó;
    private static final String à = "CL_00001834";
    
    public RealmsSliderButton(final int p_i1056_1_, final int p_i1056_2_, final int p_i1056_3_, final int p_i1056_4_, final int p_i1056_5_, final int p_i1056_6_) {
        this(p_i1056_1_, p_i1056_2_, p_i1056_3_, p_i1056_4_, p_i1056_6_, 0, 1.0f, p_i1056_5_);
    }
    
    public RealmsSliderButton(final int p_i1057_1_, final int p_i1057_2_, final int p_i1057_3_, final int p_i1057_4_, final int p_i1057_5_, final int p_i1057_6_, final float p_i1057_7_, final float p_i1057_8_) {
        super(p_i1057_1_, p_i1057_2_, p_i1057_3_, p_i1057_4_, 20, "");
        this.Â = 1.0f;
        this.Ø­áŒŠá = p_i1057_7_;
        this.Âµá€ = p_i1057_8_;
        this.Â = this.HorizonCode_Horizon_È(p_i1057_6_);
        this.HorizonCode_Horizon_È().Å = this.à();
    }
    
    public String à() {
        return "";
    }
    
    public float HorizonCode_Horizon_È(final float p_toPct_1_) {
        return MathHelper.HorizonCode_Horizon_È((this.Ý(p_toPct_1_) - this.Ø­áŒŠá) / (this.Âµá€ - this.Ø­áŒŠá), 0.0f, 1.0f);
    }
    
    public float Â(final float p_toValue_1_) {
        return this.Ý(this.Ø­áŒŠá + (this.Âµá€ - this.Ø­áŒŠá) * MathHelper.HorizonCode_Horizon_È(p_toValue_1_, 0.0f, 1.0f));
    }
    
    public float Ý(float p_clamp_1_) {
        p_clamp_1_ = this.Ø­áŒŠá(p_clamp_1_);
        return MathHelper.HorizonCode_Horizon_È(p_clamp_1_, this.Ø­áŒŠá, this.Âµá€);
    }
    
    protected float Ø­áŒŠá(float p_clampSteps_1_) {
        if (this.Ó > 0) {
            p_clampSteps_1_ = this.Ó * Math.round(p_clampSteps_1_ / this.Ó);
        }
        return p_clampSteps_1_;
    }
    
    @Override
    public int Â(final boolean p_getYImage_1_) {
        return 0;
    }
    
    @Override
    public void Ø­áŒŠá(final int p_renderBg_1_, final int p_renderBg_2_) {
        if (this.HorizonCode_Horizon_È().ˆà) {
            if (this.Ý) {
                this.Â = (p_renderBg_1_ - (this.HorizonCode_Horizon_È().ˆÏ­ + 4)) / (this.HorizonCode_Horizon_È().Ø­áŒŠá() - 8);
                this.Â = MathHelper.HorizonCode_Horizon_È(this.Â, 0.0f, 1.0f);
                final float var3 = this.Â(this.Â);
                this.Âµá€(var3);
                this.Â = this.HorizonCode_Horizon_È(var3);
                this.HorizonCode_Horizon_È().Å = this.à();
            }
            Minecraft.áŒŠà().¥à().HorizonCode_Horizon_È(RealmsSliderButton.HorizonCode_Horizon_È);
            GlStateManager.Ý(1.0f, 1.0f, 1.0f, 1.0f);
            this.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È().ˆÏ­ + (int)(this.Â * (this.HorizonCode_Horizon_È().Ø­áŒŠá() - 8)), this.HorizonCode_Horizon_È().£á, 0, 66, 4, 20);
            this.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È().ˆÏ­ + (int)(this.Â * (this.HorizonCode_Horizon_È().Ø­áŒŠá() - 8)) + 4, this.HorizonCode_Horizon_È().£á, 196, 66, 4, 20);
        }
    }
    
    @Override
    public void Â(final int p_clicked_1_, final int p_clicked_2_) {
        this.Â = (p_clicked_1_ - (this.HorizonCode_Horizon_È().ˆÏ­ + 4)) / (this.HorizonCode_Horizon_È().Ø­áŒŠá() - 8);
        this.Â = MathHelper.HorizonCode_Horizon_È(this.Â, 0.0f, 1.0f);
        this.Âµá€(this.Â(this.Â));
        this.HorizonCode_Horizon_È().Å = this.à();
        this.Ý = true;
    }
    
    public void Âµá€(final float p_clicked_1_) {
    }
    
    @Override
    public void Ý(final int p_released_1_, final int p_released_2_) {
        this.Ý = false;
    }
}
