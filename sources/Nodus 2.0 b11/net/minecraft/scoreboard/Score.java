/*   1:    */ package net.minecraft.scoreboard;
/*   2:    */ 
/*   3:    */ import java.util.Comparator;
/*   4:    */ import java.util.List;
/*   5:    */ 
/*   6:    */ public class Score
/*   7:    */ {
/*   8:  8 */   public static final Comparator field_96658_a = new Comparator()
/*   9:    */   {
/*  10:    */     private static final String __OBFID = "CL_00000618";
/*  11:    */     
/*  12:    */     public int compare(Score par1Score, Score par2Score)
/*  13:    */     {
/*  14: 13 */       return par1Score.getScorePoints() < par2Score.getScorePoints() ? -1 : par1Score.getScorePoints() > par2Score.getScorePoints() ? 1 : 0;
/*  15:    */     }
/*  16:    */     
/*  17:    */     public int compare(Object par1Obj, Object par2Obj)
/*  18:    */     {
/*  19: 17 */       return compare((Score)par1Obj, (Score)par2Obj);
/*  20:    */     }
/*  21:    */   };
/*  22:    */   private final Scoreboard theScoreboard;
/*  23:    */   private final ScoreObjective theScoreObjective;
/*  24:    */   private final String field_96654_d;
/*  25:    */   private int field_96655_e;
/*  26:    */   private static final String __OBFID = "CL_00000617";
/*  27:    */   
/*  28:    */   public Score(Scoreboard par1Scoreboard, ScoreObjective par2ScoreObjective, String par3Str)
/*  29:    */   {
/*  30: 28 */     this.theScoreboard = par1Scoreboard;
/*  31: 29 */     this.theScoreObjective = par2ScoreObjective;
/*  32: 30 */     this.field_96654_d = par3Str;
/*  33:    */   }
/*  34:    */   
/*  35:    */   public void func_96649_a(int par1)
/*  36:    */   {
/*  37: 35 */     if (this.theScoreObjective.getCriteria().isReadOnly()) {
/*  38: 37 */       throw new IllegalStateException("Cannot modify read-only score");
/*  39:    */     }
/*  40: 41 */     func_96647_c(getScorePoints() + par1);
/*  41:    */   }
/*  42:    */   
/*  43:    */   public void func_96646_b(int par1)
/*  44:    */   {
/*  45: 47 */     if (this.theScoreObjective.getCriteria().isReadOnly()) {
/*  46: 49 */       throw new IllegalStateException("Cannot modify read-only score");
/*  47:    */     }
/*  48: 53 */     func_96647_c(getScorePoints() - par1);
/*  49:    */   }
/*  50:    */   
/*  51:    */   public void func_96648_a()
/*  52:    */   {
/*  53: 59 */     if (this.theScoreObjective.getCriteria().isReadOnly()) {
/*  54: 61 */       throw new IllegalStateException("Cannot modify read-only score");
/*  55:    */     }
/*  56: 65 */     func_96649_a(1);
/*  57:    */   }
/*  58:    */   
/*  59:    */   public int getScorePoints()
/*  60:    */   {
/*  61: 71 */     return this.field_96655_e;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public void func_96647_c(int par1)
/*  65:    */   {
/*  66: 76 */     int var2 = this.field_96655_e;
/*  67: 77 */     this.field_96655_e = par1;
/*  68: 79 */     if (var2 != par1) {
/*  69: 81 */       func_96650_f().func_96536_a(this);
/*  70:    */     }
/*  71:    */   }
/*  72:    */   
/*  73:    */   public ScoreObjective func_96645_d()
/*  74:    */   {
/*  75: 87 */     return this.theScoreObjective;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public String getPlayerName()
/*  79:    */   {
/*  80: 92 */     return this.field_96654_d;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public Scoreboard func_96650_f()
/*  84:    */   {
/*  85: 97 */     return this.theScoreboard;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public void func_96651_a(List par1List)
/*  89:    */   {
/*  90:102 */     func_96647_c(this.theScoreObjective.getCriteria().func_96635_a(par1List));
/*  91:    */   }
/*  92:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.scoreboard.Score
 * JD-Core Version:    0.7.0.1
 */