/*  1:   */ package net.minecraft.scoreboard;
/*  2:   */ 
/*  3:   */ import java.util.List;
/*  4:   */ import java.util.Map;
/*  5:   */ 
/*  6:   */ public class ScoreDummyCriteria
/*  7:   */   implements IScoreObjectiveCriteria
/*  8:   */ {
/*  9:   */   private final String field_96644_g;
/* 10:   */   private static final String __OBFID = "CL_00000622";
/* 11:   */   
/* 12:   */   public ScoreDummyCriteria(String par1Str)
/* 13:   */   {
/* 14:12 */     this.field_96644_g = par1Str;
/* 15:13 */     IScoreObjectiveCriteria.field_96643_a.put(par1Str, this);
/* 16:   */   }
/* 17:   */   
/* 18:   */   public String func_96636_a()
/* 19:   */   {
/* 20:18 */     return this.field_96644_g;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public int func_96635_a(List par1List)
/* 24:   */   {
/* 25:23 */     return 0;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public boolean isReadOnly()
/* 29:   */   {
/* 30:28 */     return false;
/* 31:   */   }
/* 32:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.scoreboard.ScoreDummyCriteria
 * JD-Core Version:    0.7.0.1
 */