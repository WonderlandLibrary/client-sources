package net.minecraft.world.gen.structure;

import java.util.List;
import java.util.Random;
import net.mZ;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureNetherBridgePieces$Piece;
import net.minecraft.world.gen.structure.StructureNetherBridgePieces$Start;

public class StructureNetherBridgePieces$Corridor2 extends StructureNetherBridgePieces$Piece {
   private boolean field_111020_b;

   public StructureNetherBridgePieces$Corridor2() {
   }

   public StructureNetherBridgePieces$Corridor2(int var1, Random var2, StructureBoundingBox var3, EnumFacing var4) {
      super(var1);
      this.coordBaseMode = var4;
      this.boundingBox = var3;
      this.field_111020_b = var2.nextInt(3) == 0;
   }

   protected void readStructureFromNBT(NBTTagCompound var1) {
      super.readStructureFromNBT(var1);
      this.field_111020_b = var1.getBoolean("Chest");
   }

   protected void writeStructureToNBT(NBTTagCompound var1) {
      super.writeStructureToNBT(var1);
      var1.setBoolean("Chest", this.field_111020_b);
   }

   public void buildComponent(StructureComponent var1, List var2, Random var3) {
      this.getNextComponentZ((StructureNetherBridgePieces$Start)var1, var2, var3, 0, 1, true);
   }

   public static StructureNetherBridgePieces$Corridor2 func_175876_a(List var0, Random var1, int var2, int var3, int var4, EnumFacing var5, int var6) {
      StructureBoundingBox var7 = mZ.a(var2, var3, var4, -1, 0, 0, 5, 7, 5, var5);
      return isAboveGround(var7) && StructureComponent.findIntersecting(var0, var7) == null?new StructureNetherBridgePieces$Corridor2(var6, var1, var7, var5):null;
   }

   public boolean addComponentParts(World var1, Random var2, StructureBoundingBox var3) {
      this.fillWithBlocks(var1, var3, 0, 0, 0, 4, 1, 4, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 0, 2, 0, 4, 5, 4, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 0, 2, 0, 0, 5, 4, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 0, 3, 1, 0, 4, 1, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 0, 3, 3, 0, 4, 3, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 4, 2, 0, 4, 5, 0, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 1, 2, 4, 4, 5, 4, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 1, 3, 4, 1, 4, 4, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 3, 3, 4, 3, 4, 4, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
      if(this.field_111020_b && var3.isVecInside(new BlockPos(this.getXWithOffset(1, 3), this.getYWithOffset(2), this.getZWithOffset(1, 3)))) {
         this.field_111020_b = false;
         this.generateChestContents(var1, var3, var2, 1, 2, 3, field_111019_a, 2 + var2.nextInt(4));
      }

      this.fillWithBlocks(var1, var3, 0, 6, 0, 4, 6, 4, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);

      for(int var4 = 0; var4 <= 4; ++var4) {
         for(int var5 = 0; var5 <= 4; ++var5) {
            this.replaceAirAndLiquidDownwards(var1, Blocks.nether_brick.getDefaultState(), var4, -1, var5, var3);
         }
      }

      return true;
   }
}
