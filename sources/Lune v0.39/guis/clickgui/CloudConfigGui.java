package me.superskidder.lune.guis.clickgui;

import static me.superskidder.lune.guis.clickgui.Clickgui.rgb;
import static me.superskidder.lune.modules.Mod.mc;

import java.awt.Color;
import java.io.IOException;

import org.lwjgl.input.Mouse;

import me.superskidder.lune.Lune;
import me.superskidder.lune.font.FontLoaders;
import me.superskidder.lune.guis.clickgui2.Config;
import me.superskidder.lune.manager.ModuleManager;
import me.superskidder.lune.modules.Mod;
import me.superskidder.lune.utils.client.CloudConfigsUtil;
import me.superskidder.lune.utils.client.ErrorUtil;
import me.superskidder.lune.utils.render.RenderUtils;
import net.minecraft.client.gui.ScaledResolution;

/**
 * @author: QianXia
 * @description: 云配置GUI
 * @create: 2021/01/12-16:21
 */
public class CloudConfigGui {
    private static String[] allConfigsNames;
    public int windowX, windowY, windowWeight;

    public CloudConfigGui() {
        new GetConfigs().start();
    }

    public void drawCloudConfig(int windowX, int windowY, int windowWeight, int windowHeight) {
        final ScaledResolution scaledresolution = new ScaledResolution(mc);
        int i1 = scaledresolution.getScaledWidth();
        int j1 = scaledresolution.getScaledHeight();
        final int mouseX = Mouse.getX() * i1 / mc.displayWidth;
        final int mouseY = j1 - Mouse.getY() * j1 / mc.displayHeight - 1;
        int realY = 30;
        this.windowX = windowX;
        this.windowY = windowY;
        this.windowWeight = windowWeight;


        if (allConfigsNames == null) {
            FontLoaders.F16.drawString("Loading...", windowX + 210, windowY + realY + 8, rgb);
            return;
        }

        for (String config : allConfigsNames) {
            FontLoaders.F16.drawString(config, windowX + 210, windowY + realY + 8, rgb);

            if (Lune.configInUsing != null && Lune.configInUsing.getName().equals(config)) {
                if (Clickgui.isHovered(windowX + windowWeight - 36, windowY + realY + 5, windowX + windowWeight - 15, windowY + realY + 15, mouseX, mouseY)) {
                    RenderUtils.drawRoundRect(windowX + windowWeight - 36, windowY + realY + 5, windowX + windowWeight - 15, windowY + realY + 15, new Color(127, 147, 255).getRGB());
                } else {
                    RenderUtils.drawRoundRect(windowX + windowWeight - 36, windowY + realY + 5, windowX + windowWeight - 15, windowY + realY + 15, new Color(107, 127, 255).getRGB());
                }

                RenderUtils.drawCircle(windowX + windowWeight - 20, windowY + realY + 10, 3, new Color(255, 255, 255).getRGB());
            } else {
                if (Clickgui.isHovered(windowX + windowWeight - 36, windowY + realY + 5, windowX + windowWeight - 15, windowY + realY + 15, mouseX, mouseY)) {
                    RenderUtils.drawRoundRect((float) (windowX + windowWeight - 36.5), (float) (windowY + realY + 4.5), (float) (windowX + windowWeight - 14.5), (float) (windowY + realY + 15.5), Clickgui.color_select);
                }
                RenderUtils.drawRoundRect(windowX + windowWeight - 36, windowY + realY + 5, windowX + windowWeight - 15, windowY + realY + 15, Clickgui.color_unselect);
                RenderUtils.drawCircle(windowX + windowWeight - 30, windowY + realY + 10, 3, new Color(112, 112, 112).getRGB());
            }
            realY += 20;
        }
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        int valueStartY = 30;
        int valueRealY = valueStartY;

        if (allConfigsNames == null) {
            return;
        }

        for (String configName : allConfigsNames) {
            if (Clickgui.isHovered(windowX + windowWeight - 36, windowY + valueRealY + 5, windowX + windowWeight - 15, windowY + valueRealY + 15, mouseX, mouseY)) {
                if (Lune.configInUsing != null && Lune.configInUsing.getName().equals(configName)) {
                    for (Mod mod : ModuleManager.modList) {
                        if (mod.getState() && !mod.getName().equalsIgnoreCase("clickgui")) {
                            mod.setStage(false);
                        }
                    }
                    Lune.configInUsing = null;
                    return;
                }
                LoadCloudConfig thread = new LoadCloudConfig(configName);
                thread.start();
                return;
            }
            valueRealY += 20;
        }
    }

    class GetConfigs extends Thread {
        @Override
        public void run() {
            try {
                allConfigsNames = CloudConfigsUtil.getAllConfigsNameList();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    class LoadCloudConfig extends Thread {
        private String configName;

        public LoadCloudConfig(String configName) {
            this.configName = configName;
        }

        @Override
        public void run() {
            try {
                Lune.configInUsing = new Config(configName, "Cloud", false);
                CloudConfigsUtil.loadCloudConfig(configName);
            } catch (IOException e) {
                ErrorUtil.printException(e);
            }
        }
    }
}
