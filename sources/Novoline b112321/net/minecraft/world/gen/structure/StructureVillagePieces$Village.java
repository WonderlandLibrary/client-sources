package net.minecraft.world.gen.structure;

import java.util.List;
import java.util.Random;
import net.N4;
import net.minecraft.block.BlockSandStone$EnumType;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.BlockPos$MutableBlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureVillagePieces;
import net.minecraft.world.gen.structure.StructureVillagePieces$Start;

abstract class StructureVillagePieces$Village extends StructureComponent {
   protected int field_143015_k = -1;
   private int villagersSpawned;
   private boolean isDesertVillage;

   public StructureVillagePieces$Village() {
   }

   protected StructureVillagePieces$Village(StructureVillagePieces$Start var1, int var2) {
      super(var2);
      this.isDesertVillage = var1.inDesert;
   }

   protected void writeStructureToNBT(NBTTagCompound var1) {
      var1.setInteger("HPos", this.field_143015_k);
      var1.setInteger("VCount", this.villagersSpawned);
      var1.setBoolean("Desert", this.isDesertVillage);
   }

   protected void readStructureFromNBT(NBTTagCompound var1) {
      this.field_143015_k = var1.getInteger("HPos");
      this.villagersSpawned = var1.getInteger("VCount");
      this.isDesertVillage = var1.getBoolean("Desert");
   }

   protected StructureComponent getNextComponentNN(StructureVillagePieces$Start var1, List var2, Random var3, int var4, int var5) {
      if(this.coordBaseMode != null) {
         switch(N4.a[this.coordBaseMode.ordinal()]) {
         case 1:
            return StructureVillagePieces.access$100(var1, var2, var3, this.boundingBox.minX - 1, this.boundingBox.minY + var4, this.boundingBox.minZ + var5, EnumFacing.WEST, this.getComponentType());
         case 2:
            return StructureVillagePieces.access$100(var1, var2, var3, this.boundingBox.minX - 1, this.boundingBox.minY + var4, this.boundingBox.minZ + var5, EnumFacing.WEST, this.getComponentType());
         case 3:
            return StructureVillagePieces.access$100(var1, var2, var3, this.boundingBox.minX + var5, this.boundingBox.minY + var4, this.boundingBox.minZ - 1, EnumFacing.NORTH, this.getComponentType());
         case 4:
            return StructureVillagePieces.access$100(var1, var2, var3, this.boundingBox.minX + var5, this.boundingBox.minY + var4, this.boundingBox.minZ - 1, EnumFacing.NORTH, this.getComponentType());
         }
      }

      return null;
   }

   protected StructureComponent getNextComponentPP(StructureVillagePieces$Start var1, List var2, Random var3, int var4, int var5) {
      if(this.coordBaseMode != null) {
         switch(N4.a[this.coordBaseMode.ordinal()]) {
         case 1:
            return StructureVillagePieces.access$100(var1, var2, var3, this.boundingBox.maxX + 1, this.boundingBox.minY + var4, this.boundingBox.minZ + var5, EnumFacing.EAST, this.getComponentType());
         case 2:
            return StructureVillagePieces.access$100(var1, var2, var3, this.boundingBox.maxX + 1, this.boundingBox.minY + var4, this.boundingBox.minZ + var5, EnumFacing.EAST, this.getComponentType());
         case 3:
            return StructureVillagePieces.access$100(var1, var2, var3, this.boundingBox.minX + var5, this.boundingBox.minY + var4, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, this.getComponentType());
         case 4:
            return StructureVillagePieces.access$100(var1, var2, var3, this.boundingBox.minX + var5, this.boundingBox.minY + var4, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, this.getComponentType());
         }
      }

      return null;
   }

   protected int getAverageGroundLevel(World var1, StructureBoundingBox var2) {
      int var3 = 0;
      int var4 = 0;
      BlockPos$MutableBlockPos var5 = new BlockPos$MutableBlockPos();

      for(int var6 = this.boundingBox.minZ; var6 <= this.boundingBox.maxZ; ++var6) {
         for(int var7 = this.boundingBox.minX; var7 <= this.boundingBox.maxX; ++var7) {
            var5.func_181079_c(var7, 64, var6);
            if(var2.isVecInside(var5)) {
               var3 += Math.max(var1.getTopSolidOrLiquidBlock(var5).getY(), var1.provider.getAverageGroundLevel());
               ++var4;
            }
         }
      }

      return -1;
   }

   protected static boolean canVillageGoDeeper(StructureBoundingBox var0) {
      return var0.minY > 10;
   }

   protected void spawnVillagers(World var1, StructureBoundingBox var2, int var3, int var4, int var5, int var6) {
      if(this.villagersSpawned < var6) {
         for(int var7 = this.villagersSpawned; var7 < var6; ++var7) {
            int var8 = this.getXWithOffset(var3 + var7, var5);
            int var9 = this.getYWithOffset(var4);
            int var10 = this.getZWithOffset(var3 + var7, var5);
            if(!var2.isVecInside(new BlockPos(var8, var9, var10))) {
               break;
            }

            ++this.villagersSpawned;
            EntityVillager var11 = new EntityVillager(var1);
            var11.setLocationAndAngles((double)var8 + 0.5D, (double)var9, (double)var10 + 0.5D, 0.0F, 0.0F);
            var11.onInitialSpawn(var1.getDifficultyForLocation(new BlockPos(var11)), (IEntityLivingData)null);
            var11.setProfession(this.func_180779_c(var7, var11.getProfession()));
            var1.spawnEntityInWorld(var11);
         }
      }

   }

   protected int func_180779_c(int var1, int var2) {
      return var2;
   }

   protected IBlockState func_175847_a(IBlockState var1) {
      if(this.isDesertVillage) {
         if(var1.getBlock() == Blocks.log || var1.getBlock() == Blocks.log2) {
            return Blocks.sandstone.getDefaultState();
         }

         if(var1.getBlock() == Blocks.cobblestone) {
            return Blocks.sandstone.getStateFromMeta(BlockSandStone$EnumType.DEFAULT.getMetadata());
         }

         if(var1.getBlock() == Blocks.planks) {
            return Blocks.sandstone.getStateFromMeta(BlockSandStone$EnumType.SMOOTH.getMetadata());
         }

         if(var1.getBlock() == Blocks.oak_stairs) {
            return Blocks.sandstone_stairs.getDefaultState().withProperty(BlockStairs.FACING, var1.getValue(BlockStairs.FACING));
         }

         if(var1.getBlock() == Blocks.stone_stairs) {
            return Blocks.sandstone_stairs.getDefaultState().withProperty(BlockStairs.FACING, var1.getValue(BlockStairs.FACING));
         }

         if(var1.getBlock() == Blocks.gravel) {
            return Blocks.sandstone.getDefaultState();
         }
      }

      return var1;
   }

   protected void setBlockState(World var1, IBlockState var2, int var3, int var4, int var5, StructureBoundingBox var6) {
      IBlockState var7 = this.func_175847_a(var2);
      super.setBlockState(var1, var7, var3, var4, var5, var6);
   }

   protected void fillWithBlocks(World var1, StructureBoundingBox var2, int var3, int var4, int var5, int var6, int var7, int var8, IBlockState var9, IBlockState var10, boolean var11) {
      IBlockState var12 = this.func_175847_a(var9);
      IBlockState var13 = this.func_175847_a(var10);
      super.fillWithBlocks(var1, var2, var3, var4, var5, var6, var7, var8, var12, var13, var11);
   }

   protected void replaceAirAndLiquidDownwards(World var1, IBlockState var2, int var3, int var4, int var5, StructureBoundingBox var6) {
      IBlockState var7 = this.func_175847_a(var2);
      super.replaceAirAndLiquidDownwards(var1, var7, var3, var4, var5, var6);
   }

   protected void func_175846_a(boolean var1) {
      this.isDesertVillage = var1;
   }
}
