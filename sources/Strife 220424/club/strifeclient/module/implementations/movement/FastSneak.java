package club.strifeclient.module.implementations.movement;

import best.azura.eventbus.handler.EventHandler;
import best.azura.eventbus.handler.Listener;
import club.strifeclient.event.implementations.player.MotionEvent;
import club.strifeclient.module.Category;
import club.strifeclient.module.Module;
import club.strifeclient.module.ModuleInfo;
import club.strifeclient.util.networking.PacketUtil;
import net.minecraft.network.play.client.C0BPacketEntityAction;

@ModuleInfo(name = "FastSneak", description = "Always sneak server-side.", category = Category.MOVEMENT)
public final class FastSneak extends Module {
    @EventHandler
    private final Listener<MotionEvent> motionEventListener = e -> {
          if (e.isPre())
              PacketUtil.sendPacketNoEvent(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.STOP_SNEAKING));
          else PacketUtil.sendPacketNoEvent(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.STOP_SNEAKING));
    };
}
