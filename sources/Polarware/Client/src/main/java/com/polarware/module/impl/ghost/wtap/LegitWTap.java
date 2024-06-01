package com.polarware.module.impl.ghost.wtap;


import com.polarware.module.impl.ghost.WTapModule;
import com.polarware.event.bus.Listener;
import com.polarware.event.annotations.EventLink;
import com.polarware.event.impl.motion.PreMotionEvent;
import com.polarware.event.impl.other.AttackEvent;
import com.polarware.value.Mode;

public class LegitWTap extends Mode<WTapModule> {

    private boolean unsprint, wTap;

    public LegitWTap(String name, WTapModule parent) {
        super(name, parent);
    }


    @EventLink()
    public final Listener<AttackEvent> onAttack = event -> {
        wTap = Math.random() * 100 < getParent().chance.getValue().doubleValue();

        if (!wTap) return;

        if (mc.thePlayer.isSprinting() || mc.gameSettings.keyBindSprint.isKeyDown()) {
            mc.gameSettings.keyBindSprint.setPressed(true);
            unsprint = true;
        }
    };

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {
        if (!wTap) return;

        if (unsprint) {
            mc.gameSettings.keyBindSprint.setPressed(false);
            unsprint = false;
        }
    };
}