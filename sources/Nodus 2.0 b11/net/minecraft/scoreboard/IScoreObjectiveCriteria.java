/*  1:   */ package net.minecraft.scoreboard;
/*  2:   */ 
/*  3:   */ import java.util.HashMap;
/*  4:   */ import java.util.List;
/*  5:   */ import java.util.Map;
/*  6:   */ 
/*  7:   */ public abstract interface IScoreObjectiveCriteria
/*  8:   */ {
/*  9: 9 */   public static final Map field_96643_a = new HashMap();
/* 10:10 */   public static final IScoreObjectiveCriteria field_96641_b = new ScoreDummyCriteria("dummy");
/* 11:11 */   public static final IScoreObjectiveCriteria deathCount = new ScoreDummyCriteria("deathCount");
/* 12:12 */   public static final IScoreObjectiveCriteria playerKillCount = new ScoreDummyCriteria("playerKillCount");
/* 13:13 */   public static final IScoreObjectiveCriteria totalKillCount = new ScoreDummyCriteria("totalKillCount");
/* 14:14 */   public static final IScoreObjectiveCriteria health = new ScoreHealthCriteria("health");
/* 15:   */   
/* 16:   */   public abstract String func_96636_a();
/* 17:   */   
/* 18:   */   public abstract int func_96635_a(List paramList);
/* 19:   */   
/* 20:   */   public abstract boolean isReadOnly();
/* 21:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.scoreboard.IScoreObjectiveCriteria
 * JD-Core Version:    0.7.0.1
 */