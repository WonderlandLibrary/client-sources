package net;

import com.google.common.util.concurrent.FutureCallback;
import java.util.List;
import java.util.concurrent.CancellationException;
import net.Kj;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.chunk.ChunkCompileTaskGenerator;
import net.minecraft.client.renderer.chunk.CompiledChunk;
import net.minecraft.crash.CrashReport;

class aU8 implements FutureCallback {
   final ChunkCompileTaskGenerator c;
   final CompiledChunk b;
   final Kj a;

   aU8(Kj var1, ChunkCompileTaskGenerator var2, CompiledChunk var3) {
      this.a = var1;
      this.c = var2;
      this.b = var3;
   }

   public void a(List param1) {
      // $FF: Couldn't be decompiled
   }

   public void onFailure(Throwable var1) {
      Kj.a(this.a, this.c);
      if(!(var1 instanceof CancellationException) && !(var1 instanceof InterruptedException)) {
         Minecraft.getInstance().crashed(CrashReport.makeCrashReport(var1, "Rendering chunk"));
      }

   }

   private static CancellationException a(CancellationException var0) {
      return var0;
   }
}
