package net.minecraft.block.state.pattern;

import com.google.common.base.Predicate;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;

public class BlockHelper implements Predicate {
   private static Minecraft mc;
   private final Block block;

   private BlockHelper(Block var1) {
      this.block = var1;
   }

   public static BlockHelper forBlock(Block var0) {
      return new BlockHelper(var0);
   }

   public boolean apply(IBlockState var1) {
      return var1.getBlock() == this.block;
   }
}
