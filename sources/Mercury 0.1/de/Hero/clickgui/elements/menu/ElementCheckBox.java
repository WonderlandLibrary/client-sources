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

public class ElementCheckBox
extends Element {
    public ElementCheckBox(ModuleButton iparent, Setting iset) {
        this.parent = iparent;
        this.set = iset;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        Color temp = ColorUtil.getClickGUIColor();
        int color = new Color(temp.getRed(), temp.getGreen(), temp.getBlue(), 200).getRGB();
        Gui.drawRect(this.x, this.y, this.x + this.width, this.y + this.height, -15066598);
        FontUtil.drawString(this.setstrg, this.x + this.width - (double)FontUtil.getStringWidth(this.setstrg), this.y + (double)(FontUtil.getFontHeight() / 2) - 0.5, -1);
        Gui.drawRect(this.x + 1.0, this.y + 2.0, this.x + 12.0, this.y + 13.0, this.set.getValBoolean() ? color : -16777216);
        if (this.isCheckHovered(mouseX, mouseY)) {
            Gui.drawRect(this.x + 1.0, this.y + 2.0, this.x + 12.0, this.y + 13.0, 1427181841);
        }
    }

    @Override
    public boolean mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0 && this.isCheckHovered(mouseX, mouseY)) {
            this.set.setValBoolean(!this.set.getValBoolean());
            return true;
        }
        return super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    public boolean isCheckHovered(int mouseX, int mouseY) {
        return (double)mouseX >= this.x + 1.0 && (double)mouseX <= this.x + 12.0 && (double)mouseY >= this.y + 2.0 && (double)mouseY <= this.y + 13.0;
    }
}

