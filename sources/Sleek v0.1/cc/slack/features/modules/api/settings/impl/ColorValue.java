package cc.slack.features.modules.api.settings.impl;

import cc.slack.features.modules.api.settings.Value;
import java.awt.Color;

public class ColorValue extends Value<Color> {
   public ColorValue(String name, Color defaultValue) {
      super(name, defaultValue, (Value.VisibilityCheck)null);
   }
}
