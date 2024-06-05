package net.minecraft.world.gen.structure;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Random;
import net.mZ;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureVillagePieces$Start;
import net.minecraft.world.gen.structure.StructureVillagePieces$Village;

public class StructureVillagePieces$House2 extends StructureVillagePieces$Village {
   private static final List villageBlacksmithChestContents = Lists.newArrayList(new WeightedRandomChestContent[]{new WeightedRandomChestContent(Items.diamond, 0, 1, 3, 3), new WeightedRandomChestContent(Items.iron_ingot, 0, 1, 5, 10), new WeightedRandomChestContent(Items.gold_ingot, 0, 1, 3, 5), new WeightedRandomChestContent(Items.bread, 0, 1, 3, 15), new WeightedRandomChestContent(Items.apple, 0, 1, 3, 15), new WeightedRandomChestContent(Items.iron_pickaxe, 0, 1, 1, 5), new WeightedRandomChestContent(Items.iron_sword, 0, 1, 1, 5), new WeightedRandomChestContent(Items.iron_chestplate, 0, 1, 1, 5), new WeightedRandomChestContent(Items.iron_helmet, 0, 1, 1, 5), new WeightedRandomChestContent(Items.iron_leggings, 0, 1, 1, 5), new WeightedRandomChestContent(Items.iron_boots, 0, 1, 1, 5), new WeightedRandomChestContent(Item.getItemFromBlock(Blocks.obsidian), 0, 3, 7, 5), new WeightedRandomChestContent(Item.getItemFromBlock(Blocks.sapling), 0, 3, 7, 5), new WeightedRandomChestContent(Items.saddle, 0, 1, 1, 3), new WeightedRandomChestContent(Items.iron_horse_armor, 0, 1, 1, 1), new WeightedRandomChestContent(Items.golden_horse_armor, 0, 1, 1, 1), new WeightedRandomChestContent(Items.diamond_horse_armor, 0, 1, 1, 1)});
   private boolean hasMadeChest;

   public StructureVillagePieces$House2() {
   }

   public StructureVillagePieces$House2(StructureVillagePieces$Start var1, int var2, Random var3, StructureBoundingBox var4, EnumFacing var5) {
      super(var1, var2);
      this.coordBaseMode = var5;
      this.boundingBox = var4;
   }

   public static StructureVillagePieces$House2 func_175855_a(StructureVillagePieces$Start var0, List var1, Random var2, int var3, int var4, int var5, EnumFacing var6, int var7) {
      StructureBoundingBox var8 = mZ.a(var3, var4, var5, 0, 0, 0, 10, 6, 7, var6);
      return canVillageGoDeeper(var8) && StructureComponent.findIntersecting(var1, var8) == null?new StructureVillagePieces$House2(var0, var7, var2, var8, var6):null;
   }

   protected void writeStructureToNBT(NBTTagCompound var1) {
      super.writeStructureToNBT(var1);
      var1.setBoolean("Chest", this.hasMadeChest);
   }

   protected void readStructureFromNBT(NBTTagCompound var1) {
      super.readStructureFromNBT(var1);
      this.hasMadeChest = var1.getBoolean("Chest");
   }

   public boolean addComponentParts(World var1, Random var2, StructureBoundingBox var3) {
      if(this.field_143015_k < 0) {
         this.field_143015_k = this.getAverageGroundLevel(var1, var3);
         if(this.field_143015_k < 0) {
            return true;
         }

         this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + 6 - 1, 0);
      }

      this.fillWithBlocks(var1, var3, 0, 1, 0, 9, 4, 6, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 0, 0, 0, 9, 0, 6, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 0, 4, 0, 9, 4, 6, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 0, 5, 0, 9, 5, 6, Blocks.stone_slab.getDefaultState(), Blocks.stone_slab.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 1, 5, 1, 8, 5, 5, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 1, 1, 0, 2, 3, 0, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 0, 1, 0, 0, 4, 0, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 3, 1, 0, 3, 4, 0, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 0, 1, 6, 0, 4, 6, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
      this.setBlockState(var1, Blocks.planks.getDefaultState(), 3, 3, 1, var3);
      this.fillWithBlocks(var1, var3, 3, 1, 2, 3, 3, 2, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 4, 1, 3, 5, 3, 3, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 0, 1, 1, 0, 3, 5, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 1, 1, 6, 5, 3, 6, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 5, 1, 0, 5, 3, 0, Blocks.oak_fence.getDefaultState(), Blocks.oak_fence.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 9, 1, 0, 9, 3, 0, Blocks.oak_fence.getDefaultState(), Blocks.oak_fence.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 6, 1, 4, 9, 4, 6, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
      this.setBlockState(var1, Blocks.flowing_lava.getDefaultState(), 7, 1, 5, var3);
      this.setBlockState(var1, Blocks.flowing_lava.getDefaultState(), 8, 1, 5, var3);
      this.setBlockState(var1, Blocks.iron_bars.getDefaultState(), 9, 2, 5, var3);
      this.setBlockState(var1, Blocks.iron_bars.getDefaultState(), 9, 2, 4, var3);
      this.fillWithBlocks(var1, var3, 7, 2, 4, 8, 2, 5, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
      this.setBlockState(var1, Blocks.cobblestone.getDefaultState(), 6, 1, 3, var3);
      this.setBlockState(var1, Blocks.furnace.getDefaultState(), 6, 2, 3, var3);
      this.setBlockState(var1, Blocks.furnace.getDefaultState(), 6, 3, 3, var3);
      this.setBlockState(var1, Blocks.double_stone_slab.getDefaultState(), 8, 1, 1, var3);
      this.setBlockState(var1, Blocks.glass_pane.getDefaultState(), 0, 2, 2, var3);
      this.setBlockState(var1, Blocks.glass_pane.getDefaultState(), 0, 2, 4, var3);
      this.setBlockState(var1, Blocks.glass_pane.getDefaultState(), 2, 2, 6, var3);
      this.setBlockState(var1, Blocks.glass_pane.getDefaultState(), 4, 2, 6, var3);
      this.setBlockState(var1, Blocks.oak_fence.getDefaultState(), 2, 1, 4, var3);
      this.setBlockState(var1, Blocks.wooden_pressure_plate.getDefaultState(), 2, 2, 4, var3);
      this.setBlockState(var1, Blocks.planks.getDefaultState(), 1, 1, 5, var3);
      this.setBlockState(var1, Blocks.oak_stairs.getStateFromMeta(this.getMetadataWithOffset(Blocks.oak_stairs, 3)), 2, 1, 5, var3);
      this.setBlockState(var1, Blocks.oak_stairs.getStateFromMeta(this.getMetadataWithOffset(Blocks.oak_stairs, 1)), 1, 1, 4, var3);
      if(!this.hasMadeChest && var3.isVecInside(new BlockPos(this.getXWithOffset(5, 5), this.getYWithOffset(1), this.getZWithOffset(5, 5)))) {
         this.hasMadeChest = true;
         this.generateChestContents(var1, var3, var2, 5, 1, 5, villageBlacksmithChestContents, 3 + var2.nextInt(6));
      }

      for(int var4 = 6; var4 <= 8; ++var4) {
         if(this.getBlockStateFromPos(var1, var4, 0, -1, var3).getBlock().getMaterial() == Material.air && this.getBlockStateFromPos(var1, var4, -1, -1, var3).getBlock().getMaterial() != Material.air) {
            this.setBlockState(var1, Blocks.stone_stairs.getStateFromMeta(this.getMetadataWithOffset(Blocks.stone_stairs, 3)), var4, 0, -1, var3);
         }
      }

      for(int var6 = 0; var6 < 7; ++var6) {
         for(int var5 = 0; var5 < 10; ++var5) {
            this.clearCurrentPositionBlocksUpwards(var1, var5, 6, var6, var3);
            this.replaceAirAndLiquidDownwards(var1, Blocks.cobblestone.getDefaultState(), var5, -1, var6, var3);
         }
      }

      this.spawnVillagers(var1, var3, 7, 1, 1, 1);
      return true;
   }

   protected int func_180779_c(int var1, int var2) {
      return 3;
   }
}
