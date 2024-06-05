package net.minecraft.realms;

import net.minecraft.world.storage.SaveFormatComparator;

public class RealmsLevelSummary implements Comparable {
   private SaveFormatComparator levelSummary;

   public RealmsLevelSummary(SaveFormatComparator var1) {
      this.levelSummary = var1;
   }

   public int getGameMode() {
      return this.levelSummary.getEnumGameType().getID();
   }

   public String getLevelId() {
      return this.levelSummary.getFileName();
   }

   public boolean hasCheats() {
      return this.levelSummary.getCheatsEnabled();
   }

   public boolean isHardcore() {
      return this.levelSummary.isHardcoreModeEnabled();
   }

   public boolean isRequiresConversion() {
      return this.levelSummary.requiresConversion();
   }

   public String getLevelName() {
      return this.levelSummary.getDisplayName();
   }

   public long getLastPlayed() {
      return this.levelSummary.getLastTimePlayed();
   }

   public int compareTo(SaveFormatComparator var1) {
      return this.levelSummary.compareTo(var1);
   }

   public long getSizeOnDisk() {
      return this.levelSummary.getSizeOnDisk();
   }

   public int compareTo(RealmsLevelSummary var1) {
      return this.levelSummary.getLastTimePlayed() < var1.getLastPlayed()?1:(this.levelSummary.getLastTimePlayed() > var1.getLastPlayed()?-1:this.levelSummary.getFileName().compareTo(var1.getLevelId()));
   }
}
