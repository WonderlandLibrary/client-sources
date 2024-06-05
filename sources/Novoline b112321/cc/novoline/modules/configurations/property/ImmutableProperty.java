package cc.novoline.modules.configurations.property;

import cc.novoline.modules.configurations.property.Property;

public interface ImmutableProperty extends Property {
   String a = "Tried to change immutable property";

   default void set(Object var1) {
      throw new UnsupportedOperationException(a);
   }
}
