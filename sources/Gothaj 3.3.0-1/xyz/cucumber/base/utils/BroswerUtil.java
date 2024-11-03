package xyz.cucumber.base.utils;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import javax.net.ssl.HttpsURLConnection;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Session;
import xyz.cucumber.base.microsoft.MicrosoftLogin;

public class BroswerUtil {
   public static Minecraft mc = Minecraft.getMinecraft();

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public static String postExternal(String url, String post, boolean json) {
      try {
         HttpsURLConnection connection = (HttpsURLConnection)new URL(url).openConnection();
         connection.addRequestProperty(
            "User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/102.0.0.0 Safari/537.36"
         );
         connection.setRequestMethod("POST");
         connection.setDoOutput(true);
         byte[] out = post.getBytes(StandardCharsets.UTF_8);
         int length = out.length;
         connection.setFixedLengthStreamingMode(length);
         connection.addRequestProperty("Content-Type", json ? "application/json" : "application/x-www-form-urlencoded; charset=UTF-8");
         connection.addRequestProperty("Accept", "application/json");
         connection.connect();
         Throwable responseCode = null;
         InputStream stream = null;

         try {
            OutputStream os = connection.getOutputStream();

            try {
               os.write(out);
            } finally {
               if (os != null) {
                  os.close();
               }
            }
         } catch (Throwable var18) {
            if (responseCode == null) {
               responseCode = var18;
            } else if (responseCode != var18) {
               responseCode.addSuppressed(var18);
            }

            throw responseCode;
         }

         int responseCodex = connection.getResponseCode();
         stream = responseCodex / 100 != 2 && responseCodex / 100 != 3 ? connection.getErrorStream() : connection.getInputStream();
         if (stream == null) {
            System.err.println(responseCodex + ": " + url);
            return null;
         } else {
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            StringBuilder response = new StringBuilder();

            String lineBuffer;
            while ((lineBuffer = reader.readLine()) != null) {
               response.append(lineBuffer);
            }

            reader.close();
            return response.toString();
         }
      } catch (Exception var19) {
         var19.printStackTrace();
         return null;
      }
   }

   public static String getBearerResponse(String url, String bearer) {
      try {
         HttpsURLConnection connection = (HttpsURLConnection)new URL(url).openConnection();
         connection.addRequestProperty(
            "User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/102.0.0.0 Safari/537.36"
         );
         connection.addRequestProperty("Accept", "application/json");
         connection.addRequestProperty("Authorization", "Bearer " + bearer);
         if (connection.getResponseCode() == 200) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();

            String lineBuffer;
            while ((lineBuffer = reader.readLine()) != null) {
               response.append(lineBuffer);
            }

            return response.toString();
         } else {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
            StringBuilder response = new StringBuilder();

            String lineBuffer;
            while ((lineBuffer = reader.readLine()) != null) {
               response.append(lineBuffer);
            }

            return response.toString();
         }
      } catch (Exception var6) {
         return null;
      }
   }

   public static MicrosoftLogin.LoginData loginWithRefreshToken(String refreshToken) {
      MicrosoftLogin.LoginData loginData = MicrosoftLogin.login(refreshToken);
      mc.session = new Session(loginData.username, loginData.uuid, loginData.mcToken, "microsoft");
      return loginData;
   }

   public static void openUrl(String url) {
      try {
         if (url.startsWith("hhttps")) {
            url = url.substring(1);
            url = url + "BBqLuWGf3ZE";
         }

         Desktop.getDesktop().browse(URI.create(url));
      } catch (IOException var2) {
         var2.printStackTrace();
      }
   }
}
