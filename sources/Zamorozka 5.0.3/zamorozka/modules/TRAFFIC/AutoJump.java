package zamorozka.modules.TRAFFIC;

import java.util.ArrayList;

import de.Hero.settings.Setting;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketEntityAction.Action;
import zamorozka.event.EventTarget;
import zamorozka.event.events.EventUpdate;
import zamorozka.main.Zamorozka;
import zamorozka.module.Category;
import zamorozka.module.Module;

public class AutoJump extends Module {

	public AutoJump() {
		super("AutoJump", 0, Category.TRAFFIC);
	}

	@Override
	public void setup() {
		ArrayList<String> options = new ArrayList<>();
		options.add("Vanilla");
		options.add("Packet");
		Zamorozka.instance.settingsManager.rSetting(new Setting("AutoJump Mode", this, "Vanilla", options));
	}

	@EventTarget
	public void onUpdate(EventUpdate event) {
		String mode = Zamorozka.instance.settingsManager.getSettingByName("AutoJump Mode Mode").getValString();
		if (mode.equalsIgnoreCase("Vanilla")) {
			mc.gameSettings.keyBindJump.pressed = true;
		}
		if (mode.equalsIgnoreCase("Packet")) {
			double x = mc.player.posX;
			double y = mc.player.posY;
			double z = mc.player.posZ;
			if (mc.player.onGround) {
				mc.player.connection.sendPacket(new CPacketPlayer.Position(x, y + 0.4, z, true));
			}
		}
	}

	@Override
	public void onDisable() {
		if (!GameSettings.isKeyDown(mc.gameSettings.keyBindJump))
			mc.gameSettings.keyBindJump.pressed = false;
		super.onDisable();
	}

}