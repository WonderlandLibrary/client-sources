package dev.excellent.api.event.impl.input;

import dev.excellent.api.event.CancellableEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public final class GuiMouseClickEvent extends CancellableEvent {
    private final double mouseX, mouseY;
    private final int button;
}