package cc.novoline.modules.configurations.property.object;

import cc.novoline.modules.configurations.property.object.BooleanProperty;
import cc.novoline.modules.configurations.property.object.BooleanProperty$1;
import org.jetbrains.annotations.Nullable;

final class BooleanProperty$ImmutableBooleanProperty extends BooleanProperty {
   private BooleanProperty$ImmutableBooleanProperty(@Nullable Boolean var1) {
      super(var1);
   }

   public void set(@Nullable Boolean var1) {
      throw new UnsupportedOperationException("Tried to change immutable property");
   }

   BooleanProperty$ImmutableBooleanProperty(Boolean var1, BooleanProperty$1 var2) {
      this(var1);
   }
}
