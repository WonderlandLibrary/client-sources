package com.polarware.module.impl.player.antivoid;

import com.polarware.component.impl.player.FallDistanceComponent;
import com.polarware.module.impl.player.AntiVoidModule;
import com.polarware.event.bus.Listener;
import com.polarware.event.annotations.EventLink;
import com.polarware.event.impl.motion.PreMotionEvent;
import com.polarware.util.player.PlayerUtil;
import com.polarware.value.Mode;
import com.polarware.value.impl.NumberValue;

public class CollisionAntiVoid extends Mode<AntiVoidModule> {

    private final NumberValue distance = new NumberValue("Distance", this, 5, 0, 10, 1);

    public CollisionAntiVoid(String name, AntiVoidModule parent) {
        super(name, parent);
    }

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {

        if (FallDistanceComponent.distance > distance.getValue().intValue() && !PlayerUtil.isBlockUnder() && mc.thePlayer.posY + mc.thePlayer.motionY < Math.floor(mc.thePlayer.posY)) {
            mc.thePlayer.motionY = Math.floor(mc.thePlayer.posY) - mc.thePlayer.posY;
            if (mc.thePlayer.motionY == 0) {
                mc.thePlayer.onGround = true;
                event.setOnGround(true);
            }
        }
    };
}