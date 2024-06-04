package com.polarware.module.impl.movement.longjump;

import com.polarware.module.impl.movement.LongJumpModule;
import com.polarware.event.bus.Listener;
import com.polarware.event.annotations.EventLink;
import com.polarware.event.impl.motion.StrafeEvent;
import com.polarware.util.interfaces.InstanceAccess;
import com.polarware.util.player.MoveUtil;
import com.polarware.value.Mode;
import com.polarware.value.impl.NumberValue;

/**
 * @author Auth
 * @since 3/02/2022
 */
public class VanillaLongJump extends Mode<LongJumpModule> {

    private final NumberValue height = new NumberValue("Height", this, 0.5, 0.1, 1, 0.01);
    private final NumberValue speed = new NumberValue("Speed", this, 1, 0.1, 9.5, 0.1);

    public VanillaLongJump(String name, LongJumpModule parent) {
        super(name, parent);
    }

    @EventLink()
    public final Listener<StrafeEvent> onStrafe = event -> {

        if (InstanceAccess.mc.thePlayer.onGround) {
            InstanceAccess.mc.thePlayer.motionY = height.getValue().floatValue();
        }

        event.setSpeed(speed.getValue().floatValue());
    };

    @Override
    public void onDisable() {
        MoveUtil.stop();
    }
}