package ez.h.event.events;

import ez.h.event.*;

public class EventRenderChunk extends Event
{
    public bxr RenderChunk;
    public et BlockPos;
    
    public EventRenderChunk(final bxr renderChunk, final et blockPos) {
        this.BlockPos = blockPos;
        this.RenderChunk = renderChunk;
    }
}
