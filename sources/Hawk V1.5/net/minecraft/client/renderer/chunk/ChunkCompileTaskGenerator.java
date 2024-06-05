package net.minecraft.client.renderer.chunk;

import com.google.common.collect.Lists;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import net.minecraft.client.renderer.RegionRenderCacheBuilder;

public class ChunkCompileTaskGenerator {
   private CompiledChunk field_178547_f;
   private static final String __OBFID = "CL_00002466";
   private ChunkCompileTaskGenerator.Status field_178548_g;
   private RegionRenderCacheBuilder field_178550_e;
   private final RenderChunk field_178553_a;
   private final ReentrantLock field_178551_b = new ReentrantLock();
   private final ChunkCompileTaskGenerator.Type field_178549_d;
   private boolean field_178554_h;
   private final List field_178552_c = Lists.newArrayList();

   public RenderChunk func_178536_b() {
      return this.field_178553_a;
   }

   public RegionRenderCacheBuilder func_178545_d() {
      return this.field_178550_e;
   }

   public void func_178543_a(CompiledChunk var1) {
      this.field_178547_f = var1;
   }

   public ChunkCompileTaskGenerator.Type func_178538_g() {
      return this.field_178549_d;
   }

   public ChunkCompileTaskGenerator.Status func_178546_a() {
      return this.field_178548_g;
   }

   public ReentrantLock func_178540_f() {
      return this.field_178551_b;
   }

   public ChunkCompileTaskGenerator(RenderChunk var1, ChunkCompileTaskGenerator.Type var2) {
      this.field_178548_g = ChunkCompileTaskGenerator.Status.PENDING;
      this.field_178553_a = var1;
      this.field_178549_d = var2;
   }

   public void func_178539_a(Runnable var1) {
      this.field_178551_b.lock();

      try {
         this.field_178552_c.add(var1);
         if (this.field_178554_h) {
            var1.run();
         }
      } finally {
         this.field_178551_b.unlock();
      }

   }

   public void func_178542_e() {
      this.field_178551_b.lock();

      try {
         if (this.field_178549_d == ChunkCompileTaskGenerator.Type.REBUILD_CHUNK && this.field_178548_g != ChunkCompileTaskGenerator.Status.DONE) {
            this.field_178553_a.func_178575_a(true);
         }

         this.field_178554_h = true;
         this.field_178548_g = ChunkCompileTaskGenerator.Status.DONE;
         Iterator var1 = this.field_178552_c.iterator();

         while(var1.hasNext()) {
            Runnable var2 = (Runnable)var1.next();
            var2.run();
         }

         this.field_178551_b.unlock();
      } finally {
         this.field_178551_b.unlock();
      }
   }

   public boolean func_178537_h() {
      return this.field_178554_h;
   }

   public void func_178535_a(ChunkCompileTaskGenerator.Status var1) {
      this.field_178551_b.lock();

      try {
         this.field_178548_g = var1;
         this.field_178551_b.unlock();
      } finally {
         this.field_178551_b.unlock();
      }
   }

   public CompiledChunk func_178544_c() {
      return this.field_178547_f;
   }

   public void func_178541_a(RegionRenderCacheBuilder var1) {
      this.field_178550_e = var1;
   }

   public static enum Status {
      DONE("DONE", 3, "DONE", 3),
      PENDING("PENDING", 0, "PENDING", 0),
      UPLOADING("UPLOADING", 2, "UPLOADING", 2);

      private static final ChunkCompileTaskGenerator.Status[] $VALUES = new ChunkCompileTaskGenerator.Status[]{PENDING, COMPILING, UPLOADING, DONE};
      private static final String __OBFID = "CL_00002465";
      private static final ChunkCompileTaskGenerator.Status[] ENUM$VALUES = new ChunkCompileTaskGenerator.Status[]{PENDING, COMPILING, UPLOADING, DONE};
      COMPILING("COMPILING", 1, "COMPILING", 1);

      private Status(String var3, int var4, String var5, int var6) {
      }
   }

   public static enum Type {
      RESORT_TRANSPARENCY("RESORT_TRANSPARENCY", 1, "RESORT_TRANSPARENCY", 1),
      REBUILD_CHUNK("REBUILD_CHUNK", 0, "REBUILD_CHUNK", 0);

      private static final String __OBFID = "CL_00002464";
      private static final ChunkCompileTaskGenerator.Type[] $VALUES = new ChunkCompileTaskGenerator.Type[]{REBUILD_CHUNK, RESORT_TRANSPARENCY};
      private static final ChunkCompileTaskGenerator.Type[] ENUM$VALUES = new ChunkCompileTaskGenerator.Type[]{REBUILD_CHUNK, RESORT_TRANSPARENCY};

      private Type(String var3, int var4, String var5, int var6) {
      }
   }
}
