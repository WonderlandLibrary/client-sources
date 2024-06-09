/*
 * Decompiled with CFR 0.145.
 */
package de.Hero.clickgui.elements;

import de.Hero.clickgui.ClickGUI;
import de.Hero.clickgui.Panel;
import de.Hero.clickgui.elements.ModuleButton;
import de.Hero.clickgui.util.FontUtil;
import de.Hero.settings.Setting;
import java.util.ArrayList;

public class Element {
    public ClickGUI clickgui;
    public ModuleButton parent;
    public Setting set;
    public double offset;
    public double x;
    public double y;
    public double width;
    public double height;
    public String setstrg;
    public boolean comboextended;

    public void setup() {
        this.clickgui = this.parent.parent.clickgui;
    }

    public void update() {
        this.x = this.parent.x + this.parent.width + 2.0;
        this.y = this.parent.y + this.offset;
        this.width = this.parent.width + 10.0;
        this.height = 15.0;
        String sname = this.set.getName();
        if (this.set.isCheck()) {
            this.setstrg = String.valueOf(sname.substring(0, 1).toUpperCase()) + sname.substring(1, sname.length());
            double textx = this.x + this.width - (double)FontUtil.getStringWidth(this.setstrg);
            if (textx < this.x + 13.0) {
                this.width += this.x + 13.0 - textx + 1.0;
            }
        } else if (this.set.isCombo()) {
            this.height = this.comboextended ? this.set.getOptions().size() * (FontUtil.getFontHeight() + 2) + 15 : 15;
            this.setstrg = String.valueOf(sname.substring(0, 1).toUpperCase()) + sname.substring(1, sname.length());
            int longest = FontUtil.getStringWidth(this.setstrg);
            for (String s2 : this.set.getOptions()) {
                int temp = FontUtil.getStringWidth(s2);
                if (temp <= longest) continue;
                longest = temp;
            }
            double textx = this.x + this.width - (double)longest;
            if (textx < this.x) {
                this.width += this.x - textx + 1.0;
            }
        } else if (this.set.isSlider()) {
            this.setstrg = String.valueOf(sname.substring(0, 1).toUpperCase()) + sname.substring(1, sname.length());
            String displayval = "" + (double)Math.round(this.set.getValDouble() * 100.0) / 100.0;
            String displaymax = "" + (double)Math.round(this.set.getMax() * 100.0) / 100.0;
            double textx = this.x + this.width - (double)FontUtil.getStringWidth(this.setstrg) - (double)FontUtil.getStringWidth(displaymax) - 4.0;
            if (textx < this.x) {
                this.width += this.x - textx + 1.0;
            }
        }
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
    }

    public boolean mouseClicked(int mouseX, int mouseY, int mouseButton) {
        return this.isHovered(mouseX, mouseY);
    }

    public void mouseReleased(int mouseX, int mouseY, int state) {
    }

    public boolean isHovered(int mouseX, int mouseY) {
        return (double)mouseX >= this.x && (double)mouseX <= this.x + this.width && (double)mouseY >= this.y && (double)mouseY <= this.y + this.height;
    }
}

