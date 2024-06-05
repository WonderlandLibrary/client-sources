package zamorozka.event.events;

import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.util.math.BlockPos;
import zamorozka.event.Event;

public class EventRenderChunk extends Event {
    public BlockPos BlockPos;
    public RenderChunk RenderChunk;
    public EventRenderChunk(RenderChunk renderChunk, BlockPos blockPos)
    {
        BlockPos = blockPos;
        RenderChunk = renderChunk;
    }
}