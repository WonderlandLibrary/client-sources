package net.minecraft.world.gen.structure;

import java.util.Random;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureOceanMonumentPieces$Piece;
import net.minecraft.world.gen.structure.StructureOceanMonumentPieces$RoomDefinition;

public class StructureOceanMonumentPieces$MonumentCoreRoom extends StructureOceanMonumentPieces$Piece {
   public StructureOceanMonumentPieces$MonumentCoreRoom() {
   }

   public StructureOceanMonumentPieces$MonumentCoreRoom(EnumFacing var1, StructureOceanMonumentPieces$RoomDefinition var2, Random var3) {
      super(1, var1, var2, 2, 2, 2);
   }

   public boolean addComponentParts(World var1, Random var2, StructureBoundingBox var3) {
      this.func_175819_a(var1, var3, 1, 8, 0, 14, 8, 14, field_175828_a);
      int var4 = 7;
      IBlockState var5 = field_175826_b;
      this.fillWithBlocks(var1, var3, 0, var4, 0, 0, var4, 15, var5, var5, false);
      this.fillWithBlocks(var1, var3, 15, var4, 0, 15, var4, 15, var5, var5, false);
      this.fillWithBlocks(var1, var3, 1, var4, 0, 15, var4, 0, var5, var5, false);
      this.fillWithBlocks(var1, var3, 1, var4, 15, 14, var4, 15, var5, var5, false);

      for(var4 = 1; var4 <= 6; ++var4) {
         var5 = field_175826_b;
         if(var4 == 2 || var4 == 6) {
            var5 = field_175828_a;
         }

         for(int var6 = 0; var6 <= 15; var6 += 15) {
            this.fillWithBlocks(var1, var3, var6, var4, 0, var6, var4, 1, var5, var5, false);
            this.fillWithBlocks(var1, var3, var6, var4, 6, var6, var4, 9, var5, var5, false);
            this.fillWithBlocks(var1, var3, var6, var4, 14, var6, var4, 15, var5, var5, false);
         }

         this.fillWithBlocks(var1, var3, 1, var4, 0, 1, var4, 0, var5, var5, false);
         this.fillWithBlocks(var1, var3, 6, var4, 0, 9, var4, 0, var5, var5, false);
         this.fillWithBlocks(var1, var3, 14, var4, 0, 14, var4, 0, var5, var5, false);
         this.fillWithBlocks(var1, var3, 1, var4, 15, 14, var4, 15, var5, var5, false);
      }

      this.fillWithBlocks(var1, var3, 6, 3, 6, 9, 6, 9, field_175827_c, field_175827_c, false);
      this.fillWithBlocks(var1, var3, 7, 4, 7, 8, 5, 8, Blocks.gold_block.getDefaultState(), Blocks.gold_block.getDefaultState(), false);

      for(var4 = 3; var4 <= 6; var4 += 3) {
         for(int var10 = 6; var10 <= 9; var10 += 3) {
            this.setBlockState(var1, field_175825_e, var10, var4, 6, var3);
            this.setBlockState(var1, field_175825_e, var10, var4, 9, var3);
         }
      }

      this.fillWithBlocks(var1, var3, 5, 1, 6, 5, 2, 6, field_175826_b, field_175826_b, false);
      this.fillWithBlocks(var1, var3, 5, 1, 9, 5, 2, 9, field_175826_b, field_175826_b, false);
      this.fillWithBlocks(var1, var3, 10, 1, 6, 10, 2, 6, field_175826_b, field_175826_b, false);
      this.fillWithBlocks(var1, var3, 10, 1, 9, 10, 2, 9, field_175826_b, field_175826_b, false);
      this.fillWithBlocks(var1, var3, 6, 1, 5, 6, 2, 5, field_175826_b, field_175826_b, false);
      this.fillWithBlocks(var1, var3, 9, 1, 5, 9, 2, 5, field_175826_b, field_175826_b, false);
      this.fillWithBlocks(var1, var3, 6, 1, 10, 6, 2, 10, field_175826_b, field_175826_b, false);
      this.fillWithBlocks(var1, var3, 9, 1, 10, 9, 2, 10, field_175826_b, field_175826_b, false);
      this.fillWithBlocks(var1, var3, 5, 2, 5, 5, 6, 5, field_175826_b, field_175826_b, false);
      this.fillWithBlocks(var1, var3, 5, 2, 10, 5, 6, 10, field_175826_b, field_175826_b, false);
      this.fillWithBlocks(var1, var3, 10, 2, 5, 10, 6, 5, field_175826_b, field_175826_b, false);
      this.fillWithBlocks(var1, var3, 10, 2, 10, 10, 6, 10, field_175826_b, field_175826_b, false);
      this.fillWithBlocks(var1, var3, 5, 7, 1, 5, 7, 6, field_175826_b, field_175826_b, false);
      this.fillWithBlocks(var1, var3, 10, 7, 1, 10, 7, 6, field_175826_b, field_175826_b, false);
      this.fillWithBlocks(var1, var3, 5, 7, 9, 5, 7, 14, field_175826_b, field_175826_b, false);
      this.fillWithBlocks(var1, var3, 10, 7, 9, 10, 7, 14, field_175826_b, field_175826_b, false);
      this.fillWithBlocks(var1, var3, 1, 7, 5, 6, 7, 5, field_175826_b, field_175826_b, false);
      this.fillWithBlocks(var1, var3, 1, 7, 10, 6, 7, 10, field_175826_b, field_175826_b, false);
      this.fillWithBlocks(var1, var3, 9, 7, 5, 14, 7, 5, field_175826_b, field_175826_b, false);
      this.fillWithBlocks(var1, var3, 9, 7, 10, 14, 7, 10, field_175826_b, field_175826_b, false);
      this.fillWithBlocks(var1, var3, 2, 1, 2, 2, 1, 3, field_175826_b, field_175826_b, false);
      this.fillWithBlocks(var1, var3, 3, 1, 2, 3, 1, 2, field_175826_b, field_175826_b, false);
      this.fillWithBlocks(var1, var3, 13, 1, 2, 13, 1, 3, field_175826_b, field_175826_b, false);
      this.fillWithBlocks(var1, var3, 12, 1, 2, 12, 1, 2, field_175826_b, field_175826_b, false);
      this.fillWithBlocks(var1, var3, 2, 1, 12, 2, 1, 13, field_175826_b, field_175826_b, false);
      this.fillWithBlocks(var1, var3, 3, 1, 13, 3, 1, 13, field_175826_b, field_175826_b, false);
      this.fillWithBlocks(var1, var3, 13, 1, 12, 13, 1, 13, field_175826_b, field_175826_b, false);
      this.fillWithBlocks(var1, var3, 12, 1, 13, 12, 1, 13, field_175826_b, field_175826_b, false);
      return true;
   }
}
