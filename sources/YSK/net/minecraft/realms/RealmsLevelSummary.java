package net.minecraft.realms;

import net.minecraft.world.storage.*;

public class RealmsLevelSummary implements Comparable<RealmsLevelSummary>
{
    private SaveFormatComparator levelSummary;
    
    public String getLevelName() {
        return this.levelSummary.getDisplayName();
    }
    
    @Override
    public int compareTo(final RealmsLevelSummary realmsLevelSummary) {
        int n;
        if (this.levelSummary.getLastTimePlayed() < realmsLevelSummary.getLastPlayed()) {
            n = " ".length();
            "".length();
            if (3 != 3) {
                throw null;
            }
        }
        else if (this.levelSummary.getLastTimePlayed() > realmsLevelSummary.getLastPlayed()) {
            n = -" ".length();
            "".length();
            if (4 == 2) {
                throw null;
            }
        }
        else {
            n = this.levelSummary.getFileName().compareTo(realmsLevelSummary.getLevelId());
        }
        return n;
    }
    
    public boolean isRequiresConversion() {
        return this.levelSummary.requiresConversion();
    }
    
    @Override
    public int compareTo(final Object o) {
        return this.compareTo((RealmsLevelSummary)o);
    }
    
    public RealmsLevelSummary(final SaveFormatComparator levelSummary) {
        this.levelSummary = levelSummary;
    }
    
    public boolean hasCheats() {
        return this.levelSummary.getCheatsEnabled();
    }
    
    public int getGameMode() {
        return this.levelSummary.getEnumGameType().getID();
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (-1 == 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public long getSizeOnDisk() {
        return this.levelSummary.getSizeOnDisk();
    }
    
    public long getLastPlayed() {
        return this.levelSummary.getLastTimePlayed();
    }
    
    public String getLevelId() {
        return this.levelSummary.getFileName();
    }
    
    public int compareTo(final SaveFormatComparator saveFormatComparator) {
        return this.levelSummary.compareTo(saveFormatComparator);
    }
    
    public boolean isHardcore() {
        return this.levelSummary.isHardcoreModeEnabled();
    }
}
