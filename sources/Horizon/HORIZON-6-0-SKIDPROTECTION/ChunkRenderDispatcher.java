package HORIZON-6-0-SKIDPROTECTION;

import org.lwjgl.opengl.GL11;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.ArrayList;
import java.util.Collection;
import com.google.common.util.concurrent.ListenableFutureTask;
import com.google.common.collect.Queues;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.apache.logging.log4j.LogManager;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.List;
import java.util.concurrent.ThreadFactory;
import org.apache.logging.log4j.Logger;

public class ChunkRenderDispatcher
{
    private static final Logger HorizonCode_Horizon_È;
    private static final ThreadFactory Â;
    private final List Ý;
    private final BlockingQueue Ø­áŒŠá;
    private final BlockingQueue Âµá€;
    private final WorldVertexBufferUploader Ó;
    private final VertexBufferUploader à;
    private final Queue Ø;
    private final ChunkRenderWorker áŒŠÆ;
    private static final String áˆºÑ¢Õ = "CL_00002463";
    
    static {
        HorizonCode_Horizon_È = LogManager.getLogger();
        Â = new ThreadFactoryBuilder().setNameFormat("Chunk Batcher %d").setDaemon(true).build();
    }
    
    public ChunkRenderDispatcher() {
        this.Ý = Lists.newArrayList();
        this.Ø­áŒŠá = Queues.newArrayBlockingQueue(100);
        this.Âµá€ = Queues.newArrayBlockingQueue(5);
        this.Ó = new WorldVertexBufferUploader();
        this.à = new VertexBufferUploader();
        this.Ø = Queues.newArrayDeque();
        for (int var1 = 0; var1 < 2; ++var1) {
            final ChunkRenderWorker var2 = new ChunkRenderWorker(this);
            final Thread var3 = ChunkRenderDispatcher.Â.newThread(var2);
            var3.start();
            this.Ý.add(var2);
        }
        for (int var1 = 0; var1 < 5; ++var1) {
            this.Âµá€.add(new RegionRenderCacheBuilder());
        }
        this.áŒŠÆ = new ChunkRenderWorker(this, new RegionRenderCacheBuilder());
    }
    
    public String HorizonCode_Horizon_È() {
        return String.format("pC: %03d, pU: %1d, aB: %1d", this.Ø­áŒŠá.size(), this.Ø.size(), this.Âµá€.size());
    }
    
    public boolean HorizonCode_Horizon_È(final long p_178516_1_) {
        boolean var3 = false;
        long var4;
        do {
            boolean var5 = false;
            final Queue var6 = this.Ø;
            final Queue var7 = this.Ø;
            synchronized (this.Ø) {
                if (!this.Ø.isEmpty()) {
                    this.Ø.poll().run();
                    var5 = true;
                    var3 = true;
                }
            }
            // monitorexit(this.\u00d8)
            if (p_178516_1_ == 0L) {
                break;
            }
            if (!var5) {
                break;
            }
            var4 = p_178516_1_ - System.nanoTime();
        } while (var4 >= 0L && var4 <= 1000000000L);
        return var3;
    }
    
    public boolean HorizonCode_Horizon_È(final RenderChunk p_178507_1_) {
        p_178507_1_.Ý().lock();
        boolean var4;
        try {
            final ChunkCompileTaskGenerator var2 = p_178507_1_.Ø­áŒŠá();
            var2.HorizonCode_Horizon_È(new Runnable() {
                private static final String Â = "CL_00002462";
                
                @Override
                public void run() {
                    ChunkRenderDispatcher.this.Ø­áŒŠá.remove(var2);
                }
            });
            final boolean var3 = this.Ø­áŒŠá.offer(var2);
            if (!var3) {
                var2.Âµá€();
            }
            var4 = var3;
        }
        finally {
            p_178507_1_.Ý().unlock();
        }
        p_178507_1_.Ý().unlock();
        return var4;
    }
    
    public boolean Â(final RenderChunk p_178505_1_) {
        p_178505_1_.Ý().lock();
        boolean var3;
        try {
            final ChunkCompileTaskGenerator var2 = p_178505_1_.Ø­áŒŠá();
            try {
                this.áŒŠÆ.HorizonCode_Horizon_È(var2);
            }
            catch (InterruptedException ex) {}
            var3 = true;
        }
        finally {
            p_178505_1_.Ý().unlock();
        }
        p_178505_1_.Ý().unlock();
        return var3;
    }
    
    public void Â() {
        this.Âµá€();
        while (this.HorizonCode_Horizon_È(0L)) {}
        final ArrayList var1 = Lists.newArrayList();
        while (var1.size() != 5) {
            try {
                var1.add(this.Ý());
            }
            catch (InterruptedException ex) {}
        }
        this.Âµá€.addAll(var1);
    }
    
    public void HorizonCode_Horizon_È(final RegionRenderCacheBuilder p_178512_1_) {
        this.Âµá€.add(p_178512_1_);
    }
    
    public RegionRenderCacheBuilder Ý() throws InterruptedException {
        return this.Âµá€.take();
    }
    
    public ChunkCompileTaskGenerator Ø­áŒŠá() throws InterruptedException {
        return this.Ø­áŒŠá.take();
    }
    
    public boolean Ý(final RenderChunk p_178509_1_) {
        p_178509_1_.Ý().lock();
        boolean var3;
        try {
            final ChunkCompileTaskGenerator var2 = p_178509_1_.Âµá€();
            if (var2 != null) {
                var2.HorizonCode_Horizon_È(new Runnable() {
                    private static final String Â = "CL_00002461";
                    
                    @Override
                    public void run() {
                        ChunkRenderDispatcher.this.Ø­áŒŠá.remove(var2);
                    }
                });
                final boolean var4;
                var3 = (var4 = this.Ø­áŒŠá.offer(var2));
                return var4;
            }
            var3 = true;
        }
        finally {
            p_178509_1_.Ý().unlock();
        }
        p_178509_1_.Ý().unlock();
        return var3;
    }
    
    public ListenableFuture HorizonCode_Horizon_È(final EnumWorldBlockLayer p_178503_1_, final WorldRenderer p_178503_2_, final RenderChunk p_178503_3_, final CompiledChunk p_178503_4_) {
        if (Minecraft.áŒŠà().Ï()) {
            if (OpenGlHelper.Ó()) {
                this.HorizonCode_Horizon_È(p_178503_2_, p_178503_3_.Â(p_178503_1_.ordinal()));
            }
            else {
                this.HorizonCode_Horizon_È(p_178503_2_, ((ListedRenderChunk)p_178503_3_).HorizonCode_Horizon_È(p_178503_1_, p_178503_4_), p_178503_3_);
            }
            p_178503_2_.Ý(0.0, 0.0, 0.0);
            return Futures.immediateFuture((Object)null);
        }
        final ListenableFutureTask var5 = ListenableFutureTask.create((Runnable)new Runnable() {
            private static final String Â = "CL_00002460";
            
            @Override
            public void run() {
                ChunkRenderDispatcher.this.HorizonCode_Horizon_È(p_178503_1_, p_178503_2_, p_178503_3_, p_178503_4_);
            }
        }, (Object)null);
        final Queue var6 = this.Ø;
        final Queue var7 = this.Ø;
        synchronized (this.Ø) {
            this.Ø.add(var5);
            final ListenableFutureTask listenableFutureTask = var5;
            // monitorexit(this.\u00d8)
            return (ListenableFuture)listenableFutureTask;
        }
    }
    
    private void HorizonCode_Horizon_È(final WorldRenderer p_178510_1_, final int p_178510_2_, final RenderChunk p_178510_3_) {
        GL11.glNewList(p_178510_2_, 4864);
        GlStateManager.Çªà¢();
        p_178510_3_.Ó();
        this.Ó.HorizonCode_Horizon_È(p_178510_1_, p_178510_1_.Âµá€());
        GlStateManager.Ê();
        GL11.glEndList();
    }
    
    private void HorizonCode_Horizon_È(final WorldRenderer p_178506_1_, final VertexBuffer p_178506_2_) {
        this.à.HorizonCode_Horizon_È(p_178506_2_);
        this.à.HorizonCode_Horizon_È(p_178506_1_, p_178506_1_.Âµá€());
    }
    
    public void Âµá€() {
        while (!this.Ø­áŒŠá.isEmpty()) {
            final ChunkCompileTaskGenerator task = (ChunkCompileTaskGenerator)this.Ø­áŒŠá.poll();
            if (task != null) {
                task.Âµá€();
            }
        }
    }
}
