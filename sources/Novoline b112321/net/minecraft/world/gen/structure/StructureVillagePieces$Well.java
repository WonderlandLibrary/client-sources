package net.minecraft.world.gen.structure;

import java.util.List;
import java.util.Random;
import net.N4;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing$Plane;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureVillagePieces;
import net.minecraft.world.gen.structure.StructureVillagePieces$Start;
import net.minecraft.world.gen.structure.StructureVillagePieces$Village;

public class StructureVillagePieces$Well extends StructureVillagePieces$Village {
   public StructureVillagePieces$Well() {
   }

   public StructureVillagePieces$Well(StructureVillagePieces$Start var1, int var2, Random var3, int var4, int var5) {
      super(var1, var2);
      this.coordBaseMode = EnumFacing$Plane.HORIZONTAL.random(var3);
      switch(N4.a[this.coordBaseMode.ordinal()]) {
      case 1:
      case 2:
         this.boundingBox = new StructureBoundingBox(var4, 64, var5, var4 + 6 - 1, 78, var5 + 6 - 1);
         break;
      default:
         this.boundingBox = new StructureBoundingBox(var4, 64, var5, var4 + 6 - 1, 78, var5 + 6 - 1);
      }

   }

   public void buildComponent(StructureComponent var1, List var2, Random var3) {
      StructureVillagePieces.access$000((StructureVillagePieces$Start)var1, var2, var3, this.boundingBox.minX - 1, this.boundingBox.maxY - 4, this.boundingBox.minZ + 1, EnumFacing.WEST, this.getComponentType());
      StructureVillagePieces.access$000((StructureVillagePieces$Start)var1, var2, var3, this.boundingBox.maxX + 1, this.boundingBox.maxY - 4, this.boundingBox.minZ + 1, EnumFacing.EAST, this.getComponentType());
      StructureVillagePieces.access$000((StructureVillagePieces$Start)var1, var2, var3, this.boundingBox.minX + 1, this.boundingBox.maxY - 4, this.boundingBox.minZ - 1, EnumFacing.NORTH, this.getComponentType());
      StructureVillagePieces.access$000((StructureVillagePieces$Start)var1, var2, var3, this.boundingBox.minX + 1, this.boundingBox.maxY - 4, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, this.getComponentType());
   }

   public boolean addComponentParts(World var1, Random var2, StructureBoundingBox var3) {
      if(this.field_143015_k < 0) {
         this.field_143015_k = this.getAverageGroundLevel(var1, var3);
         if(this.field_143015_k < 0) {
            return true;
         }

         this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + 3, 0);
      }

      this.fillWithBlocks(var1, var3, 1, 0, 1, 4, 12, 4, Blocks.cobblestone.getDefaultState(), Blocks.flowing_water.getDefaultState(), false);
      this.setBlockState(var1, Blocks.air.getDefaultState(), 2, 12, 2, var3);
      this.setBlockState(var1, Blocks.air.getDefaultState(), 3, 12, 2, var3);
      this.setBlockState(var1, Blocks.air.getDefaultState(), 2, 12, 3, var3);
      this.setBlockState(var1, Blocks.air.getDefaultState(), 3, 12, 3, var3);
      this.setBlockState(var1, Blocks.oak_fence.getDefaultState(), 1, 13, 1, var3);
      this.setBlockState(var1, Blocks.oak_fence.getDefaultState(), 1, 14, 1, var3);
      this.setBlockState(var1, Blocks.oak_fence.getDefaultState(), 4, 13, 1, var3);
      this.setBlockState(var1, Blocks.oak_fence.getDefaultState(), 4, 14, 1, var3);
      this.setBlockState(var1, Blocks.oak_fence.getDefaultState(), 1, 13, 4, var3);
      this.setBlockState(var1, Blocks.oak_fence.getDefaultState(), 1, 14, 4, var3);
      this.setBlockState(var1, Blocks.oak_fence.getDefaultState(), 4, 13, 4, var3);
      this.setBlockState(var1, Blocks.oak_fence.getDefaultState(), 4, 14, 4, var3);
      this.fillWithBlocks(var1, var3, 1, 15, 1, 4, 15, 4, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);

      for(int var4 = 0; var4 <= 5; ++var4) {
         for(int var5 = 0; var5 <= 5; ++var5) {
            if(var5 == 5 || var4 == 5) {
               this.setBlockState(var1, Blocks.gravel.getDefaultState(), var5, 11, var4, var3);
               this.clearCurrentPositionBlocksUpwards(var1, var5, 12, var4, var3);
            }
         }
      }

      return true;
   }
}
