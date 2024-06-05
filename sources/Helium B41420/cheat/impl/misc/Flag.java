package rip.helium.cheat.impl.misc;

import me.hippo.systems.lwjeb.annotation.Collect;
import net.minecraft.block.BlockAir;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;
import rip.helium.Helium;
import rip.helium.cheat.Cheat;
import rip.helium.cheat.CheatCategory;
import rip.helium.event.minecraft.ProcessPacketEvent;
import rip.helium.event.minecraft.SendPacketEvent;
import rip.helium.notification.mgmt.NotificationManager;
import rip.helium.utils.Stopwatch;

public class Flag extends Cheat {
	
	public Flag() {
		super("FlagDetector", "epicogamero", CheatCategory.MISC);
	}
	
    @Collect
    public void onproc(ProcessPacketEvent e) {
           if (e.getPacket() instanceof S08PacketPlayerPosLook) {
               final S08PacketPlayerPosLook packet = (S08PacketPlayerPosLook)e.getPacket();
               NotificationManager.postWarning("FlagDetector", "Flag! Disabled movement hacks.");//Cloverhook.instance.cheatManager.
               Helium.instance.cheatManager.getCheatRegistry().get("Speed").setState(false, false);
               Helium.instance.cheatManager.getCheatRegistry().get("Flight").setState(false, false);
        }
    }

}
