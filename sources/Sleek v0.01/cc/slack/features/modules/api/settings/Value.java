package cc.slack.features.modules.api.settings;

public abstract class Value<T> {
   private final String name;
   private T value;
   private Value.VisibilityCheck check;

   public <Type extends Value<T>> Type require(Value.VisibilityCheck check) {
      this.check = check;
      return this;
   }

   public boolean isVisible() {
      return this.check != null ? this.check.check() : true;
   }

   public String getName() {
      return this.name;
   }

   public T getValue() {
      return this.value;
   }

   public Value.VisibilityCheck getCheck() {
      return this.check;
   }

   public Value(String name, T value, Value.VisibilityCheck check) {
      this.name = name;
      this.value = value;
      this.check = check;
   }

   public void setValue(T value) {
      this.value = value;
   }

   public interface VisibilityCheck {
      boolean check();
   }
}
