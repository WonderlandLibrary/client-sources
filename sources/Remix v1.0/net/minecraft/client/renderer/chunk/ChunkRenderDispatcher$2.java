package net.minecraft.client.renderer.chunk;

class ChunkRenderDispatcher$2 implements Runnable {
    final /* synthetic */ ChunkCompileTaskGenerator val$var2;
    
    @Override
    public void run() {
        ChunkRenderDispatcher.access$000(ChunkRenderDispatcher.this).remove(this.val$var2);
    }
}