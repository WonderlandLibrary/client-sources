package me.r.touchgrass.ui.ingame.style.styles;

import me.r.touchgrass.touchgrass;
import me.r.touchgrass.module.Module;
import me.r.touchgrass.ui.ingame.components.ArrayList;
import me.r.touchgrass.ui.ingame.components.Hotbar;
import me.r.touchgrass.ui.ingame.components.Info;
import me.r.touchgrass.ui.ingame.components.Watermark;
import me.r.touchgrass.ui.ingame.style.Style;
import me.r.touchgrass.utils.*;
import me.r.touchgrass.utils.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.player.EntityPlayer;

import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Iterator;

/**
 * Created by r on 18/01/2022
 */
public class Classic implements Style {

    static final Minecraft mc = Minecraft.getMinecraft();
    static final DateTimeFormatter timeFormat12 = DateTimeFormatter.ofPattern("h:mm a");
    static final DateTimeFormatter timeFormat24 = DateTimeFormatter.ofPattern("HH:mm");
    static final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    public static boolean lmao;

    public static void loadSettings() {
        if(!lmao) {
            ArrayList arrayListModule = touchgrass.getClient().moduleManager.getModule(ArrayList.class);
            Watermark watermark = touchgrass.getClient().moduleManager.getModule(Watermark.class);

            touchgrass.getClient().settingsManager.getSettingByName("List Color").setMode("White");
            touchgrass.getClient().settingsManager.getSettingByName(arrayListModule, "Background").setState(false);
            touchgrass.getClient().settingsManager.getSettingByName(watermark, "Background").setState(false);
            lmao = true;
        }
    }

    public static synchronized void classicArrayThread() {
        new Thread(() -> {
            while (true) {
                try {
                    if (!ReflectionUtil.running.getBoolean(mc)) {
                        break;
                    }
                    long listDelay = (long) touchgrass.getClient().settingsManager.getSettingByName("List Delay").getValue();
                    Thread.sleep(listDelay);

                    Iterator<Module> iterator = touchgrass.getClient().moduleManager.getModules().iterator();

                    while (iterator.hasNext()) {
                        Module mod = iterator.next();
                        if (mod.isEnabled()) {
                            if (mod.getSlideMC() < Minecraft.getMinecraft().fontRendererObj.getStringWidth(mod.getName())) {
                                mod.setSlideMC(mod.getSlideMC() + 1);
                            }

                        } else if (mod.getSlideMC() != 0 && !mod.isEnabled()) {
                            if (mod.getSlideMC() > 0) {
                                mod.setSlideMC(mod.getSlideMC() - 1);
                            }

                        }
                    }
                } catch (Exception ignored) {
                }
            }
        },"smooth array minecraft font").start();
    }

    public static void sortModules() {
        boolean lengthSort = touchgrass.getClient().settingsManager.getSettingByName("Sorting").getMode().equalsIgnoreCase("Length");
        if(lengthSort) {
            touchgrass.getClient().moduleManager.getModules().sort((m1, m2) -> Integer.compare(Minecraft.getMinecraft().fontRendererObj.getStringWidth(m2.getName()), Minecraft.getMinecraft().fontRendererObj.getStringWidth(m1.getName())));
        } else {
            touchgrass.getClient().moduleManager.getModules().sort(Comparator.comparing(Module::getName));
        }
    }

    @Override
    public void drawArrayList() {
        int count = 0;
        float rbdelay = (float) touchgrass.getClient().settingsManager.getSettingByName("Rb. Delay").getValue();
        float rbsaturation = (float) touchgrass.getClient().settingsManager.getSettingByName("Rb. Saturation").getValue();
        float rbcolorcount = (float) touchgrass.getClient().settingsManager.getSettingByName("Rb. Color Count").getValue();

        for (int i = 0; i < touchgrass.getClient().moduleManager.getEnabledMods().size(); i++) {
            ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
            Module mod = touchgrass.getClient().moduleManager.getEnabledMods().get(i);
            int mheight = count * 11 + i + 3;
            Color rainbow = ColorUtil.getRainbowColor(rbdelay, rbsaturation, 1, (long) (count * rbcolorcount));
            Color color = Color.WHITE;
            switch (touchgrass.getClient().settingsManager.getSettingByName("List Color").getMode()) {
                case "White":
                    color = Color.WHITE;
                    break;
                case "Category":
                    color = mod.getColor();
                    break;
                case "Rainbow":
                    color = rainbow;
                    break;
            }
            ArrayList arrayList = touchgrass.getClient().moduleManager.getModule(ArrayList.class);
            if(touchgrass.getClient().settingsManager.getSettingByName(arrayList, "Background").isEnabled()) {
                Gui.drawRect(sr.getScaledWidth() - mod.getSlideMC() - 6, 13 + i * 12, sr.getScaledWidth(), i * 12 + 1, Integer.MIN_VALUE);
            }

            Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(mod.getName(), sr.getScaledWidth() - mod.getSlideMC() - 3, mheight, color.getRGB());
            count++;
        }
    }

    @Override
    public void drawInfo() {
        String x = String.valueOf((int) mc.thePlayer.posX);
        String y = String.valueOf((int) mc.thePlayer.posY);
        String z = String.valueOf((int) mc.thePlayer.posZ);
        String coordinates = String.format("XYZ §7(%s, %s, %s)", x, y, z);
        String coordinates_x = String.format("X §7%s", x);
        String coordinates_y = String.format("Y §7%s", y);
        String coordinates_z = String.format("Z §7%s", z);

        String fps = String.format("FPS §7%s", Minecraft.getDebugFPS());
        boolean lines = touchgrass.getClient().settingsManager.getSettingByName(touchgrass.getClient().moduleManager.getModule(Info.class), "XYZ Style").getMode().equalsIgnoreCase("1-Line");


        if (touchgrass.getClient().settingsManager.getSettingByName(touchgrass.getClient().moduleManager.getModule(Info.class), "Alignment").getMode().equalsIgnoreCase("right")) {

            if (mc.ingameGUI.getChatGUI().getChatOpen()) {
                if (lines) {
                    mc.fontRendererObj.drawStringWithShadow(fps, Utils.getScaledRes().getScaledWidth() - mc.fontRendererObj.getStringWidth(fps) - 3, Utils.getScaledRes().getScaledHeight() - 37, -1);
                    mc.fontRendererObj.drawStringWithShadow(coordinates, Utils.getScaledRes().getScaledWidth() - mc.fontRendererObj.getStringWidth(coordinates) - 2, Utils.getScaledRes().getScaledHeight() - 26, -1);
                } else {
                    mc.fontRendererObj.drawStringWithShadow(fps, Utils.getScaledRes().getScaledWidth() - mc.fontRendererObj.getStringWidth(fps) - 2, Utils.getScaledRes().getScaledHeight() - 59, -1);
                    mc.fontRendererObj.drawStringWithShadow(coordinates_x, Utils.getScaledRes().getScaledWidth() - mc.fontRendererObj.getStringWidth(coordinates_x) - 3, Utils.getScaledRes().getScaledHeight() - 48, -1);
                    mc.fontRendererObj.drawStringWithShadow(coordinates_y, Utils.getScaledRes().getScaledWidth() - mc.fontRendererObj.getStringWidth(coordinates_y) - 3, Utils.getScaledRes().getScaledHeight() - 37, -1);
                    mc.fontRendererObj.drawStringWithShadow(coordinates_z, Utils.getScaledRes().getScaledWidth() - mc.fontRendererObj.getStringWidth(coordinates_z) - 3, Utils.getScaledRes().getScaledHeight() - 26, -1);
                }

            } else {

                if (lines) {
                    mc.fontRendererObj.drawStringWithShadow(fps, Utils.getScaledRes().getScaledWidth() - mc.fontRendererObj.getStringWidth(fps) - 3, Utils.getScaledRes().getScaledHeight() - 23, -1);
                    mc.fontRendererObj.drawStringWithShadow(coordinates, Utils.getScaledRes().getScaledWidth() - mc.fontRendererObj.getStringWidth(coordinates) - 2, Utils.getScaledRes().getScaledHeight() - 12, -1);
                } else {
                    mc.fontRendererObj.drawStringWithShadow(fps, Utils.getScaledRes().getScaledWidth() - mc.fontRendererObj.getStringWidth(fps) - 3, Utils.getScaledRes().getScaledHeight() - 45, -1);
                    mc.fontRendererObj.drawStringWithShadow(coordinates_x, Utils.getScaledRes().getScaledWidth() - mc.fontRendererObj.getStringWidth(coordinates_x) - 2, Utils.getScaledRes().getScaledHeight() - 34, -1);
                    mc.fontRendererObj.drawStringWithShadow(coordinates_y, Utils.getScaledRes().getScaledWidth() - mc.fontRendererObj.getStringWidth(coordinates_y) - 2, Utils.getScaledRes().getScaledHeight() - 23, -1);
                    mc.fontRendererObj.drawStringWithShadow(coordinates_z, Utils.getScaledRes().getScaledWidth() - mc.fontRendererObj.getStringWidth(coordinates_z) - 2, Utils.getScaledRes().getScaledHeight() - 12, -1);
                }
            }

        } else {

            if (mc.ingameGUI.getChatGUI().getChatOpen()) {

                if (lines) {
                    mc.fontRendererObj.drawStringWithShadow(coordinates, 2, Utils.getScaledRes().getScaledHeight() - 26, -1);
                    mc.fontRendererObj.drawStringWithShadow(fps, 2, Utils.getScaledRes().getScaledHeight() - 37, -1);
                } else {
                    mc.fontRendererObj.drawStringWithShadow(fps, 2, Utils.getScaledRes().getScaledHeight() - 59, -1);
                    mc.fontRendererObj.drawStringWithShadow(coordinates_x, 2, Utils.getScaledRes().getScaledHeight() - 48, -1);
                    mc.fontRendererObj.drawStringWithShadow(coordinates_y, 2, Utils.getScaledRes().getScaledHeight() - 37, -1);
                    mc.fontRendererObj.drawStringWithShadow(coordinates_z, 2, Utils.getScaledRes().getScaledHeight() - 26, -1);
                }

            } else {

                if (lines) {
                    mc.fontRendererObj.drawStringWithShadow(coordinates, 2, Utils.getScaledRes().getScaledHeight() - 12, -1);
                    mc.fontRendererObj.drawStringWithShadow(fps, 2, Utils.getScaledRes().getScaledHeight() - 23, -1);
                } else {
                    mc.fontRendererObj.drawStringWithShadow(fps, 2, Utils.getScaledRes().getScaledHeight() - 45, -1);
                    mc.fontRendererObj.drawStringWithShadow(coordinates_x, 2, Utils.getScaledRes().getScaledHeight() - 34, -1);
                    mc.fontRendererObj.drawStringWithShadow(coordinates_y, 2, Utils.getScaledRes().getScaledHeight() - 23, -1);
                    mc.fontRendererObj.drawStringWithShadow(coordinates_z, 2, Utils.getScaledRes().getScaledHeight() - 12, -1);
                }
            }
        }
    }

    @Override
    public void drawPotionEffects() {
        RenderUtil.drawPotionEffectsMC();
    }

    @Override
    public void drawWatermark() {
        Watermark watermarkModule = touchgrass.getClient().moduleManager.getModule(Watermark.class);
        boolean background = touchgrass.getClient().settingsManager.getSettingByName(watermarkModule, "Background").isEnabled();
        boolean time = touchgrass.getClient().settingsManager.getSettingByName("Time").isEnabled();
        boolean outline = touchgrass.getClient().settingsManager.getSettingByName(watermarkModule, "Outline").isEnabled();
        boolean timeformat = touchgrass.getClient().settingsManager.getSettingByName("Time Format").getMode().equals("24H");
        LocalDateTime now = LocalDateTime.now();
        String currenttime = timeformat ? timeFormat24.format(now) : timeFormat12.format(now);

        if(!touchgrass.getClient().isStableBuild && !(touchgrass.getClient().moduleManager.getModule(Hotbar.class).isEnabled() || touchgrass.getClient().settingsManager.getSettingByName("Alignment").getMode().equalsIgnoreCase("Left"))) {
            mc.fontRendererObj.drawStringWithShadow("§7Indev Build", 2, Utils.getScaledRes().getScaledHeight() - (mc.ingameGUI.getChatGUI().getChatOpen() ? 38 : 24), -1);
            mc.fontRendererObj.drawStringWithShadow(String.format("§7Latest Commit: %s | %s", HTTPUtil.commitDate, HTTPUtil.commitTime), 2, Utils.getScaledRes().getScaledHeight() - (mc.ingameGUI.getChatGUI().getChatOpen() ? 26 : 12), -1);
        }

        if (time) {

            String watermark = String.format("%s %s §7(%s)" + (touchgrass.getClient().outdated ? " §7(Outdated)" : ""), touchgrass.name, touchgrass.version, currenttime);

            if (background) {
                Gui.drawRect(0, 0, mc.fontRendererObj.getStringWidth(watermark) + 3, 11, Integer.MIN_VALUE);
            }

            if(outline) {
                Gui.drawRect(mc.fontRendererObj.getStringWidth(watermark) + 4, 0, mc.fontRendererObj.getStringWidth(watermark) + 3, 11, 0x99000000);
                Gui.drawRect(0, 11, mc.fontRendererObj.getStringWidth(watermark) + 4, 12, 0x99000000);
            }


            mc.fontRendererObj.drawStringWithShadow(watermark, 2, 2, -1);

        } else {

            String watermark_no_time = String.format("%s %s" + (touchgrass.getClient().outdated ? " §7(Outdated)" : ""), touchgrass.name, touchgrass.version);

            if(outline) {
                Gui.drawRect(0, 11, mc.fontRendererObj.getStringWidth(watermark_no_time) + 4, 12, 0x99000000);
                Gui.drawRect(mc.fontRendererObj.getStringWidth(watermark_no_time) + 4, 0, mc.fontRendererObj.getStringWidth(watermark_no_time) + 3, 11, 0x99000000);
            }

            if (background) {
                Gui.drawRect(0, 0, mc.fontRendererObj.getStringWidth(watermark_no_time) + 3, 11, Integer.MIN_VALUE);
            }

            mc.fontRendererObj.drawStringWithShadow(watermark_no_time, 2, 2, -1);
        }
    }

    @Override
    public void drawHotbar() {
        EntityPlayer entityplayer = (EntityPlayer) Minecraft.getMinecraft().getRenderViewEntity();

        float needX = ((float) Utils.getScaledRes().getScaledWidth() / 2 - 91 + entityplayer.inventory.currentItem * 20);
        float steps = 10f;

        Module mod = touchgrass.getClient().moduleManager.getModulebyName("Hotbar");
        boolean fps = touchgrass.getClient().settingsManager.getSettingByName(mod, "FPS").isEnabled();
        boolean coord = touchgrass.getClient().settingsManager.getSettingByName(mod, "Coordinates").isEnabled();
        boolean tdate = touchgrass.getClient().settingsManager.getSettingByName(mod, "Time / Date").isEnabled();

        Utils.addSlide(needX, steps);

        boolean timeformat = touchgrass.getClient().settingsManager.getSettingByName("Time Format").getMode().equals("24H");
        LocalDateTime now = LocalDateTime.now();
        String date = dateFormat.format(now);
        String time = timeformat ? timeFormat24.format(now) : timeFormat12.format(now);
        String fps1 = String.format("FPS §7%s", Minecraft.getDebugFPS());

        String x = String.valueOf((int) mc.thePlayer.posX);
        String y = String.valueOf((int) mc.thePlayer.posY);
        String z = String.valueOf((int) mc.thePlayer.posZ);

        String coordinates = String.format("X: §7%s §fY: §7%s §fZ: §7%s", x, y, z);

        if (tdate) {
            mc.fontRendererObj.drawStringWithShadow(date, Utils.getScaledRes().getScaledWidth() - mc.fontRendererObj.getStringWidth(date) - 10, Utils.getScaledRes().getScaledHeight() - 10, -1);
            mc.fontRendererObj.drawStringWithShadow(time, Utils.getScaledRes().getScaledWidth() - mc.fontRendererObj.getStringWidth(time) - 10, Utils.getScaledRes().getScaledHeight() - 21, -1);
        }

        if (coord) {
            mc.fontRendererObj.drawStringWithShadow(coordinates, 2, Utils.getScaledRes().getScaledHeight() - 10, -1);
        }

        if (fps) {
            mc.fontRendererObj.drawStringWithShadow(fps1, 2, coord ? Utils.getScaledRes().getScaledHeight() - 21 : Utils.getScaledRes().getScaledHeight() - 10, -1);
        }
    }
}
