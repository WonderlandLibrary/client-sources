package com.alan.clients.module.impl.player.scaffold.tower;

import com.alan.clients.event.Listener;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.motion.PreMotionEvent;
import com.alan.clients.module.impl.player.Scaffold;
import com.alan.clients.util.player.PlayerUtil;
import com.alan.clients.value.Mode;

public class VanillaTower extends Mode<Scaffold> {

    public VanillaTower(String name, Scaffold parent) {
        super(name, parent);
    }

    @EventLink
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {
        if (mc.gameSettings.keyBindJump.isKeyDown() && PlayerUtil.blockNear(1)) {
            mc.thePlayer.motionY = 0.42F;
        }
    };
}
