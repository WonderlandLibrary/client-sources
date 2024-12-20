package wtf.shiyeno.events.impl.game;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import wtf.shiyeno.events.Event;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class EventMouseTick extends Event {

    private int button;
}
