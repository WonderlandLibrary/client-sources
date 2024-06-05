package cc.novoline.utils.web;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

public class JsonObtainer {
   private static String b;

   public static JSONObject obtainJson(String var0) {
      String var1 = b();

      try {
         URL var2 = new URL(var0);
         HttpURLConnection var3 = (HttpURLConnection)var2.openConnection();
         var3.setRequestMethod("GET");
         var3.addRequestProperty("User-Agent", "Mozilla/4.76 (Sk1er-UHCStars V1.0)");
         InputStream var4 = var3.getInputStream();
         return new JSONObject(IOUtils.toString(var4, Charset.defaultCharset()));
      } catch (Exception var5) {
         var5.printStackTrace();
         return (new JSONObject()).put("succcess", false).put("cause", "API_DOWN");
      }
   }

   public static void b(String var0) {
      b = var0;
   }

   public static String b() {
      return b;
   }

   private static Exception a(Exception var0) {
      return var0;
   }

   static {
      b("ppb8D");
   }
}
