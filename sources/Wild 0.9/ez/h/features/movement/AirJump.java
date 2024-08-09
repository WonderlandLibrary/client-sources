package ez.h.features.movement;

import ez.h.event.events.*;
import ez.h.event.*;
import ez.h.features.*;

public class AirJump extends Feature
{
    @EventTarget
    public void onMotion(final EventMotion eventMotion) {
        if (AirJump.mc.f.o(AirJump.mc.h.c().a(0.0, -1.0, 0.0)).u() != aox.a) {
            AirJump.mc.h.z = true;
            eventMotion.onGround = true;
            AirJump.mc.h.fixedJump();
        }
    }
    
    public AirJump() {
        super("AirJump", "\u041f\u043e\u0437\u0432\u043e\u043b\u044f\u0435\u0442 \u0432\u0430\u043c \u0434\u0435\u043b\u0430\u0442\u044c \u043f\u0440\u044b\u0436\u043e\u043a \u0432 \u0432\u043e\u0437\u0434\u0443\u0445\u0435.", Category.MOVEMENT);
    }
}
