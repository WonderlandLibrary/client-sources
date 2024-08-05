package fr.dog.event.impl.player.move;

import fr.dog.event.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MoveInputEvent extends Event {
    private float forward, strafe;
    private boolean jumping, sneaking;
    private double sneakMultiplier;
}