package fr.litarvan.openauth.microsoft.model.response;

public class XboxLoginResponse$XboxLiveUserInfo {
  private final String uhs;
  
  private static String[] i;
  
  public XboxLoginResponse$XboxLiveUserInfo(String paramString) {
    this.uhs = paramString;
  }
  
  public String getUserHash() {
    return this.uhs;
  }
  
  public static void b(String[] paramArrayOfString) {
    i = paramArrayOfString;
  }
  
  public static String[] h() {
    return i;
  }
  
  static {
    if (h() == null)
      b(new String[1]); 
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\fr\litarvan\openauth\microsoft\model\response\XboxLoginResponse$XboxLiveUserInfo.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */