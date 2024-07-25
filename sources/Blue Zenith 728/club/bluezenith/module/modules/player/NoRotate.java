package club.bluezenith.module.modules.player;

import club.bluezenith.events.Listener;
import club.bluezenith.events.impl.PacketEvent;
import club.bluezenith.module.Module;
import club.bluezenith.module.ModuleCategory;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;

@SuppressWarnings("unused")
public class NoRotate extends Module {
	public NoRotate() {
		super("NoRotate", ModuleCategory.PLAYER, "norotateset");
	}

	@Listener
	public void onPacket(PacketEvent e) {
		if (e.packet instanceof S08PacketPlayerPosLook) {
			if(player == null) return;
			S08PacketPlayerPosLook the = (S08PacketPlayerPosLook) e.packet;
			the.yaw = player.rotationYaw;
			the.pitch = player.rotationPitch;
		}
	}

}
