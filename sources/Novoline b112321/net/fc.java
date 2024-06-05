package net;

import java.util.IdentityHashMap;
import java.util.Map;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import optifine.Config;

public class fc extends Block {
   protected boolean P;
   private static final Map Q = new IdentityHashMap();

   protected fc(Material var1, boolean var2) {
      super(var1);
      this.P = var2;
   }

   public boolean isOpaqueCube() {
      return false;
   }

   public boolean shouldSideBeRendered(IBlockAccess var1, BlockPos var2, EnumFacing var3) {
      return (!Config.a7() || var1.getBlockState(var2).getBlock() != this) && super.shouldSideBeRendered(var1, var2, var3);
   }

   public static void a(Block var0, int var1) {
      if(!Q.containsKey(var0)) {
         Q.put(var0, Integer.valueOf(var0.getLightOpacity()));
      }

      var0.setLightOpacity(var1);
   }

   public static void a(Block var0) {
      if(Q.containsKey(var0)) {
         int var1 = ((Integer)Q.get(var0)).intValue();
         a(var0, var1);
      }

   }
}
