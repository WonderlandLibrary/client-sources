/**
 * 
 */
package cafe.kagu.kagu.mods.impl.player;

import cafe.kagu.kagu.eventBus.EventHandler;
import cafe.kagu.kagu.eventBus.Handler;
import cafe.kagu.kagu.eventBus.impl.EventPacketReceive;
import cafe.kagu.kagu.eventBus.impl.EventPacketSend;
import cafe.kagu.kagu.eventBus.impl.EventPlayerUpdate;
import cafe.kagu.kagu.eventBus.impl.EventTick;
import cafe.kagu.kagu.mods.Module;
import cafe.kagu.kagu.settings.impl.ModeSetting;
import cafe.kagu.kagu.utils.ChatUtils;
import cafe.kagu.kagu.utils.MovementUtils;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;

/**
 * @author DistastefulBannock
 *
 */
public class ModNoFall extends Module {
	
	public ModNoFall() {
		super("NoFall", Category.PLAYER);
		setSettings(mode);
	}
	
	private ModeSetting mode = new ModeSetting("Mode", "Hypixel", "Hypixel", "Always On Ground", "Always Off Ground", "Packet", "GroundClip", "FastClip", "Test");
	
	@EventHandler
	private Handler<EventTick> onTick = e ->{
		if (e.isPost())
			return;
		setInfo(mode.getMode());
	};
	
	@EventHandler
	private Handler<EventPlayerUpdate> onPlayerUpdate = e -> {
		if (e.isPost())
			return;
		EntityPlayerSP thePlayer = mc.thePlayer;
		switch (mode.getMode()) {
			case "Hypixel":{
				if (thePlayer.fallDistance > 2) {
					mc.getNetHandler().getNetworkManager().sendPacketNoEvent(new C03PacketPlayer(true));
					thePlayer.fallDistance = 0;
				}
			}break;
			case "Always On Ground":{
				e.setOnGround(true);
			}break;
			case "Always Off Ground":{
				e.setOnGround(false);
			}break;
			case "Packet":{
				if (thePlayer.fallDistance > 2) {
					mc.getNetHandler().getNetworkManager().sendPacketNoEvent(new C03PacketPlayer(true));
				}
			}break;
			case "FastClip":{
				if (thePlayer.fallDistance > 3 && thePlayer.motionY < 0 && !MovementUtils.isTrueOnGround() && MovementUtils.isTrueOnGround(-thePlayer.motionY)) {
					thePlayer.fallDistance = 0;
					thePlayer.offsetPosition(0, thePlayer.motionY - 0.08, 0);
				}
			}break;
		}
	};
	
	@EventHandler
	private Handler<EventPacketReceive> onPacketReceive = e -> {
		if (e.isPre())
			return;
		EntityPlayerSP thePlayer = mc.thePlayer;
		switch (mode.getMode()) {
			case "GroundClip":{
				if (e.getPacket() instanceof S08PacketPlayerPosLook && thePlayer.fallDistance >= 3) {
					thePlayer.fallDistance = 0;
					thePlayer.jump();
				}
			}break;
		}
	};
	
	/**
	 * @return the mode
	 */
	public ModeSetting getMode() {
		return mode;
	}
	
}
