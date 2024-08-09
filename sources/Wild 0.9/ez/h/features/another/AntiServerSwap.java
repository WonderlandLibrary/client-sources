package ez.h.features.another;

import ez.h.features.*;
import ez.h.event.events.*;
import ez.h.utils.*;
import ez.h.event.*;

public class AntiServerSwap extends Feature
{
    int prevItem;
    
    public AntiServerSwap() {
        super("AntiServerSwap", "\u0421\u0435\u0440\u0432\u0435\u0440 \u043d\u0435 \u0441\u043c\u043e\u0436\u0435\u0442 \u043c\u0435\u043d\u044f\u0442\u044c \u0432\u0430\u0448 \u0441\u043b\u043e\u0442.", Category.ANOTHER);
        this.prevItem = -1;
    }
    
    @EventTarget
    public void onPacketReceive(final EventPacketReceive eventPacketReceive) {
        if (AntiServerSwap.mc.h == null) {
            return;
        }
        if (eventPacketReceive.getPacket() instanceof iu) {
            this.prevItem = AntiServerSwap.mc.h.bv.d;
            Debug.executeLater(100L, () -> {
                if (this.prevItem != -1) {
                    AntiServerSwap.mc.h.bv.d = this.prevItem;
                }
            });
        }
    }
}
