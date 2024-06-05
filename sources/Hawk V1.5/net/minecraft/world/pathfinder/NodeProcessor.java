package net.minecraft.world.pathfinder;

import net.minecraft.entity.Entity;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.IntHashMap;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;

public abstract class NodeProcessor {
   private static final String __OBFID = "CL_00001967";
   protected int field_176165_d;
   protected int field_176166_e;
   protected IBlockAccess field_176169_a;
   protected int field_176168_c;
   protected IntHashMap field_176167_b = new IntHashMap();

   public abstract PathPoint func_176160_a(Entity var1, double var2, double var4, double var6);

   protected PathPoint func_176159_a(int var1, int var2, int var3) {
      int var4 = PathPoint.makeHash(var1, var2, var3);
      PathPoint var5 = (PathPoint)this.field_176167_b.lookup(var4);
      if (var5 == null) {
         var5 = new PathPoint(var1, var2, var3);
         this.field_176167_b.addKey(var4, var5);
      }

      return var5;
   }

   public void func_176163_a() {
   }

   public void func_176162_a(IBlockAccess var1, Entity var2) {
      this.field_176169_a = var1;
      this.field_176167_b.clearMap();
      this.field_176168_c = MathHelper.floor_float(var2.width + 1.0F);
      this.field_176165_d = MathHelper.floor_float(var2.height + 1.0F);
      this.field_176166_e = MathHelper.floor_float(var2.width + 1.0F);
   }

   public abstract PathPoint func_176161_a(Entity var1);

   public abstract int func_176164_a(PathPoint[] var1, Entity var2, PathPoint var3, PathPoint var4, float var5);
}
