package com.polarware.module.impl.player.scaffold.tower;

import com.polarware.module.impl.player.ScaffoldModule;
import com.polarware.event.bus.Listener;
import com.polarware.event.annotations.EventLink;
import com.polarware.event.impl.motion.StrafeEvent;
import com.polarware.value.Mode;

public class LegitTower extends Mode<ScaffoldModule> {
    // Bypasses jump delay, holding down space is slower than this
    public LegitTower(String name, ScaffoldModule parent) {
        super(name, parent);
    }

    @EventLink()
    public final Listener<StrafeEvent> onStrafe = event -> {
        if (!mc.gameSettings.keyBindJump.isKeyDown()) {
            return;
        }

        if (mc.thePlayer.onGround) {
            mc.thePlayer.jump();
        }
    };
}
