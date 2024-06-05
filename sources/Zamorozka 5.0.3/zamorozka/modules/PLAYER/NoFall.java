package zamorozka.modules.PLAYER;

import java.util.ArrayList;
import java.util.UUID;

import de.Hero.settings.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketConfirmTeleport;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayer.Position;
import net.minecraft.network.play.client.CPacketSpectate;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import zamorozka.event.EventTarget;
import zamorozka.event.events.EventPacket;
import zamorozka.event.events.EventUpdate;
import zamorozka.main.Zamorozka;
import zamorozka.module.Category;
import zamorozka.module.Module;
import zamorozka.module.ModuleManager;
import zamorozka.modules.TRAFFIC.Timer;
import zamorozka.modules.WORLD.Terrain;
import zamorozka.modules.ZAMOROZKA.LagCheck;
import zamorozka.notification.NotificationPublisher;
import zamorozka.notification.NotificationType;
import zamorozka.ui.MovementUtilis;
import zamorozka.ui.TimerHelper;

public class NoFall extends Module {

	TimerHelper timer;

	public NoFall() {
		super("NoFall", 0, Category.PLAYER);
	}

	@Override
	public void setup() {
		ArrayList<String> options = new ArrayList<>();
		options.add("Vanilla");
		options.add("MatrixTest");
		options.add("OldMatrix");
		options.add("AAC");
		Zamorozka.instance.settingsManager.rSetting(new Setting("NoFall Mode", this, "MatrixTest", options));
	}

	@EventTarget
	public void onUpdate(EventUpdate event) {
		String mode = Zamorozka.instance.settingsManager.getSettingByName("NoFall Mode").getValString();
		String modeput = Character.toUpperCase(mode.charAt(0)) + mode.substring(1);
		this.setDisplayName("NoFall §f§" + " " + modeput);
		if (mode.equalsIgnoreCase("Vanilla")) {
			if (mc.player.fallDistance > 2) {
				mc.player.connection.sendPacket(new CPacketPlayer(true));
			}
		}
		if (mode.equalsIgnoreCase("MatrixTest")) {
			if (mc.player.fallDistance > 5f) {
				mc.player.capabilities.isFlying = true;
				mc.getConnection().sendPacket(new CPacketPlayer(true));
				mc.player.isInWeb = true;
			} else {
				mc.player.capabilities.isFlying = false;
			}
		}
		if (mode.equalsIgnoreCase("OldMatrix")) {
			if (mc.player.fallDistance >= 2.2f) {
				mc.player.motionX = 0.04f;
				mc.player.motionY = -55f;
				mc.player.connection.sendPacket(new CPacketPlayer(true));
				mc.player.connection.sendPacket(new CPacketPlayer(true));
				mc.player.connection.sendPacket(new CPacketPlayer(true));
			}
		}
		if (mode.equalsIgnoreCase("AAC")) {
			if (mc.player.fallDistance >= 2f) {
				mc.player.motionX = 0.01;
				mc.player.motionY = -0.0010101001323131;
				mc.player.motionX = 0.05;
				mc.player.motionY = -0.0010101001323131;
				mc.player.motionX = 0.00;
				mc.player.motionY = -0.0010101001323131;
				mc.player.connection.sendPacket(new CPacketPlayer(true));
				mc.player.connection.sendPacket(new CPacketPlayer(false));
				mc.player.connection.sendPacket(new CPacketPlayer(true));
				mc.player.connection.sendPacket(new CPacketPlayer(false));
				mc.player.connection.sendPacket(new CPacketPlayer(true));
			}
		}
	}
}