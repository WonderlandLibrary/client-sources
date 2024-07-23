package io.github.liticane.monoxide.anticheat.data.tracker.impl;

import lombok.Getter;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S14PacketEntity;
import net.minecraft.network.play.server.S18PacketEntityTeleport;
import io.github.liticane.monoxide.anticheat.data.PlayerData;
import io.github.liticane.monoxide.anticheat.data.tracker.Tracker;

@Getter
public class MovementTracker extends Tracker {

    private double x, y, z, lastX, lastY, lastZ;
    private double xDiff, yDiff, zDiff, speed, lastSpeed;

    private boolean onGround, lastOnGround;
    private boolean jumping, sprinting, sneaking, usingItem, hitSlowdown;

    public MovementTracker(PlayerData data) {
        super(data);

        this.x = data.getPlayer().posX;
        this.y = data.getPlayer().posY;
        this.z = data.getPlayer().posZ;
    }

    @Override
    public void handle(Packet<?> packet) {
        if(packet instanceof S14PacketEntity) {
            S14PacketEntity packetEntity = (S14PacketEntity) packet;

            if(packetEntity.getEntityId() == getData().getPlayer().getEntityID()) {

                this.lastX = x;
                this.lastY = y;
                this.lastZ = z;

                this.x = getData().getPlayer().posX;
                this.y = getData().getPlayer().posY;
                this.z = getData().getPlayer().posZ;

                this.xDiff = Math.abs(x - lastX);
                this.zDiff = Math.abs(z - lastZ);

                this.yDiff = y - lastY;

                this.lastSpeed = speed;
                this.speed = Math.sqrt(xDiff * xDiff + zDiff * zDiff);

                this.lastOnGround = onGround;
                this.onGround = packetEntity.getOnGround();
            }
        } else if(packet instanceof S18PacketEntityTeleport) {
            S18PacketEntityTeleport packetTeleport = (S18PacketEntityTeleport) packet;

            if(packetTeleport.getEntityId() == getData().getPlayer().getEntityID()) {
                this.lastX = x;
                this.lastY = y;
                this.lastZ = z;

                this.x = getData().getPlayer().posX;
                this.y = getData().getPlayer().posY;
                this.z = getData().getPlayer().posZ;

                this.xDiff = Math.abs(x - lastX);
                this.yDiff = Math.abs(y - lastY);
                this.zDiff = Math.abs(z - lastZ);

                this.speed = Math.abs(xDiff - zDiff);

                this.lastOnGround = onGround;
                this.onGround = packetTeleport.getOnGround();
            }
        }
    }
}
