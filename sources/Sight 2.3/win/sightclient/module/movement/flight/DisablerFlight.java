package win.sightclient.module.movement.flight;

import net.minecraft.network.play.client.C00PacketKeepAlive;
import net.minecraft.network.play.client.C03PacketPlayer;
import win.sightclient.event.Event;
import win.sightclient.event.events.client.EventPacket;
import win.sightclient.event.events.player.EventFlag;
import win.sightclient.event.events.player.EventMove;
import win.sightclient.module.Module;
import win.sightclient.module.ModuleMode;
import win.sightclient.module.settings.NumberSetting;
import win.sightclient.utils.minecraft.ChatUtils;
import win.sightclient.utils.minecraft.MoveUtils;

public class DisablerFlight extends ModuleMode {

	private boolean disabled = false;
	private NumberSetting speed;
	
	public DisablerFlight(Module parent, NumberSetting speed) {
		super(parent);
		this.speed = speed;
	}

	@Override
	public void onEnable() {
		disabled = false;
		if (mc.thePlayer == null) {
			this.parent.setToggled(false);
			return;
		}
		mc.thePlayer.sendQueue.addToSendQueueNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(
				mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, true));
		mc.thePlayer.sendQueue.addToSendQueueNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(
				mc.thePlayer.posX, mc.thePlayer.posY + 0.342, mc.thePlayer.posZ, true));
		mc.thePlayer.sendQueue.addToSendQueueNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(
				mc.thePlayer.posX, mc.thePlayer.posY + 0.1542, mc.thePlayer.posZ, true));
	}
	
	@Override
	public void onEvent(Event e) {
		 if (e instanceof EventFlag) {
			 if (this.disabled) {
				 this.parent.setToggled(false);
			 } else {
					this.disabled = true;
					ChatUtils.sendMessage("FLY!");
			 }
		} else if (e instanceof EventMove) {
			if (!disabled) {
				MoveUtils.setMotion((EventMove) e, 0);
			} else {
				EventMove em = (EventMove)e;
				if (mc.gameSettings.keyBindJump.isKeyDown() && !mc.gameSettings.keyBindSneak.isKeyDown()) {
					em.setY(mc.thePlayer.motionY = speed.getValue());
				} else if (mc.gameSettings.keyBindSneak.isKeyDown() && !mc.gameSettings.keyBindJump.isKeyDown()) {
					em.setY(mc.thePlayer.motionY = -speed.getValue());
				} else {
					em.setY(mc.thePlayer.motionY = 0);
				}
				MoveUtils.setMotion(em, speed.getValue());
			}
		}
		if (e instanceof EventPacket) {
			EventPacket ep = (EventPacket)e;
			if (!this.disabled && ep.getPacket() instanceof C03PacketPlayer) {
				ep.setCancelled();
			} else if (this.disabled && ep.getPacket() instanceof C00PacketKeepAlive) {
				ep.setCancelled();
			}
		}
	}
}
