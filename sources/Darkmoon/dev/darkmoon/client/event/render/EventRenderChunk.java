package dev.darkmoon.client.event.render;

import com.darkmagician6.eventapi.events.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraft.client.renderer.chunk.RenderChunk;

@Getter
@AllArgsConstructor
public class EventRenderChunk implements Event {
    private RenderChunk renderChunk;
}
