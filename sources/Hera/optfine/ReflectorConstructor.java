/*    */ package optfine;
/*    */ 
/*    */ import java.lang.reflect.Constructor;
/*    */ 
/*    */ public class ReflectorConstructor
/*    */ {
/*  7 */   private ReflectorClass reflectorClass = null;
/*  8 */   private Class[] parameterTypes = null;
/*    */   private boolean checked = false;
/* 10 */   private Constructor targetConstructor = null;
/*    */ 
/*    */   
/*    */   public ReflectorConstructor(ReflectorClass p_i57_1_, Class[] p_i57_2_) {
/* 14 */     this.reflectorClass = p_i57_1_;
/* 15 */     this.parameterTypes = p_i57_2_;
/* 16 */     Constructor constructor = getTargetConstructor();
/*    */   }
/*    */ 
/*    */   
/*    */   public Constructor getTargetConstructor() {
/* 21 */     if (this.checked)
/*    */     {
/* 23 */       return this.targetConstructor;
/*    */     }
/*    */ 
/*    */     
/* 27 */     this.checked = true;
/* 28 */     Class oclass = this.reflectorClass.getTargetClass();
/*    */     
/* 30 */     if (oclass == null)
/*    */     {
/* 32 */       return null;
/*    */     }
/*    */ 
/*    */     
/* 36 */     this.targetConstructor = findConstructor(oclass, this.parameterTypes);
/*    */     
/* 38 */     if (this.targetConstructor == null)
/*    */     {
/* 40 */       Config.dbg("(Reflector) Constructor not present: " + oclass.getName() + ", params: " + Config.arrayToString((Object[])this.parameterTypes));
/*    */     }
/*    */     
/* 43 */     if (this.targetConstructor != null && !this.targetConstructor.isAccessible())
/*    */     {
/* 45 */       this.targetConstructor.setAccessible(true);
/*    */     }
/*    */     
/* 48 */     return this.targetConstructor;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static Constructor findConstructor(Class p_findConstructor_0_, Class[] p_findConstructor_1_) {
/* 55 */     Constructor[] aconstructor = (Constructor[])p_findConstructor_0_.getDeclaredConstructors();
/*    */     
/* 57 */     for (int i = 0; i < aconstructor.length; i++) {
/*    */       
/* 59 */       Constructor constructor = aconstructor[i];
/* 60 */       Class[] aclass = constructor.getParameterTypes();
/*    */       
/* 62 */       if (Reflector.matchesTypes(p_findConstructor_1_, aclass))
/*    */       {
/* 64 */         return constructor;
/*    */       }
/*    */     } 
/*    */     
/* 68 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean exists() {
/* 73 */     return this.checked ? ((this.targetConstructor != null)) : ((getTargetConstructor() != null));
/*    */   }
/*    */ 
/*    */   
/*    */   public void deactivate() {
/* 78 */     this.checked = true;
/* 79 */     this.targetConstructor = null;
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\optfine\ReflectorConstructor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */