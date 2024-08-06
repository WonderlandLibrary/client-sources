package club.strifeclient.module.implementations.combat;

import best.azura.eventbus.handler.EventHandler;
import best.azura.eventbus.handler.Listener;
import club.strifeclient.event.implementations.networking.PacketOutboundEvent;
import club.strifeclient.module.Category;
import club.strifeclient.module.Module;
import club.strifeclient.module.ModuleInfo;
import club.strifeclient.util.networking.PacketUtil;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C0BPacketEntityAction;

@ModuleInfo(name = "WTap", description = "Automatically W-Tap", category = Category.COMBAT)
public final class WTap extends Module {
    @EventHandler
    private final Listener<PacketOutboundEvent> motionEventListener = e -> {
        if (e.getPacket() instanceof C02PacketUseEntity) {
            final C02PacketUseEntity packet = e.getPacket();
            if (packet.getAction() == C02PacketUseEntity.Action.ATTACK) {
                mc.thePlayer.setSprinting(false);
                PacketUtil.sendPacketNoEvent(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.STOP_SPRINTING));
                mc.thePlayer.setSprinting(true);
                PacketUtil.sendPacketNoEvent(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.START_SPRINTING));
            }
        }
    };
}
