package com.alan.clients.module.impl.movement;

import com.alan.clients.component.impl.player.RotationComponent;
import com.alan.clients.component.impl.player.rotationcomponent.MovementFix;
import com.alan.clients.event.Listener;
import com.alan.clients.event.Priorities;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.input.MoveInputEvent;
import com.alan.clients.event.impl.motion.PreMotionEvent;
import com.alan.clients.event.impl.motion.PreUpdateEvent;
import com.alan.clients.event.impl.motion.StrafeEvent;
import com.alan.clients.module.Module;
import com.alan.clients.module.api.Category;
import com.alan.clients.module.api.ModuleInfo;
import com.alan.clients.util.math.MathUtil;
import com.alan.clients.util.player.MoveUtil;
import com.alan.clients.util.vector.Vector2f;
import com.alan.clients.value.impl.BooleanValue;
import net.minecraft.potion.Potion;
import net.minecraft.util.MathHelper;

@ModuleInfo(aliases = {"module.movement.sprint.name"}, description = "module.movement.sprint.description", category = Category.MOVEMENT)
public class Sprint extends Module {
    private float forward, strafe;
    private final BooleanValue legit = new BooleanValue("Legit", this, true);
    private final BooleanValue watchdogprediction = new BooleanValue("Watchdog Prediction", this, false);
    @EventLink(value = Priorities.LOW)
    public final Listener<StrafeEvent> onStrafe = event -> {
        mc.gameSettings.keyBindSprint.setPressed(true);

        if (!legit.getValue() && !watchdogprediction.getValue()) {
            mc.thePlayer.omniSprint = MoveUtil.isMoving();

         //   MoveUtil.preventDiagonalSpeed();

            mc.thePlayer.setSprinting(!legit.getValue() && MoveUtil.isMoving() && !mc.thePlayer.isCollidedHorizontally &&
                    !mc.thePlayer.isSneaking() && !mc.thePlayer.isUsingItem());
        }





    };

    @EventLink(value = Priorities.LOW)
    Listener<PreMotionEvent> onPremotion = event -> {
        if (watchdogprediction.getValue() && mc.thePlayer.onGround) {
        double attempt_angle = MathHelper.wrapAngleTo180_double(Math.toDegrees(MoveUtil.direction()));
        double movement_angle = MathHelper.wrapAngleTo180_double(Math.toDegrees(Math.atan2(mc.thePlayer.motionZ, mc.thePlayer.motionX)) - 90);
        if (MathUtil.wrappedDifference(attempt_angle, movement_angle) > 90) {
            mc.thePlayer.omniSprint = true;

        }
    }

};

    @EventLink(value = Priorities.HIGH)
    Listener<MoveInputEvent> moveInput = event -> {
        forward = event.getForward();
        strafe = event.getStrafe();
    };
    @EventLink(value = Priorities.LOW)
    Listener<PreUpdateEvent> onPreUpdate = event -> {
        if (mc.thePlayer == null || mc.theWorld == null) {
            return;
        }
if(!(mc.thePlayer.onGroundTicks>1)){
   // RotationComponent.setRotations(new Vector2f((float) Math.toDegrees(MoveUtil.direction(forward, strafe)), mc.thePlayer.rotationPitch),
 //           10, MovementFix.NORMAL);
}

    };


    @Override
    public void onDisable() {
        mc.thePlayer.setSprinting(mc.gameSettings.keyBindSprint.isKeyDown());
        mc.thePlayer.omniSprint = false;
    }
}