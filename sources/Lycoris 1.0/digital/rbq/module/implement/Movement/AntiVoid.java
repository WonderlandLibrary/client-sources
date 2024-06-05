package digital.rbq.module.implement.Movement;

import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import digital.rbq.event.PacketReceiveEvent;
import digital.rbq.event.PacketSendEvent;
import digital.rbq.module.Category;
import digital.rbq.module.Module;

import com.darkmagician6.eventapi.EventTarget;

import digital.rbq.module.ModuleManager;
import digital.rbq.module.value.FloatValue;
import digital.rbq.utility.ChatUtils;
import digital.rbq.utility.MoveUtils;
import digital.rbq.utility.TimeHelper;

import java.util.ArrayList;

public class AntiVoid extends Module {
	public double[] lastGroundPos = new double[3];
	public static FloatValue pullbackTime = new FloatValue("AntiVoid", "Pullback Time", 1500f, 1000f, 2000f, 100f);
	public static TimeHelper timer = new TimeHelper();
	public static ArrayList<C03PacketPlayer> packets = new ArrayList<>();

	public AntiVoid() {
		super("AntiVoid", Category.Movement, false);
	}

	public static boolean isInVoid() {
		for (int i = 0; i <= 128; i++) {
			if (MoveUtils.isOnGround(i)) {
				return false;
			}
		}
		return true;
	}

	@EventTarget
	public void onPacket(PacketSendEvent e) {
		if (!packets.isEmpty() && mc.thePlayer.ticksExisted < 100)
			packets.clear();

		if (e.getPacket() instanceof C03PacketPlayer) {
			C03PacketPlayer packet = ((C03PacketPlayer) e.getPacket());
			if (isInVoid()) {
				e.setCancelled(true);
				packets.add(packet);

				if (timer.isDelayComplete(pullbackTime.getValueState())) {
					ChatUtils.debug("Send Packets");
					mc.getNetHandler().getNetworkManager().sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(lastGroundPos[0], lastGroundPos[1] - 1, lastGroundPos[2], true));
				}
			} else {
				lastGroundPos[0] = mc.thePlayer.posX;
				lastGroundPos[1] = mc.thePlayer.posY;
				lastGroundPos[2] = mc.thePlayer.posZ;

				if (!packets.isEmpty()) {
					ChatUtils.debug("Release Packets - " + packets.size());
					for (C03PacketPlayer p : packets)
						mc.getNetHandler().getNetworkManager().sendPacketNoEvent(p);
					packets.clear();
				}
				timer.reset();
			}
		}
	}

	@EventTarget
	public void onRevPacket(PacketReceiveEvent e) {
		if (e.getPacket() instanceof S08PacketPlayerPosLook && packets.size() > 1) {
			ChatUtils.debug("Pullbacks Detected, clear packets list!");
			packets.clear();
		}
	}

	public static boolean isPullbacking() {
		return ModuleManager.antiVoidMod.isEnabled() && !packets.isEmpty();
	}
}
