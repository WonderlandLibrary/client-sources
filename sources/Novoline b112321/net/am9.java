package net;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureStart;

public class am9 {
   public static String a(StructureComponent var0) {
      return MapGenStructureIO.getStructureComponentName(var0);
   }

   public static String a(StructureStart var0) {
      return MapGenStructureIO.getStructureStartName(var0);
   }

   public static StructureComponent b(NBTTagCompound var0, World var1) {
      return MapGenStructureIO.getStructureComponent(var0, var1);
   }

   public static StructureStart a(NBTTagCompound var0, World var1) {
      return MapGenStructureIO.getStructureStart(var0, var1);
   }
}
