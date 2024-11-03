package systems.sintex.auth;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import org.jsoup.Connection;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import systems.sintex.exception.MicrosoftException;
import systems.sintex.models.request.AuthorizeRequest;
import systems.sintex.models.response.AccessTokenResponse;
import systems.sintex.models.response.XSTSResponse;
import systems.sintex.models.response.XboxLiveAuthResponse;
import systems.sintex.utils.RegexUtils;

public class MicrosoftAuth {
   private static Gson gson = new Gson();
   private final String appID;
   private final String appSecret;
   private final String redirectUri;
   private static final String LIVE_URL = "https://login.live.com";
   private static final String LIVE_AUTHORIZE_URL = "https://login.live.com/oauth20_authorize.srf";
   private static final String LIVE_TOKEN_URL = "https://login.live.com/oauth20_token.srf";
   private static final String XBOX_LIVE_AUTH_URL = "https://user.auth.xboxlive.com/user/authenticate";
   private static final String XBOX_XSTS_AUTH_URL = "https://xsts.auth.xboxlive.com/xsts/authorize";

   public String getOAuth2AuthorizeUrl() {
      return "https://login.live.com/oauth20_authorize.srf?client_id="
         + this.appID
         + "&redirect_uri="
         + this.redirectUri
         + "&response_type=code&scope=XboxLive.signin+XboxLive.offline_access";
   }

   private static AuthorizeRequest getAuthorizeRequest() throws IOException {
      Connection.Response response = Jsoup.connect(
            "https://login.live.com/oauth20_authorize.srf?client_id=000000004C12AE6F&redirect_uri=https://login.live.com/oauth20_desktop.srf&scope=service::user.auth.xboxlive.com::MBI_SSL&display=touch&response_type=token&locale=en"
         )
         .method(Connection.Method.GET)
         .ignoreContentType(true)
         .ignoreHttpErrors(true)
         .execute();
      String responseBody = response.body();
      String sFTTag = RegexUtils.findFirst(responseBody, "value=\"(.+?)\"").split("\"")[1];
      String urlPost = RegexUtils.findFirst(responseBody, "urlPost:'(.+?)'").split("'")[1];
      return new AuthorizeRequest(URLEncoder.encode(sFTTag, "UTF-8"), urlPost, response.cookies());
   }

   public static AccessTokenResponse getAccessTokenWithCredentials(String email, String password) throws MicrosoftException, IOException {
      AuthorizeRequest authorizeRequest = getAuthorizeRequest();
      String requestBody = "login="
         + URLEncoder.encode(email, "UTF-8")
         + "&loginfmt="
         + URLEncoder.encode(email, "UTF-8")
         + "&passwd="
         + URLEncoder.encode(password, "UTF-8")
         + "&PPFT="
         + authorizeRequest.getSFTTag();
      Connection.Response response = Jsoup.connect(authorizeRequest.getUrlPost())
         .method(Connection.Method.POST)
         .cookies(authorizeRequest.getCookies())
         .requestBody(requestBody)
         .header("Content-Type", "application/x-www-form-urlencoded")
         .followRedirects(true)
         .execute();
      String responseBody = response.body();
      if (responseBody.contains("Sign in to")) {
         throw new MicrosoftException("Invalid credentials provided");
      } else if (responseBody.contains("Help us protect your account")) {
         throw new MicrosoftException("2-factor authentication is enabled");
      } else {
         String redirectUrl = URLDecoder.decode(URLDecoder.decode(String.valueOf(response.url()), "UTF-8"), "UTF-8");
         String accessToken = redirectUrl.split("access_token=")[1].split("&")[0];
         String refreshToken = redirectUrl.split("refresh_token=")[1].split("&")[0];
         return new AccessTokenResponse(accessToken, refreshToken);
      }
   }

   public AccessTokenResponse getAccessToken(String code) throws IOException, MicrosoftException {
      Connection.Response response = null;

      try {
         response = Jsoup.connect("https://login.live.com/oauth20_token.srf")
            .method(Connection.Method.POST)
            .data("client_id", this.appID)
            .data("client_secret", this.appSecret)
            .data("redirect_uri", this.redirectUri)
            .data("grant_type", "authorization_code")
            .data("code", code)
            .ignoreContentType(true)
            .execute();
      } catch (HttpStatusException var6) {
         throw new MicrosoftException("Invalid auth code provided");
      }

      JsonObject jsonResponse = (JsonObject)gson.fromJson(response.body(), JsonObject.class);
      String accessToken = jsonResponse.get("access_token").getAsString();
      String refreshToken = jsonResponse.get("refresh_token").getAsString();
      return new AccessTokenResponse(accessToken, refreshToken);
   }

   public AccessTokenResponse refreshAccessToken(String rToken) throws IOException, MicrosoftException {
      Connection.Response response = null;

      try {
         response = Jsoup.connect("https://login.live.com/oauth20_token.srf")
            .method(Connection.Method.POST)
            .data("client_id", this.appID)
            .data("client_secret", this.appSecret)
            .data("refresh_token", rToken)
            .data("grant_type", "refresh_token")
            .data("redirect_uri", this.redirectUri)
            .ignoreContentType(true)
            .execute();
      } catch (HttpStatusException var6) {
         throw new MicrosoftException("Invalid refresh token provided");
      }

      JsonObject jsonResponse = (JsonObject)gson.fromJson(response.body(), JsonObject.class);
      String accessToken = jsonResponse.get("access_token").getAsString();
      String refreshToken = jsonResponse.get("refresh_token").getAsString();
      return new AccessTokenResponse(accessToken, refreshToken);
   }

   public static XboxLiveAuthResponse getXboxLiveAuthResponse(String accessToken, boolean oAuth2) throws IOException, MicrosoftException {
      JsonObject requestBody = new JsonObject();
      JsonObject properties = new JsonObject();
      properties.addProperty("AuthMethod", "RPS");
      properties.addProperty("SiteName", "user.auth.xboxlive.com");
      properties.addProperty("RpsTicket", (oAuth2 ? "d=" : "") + accessToken);
      requestBody.add("Properties", properties);
      requestBody.addProperty("RelyingParty", "http://auth.xboxlive.com");
      requestBody.addProperty("TokenType", "JWT");
      Connection.Response response = null;

      try {
         response = Jsoup.connect("https://user.auth.xboxlive.com/user/authenticate")
            .method(Connection.Method.POST)
            .requestBody(requestBody.toString())
            .header("Content-Type", "application/json")
            .header("Accept", "application/json")
            .ignoreContentType(true)
            .ignoreHttpErrors(true)
            .execute();
      } catch (HttpStatusException var6) {
         throw new MicrosoftException("Invalid access token provided");
      }

      JsonObject jsonResponse = (JsonObject)gson.fromJson(response.body(), JsonObject.class);
      return new XboxLiveAuthResponse(
         jsonResponse.get("Token").getAsString(),
         jsonResponse.get("DisplayClaims").getAsJsonObject().get("xui").getAsJsonArray().get(0).getAsJsonObject().get("uhs").getAsString()
      );
   }

   public static XSTSResponse getXSTSResponse(String token) throws IOException, MicrosoftException {
      JsonObject requestBody = new JsonObject();
      requestBody.addProperty("TokenType", "JWT");
      requestBody.addProperty("RelyingParty", "rp://api.minecraftservices.com/");
      JsonObject properties = new JsonObject();
      properties.addProperty("SandboxId", "RETAIL");
      JsonArray userTokens = new JsonArray();
      userTokens.add(new JsonPrimitive(token));
      properties.add("UserTokens", userTokens);
      requestBody.add("Properties", properties);
      Connection.Response response = null;
      response = Jsoup.connect("https://xsts.auth.xboxlive.com/xsts/authorize")
         .method(Connection.Method.POST)
         .requestBody(requestBody.toString())
         .header("Content-Type", "application/json")
         .header("Accept", "application/json")
         .ignoreContentType(true)
         .ignoreHttpErrors(true)
         .execute();
      JsonObject jsonResponse = (JsonObject)gson.fromJson(response.body(), JsonObject.class);
      if (jsonResponse.has("XErr")) {
         String var6 = jsonResponse.get("XErr").getAsString();
         switch (var6) {
            case "2148916233":
               throw new MicrosoftException("The account does not have an Xbox account");
            case "2148916235":
               throw new MicrosoftException("The account is from a country where Xbox Live is not available/banned");
            case "2148916236":
            case "2148916237":
               throw new MicrosoftException("The account needs adult verification on Xbox page. (South Korea)");
            case "2148916238":
               throw new MicrosoftException("The account is a child account and needs to be added to a Family by an adult");
         }
      }

      return new XSTSResponse(
         jsonResponse.get("Token").getAsString(),
         jsonResponse.get("DisplayClaims").getAsJsonObject().get("xui").getAsJsonArray().get(0).getAsJsonObject().get("uhs").getAsString()
      );
   }

   public MicrosoftAuth(String appID, String appSecret, String redirectUri) {
      this.appID = appID;
      this.appSecret = appSecret;
      this.redirectUri = redirectUri;
   }
}
