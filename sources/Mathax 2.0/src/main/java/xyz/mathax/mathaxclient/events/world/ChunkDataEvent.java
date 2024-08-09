package xyz.mathax.mathaxclient.events.world;

import xyz.mathax.mathaxclient.utils.misc.Pool;
import net.minecraft.world.chunk.WorldChunk;

public class ChunkDataEvent {
    private static final Pool<ChunkDataEvent> INSTANCE = new Pool<>(ChunkDataEvent::new);

    public WorldChunk chunk;

    public static ChunkDataEvent get(WorldChunk chunk) {
        ChunkDataEvent event = INSTANCE.get();
        event.chunk = chunk;
        return event;
    }

    public static void returnChunkDataEvent(ChunkDataEvent event) {
        INSTANCE.free(event);
    }
}
