package dev.darkmoon.client.event.render;

import com.darkmagician6.eventapi.events.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EventRender3D implements Event {
    private float partialTicks;
}
