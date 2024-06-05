/*
 * Decompiled with CFR 0.152.
 */
package drunkclient.beta.UTILS.world;

import drunkclient.beta.UTILS.world.MovementUtil;
import net.minecraft.client.Minecraft;

public class FrictionUtil {
    public static Minecraft mc = Minecraft.getMinecraft();

    public static float applyFriction(float speed) {
        float percent = 0.0f;
        if (MovementUtil.isMoving()) {
            if (Minecraft.thePlayer.onGround) {
                percent = 99.5f;
            }
        }
        if (MovementUtil.isMoving()) {
            if (!Minecraft.thePlayer.onGround) {
                percent = 98.0f;
            }
        }
        if (!MovementUtil.isMoving()) return speed / 100.0f * percent;
        if (!Minecraft.thePlayer.isInWater()) return speed / 100.0f * percent;
        percent = 80.3f;
        return speed / 100.0f * percent;
    }

    public static float applyCustomFriction(float speed, float friction) {
        float percent = friction;
        return speed / 100.0f * percent;
    }
}

