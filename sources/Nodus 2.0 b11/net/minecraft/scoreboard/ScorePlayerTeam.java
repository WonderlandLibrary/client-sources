/*   1:    */ package net.minecraft.scoreboard;
/*   2:    */ 
/*   3:    */ import java.util.Collection;
/*   4:    */ import java.util.HashSet;
/*   5:    */ import java.util.Set;
/*   6:    */ 
/*   7:    */ public class ScorePlayerTeam
/*   8:    */   extends Team
/*   9:    */ {
/*  10:    */   private final Scoreboard theScoreboard;
/*  11:    */   private final String field_96675_b;
/*  12: 13 */   private final Set membershipSet = new HashSet();
/*  13:    */   private String teamNameSPT;
/*  14: 15 */   private String namePrefixSPT = "";
/*  15: 16 */   private String colorSuffix = "";
/*  16: 17 */   private boolean allowFriendlyFire = true;
/*  17: 18 */   private boolean canSeeFriendlyInvisibles = true;
/*  18:    */   private static final String __OBFID = "CL_00000616";
/*  19:    */   
/*  20:    */   public ScorePlayerTeam(Scoreboard par1Scoreboard, String par2Str)
/*  21:    */   {
/*  22: 23 */     this.theScoreboard = par1Scoreboard;
/*  23: 24 */     this.field_96675_b = par2Str;
/*  24: 25 */     this.teamNameSPT = par2Str;
/*  25:    */   }
/*  26:    */   
/*  27:    */   public String getRegisteredName()
/*  28:    */   {
/*  29: 33 */     return this.field_96675_b;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public String func_96669_c()
/*  33:    */   {
/*  34: 38 */     return this.teamNameSPT;
/*  35:    */   }
/*  36:    */   
/*  37:    */   public void setTeamName(String par1Str)
/*  38:    */   {
/*  39: 43 */     if (par1Str == null) {
/*  40: 45 */       throw new IllegalArgumentException("Name cannot be null");
/*  41:    */     }
/*  42: 49 */     this.teamNameSPT = par1Str;
/*  43: 50 */     this.theScoreboard.func_96538_b(this);
/*  44:    */   }
/*  45:    */   
/*  46:    */   public Collection getMembershipCollection()
/*  47:    */   {
/*  48: 56 */     return this.membershipSet;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public String getColorPrefix()
/*  52:    */   {
/*  53: 64 */     return this.namePrefixSPT;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public void setNamePrefix(String par1Str)
/*  57:    */   {
/*  58: 69 */     if (par1Str == null) {
/*  59: 71 */       throw new IllegalArgumentException("Prefix cannot be null");
/*  60:    */     }
/*  61: 75 */     this.namePrefixSPT = par1Str;
/*  62: 76 */     this.theScoreboard.func_96538_b(this);
/*  63:    */   }
/*  64:    */   
/*  65:    */   public String getColorSuffix()
/*  66:    */   {
/*  67: 85 */     return this.colorSuffix;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public void setNameSuffix(String par1Str)
/*  71:    */   {
/*  72: 90 */     if (par1Str == null) {
/*  73: 92 */       throw new IllegalArgumentException("Suffix cannot be null");
/*  74:    */     }
/*  75: 96 */     this.colorSuffix = par1Str;
/*  76: 97 */     this.theScoreboard.func_96538_b(this);
/*  77:    */   }
/*  78:    */   
/*  79:    */   public String func_142053_d(String par1Str)
/*  80:    */   {
/*  81:103 */     return getColorPrefix() + par1Str + getColorSuffix();
/*  82:    */   }
/*  83:    */   
/*  84:    */   public static String formatPlayerName(Team par0Team, String par1Str)
/*  85:    */   {
/*  86:111 */     return par0Team == null ? par1Str : par0Team.func_142053_d(par1Str);
/*  87:    */   }
/*  88:    */   
/*  89:    */   public boolean getAllowFriendlyFire()
/*  90:    */   {
/*  91:116 */     return this.allowFriendlyFire;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public void setAllowFriendlyFire(boolean par1)
/*  95:    */   {
/*  96:121 */     this.allowFriendlyFire = par1;
/*  97:122 */     this.theScoreboard.func_96538_b(this);
/*  98:    */   }
/*  99:    */   
/* 100:    */   public boolean func_98297_h()
/* 101:    */   {
/* 102:127 */     return this.canSeeFriendlyInvisibles;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public void setSeeFriendlyInvisiblesEnabled(boolean par1)
/* 106:    */   {
/* 107:132 */     this.canSeeFriendlyInvisibles = par1;
/* 108:133 */     this.theScoreboard.func_96538_b(this);
/* 109:    */   }
/* 110:    */   
/* 111:    */   public int func_98299_i()
/* 112:    */   {
/* 113:138 */     int var1 = 0;
/* 114:140 */     if (getAllowFriendlyFire()) {
/* 115:142 */       var1 |= 0x1;
/* 116:    */     }
/* 117:145 */     if (func_98297_h()) {
/* 118:147 */       var1 |= 0x2;
/* 119:    */     }
/* 120:150 */     return var1;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public void func_98298_a(int par1)
/* 124:    */   {
/* 125:155 */     setAllowFriendlyFire((par1 & 0x1) > 0);
/* 126:156 */     setSeeFriendlyInvisiblesEnabled((par1 & 0x2) > 0);
/* 127:    */   }
/* 128:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.scoreboard.ScorePlayerTeam
 * JD-Core Version:    0.7.0.1
 */