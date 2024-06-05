package me.rich.module.player;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import clickgui.setting.Setting;
import me.rich.Main;
import me.rich.event.EventTarget;
import me.rich.event.events.EventPacket;
import me.rich.module.Category;
import me.rich.module.Feature;
import me.rich.notifications.NotificationPublisher;
import me.rich.notifications.NotificationType;
import net.minecraft.network.play.client.CPacketPlayer;

public class Spider extends Feature {
    public Spider() {
        super("Spider", Keyboard.KEY_NONE, Category.PLAYER);
        ArrayList<String> spider = new ArrayList<String>();
        spider.add("Vanilla");
        spider.add("Matrix");
        Main.instance.settingsManager.rSetting(new Setting("Spider Mode", this, "Vanilla", spider));
    }

    @EventTarget
    public void EventSendPacket(EventPacket event) {
        String mode = Main.instance.settingsManager.getSettingByName("Spider Mode").getValString();
        if (mode.equalsIgnoreCase("Vanilla")) {
            if (mc.player.isCollidedHorizontally) {
                mc.player.motionY = 0.40f;
            }
        }
        
        if (mode.equalsIgnoreCase("Matrix")) {
            if (mc.player.isCollidedHorizontally) {
                if (event.getPacket() instanceof CPacketPlayer) {
                    CPacketPlayer packet = (CPacketPlayer) event.getPacket();
                    if (timerHelper.hasReached(150)) {
                        timerHelper.reset();
                        mc.player.jump();
                        mc.player.motionY = 0.42f;
                        packet.setOnGround(true);
                    }
                }
            }
        }
    }

    @Override
    public void onEnable() {
        super.onEnable();
        NotificationPublisher.queue(this.getName(), "was enabled.", NotificationType.INFO);
    }

    public void onDisable() {
        NotificationPublisher.queue(this.getName(), "was disabled.", NotificationType.INFO);
        super.onDisable();
    }
}
