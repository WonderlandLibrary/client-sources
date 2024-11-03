package systems.sintex.models.response;

public class AccessTokenResponse {
   private String accessToken;
   private String refreshToken;

   public String getAccessToken() {
      return this.accessToken;
   }

   public String getRefreshToken() {
      return this.refreshToken;
   }

   public AccessTokenResponse(String accessToken, String refreshToken) {
      this.accessToken = accessToken;
      this.refreshToken = refreshToken;
   }
}
