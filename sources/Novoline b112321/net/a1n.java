package net;

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

public class a1n extends a1C {
   private int e;

   public a1n() {
   }

   public a1n(int var1, Random var2, StructureBoundingBox var3, EnumFacing var4) {
      super(var1);
      this.coordBaseMode = var4;
      this.boundingBox = var3;
      this.e = var4 != EnumFacing.NORTH && var4 != EnumFacing.SOUTH?var3.getXSize():var3.getZSize();
   }

   protected void writeStructureToNBT(NBTTagCompound var1) {
      super.writeStructureToNBT(var1);
      var1.setInteger("Steps", this.e);
   }

   protected void readStructureFromNBT(NBTTagCompound var1) {
      super.readStructureFromNBT(var1);
      this.e = var1.getInteger("Steps");
   }

   public static StructureBoundingBox a(List var0, Random var1, int var2, int var3, int var4, EnumFacing var5) {
      boolean var6 = true;
      StructureBoundingBox var7 = mZ.a(var2, var3, var4, -1, -1, 0, 5, 5, 4, var5);
      StructureComponent var8 = StructureComponent.findIntersecting(var0, var7);
      if(var8.getBoundingBox().minY == var7.minY) {
         for(int var9 = 3; var9 >= 1; --var9) {
            var7 = mZ.a(var2, var3, var4, -1, -1, 0, 5, 5, var9 - 1, var5);
            if(!var8.getBoundingBox().intersectsWith(var7)) {
               return mZ.a(var2, var3, var4, -1, -1, 0, 5, 5, var9, var5);
            }
         }
      }

      return null;
   }

   public boolean addComponentParts(World var1, Random var2, StructureBoundingBox var3) {
      if(this.isLiquidInStructureBoundingBox(var1, var3)) {
         return false;
      } else {
         for(int var4 = 0; var4 < this.e; ++var4) {
            this.setBlockState(var1, Blocks.stonebrick.getDefaultState(), 0, 0, var4, var3);
            this.setBlockState(var1, Blocks.stonebrick.getDefaultState(), 1, 0, var4, var3);
            this.setBlockState(var1, Blocks.stonebrick.getDefaultState(), 2, 0, var4, var3);
            this.setBlockState(var1, Blocks.stonebrick.getDefaultState(), 3, 0, var4, var3);
            this.setBlockState(var1, Blocks.stonebrick.getDefaultState(), 4, 0, var4, var3);

            for(int var5 = 1; var5 <= 3; ++var5) {
               this.setBlockState(var1, Blocks.stonebrick.getDefaultState(), 0, var5, var4, var3);
               this.setBlockState(var1, Blocks.air.getDefaultState(), 1, var5, var4, var3);
               this.setBlockState(var1, Blocks.air.getDefaultState(), 2, var5, var4, var3);
               this.setBlockState(var1, Blocks.air.getDefaultState(), 3, var5, var4, var3);
               this.setBlockState(var1, Blocks.stonebrick.getDefaultState(), 4, var5, var4, var3);
            }

            this.setBlockState(var1, Blocks.stonebrick.getDefaultState(), 0, 4, var4, var3);
            this.setBlockState(var1, Blocks.stonebrick.getDefaultState(), 1, 4, var4, var3);
            this.setBlockState(var1, Blocks.stonebrick.getDefaultState(), 2, 4, var4, var3);
            this.setBlockState(var1, Blocks.stonebrick.getDefaultState(), 3, 4, var4, var3);
            this.setBlockState(var1, Blocks.stonebrick.getDefaultState(), 4, 4, var4, var3);
         }

         return true;
      }
   }
}
