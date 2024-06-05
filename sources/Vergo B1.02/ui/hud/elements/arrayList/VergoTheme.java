package xyz.vergoclient.ui.hud.elements.arrayList;

import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import xyz.vergoclient.Vergo;
import xyz.vergoclient.event.Event;
import xyz.vergoclient.event.impl.EventRenderGUI;
import xyz.vergoclient.modules.Module;
import xyz.vergoclient.modules.ModuleManager;
import xyz.vergoclient.modules.OnEventInterface;
import xyz.vergoclient.ui.fonts.FontUtil;
import xyz.vergoclient.ui.fonts.JelloFontRenderer;
import xyz.vergoclient.util.main.ColorUtils;
import xyz.vergoclient.util.Gl.BlurUtil;
import xyz.vergoclient.util.main.TimerUtil;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class VergoTheme implements OnEventInterface {

    @Override
    public void onEvent(Event e) {
        if(e instanceof EventRenderGUI && e.isPre() ) {
            // Calls the arraylist to be drawn.
            drawArrayList();
        }
    }

    public static transient TimerUtil arrayListToggleMovement = new TimerUtil();
    public static int arrayListRainbow = 0;
    public static int arrayListColor = -1;

    public static float align = 3.5f;

    public static Color waveColor = null;
    public int Rainbow = 125;

    public int waveColor2;

    public static double squeeze;

    public static void drawArrayList() {

        arrayListRainbow = 0;
        arrayListColor = -1;

        if(Vergo.config.modHud.isEnabled()) {
            arrayListColor++;

            JelloFontRenderer fr = FontUtil.neurialGrotesk;

            ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());

            ArrayList<Module> modules = new ArrayList<>();
            ModuleManager.modules.forEach(module -> {
                if (module.arrayListAnimation > 0.01 || module.isEnabled()) modules.add(module);
            });
            modules.sort(Comparator.comparingDouble(module -> fr.getStringWidth(module.getName() + (module.getInfo().isEmpty() ? "" : " " + module.getInfo()))));
            Collections.reverse(modules);

            boolean updateToggleMovement = arrayListToggleMovement.hasTimeElapsed(1000 / 40, true);

            double offset = 0;

            for (Module module : modules) {
                arrayListRainbow += 125;
                arrayListColor++;

                String textToRender = module.getName() + ChatFormatting.GRAY + " " + module.getInfo();
                if (module.getInfo().isEmpty())
                    textToRender = module.getName();

                if (updateToggleMovement) {
                    if (module.isEnabled()) {
                        module.arrayListAnimation += (1 - module.arrayListAnimation) / 8;
                        if (module.arrayListAnimation > 1)
                            module.arrayListAnimation = 1;
                    } else {
                        module.arrayListAnimation -= module.arrayListAnimation / 3;
                        if (module.arrayListAnimation < 0)
                            module.arrayListAnimation = 0;
                    }
                }

                GlStateManager.pushMatrix();

                double squeeze = module.arrayListAnimation * 2;
                if (squeeze > 1)
                    squeeze = 1;

                GlStateManager.translate((float) (sr.getScaledWidth() - (fr.getStringWidth(textToRender) / 2) - 2), (float) (offset * (fr.FONT_HEIGHT + 4)) + 0, 0);

                GlStateManager.scale(1, squeeze, 1);

                GlStateManager.translate(-(float) (sr.getScaledWidth() - (fr.getStringWidth(textToRender) / 2) - 2), -((float) (offset * (fr.FONT_HEIGHT + 4)) + 0), 0);

                if (Vergo.config.modHud.vergoColor.is("Burgundy")) {

                    waveColor = ColorUtils.fadeColor(new Color(196, 0, 69), (int) offset, 30);

                } else if (Vergo.config.modHud.vergoColor.is("Sea Blue")) {

                    waveColor = ColorUtils.fadeColor(new Color(4, 120, 219), (int) offset, 30);

                } else if (Vergo.config.modHud.vergoColor.is("New Vergo")) {

                    waveColor = ColorUtils.fadeColor(new Color(159, 0, 82), (int) offset, 25);

                } else if (Vergo.config.modHud.vergoColor.is("Nuclear Green")) {

                    waveColor = ColorUtils.fadeColor(new Color(60, 213, 69), (int) offset, 30);

                } else if (Vergo.config.modHud.vergoColor.is("Rainbow")) {
                    waveColor = ColorUtils.rainbow(20, (int) offset * 5, 0.7f, 1, 1);
                }
                else {

                    waveColor = new Color(250, 250, 250);

                }


                if(Vergo.config.modHud.blurToggle.isEnabled()) {
                    BlurUtil.blurArea(sr.getScaledWidth() - fr.getStringWidth(textToRender) - 6.5, 0, sr.getScaledWidth(), (offset + 1) * (fr.FONT_HEIGHT + 4));
                } else {

                }

                Gui.drawRect(sr.getScaledWidth() - fr.getStringWidth(textToRender) - 6.5, (offset + 1) * (fr.FONT_HEIGHT + 4), sr.getScaledWidth(), (offset) * (fr.FONT_HEIGHT + 4), 0x40000000);

                align = 4.5f;

                BlurUtil.blurArea(sr.getScaledWidth() - fr.getStringWidth(textToRender) - 6.5, 0, sr.getScaledWidth(), (offset + 1) * (fr.FONT_HEIGHT + 4));

                Gui.drawRect(sr.getScaledWidth() - 1.3f, (offset + 1) * (fr.FONT_HEIGHT + 4), sr.getScaledWidth(), (offset) * (fr.FONT_HEIGHT + 4), waveColor.getRGB());

                GlStateManager.colorState.alpha = 1;

                GlStateManager.translate((float) (sr.getScaledWidth() - (fr.getStringWidth(textToRender) / 2) - 2), (float) (offset * (fr.FONT_HEIGHT + 4)) + 0, 0);

                GlStateManager.scale(squeeze, squeeze, 1);

                GlStateManager.translate(-(float) (sr.getScaledWidth() - (fr.getStringWidth(textToRender) / 2) - 2), -((float) (offset * (fr.FONT_HEIGHT + 4)) + 0), 0);
                fr.drawString(textToRender, (float) (sr.getScaledWidth() - fr.getStringWidth(textToRender) - align), (float) (offset * (fr.FONT_HEIGHT + 4)) + 3f, waveColor.getRGB());

                GlStateManager.popMatrix();

                offset++;
                if (squeeze != 1) {
                    offset--;
                    offset += squeeze;
                }

            }
        }

        arrayListRainbow = 0;
        arrayListColor = -1;

    }
}
