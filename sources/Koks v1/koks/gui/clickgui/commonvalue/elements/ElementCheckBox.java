package koks.gui.clickgui.commonvalue.elements;


import koks.gui.clickgui.commonvalue.CommonValue;
import net.minecraft.client.gui.Gui;

import java.awt.*;
import java.io.IOException;

public class ElementCheckBox extends Element {

    public ElementCheckBox(CommonValue setting) {
        this.setting = setting;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        fr.drawString(this.setting.getName(), this.x + 13, this.y + this.height / 2 - fr.FONT_HEIGHT / 2, 0xFFFFFFFF);
        Gui.drawRect(this.x, this.y + 5, this.x + 10, this.y + this.height - 5, setting.isToggled() ? Color.GRAY.getRGB() : 0xbb000000);
        getRenderUtils().drawOutlineRect(this.x, this.y + 5, this.x + 10, this.y + this.height - 5, 1, Color.BLACK);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        if (mouseButton == 0 && mouseX >= this.x && mouseX <= this.x + 10 && mouseY >= this.y + 5 && mouseY <= this.y + this.height - 5) {
            this.setting.setToggled(!this.setting.isToggled());
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }
}