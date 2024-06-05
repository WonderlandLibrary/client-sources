package koks.utilities;

import net.minecraft.client.Minecraft;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionHelper;
import net.minecraft.util.MathHelper;

/**
 * @author avox | lmao | kroko
 * @created on 02.09.2020 : 21:16
 */
public class MovementUtil {

    public final Minecraft mc = Minecraft.getMinecraft();

    public double getDirection(float rotationYaw) {
        float left = mc.gameSettings.keyBindLeft.pressed ? mc.gameSettings.keyBindBack.pressed ? 45 : mc.gameSettings.keyBindForward.pressed ? -45 : -90 : 0;
        float right = mc.gameSettings.keyBindRight.pressed ? mc.gameSettings.keyBindBack.pressed ? -45 : mc.gameSettings.keyBindForward.pressed ? 45 : 90 : 0;
        float back = mc.gameSettings.keyBindBack.pressed ? 180 : 0;
        float yaw = back + right + left;
        return rotationYaw + yaw;
    }

    public double getDirectionEvent(float rotationYaw, boolean forward, boolean backwards, boolean leftP, boolean rightP) {
        float left = leftP ? backwards ? 45 : forward ? -45 : -90 : 0;
        float right = rightP ? backwards ? -45 : forward ? 45 : 90 : 0;
        float back = backwards ? 180 : 0;
        float yaw = back + right + left;
        return rotationYaw + yaw;
    }

    public float baseSpeed() {
        float baseMovementSpeed = 0.2875F;
        if (Minecraft.getMinecraft().thePlayer.isPotionActive(Potion.moveSpeed))
            baseMovementSpeed *= 1 + (0.2F * (Minecraft.getMinecraft().thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1F));
        return baseMovementSpeed;
    }

    public void setSpeed(double speed) {
        if (mc.gameSettings.keyBindForward.pressed || mc.gameSettings.keyBindRight.pressed || mc.gameSettings.keyBindLeft.pressed || mc.gameSettings.keyBindBack.pressed) {
            mc.thePlayer.motionX = -Math.sin(Math.toRadians(getDirection(mc.thePlayer.rotationYaw))) * speed;
            mc.thePlayer.motionZ = Math.cos(Math.toRadians(getDirection(mc.thePlayer.rotationYaw))) * speed;
        }
    }

    public void setSpeedEvent(double speed, float yaw, boolean forward, boolean backwards, boolean leftP, boolean rightP) {
        if (forward || backwards || leftP || rightP) {
            mc.thePlayer.motionX = -Math.sin(Math.toRadians(getDirectionEvent(yaw, forward, backwards, leftP, rightP))) * speed;
            mc.thePlayer.motionZ = Math.cos(Math.toRadians(getDirectionEvent(yaw, forward, backwards, leftP, rightP))) * speed;
        }
    }

}
