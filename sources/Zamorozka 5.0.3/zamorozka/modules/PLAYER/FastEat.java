package zamorozka.modules.PLAYER;

import java.util.ArrayList;

import de.Hero.settings.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemShield;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.CPacketPlayer;
import zamorozka.event.EventTarget;
import zamorozka.event.events.EventPacket;
import zamorozka.event.events.EventReceivePacket;
import zamorozka.event.events.EventUpdate;
import zamorozka.main.Zamorozka;
import zamorozka.module.Category;
import zamorozka.module.Module;

public class FastEat extends Module {

	@Override
	public void setup() {
		ArrayList<String> options = new ArrayList<>();
		options.add("Vanilla");
		options.add("VanillaNew");
		options.add("Matrix");
		options.add("AAC");
		Zamorozka.instance.settingsManager.rSetting(new Setting("FastEat Mode", this, "Matrix", options));
		Zamorozka.settingsManager.rSetting(new Setting("MatrixMaxDuration", this, 16, 1, 20, true));
		Zamorozka.settingsManager.rSetting(new Setting("MatrixPackets", this, 20, 0, 500, true));
	}

	public FastEat() {
		super("FastEat", 0, Category.PLAYER);
	}

	@EventTarget
	public void onUpdate(EventUpdate event) {
		String mode = Zamorozka.settingsManager.getSettingByName("FastEat Mode").getValString();
		String modeput = Character.toUpperCase(mode.charAt(0)) + mode.substring(1);
		this.setDisplayName("FastEat §f§" + " " + modeput);
		if (mode.equalsIgnoreCase("Vanilla")) {
			if (mc.player.getItemInUseDuration() > 14) {
				if (mc.player.isEating() || mc.player.isDrinking()) {
					for (int i = 0; i < 50; i++) {
						sendPacket(new CPacketPlayer());
					}
					mc.player.stopActiveHand();
				}
			}
		}
		if (mode.equalsIgnoreCase("VanillaNew")) {
			if (mc.player.isEating() || mc.player.isDrinking()) {
	            for (int i = 0; i != 37; ++i) {
	                this.mc.getConnection().sendPacket(new CPacketPlayer(this.mc.player.onGround));
	            }
				mc.player.stopActiveHand();
	        }
		}
		if (mode.equalsIgnoreCase("AAC")) {
			if (mc.player.isEating() || mc.player.isDrinking()) {
				if (mc.player.onGround) {
					if (!mc.player.isOnLadder()) {
						if (!mc.player.isInWater()) {
							if (!mc.player.isInLava()) {
								mc.timer.timerSpeed = 1.2f;
								return;
							}
						}
					}
				}
			}
			mc.timer.timerSpeed = 1f;
		}
		if (mode.equalsIgnoreCase("Matrix")) {
			float dur = (float) Zamorozka.settingsManager.getSettingByName("MatrixMaxDuration").getValDouble();
			if (mc.player.getItemInUseDuration() >= dur) {
				if (mc.player.isEating() || mc.player.isDrinking()) {
					float value = (float) Zamorozka.settingsManager.getSettingByName("MatrixPackets").getValDouble();
					for (int i = 0; i < value; i++) {
						sendPacket(new CPacketPlayer(mc.player.onGround));
					}
					mc.player.stopActiveHand();
				}
			}
		}
	}

	@Override
	public void onEnable() {
		super.onEnable();
	}

	@Override
	public void onDisable() {
		Minecraft.getMinecraft().player.stepHeight = 0.6f;
		Minecraft.getMinecraft().timer.timerSpeed = 1.0F;
		super.onDisable();
	}

}