package com.alan.clients.component.impl.player.translation;

public class Position {
    public final double posX;
    public final double posY;
    public final double posZ;
    public final float yaw;
    public final float pitch;

    public Position(double posX, double posY, double posZ, float yaw, float pitch) {
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.yaw = yaw;
        this.pitch = pitch;
    }
}