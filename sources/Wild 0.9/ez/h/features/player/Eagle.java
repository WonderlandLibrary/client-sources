package ez.h.features.player;

import ez.h.event.events.*;
import ez.h.event.*;
import ez.h.features.*;

public class Eagle extends Feature
{
    @Override
    public void onDisable() {
        Eagle.mc.t.Y.i = false;
        super.onDisable();
    }
    
    @EventTarget
    public void onMotion(final EventMotion eventMotion) {
        Eagle.mc.t.Y.i = (Eagle.mc.f.o(new et(eventMotion.x, eventMotion.y - 0.5, eventMotion.z)).u() == aox.a);
    }
    
    public Eagle() {
        super("Eagle", "\u0410\u0432\u0442\u043e\u043c\u0430\u0442\u0438\u0447\u0435\u0441\u043a\u0438\u0439 \u043d\u0438\u043d\u0434\u0437\u044f-\u0431\u0440\u0438\u0434\u0436\u0438\u043d\u0433.", Category.PLAYER);
    }
}
