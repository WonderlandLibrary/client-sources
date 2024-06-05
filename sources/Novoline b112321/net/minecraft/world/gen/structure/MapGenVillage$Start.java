package net.minecraft.world.gen.structure;

import java.util.List;
import java.util.Random;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureStart;
import net.minecraft.world.gen.structure.StructureVillagePieces;
import net.minecraft.world.gen.structure.StructureVillagePieces$Road;
import net.minecraft.world.gen.structure.StructureVillagePieces$Start;

public class MapGenVillage$Start extends StructureStart {
   private boolean hasMoreThanTwoComponents;

   public MapGenVillage$Start() {
   }

   public MapGenVillage$Start(World var1, Random var2, int var3, int var4, int var5) {
      super(var3, var4);
      List var6 = StructureVillagePieces.getStructureVillageWeightedPieceList(var2, var5);
      StructureVillagePieces$Start var7 = new StructureVillagePieces$Start(var1.getWorldChunkManager(), 0, var2, (var3 << 4) + 2, (var4 << 4) + 2, var6, var5);
      this.components.add(var7);
      var7.buildComponent(var7, this.components, var2);
      List var8 = var7.field_74930_j;
      List var9 = var7.field_74932_i;

      while(!var8.isEmpty() || !var9.isEmpty()) {
         if(var8.isEmpty()) {
            int var10 = var2.nextInt(var9.size());
            StructureComponent var11 = (StructureComponent)var9.remove(var10);
            var11.buildComponent(var7, this.components, var2);
         } else {
            int var13 = var2.nextInt(var8.size());
            StructureComponent var15 = (StructureComponent)var8.remove(var13);
            var15.buildComponent(var7, this.components, var2);
         }
      }

      this.updateBoundingBox();
      int var14 = 0;

      for(StructureComponent var12 : this.components) {
         if(!(var12 instanceof StructureVillagePieces$Road)) {
            ++var14;
         }
      }

      this.hasMoreThanTwoComponents = var14 > 2;
   }

   public boolean isSizeableStructure() {
      return this.hasMoreThanTwoComponents;
   }

   public void writeToNBT(NBTTagCompound var1) {
      super.writeToNBT(var1);
      var1.setBoolean("Valid", this.hasMoreThanTwoComponents);
   }

   public void readFromNBT(NBTTagCompound var1) {
      super.readFromNBT(var1);
      this.hasMoreThanTwoComponents = var1.getBoolean("Valid");
   }
}
