package koks.theme.themes;

import javafx.scene.transform.Scale;
import koks.Koks;
import koks.hud.tabgui.CategoryTab;
import koks.hud.tabgui.ModuleTab;
import koks.manager.ConfigManager;
import koks.modules.Module;
import koks.modules.impl.visuals.ClearTag;
import koks.theme.Theme;
import koks.utilities.CustomFont;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;

import java.awt.*;
import java.util.Calendar;
import java.util.Comparator;

/**
 * @author avox | lmao | kroko
 * @created on 07.09.2020 : 18:25
 */
public class Klientus extends Theme {

    private CustomFont kek = new CustomFont("fonts/AlberNew-Regular.ttf", 80);
    private CustomFont keks = new CustomFont("fonts/AlberNew-Regular.ttf", 25);
    private CustomFont kekz = new CustomFont("fonts/AlberNew-Regular.ttf", 20);

    public Klientus() {
        super(ThemeCategory.KLIENTUS);

    }

    @Override
    public void categoryTabGUI(CategoryTab categoryTab, int x, int y, int width, int height) {
        super.categoryTabGUI(categoryTab, x, y, width, height);
    }

    @Override
    public void moduleTabGUI(ModuleTab moduleTab, int x, int y, int width, int height) {
        super.moduleTabGUI(moduleTab, x, y, width, height);
    }

    @Override
    public void arrayListDesign() {
        ScaledResolution scaledResolution = new ScaledResolution(mc);


        final int[] y = {-4};
        Koks.getKoks().moduleManager.getModules().stream().sorted(Comparator.comparingDouble(mod -> -kekz.getStringWidth(mod.getNameForArrayList("§f")))).forEach(mod -> {

            if (mod.isToggled() && mod.isVisible()) {
                y[0] += kekz.FONT_HEIGHT;
                Gui.drawRect(scaledResolution.getScaledWidth() - kekz.getStringWidth(mod.getNameForArrayList("§f")) - 7, y[0] - 8, scaledResolution.getScaledWidth() - 2, y[0] + 4, new Color(47, 47, 47).getRGB());
                kekz.drawString(mod.getNameForArrayList("§f"), scaledResolution.getScaledWidth() - kekz.getStringWidth(mod.getNameForArrayList("§f")) - 5, (y[0] - 7F), Koks.getKoks().client_color.getRGB());
                Gui.drawRect(scaledResolution.getScaledWidth() - 2, y[0] - 8, scaledResolution.getScaledWidth(), y[0] + 4, Koks.getKoks().client_color.getRGB());
                mod.getAnimationModule().setYAnimation(kekz.FONT_HEIGHT);
            } else {
                mod.getAnimationModule().setYAnimation(0);

            }
        });
    }


    @Override
    public void waterMarkDesign() {
        kek.drawString(Koks.getKoks().CLIENT_NAME.substring(0, 1), 5, 0, Koks.getKoks().client_color.getRGB());
        kek.drawString(Koks.getKoks().CLIENT_NAME.substring(1), 5 + kek.getStringWidth(Koks.getKoks().CLIENT_NAME.substring(0, 1)), 0, Color.white.getRGB());
        Gui.drawRect(6, kek.FONT_HEIGHT - 5, 4 + kek.getStringWidth(Koks.getKoks().CLIENT_NAME) + 1, kek.FONT_HEIGHT - 1, Color.white.getRGB());
        Gui.drawRect(4 + kek.getStringWidth(Koks.getKoks().CLIENT_NAME) + 2, kek.FONT_HEIGHT - 4, 4 + kek.getStringWidth(Koks.getKoks().CLIENT_NAME) + 1, kek.FONT_HEIGHT - 2, Color.white.getRGB());
        Gui.drawRect(5, kek.FONT_HEIGHT - 4, 6, kek.FONT_HEIGHT - 2, Color.white.getRGB());
        Calendar calendar = Calendar.getInstance();
        String hour = calendar.get(Calendar.HOUR) + "";
        String minute = calendar.get(Calendar.MINUTE) + "";
        String second = calendar.get(Calendar.SECOND) + "";

        String formattedHour = hour.length() == 2 ? hour : "0" + hour;
        String formattedMinute = minute.length() == 2 ? minute : "0" + minute;

        String formattedSecond = second.length() == 2 ? second : "0" + second;
        String ampm = calendar.get(Calendar.AM_PM) == Calendar.PM ? "PM" : "AM";

        keks.drawString(formattedHour + ":" + formattedMinute + ":" + formattedSecond + " " + ampm, (keks.getStringWidth(Koks.getKoks().CLIENT_NAME) / 2) + 4, kek.FONT_HEIGHT - 1, Color.white.getRGB());
        String ms = "NONE";
        if (mc.getCurrentServerData() != null) {
            ms = mc.getCurrentServerData().pingToServer + "";
        }
        ScaledResolution scaledResolution = new ScaledResolution(mc);
        Gui.drawRect(0, scaledResolution.getScaledHeight() - 15, kekz.getStringWidth("MS: §f" + ms) + kekz.getStringWidth("FPS: §f" + Minecraft.getDebugFPS()) + kekz.getStringWidth("CONFIG: §f" + Koks.getKoks().configManager.currentConfig.toUpperCase()) + 7 + 7 + 7 + 3, scaledResolution.getScaledHeight(), new Color(49, 48, 55).getRGB());
        kekz.drawString("MS: §f" + ms, 3, scaledResolution.getScaledHeight() - 12, Koks.getKoks().client_color.getRGB());
        Gui.drawRect(kekz.getStringWidth("MS: §f" + ms) + 7, scaledResolution.getScaledHeight() - 15, kekz.getStringWidth("MS: §f" + ms) + 9, scaledResolution.getScaledHeight(), Koks.getKoks().client_color.getRGB());
        kekz.drawString("FPS: §f" + Minecraft.getDebugFPS(), kekz.getStringWidth("MS: §f" + ms) + 11, scaledResolution.getScaledHeight() - 12, Koks.getKoks().client_color.getRGB());
        Gui.drawRect(kekz.getStringWidth("MS: §f" + ms) + kekz.getStringWidth("FPS: §f" + Minecraft.getDebugFPS()) + 7 + 7, scaledResolution.getScaledHeight() - 15, kekz.getStringWidth("MS: §f" + ms) + kekz.getStringWidth("FPS: §f" + Minecraft.getDebugFPS()) + 9 + 7, scaledResolution.getScaledHeight(), Koks.getKoks().client_color.getRGB());
        kekz.drawString("CONFIG: §f" + Koks.getKoks().configManager.currentConfig.toUpperCase(), kekz.getStringWidth("MS: §f" + ms) + kekz.getStringWidth("FPS: §f" + Minecraft.getDebugFPS()) + 7 + 7 + 7, scaledResolution.getScaledHeight() - 12, Koks.getKoks().client_color.getRGB());
        Gui.drawRect(kekz.getStringWidth("MS: §f" + ms) + kekz.getStringWidth("FPS: §f" + Minecraft.getDebugFPS()) + kekz.getStringWidth("CONFIG: §f" + Koks.getKoks().configManager.currentConfig.toUpperCase()) + 7 + 7 + 7 + 3, scaledResolution.getScaledHeight() - 15, kekz.getStringWidth("MS: §f" + ms) + kekz.getStringWidth("FPS: §f" + Minecraft.getDebugFPS()) + kekz.getStringWidth("CONFIG: §f" + Koks.getKoks().configManager.currentConfig.toUpperCase()) + 9 + 7 + 7 + 3, scaledResolution.getScaledHeight(), Koks.getKoks().client_color.getRGB());


    }

    @Override
    public void hotBarDesign(int x, int y, int width, int height, int chooseX, int chooseWidth) {
        Color gray = new Color(47, 47, 47);
        Gui.drawGradientRect(x, y, x + width, y + height, gray.getRGB(), gray.getRGB());
        Gui.drawGradientRect(chooseX, y, chooseX + chooseWidth, y + height, gray.getRGB(), Koks.getKoks().client_color.getRGB());
    }

    @Override
    public boolean drawHotBar() {
        return true;
    }

    @Override
    public boolean drawTabGUI() {
        return false;
    }

    @Override
    public boolean drawWaterMark() {
        return true;
    }

    @Override
    public boolean drawArrayList() {
        return true;
    }
}
