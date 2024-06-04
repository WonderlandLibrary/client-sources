package com.polarware.module.impl.movement.flight;

import com.polarware.Client;
import com.polarware.module.impl.movement.FlightModule;
import com.polarware.event.bus.Listener;
import com.polarware.event.annotations.EventLink;
import com.polarware.event.impl.input.MoveInputEvent;
import com.polarware.event.impl.motion.PreMotionEvent;
import com.polarware.event.impl.motion.StrafeEvent;
import com.polarware.util.interfaces.InstanceAccess;
import com.polarware.util.player.MoveUtil;
import com.polarware.value.Mode;
import com.polarware.value.impl.NumberValue;

/**
 * @author Auth
 * @since 18/11/2021
 */

public class CubeCraftFlight extends Mode<FlightModule>  {

    private final NumberValue speed = new NumberValue("Speed", this, 1, 0.1, 9.5, 0.1);

    public CubeCraftFlight(String name, FlightModule parent) {
        super(name, parent);
    }

    @EventLink()
    public final Listener<StrafeEvent> onStrafe = event -> {

        final float speed = this.speed.getValue().floatValue();

        event.setSpeed(speed);
    };

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {

        final float speed = this.speed.getValue().floatValue();

        InstanceAccess.mc.thePlayer.motionY = -1E-10D
                + (InstanceAccess.mc.gameSettings.keyBindJump.isKeyDown() ? speed : 0.0D)
                - (InstanceAccess.mc.gameSettings.keyBindSneak.isKeyDown() ? speed : 0.0D);


        boolean playerNearby = !Client.INSTANCE.getTargetComponent().getTargets(12).isEmpty() ||
                InstanceAccess.mc.currentScreen != null;

        if (InstanceAccess.mc.thePlayer.getDistance(InstanceAccess.mc.thePlayer.lastReportedPosX, InstanceAccess.mc.thePlayer.lastReportedPosY, InstanceAccess.mc.thePlayer.lastReportedPosZ)
                <= (playerNearby ? 5 : 10) - speed - 0.15 && InstanceAccess.mc.thePlayer.swingProgressInt != 3) {
            event.setCancelled(true);
        }
    };


    @EventLink()
    public final Listener<MoveInputEvent> onMove = event -> {
        event.setSneak(false);
    };

    @Override
    public void onDisable() {
        MoveUtil.stop();
    }
}