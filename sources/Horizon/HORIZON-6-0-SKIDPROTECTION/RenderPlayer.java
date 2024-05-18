package HORIZON-6-0-SKIDPROTECTION;

public class RenderPlayer extends RendererLivingEntity
{
    private boolean HorizonCode_Horizon_È;
    private static final String Âµá€ = "CL_00001020";
    
    public RenderPlayer(final RenderManager p_i46102_1_) {
        this(p_i46102_1_, false);
    }
    
    public RenderPlayer(final RenderManager p_i46103_1_, final boolean p_i46103_2_) {
        super(p_i46103_1_, new ModelPlayer(0.0f, p_i46103_2_), 0.5f);
        this.HorizonCode_Horizon_È = p_i46103_2_;
        this.HorizonCode_Horizon_È(new LayerBipedArmor(this));
        this.HorizonCode_Horizon_È(new LayerHeldItem(this));
        this.HorizonCode_Horizon_È(new LayerArrow(this));
        this.HorizonCode_Horizon_È(new LayerDeadmau5Head(this));
        this.HorizonCode_Horizon_È(new LayerCape(this));
        this.HorizonCode_Horizon_È(new LayerCustomHead(this.Âµá€().ÂµÈ));
    }
    
    public ModelPlayer Âµá€() {
        return (ModelPlayer)super.Â();
    }
    
    public void HorizonCode_Horizon_È(final AbstractClientPlayer p_180596_1_, final double p_180596_2_, final double p_180596_4_, final double p_180596_6_, final float p_180596_8_, final float p_180596_9_) {
        if (!p_180596_1_.µÕ() || this.Â.Ó == p_180596_1_) {
            double var10 = p_180596_4_;
            if (p_180596_1_.Çªà¢() && !(p_180596_1_ instanceof EntityPlayerSP)) {
                var10 = p_180596_4_ - 0.125;
            }
            this.Ø­áŒŠá(p_180596_1_);
            super.HorizonCode_Horizon_È(p_180596_1_, p_180596_2_, var10, p_180596_6_, p_180596_8_, p_180596_9_);
        }
    }
    
    private void Ø­áŒŠá(final AbstractClientPlayer p_177137_1_) {
        final ModelPlayer var2 = this.Âµá€();
        if (p_177137_1_.Ø­áŒŠá()) {
            var2.HorizonCode_Horizon_È(false);
            var2.ÂµÈ.áˆºÑ¢Õ = true;
            var2.á.áˆºÑ¢Õ = true;
        }
        else {
            final ItemStack var3 = p_177137_1_.Ø­Ñ¢Ï­Ø­áˆº.Ø­áŒŠá();
            var2.HorizonCode_Horizon_È(true);
            var2.á.áˆºÑ¢Õ = p_177137_1_.HorizonCode_Horizon_È(EnumPlayerModelParts.à);
            var2.Æ.áˆºÑ¢Õ = p_177137_1_.HorizonCode_Horizon_È(EnumPlayerModelParts.Â);
            var2.Ý.áˆºÑ¢Õ = p_177137_1_.HorizonCode_Horizon_È(EnumPlayerModelParts.Âµá€);
            var2.Ø­áŒŠá.áˆºÑ¢Õ = p_177137_1_.HorizonCode_Horizon_È(EnumPlayerModelParts.Ó);
            var2.HorizonCode_Horizon_È.áˆºÑ¢Õ = p_177137_1_.HorizonCode_Horizon_È(EnumPlayerModelParts.Ý);
            var2.Â.áˆºÑ¢Õ = p_177137_1_.HorizonCode_Horizon_È(EnumPlayerModelParts.Ø­áŒŠá);
            var2.ˆà = 0;
            var2.µÕ = false;
            var2.Ø­à = p_177137_1_.Çªà¢();
            if (var3 == null) {
                var2.¥Æ = 0;
            }
            else {
                var2.¥Æ = 1;
                if (p_177137_1_.Ø­Ñ¢á€() > 0) {
                    final EnumAction var4 = var3.ˆÏ­();
                    if (var4 == EnumAction.Ø­áŒŠá) {
                        var2.¥Æ = 3;
                    }
                    else if (var4 == EnumAction.Âµá€) {
                        var2.µÕ = true;
                    }
                }
            }
        }
    }
    
    protected ResourceLocation_1975012498 HorizonCode_Horizon_È(final AbstractClientPlayer p_180594_1_) {
        return p_180594_1_.Ø();
    }
    
    @Override
    public void u_() {
        GlStateManager.Â(0.0f, 0.1875f, 0.0f);
    }
    
    protected void HorizonCode_Horizon_È(final AbstractClientPlayer p_77041_1_, final float p_77041_2_) {
        final float var3 = 0.9375f;
        GlStateManager.HorizonCode_Horizon_È(var3, var3, var3);
    }
    
    protected void HorizonCode_Horizon_È(final AbstractClientPlayer p_96449_1_, final double p_96449_2_, double p_96449_4_, final double p_96449_6_, final String p_96449_8_, final float p_96449_9_, final double p_96449_10_) {
        if (p_96449_10_ < 100.0) {
            final Scoreboard var12 = p_96449_1_.ÇŽÅ();
            final ScoreObjective var13 = var12.HorizonCode_Horizon_È(2);
            if (var13 != null) {
                final Score var14 = var12.Â(p_96449_1_.v_(), var13);
                this.HorizonCode_Horizon_È(p_96449_1_, String.valueOf(var14.Â()) + " " + var13.Ø­áŒŠá(), p_96449_2_, p_96449_4_, p_96449_6_, 64);
                p_96449_4_ += Render.Ý().HorizonCode_Horizon_È * 1.15f * p_96449_9_;
            }
        }
        super.HorizonCode_Horizon_È(p_96449_1_, p_96449_2_, p_96449_4_, p_96449_6_, p_96449_8_, p_96449_9_, p_96449_10_);
    }
    
    public void Â(final AbstractClientPlayer p_177138_1_) {
        final float var2 = 1.0f;
        GlStateManager.Ý(var2, var2, var2);
        final ModelPlayer var3 = this.Âµá€();
        this.Ø­áŒŠá(p_177138_1_);
        var3.Âµá€ = 0.0f;
        var3.Ø­à = false;
        var3.HorizonCode_Horizon_È(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0625f, p_177138_1_);
        var3.HorizonCode_Horizon_È();
    }
    
    public void Ý(final AbstractClientPlayer p_177139_1_) {
        final float var2 = 1.0f;
        GlStateManager.Ý(var2, var2, var2);
        final ModelPlayer var3 = this.Âµá€();
        this.Ø­áŒŠá(p_177139_1_);
        var3.Ø­à = false;
        var3.HorizonCode_Horizon_È(var3.Âµá€ = 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0625f, p_177139_1_);
        var3.Â();
    }
    
    protected void HorizonCode_Horizon_È(final AbstractClientPlayer p_77039_1_, final double p_77039_2_, final double p_77039_4_, final double p_77039_6_) {
        if (p_77039_1_.Œ() && p_77039_1_.Ï­Ó()) {
            super.HorizonCode_Horizon_È(p_77039_1_, p_77039_2_ + p_77039_1_.Ï­Ä, p_77039_4_ + p_77039_1_.¥áŠ, p_77039_6_ + p_77039_1_.µÊ);
        }
        else {
            super.HorizonCode_Horizon_È(p_77039_1_, p_77039_2_, p_77039_4_, p_77039_6_);
        }
    }
    
    protected void HorizonCode_Horizon_È(final AbstractClientPlayer p_180595_1_, final float p_180595_2_, final float p_180595_3_, final float p_180595_4_) {
        if (p_180595_1_.Œ() && p_180595_1_.Ï­Ó()) {
            GlStateManager.Â(p_180595_1_.ÐƒÂ(), 0.0f, 1.0f, 0.0f);
            GlStateManager.Â(this.Â(p_180595_1_), 0.0f, 0.0f, 1.0f);
            GlStateManager.Â(270.0f, 0.0f, 1.0f, 0.0f);
        }
        else {
            super.HorizonCode_Horizon_È(p_180595_1_, p_180595_2_, p_180595_3_, p_180595_4_);
        }
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final EntityLivingBase p_77041_1_, final float p_77041_2_) {
        this.HorizonCode_Horizon_È((AbstractClientPlayer)p_77041_1_, p_77041_2_);
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final EntityLivingBase p_77043_1_, final float p_77043_2_, final float p_77043_3_, final float p_77043_4_) {
        this.HorizonCode_Horizon_È((AbstractClientPlayer)p_77043_1_, p_77043_2_, p_77043_3_, p_77043_4_);
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final EntityLivingBase p_77039_1_, final double p_77039_2_, final double p_77039_4_, final double p_77039_6_) {
        this.HorizonCode_Horizon_È((AbstractClientPlayer)p_77039_1_, p_77039_2_, p_77039_4_, p_77039_6_);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final EntityLivingBase p_76986_1_, final double p_76986_2_, final double p_76986_4_, final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        this.HorizonCode_Horizon_È((AbstractClientPlayer)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
    
    @Override
    public ModelBase Â() {
        return this.Âµá€();
    }
    
    @Override
    protected ResourceLocation_1975012498 HorizonCode_Horizon_È(final Entity p_110775_1_) {
        return this.HorizonCode_Horizon_È((AbstractClientPlayer)p_110775_1_);
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final Entity p_177069_1_, final double p_177069_2_, final double p_177069_4_, final double p_177069_6_, final String p_177069_8_, final float p_177069_9_, final double p_177069_10_) {
        this.HorizonCode_Horizon_È((AbstractClientPlayer)p_177069_1_, p_177069_2_, p_177069_4_, p_177069_6_, p_177069_8_, p_177069_9_, p_177069_10_);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final Entity p_76986_1_, final double p_76986_2_, final double p_76986_4_, final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        this.HorizonCode_Horizon_È((AbstractClientPlayer)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
}
