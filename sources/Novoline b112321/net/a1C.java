package net;

import java.util.List;
import java.util.Random;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureStrongholdPieces;
import net.minecraft.world.gen.structure.StructureStrongholdPieces$3;
import net.minecraft.world.gen.structure.StructureStrongholdPieces$Stairs2;
import net.minecraft.world.gen.structure.StructureStrongholdPieces$Stronghold$Door;

abstract class a1C extends StructureComponent {
   protected StructureStrongholdPieces$Stronghold$Door d = StructureStrongholdPieces$Stronghold$Door.OPENING;

   public a1C() {
   }

   protected a1C(int var1) {
      super(var1);
   }

   protected void writeStructureToNBT(NBTTagCompound var1) {
      var1.setString("EntryDoor", this.d.name());
   }

   protected void readStructureFromNBT(NBTTagCompound var1) {
      this.d = StructureStrongholdPieces$Stronghold$Door.valueOf(var1.getString("EntryDoor"));
   }

   protected void a(World var1, Random var2, StructureBoundingBox var3, StructureStrongholdPieces$Stronghold$Door var4, int var5, int var6, int var7) {
      switch(StructureStrongholdPieces$3.$SwitchMap$net$minecraft$world$gen$structure$StructureStrongholdPieces$Stronghold$Door[var4.ordinal()]) {
      case 1:
      default:
         this.fillWithBlocks(var1, var3, var5, var6, var7, var5 + 3 - 1, var6 + 3 - 1, var7, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
         break;
      case 2:
         this.setBlockState(var1, Blocks.stonebrick.getDefaultState(), var5, var6, var7, var3);
         this.setBlockState(var1, Blocks.stonebrick.getDefaultState(), var5, var6 + 1, var7, var3);
         this.setBlockState(var1, Blocks.stonebrick.getDefaultState(), var5, var6 + 2, var7, var3);
         this.setBlockState(var1, Blocks.stonebrick.getDefaultState(), var5 + 1, var6 + 2, var7, var3);
         this.setBlockState(var1, Blocks.stonebrick.getDefaultState(), var5 + 2, var6 + 2, var7, var3);
         this.setBlockState(var1, Blocks.stonebrick.getDefaultState(), var5 + 2, var6 + 1, var7, var3);
         this.setBlockState(var1, Blocks.stonebrick.getDefaultState(), var5 + 2, var6, var7, var3);
         this.setBlockState(var1, Blocks.oak_door.getDefaultState(), var5 + 1, var6, var7, var3);
         this.setBlockState(var1, Blocks.oak_door.getStateFromMeta(8), var5 + 1, var6 + 1, var7, var3);
         break;
      case 3:
         this.setBlockState(var1, Blocks.air.getDefaultState(), var5 + 1, var6, var7, var3);
         this.setBlockState(var1, Blocks.air.getDefaultState(), var5 + 1, var6 + 1, var7, var3);
         this.setBlockState(var1, Blocks.iron_bars.getDefaultState(), var5, var6, var7, var3);
         this.setBlockState(var1, Blocks.iron_bars.getDefaultState(), var5, var6 + 1, var7, var3);
         this.setBlockState(var1, Blocks.iron_bars.getDefaultState(), var5, var6 + 2, var7, var3);
         this.setBlockState(var1, Blocks.iron_bars.getDefaultState(), var5 + 1, var6 + 2, var7, var3);
         this.setBlockState(var1, Blocks.iron_bars.getDefaultState(), var5 + 2, var6 + 2, var7, var3);
         this.setBlockState(var1, Blocks.iron_bars.getDefaultState(), var5 + 2, var6 + 1, var7, var3);
         this.setBlockState(var1, Blocks.iron_bars.getDefaultState(), var5 + 2, var6, var7, var3);
         break;
      case 4:
         this.setBlockState(var1, Blocks.stonebrick.getDefaultState(), var5, var6, var7, var3);
         this.setBlockState(var1, Blocks.stonebrick.getDefaultState(), var5, var6 + 1, var7, var3);
         this.setBlockState(var1, Blocks.stonebrick.getDefaultState(), var5, var6 + 2, var7, var3);
         this.setBlockState(var1, Blocks.stonebrick.getDefaultState(), var5 + 1, var6 + 2, var7, var3);
         this.setBlockState(var1, Blocks.stonebrick.getDefaultState(), var5 + 2, var6 + 2, var7, var3);
         this.setBlockState(var1, Blocks.stonebrick.getDefaultState(), var5 + 2, var6 + 1, var7, var3);
         this.setBlockState(var1, Blocks.stonebrick.getDefaultState(), var5 + 2, var6, var7, var3);
         this.setBlockState(var1, Blocks.iron_door.getDefaultState(), var5 + 1, var6, var7, var3);
         this.setBlockState(var1, Blocks.iron_door.getStateFromMeta(8), var5 + 1, var6 + 1, var7, var3);
         this.setBlockState(var1, Blocks.stone_button.getStateFromMeta(this.getMetadataWithOffset(Blocks.stone_button, 4)), var5 + 2, var6 + 1, var7 + 1, var3);
         this.setBlockState(var1, Blocks.stone_button.getStateFromMeta(this.getMetadataWithOffset(Blocks.stone_button, 3)), var5 + 2, var6 + 1, var7 - 1, var3);
      }

   }

   protected StructureStrongholdPieces$Stronghold$Door a(Random var1) {
      int var2 = var1.nextInt(5);
      switch(var2) {
      case 0:
      case 1:
      default:
         return StructureStrongholdPieces$Stronghold$Door.OPENING;
      case 2:
         return StructureStrongholdPieces$Stronghold$Door.WOOD_DOOR;
      case 3:
         return StructureStrongholdPieces$Stronghold$Door.GRATES;
      case 4:
         return StructureStrongholdPieces$Stronghold$Door.IRON_DOOR;
      }
   }

   protected StructureComponent c(StructureStrongholdPieces$Stairs2 var1, List var2, Random var3, int var4, int var5) {
      if(this.coordBaseMode != null) {
         switch(StructureStrongholdPieces$3.$SwitchMap$net$minecraft$util$EnumFacing[this.coordBaseMode.ordinal()]) {
         case 1:
            return StructureStrongholdPieces.access$300(var1, var2, var3, this.boundingBox.minX + var4, this.boundingBox.minY + var5, this.boundingBox.maxZ + 1, this.coordBaseMode, this.getComponentType());
         case 2:
            return StructureStrongholdPieces.access$300(var1, var2, var3, this.boundingBox.minX - 1, this.boundingBox.minY + var5, this.boundingBox.minZ + var4, this.coordBaseMode, this.getComponentType());
         case 3:
            return StructureStrongholdPieces.access$300(var1, var2, var3, this.boundingBox.maxX + 1, this.boundingBox.minY + var5, this.boundingBox.minZ + var4, this.coordBaseMode, this.getComponentType());
         case 4:
            return StructureStrongholdPieces.access$300(var1, var2, var3, this.boundingBox.minX + var4, this.boundingBox.minY + var5, this.boundingBox.minZ - 1, this.coordBaseMode, this.getComponentType());
         }
      }

      return null;
   }

   protected StructureComponent b(StructureStrongholdPieces$Stairs2 var1, List var2, Random var3, int var4, int var5) {
      if(this.coordBaseMode != null) {
         switch(StructureStrongholdPieces$3.$SwitchMap$net$minecraft$util$EnumFacing[this.coordBaseMode.ordinal()]) {
         case 1:
            return StructureStrongholdPieces.access$300(var1, var2, var3, this.boundingBox.minX - 1, this.boundingBox.minY + var4, this.boundingBox.minZ + var5, EnumFacing.WEST, this.getComponentType());
         case 2:
            return StructureStrongholdPieces.access$300(var1, var2, var3, this.boundingBox.minX + var5, this.boundingBox.minY + var4, this.boundingBox.minZ - 1, EnumFacing.NORTH, this.getComponentType());
         case 3:
            return StructureStrongholdPieces.access$300(var1, var2, var3, this.boundingBox.minX + var5, this.boundingBox.minY + var4, this.boundingBox.minZ - 1, EnumFacing.NORTH, this.getComponentType());
         case 4:
            return StructureStrongholdPieces.access$300(var1, var2, var3, this.boundingBox.minX - 1, this.boundingBox.minY + var4, this.boundingBox.minZ + var5, EnumFacing.WEST, this.getComponentType());
         }
      }

      return null;
   }

   protected StructureComponent a(StructureStrongholdPieces$Stairs2 var1, List var2, Random var3, int var4, int var5) {
      if(this.coordBaseMode != null) {
         switch(StructureStrongholdPieces$3.$SwitchMap$net$minecraft$util$EnumFacing[this.coordBaseMode.ordinal()]) {
         case 1:
            return StructureStrongholdPieces.access$300(var1, var2, var3, this.boundingBox.maxX + 1, this.boundingBox.minY + var4, this.boundingBox.minZ + var5, EnumFacing.EAST, this.getComponentType());
         case 2:
            return StructureStrongholdPieces.access$300(var1, var2, var3, this.boundingBox.minX + var5, this.boundingBox.minY + var4, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, this.getComponentType());
         case 3:
            return StructureStrongholdPieces.access$300(var1, var2, var3, this.boundingBox.minX + var5, this.boundingBox.minY + var4, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, this.getComponentType());
         case 4:
            return StructureStrongholdPieces.access$300(var1, var2, var3, this.boundingBox.maxX + 1, this.boundingBox.minY + var4, this.boundingBox.minZ + var5, EnumFacing.EAST, this.getComponentType());
         }
      }

      return null;
   }

   protected static boolean a(StructureBoundingBox var0) {
      return var0.minY > 10;
   }
}
