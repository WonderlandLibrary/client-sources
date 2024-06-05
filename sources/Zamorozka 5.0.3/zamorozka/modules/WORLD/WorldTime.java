package zamorozka.modules.WORLD;

import org.lwjgl.input.Keyboard;

import de.Hero.settings.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.server.SPacketTimeUpdate;
import zamorozka.event.EventTarget;
import zamorozka.event.events.EventPacket;
import zamorozka.event.events.EventReceivePacket;
import zamorozka.event.events.EventTick;
import zamorozka.event.events.EventUpdate;
import zamorozka.main.Zamorozka;
import zamorozka.module.Category;
import zamorozka.module.Module;
import zamorozka.ui.TimerHelper;

public class WorldTime extends Module {

	TimerHelper timer = new TimerHelper();
	
	@Override
	public void setup() {
		Zamorozka.settingsManager.rSetting(new Setting("WorldTime", this, 5000, 0.1, 16000, true));
		Zamorozka.settingsManager.rSetting(new Setting("SmoothTime", this, false));
		Zamorozka.settingsManager.rSetting(new Setting("SmoothTimeSpeed", this, 2, 1, 10, false));
	}

	public WorldTime() {
		super("WorldTime", Keyboard.KEY_NONE, Category.WORLD);
	}

	@EventTarget
	public void onPacket(EventPacket event) {
		if (event.getPacket() instanceof SPacketTimeUpdate) {
			event.setCancelled(true);
		}
	}

	@EventTarget
	public void onUpdate(EventUpdate event) {
		boolean nosmooth = Zamorozka.settingsManager.getSettingByName("SmoothTime").getValBoolean();
		long time = (long) Zamorozka.settingsManager.getSettingByName("WorldTime").getValDouble();
		long speed = (long) Zamorozka.settingsManager.getSettingByName("SmoothTimeSpeed").getValDouble();
		if (!nosmooth) {
			mc.world.setWorldTime(time);
		}
		else {
			mc.world.setWorldTime(timer.getCurrentMS() * speed);
		}
		if(timer.getCurrentMS() > 30000 && getState()) {
            timer.reset();
        }
	}
}