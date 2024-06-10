/*  1:   */ package net.minecraft.src;
/*  2:   */ 
/*  3:   */ import java.lang.reflect.Constructor;
/*  4:   */ 
/*  5:   */ public class ReflectorConstructor
/*  6:   */ {
/*  7: 7 */   private ReflectorClass reflectorClass = null;
/*  8: 8 */   private Class[] parameterTypes = null;
/*  9: 9 */   private boolean checked = false;
/* 10:10 */   private Constructor targetConstructor = null;
/* 11:   */   
/* 12:   */   public ReflectorConstructor(ReflectorClass reflectorClass, Class[] parameterTypes)
/* 13:   */   {
/* 14:14 */     this.reflectorClass = reflectorClass;
/* 15:15 */     this.parameterTypes = parameterTypes;
/* 16:16 */     Constructor c = getTargetConstructor();
/* 17:   */   }
/* 18:   */   
/* 19:   */   public Constructor getTargetConstructor()
/* 20:   */   {
/* 21:21 */     if (this.checked) {
/* 22:23 */       return this.targetConstructor;
/* 23:   */     }
/* 24:27 */     this.checked = true;
/* 25:28 */     Class cls = this.reflectorClass.getTargetClass();
/* 26:30 */     if (cls == null) {
/* 27:32 */       return null;
/* 28:   */     }
/* 29:36 */     this.targetConstructor = findConstructor(cls, this.parameterTypes);
/* 30:38 */     if (this.targetConstructor == null) {
/* 31:40 */       Config.dbg("(Reflector) Constructor not present: " + cls.getName() + ", params: " + Config.arrayToString(this.parameterTypes));
/* 32:   */     }
/* 33:43 */     if ((this.targetConstructor != null) && (!this.targetConstructor.isAccessible())) {
/* 34:45 */       this.targetConstructor.setAccessible(true);
/* 35:   */     }
/* 36:48 */     return this.targetConstructor;
/* 37:   */   }
/* 38:   */   
/* 39:   */   private static Constructor findConstructor(Class cls, Class[] paramTypes)
/* 40:   */   {
/* 41:55 */     Constructor[] cs = cls.getDeclaredConstructors();
/* 42:57 */     for (int i = 0; i < cs.length; i++)
/* 43:   */     {
/* 44:59 */       Constructor c = cs[i];
/* 45:60 */       Class[] types = c.getParameterTypes();
/* 46:62 */       if (Reflector.matchesTypes(paramTypes, types)) {
/* 47:64 */         return c;
/* 48:   */       }
/* 49:   */     }
/* 50:68 */     return null;
/* 51:   */   }
/* 52:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.src.ReflectorConstructor
 * JD-Core Version:    0.7.0.1
 */