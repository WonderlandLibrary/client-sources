package net.minecraft.item;

import net.minecraft.block.Block;

public class ItemPiston extends ItemBlock {
   public ItemPiston(Block p_i45348_1_) {
      super(p_i45348_1_);
   }

   public int getMetadata(int damage) {
      return 7;
   }
}
