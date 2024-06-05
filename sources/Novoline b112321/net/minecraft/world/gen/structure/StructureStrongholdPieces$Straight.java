package net.minecraft.world.gen.structure;

import java.util.List;
import java.util.Random;
import net.a1C;
import net.mZ;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureStrongholdPieces;
import net.minecraft.world.gen.structure.StructureStrongholdPieces$Stairs2;
import net.minecraft.world.gen.structure.StructureStrongholdPieces$Stronghold$Door;

public class StructureStrongholdPieces$Straight extends a1C {
   private boolean expandsX;
   private boolean expandsZ;

   public StructureStrongholdPieces$Straight() {
   }

   public StructureStrongholdPieces$Straight(int var1, Random var2, StructureBoundingBox var3, EnumFacing var4) {
      super(var1);
      this.coordBaseMode = var4;
      this.d = this.a(var2);
      this.boundingBox = var3;
      this.expandsX = var2.nextInt(2) == 0;
      this.expandsZ = var2.nextInt(2) == 0;
   }

   protected void writeStructureToNBT(NBTTagCompound var1) {
      super.writeStructureToNBT(var1);
      var1.setBoolean("Left", this.expandsX);
      var1.setBoolean("Right", this.expandsZ);
   }

   protected void readStructureFromNBT(NBTTagCompound var1) {
      super.readStructureFromNBT(var1);
      this.expandsX = var1.getBoolean("Left");
      this.expandsZ = var1.getBoolean("Right");
   }

   public void buildComponent(StructureComponent var1, List var2, Random var3) {
      this.c((StructureStrongholdPieces$Stairs2)var1, var2, var3, 1, 1);
      if(this.expandsX) {
         this.b((StructureStrongholdPieces$Stairs2)var1, var2, var3, 1, 2);
      }

      if(this.expandsZ) {
         this.a((StructureStrongholdPieces$Stairs2)var1, var2, var3, 1, 2);
      }

   }

   public static StructureStrongholdPieces$Straight func_175862_a(List var0, Random var1, int var2, int var3, int var4, EnumFacing var5, int var6) {
      StructureBoundingBox var7 = mZ.a(var2, var3, var4, -1, -1, 0, 5, 5, 7, var5);
      return a(var7) && StructureComponent.findIntersecting(var0, var7) == null?new StructureStrongholdPieces$Straight(var6, var1, var7, var5):null;
   }

   public boolean addComponentParts(World var1, Random var2, StructureBoundingBox var3) {
      if(this.isLiquidInStructureBoundingBox(var1, var3)) {
         return false;
      } else {
         this.fillWithRandomizedBlocks(var1, var3, 0, 0, 0, 4, 4, 6, true, var2, StructureStrongholdPieces.access$100());
         this.a(var1, var2, var3, this.d, 1, 1, 0);
         this.a(var1, var2, var3, StructureStrongholdPieces$Stronghold$Door.OPENING, 1, 1, 6);
         this.randomlyPlaceBlock(var1, var3, var2, 0.1F, 1, 2, 1, Blocks.torch.getDefaultState());
         this.randomlyPlaceBlock(var1, var3, var2, 0.1F, 3, 2, 1, Blocks.torch.getDefaultState());
         this.randomlyPlaceBlock(var1, var3, var2, 0.1F, 1, 2, 5, Blocks.torch.getDefaultState());
         this.randomlyPlaceBlock(var1, var3, var2, 0.1F, 3, 2, 5, Blocks.torch.getDefaultState());
         if(this.expandsX) {
            this.fillWithBlocks(var1, var3, 0, 1, 2, 0, 3, 4, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
         }

         if(this.expandsZ) {
            this.fillWithBlocks(var1, var3, 4, 1, 2, 4, 3, 4, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
         }

         return true;
      }
   }
}
