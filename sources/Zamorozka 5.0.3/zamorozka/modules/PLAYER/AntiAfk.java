package zamorozka.modules.PLAYER;

import java.util.ArrayList;
import java.util.Random;

import de.Hero.settings.Setting;
import net.minecraft.network.play.client.CPacketPlayer;
import zamorozka.event.EventTarget;
import zamorozka.event.events.EventPreMotionUpdates;
import zamorozka.event.events.EventUpdate;
import zamorozka.main.Zamorozka;
import zamorozka.module.Category;
import zamorozka.module.Module;

public class AntiAfk extends Module {

	@Override
	public void setup() {
		ArrayList<String> options = new ArrayList<>();
		options.add("Sunrise");
		options.add("Jetmine");
		Zamorozka.instance.settingsManager.rSetting(new Setting("AntiAFK Mode", this, "Sunrise", options));
	}

	public AntiAfk() {
		super("AntiAFK", 0, Category.PLAYER);
	}

	@EventTarget
	public void onPreUpdate(EventPreMotionUpdates event) {
		String mode = Zamorozka.settingsManager.getSettingByName("AntiAFK Mode").getValString();
		if (mode.equalsIgnoreCase("Jetmine")) {
			final Random random = new Random();
			float yaw = random.nextFloat() * 90F;
			float pitch = random.nextFloat() * 90F;
			if (mc.player.ticksExisted % 7 == 0) {
				mc.player.connection.sendPacket(new CPacketPlayer.Rotation(yaw, mc.player.rotationPitch, false));
				mc.player.rotationYawHead = yaw;
				mc.player.renderYawOffset = yaw;
			}
		}
		if (mode.equalsIgnoreCase("Sunrise")) {
			if (mc.player.ticksExisted % 10 == 0) {
				mc.gameSettings.keyBindForward.pressed = true;
			} else {
				mc.gameSettings.keyBindForward.pressed = false;
			}
			if (mc.player.ticksExisted % 15 == 0) {
				mc.gameSettings.keyBindBack.pressed = true;
			} else {
				mc.gameSettings.keyBindBack.pressed = false;
			}
			if (mc.player.ticksExisted % 20 == 0) {
				mc.gameSettings.keyBindLeft.pressed = true;
			} else {
				mc.gameSettings.keyBindLeft.pressed = false;
			}
			if (mc.player.ticksExisted % 25 == 0) {
				mc.gameSettings.keyBindRight.pressed = true;
			} else {
				mc.gameSettings.keyBindRight.pressed = false;
			}
			final Random random = new Random();
			float yaw = random.nextFloat() * 90F;
			float pitch = random.nextFloat() * 90F;
			if (mc.player.ticksExisted % 15 == 0) {
				mc.player.connection.sendPacket(new CPacketPlayer.Rotation(yaw, mc.player.rotationPitch, false));
				mc.player.rotationYawHead = yaw;
				mc.player.renderYawOffset = yaw;
			}
			if (mc.player.ticksExisted % 300 == 0) {
				mc.player.sendChatMessage("kykareky!111");
			}
			if (mc.player.ticksExisted % 500 == 0) {
				mc.clickMouse();
			}
		}
	}
}