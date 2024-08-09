package ez.h.features.another;

import ez.h.features.*;
import ez.h.event.*;
import ez.h.event.events.*;

public class XCarry extends Feature
{
    boolean inInventory;
    
    public XCarry() {
        super("XCarry", "\u041f\u043e\u0437\u0432\u043e\u043b\u044f\u0435\u0442 \u0441\u043a\u043b\u0430\u0434\u044b\u0432\u0430\u0442\u044c \u0432\u0435\u0449\u0438 \u0432 \u0441\u043b\u043e\u0442\u044b \u043a\u0440\u0430\u0444\u0442\u0430", Category.ANOTHER);
    }
    
    @EventTarget
    public void onPacketSent(final EventPacketSend eventPacketSend) {
        if (eventPacketSend.getPacket() instanceof lf) {
            eventPacketSend.setCancelled(true);
        }
    }
    
    @EventTarget
    public void onMotionEvent(final EventMotion eventMotion) {
        if (XCarry.mc.m instanceof bmx) {
            XCarry.mc.c.e();
        }
    }
}
