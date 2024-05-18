package de.labystudio.gui.extras;

import de.labystudio.labymod.ConfigManager;
import de.labystudio.utils.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.MathHelper;

public class SliderCoords extends GuiButton
{
    private float sliderValue = 1.0F;
    private boolean dragging;
    private final float field_146132_r = 0.0F;
    private final float field_146131_s = 1.0F;
    private final float size;
    private int valueMin;
    private float valueMax;
    private float valueStep;
    private static final String __OBFID = "CL_00000680";

    public SliderCoords(int x, int y, int size)
    {
        super(-3, x, y, size + 5, 20, "");
        this.size = (float)(size - 5);
        Minecraft minecraft = Minecraft.getMinecraft();
        this.updateText();
        this.valueMin = 0;
        this.valueMax = 4.0F;
        this.valueStep = 1.0F;
    }

    public int getX()
    {
        return this.xPosition;
    }

    public int getY()
    {
        return this.yPosition;
    }

    /**
     * Returns 0 if the button is disabled, 1 if the mouse is NOT hovering over this button and 2 if it IS hovering over
     * this button.
     */
    protected int getHoverState(boolean mouseOver)
    {
        return 0;
    }

    public float denormalizeValue(float p_148262_1_)
    {
        return this.snapToStepClamp((float)this.valueMin + (this.valueMax - (float)this.valueMin) * MathHelper.clamp_float(p_148262_1_, 0.0F, 1.0F));
    }

    public float snapToStepClamp(float p_148268_1_)
    {
        p_148268_1_ = this.snapToStep(p_148268_1_);
        return MathHelper.clamp_float(p_148268_1_, (float)this.valueMin, this.valueMax);
    }

    protected float snapToStep(float p_148264_1_)
    {
        if (this.valueStep > 0.0F)
        {
            p_148264_1_ = this.valueStep * (float)Math.round(p_148264_1_ / this.valueStep);
        }

        return p_148264_1_;
    }

    public void updateText()
    {
        if (ConfigManager.settings.truncateCoords == 0)
        {
            this.displayString = Color.cl("c") + ConfigManager.settings.truncateCoords + " digits";
        }
        else
        {
            this.displayString = ConfigManager.settings.truncateCoords + " digits";
        }
    }

    /**
     * Fired when the mouse button is dragged. Equivalent of MouseListener.mouseDragged(MouseEvent e).
     */
    protected void mouseDragged(Minecraft mc, int mouseX, int mouseY)
    {
        if (this.visible)
        {
            if (this.dragging)
            {
                this.sliderValue = (float)(mouseX - (this.getX() + 4)) / (float)(this.width - 8);
                this.sliderValue = MathHelper.clamp_float(this.sliderValue, 0.0F, 1.0F);
                this.sliderValue = (float)(mouseX - (this.xPosition + 4)) / (float)(this.width - 8);
                this.sliderValue = MathHelper.clamp_float(this.sliderValue, 0.0F, 1.0F);
                this.sliderValue = this.denormalizeValue(this.sliderValue);
                ConfigManager.settings.truncateCoords = (int)this.sliderValue;
                this.updateText();
            }

            mc.getTextureManager().bindTexture(buttonTextures);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            this.drawTexturedModalRect(this.getX() + (int)((double)((float)(ConfigManager.settings.truncateCoords * 100) / this.valueMax) * 0.72D), this.getY(), 0, 66, 4, 20);
            this.drawTexturedModalRect(this.getX() + (int)((double)((float)(ConfigManager.settings.truncateCoords * 100) / this.valueMax) * 0.72D) + 4, this.getY(), 196, 66, 4, 20);
        }
    }

    /**
     * Returns true if the mouse has been pressed on this control. Equivalent of MouseListener.mousePressed(MouseEvent
     * e).
     */
    public boolean mousePressed(Minecraft mc, int mouseX, int mouseY)
    {
        if (super.mousePressed(mc, mouseX, mouseY))
        {
            this.sliderValue = (float)(mouseX - (this.getX() + 4)) / (float)(this.width - 8);
            this.sliderValue = MathHelper.clamp_float(this.sliderValue, 0.0F, 1.0F);
            ConfigManager.settings.truncateCoords = (int)(this.sliderValue * this.valueMax);
            this.updateText();
            this.dragging = true;
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Fired when the mouse button is released. Equivalent of MouseListener.mouseReleased(MouseEvent e).
     */
    public void mouseReleased(int mouseX, int mouseY)
    {
        this.dragging = false;
        ConfigManager.save();
    }
}
