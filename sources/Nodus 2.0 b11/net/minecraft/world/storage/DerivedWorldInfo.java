/*   1:    */ package net.minecraft.world.storage;
/*   2:    */ 
/*   3:    */ import net.minecraft.nbt.NBTTagCompound;
/*   4:    */ import net.minecraft.world.GameRules;
/*   5:    */ import net.minecraft.world.WorldSettings.GameType;
/*   6:    */ import net.minecraft.world.WorldType;
/*   7:    */ 
/*   8:    */ public class DerivedWorldInfo
/*   9:    */   extends WorldInfo
/*  10:    */ {
/*  11:    */   private final WorldInfo theWorldInfo;
/*  12:    */   private static final String __OBFID = "CL_00000584";
/*  13:    */   
/*  14:    */   public DerivedWorldInfo(WorldInfo par1WorldInfo)
/*  15:    */   {
/*  16: 16 */     this.theWorldInfo = par1WorldInfo;
/*  17:    */   }
/*  18:    */   
/*  19:    */   public NBTTagCompound getNBTTagCompound()
/*  20:    */   {
/*  21: 24 */     return this.theWorldInfo.getNBTTagCompound();
/*  22:    */   }
/*  23:    */   
/*  24:    */   public NBTTagCompound cloneNBTCompound(NBTTagCompound par1NBTTagCompound)
/*  25:    */   {
/*  26: 32 */     return this.theWorldInfo.cloneNBTCompound(par1NBTTagCompound);
/*  27:    */   }
/*  28:    */   
/*  29:    */   public long getSeed()
/*  30:    */   {
/*  31: 40 */     return this.theWorldInfo.getSeed();
/*  32:    */   }
/*  33:    */   
/*  34:    */   public int getSpawnX()
/*  35:    */   {
/*  36: 48 */     return this.theWorldInfo.getSpawnX();
/*  37:    */   }
/*  38:    */   
/*  39:    */   public int getSpawnY()
/*  40:    */   {
/*  41: 56 */     return this.theWorldInfo.getSpawnY();
/*  42:    */   }
/*  43:    */   
/*  44:    */   public int getSpawnZ()
/*  45:    */   {
/*  46: 64 */     return this.theWorldInfo.getSpawnZ();
/*  47:    */   }
/*  48:    */   
/*  49:    */   public long getWorldTotalTime()
/*  50:    */   {
/*  51: 69 */     return this.theWorldInfo.getWorldTotalTime();
/*  52:    */   }
/*  53:    */   
/*  54:    */   public long getWorldTime()
/*  55:    */   {
/*  56: 77 */     return this.theWorldInfo.getWorldTime();
/*  57:    */   }
/*  58:    */   
/*  59:    */   public long getSizeOnDisk()
/*  60:    */   {
/*  61: 82 */     return this.theWorldInfo.getSizeOnDisk();
/*  62:    */   }
/*  63:    */   
/*  64:    */   public NBTTagCompound getPlayerNBTTagCompound()
/*  65:    */   {
/*  66: 90 */     return this.theWorldInfo.getPlayerNBTTagCompound();
/*  67:    */   }
/*  68:    */   
/*  69:    */   public int getVanillaDimension()
/*  70:    */   {
/*  71: 99 */     return this.theWorldInfo.getVanillaDimension();
/*  72:    */   }
/*  73:    */   
/*  74:    */   public String getWorldName()
/*  75:    */   {
/*  76:107 */     return this.theWorldInfo.getWorldName();
/*  77:    */   }
/*  78:    */   
/*  79:    */   public int getSaveVersion()
/*  80:    */   {
/*  81:115 */     return this.theWorldInfo.getSaveVersion();
/*  82:    */   }
/*  83:    */   
/*  84:    */   public long getLastTimePlayed()
/*  85:    */   {
/*  86:123 */     return this.theWorldInfo.getLastTimePlayed();
/*  87:    */   }
/*  88:    */   
/*  89:    */   public boolean isThundering()
/*  90:    */   {
/*  91:131 */     return this.theWorldInfo.isThundering();
/*  92:    */   }
/*  93:    */   
/*  94:    */   public int getThunderTime()
/*  95:    */   {
/*  96:139 */     return this.theWorldInfo.getThunderTime();
/*  97:    */   }
/*  98:    */   
/*  99:    */   public boolean isRaining()
/* 100:    */   {
/* 101:147 */     return this.theWorldInfo.isRaining();
/* 102:    */   }
/* 103:    */   
/* 104:    */   public int getRainTime()
/* 105:    */   {
/* 106:155 */     return this.theWorldInfo.getRainTime();
/* 107:    */   }
/* 108:    */   
/* 109:    */   public WorldSettings.GameType getGameType()
/* 110:    */   {
/* 111:163 */     return this.theWorldInfo.getGameType();
/* 112:    */   }
/* 113:    */   
/* 114:    */   public void setSpawnX(int par1) {}
/* 115:    */   
/* 116:    */   public void setSpawnY(int par1) {}
/* 117:    */   
/* 118:    */   public void setSpawnZ(int par1) {}
/* 119:    */   
/* 120:    */   public void incrementTotalWorldTime(long par1) {}
/* 121:    */   
/* 122:    */   public void setWorldTime(long par1) {}
/* 123:    */   
/* 124:    */   public void setSpawnPosition(int par1, int par2, int par3) {}
/* 125:    */   
/* 126:    */   public void setWorldName(String par1Str) {}
/* 127:    */   
/* 128:    */   public void setSaveVersion(int par1) {}
/* 129:    */   
/* 130:    */   public void setThundering(boolean par1) {}
/* 131:    */   
/* 132:    */   public void setThunderTime(int par1) {}
/* 133:    */   
/* 134:    */   public void setRaining(boolean par1) {}
/* 135:    */   
/* 136:    */   public void setRainTime(int par1) {}
/* 137:    */   
/* 138:    */   public boolean isMapFeaturesEnabled()
/* 139:    */   {
/* 140:225 */     return this.theWorldInfo.isMapFeaturesEnabled();
/* 141:    */   }
/* 142:    */   
/* 143:    */   public boolean isHardcoreModeEnabled()
/* 144:    */   {
/* 145:233 */     return this.theWorldInfo.isHardcoreModeEnabled();
/* 146:    */   }
/* 147:    */   
/* 148:    */   public WorldType getTerrainType()
/* 149:    */   {
/* 150:238 */     return this.theWorldInfo.getTerrainType();
/* 151:    */   }
/* 152:    */   
/* 153:    */   public void setTerrainType(WorldType par1WorldType) {}
/* 154:    */   
/* 155:    */   public boolean areCommandsAllowed()
/* 156:    */   {
/* 157:248 */     return this.theWorldInfo.areCommandsAllowed();
/* 158:    */   }
/* 159:    */   
/* 160:    */   public boolean isInitialized()
/* 161:    */   {
/* 162:256 */     return this.theWorldInfo.isInitialized();
/* 163:    */   }
/* 164:    */   
/* 165:    */   public void setServerInitialized(boolean par1) {}
/* 166:    */   
/* 167:    */   public GameRules getGameRulesInstance()
/* 168:    */   {
/* 169:269 */     return this.theWorldInfo.getGameRulesInstance();
/* 170:    */   }
/* 171:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.storage.DerivedWorldInfo
 * JD-Core Version:    0.7.0.1
 */