package com.polarware.module.impl.player.antivoid;

import com.polarware.module.impl.player.AntiVoidModule;
import com.polarware.event.bus.Listener;
import com.polarware.event.annotations.EventLink;
import com.polarware.event.impl.motion.PreMotionEvent;
import com.polarware.event.impl.other.TeleportEvent;
import com.polarware.util.player.MoveUtil;
import com.polarware.value.Mode;
import com.polarware.value.impl.NumberValue;

/**
 * @author Strikeless
 * @since 18.03.2022
 */
public class VulcanAntiVoid extends Mode<AntiVoidModule> {

    private final NumberValue distance = new NumberValue("Distance", this, 5, 0, 10, 1);

    private boolean teleported;

    public VulcanAntiVoid(String name, AntiVoidModule parent) {
        super(name, parent);
    }

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {

        if (mc.thePlayer.fallDistance > distance.getValue().floatValue()) {
            event.setPosY(event.getPosY() - event.getPosY() % 0.015625);
            event.setOnGround(true);

            mc.thePlayer.motionY = -0.08D;

            MoveUtil.stop();
        }

        if (teleported) {
            MoveUtil.stop();
            teleported = false;
        }
    };

    @EventLink()
    public final Listener<TeleportEvent> onTeleport = event -> {

        if (mc.thePlayer.fallDistance > distance.getValue().floatValue()) {
            teleported = true;
        }
    };
}
