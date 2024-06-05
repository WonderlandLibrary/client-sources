package win.sightclient.module.movement;

import net.minecraft.network.play.client.C00PacketKeepAlive;
import net.minecraft.network.play.client.C03PacketPlayer;
import win.sightclient.event.Event;
import win.sightclient.event.events.client.EventPacket;
import win.sightclient.event.events.client.EventUpdate;
import win.sightclient.event.events.player.EventFlag;
import win.sightclient.event.events.player.EventMove;
import win.sightclient.module.Category;
import win.sightclient.module.Module;
import win.sightclient.utils.minecraft.ChatUtils;
import win.sightclient.utils.minecraft.MoveUtils;

public class RocketShip extends Module {

	private boolean disabled = false;
	
	public RocketShip() {
		super("RocketShip", Category.MOVEMENT);
		this.showInClickGUI = false;
	}

	@Override
	public void onEnable() {
		ChatUtils.sendMessage("Congrats, you found this secret module.");
		// Hello final & skidders
		disabled = false;
		mc.thePlayer.sendQueue.addToSendQueueNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(
				mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, true));
		mc.thePlayer.sendQueue.addToSendQueueNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(
				mc.thePlayer.posX, mc.thePlayer.posY + 0.342, mc.thePlayer.posZ, true));
		mc.thePlayer.sendQueue.addToSendQueueNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(
				mc.thePlayer.posX, mc.thePlayer.posY + 0.1542, mc.thePlayer.posZ, true));
	}
	
	@Override
	public void onEvent(Event e) {
		if (e instanceof EventPacket) {
			EventPacket ep = (EventPacket)e;
			if (!this.disabled && ep.getPacket() instanceof C03PacketPlayer) {
				ep.setCancelled();
			} else if (this.disabled && ep.getPacket() instanceof C00PacketKeepAlive) {
				ep.setCancelled();
			}
		} else if (e instanceof EventUpdate) {
			if (this.disabled) {
				int max = 150;
				for (int i = 0; i < (max / 5); i++) {
					mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(
							mc.thePlayer.posX, mc.thePlayer.posY + (5 * i), mc.thePlayer.posZ, true));
				}
				mc.thePlayer.setPosition(mc.thePlayer.posX,
						mc.thePlayer.posY + max, mc.thePlayer.posZ);
				this.setToggled(false);
			}
		} else if (e instanceof EventFlag) {
			this.disabled = true;
		} else if (e instanceof EventMove) {
			MoveUtils.setMotion((EventMove) e, 0);
		}
	}
}
