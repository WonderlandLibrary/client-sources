package net;

import java.util.Random;
import net.minecraft.entity.EntityLiving$SpawnPlacementType;
import net.minecraft.util.BlockPos;
import net.minecraft.world.SpawnerAnimals;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.BiomeGenBase;

public class vu {
   public static boolean a(EntityLiving$SpawnPlacementType var0, World var1, BlockPos var2) {
      return SpawnerAnimals.canCreatureTypeSpawnAtLocation(var0, var1, var2);
   }

   public static int a(SpawnerAnimals var0, WorldServer var1, boolean var2, boolean var3, boolean var4) {
      return var0.a(var1, var2, var3, var4);
   }

   public static void a(World var0, BiomeGenBase var1, int var2, int var3, int var4, int var5, Random var6) {
      SpawnerAnimals.performWorldGenSpawning(var0, var1, var2, var3, var4, var5, var6);
   }
}
