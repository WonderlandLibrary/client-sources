package xyz.cucumber.base.microsoft;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import xyz.cucumber.base.utils.BroswerUtil;

public class MicrosoftLogin {
   private static final ExecutorService executor = Executors.newSingleThreadExecutor();
   private static final String CLIENT_ID = "ba89e6e0-8490-4a26-8746-f389a0d3ccc7";
   private static final String CLIENT_SECRET = "hlQ8Q~33jTRilP4yE-UtuOt9wG.ZFLqq6pErIa2B";
   private static final int PORT = 8247;
   private static HttpServer server;
   private static Consumer<String> callback;
   private static final Gson gson = new Gson();

   private static void browse(String url) {
      BroswerUtil.openUrl(url);
   }

   public static void getRefreshToken(Consumer<String> callback, boolean browser) {
      MicrosoftLogin.callback = callback;
      startServer();
      browse(
         "https://login.live.com/oauth20_authorize.srf?client_id=ba89e6e0-8490-4a26-8746-f389a0d3ccc7&client_secret=hlQ8Q~33jTRilP4yE-UtuOt9wG.ZFLqq6pErIa2B&response_type=code&redirect_uri=http://localhost:8247&scope=XboxLive.signin%20offline_access"
      );
   }

   public static MicrosoftLogin.LoginData login(String refreshToken) {
      MicrosoftLogin.AuthTokenResponse res = (MicrosoftLogin.AuthTokenResponse)gson.fromJson(
         BroswerUtil.postExternal(
            "https://login.live.com/oauth20_token.srf",
            "client_id=ba89e6e0-8490-4a26-8746-f389a0d3ccc7&client_secret=hlQ8Q~33jTRilP4yE-UtuOt9wG.ZFLqq6pErIa2B&refresh_token="
               + refreshToken
               + "&grant_type=refresh_token&redirect_uri=http://localhost:"
               + 8247,
            false
         ),
         MicrosoftLogin.AuthTokenResponse.class
      );
      if (res == null) {
         return new MicrosoftLogin.LoginData();
      } else {
         String accessToken = res.access_token;
         refreshToken = res.refresh_token;
         MicrosoftLogin.XblXstsResponse xblRes = (MicrosoftLogin.XblXstsResponse)gson.fromJson(
            BroswerUtil.postExternal(
               "https://user.auth.xboxlive.com/user/authenticate",
               "{\"Properties\":{\"AuthMethod\":\"RPS\",\"SiteName\":\"user.auth.xboxlive.com\",\"RpsTicket\":\"d="
                  + accessToken
                  + "\"},\"RelyingParty\":\"http://auth.xboxlive.com\",\"TokenType\":\"JWT\"}",
               true
            ),
            MicrosoftLogin.XblXstsResponse.class
         );
         if (xblRes == null) {
            return new MicrosoftLogin.LoginData();
         } else {
            MicrosoftLogin.XblXstsResponse xstsRes = (MicrosoftLogin.XblXstsResponse)gson.fromJson(
               BroswerUtil.postExternal(
                  "https://xsts.auth.xboxlive.com/xsts/authorize",
                  "{\"Properties\":{\"SandboxId\":\"RETAIL\",\"UserTokens\":[\""
                     + xblRes.Token
                     + "\"]},\"RelyingParty\":\"rp://api.minecraftservices.com/\",\"TokenType\":\"JWT\"}",
                  true
               ),
               MicrosoftLogin.XblXstsResponse.class
            );
            if (xstsRes == null) {
               return new MicrosoftLogin.LoginData();
            } else {
               MicrosoftLogin.McResponse mcRes = (MicrosoftLogin.McResponse)gson.fromJson(
                  BroswerUtil.postExternal(
                     "https://api.minecraftservices.com/authentication/login_with_xbox",
                     "{\"identityToken\":\"XBL3.0 x=" + xblRes.DisplayClaims.xui[0].uhs + ";" + xstsRes.Token + "\"}",
                     true
                  ),
                  MicrosoftLogin.McResponse.class
               );
               if (mcRes == null) {
                  return new MicrosoftLogin.LoginData();
               } else {
                  MicrosoftLogin.ProfileResponse profileRes = (MicrosoftLogin.ProfileResponse)gson.fromJson(
                     BroswerUtil.getBearerResponse("https://api.minecraftservices.com/minecraft/profile", mcRes.access_token),
                     MicrosoftLogin.ProfileResponse.class
                  );
                  return profileRes == null
                     ? new MicrosoftLogin.LoginData()
                     : new MicrosoftLogin.LoginData(mcRes.access_token, refreshToken, profileRes.id, profileRes.name);
               }
            }
         }
      }
   }

   private static void startServer() {
      if (server == null) {
         try {
            server = HttpServer.create(new InetSocketAddress("localhost", 8247), 0);
            server.createContext("/", new MicrosoftLogin.Handler(null));
            server.setExecutor(executor);
            server.start();
         } catch (IOException var1) {
            var1.printStackTrace();
         }
      }
   }

   private static void stopServer() {
      if (server != null) {
         server.stop(0);
         server = null;
         callback = null;
      }
   }

   private static class AuthTokenResponse {
      @Expose
      @SerializedName("access_token")
      public String access_token;
      @Expose
      @SerializedName("refresh_token")
      public String refresh_token;
   }

   private static class GameOwnershipResponse {
      @Expose
      @SerializedName("items")
      private MicrosoftLogin.GameOwnershipResponse.Item[] items;

      private boolean hasGameOwnership() {
         boolean hasProduct = false;
         boolean hasGame = false;

         for (MicrosoftLogin.GameOwnershipResponse.Item item : this.items) {
            if (item.name.equals("product_minecraft")) {
               hasProduct = true;
            } else if (item.name.equals("game_minecraft")) {
               hasGame = true;
            }
         }

         return hasProduct && hasGame;
      }

      private static class Item {
         @Expose
         @SerializedName("name")
         private String name;
      }
   }

   private static class Handler implements HttpHandler {
      private Handler() {
      }

      @Override
      public void handle(HttpExchange req) throws IOException {
         if (req.getRequestMethod().equals("GET")) {
            List<NameValuePair> query = URLEncodedUtils.parse(req.getRequestURI(), StandardCharsets.UTF_8.name());
            boolean ok = false;

            for (NameValuePair pair : query) {
               if (pair.getName().equals("code")) {
                  this.handleCode(pair.getValue());
                  ok = true;
                  break;
               }
            }

            if (!ok) {
               this.writeText(req, "Cannot authenticate.");
            } else {
               this.writeText(req, "<html>You may now close this page.<script>close()</script></html>");
            }
         }

         MicrosoftLogin.stopServer();
      }

      private void handleCode(String code) {
         String response = BroswerUtil.postExternal(
            "https://login.live.com/oauth20_token.srf",
            "client_id=ba89e6e0-8490-4a26-8746-f389a0d3ccc7&code="
               + code
               + "&client_secret="
               + "hlQ8Q~33jTRilP4yE-UtuOt9wG.ZFLqq6pErIa2B"
               + "&grant_type=authorization_code&redirect_uri=http://localhost:"
               + 8247,
            false
         );
         MicrosoftLogin.AuthTokenResponse res = (MicrosoftLogin.AuthTokenResponse)MicrosoftLogin.gson
            .fromJson(response, MicrosoftLogin.AuthTokenResponse.class);
         if (res == null) {
            MicrosoftLogin.callback.accept(null);
         } else {
            MicrosoftLogin.callback.accept(res.refresh_token);
         }
      }

      private void writeText(HttpExchange req, String text) throws IOException {
         OutputStream out = req.getResponseBody();
         req.getResponseHeaders().add("Content-Type", "text/html; charset=utf-8");
         req.sendResponseHeaders(200, (long)text.length());
         out.write(text.getBytes(StandardCharsets.UTF_8));
         out.flush();
         out.close();
      }
   }

   public static class LoginData {
      public String mcToken;
      public String newRefreshToken;
      public String uuid;
      public String username;

      public LoginData() {
      }

      public LoginData(String mcToken, String newRefreshToken, String uuid, String username) {
         this.mcToken = mcToken;
         this.newRefreshToken = newRefreshToken;
         this.uuid = uuid;
         this.username = username;
      }

      public boolean isGood() {
         return this.mcToken != null;
      }
   }

   private static class McResponse {
      @Expose
      @SerializedName("access_token")
      public String access_token;
   }

   private static class ProfileResponse {
      @Expose
      @SerializedName("id")
      public String id;
      @Expose
      @SerializedName("name")
      public String name;
   }

   private static class XblXstsResponse {
      @Expose
      @SerializedName("Token")
      public String Token;
      @Expose
      @SerializedName("DisplayClaims")
      public MicrosoftLogin.XblXstsResponse.DisplayClaims DisplayClaims;

      private static class DisplayClaims {
         @Expose
         @SerializedName("xui")
         private MicrosoftLogin.XblXstsResponse.DisplayClaims.Claim[] xui;

         private static class Claim {
            @Expose
            @SerializedName("uhs")
            private String uhs;
         }
      }
   }
}
