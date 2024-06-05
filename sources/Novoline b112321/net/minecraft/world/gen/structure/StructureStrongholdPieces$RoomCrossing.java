package net.minecraft.world.gen.structure;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Random;
import net.a1C;
import net.mZ;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureStrongholdPieces;
import net.minecraft.world.gen.structure.StructureStrongholdPieces$Stairs2;

public class StructureStrongholdPieces$RoomCrossing extends a1C {
   private static final List strongholdRoomCrossingChestContents = Lists.newArrayList(new WeightedRandomChestContent[]{new WeightedRandomChestContent(Items.iron_ingot, 0, 1, 5, 10), new WeightedRandomChestContent(Items.gold_ingot, 0, 1, 3, 5), new WeightedRandomChestContent(Items.redstone, 0, 4, 9, 5), new WeightedRandomChestContent(Items.coal, 0, 3, 8, 10), new WeightedRandomChestContent(Items.bread, 0, 1, 3, 15), new WeightedRandomChestContent(Items.apple, 0, 1, 3, 15), new WeightedRandomChestContent(Items.iron_pickaxe, 0, 1, 1, 1)});
   protected int roomType;

   public StructureStrongholdPieces$RoomCrossing() {
   }

   public StructureStrongholdPieces$RoomCrossing(int var1, Random var2, StructureBoundingBox var3, EnumFacing var4) {
      super(var1);
      this.coordBaseMode = var4;
      this.d = this.a(var2);
      this.boundingBox = var3;
      this.roomType = var2.nextInt(5);
   }

   protected void writeStructureToNBT(NBTTagCompound var1) {
      super.writeStructureToNBT(var1);
      var1.setInteger("Type", this.roomType);
   }

   protected void readStructureFromNBT(NBTTagCompound var1) {
      super.readStructureFromNBT(var1);
      this.roomType = var1.getInteger("Type");
   }

   public void buildComponent(StructureComponent var1, List var2, Random var3) {
      this.c((StructureStrongholdPieces$Stairs2)var1, var2, var3, 4, 1);
      this.b((StructureStrongholdPieces$Stairs2)var1, var2, var3, 1, 4);
      this.a((StructureStrongholdPieces$Stairs2)var1, var2, var3, 1, 4);
   }

   public static StructureStrongholdPieces$RoomCrossing func_175859_a(List var0, Random var1, int var2, int var3, int var4, EnumFacing var5, int var6) {
      StructureBoundingBox var7 = mZ.a(var2, var3, var4, -4, -1, 0, 11, 7, 11, var5);
      return a(var7) && StructureComponent.findIntersecting(var0, var7) == null?new StructureStrongholdPieces$RoomCrossing(var6, var1, var7, var5):null;
   }

   public boolean addComponentParts(World var1, Random var2, StructureBoundingBox var3) {
      if(this.isLiquidInStructureBoundingBox(var1, var3)) {
         return false;
      } else {
         this.fillWithRandomizedBlocks(var1, var3, 0, 0, 0, 10, 6, 10, true, var2, StructureStrongholdPieces.access$100());
         this.a(var1, var2, var3, this.d, 4, 1, 0);
         this.fillWithBlocks(var1, var3, 4, 1, 10, 6, 3, 10, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
         this.fillWithBlocks(var1, var3, 0, 1, 4, 0, 3, 6, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
         this.fillWithBlocks(var1, var3, 10, 1, 4, 10, 3, 6, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
         switch(this.roomType) {
         case 0:
            this.setBlockState(var1, Blocks.stonebrick.getDefaultState(), 5, 1, 5, var3);
            this.setBlockState(var1, Blocks.stonebrick.getDefaultState(), 5, 2, 5, var3);
            this.setBlockState(var1, Blocks.stonebrick.getDefaultState(), 5, 3, 5, var3);
            this.setBlockState(var1, Blocks.torch.getDefaultState(), 4, 3, 5, var3);
            this.setBlockState(var1, Blocks.torch.getDefaultState(), 6, 3, 5, var3);
            this.setBlockState(var1, Blocks.torch.getDefaultState(), 5, 3, 4, var3);
            this.setBlockState(var1, Blocks.torch.getDefaultState(), 5, 3, 6, var3);
            this.setBlockState(var1, Blocks.stone_slab.getDefaultState(), 4, 1, 4, var3);
            this.setBlockState(var1, Blocks.stone_slab.getDefaultState(), 4, 1, 5, var3);
            this.setBlockState(var1, Blocks.stone_slab.getDefaultState(), 4, 1, 6, var3);
            this.setBlockState(var1, Blocks.stone_slab.getDefaultState(), 6, 1, 4, var3);
            this.setBlockState(var1, Blocks.stone_slab.getDefaultState(), 6, 1, 5, var3);
            this.setBlockState(var1, Blocks.stone_slab.getDefaultState(), 6, 1, 6, var3);
            this.setBlockState(var1, Blocks.stone_slab.getDefaultState(), 5, 1, 4, var3);
            this.setBlockState(var1, Blocks.stone_slab.getDefaultState(), 5, 1, 6, var3);
            break;
         case 1:
            for(int var8 = 0; var8 < 5; ++var8) {
               this.setBlockState(var1, Blocks.stonebrick.getDefaultState(), 3, 1, 3 + var8, var3);
               this.setBlockState(var1, Blocks.stonebrick.getDefaultState(), 7, 1, 3 + var8, var3);
               this.setBlockState(var1, Blocks.stonebrick.getDefaultState(), 3 + var8, 1, 3, var3);
               this.setBlockState(var1, Blocks.stonebrick.getDefaultState(), 3 + var8, 1, 7, var3);
            }

            this.setBlockState(var1, Blocks.stonebrick.getDefaultState(), 5, 1, 5, var3);
            this.setBlockState(var1, Blocks.stonebrick.getDefaultState(), 5, 2, 5, var3);
            this.setBlockState(var1, Blocks.stonebrick.getDefaultState(), 5, 3, 5, var3);
            this.setBlockState(var1, Blocks.flowing_water.getDefaultState(), 5, 4, 5, var3);
            break;
         case 2:
            for(int var4 = 1; var4 <= 9; ++var4) {
               this.setBlockState(var1, Blocks.cobblestone.getDefaultState(), 1, 3, var4, var3);
               this.setBlockState(var1, Blocks.cobblestone.getDefaultState(), 9, 3, var4, var3);
            }

            for(int var5 = 1; var5 <= 9; ++var5) {
               this.setBlockState(var1, Blocks.cobblestone.getDefaultState(), var5, 3, 1, var3);
               this.setBlockState(var1, Blocks.cobblestone.getDefaultState(), var5, 3, 9, var3);
            }

            this.setBlockState(var1, Blocks.cobblestone.getDefaultState(), 5, 1, 4, var3);
            this.setBlockState(var1, Blocks.cobblestone.getDefaultState(), 5, 1, 6, var3);
            this.setBlockState(var1, Blocks.cobblestone.getDefaultState(), 5, 3, 4, var3);
            this.setBlockState(var1, Blocks.cobblestone.getDefaultState(), 5, 3, 6, var3);
            this.setBlockState(var1, Blocks.cobblestone.getDefaultState(), 4, 1, 5, var3);
            this.setBlockState(var1, Blocks.cobblestone.getDefaultState(), 6, 1, 5, var3);
            this.setBlockState(var1, Blocks.cobblestone.getDefaultState(), 4, 3, 5, var3);
            this.setBlockState(var1, Blocks.cobblestone.getDefaultState(), 6, 3, 5, var3);

            for(int var6 = 1; var6 <= 3; ++var6) {
               this.setBlockState(var1, Blocks.cobblestone.getDefaultState(), 4, var6, 4, var3);
               this.setBlockState(var1, Blocks.cobblestone.getDefaultState(), 6, var6, 4, var3);
               this.setBlockState(var1, Blocks.cobblestone.getDefaultState(), 4, var6, 6, var3);
               this.setBlockState(var1, Blocks.cobblestone.getDefaultState(), 6, var6, 6, var3);
            }

            this.setBlockState(var1, Blocks.torch.getDefaultState(), 5, 3, 5, var3);

            for(int var7 = 2; var7 <= 8; ++var7) {
               this.setBlockState(var1, Blocks.planks.getDefaultState(), 2, 3, var7, var3);
               this.setBlockState(var1, Blocks.planks.getDefaultState(), 3, 3, var7, var3);
               if(var7 <= 3 || var7 >= 7) {
                  this.setBlockState(var1, Blocks.planks.getDefaultState(), 4, 3, var7, var3);
                  this.setBlockState(var1, Blocks.planks.getDefaultState(), 5, 3, var7, var3);
                  this.setBlockState(var1, Blocks.planks.getDefaultState(), 6, 3, var7, var3);
               }

               this.setBlockState(var1, Blocks.planks.getDefaultState(), 7, 3, var7, var3);
               this.setBlockState(var1, Blocks.planks.getDefaultState(), 8, 3, var7, var3);
            }

            this.setBlockState(var1, Blocks.ladder.getStateFromMeta(this.getMetadataWithOffset(Blocks.ladder, EnumFacing.WEST.getIndex())), 9, 1, 3, var3);
            this.setBlockState(var1, Blocks.ladder.getStateFromMeta(this.getMetadataWithOffset(Blocks.ladder, EnumFacing.WEST.getIndex())), 9, 2, 3, var3);
            this.setBlockState(var1, Blocks.ladder.getStateFromMeta(this.getMetadataWithOffset(Blocks.ladder, EnumFacing.WEST.getIndex())), 9, 3, 3, var3);
            this.generateChestContents(var1, var3, var2, 3, 4, 8, WeightedRandomChestContent.func_177629_a(strongholdRoomCrossingChestContents, new WeightedRandomChestContent[]{Items.enchanted_book.getRandom(var2)}), 1 + var2.nextInt(4));
         }

         return true;
      }
   }
}
