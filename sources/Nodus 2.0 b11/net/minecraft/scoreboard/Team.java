/*  1:   */ package net.minecraft.scoreboard;
/*  2:   */ 
/*  3:   */ public abstract class Team
/*  4:   */ {
/*  5:   */   private static final String __OBFID = "CL_00000621";
/*  6:   */   
/*  7:   */   public boolean isSameTeam(Team par1Team)
/*  8:   */   {
/*  9:12 */     return par1Team != null;
/* 10:   */   }
/* 11:   */   
/* 12:   */   public abstract String getRegisteredName();
/* 13:   */   
/* 14:   */   public abstract String func_142053_d(String paramString);
/* 15:   */   
/* 16:   */   public abstract boolean func_98297_h();
/* 17:   */   
/* 18:   */   public abstract boolean getAllowFriendlyFire();
/* 19:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.scoreboard.Team
 * JD-Core Version:    0.7.0.1
 */