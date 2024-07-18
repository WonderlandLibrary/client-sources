package com.alan.clients.module.impl.movement.flight;

import com.alan.clients.event.Listener;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.motion.PreMotionEvent;
import com.alan.clients.module.impl.movement.Flight;
import com.alan.clients.value.Mode;

public class WatchdogFlight extends Mode<Flight> {

    @EventLink
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {
        mc.thePlayer.motionY = 0;

        if (mc.thePlayer.isInWater()) {
            mc.timer.timerSpeed = 0.3f;
        } else {
            mc.timer.timerSpeed = 0.45F;
        }

        if (mc.gameSettings.keyBindJump.isKeyDown()) {
            mc.thePlayer.motionY = 0.08;
        }

        if (mc.gameSettings.keyBindSneak.isKeyDown()) {
            mc.thePlayer.motionY = -0.08;
        }
    };

    public WatchdogFlight(String name, Flight parent) {
        super(name, parent);
    }

    @Override
    public void onDisable() {
        mc.timer.timerSpeed = 1F;
    }
}