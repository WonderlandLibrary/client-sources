/*  1:   */ package net.minecraft.scoreboard;
/*  2:   */ 
/*  3:   */ public class ScoreObjective
/*  4:   */ {
/*  5:   */   private final Scoreboard theScoreboard;
/*  6:   */   private final String name;
/*  7:   */   private final IScoreObjectiveCriteria objectiveCriteria;
/*  8:   */   private String displayName;
/*  9:   */   private static final String __OBFID = "CL_00000614";
/* 10:   */   
/* 11:   */   public ScoreObjective(Scoreboard par1Scoreboard, String par2Str, IScoreObjectiveCriteria par3ScoreObjectiveCriteria)
/* 12:   */   {
/* 13:15 */     this.theScoreboard = par1Scoreboard;
/* 14:16 */     this.name = par2Str;
/* 15:17 */     this.objectiveCriteria = par3ScoreObjectiveCriteria;
/* 16:18 */     this.displayName = par2Str;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public Scoreboard getScoreboard()
/* 20:   */   {
/* 21:23 */     return this.theScoreboard;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public String getName()
/* 25:   */   {
/* 26:28 */     return this.name;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public IScoreObjectiveCriteria getCriteria()
/* 30:   */   {
/* 31:33 */     return this.objectiveCriteria;
/* 32:   */   }
/* 33:   */   
/* 34:   */   public String getDisplayName()
/* 35:   */   {
/* 36:38 */     return this.displayName;
/* 37:   */   }
/* 38:   */   
/* 39:   */   public void setDisplayName(String par1Str)
/* 40:   */   {
/* 41:43 */     this.displayName = par1Str;
/* 42:44 */     this.theScoreboard.func_96532_b(this);
/* 43:   */   }
/* 44:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.scoreboard.ScoreObjective
 * JD-Core Version:    0.7.0.1
 */