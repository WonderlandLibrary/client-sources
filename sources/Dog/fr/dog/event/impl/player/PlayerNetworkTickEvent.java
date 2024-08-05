package fr.dog.event.impl.player;

import fr.dog.event.CancellableEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor
public class PlayerNetworkTickEvent extends CancellableEvent {
    private double posX, posY, posZ;
    private float yaw, pitch;
    private boolean onGround;
}
