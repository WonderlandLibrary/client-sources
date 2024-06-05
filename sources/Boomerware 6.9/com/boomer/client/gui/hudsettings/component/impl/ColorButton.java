package com.boomer.client.gui.hudsettings.component.impl;

import com.boomer.client.utils.value.impl.ColorValue;
import com.boomer.client.gui.hudsettings.component.Component;


/**
 * made by Xen for BoomerWare
 *
 * @since 7/26/2019
 **/
public class ColorButton extends Component {
    private ColorValue colorValue;

    public ColorButton(ColorValue colorValue, float posX, float posY) {
        super(colorValue.getLabel(), posX, posY);
        this.colorValue = colorValue;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        super.mouseReleased(mouseX, mouseY, mouseButton);

    }
}
