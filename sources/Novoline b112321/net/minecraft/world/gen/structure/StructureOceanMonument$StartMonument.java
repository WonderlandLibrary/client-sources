package net.minecraft.world.gen.structure;

import com.google.common.collect.Sets;
import java.util.Random;
import java.util.Set;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing$Plane;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureOceanMonumentPieces$MonumentBuilding;
import net.minecraft.world.gen.structure.StructureStart;

public class StructureOceanMonument$StartMonument extends StructureStart {
   private Set field_175791_c = Sets.newHashSet();
   private boolean field_175790_d;

   public StructureOceanMonument$StartMonument() {
   }

   public StructureOceanMonument$StartMonument(World var1, Random var2, int var3, int var4) {
      super(var3, var4);
      this.func_175789_b(var1, var2, var3, var4);
   }

   private void func_175789_b(World var1, Random var2, int var3, int var4) {
      var2.setSeed(var1.getSeed());
      long var5 = var2.nextLong();
      long var7 = var2.nextLong();
      long var9 = (long)var3 * var5;
      long var11 = (long)var4 * var7;
      var2.setSeed(var9 ^ var11 ^ var1.getSeed());
      int var13 = var3 * 16 + 8 - 29;
      int var14 = var4 * 16 + 8 - 29;
      EnumFacing var15 = EnumFacing$Plane.HORIZONTAL.random(var2);
      this.components.add(new StructureOceanMonumentPieces$MonumentBuilding(var2, var13, var14, var15));
      this.updateBoundingBox();
      this.field_175790_d = true;
   }

   public void generateStructure(World var1, Random var2, StructureBoundingBox var3) {
      if(!this.field_175790_d) {
         this.components.clear();
         this.func_175789_b(var1, var2, this.getChunkPosX(), this.getChunkPosZ());
      }

      super.generateStructure(var1, var2, var3);
   }

   public boolean func_175788_a(ChunkCoordIntPair var1) {
      return !this.field_175791_c.contains(var1) && super.func_175788_a(var1);
   }

   public void func_175787_b(ChunkCoordIntPair var1) {
      super.func_175787_b(var1);
      this.field_175791_c.add(var1);
   }

   public void writeToNBT(NBTTagCompound var1) {
      super.writeToNBT(var1);
      NBTTagList var2 = new NBTTagList();

      for(ChunkCoordIntPair var4 : this.field_175791_c) {
         NBTTagCompound var5 = new NBTTagCompound();
         var5.setInteger("X", var4.chunkXPos);
         var5.setInteger("Z", var4.chunkZPos);
         var2.appendTag(var5);
      }

      var1.setTag("Processed", var2);
   }

   public void readFromNBT(NBTTagCompound var1) {
      super.readFromNBT(var1);
      if(var1.hasKey("Processed", 9)) {
         NBTTagList var2 = var1.getTagList("Processed", 10);

         for(int var3 = 0; var3 < var2.tagCount(); ++var3) {
            NBTTagCompound var4 = var2.getCompoundTagAt(var3);
            this.field_175791_c.add(new ChunkCoordIntPair(var4.getInteger("X"), var4.getInteger("Z")));
         }
      }

   }
}
