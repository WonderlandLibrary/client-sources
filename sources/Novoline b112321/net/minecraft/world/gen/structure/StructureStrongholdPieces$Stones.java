package net.minecraft.world.gen.structure;

import java.util.Random;
import net.aXp;
import net.minecraft.block.BlockStoneBrick;
import net.minecraft.init.Blocks;
import net.minecraft.world.gen.structure.StructureComponent$BlockSelector;
import net.minecraft.world.gen.structure.StructureStrongholdPieces$1;

class StructureStrongholdPieces$Stones extends StructureComponent$BlockSelector {
   private StructureStrongholdPieces$Stones() {
   }

   public void selectBlocks(Random var1, int var2, int var3, int var4, boolean var5) {
      float var6 = var1.nextFloat();
      if(var6 < 0.2F) {
         this.blockstate = Blocks.stonebrick.getStateFromMeta(BlockStoneBrick.CRACKED_META);
      } else if(var6 < 0.5F) {
         this.blockstate = Blocks.stonebrick.getStateFromMeta(BlockStoneBrick.MOSSY_META);
      } else if(var6 < 0.55F) {
         this.blockstate = Blocks.monster_egg.getStateFromMeta(aXp.STONEBRICK.a());
      } else {
         this.blockstate = Blocks.stonebrick.getDefaultState();
      }

   }

   StructureStrongholdPieces$Stones(StructureStrongholdPieces$1 var1) {
      this();
   }
}
