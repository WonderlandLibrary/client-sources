package net.minecraft.world.gen.structure;

import java.util.List;
import java.util.Random;
import net.mZ;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureVillagePieces$Start;
import net.minecraft.world.gen.structure.StructureVillagePieces$Village;

public class StructureVillagePieces$WoodHut extends StructureVillagePieces$Village {
   private boolean isTallHouse;
   private int tablePosition;

   public StructureVillagePieces$WoodHut() {
   }

   public StructureVillagePieces$WoodHut(StructureVillagePieces$Start var1, int var2, Random var3, StructureBoundingBox var4, EnumFacing var5) {
      super(var1, var2);
      this.coordBaseMode = var5;
      this.boundingBox = var4;
      this.isTallHouse = var3.nextBoolean();
      this.tablePosition = var3.nextInt(3);
   }

   protected void writeStructureToNBT(NBTTagCompound var1) {
      super.writeStructureToNBT(var1);
      var1.setInteger("T", this.tablePosition);
      var1.setBoolean("C", this.isTallHouse);
   }

   protected void readStructureFromNBT(NBTTagCompound var1) {
      super.readStructureFromNBT(var1);
      this.tablePosition = var1.getInteger("T");
      this.isTallHouse = var1.getBoolean("C");
   }

   public static StructureVillagePieces$WoodHut func_175853_a(StructureVillagePieces$Start var0, List var1, Random var2, int var3, int var4, int var5, EnumFacing var6, int var7) {
      StructureBoundingBox var8 = mZ.a(var3, var4, var5, 0, 0, 0, 4, 6, 5, var6);
      return canVillageGoDeeper(var8) && StructureComponent.findIntersecting(var1, var8) == null?new StructureVillagePieces$WoodHut(var0, var7, var2, var8, var6):null;
   }

   public boolean addComponentParts(World var1, Random var2, StructureBoundingBox var3) {
      if(this.field_143015_k < 0) {
         this.field_143015_k = this.getAverageGroundLevel(var1, var3);
         if(this.field_143015_k < 0) {
            return true;
         }

         this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + 6 - 1, 0);
      }

      this.fillWithBlocks(var1, var3, 1, 1, 1, 3, 5, 4, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 0, 0, 0, 3, 0, 4, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 1, 0, 1, 2, 0, 3, Blocks.dirt.getDefaultState(), Blocks.dirt.getDefaultState(), false);
      if(this.isTallHouse) {
         this.fillWithBlocks(var1, var3, 1, 4, 1, 2, 4, 3, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
      } else {
         this.fillWithBlocks(var1, var3, 1, 5, 1, 2, 5, 3, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
      }

      this.setBlockState(var1, Blocks.log.getDefaultState(), 1, 4, 0, var3);
      this.setBlockState(var1, Blocks.log.getDefaultState(), 2, 4, 0, var3);
      this.setBlockState(var1, Blocks.log.getDefaultState(), 1, 4, 4, var3);
      this.setBlockState(var1, Blocks.log.getDefaultState(), 2, 4, 4, var3);
      this.setBlockState(var1, Blocks.log.getDefaultState(), 0, 4, 1, var3);
      this.setBlockState(var1, Blocks.log.getDefaultState(), 0, 4, 2, var3);
      this.setBlockState(var1, Blocks.log.getDefaultState(), 0, 4, 3, var3);
      this.setBlockState(var1, Blocks.log.getDefaultState(), 3, 4, 1, var3);
      this.setBlockState(var1, Blocks.log.getDefaultState(), 3, 4, 2, var3);
      this.setBlockState(var1, Blocks.log.getDefaultState(), 3, 4, 3, var3);
      this.fillWithBlocks(var1, var3, 0, 1, 0, 0, 3, 0, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 3, 1, 0, 3, 3, 0, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 0, 1, 4, 0, 3, 4, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 3, 1, 4, 3, 3, 4, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 0, 1, 1, 0, 3, 3, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 3, 1, 1, 3, 3, 3, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 1, 1, 0, 2, 3, 0, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 1, 1, 4, 2, 3, 4, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
      this.setBlockState(var1, Blocks.glass_pane.getDefaultState(), 0, 2, 2, var3);
      this.setBlockState(var1, Blocks.glass_pane.getDefaultState(), 3, 2, 2, var3);
      if(this.tablePosition > 0) {
         this.setBlockState(var1, Blocks.oak_fence.getDefaultState(), this.tablePosition, 1, 3, var3);
         this.setBlockState(var1, Blocks.wooden_pressure_plate.getDefaultState(), this.tablePosition, 2, 3, var3);
      }

      this.setBlockState(var1, Blocks.air.getDefaultState(), 1, 1, 0, var3);
      this.setBlockState(var1, Blocks.air.getDefaultState(), 1, 2, 0, var3);
      this.placeDoorCurrentPosition(var1, var3, var2, 1, 1, 0, EnumFacing.getHorizontal(this.getMetadataWithOffset(Blocks.oak_door, 1)));
      if(this.getBlockStateFromPos(var1, 1, 0, -1, var3).getBlock().getMaterial() == Material.air && this.getBlockStateFromPos(var1, 1, -1, -1, var3).getBlock().getMaterial() != Material.air) {
         this.setBlockState(var1, Blocks.stone_stairs.getStateFromMeta(this.getMetadataWithOffset(Blocks.stone_stairs, 3)), 1, 0, -1, var3);
      }

      for(int var4 = 0; var4 < 5; ++var4) {
         for(int var5 = 0; var5 < 4; ++var5) {
            this.clearCurrentPositionBlocksUpwards(var1, var5, 6, var4, var3);
            this.replaceAirAndLiquidDownwards(var1, Blocks.cobblestone.getDefaultState(), var5, -1, var4, var3);
         }
      }

      this.spawnVillagers(var1, var3, 1, 1, 2, 1);
      return true;
   }
}
