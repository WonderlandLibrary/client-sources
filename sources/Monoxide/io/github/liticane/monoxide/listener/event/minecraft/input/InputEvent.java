package io.github.liticane.monoxide.listener.event.minecraft.input;

import io.github.liticane.monoxide.listener.event.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class InputEvent extends Event {

    public float moveForward, moveStrafing;
    public boolean jumping, sneaking;
    public double multiplier;

}
