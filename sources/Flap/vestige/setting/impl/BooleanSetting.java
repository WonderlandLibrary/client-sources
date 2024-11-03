package vestige.setting.impl;

import java.util.function.Supplier;
import vestige.setting.AbstractSetting;

public class BooleanSetting extends AbstractSetting {
   private boolean enabled;

   public BooleanSetting(String name, boolean defaultState) {
      super(name);
      this.enabled = defaultState;
   }

   public BooleanSetting(String name, Supplier<Boolean> visibility, boolean defaultState) {
      super(name, visibility);
      this.enabled = defaultState;
   }

   public boolean isEnabled() {
      return this.enabled;
   }

   public void setEnabled(boolean enabled) {
      this.enabled = enabled;
   }
}
