package me.rich.module.render;

import clickgui.setting.Setting;
import me.rich.Main;
import me.rich.event.EventTarget;
import me.rich.event.events.EventTransformSideFirstPerson;
import me.rich.module.Category;
import me.rich.module.Feature;
import me.rich.notifications.NotificationPublisher;
import me.rich.notifications.NotificationType;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.EnumHandSide;

public class ViewModel extends Feature {
    public static Setting rightx;
    public static Setting righty;
    public static Setting rightz;
    public static Setting leftx;
    public static Setting lefty;
    public static Setting leftz;

    public ViewModel() {
        super("ViewModel" ,0, Category.RENDER);
        leftx = new Setting("LeftX", this, 0.0, -2.0, 2.0, false);
        Main.instance.settingsManager.rSetting(leftx);
        lefty = new Setting("LeftY", this, 0.2, -2.0, 2.0, false);
        Main.instance.settingsManager.rSetting(lefty);
        leftz = new Setting("LeftZ", this, 0.2, -2.0, 2.0, false);
        Main.instance.settingsManager.rSetting(leftz);
        rightx = new Setting("RightX", this, 0.0, -2.0, 2.0, false);
        Main.instance.settingsManager.rSetting(rightx);
        righty = new Setting("RightY", this, 0.2, -2.0, 2.0, false);
        Main.instance.settingsManager.rSetting(righty);
        rightz = new Setting("RightZ", this, 0.2, -2.0, 2.0, false);
        Main.instance.settingsManager.rSetting(rightz);
    }

    @EventTarget
    public void onSidePerson(EventTransformSideFirstPerson event) {
        if (event.getEnumHandSide() == EnumHandSide.RIGHT) {
            GlStateManager.translate(rightx.getValDouble(), righty.getValDouble(), rightz.getValDouble());
        }
        if (event.getEnumHandSide() == EnumHandSide.LEFT) {
            GlStateManager.translate(-leftx.getValDouble(), lefty.getValDouble(), leftz.getValDouble());
        }
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