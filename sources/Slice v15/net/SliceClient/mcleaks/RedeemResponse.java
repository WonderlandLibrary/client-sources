package net.SliceClient.mcleaks;

public class RedeemResponse {
  private String session;
  private String mcName;
  
  public RedeemResponse() {}
  
  public String getSession() {
    return session;
  }
  
  public void setSession(String session)
  {
    this.session = session;
  }
  
  public String getMcName()
  {
    return mcName;
  }
  
  public void setMcName(String mcName)
  {
    this.mcName = mcName;
  }
}
