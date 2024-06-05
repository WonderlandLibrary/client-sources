package net.minecraft.world.gen.structure;

import java.util.Random;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureOceanMonumentPieces$Piece;

public class StructureOceanMonumentPieces$Penthouse extends StructureOceanMonumentPieces$Piece {
   public StructureOceanMonumentPieces$Penthouse() {
   }

   public StructureOceanMonumentPieces$Penthouse(EnumFacing var1, StructureBoundingBox var2) {
      super(var1, var2);
   }

   public boolean addComponentParts(World var1, Random var2, StructureBoundingBox var3) {
      this.fillWithBlocks(var1, var3, 2, -1, 2, 11, -1, 11, field_175826_b, field_175826_b, false);
      this.fillWithBlocks(var1, var3, 0, -1, 0, 1, -1, 11, field_175828_a, field_175828_a, false);
      this.fillWithBlocks(var1, var3, 12, -1, 0, 13, -1, 11, field_175828_a, field_175828_a, false);
      this.fillWithBlocks(var1, var3, 2, -1, 0, 11, -1, 1, field_175828_a, field_175828_a, false);
      this.fillWithBlocks(var1, var3, 2, -1, 12, 11, -1, 13, field_175828_a, field_175828_a, false);
      this.fillWithBlocks(var1, var3, 0, 0, 0, 0, 0, 13, field_175826_b, field_175826_b, false);
      this.fillWithBlocks(var1, var3, 13, 0, 0, 13, 0, 13, field_175826_b, field_175826_b, false);
      this.fillWithBlocks(var1, var3, 1, 0, 0, 12, 0, 0, field_175826_b, field_175826_b, false);
      this.fillWithBlocks(var1, var3, 1, 0, 13, 12, 0, 13, field_175826_b, field_175826_b, false);

      for(int var4 = 2; var4 <= 11; var4 += 3) {
         this.setBlockState(var1, field_175825_e, 0, 0, var4, var3);
         this.setBlockState(var1, field_175825_e, 13, 0, var4, var3);
         this.setBlockState(var1, field_175825_e, var4, 0, 0, var3);
      }

      this.fillWithBlocks(var1, var3, 2, 0, 3, 4, 0, 9, field_175826_b, field_175826_b, false);
      this.fillWithBlocks(var1, var3, 9, 0, 3, 11, 0, 9, field_175826_b, field_175826_b, false);
      this.fillWithBlocks(var1, var3, 4, 0, 9, 9, 0, 11, field_175826_b, field_175826_b, false);
      this.setBlockState(var1, field_175826_b, 5, 0, 8, var3);
      this.setBlockState(var1, field_175826_b, 8, 0, 8, var3);
      this.setBlockState(var1, field_175826_b, 10, 0, 10, var3);
      this.setBlockState(var1, field_175826_b, 3, 0, 10, var3);
      this.fillWithBlocks(var1, var3, 3, 0, 3, 3, 0, 7, field_175827_c, field_175827_c, false);
      this.fillWithBlocks(var1, var3, 10, 0, 3, 10, 0, 7, field_175827_c, field_175827_c, false);
      this.fillWithBlocks(var1, var3, 6, 0, 10, 7, 0, 10, field_175827_c, field_175827_c, false);
      byte var7 = 3;

      for(int var5 = 0; var5 < 2; ++var5) {
         for(int var6 = 2; var6 <= 8; var6 += 3) {
            this.fillWithBlocks(var1, var3, var7, 0, var6, var7, 2, var6, field_175826_b, field_175826_b, false);
         }

         var7 = 10;
      }

      this.fillWithBlocks(var1, var3, 5, 0, 10, 5, 2, 10, field_175826_b, field_175826_b, false);
      this.fillWithBlocks(var1, var3, 8, 0, 10, 8, 2, 10, field_175826_b, field_175826_b, false);
      this.fillWithBlocks(var1, var3, 6, -1, 7, 7, -1, 8, field_175827_c, field_175827_c, false);
      this.func_181655_a(var1, var3, 6, -1, 3, 7, -1, 4, false);
      this.func_175817_a(var1, var3, 6, 1, 6);
      return true;
   }
}
