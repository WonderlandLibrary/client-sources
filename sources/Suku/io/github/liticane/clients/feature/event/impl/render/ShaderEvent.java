package io.github.liticane.clients.feature.event.impl.render;

import io.github.liticane.clients.feature.event.Event;
import io.github.liticane.clients.feature.property.impl.BooleanProperty;
import lombok.Getter;

@Getter
public class ShaderEvent extends Event {
    private final boolean bloom;

    public ShaderEvent(boolean bloom){
        this.bloom = bloom;
    }
}