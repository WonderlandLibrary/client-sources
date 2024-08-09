package ez.h.features.player;

import ez.h.features.*;
import ez.h.event.events.*;
import java.util.*;
import ez.h.event.*;

public class NoBadEffects extends Feature
{
    public NoBadEffects() {
        super("NoBadEffects", "\u0423\u0431\u0438\u0440\u0430\u0435\u0442 \u043d\u0435\u0433\u0430\u0442\u0438\u0432\u043d\u044b\u0435 \u044d\u0444\u0444\u0435\u043a\u0442\u044b.", Category.PLAYER);
    }
    
    @EventTarget
    public void onMotion(final EventMotion eventMotion) {
        for (final va va : NoBadEffects.mc.h.ca()) {
            if (va == null) {
                continue;
            }
            if (va.a().i()) {
                continue;
            }
            NoBadEffects.mc.h.c(va.a());
        }
    }
}
