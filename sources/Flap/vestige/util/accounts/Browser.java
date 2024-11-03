package vestige.util.accounts;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import javax.net.ssl.HttpsURLConnection;

public class Browser {
   public static String postExternal(String url, String post, boolean json) {
      try {
         HttpsURLConnection connection = (HttpsURLConnection)(new URL(url)).openConnection();
         connection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/102.0.0.0 Safari/537.36");
         connection.setRequestMethod("POST");
         connection.setDoOutput(true);
         byte[] out = post.getBytes(StandardCharsets.UTF_8);
         int length = out.length;
         connection.setFixedLengthStreamingMode(length);
         connection.addRequestProperty("Content-Type", json ? "application/json" : "application/x-www-form-urlencoded; charset=UTF-8");
         connection.addRequestProperty("Accept", "application/json");
         connection.connect();
         OutputStream os = connection.getOutputStream();

         try {
            os.write(out);
         } catch (Throwable var12) {
            if (os != null) {
               try {
                  os.close();
               } catch (Throwable var11) {
                  var12.addSuppressed(var11);
               }
            }

            throw var12;
         }

         if (os != null) {
            os.close();
         }

         int responseCode = connection.getResponseCode();
         InputStream stream = responseCode / 100 != 2 && responseCode / 100 != 3 ? connection.getErrorStream() : connection.getInputStream();
         if (stream == null) {
            System.err.println(responseCode + ": " + url);
            return null;
         } else {
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            StringBuilder response = new StringBuilder();

            String lineBuffer;
            while((lineBuffer = reader.readLine()) != null) {
               response.append(lineBuffer);
            }

            reader.close();
            return response.toString();
         }
      } catch (Exception var13) {
         var13.printStackTrace();
         return null;
      }
   }

   public static String getBearerResponse(String url, String bearer) {
      try {
         HttpsURLConnection connection = (HttpsURLConnection)(new URL(url)).openConnection();
         connection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/102.0.0.0 Safari/537.36");
         connection.addRequestProperty("Accept", "application/json");
         connection.addRequestProperty("Authorization", "Bearer " + bearer);
         BufferedReader reader;
         String lineBuffer;
         StringBuilder response;
         if (connection.getResponseCode() == 200) {
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            response = new StringBuilder();

            while((lineBuffer = reader.readLine()) != null) {
               response.append(lineBuffer);
            }

            return response.toString();
         } else {
            reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
            response = new StringBuilder();

            while((lineBuffer = reader.readLine()) != null) {
               response.append(lineBuffer);
            }

            return response.toString();
         }
      } catch (Exception var6) {
         return null;
      }
   }
}
