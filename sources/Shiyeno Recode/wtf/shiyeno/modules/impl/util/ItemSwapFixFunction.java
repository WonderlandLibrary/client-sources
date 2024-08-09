package wtf.shiyeno.modules.impl.util;

import net.minecraft.network.IPacket;
import net.minecraft.network.play.client.CHeldItemChangePacket;
import net.minecraft.network.play.server.SHeldItemChangePacket;
import wtf.shiyeno.events.Event;
import wtf.shiyeno.events.impl.packet.EventPacket;
import wtf.shiyeno.modules.Function;
import wtf.shiyeno.modules.FunctionAnnotation;
import wtf.shiyeno.modules.Type;

@FunctionAnnotation(
        name = "ItemSwapFix",
        type = Type.Util
)
public class ItemSwapFixFunction extends Function {
    public ItemSwapFixFunction() {
    }

    public void onEvent(Event event) {
        if (event instanceof EventPacket packetEvent) {
            if (packetEvent.isReceivePacket()) {
                IPacket var4 = packetEvent.getPacket();
                if (var4 instanceof SHeldItemChangePacket) {
                    SHeldItemChangePacket packetHeldItemChange = (SHeldItemChangePacket)var4;
                    mc.player.connection.sendPacket(new CHeldItemChangePacket(mc.player.inventory.currentItem % 8 + 1));
                    mc.player.connection.sendPacket(new CHeldItemChangePacket(mc.player.inventory.currentItem));
                    event.setCancel(true);
                }
            }
        }
    }
}