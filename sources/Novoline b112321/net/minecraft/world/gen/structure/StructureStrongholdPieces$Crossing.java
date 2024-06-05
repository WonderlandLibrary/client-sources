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

public class StructureStrongholdPieces$Crossing extends a1C {
   private boolean field_74996_b;
   private boolean field_74997_c;
   private boolean field_74995_d;
   private boolean field_74999_h;

   public StructureStrongholdPieces$Crossing() {
   }

   public StructureStrongholdPieces$Crossing(int var1, Random var2, StructureBoundingBox var3, EnumFacing var4) {
      super(var1);
      this.coordBaseMode = var4;
      this.d = this.a(var2);
      this.boundingBox = var3;
      this.field_74996_b = var2.nextBoolean();
      this.field_74997_c = var2.nextBoolean();
      this.field_74995_d = var2.nextBoolean();
      this.field_74999_h = var2.nextInt(3) > 0;
   }

   protected void writeStructureToNBT(NBTTagCompound var1) {
      super.writeStructureToNBT(var1);
      var1.setBoolean("leftLow", this.field_74996_b);
      var1.setBoolean("leftHigh", this.field_74997_c);
      var1.setBoolean("rightLow", this.field_74995_d);
      var1.setBoolean("rightHigh", this.field_74999_h);
   }

   protected void readStructureFromNBT(NBTTagCompound var1) {
      super.readStructureFromNBT(var1);
      this.field_74996_b = var1.getBoolean("leftLow");
      this.field_74997_c = var1.getBoolean("leftHigh");
      this.field_74995_d = var1.getBoolean("rightLow");
      this.field_74999_h = var1.getBoolean("rightHigh");
   }

   public void buildComponent(StructureComponent var1, List var2, Random var3) {
      int var4 = 3;
      int var5 = 5;
      if(this.coordBaseMode == EnumFacing.WEST || this.coordBaseMode == EnumFacing.NORTH) {
         var4 = 8 - var4;
         var5 = 8 - var5;
      }

      this.c((StructureStrongholdPieces$Stairs2)var1, var2, var3, 5, 1);
      if(this.field_74996_b) {
         this.b((StructureStrongholdPieces$Stairs2)var1, var2, var3, var4, 1);
      }

      if(this.field_74997_c) {
         this.b((StructureStrongholdPieces$Stairs2)var1, var2, var3, var5, 7);
      }

      if(this.field_74995_d) {
         this.a((StructureStrongholdPieces$Stairs2)var1, var2, var3, var4, 1);
      }

      if(this.field_74999_h) {
         this.a((StructureStrongholdPieces$Stairs2)var1, var2, var3, var5, 7);
      }

   }

   public static StructureStrongholdPieces$Crossing func_175866_a(List var0, Random var1, int var2, int var3, int var4, EnumFacing var5, int var6) {
      StructureBoundingBox var7 = mZ.a(var2, var3, var4, -4, -3, 0, 10, 9, 11, var5);
      return a(var7) && StructureComponent.findIntersecting(var0, var7) == null?new StructureStrongholdPieces$Crossing(var6, var1, var7, var5):null;
   }

   public boolean addComponentParts(World var1, Random var2, StructureBoundingBox var3) {
      if(this.isLiquidInStructureBoundingBox(var1, var3)) {
         return false;
      } else {
         this.fillWithRandomizedBlocks(var1, var3, 0, 0, 0, 9, 8, 10, true, var2, StructureStrongholdPieces.access$100());
         this.a(var1, var2, var3, this.d, 4, 3, 0);
         if(this.field_74996_b) {
            this.fillWithBlocks(var1, var3, 0, 3, 1, 0, 5, 3, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
         }

         if(this.field_74995_d) {
            this.fillWithBlocks(var1, var3, 9, 3, 1, 9, 5, 3, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
         }

         if(this.field_74997_c) {
            this.fillWithBlocks(var1, var3, 0, 5, 7, 0, 7, 9, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
         }

         if(this.field_74999_h) {
            this.fillWithBlocks(var1, var3, 9, 5, 7, 9, 7, 9, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
         }

         this.fillWithBlocks(var1, var3, 5, 1, 10, 7, 3, 10, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
         this.fillWithRandomizedBlocks(var1, var3, 1, 2, 1, 8, 2, 6, false, var2, StructureStrongholdPieces.access$100());
         this.fillWithRandomizedBlocks(var1, var3, 4, 1, 5, 4, 4, 9, false, var2, StructureStrongholdPieces.access$100());
         this.fillWithRandomizedBlocks(var1, var3, 8, 1, 5, 8, 4, 9, false, var2, StructureStrongholdPieces.access$100());
         this.fillWithRandomizedBlocks(var1, var3, 1, 4, 7, 3, 4, 9, false, var2, StructureStrongholdPieces.access$100());
         this.fillWithRandomizedBlocks(var1, var3, 1, 3, 5, 3, 3, 6, false, var2, StructureStrongholdPieces.access$100());
         this.fillWithBlocks(var1, var3, 1, 3, 4, 3, 3, 4, Blocks.stone_slab.getDefaultState(), Blocks.stone_slab.getDefaultState(), false);
         this.fillWithBlocks(var1, var3, 1, 4, 6, 3, 4, 6, Blocks.stone_slab.getDefaultState(), Blocks.stone_slab.getDefaultState(), false);
         this.fillWithRandomizedBlocks(var1, var3, 5, 1, 7, 7, 1, 8, false, var2, StructureStrongholdPieces.access$100());
         this.fillWithBlocks(var1, var3, 5, 1, 9, 7, 1, 9, Blocks.stone_slab.getDefaultState(), Blocks.stone_slab.getDefaultState(), false);
         this.fillWithBlocks(var1, var3, 5, 2, 7, 7, 2, 7, Blocks.stone_slab.getDefaultState(), Blocks.stone_slab.getDefaultState(), false);
         this.fillWithBlocks(var1, var3, 4, 5, 7, 4, 5, 9, Blocks.stone_slab.getDefaultState(), Blocks.stone_slab.getDefaultState(), false);
         this.fillWithBlocks(var1, var3, 8, 5, 7, 8, 5, 9, Blocks.stone_slab.getDefaultState(), Blocks.stone_slab.getDefaultState(), false);
         this.fillWithBlocks(var1, var3, 5, 5, 7, 7, 5, 9, Blocks.double_stone_slab.getDefaultState(), Blocks.double_stone_slab.getDefaultState(), false);
         this.setBlockState(var1, Blocks.torch.getDefaultState(), 6, 5, 6, var3);
         return true;
      }
   }
}
