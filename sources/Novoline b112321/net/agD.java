package net;

import net.minecraft.util.BlockPos;
import net.minecraft.world.WorldProvider;

public class agD extends WorldProvider {
   protected void generateLightBrightnessTable() {
   }

   public boolean isSurfaceWorld() {
      return true;
   }

   public boolean canRespawnHere() {
      return true;
   }

   public boolean isSkyColored() {
      return true;
   }

   public int getAverageGroundLevel() {
      return 63;
   }

   public boolean doesXZShowFog(int var1, int var2) {
      return false;
   }

   public String getDimensionName() {
      return "";
   }

   public String getInternalNameSuffix() {
      return null;
   }

   public BlockPos getSpawnCoordinate() {
      return new BlockPos(0, 64, 0);
   }

   public boolean canCoordinateBeSpawn(int var1, int var2) {
      return true;
   }
}
