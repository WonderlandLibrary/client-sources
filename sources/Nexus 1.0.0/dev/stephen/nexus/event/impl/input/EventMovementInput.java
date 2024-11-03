package dev.stephen.nexus.event.impl.input;

import dev.stephen.nexus.event.types.Event;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventMovementInput implements Event {

    boolean pressingForward;
    boolean pressingBack;
    boolean pressingLeft;
    boolean pressingRight;

    boolean jumping;
    boolean sneaking;

    float movementForward;
    float movementSideways;

    public EventMovementInput(boolean pressingForward, boolean pressingBack, boolean pressingLeft, boolean pressingRight, boolean jumping, boolean sneaking, float movementForward, float movementSideways) {
        this.pressingForward = pressingForward;
        this.pressingBack = pressingBack;
        this.pressingLeft = pressingLeft;
        this.pressingRight = pressingRight;
        this.jumping = jumping;
        this.sneaking = sneaking;

        this.movementForward = movementForward;
        this.movementSideways = movementSideways;
    }
}
