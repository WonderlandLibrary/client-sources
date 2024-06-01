package com.polarware.event.impl.render;


import com.polarware.event.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public final class Render3DEvent implements Event {

    private final float partialTicks;
}
