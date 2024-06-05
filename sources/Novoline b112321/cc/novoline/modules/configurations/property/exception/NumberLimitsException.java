package cc.novoline.modules.configurations.property.exception;

import cc.novoline.modules.configurations.property.AbstractNumberProperty;
import cc.novoline.modules.configurations.property.exception.NumberException;

public final class NumberLimitsException extends NumberException {
   public NumberLimitsException(AbstractNumberProperty var1) {
      super(var1);
   }

   public NumberLimitsException(String var1, AbstractNumberProperty var2) {
      super(var1, var2);
   }

   public NumberLimitsException(String var1, Throwable var2, AbstractNumberProperty var3) {
      super(var1, var2, var3);
   }

   public NumberLimitsException(Throwable var1, AbstractNumberProperty var2) {
      super(var1, var2);
   }
}
