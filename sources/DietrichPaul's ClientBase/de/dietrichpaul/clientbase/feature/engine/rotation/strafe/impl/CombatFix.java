package de.dietrichpaul.clientbase.feature.engine.rotation.strafe.impl;

import de.dietrichpaul.clientbase.event.StrafeInputListener;
import de.dietrichpaul.clientbase.feature.engine.rotation.strafe.CorrectMovement;

public class CombatFix extends CorrectMovement {

    @Override
    public void edit(float serverYaw, StrafeInputListener.StrafeInputEvent event) {
        if (event.moveForward != 0 || event.moveSideways != 0) {
            double angle = mc.player.getYaw() + Math.toDegrees(Math.atan2(-event.moveSideways, event.moveForward));
            if (event.moveForward > 0 && !mc.player.horizontalCollision && mc.options.sprintKey.isPressed()) {
                event.moveForward = 1;
            } else {
                event.moveForward = (int) Math.round(Math.cos(Math.toRadians(angle - serverYaw)));
            }
            event.moveSideways = (int) Math.round(-Math.sin(Math.toRadians(angle - serverYaw)));
        }
    }
}