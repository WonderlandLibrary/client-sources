/*  1:   */ package net.minecraft.stats;
/*  2:   */ 
/*  3:   */ import java.util.HashMap;
/*  4:   */ import java.util.Map;
/*  5:   */ import net.minecraft.entity.player.EntityPlayer;
/*  6:   */ import net.minecraft.util.IJsonSerializable;
/*  7:   */ import net.minecraft.util.TupleIntJsonSerializable;
/*  8:   */ 
/*  9:   */ public class StatFileWriter
/* 10:   */ {
/* 11:11 */   protected final Map field_150875_a = new HashMap();
/* 12:   */   private static final String __OBFID = "CL_00001481";
/* 13:   */   
/* 14:   */   public boolean hasAchievementUnlocked(Achievement par1Achievement)
/* 15:   */   {
/* 16:19 */     return writeStat(par1Achievement) > 0;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public boolean canUnlockAchievement(Achievement par1Achievement)
/* 20:   */   {
/* 21:27 */     return (par1Achievement.parentAchievement == null) || (hasAchievementUnlocked(par1Achievement.parentAchievement));
/* 22:   */   }
/* 23:   */   
/* 24:   */   public int func_150874_c(Achievement p_150874_1_)
/* 25:   */   {
/* 26:32 */     if (hasAchievementUnlocked(p_150874_1_)) {
/* 27:34 */       return 0;
/* 28:   */     }
/* 29:38 */     int var2 = 0;
/* 30:40 */     for (Achievement var3 = p_150874_1_.parentAchievement; (var3 != null) && (!hasAchievementUnlocked(var3)); var2++) {
/* 31:42 */       var3 = var3.parentAchievement;
/* 32:   */     }
/* 33:45 */     return var2;
/* 34:   */   }
/* 35:   */   
/* 36:   */   public void func_150871_b(EntityPlayer p_150871_1_, StatBase p_150871_2_, int p_150871_3_)
/* 37:   */   {
/* 38:51 */     if ((!p_150871_2_.isAchievement()) || (canUnlockAchievement((Achievement)p_150871_2_))) {
/* 39:53 */       func_150873_a(p_150871_1_, p_150871_2_, writeStat(p_150871_2_) + p_150871_3_);
/* 40:   */     }
/* 41:   */   }
/* 42:   */   
/* 43:   */   public void func_150873_a(EntityPlayer p_150873_1_, StatBase p_150873_2_, int p_150873_3_)
/* 44:   */   {
/* 45:59 */     TupleIntJsonSerializable var4 = (TupleIntJsonSerializable)this.field_150875_a.get(p_150873_2_);
/* 46:61 */     if (var4 == null)
/* 47:   */     {
/* 48:63 */       var4 = new TupleIntJsonSerializable();
/* 49:64 */       this.field_150875_a.put(p_150873_2_, var4);
/* 50:   */     }
/* 51:67 */     var4.setIntegerValue(p_150873_3_);
/* 52:   */   }
/* 53:   */   
/* 54:   */   public int writeStat(StatBase par1StatBase)
/* 55:   */   {
/* 56:72 */     TupleIntJsonSerializable var2 = (TupleIntJsonSerializable)this.field_150875_a.get(par1StatBase);
/* 57:73 */     return var2 == null ? 0 : var2.getIntegerValue();
/* 58:   */   }
/* 59:   */   
/* 60:   */   public IJsonSerializable func_150870_b(StatBase p_150870_1_)
/* 61:   */   {
/* 62:78 */     TupleIntJsonSerializable var2 = (TupleIntJsonSerializable)this.field_150875_a.get(p_150870_1_);
/* 63:79 */     return var2 != null ? var2.getJsonSerializableValue() : null;
/* 64:   */   }
/* 65:   */   
/* 66:   */   public IJsonSerializable func_150872_a(StatBase p_150872_1_, IJsonSerializable p_150872_2_)
/* 67:   */   {
/* 68:84 */     TupleIntJsonSerializable var3 = (TupleIntJsonSerializable)this.field_150875_a.get(p_150872_1_);
/* 69:86 */     if (var3 == null)
/* 70:   */     {
/* 71:88 */       var3 = new TupleIntJsonSerializable();
/* 72:89 */       this.field_150875_a.put(p_150872_1_, var3);
/* 73:   */     }
/* 74:92 */     var3.setJsonSerializableValue(p_150872_2_);
/* 75:93 */     return p_150872_2_;
/* 76:   */   }
/* 77:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.stats.StatFileWriter
 * JD-Core Version:    0.7.0.1
 */