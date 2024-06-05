package csgogui.comp;

import clickgui.setting.Setting;
import csgogui.CSGOGui;
import org.lwjgl.opengl.GL11;
import white.floor.features.Feature;
import white.floor.features.impl.display.ClickGUI;
import white.floor.font.Fonts;
import white.floor.helpers.DrawHelper;
import white.floor.helpers.render.AnimationHelper;

import java.awt.*;

public class CheckBox extends Comp {

    public CheckBox(double x, double y, CSGOGui parent, Feature module, Setting setting) {
        this.x = x;
        this.y = y;
        this.parent = parent;
        this.module = module;
        this.setting = setting;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY) {
        super.drawScreen(mouseX, mouseY);

        DrawHelper.drawSmoothRect((float) (parent.posX + x + 262), (float) (parent.posY + y + 1), (float) (parent.posX + x + 270), (float) (parent.posY + y + 9), setting.getValBoolean() ? new Color(90, 90, 90, ClickGUI.opacite).getRGB() : isInside(mouseX, mouseY, (float) (parent.posX + x + 262), (float) (parent.posY + y + 1), (float) (parent.posX + x + 270), (float) (parent.posY + y + 9)) ? new Color(90,90,90, ClickGUI.opacite).getRGB() : new Color(45,45,45, ClickGUI.opacite).getRGB());
        DrawHelper.drawSmoothRect((float) (parent.posX + x + 262.5), (float) (parent.posY + y + 1.5), (float) (parent.posX + x + 269.5), (float) (parent.posY + y + 8.5), new Color(24,24,24, ClickGUI.opacite).getRGB());
        DrawHelper.drawSmoothRect((float) (parent.posX + x + 263), (float) (parent.posY + y + 2), (float) (parent.posX + x + 269), (float) (parent.posY + y + 8), setting.getValBoolean() ? new Color(90, 90, 90, ClickGUI.opacite).getRGB() : isInside(mouseX, mouseY, (float) (parent.posX + x + 150), (float) (parent.posY + y), (float) (parent.posX + x + 169), (float) (parent.posY + y + 10)) ? new Color(45,45,45, 0).getRGB() : new Color(45,45,82, 0).getRGB());
        Fonts.urw16.drawStringWithShadow(setting.getName(), parent.posX + x - 70, parent.posY + y + 2, new Color(255, 255, 255, ClickGUI.opacite).getRGB());
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        if (isInside(mouseX, mouseY, (float) (parent.posX + x + 262), (float) (parent.posY + y + 1), (float) (parent.posX + x + 270), (float) (parent.posY + y + 9)) && mouseButton == 0) {
            setting.setValBoolean(!setting.getValBoolean());
        }
    }
}
