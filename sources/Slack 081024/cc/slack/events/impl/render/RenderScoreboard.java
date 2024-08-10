package cc.slack.events.impl.render;

import cc.slack.events.Event;
import cc.slack.events.State;

public class RenderScoreboard extends Event {

    State state;

    public RenderScoreboard(final State state) {
        this.state = state;
    }

    public State getState() {
        return this.state;
    }

    public boolean isPre() {
        return this.state == State.PRE;
    }

    public void setState(final State state) {
        this.state = state;
    }

}
