/*
 * Decompiled with CFR 0_122.
 */
package Monix.Mod.mods;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;

import Monix.Monix;
import Monix.Category.Category;
import Monix.Event.EventTarget;
import Monix.Event.events.EventRender2D;
import Monix.Mod.Mod;
import Monix.Mod.ModManager;
import Monix.Utils.R2DUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;

public class Hud
extends Mod {
    public Hud() {
        super("Hud", "Hud", 0, Category.RENDER);
        this.setToggled(true);
    }

    public static String NotifySays = "Welcome to Monix! RSHIFT To Open ClickGUI!";


    @EventTarget
    public void onRender(EventRender2D event) {
    	GlStateManager.pushMatrix();
        ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft(), Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);
        if (!Minecraft.getMinecraft().gameSettings.showDebugInfo && Objects.nonNull(Minecraft.getMinecraft().thePlayer) && Objects.nonNull(Minecraft.getMinecraft().theWorld)) {
            Monix.fontManager.hud.drawStringWithShadow("\u00a7lMonix \u00a773.1", 2.0, 5.0, 13371375);
            this.renderArrayList(scaledResolution);
        	Gui.drawRect(0, 0, 0, 0, 0);
            this.drawTabGui();
            int right = scaledResolution.getScaledWidth();
            int bottom = scaledResolution.getScaledHeight();
        }
    	Gui.drawRect(0, 0, 0, 0, 0);
        Gui.drawRect(0, (18 + Category.values().length * 13) - 4, mc.fontRendererObj.getStringWidth("[M] " + NotifySays) + 5, 18 + Category.values().length * 13 + mc.fontRendererObj.FONT_HEIGHT + 1, new Color(Color.black.getRed(), Color.black.getGreen(), Color.black.getBlue(), 150).getRGB());
        Minecraft.getMinecraft().fontRendererObj.drawString("\u00a75[\u00a77M\u00a75] \u00a7f" + NotifySays, 2, 18 + Category.values().length * 13, 13371375);
    	GlStateManager.popMatrix();
    	Gui.drawRect(0, 0, 0, 0, 0);
    }

    private void renderArrayList(ScaledResolution scaledResolution) {
        int index = 0;
        long x = 0;
        int yCount = 5;
        int right = scaledResolution.getScaledWidth();
        ArrayList<Mod> mod = ModManager.getMods();
        mod.sort(new Comparator<Mod>(){

            @Override
            public final int compare(Mod m1, Mod m2) {
                return Integer.valueOf(Minecraft.getMinecraft().fontRendererObj.getStringWidth(m2.GetArrayListName())).compareTo(Minecraft.getMinecraft().fontRendererObj.getStringWidth(m1.GetArrayListName()));
            }
        });
        for (Mod Mod2 : ModManager.getMods()) {
            if (!Mod2.isToggled() || Mod2.getCategory() == Category.NONE) continue;
            if (Mod2.getCategory() == Category.FILLERS) {
                return;
            }
            R2DUtils.drawRect(right - Monix.fontManager.arraylist.getStringWidth(Mod2.GetArrayListName()) - 2, yCount, right - 20, yCount + 15, 0);
            if (Mod2.getCategory() == Category.COMBAT) {
                Monix.fontManager.arraylist.drawString("\u00a7e" + Mod2.GetArrayListName(), right - Monix.fontManager.arraylist.getStringWidth(Mod2.GetArrayListName()) - 2, yCount, 13371375);
                R2DUtils.drawRect(right - 20, yCount, right, yCount + 15, 16776960);
            }
            if (Mod2.getCategory() == Category.MOVEMENT) {
                Monix.fontManager.arraylist.drawString("\u00a7a" + Mod2.GetArrayListName(), right - Monix.fontManager.arraylist.getStringWidth(Mod2.GetArrayListName()) - 2, yCount, 13371375);
                R2DUtils.drawRect(right - 20, yCount, right, yCount + 15, 65280);
            }
            if (Mod2.getCategory() == Category.RENDER) {
                Monix.fontManager.arraylist.drawString("\u00a7b" + Mod2.GetArrayListName(), right - Monix.fontManager.arraylist.getStringWidth(Mod2.GetArrayListName()) - 2, yCount, 13371375);
                R2DUtils.drawRect(right - 20, yCount, right, yCount + 15, 255);
            }
            if (Mod2.getCategory() == Category.PLAYER) {
                Monix.fontManager.arraylist.drawString(Mod2.GetArrayListName(), right - Monix.fontManager.arraylist.getStringWidth(Mod2.GetArrayListName()) - 2, yCount, 13371375);
                R2DUtils.drawRect(right - 20, yCount, right, yCount + 15, 16711935);
            }
            if (Mod2.getCategory() == Category.WORLD) {
                Monix.fontManager.arraylist.drawString("\u00a7c" + Mod2.GetArrayListName(), right - Monix.fontManager.arraylist.getStringWidth(Mod2.GetArrayListName()) - 2, yCount, 13371375);
                R2DUtils.drawRect(right - 20, yCount, right, yCount + 15, 16711680);
            }
            if (Mod2.getCategory() == Category.EXPLOITS) {
                Monix.fontManager.arraylist.drawString("\u00a74" + Mod2.GetArrayListName(), right - Monix.fontManager.arraylist.getStringWidth(Mod2.GetArrayListName()) - 2, yCount, 13371375);
                R2DUtils.drawRect(right - 20, yCount, right, yCount + 15, 9830400);
            }
            yCount += 10;
            ++index;
            ++x;
        }
    }

    public void drawTabGui() {
        Monix.tabGui.setColourBox(-88526139);
        Monix.tabGui.drawGui(2, 18);
    }

}

