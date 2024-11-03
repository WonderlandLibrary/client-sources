package net.silentclient.client.utils;

import net.minecraft.client.gui.GuiScreen;
import net.silentclient.client.Client;
import net.silentclient.client.gui.util.BlurUtil;
import net.silentclient.client.mixin.accessors.GuiAccessor;
import net.silentclient.client.mods.settings.GeneralMod;

import java.awt.*;

public class MenuBlurUtils {
    public static void loadBlur() {
        loadBlur(false);
    }

    public static void loadBlur(boolean force) {
//        if(Client.getInstance().getSettingsManager().getSettingByClass(GeneralMod.class, "Menu Background Blur").getValBoolean() || force) {
//            ((EntityRendererExt) Minecraft.getMinecraft().entityRenderer).silent$loadShader(new StaticResourceLocation("shaders/post/menu_blur.json"));
//        }
    }

    public static void renderBackground(GuiScreen instance) {
        if(Client.getInstance().getSettingsManager().getSettingByClass(GeneralMod.class, "Menu Background Blur").getValBoolean()) {
            BlurUtil.blur2(0, 0, 1920, 1080, 0, 0);
            ((GuiAccessor) instance).silent$drawGradientRect(0, 0, instance.width, instance.height, new Color(0, 0, 0, 0).getRGB(), new Color(0, 0, 0, 0).getRGB());
        } else {
            instance.drawDefaultBackground();
        }
    }

    public static void unloadBlur() {
        unloadBlur(false);
    }

    public static void unloadBlur(boolean force) {
//        if(Client.getInstance().getSettingsManager().getSettingByClass(GeneralMod.class, "Menu Background Blur").getValBoolean() || force) {
//            Minecraft.getMinecraft().entityRenderer.loadEntityShader(null);
//        }
    }
}
