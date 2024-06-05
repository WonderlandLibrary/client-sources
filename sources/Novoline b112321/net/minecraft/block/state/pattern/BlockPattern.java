package net.minecraft.block.state.pattern;

import com.google.common.base.Predicate;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.LoadingCache;
import net.minecraft.block.state.pattern.BlockPattern$CacheLoader;
import net.minecraft.block.state.pattern.BlockPattern$PatternHelper;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3i;
import net.minecraft.world.World;

public class BlockPattern {
   private final Predicate[][][] blockMatches;
   private final int fingerLength;
   private final int thumbLength;
   private final int palmLength;

   public BlockPattern(Predicate[][][] var1) {
      this.blockMatches = var1;
      this.fingerLength = var1.length;
      if(this.fingerLength > 0) {
         this.thumbLength = var1[0].length;
         if(this.thumbLength > 0) {
            this.palmLength = var1[0][0].length;
         } else {
            this.palmLength = 0;
         }
      } else {
         this.thumbLength = 0;
         this.palmLength = 0;
      }

   }

   public int getThumbLength() {
      return this.thumbLength;
   }

   public int getPalmLength() {
      return this.palmLength;
   }

   private BlockPattern$PatternHelper checkPatternAt(BlockPos var1, EnumFacing var2, EnumFacing var3, LoadingCache var4) {
      for(int var5 = 0; var5 < this.palmLength; ++var5) {
         for(int var6 = 0; var6 < this.thumbLength; ++var6) {
            for(int var7 = 0; var7 < this.fingerLength; ++var7) {
               if(!this.blockMatches[var7][var6][var5].apply(var4.getUnchecked(translateOffset(var1, var2, var3, var5, var6, var7)))) {
                  return null;
               }
            }
         }
      }

      return new BlockPattern$PatternHelper(var1, var2, var3, var4, this.palmLength, this.thumbLength, this.fingerLength);
   }

   public BlockPattern$PatternHelper match(World var1, BlockPos var2) {
      LoadingCache var3 = func_181627_a(var1, false);
      int var4 = Math.max(Math.max(this.palmLength, this.thumbLength), this.fingerLength);

      for(BlockPos var6 : BlockPos.getAllInBox(var2, var2.a(var4 - 1, var4 - 1, var4 - 1))) {
         for(EnumFacing var10 : EnumFacing.values()) {
            for(EnumFacing var14 : EnumFacing.values()) {
               if(var14 != var10 && var14 != var10.getOpposite()) {
                  BlockPattern$PatternHelper var15 = this.checkPatternAt(var6, var10, var14, var3);
                  return var15;
               }
            }
         }
      }

      return null;
   }

   public static LoadingCache func_181627_a(World var0, boolean var1) {
      return CacheBuilder.newBuilder().build(new BlockPattern$CacheLoader(var0, var1));
   }

   protected static BlockPos translateOffset(BlockPos var0, EnumFacing var1, EnumFacing var2, int var3, int var4, int var5) {
      if(var1 != var2 && var1 != var2.getOpposite()) {
         Vec3i var6 = new Vec3i(var1.getFrontOffsetX(), var1.getFrontOffsetY(), var1.getFrontOffsetZ());
         Vec3i var7 = new Vec3i(var2.getFrontOffsetX(), var2.getFrontOffsetY(), var2.getFrontOffsetZ());
         Vec3i var8 = var6.crossProduct(var7);
         return var0.a(var7.getX() * -var4 + var8.getX() * var3 + var6.getX() * var5, var7.getY() * -var4 + var8.getY() * var3 + var6.getY() * var5, var7.getZ() * -var4 + var8.getZ() * var3 + var6.getZ() * var5);
      } else {
         throw new IllegalArgumentException("Invalid forwards & up combination");
      }
   }

   private static IllegalArgumentException a(IllegalArgumentException var0) {
      return var0;
   }
}
