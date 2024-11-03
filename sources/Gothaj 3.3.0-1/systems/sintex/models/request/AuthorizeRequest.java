package systems.sintex.models.request;

import java.util.Map;

public class AuthorizeRequest {
   private String sFTTag;
   private String urlPost;
   private Map<String, String> cookies;

   public String getSFTTag() {
      return this.sFTTag;
   }

   public String getUrlPost() {
      return this.urlPost;
   }

   public Map<String, String> getCookies() {
      return this.cookies;
   }

   public AuthorizeRequest(String sFTTag, String urlPost, Map<String, String> cookies) {
      this.sFTTag = sFTTag;
      this.urlPost = urlPost;
      this.cookies = cookies;
   }
}
