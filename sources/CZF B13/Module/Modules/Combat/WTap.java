package gq.vapu.czfclient.Module.Modules.Combat;

import gq.vapu.czfclient.API.EventHandler;
import gq.vapu.czfclient.API.Events.World.EventPacketSend;
import gq.vapu.czfclient.Module.Module;
import gq.vapu.czfclient.Module.ModuleType;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C0BPacketEntityAction;

public class WTap extends Module {

    public WTap() {
        super("WTap", new String[]{"wtapper"}, ModuleType.Combat);
    }

    @EventHandler
    private void onTick(EventPacketSend e) {
        C02PacketUseEntity packet;
        if (e.getType() == 2 && EventPacketSend.getPacket() instanceof C02PacketUseEntity && mc.thePlayer != null && (packet = (C02PacketUseEntity) EventPacketSend.getPacket()).getAction() == C02PacketUseEntity.Action.ATTACK && packet.getEntityFromWorld(mc.theWorld) != mc.thePlayer && mc.thePlayer.getFoodStats().getFoodLevel() > 6) {
            boolean sprint = mc.thePlayer.isSprinting();
            mc.thePlayer.setSprinting(false);
            mc.thePlayer.sendQueue.addToSendQueue(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.STOP_SPRINTING));
            mc.thePlayer.sendQueue.addToSendQueue(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.START_SPRINTING));
            mc.thePlayer.setSprinting(sprint);
        }
    }
}

