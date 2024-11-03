package dev.star.event.impl.render;

import dev.star.event.Event;
import store.intent.intentguard.annotation.Exclude;
import store.intent.intentguard.annotation.Strategy;

public class ShaderEvent extends Event {
    private final boolean bloom;

    public ShaderEvent(boolean bloom){
        this.bloom = bloom;
    }

    @Exclude(Strategy.NAME_REMAPPING)
    public boolean isBloom() {
        return bloom;
    }
}

