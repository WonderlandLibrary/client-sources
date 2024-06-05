package net;

import java.util.List;
import java.util.Random;
import net.N4;
import net.mZ;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureVillagePieces;
import net.minecraft.world.gen.structure.StructureVillagePieces$Road;
import net.minecraft.world.gen.structure.StructureVillagePieces$Start;

public class a1v extends StructureVillagePieces$Road {
   private int g;

   public a1v() {
   }

   public a1v(StructureVillagePieces$Start var1, int var2, Random var3, StructureBoundingBox var4, EnumFacing var5) {
      super(var1, var2);
      this.coordBaseMode = var5;
      this.boundingBox = var4;
      this.g = Math.max(var4.getXSize(), var4.getZSize());
   }

   protected void writeStructureToNBT(NBTTagCompound var1) {
      super.writeStructureToNBT(var1);
      var1.setInteger("Length", this.g);
   }

   protected void readStructureFromNBT(NBTTagCompound var1) {
      super.readStructureFromNBT(var1);
      this.g = var1.getInteger("Length");
   }

   public void buildComponent(StructureComponent var1, List var2, Random var3) {
      boolean var4 = false;

      int var9;
      for(var9 = var3.nextInt(5); var9 < this.g - 8; var9 = var9 + 2 + var3.nextInt(5)) {
         StructureComponent var6 = this.getNextComponentNN((StructureVillagePieces$Start)var1, var2, var3, 0, var9);
         var9 = var9 + Math.max(var6.boundingBox.getXSize(), var6.boundingBox.getZSize());
         var4 = true;
      }

      for(var9 = var3.nextInt(5); var9 < this.g - 8; var9 = var9 + 2 + var3.nextInt(5)) {
         StructureComponent var12 = this.getNextComponentPP((StructureVillagePieces$Start)var1, var2, var3, 0, var9);
         var9 = var9 + Math.max(var12.boundingBox.getXSize(), var12.boundingBox.getZSize());
         var4 = true;
      }

      if(var3.nextInt(3) > 0 && this.coordBaseMode != null) {
         switch(N4.a[this.coordBaseMode.ordinal()]) {
         case 1:
            StructureVillagePieces.access$000((StructureVillagePieces$Start)var1, var2, var3, this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.minZ, EnumFacing.WEST, this.getComponentType());
            break;
         case 2:
            StructureVillagePieces.access$000((StructureVillagePieces$Start)var1, var2, var3, this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.maxZ - 2, EnumFacing.WEST, this.getComponentType());
            break;
         case 3:
            StructureVillagePieces.access$000((StructureVillagePieces$Start)var1, var2, var3, this.boundingBox.minX, this.boundingBox.minY, this.boundingBox.minZ - 1, EnumFacing.NORTH, this.getComponentType());
            break;
         case 4:
            StructureVillagePieces.access$000((StructureVillagePieces$Start)var1, var2, var3, this.boundingBox.maxX - 2, this.boundingBox.minY, this.boundingBox.minZ - 1, EnumFacing.NORTH, this.getComponentType());
         }
      }

      if(var3.nextInt(3) > 0 && this.coordBaseMode != null) {
         switch(N4.a[this.coordBaseMode.ordinal()]) {
         case 1:
            StructureVillagePieces.access$000((StructureVillagePieces$Start)var1, var2, var3, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.minZ, EnumFacing.EAST, this.getComponentType());
            break;
         case 2:
            StructureVillagePieces.access$000((StructureVillagePieces$Start)var1, var2, var3, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.maxZ - 2, EnumFacing.EAST, this.getComponentType());
            break;
         case 3:
            StructureVillagePieces.access$000((StructureVillagePieces$Start)var1, var2, var3, this.boundingBox.minX, this.boundingBox.minY, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, this.getComponentType());
            break;
         case 4:
            StructureVillagePieces.access$000((StructureVillagePieces$Start)var1, var2, var3, this.boundingBox.maxX - 2, this.boundingBox.minY, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, this.getComponentType());
         }
      }

   }

   public static StructureBoundingBox a(StructureVillagePieces$Start var0, List var1, Random var2, int var3, int var4, int var5, EnumFacing var6) {
      for(int var7 = 7 * MathHelper.getRandomIntegerInRange(var2, 3, 5); var7 >= 7; var7 -= 7) {
         StructureBoundingBox var8 = mZ.a(var3, var4, var5, 0, 0, 0, 3, 3, var7, var6);
         if(StructureComponent.findIntersecting(var1, var8) == null) {
            return var8;
         }
      }

      return null;
   }

   public boolean addComponentParts(World var1, Random var2, StructureBoundingBox var3) {
      IBlockState var4 = this.func_175847_a(Blocks.gravel.getDefaultState());
      IBlockState var5 = this.func_175847_a(Blocks.cobblestone.getDefaultState());

      for(int var6 = this.boundingBox.minX; var6 <= this.boundingBox.maxX; ++var6) {
         for(int var7 = this.boundingBox.minZ; var7 <= this.boundingBox.maxZ; ++var7) {
            BlockPos var8 = new BlockPos(var6, 64, var7);
            if(var3.isVecInside(var8)) {
               var8 = var1.getTopSolidOrLiquidBlock(var8).down();
               var1.setBlockState(var8, var4, 2);
               var1.setBlockState(var8.down(), var5, 2);
            }
         }
      }

      return true;
   }
}
