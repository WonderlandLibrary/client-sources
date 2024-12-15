package com.alan.clients.module.impl.player.scaffold.sprint;

import com.alan.clients.event.Listener;
import com.alan.clients.event.Priorities;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.input.KeyboardInputEvent;
import com.alan.clients.event.impl.motion.JumpEvent;
import com.alan.clients.event.impl.motion.PreMotionEvent;
import com.alan.clients.event.impl.motion.PreUpdateEvent;
import com.alan.clients.event.impl.motion.StrafeEvent;
import com.alan.clients.event.impl.packet.PacketSendEvent;
import com.alan.clients.module.impl.player.Scaffold;
import com.alan.clients.util.player.MoveUtil;
import com.alan.clients.value.Mode;
import org.lwjgl.input.Keyboard;

public class WatchdogLimitSprint extends Mode<Scaffold> {
    private boolean enable;

    public WatchdogLimitSprint(String name, Scaffold parent) {
        super(name, parent);
    }

    @EventLink(value = Priorities.HIGH)
    private final Listener<PreUpdateEvent> preMotionEventListener = event -> {
        if (mc.thePlayer.onGround && !mc.gameSettings.keyBindJump.isKeyDown()) {
            mc.timer.timerSpeed = 1.0029f;
            MoveUtil.preventDiagonalSpeed();
            mc.thePlayer.motionZ *= .998;
            mc.thePlayer.motionX *= .998;
        }

        if (mc.gameSettings.keyBindJump.isPressed() && mc.thePlayer.onGround) {
            MoveUtil.stop();
            enable = false;
        }
    };

    @EventLink(value = Priorities.HIGH)
    private final Listener<PreMotionEvent> onPreMotionEvent = event -> {
        if (mc.thePlayer.onGround) {
            event.setPosY(event.getPosY() + 1E-13);
        }


        if (mc.thePlayer.onGround && !enable && !mc.gameSettings.keyBindJump.isKeyDown()) {
            MoveUtil.stop();
            event.setPosY(event.getPosY() + 1E-13);
            enable = true;
        }
    };


    @Override
    public void onEnable() {
        enable = true;
        if (!mc.gameSettings.keyBindJump.isKeyDown()) {
            MoveUtil.stop();
        }

        if (mc.thePlayer.onGroundTicks > 10 && !mc.gameSettings.keyBindJump.isKeyDown()) {
            mc.thePlayer.motionY = .42;
        }
    }


    @Override
    public void onDisable() {
        MoveUtil.stop();
    }

    @EventLink
    public final Listener<JumpEvent> onJump = event -> {
        event.setJumpMotion(.42F);
    };

    @EventLink(value = Priorities.VERY_HIGH)
    public final Listener<KeyboardInputEvent> onKey = event -> {
        if (event.getGuiScreen() != null || event.isCancelled()) return;

        if (event.getKeyCode() == Keyboard.KEY_SPACE) {
            event.setCancelled();
        }
    };
}