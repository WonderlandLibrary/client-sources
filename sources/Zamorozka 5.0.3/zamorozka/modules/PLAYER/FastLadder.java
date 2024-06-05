package zamorozka.modules.PLAYER;

import java.util.ArrayList;

import de.Hero.settings.Setting;
import net.minecraft.network.play.client.CPacketPlayer;
import zamorozka.main.Zamorozka;
import zamorozka.module.Category;
import zamorozka.module.Module;

public class FastLadder extends Module {

	@Override
	public void setup() {
		Zamorozka.instance.settingsManager.rSetting(new Setting("LadderSpeed", this, 5, 1.1, 10, true));
	}

	public FastLadder() {
		super("FastLadder", 0, Category.PLAYER);
	}

	public void onUpdate() {
		if (!getState())
			return;
		if (mc.player.isOnLadder()) {
			if (mc.player.isCollidedHorizontally) {
				if (mc.player.ticksExisted % 15 == 0) {
					mc.player.connection.sendPacket(new CPacketPlayer(true));
				}
				if (mc.player.ticksExisted % 2 == 0) {
					mc.player.setPosition(mc.player.posX, mc.player.posY + 0.1, mc.player.posZ);
				}

			}
		}

	}
}
