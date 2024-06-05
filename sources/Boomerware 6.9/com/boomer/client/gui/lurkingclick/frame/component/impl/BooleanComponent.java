package com.boomer.client.gui.lurkingclick.frame.component.impl;

import com.boomer.client.utils.MouseUtil;
import com.boomer.client.utils.RenderUtil;
import com.boomer.client.utils.value.impl.BooleanValue;
import com.boomer.client.gui.lurkingclick.frame.component.Component;
import net.minecraft.client.gui.ScaledResolution;

import java.awt.*;

/**
 * made by oHare for BoomerWare
 *
 * @since 8/19/2019
 **/
public class BooleanComponent extends Component {
    private BooleanValue booleanValue;

    public BooleanComponent(BooleanValue booleanValue, float parentX, float parentY, float offsetX, float offsetY, float width, float height) {
        super(booleanValue.getLabel(), parentX, parentY, offsetX, offsetY, width, height);
        this.booleanValue = booleanValue;
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
        final boolean hovered = MouseUtil.mouseWithinBounds(mouseX, mouseY, getFinishedX() + getWidth() - 12, getFinishedY() + 1, 10, 10);
        RenderUtil.drawRect(getFinishedX(), getFinishedY(), getWidth(), getHeight(), new Color(0, 0, 0, 120).getRGB());
        mc.fontRendererObj.drawStringWithShadow(getLabel(), getFinishedX() + 2, getFinishedY() + getHeight() / 2 - mc.fontRendererObj.FONT_HEIGHT / 2, -1);
        RenderUtil.drawBorderedRect(getFinishedX() + getWidth() - 12, getFinishedY() + 1, 10, 10, 1, -1, booleanValue.isEnabled() ? (hovered ? new Color(0xff4F4FA8).darker().getRGB():0xff4F4FA8) : 0x00000000);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        final boolean hovered = MouseUtil.mouseWithinBounds(mouseX, mouseY, getFinishedX() + getWidth() - 12, getFinishedY() + 1, 10, 10);
        switch (mouseButton) {
            case 0:
                if (hovered) booleanValue.setEnabled(!booleanValue.isEnabled());
                break;
            default:
                break;
        }
    }
}
