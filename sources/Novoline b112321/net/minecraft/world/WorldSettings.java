package net.minecraft.world;

import net.minecraft.world.WorldSettings$GameType;
import net.minecraft.world.WorldType;
import net.minecraft.world.storage.WorldInfo;

public final class WorldSettings {
   private final long seed;
   private final WorldSettings$GameType theGameType;
   private final boolean mapFeaturesEnabled;
   private final boolean hardcoreEnabled;
   private final WorldType terrainType;
   private boolean commandsAllowed;
   private boolean bonusChestEnabled;
   private String worldName;

   public WorldSettings(long var1, WorldSettings$GameType var3, boolean var4, boolean var5, WorldType var6) {
      this.worldName = "";
      this.seed = var1;
      this.theGameType = var3;
      this.mapFeaturesEnabled = var4;
      this.hardcoreEnabled = var5;
      this.terrainType = var6;
   }

   public WorldSettings(WorldInfo var1) {
      this(var1.getSeed(), var1.getGameType(), var1.isMapFeaturesEnabled(), var1.isHardcoreModeEnabled(), var1.getTerrainType());
   }

   public WorldSettings enableBonusChest() {
      this.bonusChestEnabled = true;
      return this;
   }

   public WorldSettings enableCommands() {
      this.commandsAllowed = true;
      return this;
   }

   public boolean isBonusChestEnabled() {
      return this.bonusChestEnabled;
   }

   public long getSeed() {
      return this.seed;
   }

   public WorldSettings$GameType getGameType() {
      return this.theGameType;
   }

   public boolean getHardcoreEnabled() {
      return this.hardcoreEnabled;
   }

   public boolean isMapFeaturesEnabled() {
      return this.mapFeaturesEnabled;
   }

   public WorldType getTerrainType() {
      return this.terrainType;
   }

   public boolean areCommandsAllowed() {
      return this.commandsAllowed;
   }

   public static WorldSettings$GameType getGameTypeById(int var0) {
      return WorldSettings$GameType.getByID(var0);
   }

   public String getWorldName() {
      return this.worldName;
   }

   public WorldSettings setWorldName(String var1) {
      this.worldName = var1;
      return this;
   }
}
