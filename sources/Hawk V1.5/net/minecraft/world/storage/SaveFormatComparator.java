package net.minecraft.world.storage;

import net.minecraft.world.WorldSettings;

public class SaveFormatComparator implements Comparable {
   private final long sizeOnDisk;
   private final boolean requiresConversion;
   private final long lastTimePlayed;
   private final String fileName;
   private static final String __OBFID = "CL_00000601";
   private final String displayName;
   private final WorldSettings.GameType theEnumGameType;
   private final boolean cheatsEnabled;
   private final boolean hardcore;

   public WorldSettings.GameType getEnumGameType() {
      return this.theEnumGameType;
   }

   public boolean getCheatsEnabled() {
      return this.cheatsEnabled;
   }

   public long func_154336_c() {
      return this.sizeOnDisk;
   }

   public boolean requiresConversion() {
      return this.requiresConversion;
   }

   public long getLastTimePlayed() {
      return this.lastTimePlayed;
   }

   public int compareTo(SaveFormatComparator var1) {
      return this.lastTimePlayed < var1.lastTimePlayed ? 1 : (this.lastTimePlayed > var1.lastTimePlayed ? -1 : this.fileName.compareTo(var1.fileName));
   }

   public int compareTo(Object var1) {
      return this.compareTo((SaveFormatComparator)var1);
   }

   public SaveFormatComparator(String var1, String var2, long var3, long var5, WorldSettings.GameType var7, boolean var8, boolean var9, boolean var10) {
      this.fileName = var1;
      this.displayName = var2;
      this.lastTimePlayed = var3;
      this.sizeOnDisk = var5;
      this.theEnumGameType = var7;
      this.requiresConversion = var8;
      this.hardcore = var9;
      this.cheatsEnabled = var10;
   }

   public String getFileName() {
      return this.fileName;
   }

   public String getDisplayName() {
      return this.displayName;
   }

   public boolean isHardcoreModeEnabled() {
      return this.hardcore;
   }
}
