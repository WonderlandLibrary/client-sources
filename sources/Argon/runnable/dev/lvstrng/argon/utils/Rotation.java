package dev.lvstrng.argon.utils;

public record Rotation(double yaw, double pitch) {
    public double getYaw() {
        return yaw;
    }

    public double getPitch() {
        return pitch;
    }
}
