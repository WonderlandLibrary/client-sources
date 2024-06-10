/*   1:    */ package net.minecraft.world;
/*   2:    */ 
/*   3:    */ import net.minecraft.entity.player.PlayerCapabilities;
/*   4:    */ import net.minecraft.world.storage.WorldInfo;
/*   5:    */ 
/*   6:    */ public final class WorldSettings
/*   7:    */ {
/*   8:    */   private final long seed;
/*   9:    */   private final GameType theGameType;
/*  10:    */   private final boolean mapFeaturesEnabled;
/*  11:    */   private final boolean hardcoreEnabled;
/*  12:    */   private final WorldType terrainType;
/*  13:    */   private boolean commandsAllowed;
/*  14:    */   private boolean bonusChestEnabled;
/*  15:    */   private String field_82751_h;
/*  16:    */   private static final String __OBFID = "CL_00000147";
/*  17:    */   
/*  18:    */   public WorldSettings(long par1, GameType par3EnumGameType, boolean par4, boolean par5, WorldType par6WorldType)
/*  19:    */   {
/*  20: 33 */     this.field_82751_h = "";
/*  21: 34 */     this.seed = par1;
/*  22: 35 */     this.theGameType = par3EnumGameType;
/*  23: 36 */     this.mapFeaturesEnabled = par4;
/*  24: 37 */     this.hardcoreEnabled = par5;
/*  25: 38 */     this.terrainType = par6WorldType;
/*  26:    */   }
/*  27:    */   
/*  28:    */   public WorldSettings(WorldInfo par1WorldInfo)
/*  29:    */   {
/*  30: 43 */     this(par1WorldInfo.getSeed(), par1WorldInfo.getGameType(), par1WorldInfo.isMapFeaturesEnabled(), par1WorldInfo.isHardcoreModeEnabled(), par1WorldInfo.getTerrainType());
/*  31:    */   }
/*  32:    */   
/*  33:    */   public WorldSettings enableBonusChest()
/*  34:    */   {
/*  35: 51 */     this.bonusChestEnabled = true;
/*  36: 52 */     return this;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public WorldSettings enableCommands()
/*  40:    */   {
/*  41: 60 */     this.commandsAllowed = true;
/*  42: 61 */     return this;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public WorldSettings func_82750_a(String par1Str)
/*  46:    */   {
/*  47: 66 */     this.field_82751_h = par1Str;
/*  48: 67 */     return this;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public boolean isBonusChestEnabled()
/*  52:    */   {
/*  53: 75 */     return this.bonusChestEnabled;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public long getSeed()
/*  57:    */   {
/*  58: 83 */     return this.seed;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public GameType getGameType()
/*  62:    */   {
/*  63: 91 */     return this.theGameType;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public boolean getHardcoreEnabled()
/*  67:    */   {
/*  68: 99 */     return this.hardcoreEnabled;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public boolean isMapFeaturesEnabled()
/*  72:    */   {
/*  73:107 */     return this.mapFeaturesEnabled;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public WorldType getTerrainType()
/*  77:    */   {
/*  78:112 */     return this.terrainType;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public boolean areCommandsAllowed()
/*  82:    */   {
/*  83:120 */     return this.commandsAllowed;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public static GameType getGameTypeById(int par0)
/*  87:    */   {
/*  88:128 */     return GameType.getByID(par0);
/*  89:    */   }
/*  90:    */   
/*  91:    */   public String func_82749_j()
/*  92:    */   {
/*  93:133 */     return this.field_82751_h;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public static enum GameType
/*  97:    */   {
/*  98:138 */     NOT_SET("NOT_SET", 0, -1, ""),  SURVIVAL("SURVIVAL", 1, 0, "survival"),  CREATIVE("CREATIVE", 2, 1, "creative"),  ADVENTURE("ADVENTURE", 3, 2, "adventure");
/*  99:    */     
/* 100:    */     int id;
/* 101:    */     String name;
/* 102:145 */     private static final GameType[] $VALUES = { NOT_SET, SURVIVAL, CREATIVE, ADVENTURE };
/* 103:    */     private static final String __OBFID = "CL_00000148";
/* 104:    */     
/* 105:    */     private GameType(String par1Str, int par2, int par3, String par4Str)
/* 106:    */     {
/* 107:150 */       this.id = par3;
/* 108:151 */       this.name = par4Str;
/* 109:    */     }
/* 110:    */     
/* 111:    */     public int getID()
/* 112:    */     {
/* 113:156 */       return this.id;
/* 114:    */     }
/* 115:    */     
/* 116:    */     public String getName()
/* 117:    */     {
/* 118:161 */       return this.name;
/* 119:    */     }
/* 120:    */     
/* 121:    */     public void configurePlayerCapabilities(PlayerCapabilities par1PlayerCapabilities)
/* 122:    */     {
/* 123:166 */       if (this == CREATIVE)
/* 124:    */       {
/* 125:168 */         par1PlayerCapabilities.allowFlying = true;
/* 126:169 */         par1PlayerCapabilities.isCreativeMode = true;
/* 127:170 */         par1PlayerCapabilities.disableDamage = true;
/* 128:    */       }
/* 129:    */       else
/* 130:    */       {
/* 131:174 */         par1PlayerCapabilities.allowFlying = false;
/* 132:175 */         par1PlayerCapabilities.isCreativeMode = false;
/* 133:176 */         par1PlayerCapabilities.disableDamage = false;
/* 134:177 */         par1PlayerCapabilities.isFlying = false;
/* 135:    */       }
/* 136:180 */       par1PlayerCapabilities.allowEdit = (!isAdventure());
/* 137:    */     }
/* 138:    */     
/* 139:    */     public boolean isAdventure()
/* 140:    */     {
/* 141:185 */       return this == ADVENTURE;
/* 142:    */     }
/* 143:    */     
/* 144:    */     public boolean isCreative()
/* 145:    */     {
/* 146:190 */       return this == CREATIVE;
/* 147:    */     }
/* 148:    */     
/* 149:    */     public boolean isSurvivalOrAdventure()
/* 150:    */     {
/* 151:195 */       return (this == SURVIVAL) || (this == ADVENTURE);
/* 152:    */     }
/* 153:    */     
/* 154:    */     public static GameType getByID(int par0)
/* 155:    */     {
/* 156:200 */       GameType[] var1 = values();
/* 157:201 */       int var2 = var1.length;
/* 158:203 */       for (int var3 = 0; var3 < var2; var3++)
/* 159:    */       {
/* 160:205 */         GameType var4 = var1[var3];
/* 161:207 */         if (var4.id == par0) {
/* 162:209 */           return var4;
/* 163:    */         }
/* 164:    */       }
/* 165:213 */       return SURVIVAL;
/* 166:    */     }
/* 167:    */     
/* 168:    */     public static GameType getByName(String par0Str)
/* 169:    */     {
/* 170:218 */       GameType[] var1 = values();
/* 171:219 */       int var2 = var1.length;
/* 172:221 */       for (int var3 = 0; var3 < var2; var3++)
/* 173:    */       {
/* 174:223 */         GameType var4 = var1[var3];
/* 175:225 */         if (var4.name.equals(par0Str)) {
/* 176:227 */           return var4;
/* 177:    */         }
/* 178:    */       }
/* 179:231 */       return SURVIVAL;
/* 180:    */     }
/* 181:    */   }
/* 182:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.WorldSettings
 * JD-Core Version:    0.7.0.1
 */