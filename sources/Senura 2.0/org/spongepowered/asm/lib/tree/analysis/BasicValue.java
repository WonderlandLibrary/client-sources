/*     */ package org.spongepowered.asm.lib.tree.analysis;
/*     */ 
/*     */ import org.spongepowered.asm.lib.Type;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BasicValue
/*     */   implements Value
/*     */ {
/*  43 */   public static final BasicValue UNINITIALIZED_VALUE = new BasicValue(null);
/*     */   
/*  45 */   public static final BasicValue INT_VALUE = new BasicValue(Type.INT_TYPE);
/*     */   
/*  47 */   public static final BasicValue FLOAT_VALUE = new BasicValue(Type.FLOAT_TYPE);
/*     */   
/*  49 */   public static final BasicValue LONG_VALUE = new BasicValue(Type.LONG_TYPE);
/*     */   
/*  51 */   public static final BasicValue DOUBLE_VALUE = new BasicValue(Type.DOUBLE_TYPE);
/*     */ 
/*     */   
/*  54 */   public static final BasicValue REFERENCE_VALUE = new BasicValue(
/*  55 */       Type.getObjectType("java/lang/Object"));
/*     */   
/*  57 */   public static final BasicValue RETURNADDRESS_VALUE = new BasicValue(Type.VOID_TYPE);
/*     */   
/*     */   private final Type type;
/*     */ 
/*     */   
/*     */   public BasicValue(Type type) {
/*  63 */     this.type = type;
/*     */   }
/*     */   
/*     */   public Type getType() {
/*  67 */     return this.type;
/*     */   }
/*     */   
/*     */   public int getSize() {
/*  71 */     return (this.type == Type.LONG_TYPE || this.type == Type.DOUBLE_TYPE) ? 2 : 1;
/*     */   }
/*     */   
/*     */   public boolean isReference() {
/*  75 */     return (this.type != null && (this.type
/*  76 */       .getSort() == 10 || this.type.getSort() == 9));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object value) {
/*  81 */     if (value == this)
/*  82 */       return true; 
/*  83 */     if (value instanceof BasicValue) {
/*  84 */       if (this.type == null) {
/*  85 */         return (((BasicValue)value).type == null);
/*     */       }
/*  87 */       return this.type.equals(((BasicValue)value).type);
/*     */     } 
/*     */     
/*  90 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  96 */     return (this.type == null) ? 0 : this.type.hashCode();
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 101 */     if (this == UNINITIALIZED_VALUE)
/* 102 */       return "."; 
/* 103 */     if (this == RETURNADDRESS_VALUE)
/* 104 */       return "A"; 
/* 105 */     if (this == REFERENCE_VALUE) {
/* 106 */       return "R";
/*     */     }
/* 108 */     return this.type.getDescriptor();
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\asm\lib\tree\analysis\BasicValue.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */