package cc.slack.events.impl.game;

import cc.slack.events.Event;
import cc.slack.events.State;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TickEvent extends Event {

    private State state;

    public TickEvent() {
        this.state = State.PRE;
    }
}