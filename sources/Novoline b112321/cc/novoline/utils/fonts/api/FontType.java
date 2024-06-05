package cc.novoline.utils.fonts.api;

public enum FontType {
   DM("diramight.ttf"),
   FIXEDSYS("tahoma.ttf"),
   ICONFONT("stylesicons.ttf"),
   SF("SF.ttf"),
   SFBOLD("SFBOLD.ttf"),
   SFTHIN("SFREGULAR.ttf"),
   OXIDE("diramight.ttf"),
   VERDANA("VERDANA.ttf"),
   NOTIFICATIONS("NOTIFICATIONS.ttf");

   private final String fileName;
   private static String[] c;

   private FontType(String var3) {
      this.fileName = var3;
   }

   public String fileName() {
      return this.fileName;
   }

   static {
      b((String[])null);
   }

   public static void b(String[] var0) {
      c = var0;
   }

   public static String[] b() {
      return c;
   }
}
