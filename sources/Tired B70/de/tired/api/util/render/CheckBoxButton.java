package de.tired.api.util.render;

import de.tired.api.extension.Extension;
import de.tired.api.util.font.FontManager;
import de.tired.interfaces.IHook;
import de.tired.module.impl.list.visual.ClickGUI;
import net.minecraft.client.gui.Gui;

import java.awt.*;

public class CheckBoxButton extends Gui implements IHook {

    private final double WIDTH = 20, HEIGHT = 20;

    public int x, y;

    public int id;

    public boolean isState;

    public String text;

    public CheckBoxButton(String text, int id, int x, int y) {
        this.x = x;
        this.y = y;
        this.id = id;
        this.text = text;
    }

    public void drawButton() {

        Gui.drawRect(x, y, x + WIDTH, y + HEIGHT, isState ? ClickGUI.getInstance().colorPickerSetting.ColorPickerC.getRGB() : new Color(211, 47, 47).getRGB());

        if (isState) {
            Extension.EXTENSION.getGenerallyProcessor().renderProcessor.drawCheckMark(x, y + 3, 20, -1);
        } else {
            FontManager.moonF.drawString3("X", (float) (x + 7), (float) (y + 10), -1);
        }

        MC.fontRendererObj.drawStringWithShadow(text, (float) (x + WIDTH) - 37, (float) y + 6 + (float) HEIGHT, -1);


    }

    public boolean over(int mouseX, int mouseY) {
        return mouseX >= x && mouseY >= y && mouseX < x + WIDTH && mouseY < y + HEIGHT;
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseKey) {
        if (over(mouseX, mouseY)) {
            if (mouseKey == 0) {
                this.isState = !isState;
            }
        }
    }

}
