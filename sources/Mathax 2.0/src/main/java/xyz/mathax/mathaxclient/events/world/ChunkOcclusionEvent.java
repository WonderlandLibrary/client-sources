package xyz.mathax.mathaxclient.events.world;

import xyz.mathax.mathaxclient.events.Cancellable;

public class ChunkOcclusionEvent extends Cancellable {
    private static final ChunkOcclusionEvent INSTANCE = new ChunkOcclusionEvent();

    public static ChunkOcclusionEvent get() {
        INSTANCE.setCancelled(false);
        return INSTANCE;
    }
}
