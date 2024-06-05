package zamorozka.modules.TRAFFIC;

import java.util.ArrayList;

import de.Hero.settings.Setting;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import zamorozka.event.EventTarget;
import zamorozka.event.events.EventPacket;
import zamorozka.event.events.EventPreMotionUpdates;
import zamorozka.main.Zamorozka;
import zamorozka.module.Category;
import zamorozka.module.Module;
import zamorozka.module.ModuleManager;
import zamorozka.modules.PLAYER.NoFall;
import zamorozka.modules.ZAMOROZKA.LagCheck;
import zamorozka.notification.NotificationPublisher;
import zamorozka.notification.NotificationType;
import zamorozka.ui.ClientUtils;

public class Timer extends Module {

	@Override
	public void setup() {
		Zamorozka.settingsManager.rSetting(new Setting("Timer speed", this, 2, 0, 5, true));
	}

	public Timer() {
		super("Timer", 0, Category.TRAFFIC);
	}
	
	@EventTarget
	public void onUpdate(EventPreMotionUpdates event) {
		if (!getState()) {
			return;
		}
		
		double mode = Zamorozka.settingsManager.getSettingByName("Timer speed").getValDouble();
		this.setDisplayName("Timer §f§ " + ClientUtils.round((float) Zamorozka.settingsManager.getSettingByName("Timer speed").getValDouble(), 2));

		mc.timer.timerSpeed = (float) Zamorozka.settingsManager.getSettingByName("Timer speed").getValDouble();

	}

	@EventTarget
	public void onPacket1(EventPacket event) {
		Packet<?> p = event.getPacket();
		if (event.isIncoming()) {
			if (p instanceof SPacketPlayerPosLook && mc.player != null) {
				mc.player.onGround = false;
				mc.player.motionX *= 0;
				mc.player.motionZ *= 0;
				mc.player.jumpMovementFactor = 0;
				if (Zamorozka.moduleManager.getModule(Timer.class).getState() && Zamorozka.moduleManager.getModule(LagCheck.class).getState()) {
					Zamorozka.moduleManager.getModule(Timer.class).toggle();
					NotificationPublisher.queue("LagBack", "Timer was lagback!", NotificationType.WARNING);
					isEnabled = false;
					ModuleManager.getModule(Timer.class).setState(false);
				}
			}
		}
	}

	@Override
	public void onDisable() {
		mc.timer.timerSpeed = 1f;
		isEnabled = false;
		super.onDisable();
	}

}