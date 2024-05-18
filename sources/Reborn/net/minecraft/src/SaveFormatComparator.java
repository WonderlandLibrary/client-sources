package net.minecraft.src;

public class SaveFormatComparator implements Comparable
{
    private final String fileName;
    private final String displayName;
    private final long lastTimePlayed;
    private final long sizeOnDisk;
    private final boolean requiresConversion;
    private final EnumGameType theEnumGameType;
    private final boolean hardcore;
    private final boolean cheatsEnabled;
    
    public SaveFormatComparator(final String par1Str, final String par2Str, final long par3, final long par5, final EnumGameType par7EnumGameType, final boolean par8, final boolean par9, final boolean par10) {
        this.fileName = par1Str;
        this.displayName = par2Str;
        this.lastTimePlayed = par3;
        this.sizeOnDisk = par5;
        this.theEnumGameType = par7EnumGameType;
        this.requiresConversion = par8;
        this.hardcore = par9;
        this.cheatsEnabled = par10;
    }
    
    public String getFileName() {
        return this.fileName;
    }
    
    public String getDisplayName() {
        return this.displayName;
    }
    
    public boolean requiresConversion() {
        return this.requiresConversion;
    }
    
    public long getLastTimePlayed() {
        return this.lastTimePlayed;
    }
    
    public int compareTo(final SaveFormatComparator par1SaveFormatComparator) {
        return (this.lastTimePlayed < par1SaveFormatComparator.lastTimePlayed) ? 1 : ((this.lastTimePlayed > par1SaveFormatComparator.lastTimePlayed) ? -1 : this.fileName.compareTo(par1SaveFormatComparator.fileName));
    }
    
    public EnumGameType getEnumGameType() {
        return this.theEnumGameType;
    }
    
    public boolean isHardcoreModeEnabled() {
        return this.hardcore;
    }
    
    public boolean getCheatsEnabled() {
        return this.cheatsEnabled;
    }
    
    @Override
    public int compareTo(final Object par1Obj) {
        return this.compareTo((SaveFormatComparator)par1Obj);
    }
}
