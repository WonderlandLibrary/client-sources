package club.strifeclient.event.implementations.player;

import club.strifeclient.event.Event;
import lombok.AllArgsConstructor;
import net.minecraft.client.Minecraft;

@AllArgsConstructor
public class StrafeEvent extends Event {
    private static final Minecraft mc = Minecraft.getMinecraft();
    public float strafe, forward, friction, yaw;

    public void setMotion(double speed) {
        mc.thePlayer.motionX = 0;
        mc.thePlayer.motionZ = 0;
        mc.thePlayer.onGround = false;
        speed *= strafe != 0 && forward != 0 ? 0.91 : 1;
        this.friction = (float) speed;
    }

    /**
     * Sets motion with legitimate strafe & forward components
     * @param friction - The friction
     */
    public void setMotionLegit(double friction) {
        friction = mc.thePlayer.onGround ? (float)friction : friction * 0.43F;
    }

    /**
     * Sets motion with an illegitimate strafe & legitimate forward component
     * @param friction - The friction
     * @param strafeComponent - Strafe component value ranging from 0.0 to 1.0
     */
    public void setMotionPartialStrafe(double friction, float strafeComponent) {
        final float remainder = 1F - strafeComponent;
        if (forward != 0 && strafe != 0)
            friction *= 0.91;
        if (mc.thePlayer.onGround) {
            setMotion(friction);
        } else {
            mc.thePlayer.motionX *= strafeComponent;
            mc.thePlayer.motionZ *= strafeComponent;
            this.friction = (float)friction * remainder;
        }
    }
}
