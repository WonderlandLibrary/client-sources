package com.polarware.module.impl.movement.sneak;

import com.polarware.module.impl.movement.SneakModule;
import com.polarware.event.bus.Listener;
import com.polarware.event.annotations.EventLink;
import com.polarware.event.impl.motion.PreMotionEvent;
import com.polarware.value.Mode;
import org.lwjgl.input.Keyboard;

/**
 * @author Auth
 * @since 25/06/2022
 */

public class HoldSneak extends Mode<SneakModule> {

    public HoldSneak(String name, SneakModule parent) {
        super(name, parent);
    }

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {
        mc.gameSettings.keyBindSneak.setPressed(true);
    };

    @Override
    public void onDisable() {
        mc.gameSettings.keyBindSneak.setPressed(Keyboard.isKeyDown(mc.gameSettings.keyBindSneak.getKeyCode()));
    }
}