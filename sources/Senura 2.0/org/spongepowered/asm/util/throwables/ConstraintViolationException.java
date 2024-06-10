/*    */ package org.spongepowered.asm.util.throwables;
/*    */ 
/*    */ import org.spongepowered.asm.util.ConstraintParser;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ConstraintViolationException
/*    */   extends Exception
/*    */ {
/*    */   private static final String MISSING_VALUE = "UNRESOLVED";
/*    */   private static final long serialVersionUID = 1L;
/*    */   private final ConstraintParser.Constraint constraint;
/*    */   private final String badValue;
/*    */   
/*    */   public ConstraintViolationException(ConstraintParser.Constraint constraint) {
/* 43 */     this.constraint = constraint;
/* 44 */     this.badValue = "UNRESOLVED";
/*    */   }
/*    */   
/*    */   public ConstraintViolationException(ConstraintParser.Constraint constraint, int badValue) {
/* 48 */     this.constraint = constraint;
/* 49 */     this.badValue = String.valueOf(badValue);
/*    */   }
/*    */   
/*    */   public ConstraintViolationException(String message, ConstraintParser.Constraint constraint) {
/* 53 */     super(message);
/* 54 */     this.constraint = constraint;
/* 55 */     this.badValue = "UNRESOLVED";
/*    */   }
/*    */   
/*    */   public ConstraintViolationException(String message, ConstraintParser.Constraint constraint, int badValue) {
/* 59 */     super(message);
/* 60 */     this.constraint = constraint;
/* 61 */     this.badValue = String.valueOf(badValue);
/*    */   }
/*    */   
/*    */   public ConstraintViolationException(Throwable cause, ConstraintParser.Constraint constraint) {
/* 65 */     super(cause);
/* 66 */     this.constraint = constraint;
/* 67 */     this.badValue = "UNRESOLVED";
/*    */   }
/*    */   
/*    */   public ConstraintViolationException(Throwable cause, ConstraintParser.Constraint constraint, int badValue) {
/* 71 */     super(cause);
/* 72 */     this.constraint = constraint;
/* 73 */     this.badValue = String.valueOf(badValue);
/*    */   }
/*    */   
/*    */   public ConstraintViolationException(String message, Throwable cause, ConstraintParser.Constraint constraint) {
/* 77 */     super(message, cause);
/* 78 */     this.constraint = constraint;
/* 79 */     this.badValue = "UNRESOLVED";
/*    */   }
/*    */   
/*    */   public ConstraintViolationException(String message, Throwable cause, ConstraintParser.Constraint constraint, int badValue) {
/* 83 */     super(message, cause);
/* 84 */     this.constraint = constraint;
/* 85 */     this.badValue = String.valueOf(badValue);
/*    */   }
/*    */   
/*    */   public ConstraintParser.Constraint getConstraint() {
/* 89 */     return this.constraint;
/*    */   }
/*    */   
/*    */   public String getBadValue() {
/* 93 */     return this.badValue;
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\as\\util\throwables\ConstraintViolationException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */