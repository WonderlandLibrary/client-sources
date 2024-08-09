package dev.darkmoon.client.module.impl.player;

import com.darkmagician6.eventapi.EventTarget;
import dev.darkmoon.client.event.packet.EventReceivePacket;
import dev.darkmoon.client.module.Category;
import dev.darkmoon.client.module.Module;
import dev.darkmoon.client.module.ModuleAnnotation;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.network.play.server.SPacketHeldItemChange;

@ModuleAnnotation(name = "SwapFix", category = Category.PLAYER)
public class SwapFix extends Module {
    @EventTarget
    public void onPacket(EventReceivePacket eventPacket) {
        if (eventPacket.getPacket() instanceof SPacketHeldItemChange) {
            SPacketHeldItemChange packetHeldItemChange = (SPacketHeldItemChange) eventPacket.getPacket();
            if (packetHeldItemChange.getHeldItemHotbarIndex() != mc.player.inventory.currentItem) {
                mc.player.connection.sendPacket(new CPacketHeldItemChange(mc.player.inventory.currentItem));
                eventPacket.setCancelled(true);
            }
        }
    }
}