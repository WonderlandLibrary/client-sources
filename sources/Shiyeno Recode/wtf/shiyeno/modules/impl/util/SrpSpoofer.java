package wtf.shiyeno.modules.impl.util;

import net.minecraft.network.play.client.CResourcePackStatusPacket;
import net.minecraft.network.play.client.CResourcePackStatusPacket.Action;
import net.minecraft.network.play.server.SSendResourcePackPacket;
import wtf.shiyeno.events.Event;
import wtf.shiyeno.events.impl.packet.EventPacket;
import wtf.shiyeno.modules.Function;
import wtf.shiyeno.modules.FunctionAnnotation;
import wtf.shiyeno.modules.Type;

@FunctionAnnotation(
        name = "SrpSpoofer",
        type = Type.Util
)
public class SrpSpoofer extends Function {
    public SrpSpoofer() {
    }

    public void onEvent(Event event) {
        EventPacket e;
        if (event instanceof EventPacket && (e = (EventPacket)event).getPacket() instanceof SSendResourcePackPacket) {
            mc.player.connection.sendPacket(new CResourcePackStatusPacket(Action.ACCEPTED));
            mc.player.connection.sendPacket(new CResourcePackStatusPacket(Action.SUCCESSFULLY_LOADED));
            event.setCancel(true);
        }
    }
}