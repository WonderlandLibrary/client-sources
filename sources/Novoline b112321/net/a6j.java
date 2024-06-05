package net;

import java.util.List;
import java.util.Random;
import net.minecraft.world.gen.structure.StructureVillagePieces;

public class a6j {
   public static void a() {
      StructureVillagePieces.registerVillagePieces();
   }

   public static List a(Random var0, int var1) {
      return StructureVillagePieces.getStructureVillageWeightedPieceList(var0, var1);
   }
}
