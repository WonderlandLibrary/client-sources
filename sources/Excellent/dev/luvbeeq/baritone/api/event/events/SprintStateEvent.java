package dev.luvbeeq.baritone.api.event.events;

/**
 * @author Brady
 * @since 1/18/2019
 */
public final class SprintStateEvent {

    private Boolean state;

    public final void setState(boolean state) {
        this.state = state;
    }

    public final Boolean getState() {
        return this.state;
    }
}
