package vestige.util.network;

import com.sun.net.httpserver.HttpServer;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Executor;
import net.minecraft.util.Session;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import vestige.ui.menu.AltLoginScreen;
import vestige.util.IMinecraft;

public class MicrosoftExternalLogin implements IMinecraft {
   private AltLoginScreen screen;

   public MicrosoftExternalLogin(AltLoginScreen screen) {
      this.screen = screen;
   }

   public void start() throws Exception {
      HttpServer httpServer = HttpServer.create(new InetSocketAddress(11999), 0);
      String authUrl = "https://login.live.com/oauth20_authorize.srf?client_id=18b632af-d7fb-43bc-aabd-6e4f3e5920bd&response_type=code&redirect_uri=http://localhost:11999/&scope=XboxLive.signin%20offline_access";
      Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();
      StringSelection stringSelection = new StringSelection(authUrl);
      clip.setContents(stringSelection, stringSelection);
      this.screen.setStatus("Login URL copied to your clipboard.");
      httpServer.createContext("/", (exchange) -> {
         String code = exchange.getRequestURI().getQuery().split("=")[1];

         try {
            String message = "Currently logging into account, you can close this window.";
            exchange.sendResponseHeaders(200, (long)message.length());
            OutputStream responseBody = exchange.getResponseBody();
            responseBody.write(message.getBytes(StandardCharsets.UTF_8));
            responseBody.close();
            this.screen.setStatus("Logging in...");
            String accessToken = this.getAccessToken(code);
            String minecraftToken = this.getAccessToken(accessToken);
            new Session(minecraftToken, "Minecraft", "mojang", "token");
            this.screen.setStatus("Logged in successfully!");
            httpServer.stop(0);
         } catch (Exception var9) {
            var9.printStackTrace();
            httpServer.stop(0);
            this.screen.setStatus("Login failed !");
         }

      });
      httpServer.setExecutor((Executor)null);
      httpServer.start();
   }

   private String getAccessToken(String code) throws Exception {
      String tokenUrl = "https://login.live.com/oauth20_token.srf";
      String postParams = String.format("client_id=faecf465-0b9d-443e-ba0e-aff540aca867&client_secret=08c5c1d9-f815-4951-8c27-191fc52311d9&redirect_uri=http://localhost:11999/&grant_type=authorization_code&code=%s", code);
      HttpURLConnection connection = (HttpURLConnection)(new URL(tokenUrl)).openConnection();
      connection.setRequestMethod("POST");
      connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
      connection.setDoOutput(true);
      OutputStream os = connection.getOutputStream();

      try {
         os.write(postParams.getBytes(StandardCharsets.UTF_8));
         os.flush();
      } catch (Throwable var11) {
         if (os != null) {
            try {
               os.close();
            } catch (Throwable var10) {
               var11.addSuppressed(var10);
            }
         }

         throw var11;
      }

      if (os != null) {
         os.close();
      }

      int responseCode = connection.getResponseCode();
      BufferedReader errorReader;
      StringBuilder errorResponse;
      String errorLine;
      if (responseCode == 200) {
         errorReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
         errorResponse = new StringBuilder();

         while((errorLine = errorReader.readLine()) != null) {
            errorResponse.append(errorLine);
         }

         errorReader.close();
         JSONParser parser = new JSONParser();
         JSONObject jsonResponse = (JSONObject) parser.parse(errorResponse.toString());
         return jsonResponse.get("access_token").toString();
      } else {
         errorReader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
         errorResponse = new StringBuilder();

         while((errorLine = errorReader.readLine()) != null) {
            errorResponse.append(errorLine);
         }

         errorReader.close();
         throw new IOException("Server returned HTTP response code: " + responseCode + " - " + errorResponse.toString());
      }
   }
}
