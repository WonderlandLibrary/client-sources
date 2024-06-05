package net.minecraft.world;

import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.world.storage.WorldInfo;

public final class WorldSettings {
   private final boolean hardcoreEnabled;
   private boolean commandsAllowed;
   private final WorldSettings.GameType theGameType;
   private String worldName;
   private boolean bonusChestEnabled;
   private final boolean mapFeaturesEnabled;
   private final long seed;
   private static final String __OBFID = "CL_00000147";
   private final WorldType terrainType;

   public boolean isMapFeaturesEnabled() {
      return this.mapFeaturesEnabled;
   }

   public WorldType getTerrainType() {
      return this.terrainType;
   }

   public boolean getHardcoreEnabled() {
      return this.hardcoreEnabled;
   }

   public WorldSettings enableBonusChest() {
      this.bonusChestEnabled = true;
      return this;
   }

   public WorldSettings.GameType getGameType() {
      return this.theGameType;
   }

   public String getWorldName() {
      return this.worldName;
   }

   public long getSeed() {
      return this.seed;
   }

   public WorldSettings enableCommands() {
      this.commandsAllowed = true;
      return this;
   }

   public WorldSettings(long var1, WorldSettings.GameType var3, boolean var4, boolean var5, WorldType var6) {
      this.worldName = "";
      this.seed = var1;
      this.theGameType = var3;
      this.mapFeaturesEnabled = var4;
      this.hardcoreEnabled = var5;
      this.terrainType = var6;
   }

   public static WorldSettings.GameType getGameTypeById(int var0) {
      return WorldSettings.GameType.getByID(var0);
   }

   public WorldSettings(WorldInfo var1) {
      this(var1.getSeed(), var1.getGameType(), var1.isMapFeaturesEnabled(), var1.isHardcoreModeEnabled(), var1.getTerrainType());
   }

   public WorldSettings setWorldName(String var1) {
      this.worldName = var1;
      return this;
   }

   public boolean areCommandsAllowed() {
      return this.commandsAllowed;
   }

   public boolean isBonusChestEnabled() {
      return this.bonusChestEnabled;
   }

   public static enum GameType {
      NOT_SET("NOT_SET", 0, -1, "");

      private static final WorldSettings.GameType[] $VALUES = new WorldSettings.GameType[]{NOT_SET, SURVIVAL, CREATIVE, ADVENTURE, SPECTATOR};
      private static final String __OBFID = "CL_00000148";
      ADVENTURE("ADVENTURE", 3, 2, "adventure");

      String name;
      CREATIVE("CREATIVE", 2, 1, "creative");

      private static final WorldSettings.GameType[] ENUM$VALUES = new WorldSettings.GameType[]{NOT_SET, SURVIVAL, CREATIVE, ADVENTURE, SPECTATOR};
      SPECTATOR("SPECTATOR", 4, 3, "spectator"),
      SURVIVAL("SURVIVAL", 1, 0, "survival");

      int id;

      private GameType(String var3, int var4, int var5, String var6) {
         this.id = var5;
         this.name = var6;
      }

      public static WorldSettings.GameType getByName(String var0) {
         WorldSettings.GameType[] var1 = values();
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            WorldSettings.GameType var4 = var1[var3];
            if (var4.name.equals(var0)) {
               return var4;
            }
         }

         return SURVIVAL;
      }

      public boolean isCreative() {
         return this == CREATIVE;
      }

      public void configurePlayerCapabilities(PlayerCapabilities var1) {
         if (this == CREATIVE) {
            var1.allowFlying = true;
            var1.isCreativeMode = true;
            var1.disableDamage = true;
         } else if (this == SPECTATOR) {
            var1.allowFlying = true;
            var1.isCreativeMode = false;
            var1.disableDamage = true;
            var1.isFlying = true;
         } else {
            var1.allowFlying = false;
            var1.isCreativeMode = false;
            var1.disableDamage = false;
            var1.isFlying = false;
         }

         var1.allowEdit = !this.isAdventure();
      }

      public int getID() {
         return this.id;
      }

      public boolean isSurvivalOrAdventure() {
         return this == SURVIVAL || this == ADVENTURE;
      }

      public boolean isAdventure() {
         return this == ADVENTURE || this == SPECTATOR;
      }

      public static WorldSettings.GameType getByID(int var0) {
         WorldSettings.GameType[] var1 = values();
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            WorldSettings.GameType var4 = var1[var3];
            if (var4.id == var0) {
               return var4;
            }
         }

         return SURVIVAL;
      }

      public String getName() {
         return this.name;
      }
   }
}
