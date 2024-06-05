package net.minecraft.item;

import com.google.common.collect.Sets;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item$ToolMaterial;
import net.minecraft.item.ItemTool;

public class ItemSpade extends ItemTool {
   private static final Set EFFECTIVE_ON = Sets.newHashSet(new Block[]{Blocks.clay, Blocks.dirt, Blocks.farmland, Blocks.grass, Blocks.gravel, Blocks.mycelium, Blocks.sand, Blocks.snow, Blocks.snow_layer, Blocks.soul_sand});

   public ItemSpade(Item$ToolMaterial var1) {
      super(1.0F, var1, EFFECTIVE_ON);
   }

   public boolean canHarvestBlock(Block var1) {
      return var1 == Blocks.snow_layer || var1 == Blocks.snow;
   }
}
