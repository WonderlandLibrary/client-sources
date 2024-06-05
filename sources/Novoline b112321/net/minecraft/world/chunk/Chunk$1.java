package net.minecraft.world.chunk;

import java.util.concurrent.Callable;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.util.BlockPos;
import net.minecraft.world.chunk.Chunk;

class Chunk$1 implements Callable {
   final int val$x;
   final int val$y;
   final int val$z;
   final Chunk this$0;

   Chunk$1(Chunk var1, int var2, int var3, int var4) {
      this.this$0 = var1;
      this.val$x = var2;
      this.val$y = var3;
      this.val$z = var4;
   }

   public String call() throws Exception {
      return CrashReportCategory.getCoordinateInfo(new BlockPos(this.this$0.xPosition * 16 + this.val$x, this.val$y, this.this$0.zPosition * 16 + this.val$z));
   }
}
