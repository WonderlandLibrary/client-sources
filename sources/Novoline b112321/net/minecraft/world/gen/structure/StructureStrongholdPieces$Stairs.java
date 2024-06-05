package net.minecraft.world.gen.structure;

import java.util.List;
import java.util.Random;
import net.a1C;
import net.mZ;
import net.minecraft.block.BlockStoneSlab$EnumType;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing$Plane;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureStrongholdPieces;
import net.minecraft.world.gen.structure.StructureStrongholdPieces$3;
import net.minecraft.world.gen.structure.StructureStrongholdPieces$Crossing;
import net.minecraft.world.gen.structure.StructureStrongholdPieces$Stairs2;
import net.minecraft.world.gen.structure.StructureStrongholdPieces$Stronghold$Door;

public class StructureStrongholdPieces$Stairs extends a1C {
   private boolean field_75024_a;

   public StructureStrongholdPieces$Stairs() {
   }

   public StructureStrongholdPieces$Stairs(int var1, Random var2, int var3, int var4) {
      super(var1);
      this.field_75024_a = true;
      this.coordBaseMode = EnumFacing$Plane.HORIZONTAL.random(var2);
      this.d = StructureStrongholdPieces$Stronghold$Door.OPENING;
      switch(StructureStrongholdPieces$3.$SwitchMap$net$minecraft$util$EnumFacing[this.coordBaseMode.ordinal()]) {
      case 1:
      case 4:
         this.boundingBox = new StructureBoundingBox(var3, 64, var4, var3 + 5 - 1, 74, var4 + 5 - 1);
         break;
      default:
         this.boundingBox = new StructureBoundingBox(var3, 64, var4, var3 + 5 - 1, 74, var4 + 5 - 1);
      }

   }

   public StructureStrongholdPieces$Stairs(int var1, Random var2, StructureBoundingBox var3, EnumFacing var4) {
      super(var1);
      this.field_75024_a = false;
      this.coordBaseMode = var4;
      this.d = this.a(var2);
      this.boundingBox = var3;
   }

   protected void writeStructureToNBT(NBTTagCompound var1) {
      super.writeStructureToNBT(var1);
      var1.setBoolean("Source", this.field_75024_a);
   }

   protected void readStructureFromNBT(NBTTagCompound var1) {
      super.readStructureFromNBT(var1);
      this.field_75024_a = var1.getBoolean("Source");
   }

   public void buildComponent(StructureComponent var1, List var2, Random var3) {
      if(this.field_75024_a) {
         StructureStrongholdPieces.access$202(StructureStrongholdPieces$Crossing.class);
      }

      this.c((StructureStrongholdPieces$Stairs2)var1, var2, var3, 1, 1);
   }

   public static StructureStrongholdPieces$Stairs func_175863_a(List var0, Random var1, int var2, int var3, int var4, EnumFacing var5, int var6) {
      StructureBoundingBox var7 = mZ.a(var2, var3, var4, -1, -7, 0, 5, 11, 5, var5);
      return a(var7) && StructureComponent.findIntersecting(var0, var7) == null?new StructureStrongholdPieces$Stairs(var6, var1, var7, var5):null;
   }

   public boolean addComponentParts(World var1, Random var2, StructureBoundingBox var3) {
      if(this.isLiquidInStructureBoundingBox(var1, var3)) {
         return false;
      } else {
         this.fillWithRandomizedBlocks(var1, var3, 0, 0, 0, 4, 10, 4, true, var2, StructureStrongholdPieces.access$100());
         this.a(var1, var2, var3, this.d, 1, 7, 0);
         this.a(var1, var2, var3, StructureStrongholdPieces$Stronghold$Door.OPENING, 1, 1, 4);
         this.setBlockState(var1, Blocks.stonebrick.getDefaultState(), 2, 6, 1, var3);
         this.setBlockState(var1, Blocks.stonebrick.getDefaultState(), 1, 5, 1, var3);
         this.setBlockState(var1, Blocks.stone_slab.getStateFromMeta(BlockStoneSlab$EnumType.STONE.getMetadata()), 1, 6, 1, var3);
         this.setBlockState(var1, Blocks.stonebrick.getDefaultState(), 1, 5, 2, var3);
         this.setBlockState(var1, Blocks.stonebrick.getDefaultState(), 1, 4, 3, var3);
         this.setBlockState(var1, Blocks.stone_slab.getStateFromMeta(BlockStoneSlab$EnumType.STONE.getMetadata()), 1, 5, 3, var3);
         this.setBlockState(var1, Blocks.stonebrick.getDefaultState(), 2, 4, 3, var3);
         this.setBlockState(var1, Blocks.stonebrick.getDefaultState(), 3, 3, 3, var3);
         this.setBlockState(var1, Blocks.stone_slab.getStateFromMeta(BlockStoneSlab$EnumType.STONE.getMetadata()), 3, 4, 3, var3);
         this.setBlockState(var1, Blocks.stonebrick.getDefaultState(), 3, 3, 2, var3);
         this.setBlockState(var1, Blocks.stonebrick.getDefaultState(), 3, 2, 1, var3);
         this.setBlockState(var1, Blocks.stone_slab.getStateFromMeta(BlockStoneSlab$EnumType.STONE.getMetadata()), 3, 3, 1, var3);
         this.setBlockState(var1, Blocks.stonebrick.getDefaultState(), 2, 2, 1, var3);
         this.setBlockState(var1, Blocks.stonebrick.getDefaultState(), 1, 1, 1, var3);
         this.setBlockState(var1, Blocks.stone_slab.getStateFromMeta(BlockStoneSlab$EnumType.STONE.getMetadata()), 1, 2, 1, var3);
         this.setBlockState(var1, Blocks.stonebrick.getDefaultState(), 1, 1, 2, var3);
         this.setBlockState(var1, Blocks.stone_slab.getStateFromMeta(BlockStoneSlab$EnumType.STONE.getMetadata()), 1, 1, 3, var3);
         return true;
      }
   }
}
