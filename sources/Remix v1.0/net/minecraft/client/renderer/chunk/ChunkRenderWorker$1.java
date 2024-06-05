package net.minecraft.client.renderer.chunk;

import com.google.common.util.concurrent.*;

class ChunkRenderWorker$1 implements Runnable {
    final /* synthetic */ ListenableFuture val$var19;
    
    @Override
    public void run() {
        this.val$var19.cancel(false);
    }
}