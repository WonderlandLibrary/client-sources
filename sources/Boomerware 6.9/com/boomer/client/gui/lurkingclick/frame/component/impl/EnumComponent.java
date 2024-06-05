package com.boomer.client.gui.lurkingclick.frame.component.impl;

import com.boomer.client.utils.MouseUtil;
import com.boomer.client.utils.RenderUtil;
import com.boomer.client.utils.value.impl.EnumValue;
import com.boomer.client.gui.lurkingclick.frame.component.Component;
import net.minecraft.client.gui.ScaledResolution;
import org.apache.commons.lang3.StringUtils;
import org.lwjgl.opengl.GL11;

import java.awt.*;

/**
 * made by oHare for BoomerWare
 *
 * @since 8/19/2019
 **/
public class EnumComponent extends Component {
    private EnumValue enumValue;
    private boolean extended;
    private float defaultHeight;

    public EnumComponent(EnumValue enumValue, float parentX, float parentY, float offsetX, float offsetY, float width, float height) {
        super(enumValue.getLabel(), parentX, parentY, offsetX, offsetY, width, height);
        this.enumValue = enumValue;
        this.defaultHeight = height;
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
        RenderUtil.drawBorderedRect(getFinishedX() + 4, getFinishedY() + 5, getWidth() - 8, getHeight() - 6, 1, -1, 0x00000000);
        GL11.glPushMatrix();
        GL11.glScalef(0.8f, 0.8f, 0.8f);
        mc.fontRendererObj.drawStringWithShadow(getLabel(), (getFinishedX() + 4 + getWidth() / 2 - mc.fontRendererObj.getStringWidth(getLabel()) / 2) * 1.25f, (getFinishedY() - 2f) * 1.25f, -1);
        GL11.glPopMatrix();
        final boolean hovered = MouseUtil.mouseWithinBounds(mouseX, mouseY, getFinishedX() + 5, getFinishedY() + 6, getWidth() - 10, defaultHeight - 11);
        if (hovered)
            RenderUtil.drawRect(getFinishedX() + 5, getFinishedY() + 6, getWidth() - 10, defaultHeight -9, new Color(0, 0, 0, 120).getRGB());
        mc.fontRendererObj.drawStringWithShadow(StringUtils.capitalize(enumValue.getValue().toString().toLowerCase()), getFinishedX() + 2 + getWidth() / 2 - mc.fontRendererObj.getStringWidth(StringUtils.capitalize(enumValue.getValue().toString().toLowerCase())) / 2, getFinishedY() + 7, 0xff4F4FA8);
        float additionHeight = defaultHeight - 2;
        if (extended) {
            for (Enum enoom : enumValue.getConstants()) {
                if (enoom != enumValue.getValue()) {
                    boolean hoveredEnum = MouseUtil.mouseWithinBounds(mouseX, mouseY, getFinishedX() + 5, getFinishedY() + additionHeight - 2, getWidth() - 10, 12);
                    if (hoveredEnum)
                        RenderUtil.drawRect(getFinishedX() + 5, getFinishedY() + additionHeight - 2, getWidth() - 10, 11, new Color(0, 0, 0, 120).getRGB());
                    mc.fontRendererObj.drawStringWithShadow(StringUtils.capitalize(enoom.name().toLowerCase()), getFinishedX() + 2 + getWidth() / 2 - mc.fontRendererObj.getStringWidth(StringUtils.capitalize(enoom.name().toLowerCase())) / 2, getFinishedY() + additionHeight, -1);
                    additionHeight += 11;
                }
            }
            if (getHeight() != additionHeight + 2) setHeight(additionHeight + 2);
        } else if (getHeight() != defaultHeight) {
            setHeight(defaultHeight);
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        final boolean hovered = MouseUtil.mouseWithinBounds(mouseX, mouseY, getFinishedX() + 5, getFinishedY() + 6, getWidth() - 10, defaultHeight - 11);
        switch (mouseButton) {
            case 0:
                if (hovered) extended ^= true;
                float additionHeight = defaultHeight - 2;
                if (extended) {
                    for (Enum enoom : enumValue.getConstants()) {
                        if (enoom != enumValue.getValue()) {
                            boolean hoveredEnum = MouseUtil.mouseWithinBounds(mouseX, mouseY, getFinishedX() + 5, getFinishedY() + additionHeight - 2, getWidth() - 10, 11);
                            if (hoveredEnum) {
                                enumValue.setValue(enoom);
                                extended = false;
                            }
                            additionHeight += 11;
                        }
                    }
                }
                break;
            default:
                break;
        }
    }
}
