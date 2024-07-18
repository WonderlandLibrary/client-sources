package com.alan.clients.module.impl.movement.longjump;

import com.alan.clients.event.Listener;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.motion.PreMotionEvent;
import com.alan.clients.module.impl.movement.LongJump;
import com.alan.clients.value.Mode;

public final class Vulcan2LongJump extends Mode<LongJump> {
    public Vulcan2LongJump(String name, LongJump parent) {
        super(name, parent);
    }
    private int ticks;

    @Override
    public void onEnable() {
        ticks = 0;

    }

    @EventLink
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {
        ticks++;
        if (ticks == 1) {
            mc.thePlayer.motionY = 0;
            mc.thePlayer.onGround = true;
            mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY + 9.9, mc.thePlayer.posZ);
        }

        if (ticks > 0 && !(ticks >3)) {
            mc.thePlayer.motionY = 0;
            mc.thePlayer.onGround = true;

        }

        if (ticks > 3 && ticks % 2 == 0 & !mc.thePlayer.onGround) {
            mc.thePlayer.motionY = -0.155;

        } else if (!(ticks % 2 == 0 && !mc.thePlayer.onGround)){
            mc.thePlayer.motionY = -0.098;
        }

    };
}
