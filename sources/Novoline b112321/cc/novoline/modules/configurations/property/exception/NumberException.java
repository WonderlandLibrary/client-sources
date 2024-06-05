package cc.novoline.modules.configurations.property.exception;

import cc.novoline.modules.configurations.property.AbstractNumberProperty;
import cc.novoline.modules.configurations.property.exception.PropertyException;

public class NumberException extends PropertyException {
   public NumberException(AbstractNumberProperty var1) {
      super(var1);
   }

   public NumberException(String var1, AbstractNumberProperty var2) {
      super((String)var1, var2);
   }

   public NumberException(String var1, Throwable var2, AbstractNumberProperty var3) {
      super(var1, var2, var3);
   }

   public NumberException(Throwable var1, AbstractNumberProperty var2) {
      super((Throwable)var1, var2);
   }
}
