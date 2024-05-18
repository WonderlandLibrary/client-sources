package mods.itemphysic;

import java.text.DecimalFormat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import org.lwjgl.opengl.GL11;

public class GuiSlideControl extends GuiButton
{
    public String label;
    public float curValue;
    public float minValue;
    public float maxValue;
    public boolean isSliding;
    public boolean useIntegers;
    private static DecimalFormat numFormat = new DecimalFormat("#.00");

    public GuiSlideControl(int id, int x, int y, int width, int height, String displayString, float minVal, float maxVal, float curVal, boolean useInts)
    {
        super(id, x, y, width, height, useInts ? displayString + (int)curVal : displayString + numFormat.format((double)curVal));
        this.label = displayString;
        this.minValue = minVal;
        this.maxValue = maxVal;
        this.curValue = (curVal - minVal) / (maxVal - minVal);
        this.useIntegers = useInts;
    }

    public float GetValueAsFloat()
    {
        return (this.maxValue - this.minValue) * this.curValue + this.minValue;
    }

    public int GetValueAsInt()
    {
        return (int)((this.maxValue - this.minValue) * this.curValue + this.minValue);
    }

    protected float roundValue(float value)
    {
        value = 0.01F * (float)Math.round(value / 0.01F);
        return value;
    }

    public String GetLabel()
    {
        return this.useIntegers ? this.label + this.GetValueAsInt() : this.label + numFormat.format((double)this.GetValueAsFloat());
    }

    protected void SetLabel()
    {
        this.displayString = this.GetLabel();
    }

    /**
     * Returns 0 if the button is disabled, 1 if the mouse is NOT hovering over this button and 2 if it IS hovering over
     * this button.
     */
    protected int getHoverState(boolean isMouseOver)
    {
        return 0;
    }

    /**
     * Fired when the mouse button is dragged. Equivalent of MouseListener.mouseDragged(MouseEvent e).
     */
    protected void mouseDragged(Minecraft mc, int mousePosX, int mousePosY)
    {
        if (this.visible)
        {
            if (this.isSliding)
            {
                this.curValue = this.roundValue((float)(mousePosX - (this.xPosition + 4)) / (float)(this.width - 8));

                if (this.curValue < 0.0F)
                {
                    this.curValue = 0.0F;
                }

                if (this.curValue > 1.0F)
                {
                    this.curValue = 1.0F;
                }

                this.SetLabel();
            }

            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.drawTexturedModalRect(this.xPosition + (int)(this.curValue * (float)(this.width - 8)), this.yPosition, 0, 66, 4, 20);
            this.drawTexturedModalRect(this.xPosition + (int)(this.curValue * (float)(this.width - 8)) + 4, this.yPosition, 196, 66, 4, 20);
        }
    }

    /**
     * Returns true if the mouse has been pressed on this control. Equivalent of MouseListener.mousePressed(MouseEvent
     * e).
     */
    public boolean mousePressed(Minecraft mc, int mousePosX, int mousePosY)
    {
        if (super.mousePressed(mc, mousePosX, mousePosY))
        {
            this.curValue = this.roundValue((float)(mousePosX - (this.xPosition + 4)) / (float)(this.width - 8));

            if (this.curValue < 0.0F)
            {
                this.curValue = 0.0F;
            }

            if (this.curValue > 1.0F)
            {
                this.curValue = 1.0F;
            }

            this.SetLabel();
            this.isSliding = true;
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
    public void mouseReleased(int mousePosX, int mousePosY)
    {
        this.isSliding = false;
    }
}
