package optifine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import net.minecraft.client.Minecraft;

public class HttpUtils {
   public static final String POST_URL = "http://optifine.net";
   public static final String SERVER_URL = "http://s.optifine.net";

   public static String post(String var0, Map var1, byte[] var2) throws IOException {
      HttpURLConnection var3 = null;

      String var13;
      try {
         URL var4 = new URL(var0);
         var3 = (HttpURLConnection)var4.openConnection(Minecraft.getMinecraft().getProxy());
         var3.setRequestMethod("POST");
         if (var1 != null) {
            Set var5 = var1.keySet();
            Iterator var6 = var5.iterator();

            while(var6.hasNext()) {
               String var7 = (String)var6.next();
               String var8 = String.valueOf((new StringBuilder()).append(var1.get(var7)));
               var3.setRequestProperty(var7, var8);
            }
         }

         var3.setRequestProperty("Content-Type", "text/plain");
         var3.setRequestProperty("Content-Length", String.valueOf((new StringBuilder()).append(var2.length)));
         var3.setRequestProperty("Content-Language", "en-US");
         var3.setUseCaches(false);
         var3.setDoInput(true);
         var3.setDoOutput(true);
         OutputStream var16 = var3.getOutputStream();
         var16.write(var2);
         var16.flush();
         var16.close();
         InputStream var17 = var3.getInputStream();
         InputStreamReader var18 = new InputStreamReader(var17, "ASCII");
         BufferedReader var19 = new BufferedReader(var18);
         StringBuffer var9 = new StringBuffer();

         String var10;
         while((var10 = var19.readLine()) != null) {
            var9.append(var10);
            var9.append('\r');
         }

         var19.close();
         String var11 = var9.toString();
         var13 = var11;
      } finally {
         if (var3 != null) {
            var3.disconnect();
         }

      }

      return var13;
   }

   public static byte[] get(String var0) throws IOException {
      HttpURLConnection var1 = null;

      try {
         URL var2 = new URL(var0);
         var1 = (HttpURLConnection)var2.openConnection(Minecraft.getMinecraft().getProxy());
         var1.setDoInput(true);
         var1.setDoOutput(false);
         var1.connect();
         if (var1.getResponseCode() / 100 != 2) {
            if (var1.getErrorStream() != null) {
               Config.readAll(var1.getErrorStream());
            }

            throw new IOException(String.valueOf((new StringBuilder("HTTP response: ")).append(var1.getResponseCode())));
         } else {
            InputStream var3 = var1.getInputStream();
            byte[] var4 = new byte[var1.getContentLength()];
            int var5 = 0;

            do {
               int var6 = var3.read(var4, var5, var4.length - var5);
               if (var6 < 0) {
                  throw new IOException(String.valueOf((new StringBuilder("Input stream closed: ")).append(var0)));
               }

               var5 += var6;
            } while(var5 < var4.length);

            byte[] var8 = var4;
            return var8;
         }
      } finally {
         if (var1 != null) {
            var1.disconnect();
         }

      }
   }
}
