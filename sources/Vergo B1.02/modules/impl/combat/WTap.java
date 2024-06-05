package xyz.vergoclient.modules.impl.combat;

import xyz.vergoclient.event.Event;
import xyz.vergoclient.event.impl.EventSendPacket;
import xyz.vergoclient.modules.Module;
import xyz.vergoclient.modules.OnEventInterface;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C02PacketUseEntity.Action;
import net.minecraft.network.play.client.C0BPacketEntityAction;

public class WTap extends Module implements OnEventInterface {

	public WTap() {
		super("WTap", Category.COMBAT);
	}

	@Override
	public void onEvent(Event e) {
		
		if (e instanceof EventSendPacket) {
			if (((EventSendPacket)e).packet instanceof C02PacketUseEntity && ((C02PacketUseEntity)((EventSendPacket)e).packet).getAction() == Action.ATTACK) {
				if (e.isPre()) {
					mc.getNetHandler().getNetworkManager().sendPacketNoEvent(new C0BPacketEntityAction(mc.thePlayer, net.minecraft.network.play.client.C0BPacketEntityAction.Action.STOP_SPRINTING));
					mc.getNetHandler().getNetworkManager().sendPacketNoEvent(new C0BPacketEntityAction(mc.thePlayer, net.minecraft.network.play.client.C0BPacketEntityAction.Action.START_SPRINTING));
				}
			}
		}
		
	}
	
}
