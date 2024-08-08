package org.spongepowered.asm.mixin.injection;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface At {
   String id() default "";

   String value();

   String slice() default "";

   At.Shift shift() default At.Shift.NONE;

   int by() default 0;

   String[] args() default {};

   String target() default "";

   int ordinal() default -1;

   int opcode() default -1;

   boolean remap() default true;

   public static enum Shift {
      NONE,
      BEFORE,
      AFTER,
      BY;

      private static final At.Shift[] $VALUES = new At.Shift[]{NONE, BEFORE, AFTER, BY};
   }
}
