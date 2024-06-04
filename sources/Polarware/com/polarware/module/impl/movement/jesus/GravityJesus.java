package com.polarware.module.impl.movement.jesus;

import com.polarware.module.impl.movement.JesusModule;
import com.polarware.event.bus.Listener;
import com.polarware.event.annotations.EventLink;
import com.polarware.event.impl.motion.WaterEvent;
import com.polarware.value.Mode;

/**
 * @author Alan
 * @since 16.05.2022
 */

public class GravityJesus extends Mode<JesusModule> {

    public GravityJesus(String name, JesusModule parent) {
        super(name, parent);
    }

    @EventLink()
    public final Listener<WaterEvent> onWater = event -> {
        event.setWater(event.isWater() && mc.thePlayer.offGroundTicks > 5 && mc.gameSettings.keyBindJump.isKeyDown());
    };
}