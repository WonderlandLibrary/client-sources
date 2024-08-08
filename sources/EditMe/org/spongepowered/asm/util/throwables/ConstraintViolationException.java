package org.spongepowered.asm.util.throwables;

import org.spongepowered.asm.util.ConstraintParser;

public class ConstraintViolationException extends Exception {
   private static final String MISSING_VALUE = "UNRESOLVED";
   private static final long serialVersionUID = 1L;
   private final ConstraintParser.Constraint constraint;
   private final String badValue;

   public ConstraintViolationException(ConstraintParser.Constraint var1) {
      this.constraint = var1;
      this.badValue = "UNRESOLVED";
   }

   public ConstraintViolationException(ConstraintParser.Constraint var1, int var2) {
      this.constraint = var1;
      this.badValue = String.valueOf(var2);
   }

   public ConstraintViolationException(String var1, ConstraintParser.Constraint var2) {
      super(var1);
      this.constraint = var2;
      this.badValue = "UNRESOLVED";
   }

   public ConstraintViolationException(String var1, ConstraintParser.Constraint var2, int var3) {
      super(var1);
      this.constraint = var2;
      this.badValue = String.valueOf(var3);
   }

   public ConstraintViolationException(Throwable var1, ConstraintParser.Constraint var2) {
      super(var1);
      this.constraint = var2;
      this.badValue = "UNRESOLVED";
   }

   public ConstraintViolationException(Throwable var1, ConstraintParser.Constraint var2, int var3) {
      super(var1);
      this.constraint = var2;
      this.badValue = String.valueOf(var3);
   }

   public ConstraintViolationException(String var1, Throwable var2, ConstraintParser.Constraint var3) {
      super(var1, var2);
      this.constraint = var3;
      this.badValue = "UNRESOLVED";
   }

   public ConstraintViolationException(String var1, Throwable var2, ConstraintParser.Constraint var3, int var4) {
      super(var1, var2);
      this.constraint = var3;
      this.badValue = String.valueOf(var4);
   }

   public ConstraintParser.Constraint getConstraint() {
      return this.constraint;
   }

   public String getBadValue() {
      return this.badValue;
   }
}
