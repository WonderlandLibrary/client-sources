package systems.sintex.models.response;

public class XboxLiveAuthResponse {
   private String accessToken;
   private String userHash;

   public String getAccessToken() {
      return this.accessToken;
   }

   public String getUserHash() {
      return this.userHash;
   }

   public XboxLiveAuthResponse(String accessToken, String userHash) {
      this.accessToken = accessToken;
      this.userHash = userHash;
   }
}
