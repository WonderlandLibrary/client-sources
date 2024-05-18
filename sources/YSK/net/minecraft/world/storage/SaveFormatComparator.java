package net.minecraft.world.storage;

import net.minecraft.world.*;

public class SaveFormatComparator implements Comparable<SaveFormatComparator>
{
    private final boolean cheatsEnabled;
    private final String displayName;
    private final long lastTimePlayed;
    private final String fileName;
    private final boolean requiresConversion;
    private final boolean hardcore;
    private final WorldSettings.GameType theEnumGameType;
    private final long sizeOnDisk;
    
    public String getFileName() {
        return this.fileName;
    }
    
    @Override
    public int compareTo(final SaveFormatComparator saveFormatComparator) {
        int n;
        if (this.lastTimePlayed < saveFormatComparator.lastTimePlayed) {
            n = " ".length();
            "".length();
            if (1 >= 3) {
                throw null;
            }
        }
        else if (this.lastTimePlayed > saveFormatComparator.lastTimePlayed) {
            n = -" ".length();
            "".length();
            if (3 == 4) {
                throw null;
            }
        }
        else {
            n = this.fileName.compareTo(saveFormatComparator.fileName);
        }
        return n;
    }
    
    public String getDisplayName() {
        return this.displayName;
    }
    
    public long getLastTimePlayed() {
        return this.lastTimePlayed;
    }
    
    public boolean getCheatsEnabled() {
        return this.cheatsEnabled;
    }
    
    public boolean requiresConversion() {
        return this.requiresConversion;
    }
    
    public long getSizeOnDisk() {
        return this.sizeOnDisk;
    }
    
    public SaveFormatComparator(final String fileName, final String displayName, final long lastTimePlayed, final long sizeOnDisk, final WorldSettings.GameType theEnumGameType, final boolean requiresConversion, final boolean hardcore, final boolean cheatsEnabled) {
        this.fileName = fileName;
        this.displayName = displayName;
        this.lastTimePlayed = lastTimePlayed;
        this.sizeOnDisk = sizeOnDisk;
        this.theEnumGameType = theEnumGameType;
        this.requiresConversion = requiresConversion;
        this.hardcore = hardcore;
        this.cheatsEnabled = cheatsEnabled;
    }
    
    public boolean isHardcoreModeEnabled() {
        return this.hardcore;
    }
    
    public WorldSettings.GameType getEnumGameType() {
        return this.theEnumGameType;
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
            if (-1 != -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public int compareTo(final Object o) {
        return this.compareTo((SaveFormatComparator)o);
    }
}
