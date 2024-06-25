package cc.slack.features.modules.api.settings.impl;

import cc.slack.features.modules.api.settings.Value;

public class SubCatagory extends Value<Boolean> {
   public SubCatagory(String name) {
      super(name, false, (Value.VisibilityCheck)null);
   }
}
