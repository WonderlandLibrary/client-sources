package dev.excellent.api.event.impl.player;

import dev.excellent.api.event.EventBase;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostMoveEvent extends EventBase {
    private double horizontalMove;
}
