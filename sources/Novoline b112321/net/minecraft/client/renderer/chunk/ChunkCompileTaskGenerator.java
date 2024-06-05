package net.minecraft.client.renderer.chunk;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import net.minecraft.client.renderer.RegionRenderCacheBuilder;
import net.minecraft.client.renderer.chunk.ChunkCompileTaskGenerator$Status;
import net.minecraft.client.renderer.chunk.ChunkCompileTaskGenerator$Type;
import net.minecraft.client.renderer.chunk.CompiledChunk;
import net.minecraft.client.renderer.chunk.RenderChunk;

public class ChunkCompileTaskGenerator {
   private final RenderChunk renderChunk;
   private final ReentrantLock lock = new ReentrantLock();
   private final List listFinishRunnables = Lists.newArrayList();
   private final ChunkCompileTaskGenerator$Type type;
   private RegionRenderCacheBuilder regionRenderCacheBuilder;
   private CompiledChunk compiledChunk;
   private ChunkCompileTaskGenerator$Status status = ChunkCompileTaskGenerator$Status.PENDING;
   private boolean finished;

   public ChunkCompileTaskGenerator(RenderChunk var1, ChunkCompileTaskGenerator$Type var2) {
      this.renderChunk = var1;
      this.type = var2;
   }

   public ChunkCompileTaskGenerator$Status getStatus() {
      return this.status;
   }

   public RenderChunk getRenderChunk() {
      return this.renderChunk;
   }

   public CompiledChunk getCompiledChunk() {
      return this.compiledChunk;
   }

   public void setCompiledChunk(CompiledChunk var1) {
      this.compiledChunk = var1;
   }

   public RegionRenderCacheBuilder getRegionRenderCacheBuilder() {
      return this.regionRenderCacheBuilder;
   }

   public void setRegionRenderCacheBuilder(RegionRenderCacheBuilder var1) {
      this.regionRenderCacheBuilder = var1;
   }

   public void setStatus(ChunkCompileTaskGenerator$Status var1) {
      this.lock.lock();
      ChunkCompileTaskGenerator var10000 = this;
      ChunkCompileTaskGenerator$Status var10001 = var1;

      try {
         var10000.status = var10001;
      } finally {
         this.lock.unlock();
      }

   }

   public void finish() {
      this.lock.lock();
      ChunkCompileTaskGenerator var10000 = this;

      try {
         if(var10000.type == ChunkCompileTaskGenerator$Type.REBUILD_CHUNK && this.status != ChunkCompileTaskGenerator$Status.DONE) {
            this.renderChunk.setNeedsUpdate(true);
         }

         this.finished = true;
         this.status = ChunkCompileTaskGenerator$Status.DONE;

         for(Runnable var2 : this.listFinishRunnables) {
            var2.run();
         }
      } finally {
         this.lock.unlock();
      }

   }

   public void addFinishRunnable(Runnable var1) {
      this.lock.lock();
      ChunkCompileTaskGenerator var10000 = this;

      try {
         var10000.listFinishRunnables.add(var1);
         if(this.finished) {
            var1.run();
         }
      } finally {
         this.lock.unlock();
      }

   }

   public ReentrantLock getLock() {
      return this.lock;
   }

   public ChunkCompileTaskGenerator$Type getType() {
      return this.type;
   }

   public boolean isFinished() {
      return this.finished;
   }
}
