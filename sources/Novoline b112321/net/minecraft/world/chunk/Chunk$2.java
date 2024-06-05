package net.minecraft.world.chunk;

import java.util.concurrent.Callable;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.util.BlockPos;
import net.minecraft.world.chunk.Chunk;

class Chunk$2 implements Callable {
   final BlockPos val$pos;
   final Chunk this$0;

   Chunk$2(Chunk var1, BlockPos var2) {
      this.this$0 = var1;
      this.val$pos = var2;
   }

   public String call() throws Exception {
      return CrashReportCategory.getCoordinateInfo(this.val$pos);
   }
}
