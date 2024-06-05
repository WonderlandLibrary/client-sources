package koks.gui.clickgui.commonvalue.elements;

import koks.Koks;
import koks.gui.clickgui.commonvalue.CommonValue;
import koks.utilities.value.ColorPicker;

import java.awt.*;
import java.io.IOException;

/**
 * @author avox | lmao
 * @since on 29/07/2020.
 */
public class ElementColorPicker extends Element {

    public ColorPicker colorPicker = new ColorPicker();
    public boolean visibleColorPicker = false;

    public ElementColorPicker(CommonValue setting) {
        this.setting = setting;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {

        mc.fontRendererObj.drawStringWithShadow(setting.getName(), x + 3, y, -1);
        colorPicker.drawScreen(mouseX, mouseY, x + 2, y + mc.fontRendererObj.FONT_HEIGHT, width, setting);

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        if (mouseX > x && mouseX < x + width && mouseY > y && mouseY < y + mc.fontRendererObj.FONT_HEIGHT && mouseButton == 0) {
            this.visibleColorPicker = !this.visibleColorPicker;

        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);
    }

}
