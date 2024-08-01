package wtf.diablo.client.event.impl.client.renderering;

import wtf.diablo.client.event.api.AbstractEvent;

public final class EventShader extends AbstractEvent {
    private final boolean bloom;

    public EventShader(final boolean bloom) {
        this.bloom = bloom;
    }

    public boolean isBloom() {
        return this.bloom;
    }
}
