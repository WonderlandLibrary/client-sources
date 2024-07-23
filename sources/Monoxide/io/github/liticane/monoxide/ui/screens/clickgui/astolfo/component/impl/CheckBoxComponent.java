package io.github.liticane.monoxide.ui.screens.clickgui.astolfo.component.impl;

import io.github.liticane.monoxide.util.render.font.FontStorage;
import net.minecraft.client.gui.FontRenderer;
import io.github.liticane.monoxide.value.Value;
import io.github.liticane.monoxide.value.impl.BooleanValue;
import io.github.liticane.monoxide.util.render.RenderUtil;

import java.awt.*;

public class CheckBoxComponent extends ValueComponent {

    public CheckBoxComponent(Value value, float posX, float posY, float baseWidth, float baseHeight) {
        super(value, posX, posY, baseWidth, baseHeight);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY) {
        FontRenderer normal = FontStorage.getInstance().findFont("SFUI Medium", 16);
        normal.drawString(value.getName(), getPosX() + 5 + getAddX(), getPosY() + getBaseHeight() / 2 - normal.FONT_HEIGHT / 2, -1);
        normal.drawString("X", getPosX() + this.getBaseWidth() - 5 - normal.getStringWidthInt("X") + getAddX(), getPosY() + getBaseHeight() / 2 - normal.FONT_HEIGHT / 2, !((boolean)value.getValue()) ? new Color(200, 200, 200).getRGB() : -1);
    }

    @Override
    public void mouseClick(int mouseX, int mouseY, int mouseButton) {
        if(RenderUtil.isHovered(mouseX, mouseY, this.getPosX(), this.getPosY(), this.getBaseWidth(), this.getBaseHeight())) {
            BooleanValue booleanValue = (BooleanValue) value;
            booleanValue.setValue(!booleanValue.getValue());
        }
    }
}
