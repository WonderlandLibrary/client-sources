/*
 * This file is part of Clientbase - https://github.com/DietrichPaul/Clientbase
 * by DietrichPaul, FlorianMichael and contributors
 *
 * To the extent possible under law, the person who associated CC0 with
 * Clientbase has waived all copyright and related or neighboring rights
 * to Clientbase.
 *
 * You should have received a copy of the CC0 legalcode along with this
 * work.  If not, see <http://creativecommons.org/publicdomain/zero/1.0/>.
 */
package de.dietrichpaul.clientbase.feature.engine.rotation.strafe.impl;

import de.dietrichpaul.clientbase.event.StrafeInputListener;
import de.dietrichpaul.clientbase.feature.engine.rotation.strafe.CorrectMovement;
import net.minecraft.util.math.MathHelper;

public class ErrorCorrectedFix extends CorrectMovement {

    private final double epsilon;
    private double partialAngles;

    public ErrorCorrectedFix(double epsilon) {
        this.epsilon = epsilon;
    }

    public void reset() {
        partialAngles = 0;
    }

    @Override
    public void edit(float serverYaw, StrafeInputListener.StrafeInputEvent event) {
        if (event.moveForward != 0 || event.moveSideways != 0) {
            double angle = mc.player.getYaw() + Math.toDegrees(Math.atan2(-event.moveSideways, event.moveForward));
            event.moveForward = (int) Math.round(Math.cos(Math.toRadians(partialAngles + angle - serverYaw)));
            event.moveSideways = (int) Math.round(-Math.sin(Math.toRadians(partialAngles + angle - serverYaw)));
            double serverAngle = serverYaw + Math.toDegrees(Math.atan2(-event.moveSideways, event.moveForward));

            partialAngles += MathHelper.wrapDegrees(angle - serverAngle) * epsilon;
        }
    }
}
