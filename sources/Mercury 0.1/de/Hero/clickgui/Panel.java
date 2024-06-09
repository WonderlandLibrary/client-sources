/*
 * Decompiled with CFR 0.145.
 */
package de.Hero.clickgui;

import de.Hero.clickgui.ClickGUI;
import de.Hero.clickgui.elements.ModuleButton;
import de.Hero.clickgui.util.ColorUtil;
import de.Hero.clickgui.util.FontUtil;
import de.Hero.settings.Setting;
import de.Hero.settings.SettingsManager;
import java.awt.Color;
import java.util.ArrayList;
import net.minecraft.client.gui.Gui;
import us.amerikan.amerikan;

public class Panel {
    public String title;
    public double x;
    public double y;
    private double x2;
    private double y2;
    public double width;
    public double height;
    public boolean dragging;
    public boolean extended;
    public boolean visible;
    public ArrayList<ModuleButton> Elements = new ArrayList();
    public ClickGUI clickgui;

    public Panel(String ititle, double ix2, double iy2, double iwidth, double iheight, boolean iextended, ClickGUI parent) {
        this.title = ititle;
        this.x = ix2;
        this.y = iy2;
        this.width = iwidth;
        this.height = iheight;
        this.extended = iextended;
        this.dragging = false;
        this.visible = true;
        this.clickgui = parent;
        this.setup();
    }

    public void setup() {
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        if (!this.visible) {
            return;
        }
        if (this.dragging) {
            this.x = this.x2 + (double)mouseX;
            this.y = this.y2 + (double)mouseY;
        }
        Color temp = ColorUtil.getClickGUIColor().darker();
        int outlineColor = new Color(temp.getRed(), temp.getGreen(), temp.getBlue(), 170).getRGB();
        if (amerikan.setmgr.getSettingByName("Design").getValString().equalsIgnoreCase("HeroCode")) {
            this.width = 80.0;
            Gui.drawRect(this.x, this.y, this.x + this.width, this.y + this.height, -15592942);
            Gui.drawRect(this.x - 2.0, this.y, this.x, this.y + this.height, outlineColor);
            FontUtil.drawStringWithShadow(this.title, this.x + 2.0, this.y + this.height / 2.0 - (double)(FontUtil.getFontHeight() / 2), -1052689);
        } else if (amerikan.setmgr.getSettingByName("Design").getValString().equalsIgnoreCase("Client")) {
            this.width = 95.0;
            Color temp1 = ColorUtil.getClickGUIColor();
            int color = new Color(temp1.getRed(), temp1.getGreen(), temp1.getBlue(), 255).darker().getRGB();
            Gui.drawRect(this.x, this.y, this.x + this.width, this.y + this.height, -15592942);
            Gui.drawRect(this.x, this.y + 14.0, this.x + this.width, this.y + 15.0, color);
            FontUtil.drawStringWithShadow(this.title, this.x + 2.0, this.y + this.height / 2.0 - (double)(FontUtil.getFontHeight() / 2), -1052689);
            FontUtil.drawString("-", this.x + this.width - 7.0, this.y + this.height - 9.0 - (double)(FontUtil.getFontHeight() / 2), -1052689);
            FontUtil.drawString("-", this.x + this.width - 7.0, this.y + this.height - 7.0 - (double)(FontUtil.getFontHeight() / 2), -1052689);
            FontUtil.drawString("-", this.x + this.width - 7.0, this.y + this.height - 5.0 - (double)(FontUtil.getFontHeight() / 2), -1052689);
        }
        if (this.extended && !this.Elements.isEmpty()) {
            double startY = this.y + this.height;
            int epanelcolor = amerikan.setmgr.getSettingByName("Design").getValString().equalsIgnoreCase("HeroCode") ? -14474461 : (amerikan.setmgr.getSettingByName("Design").getValString().equalsIgnoreCase("Client") ? -1156246251 : 0);
            for (ModuleButton et2 : this.Elements) {
                if (amerikan.setmgr.getSettingByName("Design").getValString().equalsIgnoreCase("HeroCode")) {
                    Gui.drawRect(this.x - 2.0, startY, this.x + this.width, startY + et2.height + 1.0, outlineColor);
                }
                Gui.drawRect(this.x, startY, this.x + this.width, startY + et2.height + 1.0, epanelcolor);
                et2.x = this.x + 2.0;
                et2.y = startY;
                et2.width = this.width - 4.0;
                et2.drawScreen(mouseX, mouseY, partialTicks);
                startY += et2.height + 1.0;
            }
            Gui.drawRect(this.x, startY + 1.0, this.x + this.width, startY + 1.0, epanelcolor);
        }
    }

    public boolean mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (!this.visible) {
            return false;
        }
        if (mouseButton == 0 && this.isHovered(mouseX, mouseY)) {
            this.x2 = this.x - (double)mouseX;
            this.y2 = this.y - (double)mouseY;
            this.dragging = true;
            return true;
        }
        if (mouseButton == 1 && this.isHovered(mouseX, mouseY)) {
            this.extended = !this.extended;
            return true;
        }
        if (this.extended) {
            for (ModuleButton et2 : this.Elements) {
                if (!et2.mouseClicked(mouseX, mouseY, mouseButton)) continue;
                return true;
            }
        }
        return false;
    }

    public void mouseReleased(int mouseX, int mouseY, int state) {
        if (!this.visible) {
            return;
        }
        if (state == 0) {
            this.dragging = false;
        }
    }

    public boolean isHovered(int mouseX, int mouseY) {
        return (double)mouseX >= this.x && (double)mouseX <= this.x + this.width && (double)mouseY >= this.y && (double)mouseY <= this.y + this.height;
    }
}

