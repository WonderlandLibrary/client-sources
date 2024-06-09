/*    */ package optfine;
/*    */ 
/*    */ public class ReflectorClass
/*    */ {
/*  5 */   private String targetClassName = null;
/*    */   private boolean checked = false;
/*  7 */   private Class targetClass = null;
/*    */ 
/*    */   
/*    */   public ReflectorClass(String p_i55_1_) {
/* 11 */     this.targetClassName = p_i55_1_;
/* 12 */     Class oclass = getTargetClass();
/*    */   }
/*    */ 
/*    */   
/*    */   public ReflectorClass(Class p_i56_1_) {
/* 17 */     this.targetClass = p_i56_1_;
/* 18 */     this.targetClassName = p_i56_1_.getName();
/* 19 */     this.checked = true;
/*    */   }
/*    */ 
/*    */   
/*    */   public Class getTargetClass() {
/* 24 */     if (this.checked)
/*    */     {
/* 26 */       return this.targetClass;
/*    */     }
/*    */ 
/*    */     
/* 30 */     this.checked = true;
/*    */ 
/*    */     
/*    */     try {
/* 34 */       this.targetClass = Class.forName(this.targetClassName);
/*    */     }
/* 36 */     catch (ClassNotFoundException var2) {
/*    */       
/* 38 */       Config.log("(Reflector) Class not present: " + this.targetClassName);
/*    */     }
/* 40 */     catch (Throwable throwable) {
/*    */       
/* 42 */       throwable.printStackTrace();
/*    */     } 
/*    */     
/* 45 */     return this.targetClass;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean exists() {
/* 51 */     return (getTargetClass() != null);
/*    */   }
/*    */ 
/*    */   
/*    */   public String getTargetClassName() {
/* 56 */     return this.targetClassName;
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\optfine\ReflectorClass.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */