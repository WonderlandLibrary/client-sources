package net.minecraft.client.renderer.chunk;

import com.google.common.collect.Lists;
import com.google.common.collect.Queues;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadFactory;
import net.HN;
import net.Kj;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RegionRenderCacheBuilder;
import net.minecraft.client.renderer.VertexBufferUploader;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.chunk.ChunkCompileTaskGenerator;
import net.minecraft.client.renderer.chunk.CompiledChunk;
import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.client.renderer.vertex.VertexBuffer;
import net.minecraft.util.EnumWorldBlockLayer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.GL11;

public class ChunkRenderDispatcher {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final ThreadFactory threadFactory = (new ThreadFactoryBuilder()).setNameFormat("Chunk Batcher %d").setDaemon(true).build();
   private final List listThreadedWorkers = Lists.newArrayList();
   private final BlockingQueue queueChunkUpdates = Queues.newArrayBlockingQueue(100);
   private final BlockingQueue queueFreeRenderBuilders = Queues.newArrayBlockingQueue(5);
   private final HN b = new HN();
   private final VertexBufferUploader vertexUploader = new VertexBufferUploader();
   private final Queue queueChunkUploads = Queues.newArrayDeque();
   private final Kj c;

   public ChunkRenderDispatcher() {
      for(int var1 = 0; var1 < 2; ++var1) {
         Kj var2 = new Kj(this);
         Thread var3 = threadFactory.newThread(var2);
         var3.start();
         this.listThreadedWorkers.add(var2);
      }

      for(int var4 = 0; var4 < 5; ++var4) {
         this.queueFreeRenderBuilders.add(new RegionRenderCacheBuilder());
      }

      this.c = new Kj(this, new RegionRenderCacheBuilder());
   }

   public String getDebugInfo() {
      return String.format("pC: %03d, pU: %1d, aB: %1d", new Object[]{Integer.valueOf(this.queueChunkUpdates.size()), Integer.valueOf(this.queueChunkUploads.size()), Integer.valueOf(this.queueFreeRenderBuilders.size())});
   }

   public boolean runChunkUploads(long param1) {
      // $FF: Couldn't be decompiled
   }

   public boolean updateChunkLater(RenderChunk var1) {
      var1.getLockCompileTask().lock();
      RenderChunk var10000 = var1;

      boolean var2;
      try {
         ChunkCompileTaskGenerator var3 = var10000.makeCompileTaskChunk();
         var3.addFinishRunnable(this::lambda$updateChunkLater$0);
         boolean var4 = this.queueChunkUpdates.offer(var3);
         var3.finish();
         var2 = var4;
      } finally {
         var1.getLockCompileTask().unlock();
      }

      return var2;
   }

   public boolean updateChunkNow(RenderChunk var1) {
      var1.getLockCompileTask().lock();
      RenderChunk var10000 = var1;

      boolean var2;
      try {
         ChunkCompileTaskGenerator var3 = var10000.makeCompileTaskChunk();

         try {
            this.c.a(var3);
         } catch (InterruptedException var8) {
            ;
         }

         var2 = true;
      } finally {
         var1.getLockCompileTask().unlock();
      }

      return var2;
   }

   public void stopChunkUpdates() {
      this.clearChunkUpdates();

      while(this.runChunkUploads(0L)) {
         ;
      }

      ArrayList var1 = Lists.newArrayList();

      while(var1.size() != 5) {
         ArrayList var10000 = var1;
         ChunkRenderDispatcher var10001 = this;

         try {
            var10000.add(var10001.allocateRenderBuilder());
         } catch (InterruptedException var3) {
            ;
         }
      }

      this.queueFreeRenderBuilders.addAll(var1);
   }

   public void freeRenderBuilder(RegionRenderCacheBuilder var1) {
      this.queueFreeRenderBuilders.add(var1);
   }

   public RegionRenderCacheBuilder allocateRenderBuilder() throws InterruptedException {
      return (RegionRenderCacheBuilder)this.queueFreeRenderBuilders.take();
   }

   public ChunkCompileTaskGenerator getNextChunkUpdate() throws InterruptedException {
      return (ChunkCompileTaskGenerator)this.queueChunkUpdates.take();
   }

   public boolean b(RenderChunk var1) {
      var1.getLockCompileTask().lock();
      RenderChunk var10000 = var1;

      boolean var4;
      try {
         ChunkCompileTaskGenerator var3 = var10000.makeCompileTaskTransparency();
         boolean var2 = true;
         var4 = var2;
      } finally {
         var1.getLockCompileTask().unlock();
      }

      return var4;
   }

   public ListenableFuture uploadChunk(EnumWorldBlockLayer param1, WorldRenderer param2, RenderChunk param3, CompiledChunk param4) {
      // $FF: Couldn't be decompiled
   }

   private void uploadDisplayList(WorldRenderer var1, int var2, RenderChunk var3) {
      GL11.glNewList(var2, 4864);
      GlStateManager.pushMatrix();
      var3.multModelviewMatrix();
      this.b.func_181679_a(var1);
      GlStateManager.popMatrix();
      GL11.glEndList();
   }

   private void uploadVertexBuffer(WorldRenderer var1, VertexBuffer var2) {
      this.vertexUploader.setVertexBuffer(var2);
      this.vertexUploader.func_181679_a(var1);
   }

   public void clearChunkUpdates() {
      while(!this.queueChunkUpdates.isEmpty()) {
         ChunkCompileTaskGenerator var1 = (ChunkCompileTaskGenerator)this.queueChunkUpdates.poll();
         var1.finish();
      }

   }

   private void lambda$uploadChunk$2(EnumWorldBlockLayer var1, WorldRenderer var2, RenderChunk var3, CompiledChunk var4) {
      this.uploadChunk(var1, var2, var3, var4);
   }

   private void lambda$updateTransparencyLater$1(ChunkCompileTaskGenerator var1) {
      this.queueChunkUpdates.remove(var1);
   }

   private void lambda$updateChunkLater$0(ChunkCompileTaskGenerator var1) {
      this.queueChunkUpdates.remove(var1);
   }

   private static InterruptedException a(InterruptedException var0) {
      return var0;
   }
}
