package ez.h.features.player;

import ez.h.features.*;
import ez.h.event.events.*;
import ez.h.event.*;

public class NoJumpDelay extends Feature
{
    public NoJumpDelay() {
        super("NoJumpDelay", "\u0423\u0431\u0438\u0440\u0430\u0435\u0442 \u0437\u0430\u0434\u0435\u0440\u0436\u043a\u0443 \u043c\u0435\u0436\u0434\u0443 \u043f\u0440\u044b\u0436\u043a\u0430\u043c\u0438.", Category.PLAYER);
    }
    
    @EventTarget
    public void onMotion(final EventMotion eventMotion) {
        NoJumpDelay.mc.h.bD = 0;
    }
}
