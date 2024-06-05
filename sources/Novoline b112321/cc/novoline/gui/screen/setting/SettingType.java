package cc.novoline.gui.screen.setting;

public enum SettingType {
   COMBOBOX,
   SELECTBOX,
   CHECKBOX,
   SLIDER,
   TEXTBOX,
   COLOR_PICKER,
   BINDABLE,
   GUI,
   BUTTON;

   private static boolean b;

   static {
      b(true);
   }

   public static void b(boolean var0) {
      b = var0;
   }

   public static boolean b() {
      return b;
   }

   public static boolean c() {
      boolean var0 = b();
      return true;
   }
}
