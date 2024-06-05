package net;

import java.util.Iterator;
import java.util.NoSuchElementException;
import net.minecraft.util.BlockPos;
import optifine.BlockPosM;
import shadersmod.client.ShaderOption;

public class a8S implements Iterator {
   private double j;
   private double g;
   private int b;
   private int m;
   private double e;
   private double i;
   private double l;
   private double c;
   private int d;
   private double a;
   private double f;
   private BlockPosM h = new BlockPosM(0, 0, 0);
   private boolean k;

   public a8S(BlockPos var1, BlockPos var2, double var3, double var5) {
      this.j = var3;
      this.g = var5;
      this.b = var1.getX();
      ShaderOption.p();
      this.m = var2.getX();
      this.e = (double)var1.getY();
      this.i = (double)var2.getY() - 0.5D;
      this.l = (double)var1.getZ();
      this.c = (double)var2.getZ() - 0.5D;
      this.d = this.b;
      this.a = this.e;
      this.f = this.l;
      this.k = this.d < this.m && this.a < this.i && this.f < this.c;
   }

   public boolean hasNext() {
      return this.k;
   }

   public BlockPos a() {
      String[] var1 = ShaderOption.p();
      if(!this.k) {
         throw new NoSuchElementException();
      } else {
         this.h.setXyz((double)this.d, this.a, this.f);
         this.b();
         this.k = this.d < this.m && this.a < this.i && this.f < this.c;
         return this.h;
      }
   }

   private void b() {
      ShaderOption.p();
      ++this.f;
      if(this.f >= this.c) {
         this.f = this.l;
         ++this.a;
         if(this.a >= this.i) {
            this.a = this.e;
            this.e += this.j;
            this.i += this.j;
            this.a = this.e;
            this.l += this.g;
            this.c += this.g;
            this.f = this.l;
            ++this.d;
         }
      }

   }

   public void remove() {
      throw new RuntimeException("Not implemented");
   }

   private static NoSuchElementException a(NoSuchElementException var0) {
      return var0;
   }
}
