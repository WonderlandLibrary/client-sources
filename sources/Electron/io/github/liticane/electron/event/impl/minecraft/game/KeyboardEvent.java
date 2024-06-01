package io.github.liticane.electron.event.impl.minecraft.game;

import io.github.liticane.electron.event.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class KeyboardEvent extends Event {
    private int key;
}
