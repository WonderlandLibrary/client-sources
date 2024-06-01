package com.polarware.module.impl.movement.flight.deprecated;

import com.polarware.module.impl.movement.FlightModule;
import com.polarware.event.bus.Listener;
import com.polarware.event.annotations.EventLink;
import com.polarware.event.impl.motion.StrafeEvent;
import com.polarware.util.player.DamageUtil;
import com.polarware.util.player.MoveUtil;
import com.polarware.value.Mode;

public class ACRFlight extends Mode<FlightModule> {

    private int offGroundTicks;

    public ACRFlight(String name, FlightModule parent) {
        super(name, parent);
    }

    @Override
    public void onEnable() {
        DamageUtil.damagePlayer(DamageUtil.DamageType.POSITION, 3.42F, 1, false, false);
    }

    @EventLink()
    public final Listener<StrafeEvent> onStrafe = event -> {
        MoveUtil.strafe(0.06);
        if (mc.thePlayer.ticksExisted % 3 == 0) {
            mc.thePlayer.motionY = 0.40444491418477213;
            offGroundTicks = 0;
        }

        if (offGroundTicks == 1) {
            MoveUtil.strafe(0.36);
            mc.thePlayer.motionY = 0.33319999363422365;
        }
    };

}
