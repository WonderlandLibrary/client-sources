package systems.sintex.models.response;

public class XSTSResponse {
   private String accessToken;
   private String userHash;

   public String getAccessToken() {
      return this.accessToken;
   }

   public String getUserHash() {
      return this.userHash;
   }

   public XSTSResponse(String accessToken, String userHash) {
      this.accessToken = accessToken;
      this.userHash = userHash;
   }
}
