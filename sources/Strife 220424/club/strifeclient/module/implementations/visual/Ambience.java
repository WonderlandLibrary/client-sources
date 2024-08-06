package club.strifeclient.module.implementations.visual;

import best.azura.eventbus.handler.EventHandler;
import best.azura.eventbus.handler.Listener;
import club.strifeclient.event.implementations.networking.PacketInboundEvent;
import club.strifeclient.module.Category;
import club.strifeclient.module.Module;
import club.strifeclient.module.ModuleInfo;
import club.strifeclient.setting.implementations.DoubleSetting;
import net.minecraft.network.play.server.S03PacketTimeUpdate;

@ModuleInfo(name = "Ambience", description = "Change the world time.", aliases = {"WorldTime", "TimeChanger"}, category = Category.VISUAL)
public final class Ambience extends Module {
    private final DoubleSetting timeProperty = new DoubleSetting("Time", 0, 0, 24000, 100);
    public void onTick() {
        if (!isEnabled()) return;
        if (mc.theWorld != null)
            mc.theWorld.setWorldTime(timeProperty.getLong());
    }
    @EventHandler
    private final Listener<PacketInboundEvent> packetInboundListener = e -> {
        if (e.getPacket() instanceof S03PacketTimeUpdate) {
            e.setCancelled(true);
            onTick();
        }
    };
}
