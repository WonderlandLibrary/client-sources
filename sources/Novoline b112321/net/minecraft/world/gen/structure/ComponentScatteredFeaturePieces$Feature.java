package net.minecraft.world.gen.structure;

import java.util.Random;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos$MutableBlockPos;
import net.minecraft.util.EnumFacing$Plane;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.ComponentScatteredFeaturePieces$1;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;

abstract class ComponentScatteredFeaturePieces$Feature extends StructureComponent {
   protected int scatteredFeatureSizeX;
   protected int scatteredFeatureSizeY;
   protected int scatteredFeatureSizeZ;
   protected int field_74936_d = -1;

   public ComponentScatteredFeaturePieces$Feature() {
   }

   protected ComponentScatteredFeaturePieces$Feature(Random var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      super(0);
      this.scatteredFeatureSizeX = var5;
      this.scatteredFeatureSizeY = var6;
      this.scatteredFeatureSizeZ = var7;
      this.coordBaseMode = EnumFacing$Plane.HORIZONTAL.random(var1);
      switch(ComponentScatteredFeaturePieces$1.$SwitchMap$net$minecraft$util$EnumFacing[this.coordBaseMode.ordinal()]) {
      case 1:
      case 2:
         this.boundingBox = new StructureBoundingBox(var2, var3, var4, var2 + var5 - 1, var3 + var6 - 1, var4 + var7 - 1);
         break;
      default:
         this.boundingBox = new StructureBoundingBox(var2, var3, var4, var2 + var7 - 1, var3 + var6 - 1, var4 + var5 - 1);
      }

   }

   protected void writeStructureToNBT(NBTTagCompound var1) {
      var1.setInteger("Width", this.scatteredFeatureSizeX);
      var1.setInteger("Height", this.scatteredFeatureSizeY);
      var1.setInteger("Depth", this.scatteredFeatureSizeZ);
      var1.setInteger("HPos", this.field_74936_d);
   }

   protected void readStructureFromNBT(NBTTagCompound var1) {
      this.scatteredFeatureSizeX = var1.getInteger("Width");
      this.scatteredFeatureSizeY = var1.getInteger("Height");
      this.scatteredFeatureSizeZ = var1.getInteger("Depth");
      this.field_74936_d = var1.getInteger("HPos");
   }

   protected boolean a(World var1, StructureBoundingBox var2, int var3) {
      if(this.field_74936_d >= 0) {
         return true;
      } else {
         int var4 = 0;
         int var5 = 0;
         BlockPos$MutableBlockPos var6 = new BlockPos$MutableBlockPos();

         for(int var7 = this.boundingBox.minZ; var7 <= this.boundingBox.maxZ; ++var7) {
            for(int var8 = this.boundingBox.minX; var8 <= this.boundingBox.maxX; ++var8) {
               var6.func_181079_c(var8, 64, var7);
               if(var2.isVecInside(var6)) {
                  var4 += Math.max(var1.getTopSolidOrLiquidBlock(var6).getY(), var1.provider.getAverageGroundLevel());
                  ++var5;
               }
            }
         }

         return false;
      }
   }
}
