package vestige.util.accounts;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;

public class MicrosoftLogin {
   private static final ExecutorService executor = Executors.newSingleThreadExecutor();
   private static final String CLIENT_ID = "ba89e6e0-8490-4a26-8746-f389a0d3ccc7";
   private static final String CLIENT_SECRET = "hlQ8Q~33jTRilP4yE-UtuOt9wG.ZFLqq6pErIa2B";
   private static final int PORT = 8247;
   private static HttpServer server;
   private static Consumer<String> callback;
   private static final Gson gson = new Gson();

   private static void browse(String url) {
      String[] browsers = new String[]{"C:\\Program Files\\Mozilla Firefox\\firefox.exe " + url + " -private-window", "C:\\Program Files (x86)\\Microsoft\\Edge\\Application\\msedge.exe " + url + " -incognito", "C:\\Program Files\\BraveSoftware\\Brave-Browser\\Application\\brave.exe " + url + " -incognito", "C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe " + url + " -incognito", "open -na \"Google Chrome\" --args --incognito \"" + url + "\""};
      String[] var2 = browsers;
      int var3 = browsers.length;
      int var4 = 0;

      while(var4 < var3) {
         String browser = var2[var4];

         try {
            Runtime.getRuntime().exec(browser);
            return;
         } catch (Exception var7) {
            ++var4;
         }
      }

   }

   private static void copy(String url) {
      Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
      clipboard.setContents(new StringSelection(url), (ClipboardOwner)null);
   }

   public static void getRefreshToken(Consumer<String> callback) {
      MicrosoftLogin.callback = callback;
      startServer();
      copy("https://login.live.com/oauth20_authorize.srf?client_id=ba89e6e0-8490-4a26-8746-f389a0d3ccc7&client_secret=hlQ8Q~33jTRilP4yE-UtuOt9wG.ZFLqq6pErIa2B&response_type=code&redirect_uri=http://localhost:8247&scope=XboxLive.signin%20offline_access&prompt=select_account");
   }

   public static void getRefreshTokenAndOpenBrowser(Consumer<String> callback) {
      MicrosoftLogin.callback = callback;
      startServer();
      browse("https://login.live.com/oauth20_authorize.srf?client_id=ba89e6e0-8490-4a26-8746-f389a0d3ccc7&client_secret=hlQ8Q~33jTRilP4yE-UtuOt9wG.ZFLqq6pErIa2B&response_type=code&redirect_uri=http://localhost:8247&scope=XboxLive.signin%20offline_access&prompt=select_account");
   }

   public static MicrosoftLogin.LoginData login(String refreshToken) {
      MicrosoftLogin.AuthTokenResponse res = (MicrosoftLogin.AuthTokenResponse)gson.fromJson(Browser.postExternal("https://login.live.com/oauth20_token.srf", "client_id=ba89e6e0-8490-4a26-8746-f389a0d3ccc7&client_secret=hlQ8Q~33jTRilP4yE-UtuOt9wG.ZFLqq6pErIa2B&refresh_token=" + refreshToken + "&grant_type=refresh_token&redirect_uri=http://localhost:" + 8247 + "&prompt=select_account", false), MicrosoftLogin.AuthTokenResponse.class);
      if (res == null) {
         return new MicrosoftLogin.LoginData();
      } else {
         String accessToken = res.access_token;
         refreshToken = res.refresh_token;
         MicrosoftLogin.XblXstsResponse xblRes = (MicrosoftLogin.XblXstsResponse)gson.fromJson(Browser.postExternal("https://user.auth.xboxlive.com/user/authenticate", "{\"Properties\":{\"AuthMethod\":\"RPS\",\"SiteName\":\"user.auth.xboxlive.com\",\"RpsTicket\":\"d=" + accessToken + "\"},\"RelyingParty\":\"http://auth.xboxlive.com\",\"TokenType\":\"JWT\"}", true), MicrosoftLogin.XblXstsResponse.class);
         if (xblRes == null) {
            return new MicrosoftLogin.LoginData();
         } else {
            MicrosoftLogin.XblXstsResponse xstsRes = (MicrosoftLogin.XblXstsResponse)gson.fromJson(Browser.postExternal("https://xsts.auth.xboxlive.com/xsts/authorize", "{\"Properties\":{\"SandboxId\":\"RETAIL\",\"UserTokens\":[\"" + xblRes.Token + "\"]},\"RelyingParty\":\"rp://api.minecraftservices.com/\",\"TokenType\":\"JWT\"}", true), MicrosoftLogin.XblXstsResponse.class);
            if (xstsRes == null) {
               return new MicrosoftLogin.LoginData();
            } else {
               MicrosoftLogin.McResponse mcRes = (MicrosoftLogin.McResponse)gson.fromJson(Browser.postExternal("https://api.minecraftservices.com/authentication/login_with_xbox", "{\"identityToken\":\"XBL3.0 x=" + xblRes.DisplayClaims.xui[0].uhs + ";" + xstsRes.Token + "\"}", true), MicrosoftLogin.McResponse.class);
               if (mcRes == null) {
                  return new MicrosoftLogin.LoginData();
               } else {
                  MicrosoftLogin.ProfileResponse profileRes = (MicrosoftLogin.ProfileResponse)gson.fromJson(Browser.getBearerResponse("https://api.minecraftservices.com/minecraft/profile", mcRes.access_token), MicrosoftLogin.ProfileResponse.class);
                  return profileRes == null ? new MicrosoftLogin.LoginData() : new MicrosoftLogin.LoginData(mcRes.access_token, refreshToken, profileRes.id, profileRes.name);
               }
            }
         }
      }
   }

   private static void startServer() {
      if (server == null) {
         try {
            server = HttpServer.create(new InetSocketAddress("localhost", 8247), 0);
            server.createContext("/", new MicrosoftLogin.Handler());
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

   public static class McResponse {
      @Expose
      @SerializedName("access_token")
      public String access_token;
   }

   public static class ProfileResponse {
      @Expose
      @SerializedName("id")
      public String id;
      @Expose
      @SerializedName("name")
      public String name;
   }

   private static class Handler implements HttpHandler {
      private Handler() {
      }

      public void handle(HttpExchange req) throws IOException {
         if (req.getRequestMethod().equals("GET")) {
            List<NameValuePair> query = URLEncodedUtils.parse(req.getRequestURI(), StandardCharsets.UTF_8.name());
            boolean ok = false;
            Iterator var4 = query.iterator();

            while(var4.hasNext()) {
               NameValuePair pair = (NameValuePair)var4.next();
               if (pair.getName().equals("code")) {
                  this.handleCode(pair.getValue());
                  ok = true;
                  break;
               }
            }

            if (!ok) {
               this.writeText(req, "Cannot authenticate.");
            } else {
               this.writeText(req, "<html>You may now close this page, if you didn't specify an account then clear your cookies to select an individual one.</html>");
            }
         }

         MicrosoftLogin.stopServer();
      }

      private void handleCode(String code) {
         String response = Browser.postExternal("https://login.live.com/oauth20_token.srf", "client_id=ba89e6e0-8490-4a26-8746-f389a0d3ccc7&code=" + code + "&client_secret=" + "hlQ8Q~33jTRilP4yE-UtuOt9wG.ZFLqq6pErIa2B" + "&grant_type=authorization_code&redirect_uri=http://localhost:" + 8247, false);
         MicrosoftLogin.AuthTokenResponse res = (MicrosoftLogin.AuthTokenResponse)MicrosoftLogin.gson.fromJson(response, MicrosoftLogin.AuthTokenResponse.class);
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

      // $FF: synthetic method
      Handler(Object x0) {
         this();
      }
   }

   private static class GameOwnershipResponse {
      @Expose
      @SerializedName("items")
      private MicrosoftLogin.GameOwnershipResponse.Item[] items;

      private boolean hasGameOwnership() {
         boolean hasProduct = false;
         boolean hasGame = false;
         MicrosoftLogin.GameOwnershipResponse.Item[] var3 = this.items;
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            MicrosoftLogin.GameOwnershipResponse.Item item = var3[var5];
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
}
