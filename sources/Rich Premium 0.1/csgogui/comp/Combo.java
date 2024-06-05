package csgogui.comp;

import clickgui.setting.Setting;
import csgogui.CSGOGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import white.floor.features.Feature;
import white.floor.features.impl.display.ClickGUI;
import white.floor.font.Fonts;
import white.floor.helpers.DrawHelper;

import java.awt.*;

public class Combo extends Comp {

    public Combo(double x, double y, CSGOGui parent, Feature module, Setting setting) {
        this.x = x;
        this.y = y;
        this.parent = parent;
        this.module = module;
        this.setting = setting;
    }


    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        if (isInside(mouseX, mouseY, (float) (parent.posX + x + 265.5) - Fonts.urw16.getStringWidth(setting.getValString()) - 2, (float) parent.posY + (float)  y + 0.5f, (float) (parent.posX + x + 269.5),  (float) parent.posY + (float) y + 10.5f) && mouseButton == 0) {
            int max = setting.getOptions().size();
            if (parent.modeIndex + 1 >= max) {
                parent.modeIndex = 0;
            } else {
                parent.modeIndex++;
            }
            setting.setValString(setting.getOptions().get(parent.modeIndex));
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY) {
        super.drawScreen(mouseX, mouseY);

        DrawHelper.drawSmoothRect((float) (parent.posX + x + 266.5) - Fonts.urw16.getStringWidth(setting.getValString()) - 2, (float) parent.posY + (float)  y + 0.5f, (float) (parent.posX + x + 269.5),  (float) parent.posY + (float) y + 10.5f, isInside(mouseX, mouseY, (float) (parent.posX + x + 265.5) - Fonts.urw16.getStringWidth(setting.getValString()) - 2, (float) parent.posY + (float)  y + 0.5f, (float) (parent.posX + x + 269.5),  (float) parent.posY + (float) y + 10.5f) ? new Color(90,90,90, ClickGUI.opacite).getRGB() : new Color(45,45,45, ClickGUI.opacite).getRGB());
        DrawHelper.drawSmoothRect((float) (parent.posX + x + 267) - Fonts.urw16.getStringWidth(setting.getValString()) - 2, (float) parent.posY + (float)  y + 1, (float) (parent.posX + x + 269),  (float) parent.posY + (float) y + 10, new Color(28, 28, 28, ClickGUI.opacite).getRGB());
        Fonts.urw16.drawStringWithShadow(setting.getName(), parent.posX + x - 70, parent.posY + y + 2, new Color(255, 255, 255, ClickGUI.opacite).getRGB());
        Fonts.urw16.drawStringWithShadow(setting.getValString(), (parent.posX + x + 267) - Fonts.urw16.getStringWidth(setting.getValString()), (parent.posY + y + 2.5), new Color(255,255,255, ClickGUI.opacite).getRGB());
    }
}
