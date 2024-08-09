/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.utils.player;

import mpp.venusfr.events.EventInput;
import mpp.venusfr.events.MovingEvent;
import mpp.venusfr.utils.client.IMinecraft;
import net.minecraft.util.MovementInput;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;

public final class MoveUtils
implements IMinecraft {
    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static boolean isMoving() {
        if (MoveUtils.mc.player.movementInput.moveForward != 0.0f) return true;
        MovementInput cfr_ignored_0 = MoveUtils.mc.player.movementInput;
        if (MovementInput.moveStrafe == 0.0f) return false;
        return true;
    }

    public static void fixMovement(EventInput eventInput, float f) {
        float f2 = eventInput.getForward();
        float f3 = eventInput.getStrafe();
        double d = MathHelper.wrapDegrees(Math.toDegrees(MoveUtils.direction(MoveUtils.mc.player.isElytraFlying() ? f : MoveUtils.mc.player.rotationYaw, f2, f3)));
        if (f2 == 0.0f && f3 == 0.0f) {
            return;
        }
        float f4 = 0.0f;
        float f5 = 0.0f;
        float f6 = Float.MAX_VALUE;
        for (float f7 = -1.0f; f7 <= 1.0f; f7 += 1.0f) {
            for (float f8 = -1.0f; f8 <= 1.0f; f8 += 1.0f) {
                double d2;
                double d3;
                if (f8 == 0.0f && f7 == 0.0f || !((d3 = Math.abs(d - (d2 = MathHelper.wrapDegrees(Math.toDegrees(MoveUtils.direction(f, f7, f8)))))) < (double)f6)) continue;
                f6 = (float)d3;
                f4 = f7;
                f5 = f8;
            }
        }
        eventInput.setForward(f4);
        eventInput.setStrafe(f5);
    }

    public static double direction(float f, double d, double d2) {
        if (d < 0.0) {
            f += 180.0f;
        }
        float f2 = 1.0f;
        if (d < 0.0) {
            f2 = -0.5f;
        } else if (d > 0.0) {
            f2 = 0.5f;
        }
        if (d2 > 0.0) {
            f -= 90.0f * f2;
        }
        if (d2 < 0.0) {
            f += 90.0f * f2;
        }
        return Math.toRadians(f);
    }

    public static double getMotion() {
        return Math.hypot(MoveUtils.mc.player.getMotion().x, MoveUtils.mc.player.getMotion().z);
    }

    public static void setMotion(double d) {
        if (!MoveUtils.isMoving()) {
            return;
        }
        double d2 = MoveUtils.getDirection(true);
        MoveUtils.mc.player.setMotion(-Math.sin(d2) * d, MoveUtils.mc.player.motion.y, Math.cos(d2) * d);
    }

    public static boolean isBlockUnder(float f) {
        if (MoveUtils.mc.player.getPosY() < 0.0) {
            return true;
        }
        AxisAlignedBB axisAlignedBB = MoveUtils.mc.player.getBoundingBox().offset(0.0, -f, 0.0);
        return MoveUtils.mc.world.getCollisionShapes(MoveUtils.mc.player, axisAlignedBB).toList().isEmpty();
    }

    public static double getDirection(boolean bl) {
        float f = MoveUtils.mc.player.rotationYaw;
        if (MoveUtils.mc.player.moveForward < 0.0f) {
            f += 180.0f;
        }
        float f2 = 1.0f;
        if (MoveUtils.mc.player.moveForward < 0.0f) {
            f2 = -0.5f;
        } else if (MoveUtils.mc.player.moveForward > 0.0f) {
            f2 = 0.5f;
        }
        if (MoveUtils.mc.player.moveStrafing > 0.0f) {
            f -= 90.0f * f2;
        }
        if (MoveUtils.mc.player.moveStrafing < 0.0f) {
            f += 90.0f * f2;
        }
        return bl ? Math.toRadians(f) : (double)f;
    }

    private MoveUtils() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static class MoveEvent {
        public static void setMoveMotion(MovingEvent movingEvent, double d) {
            double d2 = IMinecraft.mc.player.movementInput.moveForward;
            MovementInput cfr_ignored_0 = IMinecraft.mc.player.movementInput;
            double d3 = MovementInput.moveStrafe;
            float f = IMinecraft.mc.player.rotationYaw;
            if (d2 == 0.0 && d3 == 0.0) {
                movingEvent.getMotion().x = 0.0;
                movingEvent.getMotion().z = 0.0;
            } else {
                if (d2 != 0.0) {
                    if (d3 > 0.0) {
                        f += (float)(d2 > 0.0 ? -45 : 45);
                    } else if (d3 < 0.0) {
                        f += (float)(d2 > 0.0 ? 45 : -45);
                    }
                    d3 = 0.0;
                    if (d2 > 0.0) {
                        d2 = 1.0;
                    } else if (d2 < 0.0) {
                        d2 = -1.0;
                    }
                }
                movingEvent.getMotion().x = d2 * d * (double)MathHelper.cos((float)Math.toRadians(f + 90.0f)) + d3 * d * (double)MathHelper.sin((float)Math.toRadians(f + 90.0f));
                movingEvent.getMotion().z = d2 * d * (double)MathHelper.sin((float)Math.toRadians(f + 90.0f)) - d3 * d * (double)MathHelper.cos((float)Math.toRadians(f + 90.0f));
            }
        }
    }
}

