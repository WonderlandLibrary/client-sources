package cc.slack.features.modules.api.settings.impl;

import cc.slack.features.modules.api.settings.Value;

public class BooleanValue extends Value<Boolean> {
   public BooleanValue(String name, boolean defaultValue) {
      super(name, defaultValue, (Value.VisibilityCheck)null);
   }
}
