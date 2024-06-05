package net.minecraft.client.renderer.chunk;

import com.google.common.collect.Lists;
import com.google.common.collect.Queues;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListenableFutureTask;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadFactory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RegionRenderCacheBuilder;
import net.minecraft.client.renderer.VertexBufferUploader;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.WorldVertexBufferUploader;
import net.minecraft.client.renderer.vertex.VertexBuffer;
import net.minecraft.util.EnumWorldBlockLayer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.GL11;

public class ChunkRenderDispatcher {
   private final VertexBufferUploader field_178518_g = new VertexBufferUploader();
   private final BlockingQueue field_178519_d = Queues.newArrayBlockingQueue(100);
   private static final Logger field_178523_a = LogManager.getLogger();
   private final Queue field_178524_h = Queues.newArrayDeque();
   private final ChunkRenderWorker field_178525_i;
   private static final String __OBFID = "CL_00002463";
   private final BlockingQueue field_178520_e = Queues.newArrayBlockingQueue(5);
   private final WorldVertexBufferUploader field_178517_f = new WorldVertexBufferUploader();
   private final List field_178522_c = Lists.newArrayList();
   private static final ThreadFactory field_178521_b = (new ThreadFactoryBuilder()).setNameFormat("Chunk Batcher %d").setDaemon(true).build();

   public boolean func_178507_a(RenderChunk var1) {
      var1.func_178579_c().lock();

      try {
         ChunkCompileTaskGenerator var3 = var1.func_178574_d();
         var3.func_178539_a(new Runnable(this, var3) {
            private final ChunkCompileTaskGenerator val$var2;
            private static final String __OBFID = "CL_00002462";
            final ChunkRenderDispatcher this$0;

            {
               this.this$0 = var1;
               this.val$var2 = var2;
            }

            public void run() {
               ChunkRenderDispatcher.access$0(this.this$0).remove(this.val$var2);
            }
         });
         boolean var4 = this.field_178519_d.offer(var3);
         if (!var4) {
            var3.func_178542_e();
         }

         var1.func_178579_c().unlock();
         return var4;
      } finally {
         var1.func_178579_c().unlock();
      }
   }

   public ChunkCompileTaskGenerator func_178511_d() throws InterruptedException, InterruptedException {
      return (ChunkCompileTaskGenerator)this.field_178519_d.take();
   }

   public ChunkRenderDispatcher() {
      int var1;
      for(var1 = 0; var1 < 2; ++var1) {
         ChunkRenderWorker var2 = new ChunkRenderWorker(this);
         Thread var3 = field_178521_b.newThread(var2);
         var3.start();
         this.field_178522_c.add(var2);
      }

      for(var1 = 0; var1 < 5; ++var1) {
         this.field_178520_e.add(new RegionRenderCacheBuilder());
      }

      this.field_178525_i = new ChunkRenderWorker(this, new RegionRenderCacheBuilder());
   }

   public boolean func_178509_c(RenderChunk var1) {
      var1.func_178579_c().lock();

      boolean var6;
      try {
         ChunkCompileTaskGenerator var3 = var1.func_178582_e();
         boolean var4;
         if (var3 != null) {
            var3.func_178539_a(new Runnable(this, var3) {
               private static final String __OBFID = "CL_00002461";
               private final ChunkCompileTaskGenerator val$var2;
               final ChunkRenderDispatcher this$0;

               public void run() {
                  ChunkRenderDispatcher.access$0(this.this$0).remove(this.val$var2);
               }

               {
                  this.this$0 = var1;
                  this.val$var2 = var2;
               }
            });
            var4 = this.field_178519_d.offer(var3);
            var1.func_178579_c().unlock();
            return var4;
         }

         var4 = true;
         var6 = var4;
      } finally {
         var1.func_178579_c().unlock();
      }

      return var6;
   }

   public void func_178512_a(RegionRenderCacheBuilder var1) {
      this.field_178520_e.add(var1);
   }

   private void func_178506_a(WorldRenderer var1, VertexBuffer var2) {
      this.field_178518_g.func_178178_a(var2);
      this.field_178518_g.draw(var1, var1.func_178976_e());
   }

   static BlockingQueue access$0(ChunkRenderDispatcher var0) {
      return var0.field_178519_d;
   }

   public void func_178513_e() {
      while(!this.field_178519_d.isEmpty()) {
         ChunkCompileTaskGenerator var1 = (ChunkCompileTaskGenerator)this.field_178519_d.poll();
         if (var1 != null) {
            var1.func_178542_e();
         }
      }

   }

   public boolean func_178505_b(RenderChunk var1) {
      var1.func_178579_c().lock();

      try {
         ChunkCompileTaskGenerator var3 = var1.func_178574_d();

         try {
            this.field_178525_i.func_178474_a(var3);
         } catch (InterruptedException var8) {
         }

         boolean var2 = true;
         var1.func_178579_c().unlock();
         return var2;
      } finally {
         var1.func_178579_c().unlock();
      }
   }

   public boolean func_178516_a(long var1) {
      boolean var3 = false;

      long var4;
      do {
         boolean var6 = false;
         Queue var7 = this.field_178524_h;
         Queue var8 = this.field_178524_h;
         synchronized(this.field_178524_h) {
            if (!this.field_178524_h.isEmpty()) {
               ((ListenableFutureTask)this.field_178524_h.poll()).run();
               var6 = true;
               var3 = true;
            }
         }

         if (var1 == 0L || !var6) {
            break;
         }

         var4 = var1 - System.nanoTime();
      } while(var4 >= 0L && var4 <= 1000000000L);

      return var3;
   }

   private void func_178510_a(WorldRenderer var1, int var2, RenderChunk var3) {
      GL11.glNewList(var2, 4864);
      GlStateManager.pushMatrix();
      var3.func_178572_f();
      this.field_178517_f.draw(var1, var1.func_178976_e());
      GlStateManager.popMatrix();
      GL11.glEndList();
   }

   public String func_178504_a() {
      return String.format("pC: %03d, pU: %1d, aB: %1d", this.field_178519_d.size(), this.field_178524_h.size(), this.field_178520_e.size());
   }

   public ListenableFuture func_178503_a(EnumWorldBlockLayer var1, WorldRenderer var2, RenderChunk var3, CompiledChunk var4) {
      if (Minecraft.getMinecraft().isCallingFromMinecraftThread()) {
         if (OpenGlHelper.func_176075_f()) {
            this.func_178506_a(var2, var3.func_178565_b(var1.ordinal()));
         } else {
            this.func_178510_a(var2, ((ListedRenderChunk)var3).func_178600_a(var1, var4), var3);
         }

         var2.setTranslation(0.0D, 0.0D, 0.0D);
         return Futures.immediateFuture((Object)null);
      } else {
         ListenableFutureTask var5 = ListenableFutureTask.create(new Runnable(this, var1, var2, var3, var4) {
            final ChunkRenderDispatcher this$0;
            private static final String __OBFID = "CL_00002460";
            private final WorldRenderer val$p_178503_2_;
            private final RenderChunk val$p_178503_3_;
            private final EnumWorldBlockLayer val$p_178503_1_;
            private final CompiledChunk val$p_178503_4_;

            {
               this.this$0 = var1;
               this.val$p_178503_1_ = var2;
               this.val$p_178503_2_ = var3;
               this.val$p_178503_3_ = var4;
               this.val$p_178503_4_ = var5;
            }

            public void run() {
               this.this$0.func_178503_a(this.val$p_178503_1_, this.val$p_178503_2_, this.val$p_178503_3_, this.val$p_178503_4_);
            }
         }, (Object)null);
         Queue var6 = this.field_178524_h;
         Queue var7 = this.field_178524_h;
         synchronized(this.field_178524_h) {
            this.field_178524_h.add(var5);
            return var5;
         }
      }
   }

   public RegionRenderCacheBuilder func_178515_c() throws InterruptedException, InterruptedException {
      return (RegionRenderCacheBuilder)this.field_178520_e.take();
   }

   public void func_178514_b() {
      this.func_178513_e();

      while(this.func_178516_a(0L)) {
      }

      ArrayList var1 = Lists.newArrayList();

      while(var1.size() != 5) {
         try {
            var1.add(this.func_178515_c());
         } catch (InterruptedException var3) {
         }
      }

      this.field_178520_e.addAll(var1);
   }
}
