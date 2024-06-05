package net.minecraft.world.gen.structure;

import java.util.List;
import java.util.Random;
import net.mZ;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureNetherBridgePieces$Piece;

public class StructureNetherBridgePieces$End extends StructureNetherBridgePieces$Piece {
   private int fillSeed;

   public StructureNetherBridgePieces$End() {
   }

   public StructureNetherBridgePieces$End(int var1, Random var2, StructureBoundingBox var3, EnumFacing var4) {
      super(var1);
      this.coordBaseMode = var4;
      this.boundingBox = var3;
      this.fillSeed = var2.nextInt();
   }

   public static StructureNetherBridgePieces$End func_175884_a(List var0, Random var1, int var2, int var3, int var4, EnumFacing var5, int var6) {
      StructureBoundingBox var7 = mZ.a(var2, var3, var4, -1, -3, 0, 5, 10, 8, var5);
      return isAboveGround(var7) && StructureComponent.findIntersecting(var0, var7) == null?new StructureNetherBridgePieces$End(var6, var1, var7, var5):null;
   }

   protected void readStructureFromNBT(NBTTagCompound var1) {
      super.readStructureFromNBT(var1);
      this.fillSeed = var1.getInteger("Seed");
   }

   protected void writeStructureToNBT(NBTTagCompound var1) {
      super.writeStructureToNBT(var1);
      var1.setInteger("Seed", this.fillSeed);
   }

   public boolean addComponentParts(World var1, Random var2, StructureBoundingBox var3) {
      Random var4 = new Random((long)this.fillSeed);

      for(int var5 = 0; var5 <= 4; ++var5) {
         for(int var6 = 3; var6 <= 4; ++var6) {
            int var7 = var4.nextInt(8);
            this.fillWithBlocks(var1, var3, var5, var6, 0, var5, var6, var7, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
         }
      }

      int var8 = var4.nextInt(8);
      this.fillWithBlocks(var1, var3, 0, 5, 0, 0, 5, var8, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
      var8 = var4.nextInt(8);
      this.fillWithBlocks(var1, var3, 4, 5, 0, 4, 5, var8, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);

      for(var8 = 0; var8 <= 4; ++var8) {
         int var12 = var4.nextInt(5);
         this.fillWithBlocks(var1, var3, var8, 2, 0, var8, 2, var12, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
      }

      for(var8 = 0; var8 <= 4; ++var8) {
         for(int var13 = 0; var13 <= 1; ++var13) {
            int var14 = var4.nextInt(3);
            this.fillWithBlocks(var1, var3, var8, var13, 0, var8, var13, var14, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
         }
      }

      return true;
   }
}
