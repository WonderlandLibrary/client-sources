package cc.novoline.modules.configurations.property;

import cc.novoline.modules.configurations.property.AbstractProperty;
import cc.novoline.modules.configurations.property.ImmutableProperty;

public abstract class AbstractImmutableProperty extends AbstractProperty implements ImmutableProperty {
   public final void set(Object var1) {
      super.set(var1);
   }
}
