package zamorozka.modules.WORLD;

import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.network.play.server.SPacketSpawnExperienceOrb;
import net.minecraft.network.play.server.SPacketSpawnGlobalEntity;
import net.minecraft.network.play.server.SPacketSpawnObject;
import net.minecraft.network.play.server.SPacketSpawnPainting;
import zamorozka.event.EventTarget;
import zamorozka.event.events.EventReceivePacket;
import zamorozka.module.Category;
import zamorozka.module.Module;

public class NoEntitySpawnPackets extends Module {

	public NoEntitySpawnPackets() {
		super("NoEntitySpawnPackets", 0, Category.WORLD);
	}
	
	@EventTarget
	public void receivePacket(EventReceivePacket event) {
		if (event.getPacket() instanceof SPacketSpawnGlobalEntity) {
			SPacketSpawnGlobalEntity packet = (SPacketSpawnGlobalEntity)event.getPacket();
			event.setCancelled(true);
		}
		if (event.getPacket() instanceof SPacketSpawnObject) {
			SPacketSpawnObject packet = (SPacketSpawnObject)event.getPacket();
			event.setCancelled(true);
		}
		if (event.getPacket() instanceof SPacketSpawnExperienceOrb) {
			SPacketSpawnExperienceOrb packet = (SPacketSpawnExperienceOrb)event.getPacket();
			event.setCancelled(true);
		}
		if (event.getPacket() instanceof SPacketSpawnPainting) {
			SPacketSpawnPainting packet = (SPacketSpawnPainting)event.getPacket();
			event.setCancelled(true);
		}
	}
}