/*  1:   */ package net.minecraft.src;
/*  2:   */ 
/*  3:   */ import java.lang.reflect.Field;
/*  4:   */ 
/*  5:   */ public class ReflectorField
/*  6:   */ {
/*  7: 7 */   private ReflectorClass reflectorClass = null;
/*  8: 8 */   private String targetFieldName = null;
/*  9: 9 */   private boolean checked = false;
/* 10:10 */   private Field targetField = null;
/* 11:   */   
/* 12:   */   public ReflectorField(ReflectorClass reflectorClass, String targetFieldName)
/* 13:   */   {
/* 14:14 */     this.reflectorClass = reflectorClass;
/* 15:15 */     this.targetFieldName = targetFieldName;
/* 16:16 */     Field f = getTargetField();
/* 17:   */   }
/* 18:   */   
/* 19:   */   public Field getTargetField()
/* 20:   */   {
/* 21:21 */     if (this.checked) {
/* 22:23 */       return this.targetField;
/* 23:   */     }
/* 24:27 */     this.checked = true;
/* 25:28 */     Class cls = this.reflectorClass.getTargetClass();
/* 26:30 */     if (cls == null) {
/* 27:32 */       return null;
/* 28:   */     }
/* 29:   */     try
/* 30:   */     {
/* 31:38 */       this.targetField = cls.getDeclaredField(this.targetFieldName);
/* 32:40 */       if (!this.targetField.isAccessible()) {
/* 33:42 */         this.targetField.setAccessible(true);
/* 34:   */       }
/* 35:   */     }
/* 36:   */     catch (SecurityException var3)
/* 37:   */     {
/* 38:47 */       var3.printStackTrace();
/* 39:   */     }
/* 40:   */     catch (NoSuchFieldException var4)
/* 41:   */     {
/* 42:51 */       Config.log("(Reflector) Field not present: " + cls.getName() + "." + this.targetFieldName);
/* 43:   */     }
/* 44:54 */     return this.targetField;
/* 45:   */   }
/* 46:   */   
/* 47:   */   public Object getValue()
/* 48:   */   {
/* 49:61 */     return Reflector.getFieldValue(null, this);
/* 50:   */   }
/* 51:   */   
/* 52:   */   public void setValue(Object value)
/* 53:   */   {
/* 54:66 */     Reflector.setFieldValue(null, this, value);
/* 55:   */   }
/* 56:   */   
/* 57:   */   public boolean exists()
/* 58:   */   {
/* 59:71 */     return this.targetField != null;
/* 60:   */   }
/* 61:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.src.ReflectorField
 * JD-Core Version:    0.7.0.1
 */