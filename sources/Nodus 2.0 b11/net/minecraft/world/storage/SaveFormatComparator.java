/*  1:   */ package net.minecraft.world.storage;
/*  2:   */ 
/*  3:   */ import net.minecraft.world.WorldSettings.GameType;
/*  4:   */ 
/*  5:   */ public class SaveFormatComparator
/*  6:   */   implements Comparable
/*  7:   */ {
/*  8:   */   private final String fileName;
/*  9:   */   private final String displayName;
/* 10:   */   private final long lastTimePlayed;
/* 11:   */   private final long sizeOnDisk;
/* 12:   */   private final boolean requiresConversion;
/* 13:   */   private final WorldSettings.GameType theEnumGameType;
/* 14:   */   private final boolean hardcore;
/* 15:   */   private final boolean cheatsEnabled;
/* 16:   */   private static final String __OBFID = "CL_00000601";
/* 17:   */   
/* 18:   */   public SaveFormatComparator(String par1Str, String par2Str, long par3, long par5, WorldSettings.GameType par7EnumGameType, boolean par8, boolean par9, boolean par10)
/* 19:   */   {
/* 20:24 */     this.fileName = par1Str;
/* 21:25 */     this.displayName = par2Str;
/* 22:26 */     this.lastTimePlayed = par3;
/* 23:27 */     this.sizeOnDisk = par5;
/* 24:28 */     this.theEnumGameType = par7EnumGameType;
/* 25:29 */     this.requiresConversion = par8;
/* 26:30 */     this.hardcore = par9;
/* 27:31 */     this.cheatsEnabled = par10;
/* 28:   */   }
/* 29:   */   
/* 30:   */   public String getFileName()
/* 31:   */   {
/* 32:39 */     return this.fileName;
/* 33:   */   }
/* 34:   */   
/* 35:   */   public String getDisplayName()
/* 36:   */   {
/* 37:47 */     return this.displayName;
/* 38:   */   }
/* 39:   */   
/* 40:   */   public boolean requiresConversion()
/* 41:   */   {
/* 42:52 */     return this.requiresConversion;
/* 43:   */   }
/* 44:   */   
/* 45:   */   public long getLastTimePlayed()
/* 46:   */   {
/* 47:57 */     return this.lastTimePlayed;
/* 48:   */   }
/* 49:   */   
/* 50:   */   public int compareTo(SaveFormatComparator par1SaveFormatComparator)
/* 51:   */   {
/* 52:62 */     return this.lastTimePlayed > par1SaveFormatComparator.lastTimePlayed ? -1 : this.lastTimePlayed < par1SaveFormatComparator.lastTimePlayed ? 1 : this.fileName.compareTo(par1SaveFormatComparator.fileName);
/* 53:   */   }
/* 54:   */   
/* 55:   */   public WorldSettings.GameType getEnumGameType()
/* 56:   */   {
/* 57:70 */     return this.theEnumGameType;
/* 58:   */   }
/* 59:   */   
/* 60:   */   public boolean isHardcoreModeEnabled()
/* 61:   */   {
/* 62:75 */     return this.hardcore;
/* 63:   */   }
/* 64:   */   
/* 65:   */   public boolean getCheatsEnabled()
/* 66:   */   {
/* 67:83 */     return this.cheatsEnabled;
/* 68:   */   }
/* 69:   */   
/* 70:   */   public int compareTo(Object par1Obj)
/* 71:   */   {
/* 72:88 */     return compareTo((SaveFormatComparator)par1Obj);
/* 73:   */   }
/* 74:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.storage.SaveFormatComparator
 * JD-Core Version:    0.7.0.1
 */