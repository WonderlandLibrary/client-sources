package csgogui.comp;

import clickgui.setting.Setting;
import csgogui.CSGOGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.math.MathHelper;
import white.floor.features.Feature;
import white.floor.features.impl.display.ClickGUI;
import white.floor.font.Fonts;
import white.floor.helpers.DrawHelper;
import white.floor.helpers.render.AnimationHelper;

import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class Slider extends Comp {

    private boolean dragging = false;
    private double renderWidth;
    private double renderWidth2;
    private float anim = 0.0f;

    public Slider(double x, double y, CSGOGui parent, Feature module, Setting setting) {
        this.x = x;
        this.y = y;
        this.parent = parent;
        this.module = module;
        this.setting = setting;
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        if (isInside(mouseX, mouseY, parent.posX + x + 179, parent.posY + y + 3.5,parent.posX + x + 178 + renderWidth2, parent.posY + y + 5.5) && mouseButton == 0) {
            dragging = true;
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);
        dragging = false;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY) {
        super.drawScreen(mouseX, mouseY);

        double min = setting.getMin();
        double max = setting.getMax();
        double l = 90;

        renderWidth = (l) * (setting.getValDouble() - min) / (max - min);
        renderWidth2 = (l) * (setting.getMax() - min) / (max - min);

        double diff = Math.min(l, Math.max(0, mouseX - (parent.posX + x + 180)));
        if (dragging) {
            if (diff == 0) {
                setting.setValDouble(setting.getMin());
            }
            else {
                double newValue = roundToPlace(((diff / l) * (max - min) + min), 2);
                setting.setValDouble(newValue);
            }
        }

        this.anim = (float) MathHelper.lerp(this.anim, renderWidth, 15 * Feature.deltaTime());
        DrawHelper.drawSmoothRect((float) (parent.posX + x + 179), (float) (parent.posY + y + 3.5),(float) (parent.posX + x + 180 + renderWidth2), (float) (parent.posY + y + 5.5), new Color(30,30,30, ClickGUI.opacite).getRGB());
        if(setting.getValDouble() > setting.getMin())
        DrawHelper.drawSmoothRect((float) (parent.posX + x + 179), (float) (parent.posY + y + 3.5), (float) (parent.posX + x + 179.5 + this.anim), (float) (parent.posY + y + 5.5), new Color(90,90,90, ClickGUI.opacite).getRGB());
        Fonts.urw16.drawStringWithShadow(setting.getName(),parent.posX + x - 70,parent.posY + y + 2, new Color(255, 255, 255, ClickGUI.opacite).getRGB());

        if (isInside(mouseX, mouseY, parent.posX + x + 179, parent.posY + y + 3.5,parent.posX + x + 178 + renderWidth2, parent.posY + y + 5.5) || dragging) {
            Fonts.urw13.drawStringWithShadow(setting.getValDouble() + "", parent.posX + x + 179 + renderWidth2 - Fonts.urw13 .getStringWidth(setting.getValDouble() + ""), parent.posY + y + 7.5, new Color(255, 255, 255, ClickGUI.opacite).getRGB());
        }
    }

    private double roundToPlace(double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

}
