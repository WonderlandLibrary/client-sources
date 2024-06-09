package dev.vertic.event.impl.other;

import dev.vertic.event.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.network.play.client.C03PacketPlayer;

@Getter
@Setter
@AllArgsConstructor
public class TeleportEvent extends Event {

    private C03PacketPlayer response;
    private double posX;
    private double posY;
    private double posZ;
    private float yaw;
    private float pitch;

}
