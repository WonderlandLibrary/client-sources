package wtf.shiyeno.modules.impl.util;

import net.minecraft.network.play.client.CConfirmTeleportPacket;
import wtf.shiyeno.events.Event;
import wtf.shiyeno.events.impl.packet.EventPacket;
import wtf.shiyeno.modules.Function;
import wtf.shiyeno.modules.FunctionAnnotation;
import wtf.shiyeno.modules.Type;
import wtf.shiyeno.util.misc.TimerUtil;

@FunctionAnnotation(
        name = "GodMode",
        type = Type.Util
)
public class GodMode extends Function {
    private final TimerUtil timerUtil = new TimerUtil();

    public GodMode() {
    }

    public void onEvent(Event event) {
        if (event instanceof EventPacket e) {
            if (!((EventPacket)event).isSendPacket() || mc.player == null || mc.world == null) {
                return;
            }

            if (e.getPacket() instanceof CConfirmTeleportPacket) {
                e.setCancel(true);
            }
        }
    }

    public void onDisable() {
        super.onDisable();
    }
}