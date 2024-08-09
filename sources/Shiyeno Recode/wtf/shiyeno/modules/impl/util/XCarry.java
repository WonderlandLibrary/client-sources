package wtf.shiyeno.modules.impl.util;

import net.minecraft.network.play.client.CCloseWindowPacket;
import wtf.shiyeno.events.Event;
import wtf.shiyeno.events.impl.packet.EventPacket;
import wtf.shiyeno.modules.Function;
import wtf.shiyeno.modules.FunctionAnnotation;
import wtf.shiyeno.modules.Type;

@FunctionAnnotation(
        name = "XCarry",
        type = Type.Util
)
public class XCarry extends Function {
    public XCarry() {
    }

    public void onEvent(Event event) {
        if (event instanceof EventPacket && ((EventPacket)event).getPacket() instanceof CCloseWindowPacket) {
            event.setCancel(true);
        }
    }
}