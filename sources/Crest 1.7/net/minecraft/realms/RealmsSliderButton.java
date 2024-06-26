// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.realms;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.util.MathHelper;

public class RealmsSliderButton extends RealmsButton
{
    public float value;
    public boolean sliding;
    private final float minValue;
    private final float maxValue;
    private int steps;
    private static final String __OBFID = "CL_00001834";
    
    public RealmsSliderButton(final int p_i1056_1_, final int p_i1056_2_, final int p_i1056_3_, final int p_i1056_4_, final int p_i1056_5_, final int p_i1056_6_) {
        this(p_i1056_1_, p_i1056_2_, p_i1056_3_, p_i1056_4_, p_i1056_6_, 0, 1.0f, p_i1056_5_);
    }
    
    public RealmsSliderButton(final int p_i1057_1_, final int p_i1057_2_, final int p_i1057_3_, final int p_i1057_4_, final int p_i1057_5_, final int p_i1057_6_, final float p_i1057_7_, final float p_i1057_8_) {
        super(p_i1057_1_, p_i1057_2_, p_i1057_3_, p_i1057_4_, 20, "");
        this.value = 1.0f;
        this.minValue = p_i1057_7_;
        this.maxValue = p_i1057_8_;
        this.value = this.toPct(p_i1057_6_);
        this.getProxy().displayString = this.getMessage();
    }
    
    public String getMessage() {
        return "";
    }
    
    public float toPct(final float p_toPct_1_) {
        return MathHelper.clamp_float((this.clamp(p_toPct_1_) - this.minValue) / (this.maxValue - this.minValue), 0.0f, 1.0f);
    }
    
    public float toValue(final float p_toValue_1_) {
        return this.clamp(this.minValue + (this.maxValue - this.minValue) * MathHelper.clamp_float(p_toValue_1_, 0.0f, 1.0f));
    }
    
    public float clamp(float p_clamp_1_) {
        p_clamp_1_ = this.clampSteps(p_clamp_1_);
        return MathHelper.clamp_float(p_clamp_1_, this.minValue, this.maxValue);
    }
    
    protected float clampSteps(float p_clampSteps_1_) {
        if (this.steps > 0) {
            p_clampSteps_1_ = this.steps * Math.round(p_clampSteps_1_ / this.steps);
        }
        return p_clampSteps_1_;
    }
    
    @Override
    public int getYImage(final boolean p_getYImage_1_) {
        return 0;
    }
    
    @Override
    public void renderBg(final int p_renderBg_1_, final int p_renderBg_2_) {
        if (this.getProxy().visible) {
            if (this.sliding) {
                this.value = (p_renderBg_1_ - (this.getProxy().xPosition + 4)) / (this.getProxy().getButtonWidth() - 8);
                this.value = MathHelper.clamp_float(this.value, 0.0f, 1.0f);
                final float var3 = this.toValue(this.value);
                this.clicked(var3);
                this.value = this.toPct(var3);
                this.getProxy().displayString = this.getMessage();
            }
            Minecraft.getMinecraft().getTextureManager().bindTexture(RealmsSliderButton.WIDGETS_LOCATION);
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            this.blit(this.getProxy().xPosition + (int)(this.value * (this.getProxy().getButtonWidth() - 8)), this.getProxy().yPosition, 0, 66, 4, 20);
            this.blit(this.getProxy().xPosition + (int)(this.value * (this.getProxy().getButtonWidth() - 8)) + 4, this.getProxy().yPosition, 196, 66, 4, 20);
        }
    }
    
    @Override
    public void clicked(final int p_clicked_1_, final int p_clicked_2_) {
        this.value = (p_clicked_1_ - (this.getProxy().xPosition + 4)) / (this.getProxy().getButtonWidth() - 8);
        this.value = MathHelper.clamp_float(this.value, 0.0f, 1.0f);
        this.clicked(this.toValue(this.value));
        this.getProxy().displayString = this.getMessage();
        this.sliding = true;
    }
    
    public void clicked(final float p_clicked_1_) {
    }
    
    @Override
    public void released(final int p_released_1_, final int p_released_2_) {
        this.sliding = false;
    }
}
