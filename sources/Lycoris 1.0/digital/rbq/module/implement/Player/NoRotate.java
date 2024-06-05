package digital.rbq.module.implement.Player;

import com.darkmagician6.eventapi.EventTarget;

import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import digital.rbq.event.PacketReceiveEvent;
import digital.rbq.event.PacketSendEvent;
import digital.rbq.event.RespawnEvent;
import digital.rbq.module.Category;
import digital.rbq.module.Module;
import digital.rbq.module.value.BooleanValue;
import digital.rbq.utility.ChatUtils;
import digital.rbq.utility.DelayTimer;

public class NoRotate extends Module {

	private DelayTimer timer = new DelayTimer();
	public NoRotate() {
		super("NoRotate", Category.Player, false);
	}
	public static BooleanValue silent = new BooleanValue("NoRotate", "Silent", true);
	@EventTarget
	public void onPacketTake(PacketReceiveEvent event) {
		if (!timer.hasPassed(5000)) return;

		if (event.packet instanceof S08PacketPlayerPosLook) {
			S08PacketPlayerPosLook packet = (S08PacketPlayerPosLook) event.packet;
			float yaw = packet.getYaw(), pitch = packet.getPitch();
			ChatUtils.debug("Pullback Detected: " + yaw + " " + pitch + " - " + mc.thePlayer.rotationYaw + " " + mc.thePlayer.rotationPitch);

			if (silent.getValue()) {
				float yawSpilt = (mc.thePlayer.rotationYaw - yaw) / 5, pitchSpilt = (mc.thePlayer.rotationPitch - pitch) / 5;

				mc.getNetHandler().getNetworkManager().sendPacketNoEvent(new C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, yaw, pitch, mc.thePlayer.onGround));
				for (int i = 0; i < 5; i++) {
					mc.getNetHandler().getNetworkManager().sendPacketNoEvent(new C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, yaw += yawSpilt, pitch += pitchSpilt, mc.thePlayer.onGround));
					ChatUtils.debug(yaw + " " + pitch);
				}
			}

			packet.setYaw(mc.thePlayer.rotationYaw);
			packet.setPitch(mc.thePlayer.rotationPitch);
		}
	}

	@EventTarget
	public void onRespawn(RespawnEvent event) {
		timer.reset();
	}
}
