/*   1:    */ package net.minecraft.src;
/*   2:    */ 
/*   3:    */ import java.lang.reflect.Method;
/*   4:    */ 
/*   5:    */ public class ReflectorMethod
/*   6:    */ {
/*   7:    */   private ReflectorClass reflectorClass;
/*   8:    */   private String targetMethodName;
/*   9:    */   private Class[] targetMethodParameterTypes;
/*  10:    */   private boolean checked;
/*  11:    */   private Method targetMethod;
/*  12:    */   
/*  13:    */   public ReflectorMethod(ReflectorClass reflectorClass, String targetMethodName)
/*  14:    */   {
/*  15: 15 */     this(reflectorClass, targetMethodName, null);
/*  16:    */   }
/*  17:    */   
/*  18:    */   public ReflectorMethod(ReflectorClass reflectorClass, String targetMethodName, Class[] targetMethodParameterTypes)
/*  19:    */   {
/*  20: 20 */     this.reflectorClass = null;
/*  21: 21 */     this.targetMethodName = null;
/*  22: 22 */     this.targetMethodParameterTypes = null;
/*  23: 23 */     this.checked = false;
/*  24: 24 */     this.targetMethod = null;
/*  25: 25 */     this.reflectorClass = reflectorClass;
/*  26: 26 */     this.targetMethodName = targetMethodName;
/*  27: 27 */     this.targetMethodParameterTypes = targetMethodParameterTypes;
/*  28: 28 */     Method m = getTargetMethod();
/*  29:    */   }
/*  30:    */   
/*  31:    */   public Method getTargetMethod()
/*  32:    */   {
/*  33: 33 */     if (this.checked) {
/*  34: 35 */       return this.targetMethod;
/*  35:    */     }
/*  36: 39 */     this.checked = true;
/*  37: 40 */     Class cls = this.reflectorClass.getTargetClass();
/*  38: 42 */     if (cls == null) {
/*  39: 44 */       return null;
/*  40:    */     }
/*  41: 48 */     Method[] ms = cls.getDeclaredMethods();
/*  42: 49 */     int i = 0;
/*  43:    */     for (;;)
/*  44:    */     {
/*  45: 54 */       if (i >= ms.length)
/*  46:    */       {
/*  47: 56 */         Config.log("(Reflector) Method not pesent: " + cls.getName() + "." + this.targetMethodName);
/*  48: 57 */         return null;
/*  49:    */       }
/*  50: 60 */       Method m = ms[i];
/*  51: 62 */       if (m.getName().equals(this.targetMethodName))
/*  52:    */       {
/*  53: 64 */         if (this.targetMethodParameterTypes == null) {
/*  54:    */           break;
/*  55:    */         }
/*  56: 69 */         Class[] types = m.getParameterTypes();
/*  57: 71 */         if (Reflector.matchesTypes(this.targetMethodParameterTypes, types)) {
/*  58:    */           break;
/*  59:    */         }
/*  60:    */       }
/*  61: 77 */       i++;
/*  62:    */     }
/*  63:    */     Method m;
/*  64: 80 */     this.targetMethod = m;
/*  65: 82 */     if (!this.targetMethod.isAccessible()) {
/*  66: 84 */       this.targetMethod.setAccessible(true);
/*  67:    */     }
/*  68: 87 */     return this.targetMethod;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public boolean exists()
/*  72:    */   {
/*  73: 94 */     return this.targetMethod != null;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public Class getReturnType()
/*  77:    */   {
/*  78: 99 */     Method tm = getTargetMethod();
/*  79:100 */     return tm == null ? null : tm.getReturnType();
/*  80:    */   }
/*  81:    */   
/*  82:    */   public void deactivate()
/*  83:    */   {
/*  84:105 */     this.checked = true;
/*  85:106 */     this.targetMethod = null;
/*  86:    */   }
/*  87:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.src.ReflectorMethod
 * JD-Core Version:    0.7.0.1
 */