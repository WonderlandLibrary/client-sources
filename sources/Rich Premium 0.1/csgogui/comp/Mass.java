package csgogui.comp;

import clickgui.setting.Setting;
import csgogui.CSGOGui;
import net.minecraft.client.gui.Gui;
import white.floor.features.Feature;
import white.floor.features.impl.display.ClickGUI;
import white.floor.font.Fonts;
import white.floor.helpers.DrawHelper;
import white.floor.helpers.render.AnimationHelper;

import java.awt.*;

public class Mass extends Comp {

    public Mass(double x, double y, CSGOGui parent, Feature module, Setting setting) {
        this.x = x;
        this.y = y;
        this.parent = parent;
        this.module = module;
        this.setting = setting;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY) {
        super.drawScreen(mouseX, mouseY);


        Fonts.urw18.drawStringWithShadow(module.getName(), (parent.posX + x + 120) - Fonts.urw18.getStringWidth(module.getName()), parent.posY + y, new Color(255, 255, 255, ClickGUI.opacite).getRGB());
        Gui.drawRect(parent.posX + x + 96 + Fonts.urw18.getStringWidth(module.getName()), parent.posY + y + 4.5, parent.posX + x + 268,parent.posY + y + 5, new Color(44,44,44, ClickGUI.opacite).getRGB());
        Gui.drawRect((parent.posX + x - 68), parent.posY + y + 4.5, parent.posX + x + 114 - Fonts.urw18.getStringWidth(module.getName()),parent.posY + y + 5, new Color(44,44,44, ClickGUI.opacite).getRGB());

    }
}