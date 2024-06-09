/*
 * Decompiled with CFR 0.145.
 */
package de.Hero.clickgui.elements.menu;

import de.Hero.clickgui.ClickGUI;
import de.Hero.clickgui.elements.Element;
import de.Hero.clickgui.elements.ModuleButton;
import de.Hero.clickgui.util.ColorUtil;
import de.Hero.clickgui.util.FontUtil;
import de.Hero.settings.Setting;
import de.Hero.settings.SettingsManager;
import java.awt.Color;
import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.Gui;
import us.amerikan.amerikan;

public class ElementComboBox
extends Element {
    public ElementComboBox(ModuleButton iparent, Setting iset) {
        this.parent = iparent;
        this.set = iset;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        Color temp = ColorUtil.getClickGUIColor();
        int color = new Color(temp.getRed(), temp.getGreen(), temp.getBlue(), 150).getRGB();
        Gui.drawRect(this.x, this.y, this.x + this.width, this.y + this.height, -15066598);
        FontUtil.drawTotalCenteredString(this.setstrg, this.x + this.width / 2.0, this.y + 7.0, -1);
        int clr1 = color;
        int clr2 = temp.getRGB();
        Gui.drawRect(this.x, this.y + 14.0, this.x + this.width, this.y + 15.0, 1996488704);
        if (this.comboextended) {
            Gui.drawRect(this.x, this.y + 15.0, this.x + this.width, this.y + this.height, -1441656302);
            double ay2 = this.y + 15.0;
            for (String sld : this.set.getOptions()) {
                String elementtitle = String.valueOf(sld.substring(0, 1).toUpperCase()) + sld.substring(1, sld.length());
                FontUtil.drawCenteredString(elementtitle, this.x + this.width / 2.0, ay2 + 2.0, -1);
                if (sld.equalsIgnoreCase(this.set.getValString())) {
                    Gui.drawRect(this.x, ay2, this.x + 1.5, ay2 + (double)FontUtil.getFontHeight() + 2.0, clr1);
                }
                if ((double)mouseX >= this.x && (double)mouseX <= this.x + this.width && (double)mouseY >= ay2 && (double)mouseY < ay2 + (double)FontUtil.getFontHeight() + 2.0) {
                    Gui.drawRect(this.x + this.width - 1.2, ay2, this.x + this.width, ay2 + (double)FontUtil.getFontHeight() + 2.0, clr2);
                }
                ay2 += (double)(FontUtil.getFontHeight() + 2);
            }
        }
    }

    @Override
    public boolean mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0) {
            if (this.isButtonHovered(mouseX, mouseY)) {
                this.comboextended = !this.comboextended;
                return true;
            }
            if (!this.comboextended) {
                return false;
            }
            double ay2 = this.y + 15.0;
            for (String slcd : this.set.getOptions()) {
                if ((double)mouseX >= this.x && (double)mouseX <= this.x + this.width && (double)mouseY >= ay2 && (double)mouseY <= ay2 + (double)FontUtil.getFontHeight() + 2.0) {
                    if (amerikan.setmgr.getSettingByName("Sound").getValBoolean()) {
                        Minecraft.getMinecraft();
                        Minecraft.thePlayer.playSound("tile.piston.in", 20.0f, 20.0f);
                    }
                    if (this.clickgui != null && this.clickgui.setmgr != null) {
                        this.clickgui.setmgr.getSettingByName(this.set.getName()).setValString(slcd.toLowerCase());
                    }
                    return true;
                }
                ay2 += (double)(FontUtil.getFontHeight() + 2);
            }
        }
        return super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    public boolean isButtonHovered(int mouseX, int mouseY) {
        return (double)mouseX >= this.x && (double)mouseX <= this.x + this.width && (double)mouseY >= this.y && (double)mouseY <= this.y + 15.0;
    }
}

