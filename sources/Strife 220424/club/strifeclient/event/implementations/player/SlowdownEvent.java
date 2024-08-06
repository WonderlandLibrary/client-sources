package club.strifeclient.event.implementations.player;

import best.azura.eventbus.events.CancellableEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SlowdownEvent extends CancellableEvent {
    public float forward, strafe;
}
