package com.boomer.client.gui.lurkingclick.frame.component.impl;

import com.boomer.client.gui.lurkingclick.frame.component.Component;
import com.boomer.client.utils.MathUtils;
import com.boomer.client.utils.MouseUtil;
import com.boomer.client.utils.RenderUtil;
import com.boomer.client.utils.value.impl.NumberValue;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;

import java.awt.*;

/**
 * made by oHare for BoomerWare
 *
 * @since 8/19/2019
 **/
public class SliderComponent extends Component {
    private NumberValue numberValue;
    private boolean sliding;

    public SliderComponent(NumberValue numberValue, float parentX, float parentY, float offsetX, float offsetY, float width, float height) {
        super(numberValue.getLabel(), parentX, parentY, offsetX, offsetY, width, height);
        this.numberValue = numberValue;
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
        final boolean hovered = MouseUtil.mouseWithinBounds(mouseX, mouseY, getFinishedX() + 4, getFinishedY() + 5, getWidth() - 8, getHeight() - 8);
        final float startX = getFinishedX() + 4.5f;
        final float sliderwidth = getWidth() - 10;
        float length = MathHelper.floor_double(((numberValue.getValue()).floatValue() - numberValue.getMinimum().floatValue()) / (numberValue.getMaximum().floatValue() - numberValue.getMinimum().floatValue()) * sliderwidth);
        RenderUtil.drawRect(getFinishedX(), getFinishedY(), getWidth(), getHeight(), new Color(0, 0, 0, 120).getRGB());
        RenderUtil.drawBorderedRect(getFinishedX() + 4, getFinishedY() + 5, getWidth() - 8, getHeight() - 8, 1, -1, 0x00000000);
        RenderUtil.drawRect(getFinishedX() + 5f, getFinishedY() + 6f, length, getHeight() - 10, hovered ? new Color(0xff4F4FA8).darker().getRGB():0xff4F4FA8);
        mc.fontRendererObj.drawStringWithShadow(numberValue.getValue().toString(),getFinishedX() + 2 + getWidth() / 2 - mc.fontRendererObj.getStringWidth(numberValue.getValue().toString()) / 2,getFinishedY() + 6.5f,-1);
        GL11.glPushMatrix();
        GL11.glScalef(0.8f,0.8f,0.8f);
        mc.fontRendererObj.drawStringWithShadow(getLabel(),(getFinishedX() + 4 + getWidth() / 2 - mc.fontRendererObj.getStringWidth(getLabel()) / 2) * 1.25f,(getFinishedY() - 2f) * 1.25f,-1);
        GL11.glPopMatrix();
        if (sliding) {
            if (numberValue.getValue() instanceof Double) {
                numberValue.setValue(MathUtils.round(((mouseX - startX) * (numberValue.getMaximum().doubleValue() - numberValue.getMinimum().doubleValue()) / sliderwidth + numberValue.getMinimum().doubleValue()), 2));
            }
            if (numberValue.getValue() instanceof Float) {
                numberValue.setValue((float) MathUtils.round(((mouseX - startX) * (numberValue.getMaximum().floatValue() - numberValue.getMinimum().floatValue()) / sliderwidth + numberValue.getMinimum().floatValue()), 2));
            }
            if (numberValue.getValue() instanceof Long) {
                numberValue.setValue((long) MathUtils.round(((mouseX - startX) * (numberValue.getMaximum().longValue() - numberValue.getMinimum().longValue()) / sliderwidth + numberValue.getMinimum().longValue()), 2));
            }
            if (numberValue.getValue() instanceof Integer) {
                numberValue.setValue((int) MathUtils.round(((mouseX - startX) * (numberValue.getMaximum().intValue() - numberValue.getMinimum().intValue()) / sliderwidth + numberValue.getMinimum().intValue()), 2));
            }
            if (numberValue.getValue() instanceof Short) {
                numberValue.setValue((short) MathUtils.round(((mouseX - startX) * (numberValue.getMaximum().shortValue() - numberValue.getMinimum().shortValue()) / sliderwidth + numberValue.getMinimum().shortValue()), 2));
            }
            if (numberValue.getValue() instanceof Byte) {
                numberValue.setValue((byte) MathUtils.round(((mouseX - startX) * (numberValue.getMaximum().byteValue() - numberValue.getMinimum().byteValue()) / sliderwidth + numberValue.getMinimum().byteValue()), 2));
            }
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        final boolean hovered = MouseUtil.mouseWithinBounds(mouseX, mouseY, getFinishedX() + 4, getFinishedY() + 5, getWidth() - 8, getHeight() - 8);
        switch (mouseButton) {
            case 0:
                if (hovered) sliding = true;
                break;
            default:
                break;
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        super.mouseReleased(mouseX, mouseY, mouseButton);
        switch (mouseButton) {
            case 0:
                if (sliding) sliding = false;
                break;
            default:
                break;
        }
    }
}
