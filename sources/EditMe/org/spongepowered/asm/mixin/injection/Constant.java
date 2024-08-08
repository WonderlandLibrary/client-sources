package org.spongepowered.asm.mixin.injection;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Constant {
   boolean nullValue() default false;

   int intValue() default 0;

   float floatValue() default 0.0F;

   long longValue() default 0L;

   double doubleValue() default 0.0D;

   String stringValue() default "";

   Class classValue() default Object.class;

   int ordinal() default -1;

   String slice() default "";

   Constant.Condition[] expandZeroConditions() default {};

   boolean log() default false;

   public static enum Condition {
      LESS_THAN_ZERO(new int[]{155, 156}),
      LESS_THAN_OR_EQUAL_TO_ZERO(new int[]{158, 157}),
      GREATER_THAN_OR_EQUAL_TO_ZERO(LESS_THAN_ZERO),
      GREATER_THAN_ZERO(LESS_THAN_OR_EQUAL_TO_ZERO);

      private final int[] opcodes;
      private final Constant.Condition equivalence;
      private static final Constant.Condition[] $VALUES = new Constant.Condition[]{LESS_THAN_ZERO, LESS_THAN_OR_EQUAL_TO_ZERO, GREATER_THAN_OR_EQUAL_TO_ZERO, GREATER_THAN_ZERO};

      private Condition(int... var3) {
         this((Constant.Condition)null, var3);
      }

      private Condition(Constant.Condition var3) {
         this(var3, var3.opcodes);
      }

      private Condition(Constant.Condition var3, int... var4) {
         this.equivalence = var3 != null ? var3 : this;
         this.opcodes = var4;
      }

      public Constant.Condition getEquivalentCondition() {
         return this.equivalence;
      }

      public int[] getOpcodes() {
         return this.opcodes;
      }
   }
}
