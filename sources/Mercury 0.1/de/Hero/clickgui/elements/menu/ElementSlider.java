/*
 * Decompiled with CFR 0.145.
 */
package de.Hero.clickgui.elements.menu;

import de.Hero.clickgui.elements.Element;
import de.Hero.clickgui.elements.ModuleButton;
import de.Hero.clickgui.util.ColorUtil;
import de.Hero.clickgui.util.FontUtil;
import de.Hero.settings.Setting;
import java.awt.Color;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.MathHelper;

public class ElementSlider
extends Element {
    public boolean dragging;

    public ElementSlider(ModuleButton iparent, Setting iset) {
        this.parent = iparent;
        this.set = iset;
        this.dragging = false;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        String displayval = "" + (double)Math.round(this.set.getValDouble() * 100.0) / 100.0;
        boolean hoveredORdragged = this.isSliderHovered(mouseX, mouseY) || this.dragging;
        Color temp = ColorUtil.getClickGUIColor();
        int color = new Color(temp.getRed(), temp.getGreen(), temp.getBlue(), hoveredORdragged ? 250 : 200).getRGB();
        int color2 = new Color(temp.getRed(), temp.getGreen(), temp.getBlue(), hoveredORdragged ? 255 : 230).getRGB();
        double percentBar = (this.set.getValDouble() - this.set.getMin()) / (this.set.getMax() - this.set.getMin());
        Gui.drawRect(this.x, this.y, this.x + this.width, this.y + this.height, -15066598);
        FontUtil.drawString(this.setstrg, this.x + 1.0, this.y + 2.0, -1);
        FontUtil.drawString(displayval, this.x + this.width - (double)FontUtil.getStringWidth(displayval), this.y + 2.0, -1);
        Gui.drawRect(this.x, this.y + 12.0, this.x + this.width, this.y + 13.5, -15724528);
        Gui.drawRect(this.x, this.y + 12.0, this.x + percentBar * this.width, this.y + 13.5, color);
        if (percentBar > 0.0 && percentBar < 1.0) {
            Gui.drawRect(this.x + percentBar * this.width - 1.0, this.y + 12.0, this.x + Math.min(percentBar * this.width, this.width), this.y + 13.5, color2);
        }
        if (this.dragging) {
            double diff = this.set.getMax() - this.set.getMin();
            double val = this.set.getMin() + MathHelper.clamp_double(((double)mouseX - this.x) / this.width, 0.0, 1.0) * diff;
            this.set.setValDouble(val);
        }
    }

    @Override
    public boolean mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0 && this.isSliderHovered(mouseX, mouseY)) {
            this.dragging = true;
            return true;
        }
        return super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {
        this.dragging = false;
    }

    public boolean isSliderHovered(int mouseX, int mouseY) {
        return (double)mouseX >= this.x && (double)mouseX <= this.x + this.width && (double)mouseY >= this.y + 11.0 && (double)mouseY <= this.y + 14.0;
    }
}

