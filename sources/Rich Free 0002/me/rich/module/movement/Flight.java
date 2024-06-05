package me.rich.module.movement;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import clickgui.setting.*;
import me.rich.Main;
import me.rich.event.EventTarget;
import me.rich.event.events.EventUpdate;
import me.rich.event.events.EventUpdateLiving;
import me.rich.helpers.movement.MovementHelper;
import me.rich.module.Category;
import me.rich.module.Feature;
import me.rich.module.hud.FeatureList;
import me.rich.notifications.NotificationPublisher;
import me.rich.notifications.NotificationType;

public class Flight extends Feature {

    public Flight() {
        super("Flight", 0, Category.MOVEMENT);
		Main.instance.settingsManager.rSetting(new Setting("Speed", this, 0.5F, 0.1F, 5.0F, false));

    }

    @EventTarget
    public void onUpdate(EventUpdateLiving e) {
        this.setModuleName(this.name + " §7[" + Main.settingsManager.getSettingByName(Main.moduleManager.getModule(Flight.class), "Speed").getValFloat() + "]");
        if (mc.player.onGround) {
            mc.player.jump();
        } else {
            mc.player.motionX = 0;
            mc.player.motionZ = 0;
            mc.player.motionY = -0.01;
            mc.player.capabilities.isFlying = true;
             mc.player.capabilities.setFlySpeed(Main.settingsManager.getSettingByName(Main.moduleManager.getModule(Flight.class), "Speed").getValFloat()); 
             mc.player.speedInAir = 0.3f;
            if (mc.gameSettings.keyBindSneak.isKeyDown()) {
                mc.player.motionY -= 0.6;
            } else if (mc.gameSettings.keyBindJump.isKeyDown()) {
                mc.player.motionY += 0.6;
            }
        }
        }
    
    @Override
    public void onEnable() {
        super.onEnable();
        NotificationPublisher.queue(getName(), "was enabled.", NotificationType.INFO);
    }
    @Override
    public void onDisable() {
        super.onDisable();
        NotificationPublisher.queue(getName(), "was disabled.", NotificationType.INFO);

        mc.player.speedInAir = 0.02f;
        mc.timer.timerSpeed = 1.0f;
        mc.player.capabilities.isFlying = false;
        mc.player.capabilities.setFlySpeed(0.05F);
            mc.player.motionY = 0;
            mc.player.motionX = 0;
            mc.player.motionZ = 0;
    }
}