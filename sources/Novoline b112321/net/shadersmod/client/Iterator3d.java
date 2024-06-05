package net.shadersmod.client;

import java.util.Iterator;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;
import net.optifine.BlockPosM;
import net.shadersmod.client.IteratorAxis;
import net.shadersmod.client.ShaderOption;

public class Iterator3d implements Iterator {
   private IteratorAxis iteratorAxis;
   private BlockPosM blockPos;
   private int axis;
   private int kX;
   private int kY;
   private int kZ;
   private static final int AXIS_X = 0;
   private static final int AXIS_Y = 1;
   private static final int AXIS_Z = 2;

   public Iterator3d(BlockPos var1, BlockPos var2, int var3, int var4) {
      ShaderOption.p();
      this.blockPos = new BlockPosM(0, 0, 0);
      this.axis = 0;
      boolean var6 = var1.getX() > var2.getX();
      boolean var7 = var1.getY() > var2.getY();
      boolean var8 = var1.getZ() > var2.getZ();
      var1 = this.reverseCoord(var1, var6, var7, var8);
      var2 = this.reverseCoord(var2, var6, var7, var8);
      this.kX = var6?-1:1;
      this.kY = var7?-1:1;
      this.kZ = var8?-1:1;
      Vec3 var9 = new Vec3((double)(var2.getX() - var1.getX()), (double)(var2.getY() - var1.getY()), (double)(var2.getZ() - var1.getZ()));
      Vec3 var10 = var9.normalize();
      Vec3 var11 = new Vec3(1.0D, 0.0D, 0.0D);
      double var12 = var10.dotProduct(var11);
      double var14 = Math.abs(var12);
      Vec3 var16 = new Vec3(0.0D, 1.0D, 0.0D);
      double var17 = var10.dotProduct(var16);
      double var19 = Math.abs(var17);
      Vec3 var21 = new Vec3(0.0D, 0.0D, 1.0D);
      double var22 = var10.dotProduct(var21);
      double var24 = Math.abs(var22);
      if(var24 >= var19 && var24 >= var14) {
         this.axis = 2;
         BlockPos var26 = new BlockPos(var1.getZ(), var1.getY() - var3, var1.getX() - var4);
         BlockPos var27 = new BlockPos(var2.getZ(), var1.getY() + var3 + 1, var1.getX() + var4 + 1);
         int var28 = var2.getZ() - var1.getZ();
         double var29 = (double)(var2.getY() - var1.getY()) / (1.0D * (double)var28);
         double var31 = (double)(var2.getX() - var1.getX()) / (1.0D * (double)var28);
         this.iteratorAxis = new IteratorAxis(var26, var27, var29, var31);
      }

      if(var19 >= var14 && var19 >= var24) {
         this.axis = 1;
         BlockPos var35 = new BlockPos(var1.getY(), var1.getX() - var3, var1.getZ() - var4);
         BlockPos var37 = new BlockPos(var2.getY(), var1.getX() + var3 + 1, var1.getZ() + var4 + 1);
         int var39 = var2.getY() - var1.getY();
         double var41 = (double)(var2.getX() - var1.getX()) / (1.0D * (double)var39);
         double var43 = (double)(var2.getZ() - var1.getZ()) / (1.0D * (double)var39);
         this.iteratorAxis = new IteratorAxis(var35, var37, var41, var43);
      }

      this.axis = 0;
      BlockPos var36 = new BlockPos(var1.getX(), var1.getY() - var3, var1.getZ() - var4);
      BlockPos var38 = new BlockPos(var2.getX(), var1.getY() + var3 + 1, var1.getZ() + var4 + 1);
      int var40 = var2.getX() - var1.getX();
      double var42 = (double)(var2.getY() - var1.getY()) / (1.0D * (double)var40);
      double var44 = (double)(var2.getZ() - var1.getZ()) / (1.0D * (double)var40);
      this.iteratorAxis = new IteratorAxis(var36, var38, var42, var44);
   }

   private BlockPos reverseCoord(BlockPos var1, boolean var2, boolean var3, boolean var4) {
      String[] var5 = ShaderOption.p();
      if(var2) {
         var1 = new BlockPos(-var1.getX(), var1.getY(), var1.getZ());
      }

      if(var3) {
         var1 = new BlockPos(var1.getX(), -var1.getY(), var1.getZ());
      }

      if(var4) {
         var1 = new BlockPos(var1.getX(), var1.getY(), -var1.getZ());
      }

      return var1;
   }

   public boolean hasNext() {
      return this.iteratorAxis.hasNext();
   }

   public BlockPos next() {
      ShaderOption.p();
      BlockPos var2 = this.iteratorAxis.next();
      switch(this.axis) {
      case 0:
         this.blockPos.setXyz(var2.getX() * this.kX, var2.getY() * this.kY, var2.getZ() * this.kZ);
         return this.blockPos;
      case 1:
         this.blockPos.setXyz(var2.getY() * this.kX, var2.getX() * this.kY, var2.getZ() * this.kZ);
         return this.blockPos;
      case 2:
         this.blockPos.setXyz(var2.getZ() * this.kX, var2.getY() * this.kY, var2.getX() * this.kZ);
         return this.blockPos;
      default:
         this.blockPos.setXyz(var2.getX() * this.kX, var2.getY() * this.kY, var2.getZ() * this.kZ);
         return this.blockPos;
      }
   }

   public void remove() {
      throw new RuntimeException("Not supported");
   }

   public static void main(String[] var0) {
      ShaderOption.p();
      BlockPos var2 = new BlockPos(10, 20, 30);
      BlockPos var3 = new BlockPos(30, 40, 20);
      Iterator3d var4 = new Iterator3d(var2, var3, 1, 1);
      if(var4.hasNext()) {
         BlockPos var5 = var4.next();
         System.out.println("" + var5);
      }

   }

   private static RuntimeException a(RuntimeException var0) {
      return var0;
   }
}
