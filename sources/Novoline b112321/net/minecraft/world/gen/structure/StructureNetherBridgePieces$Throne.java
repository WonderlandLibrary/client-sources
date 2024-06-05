package net.minecraft.world.gen.structure;

import java.util.List;
import java.util.Random;
import net.mZ;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureNetherBridgePieces$Piece;

public class StructureNetherBridgePieces$Throne extends StructureNetherBridgePieces$Piece {
   private boolean hasSpawner;

   public StructureNetherBridgePieces$Throne() {
   }

   public StructureNetherBridgePieces$Throne(int var1, Random var2, StructureBoundingBox var3, EnumFacing var4) {
      super(var1);
      this.coordBaseMode = var4;
      this.boundingBox = var3;
   }

   protected void readStructureFromNBT(NBTTagCompound var1) {
      super.readStructureFromNBT(var1);
      this.hasSpawner = var1.getBoolean("Mob");
   }

   protected void writeStructureToNBT(NBTTagCompound var1) {
      super.writeStructureToNBT(var1);
      var1.setBoolean("Mob", this.hasSpawner);
   }

   public static StructureNetherBridgePieces$Throne func_175874_a(List var0, Random var1, int var2, int var3, int var4, int var5, EnumFacing var6) {
      StructureBoundingBox var7 = mZ.a(var2, var3, var4, -2, 0, 0, 7, 8, 9, var6);
      return isAboveGround(var7) && StructureComponent.findIntersecting(var0, var7) == null?new StructureNetherBridgePieces$Throne(var5, var1, var7, var6):null;
   }

   public boolean addComponentParts(World var1, Random var2, StructureBoundingBox var3) {
      this.fillWithBlocks(var1, var3, 0, 2, 0, 6, 7, 7, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 1, 0, 0, 5, 1, 7, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 1, 2, 1, 5, 2, 7, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 1, 3, 2, 5, 3, 7, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 1, 4, 3, 5, 4, 7, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 1, 2, 0, 1, 4, 2, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 5, 2, 0, 5, 4, 2, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 1, 5, 2, 1, 5, 3, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 5, 5, 2, 5, 5, 3, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 0, 5, 3, 0, 5, 8, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 6, 5, 3, 6, 5, 8, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 1, 5, 8, 5, 5, 8, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
      this.setBlockState(var1, Blocks.nether_brick_fence.getDefaultState(), 1, 6, 3, var3);
      this.setBlockState(var1, Blocks.nether_brick_fence.getDefaultState(), 5, 6, 3, var3);
      this.fillWithBlocks(var1, var3, 0, 6, 3, 0, 6, 8, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 6, 6, 3, 6, 6, 8, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 1, 6, 8, 5, 7, 8, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 2, 8, 8, 4, 8, 8, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
      if(!this.hasSpawner) {
         BlockPos var4 = new BlockPos(this.getXWithOffset(3, 5), this.getYWithOffset(5), this.getZWithOffset(3, 5));
         if(var3.isVecInside(var4)) {
            this.hasSpawner = true;
            var1.setBlockState(var4, Blocks.mob_spawner.getDefaultState(), 2);
            TileEntity var5 = var1.getTileEntity(var4);
            if(var5 instanceof TileEntityMobSpawner) {
               ((TileEntityMobSpawner)var5).getSpawnerBaseLogic().setEntityName("Blaze");
            }
         }
      }

      for(int var6 = 0; var6 <= 6; ++var6) {
         for(int var7 = 0; var7 <= 6; ++var7) {
            this.replaceAirAndLiquidDownwards(var1, Blocks.nether_brick.getDefaultState(), var6, -1, var7, var3);
         }
      }

      return true;
   }
}
