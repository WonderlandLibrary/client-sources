package net;

import net.minecraft.util.BlockPos;

public class df {
   int c;
   int e;
   int b;
   int a;
   int d;

   public df(int var1, int var2, int var3, int var4) {
      this.c = var1;
      this.b = var2;
      this.a = var3;
      this.d = var4;
   }

   public int b() {
      return this.c;
   }

   public int a() {
      return this.b;
   }

   public int c() {
      return this.a + this.d;
   }

   public BlockPos a(int var1) {
      return new BlockPos(this.c, var1, this.b);
   }
}
