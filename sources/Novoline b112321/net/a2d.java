package net;

import java.util.List;
import net.minecraft.util.BlockPos;
import net.minecraft.village.Village;
import net.minecraft.village.VillageCollection;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;

public class a2d {
   public static void a(VillageCollection var0, BlockPos var1) {
      var0.addToVillagerPositionList(var1);
   }

   public static Village a(VillageCollection var0, BlockPos var1, int var2) {
      return var0.getNearestVillage(var1, var2);
   }

   public static List a(VillageCollection var0) {
      return var0.getVillageList();
   }

   public static String a(WorldProvider var0) {
      return VillageCollection.fileNameForProvider(var0);
   }

   public static void a(VillageCollection var0, World var1) {
      var0.setWorldsForAll(var1);
   }
}
