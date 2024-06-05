package zamorozka.modules.TRAFFIC;

import java.util.ArrayList;
import java.util.Random;

import de.Hero.settings.Setting;
import net.minecraft.network.play.client.CPacketConfirmTeleport;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import zamorozka.event.EventTarget;
import zamorozka.event.events.EventPreMotionUpdates;
import zamorozka.event.events.EventReceivePacket;
import zamorozka.main.Zamorozka;
import zamorozka.module.Category;
import zamorozka.module.Module;
import zamorozka.ui.ClientUtils;
import zamorozka.ui.MoveUtils;
import zamorozka.ui.MovementUtilis;
import zamorozka.ui.TimerUtils;

public class PacketFly extends Module {

	@Override
	public void setup() {
		ArrayList<String> options = new ArrayList<>();
		options.add("MotionY-");
		options.add("MotionY+");
		options.add("RandomY");
		Zamorozka.instance.settingsManager.rSetting(new Setting("PacketFly Mode", this, "MotionY-", options));
	}

	public PacketFly() {
		super("PacketFly", 0, Category.TRAFFIC);
	}

	private Random _random = new Random();
	private TimerUtils _antiKickTimer = new TimerUtils();

	@EventTarget
	public void onUpdate(EventReceivePacket event) {

		if (event.getPacket() instanceof SPacketPlayerPosLook && mc.currentScreen == null) {
			SPacketPlayerPosLook packet = (SPacketPlayerPosLook) event.getPacket();

			mc.player.connection.sendPacket(new CPacketConfirmTeleport(packet.getTeleportId()));
			mc.player.connection.sendPacket(new CPacketPlayer.PositionRotation(packet.getX(), packet.getY(), packet.getZ(), packet.getYaw(), packet.getPitch(), false));

			mc.player.setPosition(packet.getX(), packet.getY(), packet.getZ());

			event.setCancelled(true);
		}
	}

	@EventTarget
	public void onPlayerUpdate(EventPreMotionUpdates event) {
		String mode = Zamorozka.instance.settingsManager.getSettingByName("PacketFly Mode").getValString();
		mc.player.setVelocity(0, 0, 0);

		// cancel the event, we don't want to send normal packets
		event.setCancelled(true);

		float speedY = 0;

		if (mc.player.movementInput.jump) {
			if (!_antiKickTimer.hasReached(3000))
				speedY = mc.player.ticksExisted % 20 == 0 ? -0.04f : 0.031f;
			else {
				_antiKickTimer.reset();
				speedY = -0.08f;
			}
		} else if (mc.player.movementInput.sneak)
			speedY = -0.0031f;

		final double[] dir = MoveUtils.directionSpeed(0.031);

		mc.player.motionX = dir[0];
		mc.player.motionY = speedY;
		mc.player.motionZ = dir[1];

		mc.player.connection
				.sendPacket(new CPacketPlayer.PositionRotation(mc.player.posX + mc.player.motionX, mc.player.posY + mc.player.motionY, mc.player.posZ + mc.player.motionZ, mc.player.rotationYaw, mc.player.rotationPitch, mc.player.onGround));

		double x = mc.player.posX + mc.player.motionX;
		double y = mc.player.posY + mc.player.motionY;
		double z = mc.player.posZ + mc.player.motionZ;
		if (mode.equalsIgnoreCase("MotionY-")) {
			y -= -1337.0;
			mc.player.connection.sendPacket(new CPacketPlayer.Position(x, y, z, mc.player.onGround));
		}
		if (mode.equalsIgnoreCase("MotionY+")) {
			y += 1337.0;
			mc.player.connection.sendPacket(new CPacketPlayer.Position(x, y, z, mc.player.onGround));
		}
		if (mode.equalsIgnoreCase("RandomY")) {
			y += _random.nextInt(10000);
			mc.player.connection.sendPacket(new CPacketPlayer.Position(x, y, z, mc.player.onGround));
		}
	}
}