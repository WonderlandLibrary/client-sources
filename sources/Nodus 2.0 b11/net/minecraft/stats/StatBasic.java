/*  1:   */ package net.minecraft.stats;
/*  2:   */ 
/*  3:   */ import java.util.List;
/*  4:   */ import net.minecraft.util.IChatComponent;
/*  5:   */ 
/*  6:   */ public class StatBasic
/*  7:   */   extends StatBase
/*  8:   */ {
/*  9:   */   private static final String __OBFID = "CL_00001469";
/* 10:   */   
/* 11:   */   public StatBasic(String p_i45303_1_, IChatComponent p_i45303_2_, IStatType p_i45303_3_)
/* 12:   */   {
/* 13:11 */     super(p_i45303_1_, p_i45303_2_, p_i45303_3_);
/* 14:   */   }
/* 15:   */   
/* 16:   */   public StatBasic(String p_i45304_1_, IChatComponent p_i45304_2_)
/* 17:   */   {
/* 18:16 */     super(p_i45304_1_, p_i45304_2_);
/* 19:   */   }
/* 20:   */   
/* 21:   */   public StatBase registerStat()
/* 22:   */   {
/* 23:24 */     super.registerStat();
/* 24:25 */     StatList.generalStats.add(this);
/* 25:26 */     return this;
/* 26:   */   }
/* 27:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.stats.StatBasic
 * JD-Core Version:    0.7.0.1
 */