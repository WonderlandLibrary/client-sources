package vestige.setting.impl;

import java.awt.Color;
import java.util.function.Supplier;
import vestige.setting.AbstractSetting;

public class DescriptionSettings extends AbstractSetting {
   public DescriptionSettings(String name, Color color) {
      super(name, color);
   }

   public DescriptionSettings(String name, Supplier<Boolean> visibility, Color color) {
      super(name, visibility);
   }
}
