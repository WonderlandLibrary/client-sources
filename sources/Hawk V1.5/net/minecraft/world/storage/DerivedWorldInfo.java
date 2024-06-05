package net.minecraft.world.storage;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.GameRules;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.WorldType;

public class DerivedWorldInfo extends WorldInfo {
   private final WorldInfo theWorldInfo;
   private static final String __OBFID = "CL_00000584";

   public void setServerInitialized(boolean var1) {
   }

   public boolean areCommandsAllowed() {
      return this.theWorldInfo.areCommandsAllowed();
   }

   public boolean isDifficultyLocked() {
      return this.theWorldInfo.isDifficultyLocked();
   }

   public long getLastTimePlayed() {
      return this.theWorldInfo.getLastTimePlayed();
   }

   public void setRainTime(int var1) {
   }

   public void setSpawnZ(int var1) {
   }

   public boolean isThundering() {
      return this.theWorldInfo.isThundering();
   }

   public boolean isHardcoreModeEnabled() {
      return this.theWorldInfo.isHardcoreModeEnabled();
   }

   public String getWorldName() {
      return this.theWorldInfo.getWorldName();
   }

   public long getWorldTotalTime() {
      return this.theWorldInfo.getWorldTotalTime();
   }

   public long getSizeOnDisk() {
      return this.theWorldInfo.getSizeOnDisk();
   }

   public NBTTagCompound getNBTTagCompound() {
      return this.theWorldInfo.getNBTTagCompound();
   }

   public void setSaveVersion(int var1) {
   }

   public void setTerrainType(WorldType var1) {
   }

   public void setDifficulty(EnumDifficulty var1) {
   }

   public long getWorldTime() {
      return this.theWorldInfo.getWorldTime();
   }

   public void setRaining(boolean var1) {
   }

   public boolean isMapFeaturesEnabled() {
      return this.theWorldInfo.isMapFeaturesEnabled();
   }

   public void setAllowCommands(boolean var1) {
   }

   public void setThunderTime(int var1) {
   }

   public void setSpawn(BlockPos var1) {
   }

   public DerivedWorldInfo(WorldInfo var1) {
      this.theWorldInfo = var1;
   }

   public void setSpawnY(int var1) {
   }

   public void setThundering(boolean var1) {
   }

   public void setSpawnX(int var1) {
   }

   public boolean isInitialized() {
      return this.theWorldInfo.isInitialized();
   }

   public void setWorldTime(long var1) {
   }

   public int getRainTime() {
      return this.theWorldInfo.getRainTime();
   }

   public NBTTagCompound getPlayerNBTTagCompound() {
      return this.theWorldInfo.getPlayerNBTTagCompound();
   }

   public int getSpawnZ() {
      return this.theWorldInfo.getSpawnZ();
   }

   public NBTTagCompound cloneNBTCompound(NBTTagCompound var1) {
      return this.theWorldInfo.cloneNBTCompound(var1);
   }

   public long getSeed() {
      return this.theWorldInfo.getSeed();
   }

   public void incrementTotalWorldTime(long var1) {
   }

   public int getSpawnY() {
      return this.theWorldInfo.getSpawnY();
   }

   public boolean isRaining() {
      return this.theWorldInfo.isRaining();
   }

   public int getSpawnX() {
      return this.theWorldInfo.getSpawnX();
   }

   public int getThunderTime() {
      return this.theWorldInfo.getThunderTime();
   }

   public WorldSettings.GameType getGameType() {
      return this.theWorldInfo.getGameType();
   }

   public void setWorldName(String var1) {
   }

   public int getSaveVersion() {
      return this.theWorldInfo.getSaveVersion();
   }

   public WorldType getTerrainType() {
      return this.theWorldInfo.getTerrainType();
   }

   public void setDifficultyLocked(boolean var1) {
   }

   public EnumDifficulty getDifficulty() {
      return this.theWorldInfo.getDifficulty();
   }

   public GameRules getGameRulesInstance() {
      return this.theWorldInfo.getGameRulesInstance();
   }
}
