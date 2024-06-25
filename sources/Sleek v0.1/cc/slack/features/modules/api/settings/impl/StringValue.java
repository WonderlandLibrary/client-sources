package cc.slack.features.modules.api.settings.impl;

import cc.slack.features.modules.api.settings.Value;

public class StringValue extends Value<String> {
   public StringValue(String name, String defaultValue) {
      super(name, defaultValue, (Value.VisibilityCheck)null);
   }
}
