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

public class StructureStrongholdPieces$Library extends a1C {
   private static final List strongholdLibraryChestContents = Lists.newArrayList(new WeightedRandomChestContent[]{new WeightedRandomChestContent(Items.book, 0, 1, 3, 20), new WeightedRandomChestContent(Items.paper, 0, 2, 7, 20), new WeightedRandomChestContent(Items.map, 0, 1, 1, 1), new WeightedRandomChestContent(Items.compass, 0, 1, 1, 1)});
   private boolean isLargeRoom;

   public StructureStrongholdPieces$Library() {
   }

   public StructureStrongholdPieces$Library(int var1, Random var2, StructureBoundingBox var3, EnumFacing var4) {
      super(var1);
      this.coordBaseMode = var4;
      this.d = this.a(var2);
      this.boundingBox = var3;
      this.isLargeRoom = var3.getYSize() > 6;
   }

   protected void writeStructureToNBT(NBTTagCompound var1) {
      super.writeStructureToNBT(var1);
      var1.setBoolean("Tall", this.isLargeRoom);
   }

   protected void readStructureFromNBT(NBTTagCompound var1) {
      super.readStructureFromNBT(var1);
      this.isLargeRoom = var1.getBoolean("Tall");
   }

   public static StructureStrongholdPieces$Library func_175864_a(List var0, Random var1, int var2, int var3, int var4, EnumFacing var5, int var6) {
      StructureBoundingBox var7 = mZ.a(var2, var3, var4, -4, -1, 0, 14, 11, 15, var5);
      if(!a(var7) || StructureComponent.findIntersecting(var0, var7) != null) {
         var7 = mZ.a(var2, var3, var4, -4, -1, 0, 14, 6, 15, var5);
         if(!a(var7) || StructureComponent.findIntersecting(var0, var7) != null) {
            return null;
         }
      }

      return new StructureStrongholdPieces$Library(var6, var1, var7, var5);
   }

   public boolean addComponentParts(World var1, Random var2, StructureBoundingBox var3) {
      if(this.isLiquidInStructureBoundingBox(var1, var3)) {
         return false;
      } else {
         byte var4 = 11;
         if(!this.isLargeRoom) {
            var4 = 6;
         }

         this.fillWithRandomizedBlocks(var1, var3, 0, 0, 0, 13, var4 - 1, 14, true, var2, StructureStrongholdPieces.access$100());
         this.a(var1, var2, var3, this.d, 4, 1, 0);
         this.func_175805_a(var1, var3, var2, 0.07F, 2, 1, 1, 11, 4, 13, Blocks.web.getDefaultState(), Blocks.web.getDefaultState(), false);
         boolean var5 = true;
         boolean var6 = true;

         for(int var7 = 1; var7 <= 13; ++var7) {
            if((var7 - 1) % 4 == 0) {
               this.fillWithBlocks(var1, var3, 1, 1, var7, 1, 4, var7, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
               this.fillWithBlocks(var1, var3, 12, 1, var7, 12, 4, var7, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
               this.setBlockState(var1, Blocks.torch.getDefaultState(), 2, 3, var7, var3);
               this.setBlockState(var1, Blocks.torch.getDefaultState(), 11, 3, var7, var3);
               if(this.isLargeRoom) {
                  this.fillWithBlocks(var1, var3, 1, 6, var7, 1, 9, var7, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
                  this.fillWithBlocks(var1, var3, 12, 6, var7, 12, 9, var7, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
               }
            } else {
               this.fillWithBlocks(var1, var3, 1, 1, var7, 1, 4, var7, Blocks.bookshelf.getDefaultState(), Blocks.bookshelf.getDefaultState(), false);
               this.fillWithBlocks(var1, var3, 12, 1, var7, 12, 4, var7, Blocks.bookshelf.getDefaultState(), Blocks.bookshelf.getDefaultState(), false);
               if(this.isLargeRoom) {
                  this.fillWithBlocks(var1, var3, 1, 6, var7, 1, 9, var7, Blocks.bookshelf.getDefaultState(), Blocks.bookshelf.getDefaultState(), false);
                  this.fillWithBlocks(var1, var3, 12, 6, var7, 12, 9, var7, Blocks.bookshelf.getDefaultState(), Blocks.bookshelf.getDefaultState(), false);
               }
            }
         }

         for(int var10 = 3; var10 < 12; var10 += 2) {
            this.fillWithBlocks(var1, var3, 3, 1, var10, 4, 3, var10, Blocks.bookshelf.getDefaultState(), Blocks.bookshelf.getDefaultState(), false);
            this.fillWithBlocks(var1, var3, 6, 1, var10, 7, 3, var10, Blocks.bookshelf.getDefaultState(), Blocks.bookshelf.getDefaultState(), false);
            this.fillWithBlocks(var1, var3, 9, 1, var10, 10, 3, var10, Blocks.bookshelf.getDefaultState(), Blocks.bookshelf.getDefaultState(), false);
         }

         if(this.isLargeRoom) {
            this.fillWithBlocks(var1, var3, 1, 5, 1, 3, 5, 13, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
            this.fillWithBlocks(var1, var3, 10, 5, 1, 12, 5, 13, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
            this.fillWithBlocks(var1, var3, 4, 5, 1, 9, 5, 2, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
            this.fillWithBlocks(var1, var3, 4, 5, 12, 9, 5, 13, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
            this.setBlockState(var1, Blocks.planks.getDefaultState(), 9, 5, 11, var3);
            this.setBlockState(var1, Blocks.planks.getDefaultState(), 8, 5, 11, var3);
            this.setBlockState(var1, Blocks.planks.getDefaultState(), 9, 5, 10, var3);
            this.fillWithBlocks(var1, var3, 3, 6, 2, 3, 6, 12, Blocks.oak_fence.getDefaultState(), Blocks.oak_fence.getDefaultState(), false);
            this.fillWithBlocks(var1, var3, 10, 6, 2, 10, 6, 10, Blocks.oak_fence.getDefaultState(), Blocks.oak_fence.getDefaultState(), false);
            this.fillWithBlocks(var1, var3, 4, 6, 2, 9, 6, 2, Blocks.oak_fence.getDefaultState(), Blocks.oak_fence.getDefaultState(), false);
            this.fillWithBlocks(var1, var3, 4, 6, 12, 8, 6, 12, Blocks.oak_fence.getDefaultState(), Blocks.oak_fence.getDefaultState(), false);
            this.setBlockState(var1, Blocks.oak_fence.getDefaultState(), 9, 6, 11, var3);
            this.setBlockState(var1, Blocks.oak_fence.getDefaultState(), 8, 6, 11, var3);
            this.setBlockState(var1, Blocks.oak_fence.getDefaultState(), 9, 6, 10, var3);
            int var11 = this.getMetadataWithOffset(Blocks.ladder, 3);
            this.setBlockState(var1, Blocks.ladder.getStateFromMeta(var11), 10, 1, 13, var3);
            this.setBlockState(var1, Blocks.ladder.getStateFromMeta(var11), 10, 2, 13, var3);
            this.setBlockState(var1, Blocks.ladder.getStateFromMeta(var11), 10, 3, 13, var3);
            this.setBlockState(var1, Blocks.ladder.getStateFromMeta(var11), 10, 4, 13, var3);
            this.setBlockState(var1, Blocks.ladder.getStateFromMeta(var11), 10, 5, 13, var3);
            this.setBlockState(var1, Blocks.ladder.getStateFromMeta(var11), 10, 6, 13, var3);
            this.setBlockState(var1, Blocks.ladder.getStateFromMeta(var11), 10, 7, 13, var3);
            byte var8 = 7;
            byte var9 = 7;
            this.setBlockState(var1, Blocks.oak_fence.getDefaultState(), var8 - 1, 9, var9, var3);
            this.setBlockState(var1, Blocks.oak_fence.getDefaultState(), var8, 9, var9, var3);
            this.setBlockState(var1, Blocks.oak_fence.getDefaultState(), var8 - 1, 8, var9, var3);
            this.setBlockState(var1, Blocks.oak_fence.getDefaultState(), var8, 8, var9, var3);
            this.setBlockState(var1, Blocks.oak_fence.getDefaultState(), var8 - 1, 7, var9, var3);
            this.setBlockState(var1, Blocks.oak_fence.getDefaultState(), var8, 7, var9, var3);
            this.setBlockState(var1, Blocks.oak_fence.getDefaultState(), var8 - 2, 7, var9, var3);
            this.setBlockState(var1, Blocks.oak_fence.getDefaultState(), var8 + 1, 7, var9, var3);
            this.setBlockState(var1, Blocks.oak_fence.getDefaultState(), var8 - 1, 7, var9 - 1, var3);
            this.setBlockState(var1, Blocks.oak_fence.getDefaultState(), var8 - 1, 7, var9 + 1, var3);
            this.setBlockState(var1, Blocks.oak_fence.getDefaultState(), var8, 7, var9 - 1, var3);
            this.setBlockState(var1, Blocks.oak_fence.getDefaultState(), var8, 7, var9 + 1, var3);
            this.setBlockState(var1, Blocks.torch.getDefaultState(), var8 - 2, 8, var9, var3);
            this.setBlockState(var1, Blocks.torch.getDefaultState(), var8 + 1, 8, var9, var3);
            this.setBlockState(var1, Blocks.torch.getDefaultState(), var8 - 1, 8, var9 - 1, var3);
            this.setBlockState(var1, Blocks.torch.getDefaultState(), var8 - 1, 8, var9 + 1, var3);
            this.setBlockState(var1, Blocks.torch.getDefaultState(), var8, 8, var9 - 1, var3);
            this.setBlockState(var1, Blocks.torch.getDefaultState(), var8, 8, var9 + 1, var3);
         }

         this.generateChestContents(var1, var3, var2, 3, 3, 5, WeightedRandomChestContent.func_177629_a(strongholdLibraryChestContents, new WeightedRandomChestContent[]{Items.enchanted_book.getRandom(var2, 1, 5, 2)}), 1 + var2.nextInt(4));
         if(this.isLargeRoom) {
            this.setBlockState(var1, Blocks.air.getDefaultState(), 12, 9, 1, var3);
            this.generateChestContents(var1, var3, var2, 12, 8, 1, WeightedRandomChestContent.func_177629_a(strongholdLibraryChestContents, new WeightedRandomChestContent[]{Items.enchanted_book.getRandom(var2, 1, 5, 2)}), 1 + var2.nextInt(4));
         }

         return true;
      }
   }
}
