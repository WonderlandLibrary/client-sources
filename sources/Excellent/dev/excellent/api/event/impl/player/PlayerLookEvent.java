package dev.excellent.api.event.impl.player;

import dev.excellent.api.event.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.joml.Vector2f;

@Getter
@Setter
@AllArgsConstructor
public final class PlayerLookEvent extends Event {
    private Vector2f rotation;
}