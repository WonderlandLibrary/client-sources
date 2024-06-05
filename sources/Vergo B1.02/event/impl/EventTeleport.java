package xyz.vergoclient.event.impl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.network.play.client.C03PacketPlayer;
import xyz.vergoclient.event.Event;
@Getter
@Setter
@AllArgsConstructor
public final class EventTeleport extends Event {
    private C03PacketPlayer.C06PacketPlayerPosLook response;
    private double posX;
    private double posY;
    private double posZ;
    private float yaw;
    private float pitch;
}
