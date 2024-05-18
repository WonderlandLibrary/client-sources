package de.labystudio.gui.extras;

import de.labystudio.labymod.ConfigManager;
import de.labystudio.utils.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.MathHelper;

public class SliderColor extends GuiButton
{
    private float sliderValue = 1.0F;
    public boolean dragging;
    private final float field_146132_r = 0.0F;
    private final float field_146131_s = 1.0F;
    private final float size;
    private int valueMin;
    private int color;
    private int colorID;
    private float valueMax;
    private float valueStep;
    private static final String __OBFID = "CL_00000680";

    public SliderColor(int id, int x, int y, int size, int color)
    {
        super(id, x, y, size + 5, 20, "");
        this.size = (float)(size - 5);
        this.colorID = color;
        this.color = this.getColor();
        Minecraft minecraft = Minecraft.getMinecraft();
        this.updateText();
        this.valueMin = 0;
        this.valueMax = 15.0F;
        this.valueStep = 1.0F;
    }

    public void setColor(int newColor)
    {
        switch (this.colorID)
        {
            case 1:
                ConfigManager.settings.color1 = Color.IDToColor(newColor);
                break;

            case 2:
                ConfigManager.settings.color2 = Color.IDToColor(newColor);
                break;

            case 3:
                ConfigManager.settings.color3 = Color.IDToColor(newColor);
                break;

            case 4:
                ConfigManager.settings.color4 = Color.IDToColor(newColor);
                break;

            case 5:
                ConfigManager.settings.color5 = Color.IDToColor(newColor);
                break;

            case 6:
                ConfigManager.settings.color6 = Color.IDToColor(newColor);
                break;

            case 7:
                ConfigManager.settings.color7 = Color.IDToColor(newColor);
                break;

            case 8:
                ConfigManager.settings.color8 = Color.IDToColor(newColor);
                break;

            case 9:
                ConfigManager.settings.color9 = Color.IDToColor(newColor);
                break;

            case 10:
                ConfigManager.settings.color10 = Color.IDToColor(newColor);
        }
    }

    public int getColor()
    {
        switch (this.colorID)
        {
            case 1:
                return Color.colorToID(ConfigManager.settings.color1);

            case 2:
                return Color.colorToID(ConfigManager.settings.color2);

            case 3:
                return Color.colorToID(ConfigManager.settings.color3);

            case 4:
                return Color.colorToID(ConfigManager.settings.color4);

            case 5:
                return Color.colorToID(ConfigManager.settings.color5);

            case 6:
                return Color.colorToID(ConfigManager.settings.color6);

            case 7:
                return Color.colorToID(ConfigManager.settings.color7);

            case 8:
                return Color.colorToID(ConfigManager.settings.color8);

            case 9:
                return Color.colorToID(ConfigManager.settings.color9);

            case 10:
                return Color.colorToID(ConfigManager.settings.color10);

            default:
                return 0;
        }
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
        this.color = this.getColor();
        this.displayString = "";
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
                this.setColor((int)this.sliderValue);
                this.updateText();
            }

            mc.getTextureManager().bindTexture(buttonTextures);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

            for (int i = 0; i <= 2; ++i)
            {
                for (int j = 0; j <= 15; ++j)
                {
                    if (j == this.getColor())
                    {
                        this.drawString(mc.fontRendererObj, Color.IDToColor(j) + Color.clc("l") + "\u258c", this.getX() + (int)((double)((float)(j * 100) / this.valueMax) * 0.76D) + 2, this.getY() + i * 5 + 1, 0);
                    }
                    else
                    {
                        this.drawString(mc.fontRendererObj, Color.IDToColor(j) + "*", this.getX() + (int)((double)((float)(j * 100) / this.valueMax) * 0.76D) + 2, this.getY() + 5 + 1, 0);
                    }
                }
            }
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
            this.setColor((int)this.sliderValue);
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
