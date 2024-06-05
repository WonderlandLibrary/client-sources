package net;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.world.World;

public class aJ2 {
   public static IBlockState a(EntityFallingBlock var0) {
      return var0.getBlock();
   }

   public static World b(EntityFallingBlock var0) {
      return var0.getWorldObj();
   }

   public static void a(EntityFallingBlock var0, boolean var1) {
      var0.setHurtEntities(var1);
   }
}
