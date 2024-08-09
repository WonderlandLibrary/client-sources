package dev.excellent.api.event.impl.input;

import dev.excellent.api.event.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraft.client.gui.screen.Screen;

@Getter
@AllArgsConstructor
public final class KeyboardReleaseEvent extends Event {
    private final int keyCode;
    private final Screen screen;
}
