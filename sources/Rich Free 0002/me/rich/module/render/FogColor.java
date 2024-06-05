package me.rich.module.render;



import clickgui.setting.Setting;
import me.rich.Main;
import me.rich.event.EventTarget;
import me.rich.event.events.EventEntityViewRender;
import me.rich.event.events.EventRenderModel;
import me.rich.module.Category;
import me.rich.module.Feature;
import me.rich.notifications.NotificationPublisher;
import me.rich.notifications.NotificationType;

public class FogColor extends Feature {

    public FogColor() {
        super("FogColor", 0, Category.RENDER);
        Main.settingsManager.rSetting(new Setting("Red", this, 255, 0, 255, true));
        Main.settingsManager.rSetting(new Setting("Green", this, 42, 0, 255, true));
        Main.settingsManager.rSetting(new Setting("Blue", this, 42, 0, 255, true));
        Main.settingsManager.rSetting(new Setting("ClientColor", this, true));


    }

    @EventTarget
    public void onFogColorRender(EventRenderModel.EventColorFov event) {
        final float[] hue = { System.currentTimeMillis() % 11520L / 11520.0f };
        final int rgb = Main.getClientColor().getRGB();
        final int r = rgb >> 16 & 0xFF;
        final int g = rgb >> 8 & 0xFF;
        final int b = rgb & 0xFF;
        if (Main.settingsManager.getSettingByName(Main.moduleManager.getModule(FogColor.class), "ClientColor").getValBoolean()) {
            event.setRed(r / 255.0f);
            event.setGreen(g / 255.0f);
            event.setBlue(b / 255.0f);
        } else {
            event.setRed(Main.settingsManager.getSettingByName(Main.moduleManager.getModule(FogColor.class), "Red").getValFloat() / 255.0f);
            event.setGreen(Main.settingsManager.getSettingByName(Main.moduleManager.getModule(FogColor.class), "Green").getValFloat() / 255.0f);
            event.setBlue(Main.settingsManager.getSettingByName(Main.moduleManager.getModule(FogColor.class), "Blue").getValFloat() / 255.0f);
        }
    }

    @EventTarget
    public void fog(EventEntityViewRender.FogDensity event) {
        event.setDensity((float) 0.05);
        event.setCancelled(true);
    }
    @Override
    public void onEnable() {
        super.onEnable();
        NotificationPublisher.queue(getName(), "was enabled.", NotificationType.INFO);
    }

    public void onDisable() {
        NotificationPublisher.queue(getName(), "was disabled.", NotificationType.INFO);
        super.onDisable();
    }
}