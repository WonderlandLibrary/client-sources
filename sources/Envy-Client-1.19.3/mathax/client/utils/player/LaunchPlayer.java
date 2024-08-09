package mathax.client.utils.player;

import static mathax.client.utils.vayzeutils.AntiWallImport.mc;

public class LaunchPlayer {
    public static void MatrixElytra(double BoostValue) {
        // Get the player's rotation pitch and yaw
        float rotationYaw = mc.player.getYaw();
        float rotationPitch = mc.player.getPitch();

        // Convert degrees to radians
        double yawRadians = Math.toRadians(rotationYaw);
        double pitchRadians = Math.toRadians(rotationPitch);

        // Calculate the direction vector
        double x = -Math.sin(yawRadians) * Math.cos(pitchRadians);
        double y = -Math.sin(pitchRadians);
        double z = Math.cos(yawRadians) * Math.cos(pitchRadians);

        // Normalize the direction vector
        double length = Math.sqrt(x * x + y * y + z * z);
        x /= length;
        y /= length;
        z /= length;

        // Scale the direction vector by the speed
        x *= BoostValue;
        y *= BoostValue;
        z *= BoostValue;

        // Set the velocity in the direction the player is facing
        mc.player.setVelocity(x, y, z);
    }
}

