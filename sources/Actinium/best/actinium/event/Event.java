package best.actinium.event;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Event {
    private EventType type;
    public boolean cancelled;
}