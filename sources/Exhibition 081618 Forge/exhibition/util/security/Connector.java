package exhibition.util.security;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.Iterator;
import java.util.Map.Entry;

public class Connector {
   private static void readStream(HttpURLConnection connection, StringBuilder appender) {
      try {
         InputStream stream = connection.getResponseCode() == 200 ? connection.getInputStream() : connection.getErrorStream();
         if (stream == null) {
            throw new IOException();
         }

         BufferedReader buffer = new BufferedReader(new InputStreamReader(stream));

         String line;
         while((line = buffer.readLine()) != null) {
            appender.append(line).append("\n");
         }

         buffer.close();
      } catch (IOException var5) {
         var5.printStackTrace();
      }

   }

   public static String get(Connection connection) {
      return request(connection, Connector.Method.GET);
   }

   public static String post(Connection connection) {
      return request(connection, Connector.Method.POST);
   }

   private static String request(Connection connection, Connector.Method method) {
      try {
         String payload = connection.getPayload();
         URL url = new URL(connection.getUrl() + (method == Connector.Method.GET ? (payload.isEmpty() ? "" : String.format("?%s", payload)) : ""));
         HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
         urlConnection.setRequestMethod(method.name());
         Iterator var5 = connection.getHeaders().entrySet().iterator();

         while(var5.hasNext()) {
            Entry header = (Entry)var5.next();
            urlConnection.setRequestProperty((String)header.getKey(), (String)header.getValue());
         }

         if (method == Connector.Method.POST) {
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);

            try {
               DataOutputStream output = new DataOutputStream(urlConnection.getOutputStream());
               output.writeBytes(payload);
               output.flush();
               output.close();
            } catch (Exception var7) {
               return null;
            }
         }

         StringBuilder response = new StringBuilder();
         if (urlConnection.getResponseCode() == 204) {
            return response.toString();
         } else {
            readStream(urlConnection, response);
            return response.toString();
         }
      } catch (IOException var8) {
         var8.printStackTrace();
         return null;
      }
   }

   public static void openURL(String url) {
      try {
         Desktop.getDesktop().browse(new URI(url));
      } catch (Exception var2) {
         var2.printStackTrace();
      }

   }

   public static enum Method {
      GET,
      POST;
   }
}
