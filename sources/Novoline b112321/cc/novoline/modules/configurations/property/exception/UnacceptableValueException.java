package cc.novoline.modules.configurations.property.exception;

import cc.novoline.modules.configurations.property.Property;
import cc.novoline.modules.configurations.property.exception.PropertyException;

public final class UnacceptableValueException extends PropertyException {
   public UnacceptableValueException(Property var1) {
      super(var1);
   }

   public UnacceptableValueException(String var1, Property var2) {
      super(var1, var2);
   }

   public UnacceptableValueException(String var1, Throwable var2, Property var3) {
      super(var1, var2, var3);
   }

   public UnacceptableValueException(Throwable var1, Property var2) {
      super(var1, var2);
   }
}
