package ez.h.event.events;

import ez.h.event.*;

public class EventRenderChunkContainer extends Event
{
    public bxr RenderChunk;
    
    public EventRenderChunkContainer(final bxr renderChunk) {
        this.RenderChunk = renderChunk;
    }
}
