/*
 * Decompiled with CFR 0.145.
 */
package de.Hero.clickgui;

import de.Hero.clickgui.Panel;
import de.Hero.clickgui.elements.Element;
import de.Hero.clickgui.elements.ModuleButton;
import de.Hero.clickgui.elements.menu.ElementSlider;
import de.Hero.clickgui.util.ColorUtil;
import de.Hero.clickgui.util.FontUtil;
import de.Hero.settings.Setting;
import de.Hero.settings.SettingsManager;
import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.shader.ShaderGroup;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import us.amerikan.amerikan;
import us.amerikan.modules.Category;
import us.amerikan.modules.Module;
import us.amerikan.modules.ModuleManager;

public class ClickGUI
extends GuiScreen {
    public static ArrayList<Panel> panels;
    public static ArrayList<Panel> rpanels;
    private ModuleButton mb = null;
    public SettingsManager setmgr = amerikan.setmgr;

    public ClickGUI() {
        FontUtil.setupFontUtils();
        panels = new ArrayList();
        double pwidth = 80.0;
        double pheight = 15.0;
        double px2 = 10.0;
        double py2 = 10.0;
        double pyplus = pheight + 10.0;
        for (final Category c2 : Category.values()) {
            String title = String.valueOf(Character.toUpperCase(c2.name().toLowerCase().charAt(0))) + c2.name().toLowerCase().substring(1);
            panels.add(new Panel(title, px2, py2, pwidth, pheight, false, this){

                @Override
                public void setup() {
                    for (Module m2 : amerikan.modulemanager.getModules()) {
                        if (!m2.getCategory().equals((Object)c2)) continue;
                        this.Elements.add(new ModuleButton(m2, this));
                    }
                }
            });
            py2 += pyplus;
        }
        rpanels = new ArrayList();
        for (Panel p2 : panels) {
            rpanels.add(p2);
        }
        Collections.reverse(rpanels);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        for (Panel p2 : panels) {
            p2.drawScreen(mouseX, mouseY, partialTicks);
        }
        ScaledResolution s2 = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
        GL11.glPushMatrix();
        GL11.glTranslated(s2.getScaledWidth(), s2.getScaledHeight(), 0.0);
        GL11.glScaled(0.5, 0.5, 0.5);
        FontUtil.drawStringWithShadow("byHeroCode", -Minecraft.getMinecraft().fontRendererObj.getStringWidth("byHeroCode"), -Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT, -15599509);
        GL11.glPopMatrix();
        this.mb = null;
        block1 : for (Panel p3 : panels) {
            if (p3 == null || !p3.visible || !p3.extended || p3.Elements == null || p3.Elements.size() <= 0) continue;
            for (ModuleButton e2 : p3.Elements) {
                if (!e2.listening) continue;
                this.mb = e2;
                break block1;
            }
        }
        for (Panel panel : panels) {
            if (!panel.extended || !panel.visible || panel.Elements == null) continue;
            for (ModuleButton b2 : panel.Elements) {
                if (!b2.extended || b2.menuelements == null || b2.menuelements.isEmpty()) continue;
                double off = 0.0;
                Color temp = ColorUtil.getClickGUIColor().darker();
                int outlineColor = new Color(temp.getRed(), temp.getGreen(), temp.getBlue(), 170).getRGB();
                for (Element e3 : b2.menuelements) {
                    e3.offset = off;
                    e3.update();
                    if (amerikan.setmgr.getSettingByName("Design").getValString().equalsIgnoreCase("HeroCode")) {
                        Gui.drawRect(e3.x, e3.y, e3.x + e3.width + 2.0, e3.y + e3.height, outlineColor);
                    }
                    e3.drawScreen(mouseX, mouseY, partialTicks);
                    off += e3.height;
                }
            }
        }
        if (this.mb != null) {
            ClickGUI.drawRect(0, 0, this.width, this.height, -2012213232);
            GL11.glPushMatrix();
            GL11.glTranslatef(s2.getScaledWidth() / 2, s2.getScaledHeight() / 2, 0.0f);
            GL11.glScalef(4.0f, 4.0f, 0.0f);
            FontUtil.drawTotalCenteredStringWithShadow("Listening...", 0.0, -10.0, -1);
            GL11.glScalef(0.5f, 0.5f, 0.0f);
            FontUtil.drawTotalCenteredStringWithShadow("Press 'ESCAPE' to unbind " + this.mb.mod.getName() + (this.mb.mod.getKeybind() > -1 ? " (" + Keyboard.getKeyName(this.mb.mod.getKeybind()) + ")" : ""), 0.0, 0.0, -1);
            GL11.glScalef(0.25f, 0.25f, 0.0f);
            FontUtil.drawTotalCenteredStringWithShadow("by HeroCode", 0.0, 20.0, -1);
            GL11.glPopMatrix();
        }
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (this.mb != null) {
            return;
        }
        for (Panel panel : rpanels) {
            if (!panel.extended || !panel.visible || panel.Elements == null) continue;
            for (ModuleButton b2 : panel.Elements) {
                if (!b2.extended) continue;
                for (Element e2 : b2.menuelements) {
                    if (!e2.mouseClicked(mouseX, mouseY, mouseButton)) continue;
                    return;
                }
            }
        }
        for (Panel p2 : rpanels) {
            if (!p2.mouseClicked(mouseX, mouseY, mouseButton)) continue;
            return;
        }
        try {
            super.mouseClicked(mouseX, mouseY, mouseButton);
        }
        catch (IOException e3) {
            e3.printStackTrace();
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {
        if (this.mb != null) {
            return;
        }
        for (Panel panel : rpanels) {
            if (!panel.extended || !panel.visible || panel.Elements == null) continue;
            for (ModuleButton b2 : panel.Elements) {
                if (!b2.extended) continue;
                for (Element e2 : b2.menuelements) {
                    e2.mouseReleased(mouseX, mouseY, state);
                }
            }
        }
        for (Panel p2 : rpanels) {
            p2.mouseReleased(mouseX, mouseY, state);
        }
        super.mouseReleased(mouseX, mouseY, state);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) {
        for (Panel p2 : rpanels) {
            if (p2 == null || !p2.visible || !p2.extended || p2.Elements == null || p2.Elements.size() <= 0) continue;
            for (ModuleButton e2 : p2.Elements) {
                try {
                    if (!e2.keyTyped(typedChar, keyCode)) continue;
                    return;
                }
                catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        try {
            super.keyTyped(typedChar, keyCode);
        }
        catch (IOException e2) {
            e2.printStackTrace();
        }
    }

    @Override
    public void initGui() {
        if (OpenGlHelper.shadersSupported && this.mc.func_175606_aa() instanceof EntityPlayer) {
            if (this.mc.entityRenderer.theShaderGroup != null) {
                this.mc.entityRenderer.theShaderGroup.deleteShaderGroup();
            }
            this.mc.entityRenderer.func_175069_a(new ResourceLocation("shaders/post/blur.json"));
        }
    }

    @Override
    public void onGuiClosed() {
        if (this.mc.entityRenderer.theShaderGroup != null) {
            this.mc.entityRenderer.theShaderGroup.deleteShaderGroup();
            this.mc.entityRenderer.theShaderGroup = null;
        }
        for (Panel panel : rpanels) {
            if (!panel.extended || !panel.visible || panel.Elements == null) continue;
            for (ModuleButton b2 : panel.Elements) {
                if (!b2.extended) continue;
                for (Element e2 : b2.menuelements) {
                    if (!(e2 instanceof ElementSlider)) continue;
                    ((ElementSlider)e2).dragging = false;
                }
            }
        }
    }

    public void closeAllSettings() {
        for (Panel p2 : rpanels) {
            if (p2 == null || !p2.visible || !p2.extended || p2.Elements == null || p2.Elements.size() <= 0) continue;
            for (ModuleButton moduleButton : p2.Elements) {
            }
        }
    }

}

