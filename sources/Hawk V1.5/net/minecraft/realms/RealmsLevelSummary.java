package net.minecraft.realms;

import net.minecraft.world.storage.SaveFormatComparator;

public class RealmsLevelSummary implements Comparable {
   private SaveFormatComparator levelSummary;
   private static final String __OBFID = "CL_00001857";

   public long getSizeOnDisk() {
      return this.levelSummary.func_154336_c();
   }

   public int getGameMode() {
      return this.levelSummary.getEnumGameType().getID();
   }

   public int compareTo(RealmsLevelSummary var1) {
      return this.levelSummary.getLastTimePlayed() < var1.getLastPlayed() ? 1 : (this.levelSummary.getLastTimePlayed() > var1.getLastPlayed() ? -1 : this.levelSummary.getFileName().compareTo(var1.getLevelId()));
   }

   public String getLevelId() {
      return this.levelSummary.getFileName();
   }

   public long getLastPlayed() {
      return this.levelSummary.getLastTimePlayed();
   }

   public boolean isRequiresConversion() {
      return this.levelSummary.requiresConversion();
   }

   public boolean hasCheats() {
      return this.levelSummary.getCheatsEnabled();
   }

   public int compareTo(SaveFormatComparator var1) {
      return this.levelSummary.compareTo(var1);
   }

   public RealmsLevelSummary(SaveFormatComparator var1) {
      this.levelSummary = var1;
   }

   public boolean isHardcore() {
      return this.levelSummary.isHardcoreModeEnabled();
   }

   public int compareTo(Object var1) {
      return this.compareTo((RealmsLevelSummary)var1);
   }

   public String getLevelName() {
      return this.levelSummary.getDisplayName();
   }
}
