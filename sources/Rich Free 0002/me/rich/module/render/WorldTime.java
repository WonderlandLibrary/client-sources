package me.rich.module.render;

import org.lwjgl.input.Keyboard;

import clickgui.setting.Setting;
import me.rich.Main;
import me.rich.event.EventTarget;
import me.rich.event.events.EventReceivePacket;
import me.rich.event.events.EventUpdate;
import me.rich.helpers.world.TimerHelper;
import me.rich.module.Category;
import me.rich.module.Feature;
import me.rich.notifications.NotificationPublisher;
import me.rich.notifications.NotificationType;
import net.minecraft.network.play.server.SPacketTimeUpdate;


public class WorldTime extends Feature {

	TimerHelper timer = new TimerHelper();
	
	@Override
	public void setup() {
		Main.settingsManager.rSetting(new Setting("TimeSpeed", this, 2, 1, 10, false));
	}

	public WorldTime() {
		super("WorldTime", Keyboard.KEY_NONE, Category.RENDER);
	}

	@EventTarget
	public void onPacket(EventReceivePacket event) {
		if (event.getPacket() instanceof SPacketTimeUpdate) {
			event.setCancelled(true);
		}
	}

	@EventTarget
	public void onUpdate(EventUpdate event) {
		long speed = (long) Main.settingsManager.getSettingByName("TimeSpeed").getValDouble();
			mc.world.setWorldTime(timer.getCurrentMS() * speed);
		
		if(timer.getCurrentMS() > 30000 && isToggled()) {
            timer.reset();
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