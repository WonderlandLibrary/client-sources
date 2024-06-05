package net.minecraft.client.renderer.chunk;

import com.google.common.collect.Lists;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CancellationException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RegionRenderCacheBuilder;
import net.minecraft.crash.CrashReport;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumWorldBlockLayer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ChunkRenderWorker implements Runnable {
   private final RegionRenderCacheBuilder field_178478_c;
   private final ChunkRenderDispatcher field_178477_b;
   private static final Logger LOGGER = LogManager.getLogger();
   private static final String __OBFID = "CL_00002459";

   public ChunkRenderWorker(ChunkRenderDispatcher var1) {
      this(var1, (RegionRenderCacheBuilder)null);
   }

   static void access$0(ChunkRenderWorker var0, ChunkCompileTaskGenerator var1) {
      var0.func_178473_b(var1);
   }

   static Logger access$1() {
      return LOGGER;
   }

   public ChunkRenderWorker(ChunkRenderDispatcher var1, RegionRenderCacheBuilder var2) {
      this.field_178477_b = var1;
      this.field_178478_c = var2;
   }

   protected void func_178474_a(ChunkCompileTaskGenerator var1) throws InterruptedException {
      var1.func_178540_f().lock();

      try {
         if (var1.func_178546_a() != ChunkCompileTaskGenerator.Status.PENDING) {
            if (!var1.func_178537_h()) {
               LOGGER.warn(String.valueOf((new StringBuilder("Chunk render task was ")).append(var1.func_178546_a()).append(" when I expected it to be pending; ignoring task")));
            }

            return;
         }

         var1.func_178535_a(ChunkCompileTaskGenerator.Status.COMPILING);
      } finally {
         var1.func_178540_f().unlock();
      }

      label275: {
         Entity var2 = Minecraft.getMinecraft().func_175606_aa();
         if (var2 == null) {
            var1.func_178542_e();
         } else {
            var1.func_178541_a(this.func_178475_b());
            float var3 = (float)var2.posX;
            float var4 = (float)var2.posY + var2.getEyeHeight();
            float var5 = (float)var2.posZ;
            ChunkCompileTaskGenerator.Type var6 = var1.func_178538_g();
            if (var6 == ChunkCompileTaskGenerator.Type.REBUILD_CHUNK) {
               var1.func_178536_b().func_178581_b(var3, var4, var5, var1);
            } else if (var6 == ChunkCompileTaskGenerator.Type.RESORT_TRANSPARENCY) {
               var1.func_178536_b().func_178570_a(var3, var4, var5, var1);
            }

            var1.func_178540_f().lock();
            boolean var15 = false;

            CompiledChunk var7;
            ArrayList var8;
            label247: {
               label246: {
                  EnumWorldBlockLayer[] var9;
                  int var10;
                  int var11;
                  try {
                     var15 = true;
                     if (var1.func_178546_a() != ChunkCompileTaskGenerator.Status.COMPILING) {
                        if (!var1.func_178537_h()) {
                           LOGGER.warn(String.valueOf((new StringBuilder("Chunk render task was ")).append(var1.func_178546_a()).append(" when I expected it to be compiling; aborting task")));
                        }

                        this.func_178473_b(var1);
                        var15 = false;
                        break label275;
                     }

                     var1.func_178535_a(ChunkCompileTaskGenerator.Status.UPLOADING);
                     var1.func_178540_f().unlock();
                     var7 = var1.func_178544_c();
                     var8 = Lists.newArrayList();
                     if (var6 != ChunkCompileTaskGenerator.Type.REBUILD_CHUNK) {
                        var15 = false;
                        break label246;
                     }

                     var9 = EnumWorldBlockLayer.values();
                     var10 = var9.length;
                     var11 = 0;
                     var15 = false;
                  } finally {
                     if (var15) {
                        var1.func_178540_f().unlock();
                     }
                  }

                  while(true) {
                     if (var11 >= var10) {
                        break label247;
                     }

                     EnumWorldBlockLayer var12 = var9[var11];
                     if (var7.func_178492_d(var12)) {
                        var8.add(this.field_178477_b.func_178503_a(var12, var1.func_178545_d().func_179038_a(var12), var1.func_178536_b(), var7));
                     }

                     ++var11;
                  }
               }

               if (var6 == ChunkCompileTaskGenerator.Type.RESORT_TRANSPARENCY) {
                  var8.add(this.field_178477_b.func_178503_a(EnumWorldBlockLayer.TRANSLUCENT, var1.func_178545_d().func_179038_a(EnumWorldBlockLayer.TRANSLUCENT), var1.func_178536_b(), var7));
               }
            }

            ListenableFuture var20 = Futures.allAsList(var8);
            var1.func_178539_a(new Runnable(this, var20) {
               private static final String __OBFID = "CL_00002458";
               final ChunkRenderWorker this$0;
               private final ListenableFuture val$var19;

               {
                  this.this$0 = var1;
                  this.val$var19 = var2;
               }

               public void run() {
                  this.val$var19.cancel(false);
               }
            });
            Futures.addCallback(var20, new FutureCallback(this, var1, var7) {
               private final ChunkCompileTaskGenerator val$p_178474_1_;
               private final CompiledChunk val$var7;
               final ChunkRenderWorker this$0;
               private static final String __OBFID = "CL_00002457";

               {
                  this.this$0 = var1;
                  this.val$p_178474_1_ = var2;
                  this.val$var7 = var3;
               }

               public void func_178481_a(List var1) {
                  ChunkRenderWorker.access$0(this.this$0, this.val$p_178474_1_);
                  this.val$p_178474_1_.func_178540_f().lock();

                  try {
                     if (this.val$p_178474_1_.func_178546_a() == ChunkCompileTaskGenerator.Status.UPLOADING) {
                        this.val$p_178474_1_.func_178535_a(ChunkCompileTaskGenerator.Status.DONE);
                        this.val$p_178474_1_.func_178540_f().unlock();
                        this.val$p_178474_1_.func_178536_b().func_178580_a(this.val$var7);
                        return;
                     }

                     if (!this.val$p_178474_1_.func_178537_h()) {
                        ChunkRenderWorker.access$1().warn(String.valueOf((new StringBuilder("Chunk render task was ")).append(this.val$p_178474_1_.func_178546_a()).append(" when I expected it to be uploading; aborting task")));
                     }
                  } finally {
                     this.val$p_178474_1_.func_178540_f().unlock();
                  }

               }

               public void onFailure(Throwable var1) {
                  ChunkRenderWorker.access$0(this.this$0, this.val$p_178474_1_);
                  if (!(var1 instanceof CancellationException) && !(var1 instanceof InterruptedException)) {
                     Minecraft.getMinecraft().crashed(CrashReport.makeCrashReport(var1, "Rendering chunk"));
                  }

               }

               public void onSuccess(Object var1) {
                  this.func_178481_a((List)var1);
               }
            });
         }

         return;
      }

      var1.func_178540_f().unlock();
   }

   private RegionRenderCacheBuilder func_178475_b() throws InterruptedException {
      return this.field_178478_c != null ? this.field_178478_c : this.field_178477_b.func_178515_c();
   }

   private void func_178473_b(ChunkCompileTaskGenerator var1) {
      if (this.field_178478_c == null) {
         this.field_178477_b.func_178512_a(var1.func_178545_d());
      }

   }

   public void run() {
      try {
         while(true) {
            this.func_178474_a(this.field_178477_b.func_178511_d());
         }
      } catch (InterruptedException var3) {
         LOGGER.debug("Stopping due to interrupt");
      } catch (Throwable var4) {
         CrashReport var2 = CrashReport.makeCrashReport(var4, "Batching chunks");
         Minecraft.getMinecraft().crashed(Minecraft.getMinecraft().addGraphicsAndWorldToCrashReport(var2));
      }

   }
}
