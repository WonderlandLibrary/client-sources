/*  1:   */ package net.minecraft.util;
/*  2:   */ 
/*  3:   */ import java.lang.reflect.Field;
/*  4:   */ import java.lang.reflect.Modifier;
/*  5:   */ 
/*  6:   */ public abstract class ValueObject
/*  7:   */ {
/*  8:   */   private static final String __OBFID = "CL_00001173";
/*  9:   */   
/* 10:   */   public String toString()
/* 11:   */   {
/* 12:12 */     StringBuilder var1 = new StringBuilder("{");
/* 13:13 */     Field[] var2 = getClass().getFields();
/* 14:14 */     int var3 = var2.length;
/* 15:16 */     for (int var4 = 0; var4 < var3; var4++)
/* 16:   */     {
/* 17:18 */       Field var5 = var2[var4];
/* 18:20 */       if (!func_148766_a(var5)) {
/* 19:   */         try
/* 20:   */         {
/* 21:24 */           var1.append(var5.getName()).append("=").append(var5.get(this)).append(" ");
/* 22:   */         }
/* 23:   */         catch (IllegalAccessException localIllegalAccessException) {}
/* 24:   */       }
/* 25:   */     }
/* 26:33 */     var1.deleteCharAt(var1.length() - 1);
/* 27:34 */     var1.append('}');
/* 28:35 */     return var1.toString();
/* 29:   */   }
/* 30:   */   
/* 31:   */   private static boolean func_148766_a(Field p_148766_0_)
/* 32:   */   {
/* 33:40 */     return Modifier.isStatic(p_148766_0_.getModifiers());
/* 34:   */   }
/* 35:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.util.ValueObject
 * JD-Core Version:    0.7.0.1
 */