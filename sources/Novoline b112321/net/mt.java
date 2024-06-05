package net;

public enum mt {
   DM("diramight.ttf"),
   FIXEDSYS("tahoma.ttf"),
   ICONFONT("stylesicons.ttf"),
   SF("SF.ttf"),
   SFBOLD("SFBOLD.ttf"),
   SFTHIN("SFREGULAR.ttf"),
   OXIDE("diramight.ttf"),
   VERDANA("VERDANA.ttf"),
   NOTIFICATIONS("NOTIFICATIONS.ttf");

   private final String a;
   private static String[] c;

   private mt(String var3) {
      this.a = var3;
   }

   public String a() {
      return this.a;
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
