package net.minecraft.item;

import com.google.common.collect.Sets;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

public class ItemSpade extends ItemTool {
   private static final String __OBFID = "CL_00000063";
   private static final Set field_150916_c;

   static {
      field_150916_c = Sets.newHashSet(new Block[]{Blocks.clay, Blocks.dirt, Blocks.farmland, Blocks.grass, Blocks.gravel, Blocks.mycelium, Blocks.sand, Blocks.snow, Blocks.snow_layer, Blocks.soul_sand});
   }

   public boolean canHarvestBlock(Block var1) {
      return var1 == Blocks.snow_layer ? true : var1 == Blocks.snow;
   }

   public ItemSpade(Item.ToolMaterial var1) {
      super(1.0F, var1, field_150916_c);
   }
}
