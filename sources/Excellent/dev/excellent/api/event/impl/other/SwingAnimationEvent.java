package dev.excellent.api.event.impl.other;

import dev.excellent.api.event.CancellableEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public final class SwingAnimationEvent extends CancellableEvent {
    private int animation;
}