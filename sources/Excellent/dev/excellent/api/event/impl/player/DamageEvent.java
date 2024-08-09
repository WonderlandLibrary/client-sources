package dev.excellent.api.event.impl.player;

import dev.excellent.api.event.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DamageEvent extends Event {
    private final DamageType damageType;

    public enum DamageType {
        ENDER_PEARL, ARROW, FALL
    }
}
