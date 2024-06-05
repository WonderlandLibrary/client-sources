package de.dietrichpaul.clientbase.util.minecraft;

import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.Vec3d;

public class MoveUtil {
    private static final MinecraftClient mc = MinecraftClient.getInstance();

    public static float getPlayerSpeedF() {
        if (mc.player == null) return 0.0F;
        return (float) Math.sqrt(
                Math.pow(mc.player.getVelocity().x, 2)
                        + Math.pow(mc.player.getVelocity().z, 2)
        );
    }

    public static void setPlayerSpeedF(final float speed) {
        if (mc.player == null) return;
        mc.player.setVelocity(new Vec3d(
                Math.cos(getPlayerMoveYawF()) * speed,
                mc.player.getVelocity().y,
                Math.sin(getPlayerMoveYawF()) * speed
        ));
    }

    public static double getPlayerSpeedD() {
        if (mc.player == null) return 0.0D;
        return Math.sqrt(
                Math.pow(mc.player.getVelocity().x, 2)
                        + Math.pow(mc.player.getVelocity().z, 2)
        );
    }

    public static void setPlayerSpeedD(final double speed) {
        if (mc.player == null) return;
        mc.player.setVelocity(new Vec3d(
                Math.cos(getPlayerMoveYawD()) * speed,
                mc.player.getVelocity().y,
                Math.sin(getPlayerMoveYawD()) * speed
        ));
    }

    public static boolean isPlayerMovingForward() {
        if (mc.player == null) return false;
        return mc.player.input.movementForward > 0;
    }

    public static boolean isPlayerMovingBackward() {
        if (mc.player == null) return false;
        return mc.player.input.movementForward < 0;
    }

    public static boolean isPlayerMovingLeft() {
        if (mc.player == null) return false;
        return mc.player.input.movementSideways > 0;
    }

    public static boolean isPlayerMovingRight() {
        if (mc.player == null) return false;
        return mc.player.input.movementSideways < 0;
    }

    public static boolean isPlayerMoving() {
        return isPlayerMovingForward() || isPlayerMovingLeft() || isPlayerMovingBackward() || isPlayerMovingRight();
    }

    public static float getPlayerMoveYawF() {
        if (mc.player == null) return 0.0F;
        return (float) Math.toRadians(Math.toDegrees(Math.atan2(
                mc.player.input.movementForward,
                mc.player.input.movementSideways
        )) + mc.player.headYaw);
    }

    public static double getPlayerMoveYawD() {
        if (mc.player == null) return 0.0F;
        return Math.toRadians(Math.toDegrees(Math.atan2(
                mc.player.input.movementForward,
                mc.player.input.movementSideways
        )) + mc.player.headYaw);
    }
}
