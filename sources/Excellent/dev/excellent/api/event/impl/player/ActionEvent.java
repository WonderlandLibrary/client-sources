package dev.excellent.api.event.impl.player;

import dev.excellent.api.event.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ActionEvent extends Event {
    private boolean sprintState;
}