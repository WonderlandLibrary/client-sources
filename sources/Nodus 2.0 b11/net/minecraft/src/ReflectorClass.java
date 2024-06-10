/*  1:   */ package net.minecraft.src;
/*  2:   */ 
/*  3:   */ public class ReflectorClass
/*  4:   */ {
/*  5: 5 */   private String targetClassName = null;
/*  6: 6 */   private boolean checked = false;
/*  7: 7 */   private Class targetClass = null;
/*  8:   */   
/*  9:   */   public ReflectorClass(String targetClassName)
/* 10:   */   {
/* 11:11 */     this.targetClassName = targetClassName;
/* 12:12 */     Class cls = getTargetClass();
/* 13:   */   }
/* 14:   */   
/* 15:   */   public ReflectorClass(Class targetClass)
/* 16:   */   {
/* 17:17 */     this.targetClass = targetClass;
/* 18:18 */     this.targetClassName = targetClass.getName();
/* 19:19 */     this.checked = true;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public Class getTargetClass()
/* 23:   */   {
/* 24:24 */     if (this.checked) {
/* 25:26 */       return this.targetClass;
/* 26:   */     }
/* 27:30 */     this.checked = true;
/* 28:   */     try
/* 29:   */     {
/* 30:34 */       this.targetClass = Class.forName(this.targetClassName);
/* 31:   */     }
/* 32:   */     catch (ClassNotFoundException var2)
/* 33:   */     {
/* 34:38 */       Config.log("(Reflector) Class not present: " + this.targetClassName);
/* 35:   */     }
/* 36:   */     catch (Throwable var3)
/* 37:   */     {
/* 38:42 */       var3.printStackTrace();
/* 39:   */     }
/* 40:45 */     return this.targetClass;
/* 41:   */   }
/* 42:   */   
/* 43:   */   public boolean exists()
/* 44:   */   {
/* 45:51 */     return getTargetClass() != null;
/* 46:   */   }
/* 47:   */   
/* 48:   */   public String getTargetClassName()
/* 49:   */   {
/* 50:56 */     return this.targetClassName;
/* 51:   */   }
/* 52:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.src.ReflectorClass
 * JD-Core Version:    0.7.0.1
 */