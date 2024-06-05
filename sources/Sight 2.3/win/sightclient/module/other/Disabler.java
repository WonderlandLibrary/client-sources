package win.sightclient.module.other;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.network.play.client.C00PacketKeepAlive;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.network.play.client.C0CPacketInput;
import net.minecraft.network.play.client.C0FPacketConfirmTransaction;
import net.minecraft.network.play.client.C13PacketPlayerAbilities;
import net.minecraft.network.play.client.C18PacketSpectate;
import win.sightclient.event.Event;
import win.sightclient.event.events.client.EventPacket;
import win.sightclient.event.events.player.EventFlag;
import win.sightclient.event.events.player.EventMove;
import win.sightclient.module.Category;
import win.sightclient.module.Module;
import win.sightclient.module.settings.ModeSetting;
import win.sightclient.utils.minecraft.ChatUtils;
import win.sightclient.utils.minecraft.MoveUtils;

public class Disabler extends Module {

	private ModeSetting mode = new ModeSetting("Mode", this, new String[] {"Hypixel", "Ghostly", "HypixelHover"});
	
	public Disabler() {
		super("Disabler", Category.OTHER);
	}

	@Override
	public void onEvent(Event e) {
		if (e instanceof EventPacket) {
			this.setSuffix(this.mode.getValue());
			EventPacket ep = (EventPacket)e;
			if (this.mode.getValue().equalsIgnoreCase("Ghostly")) {
	            if (ep.getPacket() instanceof C03PacketPlayer) {
	                mc.thePlayer.sendQueue.addToSendQueue(new C18PacketSpectate(mc.thePlayer.getGameProfile().getId()));
	                mc.thePlayer.sendQueue.addToSendQueue(new C0CPacketInput(1.05F, 1.05F, true, false));
	            }
	            if (ep.getPacket() instanceof C0FPacketConfirmTransaction || ep.getPacket() instanceof C00PacketKeepAlive) {
	                e.setCancelled();
	            }
	            if (ep.getPacket() instanceof C0BPacketEntityAction) {
	                e.setCancelled();
	            }
			} else if (ep.getPacket() instanceof C03PacketPlayer && this.mode.getValue().equalsIgnoreCase("HypixelHover")) {
				ep.setCancelled();
			}
		} else if (e instanceof EventFlag && this.mode.getValue().equalsIgnoreCase("HypixelHover")) {
			this.setToggled(false);
			ChatUtils.sendMessage("Disabled checks.");
		} else if (e instanceof EventMove && this.mode.getValue().equalsIgnoreCase("HypixelHover")) {
			MoveUtils.setMotion((EventMove) e, 0);
		} else if (e instanceof EventPacket && mc.thePlayer != null && mc.theWorld != null && this.mode.getValue().equalsIgnoreCase("Hypixel")) {
			EventPacket ep = (EventPacket)e;
			if (ep.isSending()) {
	            if (ep.getPacket() instanceof C0FPacketConfirmTransaction) {
	                final C0FPacketConfirmTransaction packetConfirmTransaction = (C0FPacketConfirmTransaction)ep.getPacket();
	                Minecraft.getMinecraft().getNetHandler().addToSendQueueNoEvent(new C0FPacketConfirmTransaction(Integer.MAX_VALUE, packetConfirmTransaction.getUid(), false));
	                ep.setCancelled();
	            }
	            if (ep.getPacket() instanceof C00PacketKeepAlive) {
	            	ep.setCancelled();
	            }
	            if (ep.getPacket() instanceof C13PacketPlayerAbilities) {
	            	ep.setCancelled();
	            }
			}
		}
	}
	
	@Override
	public void onEnable() {
		if (this.mode.getValue().equalsIgnoreCase("HypixelHover")) {
			mc.thePlayer.sendQueue.addToSendQueueNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(
					mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, true));
			mc.thePlayer.sendQueue.addToSendQueueNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(
					mc.thePlayer.posX, mc.thePlayer.posY + 0.342, mc.thePlayer.posZ, true));
			mc.thePlayer.sendQueue.addToSendQueueNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(
					mc.thePlayer.posX, mc.thePlayer.posY + 0.1542, mc.thePlayer.posZ, true));
		} else if (this.mode.getValue().equalsIgnoreCase("Hypixel")) {
			ChatUtils.sendMessage("Please relog for the disabler to work.");
		}
	}
	
	public static void sendDisable(boolean state) {
		PlayerCapabilities playerCapabilities = new PlayerCapabilities();
        playerCapabilities.allowFlying = state;
        playerCapabilities.isFlying = state;
        mc.thePlayer.sendQueue.addToSendQueueNoEvent(new C13PacketPlayerAbilities(playerCapabilities));
	}
}
