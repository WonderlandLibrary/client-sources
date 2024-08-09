package ez.h.features.player;

import ez.h.features.*;
import ez.h.event.events.*;
import ez.h.utils.*;
import ez.h.event.*;

public class AutoParkour extends Feature
{
    public AutoParkour() {
        super("AutoParkour", "\u0410\u0432\u0442\u043e\u043c\u0430\u0442\u0438\u0447\u0435\u0441\u043a\u0438 \u043f\u0440\u044b\u0433\u0430\u0435\u0442 \u043a\u043e\u0433\u0434\u0430 \u0432\u044b \u043d\u0430\u0445\u043e\u0434\u0438\u0442\u0435\u0441\u044c \u043d\u0430 \u043a\u0440\u0430\u044e \u0431\u043b\u043e\u043a\u0430.", Category.PLAYER);
    }
    
    @EventTarget
    public void onMotion(final EventMotion eventMotion) {
        if (Utils.isBlockEdge((vp)AutoParkour.mc.h)) {
            AutoParkour.mc.h.cu();
        }
    }
}
