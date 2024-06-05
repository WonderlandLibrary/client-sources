package com.boomer.client.gui.lurkingclick.frame.component.impl;

import com.boomer.client.gui.lurkingclick.frame.component.Component;
import com.boomer.client.utils.MouseUtil;
import com.boomer.client.utils.RenderUtil;
import com.boomer.client.utils.TimerUtil;
import com.boomer.client.utils.value.impl.ColorValue;
import net.minecraft.client.gui.ScaledResolution;

import java.awt.*;

/**
 * made by oHare for BoomerWare
 *
 * @since 8/19/2019
 **/
public class ColorComponent extends Component {
    private ColorValue colorValue;
    private boolean pressedhue;
    private int pos;
    private float hue;
    private float saturation;
    private float brightness;
    private boolean rainbow;
    private int rainbowI;
    private TimerUtil timer = new TimerUtil();
    public ColorComponent(ColorValue colorValue, float parentX, float parentY, float offsetX, float offsetY, float width, float height) {
        super(colorValue.getLabel(), parentX, parentY, offsetX, offsetY, width, height);
        this.colorValue = colorValue;
        float[] hsb = new float[3];
        final Color clr = new Color(colorValue.getValue());
        hsb = Color.RGBtoHSB(clr.getRed(), clr.getGreen(), clr.getBlue(), hsb);
        this.hue = hsb[0];
        this.saturation = hsb[1];
        this.brightness = hsb[2];
    }

    public void updatePosition(float posX, float posY) {
        setParentX(posX);
        setParentY(posY);
        setFinishedX(posX + getOffsetX());
        setFinishedY(posY + getOffsetY());
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks, ScaledResolution scaledResolution) {
        super.drawScreen(mouseX, mouseY, partialTicks, scaledResolution);
        RenderUtil.drawRect(getFinishedX(), getFinishedY(), getWidth(), getHeight(), new Color(0, 0, 0, 120).getRGB());
        if (rainbow&& timer.sleep(25)) {
            if (rainbowI < getWidth()) {
                rainbowI++;
            } else if (rainbowI + 1 >= getWidth()) {
                rainbowI = 0;
            }
        }
        for (int i = 0; i < getWidth(); i++) {
            float posx = getFinishedX() + i;
            int color = Color.getHSBColor(i / getWidth(), saturation, brightness).getRGB();
            RenderUtil.drawRect(posx,getFinishedY(),  1,  getHeight(), color);
            if (i == pos) {
                RenderUtil.drawRect(posx, getFinishedY(),  1,  getHeight(), -1);
            }
            if (mouseX == posx && !rainbow) {
                if (pressedhue) {
                    colorValue.setValue(color);
                    pos = i;
                    hue = i / getWidth();
                }
            }
            if (rainbow && rainbowI == i) {
                colorValue.setValue(color);
                pos = i;
                hue = i / getWidth();
            }
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        final boolean hovered = MouseUtil.mouseWithinBounds(mouseX, mouseY, getFinishedX(), getFinishedY(), getWidth(), getHeight());
        switch (mouseButton) {
            case 0:
                if (hovered) {
                    pressedhue = true;
                }
                break;
            case 2:
                if (hovered) {
                    if (rainbow) {
                        rainbowI = 0;
                        rainbow = false;
                    } else {
                        rainbowI = pos;
                        rainbow = true;
                    }
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        switch (mouseButton) {
            case 0:
                if (pressedhue) {
                    pressedhue = false;
                }
                break;
            default:
                break;
        }
    }
}
