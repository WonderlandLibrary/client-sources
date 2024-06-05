package zamorozka.event.events;

import zamorozka.event.Event;

public class EventRenderChunkContainer extends Event {

    public net.minecraft.client.renderer.chunk.RenderChunk RenderChunk;
    public EventRenderChunkContainer(net.minecraft.client.renderer.chunk.RenderChunk renderChunk)
    {
        RenderChunk = renderChunk;
    }
	
}
