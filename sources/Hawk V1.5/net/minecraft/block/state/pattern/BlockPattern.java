package net.minecraft.block.state.pattern;

import com.google.common.base.Predicate;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.LoadingCache;
import java.util.Iterator;
import net.minecraft.block.state.BlockWorldState;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3i;
import net.minecraft.world.World;

public class BlockPattern {
   private final int field_177688_c;
   private final int field_177686_d;
   private static final String __OBFID = "CL_00002024";
   private final Predicate[][][] field_177689_a;
   private final int field_177687_b;

   public int func_177684_c() {
      return this.field_177686_d;
   }

   public BlockPattern(Predicate[][][] var1) {
      this.field_177689_a = var1;
      this.field_177687_b = var1.length;
      if (this.field_177687_b > 0) {
         this.field_177688_c = var1[0].length;
         if (this.field_177688_c > 0) {
            this.field_177686_d = var1[0][0].length;
         } else {
            this.field_177686_d = 0;
         }
      } else {
         this.field_177688_c = 0;
         this.field_177686_d = 0;
      }

   }

   public int func_177685_b() {
      return this.field_177688_c;
   }

   private BlockPattern.PatternHelper func_177682_a(BlockPos var1, EnumFacing var2, EnumFacing var3, LoadingCache var4) {
      for(int var5 = 0; var5 < this.field_177686_d; ++var5) {
         for(int var6 = 0; var6 < this.field_177688_c; ++var6) {
            for(int var7 = 0; var7 < this.field_177687_b; ++var7) {
               if (!this.field_177689_a[var7][var6][var5].apply(var4.getUnchecked(func_177683_a(var1, var2, var3, var5, var6, var7)))) {
                  return null;
               }
            }
         }
      }

      return new BlockPattern.PatternHelper(var1, var2, var3, var4);
   }

   protected static BlockPos func_177683_a(BlockPos var0, EnumFacing var1, EnumFacing var2, int var3, int var4, int var5) {
      if (var1 != var2 && var1 != var2.getOpposite()) {
         Vec3i var6 = new Vec3i(var1.getFrontOffsetX(), var1.getFrontOffsetY(), var1.getFrontOffsetZ());
         Vec3i var7 = new Vec3i(var2.getFrontOffsetX(), var2.getFrontOffsetY(), var2.getFrontOffsetZ());
         Vec3i var8 = var6.crossProduct(var7);
         return var0.add(var7.getX() * -var4 + var8.getX() * var3 + var6.getX() * var5, var7.getY() * -var4 + var8.getY() * var3 + var6.getY() * var5, var7.getZ() * -var4 + var8.getZ() * var3 + var6.getZ() * var5);
      } else {
         throw new IllegalArgumentException("Invalid forwards & up combination");
      }
   }

   public BlockPattern.PatternHelper func_177681_a(World var1, BlockPos var2) {
      LoadingCache var3 = CacheBuilder.newBuilder().build(new BlockPattern.CacheLoader(var1));
      int var4 = Math.max(Math.max(this.field_177686_d, this.field_177688_c), this.field_177687_b);
      Iterator var5 = BlockPos.getAllInBox(var2, var2.add(var4 - 1, var4 - 1, var4 - 1)).iterator();

      while(var5.hasNext()) {
         BlockPos var6 = (BlockPos)var5.next();
         EnumFacing[] var7 = EnumFacing.values();
         int var8 = var7.length;

         for(int var9 = 0; var9 < var8; ++var9) {
            EnumFacing var10 = var7[var9];
            EnumFacing[] var11 = EnumFacing.values();
            int var12 = var11.length;

            for(int var13 = 0; var13 < var12; ++var13) {
               EnumFacing var14 = var11[var13];
               if (var14 != var10 && var14 != var10.getOpposite()) {
                  BlockPattern.PatternHelper var15 = this.func_177682_a(var6, var10, var14, var3);
                  if (var15 != null) {
                     return var15;
                  }
               }
            }
         }
      }

      return null;
   }

   public static class PatternHelper {
      private static final String __OBFID = "CL_00002022";
      private final EnumFacing field_177672_b;
      private final LoadingCache field_177671_d;
      private final EnumFacing field_177673_c;
      private final BlockPos field_177674_a;

      public EnumFacing func_177668_c() {
         return this.field_177673_c;
      }

      public BlockWorldState func_177670_a(int var1, int var2, int var3) {
         return (BlockWorldState)this.field_177671_d.getUnchecked(BlockPattern.func_177683_a(this.field_177674_a, this.func_177669_b(), this.func_177668_c(), var1, var2, var3));
      }

      public EnumFacing func_177669_b() {
         return this.field_177672_b;
      }

      public PatternHelper(BlockPos var1, EnumFacing var2, EnumFacing var3, LoadingCache var4) {
         this.field_177674_a = var1;
         this.field_177672_b = var2;
         this.field_177673_c = var3;
         this.field_177671_d = var4;
      }
   }

   static class CacheLoader extends com.google.common.cache.CacheLoader {
      private static final String __OBFID = "CL_00002023";
      private final World field_177680_a;

      public Object load(Object var1) {
         return this.func_177679_a((BlockPos)var1);
      }

      public CacheLoader(World var1) {
         this.field_177680_a = var1;
      }

      public BlockWorldState func_177679_a(BlockPos var1) {
         return new BlockWorldState(this.field_177680_a, var1);
      }
   }
}
