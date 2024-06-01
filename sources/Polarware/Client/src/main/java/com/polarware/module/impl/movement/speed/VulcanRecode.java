package com.polarware.module.impl.movement.speed;

import com.polarware.event.annotations.EventLink;
import com.polarware.event.bus.Listener;
import com.polarware.event.impl.motion.PreMotionEvent;
import com.polarware.event.impl.motion.StrafeEvent;
import com.polarware.module.impl.movement.SpeedModule;
import com.polarware.util.player.MoveUtil;
import com.polarware.value.Mode;
import net.minecraft.util.MathHelper;
import org.lwjgl.input.Keyboard;

import java.util.Random;

/**
 * @author Nyghtfulll
 * @since 1/8/2024
 */

public class VulcanRecode extends Mode<SpeedModule> {

    public VulcanRecode(String name, SpeedModule parent) {
        super(name, parent);
    }

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {

        mc.thePlayer.movementInput.sneak = mc.thePlayer.sendQueue.doneLoadingTerrain;
    };

    @EventLink()
    public final Listener<StrafeEvent> onStrafe = event -> {
       // mc.gameSettings.keyBindSneak.setPressed(true);
        if (MoveUtil.isMoving() && mc.thePlayer.onGround) {
            mc.thePlayer.jump();
        }

        event.setSpeed(!mc.thePlayer.onGround ? 0.25f : 0.6);
    };

    @Override
    public void onDisable() {
        mc.gameSettings.keyBindSneak.setPressed(Keyboard.isKeyDown(mc.gameSettings.keyBindSneak.getKeyCode()));
    }
}