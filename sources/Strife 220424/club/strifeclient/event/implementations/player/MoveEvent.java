package club.strifeclient.event.implementations.player;

import best.azura.eventbus.events.CancellableEvent;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MoveEvent extends CancellableEvent {
    public double x, y, z;
}
