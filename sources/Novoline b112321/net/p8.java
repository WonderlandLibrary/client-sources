package net;

import java.util.List;
import java.util.Random;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureVillagePieces$Start;

public class p8 {
   public static StructureBoundingBox b(StructureVillagePieces$Start var0) {
      return var0.getBoundingBox();
   }

   public static WorldChunkManager a(StructureVillagePieces$Start var0) {
      return var0.getWorldChunkManager();
   }

   public static void a(StructureVillagePieces$Start var0, StructureComponent var1, List var2, Random var3) {
      var0.buildComponent(var1, var2, var3);
   }
}
