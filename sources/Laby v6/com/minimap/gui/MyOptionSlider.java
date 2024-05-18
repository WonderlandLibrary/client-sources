package com.minimap.gui;

import net.minecraft.client.gui.*;
import com.minimap.settings.*;
import com.minimap.*;
import net.minecraft.client.*;
import net.minecraft.util.*;
import java.io.*;
import net.minecraft.client.renderer.*;

public class MyOptionSlider extends GuiButton
{
    private float sliderValue;
    public boolean dragging;
    private ModOptions options;
    
    public MyOptionSlider(final int p_i45016_1_, final int p_i45016_2_, final int p_i45016_3_, final ModOptions p_i45016_4_) {
        this(p_i45016_1_, p_i45016_2_, p_i45016_3_, p_i45016_4_, 0.0f, 1.0f);
    }
    
    public MyOptionSlider(final int p_i45017_1_, final int p_i45017_2_, final int p_i45017_3_, final ModOptions p_i45017_4_, final float p_i45017_5_, final float p_i45017_6_) {
        super(p_i45017_1_, p_i45017_2_, p_i45017_3_, 150, 20, "");
        this.sliderValue = 1.0f;
        this.options = p_i45017_4_;
        this.sliderValue = p_i45017_4_.normalizeValue(XaeroMinimap.getSettings().getOptionFloatValue(p_i45017_4_));
        super.displayString = XaeroMinimap.getSettings().getKeyBinding(p_i45017_4_);
    }
    
    @Override
    protected int getHoverState(final boolean mouseOver) {
        return 0;
    }
    
    @Override
    protected void mouseDragged(final Minecraft mc, final int mouseX, final int mouseY) {
        if (super.visible) {
            if (this.dragging) {
                this.sliderValue = (mouseX - (super.xPosition + 4)) / (super.width - 8);
                this.sliderValue = MathHelper.clamp_float(this.sliderValue, 0.0f, 1.0f);
                final float f = this.options.denormalizeValue(this.sliderValue);
                try {
                    XaeroMinimap.getSettings().setOptionFloatValue(this.options, f);
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
                this.sliderValue = this.options.normalizeValue(f);
                super.displayString = XaeroMinimap.getSettings().getKeyBinding(this.options);
            }
            mc.getTextureManager().bindTexture(GuiButton.buttonTextures);
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            this.drawTexturedModalRect(super.xPosition + (int)(this.sliderValue * (super.width - 8)), super.yPosition, 0, 66, 4, 20);
            this.drawTexturedModalRect(super.xPosition + (int)(this.sliderValue * (super.width - 8)) + 4, super.yPosition, 196, 66, 4, 20);
        }
    }
    
    @Override
    public boolean mousePressed(final Minecraft mc, final int mouseX, final int mouseY) {
        if (super.mousePressed(mc, mouseX, mouseY)) {
            this.sliderValue = (mouseX - (super.xPosition + 4)) / (super.width - 8);
            this.sliderValue = MathHelper.clamp_float(this.sliderValue, 0.0f, 1.0f);
            try {
                XaeroMinimap.getSettings().setOptionFloatValue(this.options, this.options.denormalizeValue(this.sliderValue));
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            super.displayString = XaeroMinimap.getSettings().getKeyBinding(this.options);
            return this.dragging = true;
        }
        return false;
    }
    
    @Override
    public void mouseReleased(final int mouseX, final int mouseY) {
        this.dragging = false;
    }
}
