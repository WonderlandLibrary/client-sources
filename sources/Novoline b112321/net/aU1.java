package net;

import java.util.List;
import java.util.Random;
import net.bgN;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureMineshaftPieces;

public class aU1 extends StructureComponent {
   public aU1() {
   }

   public aU1(int var1, Random var2, StructureBoundingBox var3, EnumFacing var4) {
      super(var1);
      this.coordBaseMode = var4;
      this.boundingBox = var3;
   }

   protected void writeStructureToNBT(NBTTagCompound var1) {
   }

   protected void readStructureFromNBT(NBTTagCompound var1) {
   }

   public static StructureBoundingBox a(List var0, Random var1, int var2, int var3, int var4, EnumFacing var5) {
      StructureBoundingBox var6 = new StructureBoundingBox(var2, var3 - 5, var4, var2, var3 + 2, var4);
      switch(bgN.a[var5.ordinal()]) {
      case 1:
         var6.maxX = var2 + 2;
         var6.minZ = var4 - 8;
         break;
      case 2:
         var6.maxX = var2 + 2;
         var6.maxZ = var4 + 8;
         break;
      case 3:
         var6.minX = var2 - 8;
         var6.maxZ = var4 + 2;
         break;
      case 4:
         var6.maxX = var2 + 8;
         var6.maxZ = var4 + 2;
      }

      return StructureComponent.findIntersecting(var0, var6) != null?null:var6;
   }

   public void buildComponent(StructureComponent var1, List var2, Random var3) {
      int var4 = this.getComponentType();
      if(this.coordBaseMode != null) {
         switch(bgN.a[this.coordBaseMode.ordinal()]) {
         case 1:
            StructureMineshaftPieces.access$000(var1, var2, var3, this.boundingBox.minX, this.boundingBox.minY, this.boundingBox.minZ - 1, EnumFacing.NORTH, var4);
            break;
         case 2:
            StructureMineshaftPieces.access$000(var1, var2, var3, this.boundingBox.minX, this.boundingBox.minY, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, var4);
            break;
         case 3:
            StructureMineshaftPieces.access$000(var1, var2, var3, this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.minZ, EnumFacing.WEST, var4);
            break;
         case 4:
            StructureMineshaftPieces.access$000(var1, var2, var3, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.minZ, EnumFacing.EAST, var4);
         }
      }

   }

   public boolean addComponentParts(World var1, Random var2, StructureBoundingBox var3) {
      if(this.isLiquidInStructureBoundingBox(var1, var3)) {
         return false;
      } else {
         this.fillWithBlocks(var1, var3, 0, 5, 0, 2, 7, 1, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
         this.fillWithBlocks(var1, var3, 0, 0, 7, 2, 2, 8, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);

         for(int var4 = 0; var4 < 5; ++var4) {
            this.fillWithBlocks(var1, var3, 0, 5 - var4 - (var4 < 4?1:0), 2 + var4, 2, 7 - var4, 2 + var4, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
         }

         return true;
      }
   }
}
