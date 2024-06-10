/*  1:   */ package net.minecraft.scoreboard;
/*  2:   */ 
/*  3:   */ import java.util.Iterator;
/*  4:   */ import java.util.List;
/*  5:   */ import net.minecraft.entity.player.EntityPlayer;
/*  6:   */ import net.minecraft.util.MathHelper;
/*  7:   */ 
/*  8:   */ public class ScoreHealthCriteria
/*  9:   */   extends ScoreDummyCriteria
/* 10:   */ {
/* 11:   */   private static final String __OBFID = "CL_00000623";
/* 12:   */   
/* 13:   */   public ScoreHealthCriteria(String par1Str)
/* 14:   */   {
/* 15:14 */     super(par1Str);
/* 16:   */   }
/* 17:   */   
/* 18:   */   public int func_96635_a(List par1List)
/* 19:   */   {
/* 20:19 */     float var2 = 0.0F;
/* 21:   */     EntityPlayer var4;
/* 22:22 */     for (Iterator var3 = par1List.iterator(); var3.hasNext(); var2 += var4.getHealth() + var4.getAbsorptionAmount()) {
/* 23:24 */       var4 = (EntityPlayer)var3.next();
/* 24:   */     }
/* 25:27 */     if (par1List.size() > 0) {
/* 26:29 */       var2 /= par1List.size();
/* 27:   */     }
/* 28:32 */     return MathHelper.ceiling_float_int(var2);
/* 29:   */   }
/* 30:   */   
/* 31:   */   public boolean isReadOnly()
/* 32:   */   {
/* 33:37 */     return true;
/* 34:   */   }
/* 35:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.scoreboard.ScoreHealthCriteria
 * JD-Core Version:    0.7.0.1
 */