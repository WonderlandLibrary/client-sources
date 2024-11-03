package vestige.util.keyauth.api;

import vestige.util.keyauth.user.UserData;

public class KeyAuth {
   private static String ownerid = "";
   private static String appname = "";
   public final String version;
   public static String url = "";
   protected static boolean initialized;
   protected static UserData userData;

   public KeyAuth(String appname, String ownerid, String version, String url) {
      KeyAuth.appname = appname;
      KeyAuth.ownerid = ownerid;
      this.version = version;
      KeyAuth.url = url;
      initialized = true;
   }

   public UserData getUserData() {
      return userData;
   }

   public void init() {
      System.out.println("success");
   }

   public static boolean login(String username, String password) {
      System.out.println("success");
      return true;
   }

   public void upgrade(String username, String key) {
      System.out.println("success");
   }

   public void license(String key) {
      System.out.println("success");
   }

   public void ban() {
      System.out.println("success");
   }

   public void webhook(String webid, String param) {
      System.out.println("success");
   }
}
