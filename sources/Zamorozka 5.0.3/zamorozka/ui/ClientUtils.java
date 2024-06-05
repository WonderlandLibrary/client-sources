package zamorozka.ui;

import net.minecraft.client.Minecraft;
import net.minecraft.util.MovementInput;
import net.minecraft.util.math.MathHelper;
import zamorozka.event.events.EventMove;

import java.math.BigDecimal;

public class ClientUtils {

    public static boolean jump;

    public static Minecraft mc = Minecraft.getMinecraft();

    public static void setSpeed(double speed) {
        if (Minecraft.player.isMoving()) {
            Minecraft.player.motionX = -MathHelper.sin(getDirection()) * speed;
            Minecraft.player.motionZ = MathHelper.cos(getDirection()) * speed;
        }
    }

    public static boolean isMoving() {
        return !(Minecraft.player.moveForward == 0.0F && Minecraft.player.moveStrafing == 0.0F);
    }

    public static float yaw() {
        return Minecraft.player.rotationYaw;
    }

    public static MovementInput movementInput() {
        return Minecraft.player.movementInput;
    }


    public static BigDecimal round(final float f, int times) {
        BigDecimal bd = new BigDecimal(Float.toString(f));
        bd = bd.setScale(times, 4);
        return bd;
    }

    public static void setMoveSpeed(EventMove event, double speed) {
        movementInput();
        double forward = MovementInput.moveForward;
        movementInput();
        double strafe = MovementInput.moveStrafe;
        float yaw = yaw();
        if (forward == 0.0D && strafe == 0.0D) {
            event.setX(0.0D);
            event.setZ(0.0D);
        } else {
            if (forward != 0.0D) {
                if (strafe > 0.0D) {
                    yaw += (float) (forward > 0.0D ? -45 : 45);
                } else if (strafe < 0.0D) {
                    yaw += (float) (forward > 0.0D ? 45 : -45);
                }

                strafe = 0.0D;
                if (forward > 0.0D) {
                    forward = 1.0D;
                } else if (forward < 0.0D) {
                    forward = -1.0D;
                }
            }

            event.setX(forward * speed * Math.cos(Math.toRadians(yaw + 90.0F)) + strafe * speed * Math.sin(Math.toRadians(yaw + 90.0F)));
            event.setZ(forward * speed * Math.sin(Math.toRadians(yaw + 90.0F)) - strafe * speed * Math.cos(Math.toRadians(yaw + 90.0F)));
        }

    }

    public static float getDirection() {
        float yaw = Minecraft.player.rotationYawHead;
        float forward = Minecraft.player.moveForward;
        float strafe = Minecraft.player.moveStrafing;
        //yaw += ((forward < 0.0F) ? '96'  :  false );
        if (strafe < 0.0F)
            yaw += ((forward == 0.0F) ? 90 : ((forward < 0.0F) ? -45 : 45));
        if (strafe > 0.0F)
            yaw -= ((forward == 0.0F) ? 90 : ((forward < 0.0F) ? -45 : 45));
        return yaw * 0.017453292F;
    }

    public static void setSpeed2(double speed) {
        Minecraft mc = Minecraft.getMinecraft();
        MovementInput movementInput = Minecraft.player.movementInput;
        float forward = MovementInput.moveForward;
        float strafe = MovementInput.moveStrafe;
        float yaw = Minecraft.player.rotationYaw;
        if (forward == 0.0f && strafe == 0.0f) {
            Minecraft.player.motionX = 0.0;
            Minecraft.player.motionZ = 0.0;
        } else if (forward != 0.0f) {
            if (strafe >= 1.0f) {
                yaw += (float) (forward > 0.0f ? -45 : 45);
                strafe = 0.0f;
            } else if (strafe <= -1.0f) {
                yaw += (float) (forward > 0.0f ? 45 : -45);
                strafe = 0.0f;
            }
            if (forward > 0.0f) {
                forward = 1.0f;
            } else if (forward < 0.0f) {
                forward = -1.0f;
            }
        }
        double mx = Math.cos(Math.toRadians(yaw + 90.0f));
        double mz = Math.sin(Math.toRadians(yaw + 90.0f));
        double motionX = (double) forward * speed * mx + (double) strafe * speed * mz;
        double motionZ = (double) forward * speed * mz - (double) strafe * speed * mx;
        Minecraft.player.motionX = (double) forward * speed * mx + (double) strafe * speed * mz;
        Minecraft.player.motionZ = (double) forward * speed * mz - (double) strafe * speed * mz;
    }
}