package exhibition.module;

public interface Toggleable {
   void toggle();

   void onEnable();

   void onDisable();

   boolean isEnabled();
}
