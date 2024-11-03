package systems.sintex.auth;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.IOException;
import org.jsoup.Connection;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import systems.sintex.exception.MinecraftException;
import systems.sintex.models.response.MinecraftProfileResponse;

public class MinecraftAuth {
   private static Gson gson = new Gson();
   private static final String MINECRAFT_AUTH_URL = "https://api.minecraftservices.com/authentication/login_with_xbox";
   private static final String MINECRAFT_PROFILE_URL = "https://api.minecraftservices.com/minecraft/profile";

   public static String getMinecraftBearerToken(String userHash, String xSTSToken) throws IOException, MinecraftException {
      JsonObject requestBody = new JsonObject();
      requestBody.addProperty("identityToken", "XBL3.0 x=" + userHash + ";" + xSTSToken);
      requestBody.addProperty("ensureLegacyEnabled", true);
      Connection.Response response = null;

      try {
         response = Jsoup.connect("https://api.minecraftservices.com/authentication/login_with_xbox")
            .method(Connection.Method.POST)
            .requestBody(requestBody.toString())
            .header("Content-Type", "application/json")
            .header("Accept", "application/json")
            .ignoreContentType(true)
            .execute();
      } catch (HttpStatusException var5) {
         throw new MinecraftException("Invalid User Hash or Invalid XSTS Token");
      }

      return ((JsonObject)gson.fromJson(response.body(), JsonObject.class)).get("access_token").getAsString();
   }

   public static MinecraftProfileResponse getMinecraftProfile(String bearerToken) throws IOException, MinecraftException {
      Connection.Response response = Jsoup.connect("https://api.minecraftservices.com/minecraft/profile")
         .method(Connection.Method.GET)
         .header("Authorization", "Bearer " + bearerToken)
         .ignoreContentType(true)
         .ignoreHttpErrors(true)
         .execute();
      JsonObject jsonResponse = (JsonObject)gson.fromJson(response.body(), JsonObject.class);
      if (jsonResponse.has("error") && jsonResponse.get("error").getAsString().equals("NOT_FOUND")) {
         throw new MinecraftException("The account does not own minecraft");
      } else {
         return new MinecraftProfileResponse(jsonResponse.get("id").getAsString(), jsonResponse.get("name").getAsString());
      }
   }
}
