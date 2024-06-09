package exhibition.management.keybinding;

public interface Bindable {
   void setKeybind(Keybind var1);

   void onBindPress();

   void onBindRelease();
}
