/*
 * Decompiled with CFR 0.145.
 */
package de.Hero.clickgui.elements;

import de.Hero.clickgui.ClickGUI;
import de.Hero.clickgui.Panel;
import de.Hero.clickgui.elements.Element;
import de.Hero.clickgui.elements.menu.ElementCheckBox;
import de.Hero.clickgui.elements.menu.ElementComboBox;
import de.Hero.clickgui.elements.menu.ElementSlider;
import de.Hero.clickgui.util.ColorUtil;
import de.Hero.clickgui.util.FontUtil;
import de.Hero.settings.Setting;
import de.Hero.settings.SettingsManager;
import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import us.amerikan.amerikan;
import us.amerikan.modules.Module;

public class ModuleButton {
    public Module mod;
    public ArrayList<Element> menuelements;
    public Panel parent;
    public double x;
    public double y;
    public double width;
    public double height;
    public boolean extended = false;
    public boolean listening = false;

    public ModuleButton(Module imod, Panel pl) {
        this.mod = imod;
        this.height = Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT + 2;
        this.parent = pl;
        this.menuelements = new ArrayList();
        if (amerikan.setmgr.getSettingsByMod(imod) != null) {
            for (Setting s2 : amerikan.setmgr.getSettingsByMod(imod)) {
                if (s2.isCheck()) {
                    this.menuelements.add(new ElementCheckBox(this, s2));
                    continue;
                }
                if (s2.isSlider()) {
                    this.menuelements.add(new ElementSlider(this, s2));
                    continue;
                }
                if (!s2.isCombo()) continue;
                this.menuelements.add(new ElementComboBox(this, s2));
            }
        }
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        Color temp = ColorUtil.getClickGUIColor();
        int color = new Color(temp.getRed(), temp.getGreen(), temp.getBlue(), 150).getRGB();
        int textcolor = -5263441;
        if (this.mod.isToggled()) {
            Gui.drawRect(this.x - 2.0, this.y, this.x + this.width + 2.0, this.y + this.height + 1.0, color);
            textcolor = -1052689;
        }
        if (this.isHovered(mouseX, mouseY)) {
            Gui.drawRect(this.x - 2.0, this.y, this.x + this.width + 2.0, this.y + this.height + 1.0, 1427181841);
        }
        FontUtil.drawTotalCenteredStringWithShadow(this.mod.getName(), this.x + this.width / 2.0, this.y + 1.0 + this.height / 2.0, textcolor);
    }

    public boolean mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (!this.isHovered(mouseX, mouseY)) {
            return false;
        }
        if (mouseButton == 0) {
            this.mod.toggle();
            if (amerikan.setmgr.getSettingByName("Sound").getValBoolean()) {
                Minecraft.getMinecraft();
                Minecraft.thePlayer.playSound("random.click", 0.5f, 0.5f);
            }
        } else if (mouseButton == 1) {
            if (this.menuelements != null && this.menuelements.size() > 0) {
                boolean b2 = !this.extended;
                amerikan.instance.clickgui.closeAllSettings();
                this.extended = b2;
                if (amerikan.setmgr.getSettingByName("Sound").getValBoolean()) {
                    if (this.extended) {
                        Minecraft.getMinecraft();
                        Minecraft.thePlayer.playSound("tile.piston.out", 1.0f, 1.0f);
                    } else {
                        Minecraft.getMinecraft();
                        Minecraft.thePlayer.playSound("tile.piston.in", 1.0f, 1.0f);
                    }
                }
            }
        } else if (mouseButton == 2) {
            this.listening = true;
        }
        return true;
    }

    public boolean keyTyped(char typedChar, int keyCode) throws IOException {
        if (this.listening) {
            this.listening = false;
            return true;
        }
        return false;
    }

    public boolean isHovered(int mouseX, int mouseY) {
        return (double)mouseX >= this.x && (double)mouseX <= this.x + this.width && (double)mouseY >= this.y && (double)mouseY <= this.y + this.height;
    }
}

