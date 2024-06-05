package net.minecraft.client.renderer.chunk;

import com.google.common.util.concurrent.*;
import java.util.*;
import java.util.concurrent.*;
import net.minecraft.client.*;
import net.minecraft.crash.*;

class ChunkRenderWorker$2 implements FutureCallback {
    final /* synthetic */ ChunkCompileTaskGenerator val$p_178474_1_;
    final /* synthetic */ CompiledChunk val$var7;
    
    public void func_178481_a(final List p_178481_1_) {
        ChunkRenderWorker.access$000(ChunkRenderWorker.this, this.val$p_178474_1_);
        this.val$p_178474_1_.func_178540_f().lock();
        try {
            if (this.val$p_178474_1_.func_178546_a() != ChunkCompileTaskGenerator.Status.UPLOADING) {
                if (!this.val$p_178474_1_.func_178537_h()) {
                    ChunkRenderWorker.access$100().warn("Chunk render task was " + this.val$p_178474_1_.func_178546_a() + " when I expected it to be uploading; aborting task");
                }
                return;
            }
            this.val$p_178474_1_.func_178535_a(ChunkCompileTaskGenerator.Status.DONE);
        }
        finally {
            this.val$p_178474_1_.func_178540_f().unlock();
        }
        this.val$p_178474_1_.func_178536_b().func_178580_a(this.val$var7);
    }
    
    public void onFailure(final Throwable p_onFailure_1_) {
        ChunkRenderWorker.access$000(ChunkRenderWorker.this, this.val$p_178474_1_);
        if (!(p_onFailure_1_ instanceof CancellationException) && !(p_onFailure_1_ instanceof InterruptedException)) {
            Minecraft.getMinecraft().crashed(CrashReport.makeCrashReport(p_onFailure_1_, "Rendering chunk"));
        }
    }
    
    public void onSuccess(final Object p_onSuccess_1_) {
        this.func_178481_a((List)p_onSuccess_1_);
    }
}