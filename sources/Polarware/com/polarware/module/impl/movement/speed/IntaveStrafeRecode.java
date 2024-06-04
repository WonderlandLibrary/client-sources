package com.polarware.module.impl.movement.speed;

import com.polarware.event.annotations.EventLink;
import com.polarware.event.bus.Listener;
import com.polarware.event.impl.motion.PreMotionEvent;
import com.polarware.event.impl.motion.StrafeEvent;
import com.polarware.module.impl.movement.SpeedModule;
import com.polarware.util.player.MoveUtil;
import com.polarware.value.Mode;
import com.polarware.value.impl.NumberValue;
import org.lwjgl.input.Keyboard;

/**
 * @author Nyghtfulll
 * @since 12/19/2023
 */

public class IntaveStrafeRecode extends Mode<SpeedModule> {

    public IntaveStrafeRecode(String name, SpeedModule parent) {
        super(name, parent);
    }

    @EventLink()
    public final Listener<StrafeEvent> onStrafe = event -> {
        mc.gameSettings.keyBindSneak.setPressed(true);
        if (MoveUtil.isMoving() && mc.thePlayer.onGround) {
            mc.thePlayer.jump();
        }

        event.setSpeed(0.4);
    };

    @Override
    public void onDisable() {
        mc.gameSettings.keyBindSneak.setPressed(Keyboard.isKeyDown(mc.gameSettings.keyBindSneak.getKeyCode()));
    }
}