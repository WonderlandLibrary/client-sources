package com.polarware.module.impl.movement.flight;

import com.polarware.event.annotations.EventLink;
import com.polarware.event.bus.Listener;
import com.polarware.event.impl.motion.PreMotionEvent;
import com.polarware.module.impl.movement.FlightModule;
import com.polarware.util.chat.ChatUtil;
import com.polarware.util.player.MoveUtil;
import com.polarware.value.Mode;

public class Polar extends Mode<FlightModule> {

    public Polar(String name, FlightModule parent) {
        super(name, parent);
    }

    @Override
    public void onEnable() {
        ChatUtil.display("Smash");
    }
    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {
        mc.gameSettings.keyBindSprint.setPressed(true);
      //  mc.thePlayer.motionY = 0.50f;
        if(mc.thePlayer.onGround) {
            MoveUtil.strafe(5);
            mc.thePlayer.motionY = 0.70f;
        }
        mc.timer.timerSpeed = 0.2f;
    };
}

