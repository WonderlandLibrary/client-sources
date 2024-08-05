package fr.dog.event.impl.render;

import fr.dog.event.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor
public final class Render3DEvent extends Event {
    private final float partialTicks;
}