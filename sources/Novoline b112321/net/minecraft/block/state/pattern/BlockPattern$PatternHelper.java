package net.minecraft.block.state.pattern;

import com.google.common.base.Objects;
import com.google.common.cache.LoadingCache;
import net.minecraft.block.state.BlockWorldState;
import net.minecraft.block.state.pattern.BlockPattern;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class BlockPattern$PatternHelper {
   private final BlockPos pos;
   private final EnumFacing finger;
   private final EnumFacing thumb;
   private final LoadingCache lcache;
   private final int field_181120_e;
   private final int field_181121_f;
   private final int field_181122_g;

   public BlockPattern$PatternHelper(BlockPos var1, EnumFacing var2, EnumFacing var3, LoadingCache var4, int var5, int var6, int var7) {
      this.pos = var1;
      this.finger = var2;
      this.thumb = var3;
      this.lcache = var4;
      this.field_181120_e = var5;
      this.field_181121_f = var6;
      this.field_181122_g = var7;
   }

   public BlockPos func_181117_a() {
      return this.pos;
   }

   public EnumFacing getFinger() {
      return this.finger;
   }

   public EnumFacing getThumb() {
      return this.thumb;
   }

   public int func_181118_d() {
      return this.field_181120_e;
   }

   public int func_181119_e() {
      return this.field_181121_f;
   }

   public BlockWorldState translateOffset(int var1, int var2, int var3) {
      return (BlockWorldState)this.lcache.getUnchecked(BlockPattern.translateOffset(this.pos, this.getFinger(), this.getThumb(), var1, var2, var3));
   }

   public String toString() {
      return Objects.toStringHelper(this).add("up", this.thumb).add("forwards", this.finger).add("frontTopLeft", this.pos).toString();
   }
}
