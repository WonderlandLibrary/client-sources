package net.minecraft.client.renderer.chunk;

import net.minecraft.util.*;
import net.minecraft.client.renderer.*;

class ChunkRenderDispatcher$3 implements Runnable {
    final /* synthetic */ EnumWorldBlockLayer val$p_178503_1_;
    final /* synthetic */ WorldRenderer val$p_178503_2_;
    final /* synthetic */ RenderChunk val$p_178503_3_;
    final /* synthetic */ CompiledChunk val$p_178503_4_;
    
    @Override
    public void run() {
        ChunkRenderDispatcher.this.func_178503_a(this.val$p_178503_1_, this.val$p_178503_2_, this.val$p_178503_3_, this.val$p_178503_4_);
    }
}