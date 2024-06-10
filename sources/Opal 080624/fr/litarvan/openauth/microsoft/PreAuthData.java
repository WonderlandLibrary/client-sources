package fr.litarvan.openauth.microsoft;

public class PreAuthData {
  private final String ppft;
  
  private final String urlPost;
  
  public PreAuthData(String paramString1, String paramString2) {
    this.ppft = paramString1;
    this.urlPost = paramString2;
  }
  
  public String getPPFT() {
    return this.ppft;
  }
  
  public String getUrlPost() {
    return this.urlPost;
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\fr\litarvan\openauth\microsoft\PreAuthData.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */