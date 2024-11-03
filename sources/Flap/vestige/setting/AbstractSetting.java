package vestige.setting;

import java.awt.Color;
import java.util.function.Supplier;

public abstract class AbstractSetting {
   private final String name;
   private Color color;
   private Supplier<Boolean> visibility = () -> {
      return true;
   };

   public AbstractSetting(String name) {
      this.name = name;
   }

   public AbstractSetting(String name, Supplier<Boolean> visibility) {
      this.name = name;
      this.visibility = visibility;
   }

   public AbstractSetting(String name, Color color) {
      this.name = name;
      this.color = color;
   }

   public String getName() {
      return this.name;
   }

   public Color getColor() {
      return this.color;
   }

   public Supplier<Boolean> getVisibility() {
      return this.visibility;
   }
}
