package com.polarware.module.impl.player.scaffold.sprint;

import com.polarware.component.impl.player.RotationComponent;
import com.polarware.module.impl.player.ScaffoldModule;
import com.polarware.event.bus.Listener;
import com.polarware.event.annotations.EventLink;
import com.polarware.event.impl.motion.PreMotionEvent;
import com.polarware.value.Mode;
import net.minecraft.util.MathHelper;

public class LegitSprint extends Mode<ScaffoldModule> {

    public LegitSprint(String name, ScaffoldModule parent) {
        super(name, parent);
    }

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {

        if (Math.abs(MathHelper.wrapAngleTo180_float(mc.thePlayer.rotationYaw) - MathHelper.wrapAngleTo180_float(RotationComponent.rotations.x)) > 90) {
            mc.gameSettings.keyBindSprint.setPressed(false);
            mc.thePlayer.setSprinting(false);
        }
    };
}
