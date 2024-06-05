package net.minecraft.src;

import net.minecraft.client.*;
import org.lwjgl.opengl.*;

public class GuiSlider extends GuiButton
{
    public float sliderValue;
    public boolean dragging;
    private EnumOptions idFloat;
    
    public GuiSlider(final int par1, final int par2, final int par3, final EnumOptions par4EnumOptions, final String par5Str, final float par6) {
        super(par1, par2, par3, 150, 20, par5Str);
        this.sliderValue = 1.0f;
        this.dragging = false;
        this.idFloat = null;
        this.idFloat = par4EnumOptions;
        this.sliderValue = par6;
    }
    
    @Override
    protected int getHoverState(final boolean par1) {
        return 0;
    }
    
    @Override
    protected void mouseDragged(final Minecraft par1Minecraft, final int par2, final int par3) {
        if (this.drawButton) {
            if (this.dragging) {
                this.sliderValue = (par2 - (this.xPosition + 4)) / (this.width - 8);
                if (this.sliderValue < 0.0f) {
                    this.sliderValue = 0.0f;
                }
                if (this.sliderValue > 1.0f) {
                    this.sliderValue = 1.0f;
                }
                par1Minecraft.gameSettings.setOptionFloatValue(this.idFloat, this.sliderValue);
                this.displayString = par1Minecraft.gameSettings.getKeyBinding(this.idFloat);
            }
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            this.drawTexturedModalRect(this.xPosition + (int)(this.sliderValue * (this.width - 8)), this.yPosition, 0, 66, 4, 20);
            this.drawTexturedModalRect(this.xPosition + (int)(this.sliderValue * (this.width - 8)) + 4, this.yPosition, 196, 66, 4, 20);
        }
    }
    
    @Override
    public boolean mousePressed(final Minecraft par1Minecraft, final int par2, final int par3) {
        if (super.mousePressed(par1Minecraft, par2, par3)) {
            this.sliderValue = (par2 - (this.xPosition + 4)) / (this.width - 8);
            if (this.sliderValue < 0.0f) {
                this.sliderValue = 0.0f;
            }
            if (this.sliderValue > 1.0f) {
                this.sliderValue = 1.0f;
            }
            par1Minecraft.gameSettings.setOptionFloatValue(this.idFloat, this.sliderValue);
            this.displayString = par1Minecraft.gameSettings.getKeyBinding(this.idFloat);
            return this.dragging = true;
        }
        return false;
    }
    
    @Override
    public void mouseReleased(final int par1, final int par2) {
        this.dragging = false;
    }
}
