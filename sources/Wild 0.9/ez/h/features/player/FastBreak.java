package ez.h.features.player;

import ez.h.event.events.*;
import ez.h.event.*;
import ez.h.features.*;

public class FastBreak extends Feature
{
    @Override
    public void onDisable() {
        FastBreak.mc.c.g = 5;
    }
    
    @EventTarget
    public void onMotion(final EventMotion eventMotion) {
        FastBreak.mc.c.g = 0;
    }
    
    public FastBreak() {
        super("FastBreak", "\u0423\u0431\u0438\u0440\u0430\u0435\u0442 \u0437\u0430\u0434\u0435\u0440\u0436\u043a\u0443 \u043c\u0435\u0436\u0434\u0443 \u043a\u043e\u043f\u0430\u043d\u0438\u0435\u043c \u0431\u043b\u043e\u043a\u043e\u0432.", Category.PLAYER);
    }
}
