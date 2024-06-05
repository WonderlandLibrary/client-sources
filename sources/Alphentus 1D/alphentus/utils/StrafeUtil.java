package alphentus.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.util.MovementInput;

/**
 * @author avox | lmao
 * @since on 05.08.2020.
 */
public class StrafeUtil {

    private static Minecraft mc = Minecraft.getMinecraft();

    public static void strafe() {
        double currentSpeed = Math.sqrt(Math.pow(mc.thePlayer.motionX, 2.0D) + Math.pow(mc.thePlayer.motionZ, 2.0D));
        MovementInput movementInput = mc.thePlayer.movementInput;
        float forward = movementInput.moveForward;
        float strafe = movementInput.moveStrafe;
        float yaw = mc.thePlayer.rotationYaw;
        if (forward != 0.0D) {
            if (strafe > 0.0D) {
                yaw += ((forward > 0.0D) ? -45 : 45);
            } else if (strafe < 0.0D) {
                yaw += ((forward > 0.0D) ? 45 : -45);
            }
            strafe = 0.0F;
            if (forward > 0.0F) {
                forward = 1.0F;
            } else if (forward < 0.0F) {
                forward = -1.0F;
            }
        }
        if (strafe > 0.0D) {
            strafe = 1.0F;
        } else if (strafe < 0.0D) {
            strafe = -1.0F;
        }
        double mx = Math.cos(Math.toRadians((yaw + 90.0F)));
        double mz = Math.sin(Math.toRadians((yaw + 90.0F)));

        double ms = currentSpeed;

        mc.thePlayer.motionX = forward * ms * mx + strafe * ms * mz;
        mc.thePlayer.motionZ = forward * ms * mz - strafe * ms * mx;
    }
}