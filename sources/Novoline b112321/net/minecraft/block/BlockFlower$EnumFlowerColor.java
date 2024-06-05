package net.minecraft.block;

import net.minecraft.block.BlockFlower;
import net.minecraft.init.Blocks;

public enum BlockFlower$EnumFlowerColor {
   YELLOW,
   RED;

   private static final BlockFlower$EnumFlowerColor[] $VALUES = new BlockFlower$EnumFlowerColor[]{YELLOW, RED};

   public BlockFlower getBlock() {
      return this == YELLOW?Blocks.yellow_flower:Blocks.red_flower;
   }
}
