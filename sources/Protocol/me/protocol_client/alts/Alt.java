package me.protocol_client.alts;

public class Alt implements Comparable<Alt>
{
  private String userName;
  private String email;
  private String password;
  
  public void setUserName(String userName)
  {
    this.userName = userName;
  }
  
  public void setEmail(String email)
  {
    this.email = email;
  }
  
  public void setPassword(String password)
  {
    this.password = password;
  }
  
  public boolean equals(Object o)
  {
    if (o == this) {
      return true;
    }
    if (!(o instanceof Alt)) {
      return false;
    }
    Alt other = (Alt)o;
    if (!other.canEqual(this)) {
      return false;
    }
    Object this$userName = getUserName();Object other$userName = other.getUserName();
    if (this$userName == null ? other$userName != null : !this$userName.equals(other$userName)) {
      return false;
    }
    Object this$email = getEmail();Object other$email = other.getEmail();
    if (this$email == null ? other$email != null : !this$email.equals(other$email)) {
      return false;
    }
    Object this$password = getPassword();Object other$password = other.getPassword();return this$password == null ? other$password == null : this$password.equals(other$password);
  }
  
  protected boolean canEqual(Object other)
  {
    return other instanceof Alt;
  }
  
  public int hashCode()
  {
    int PRIME = 59;int result = 1;Object $userName = getUserName();result = result * 59 + ($userName == null ? 0 : $userName.hashCode());Object $email = getEmail();result = result * 59 + ($email == null ? 0 : $email.hashCode());Object $password = getPassword();result = result * 59 + ($password == null ? 0 : $password.hashCode());return result;
  }
  
  public String toString()
  {
    return "Alt(userName=" + getUserName() + ", email=" + getEmail() + ", password=" + getPassword() + ")";
  }
  
  public String getUserName()
  {
    return this.userName;
  }
  
  public String getEmail()
  {
    return this.email;
  }
  
  public String getPassword()
  {
    return this.password;
  }
  
  public Alt(String userName, String email, String password)
  {
    this.userName = userName;
    this.email = email;
    this.password = password;
  }
  
  public Alt(String userName, String password)
  {
    this(userName, "", password);
  }
  
  public Alt(String userName)
  {
    this(userName, "");
  }
  
  public boolean isPremium()
  {
    return this.password.length() > 0;
  }
  
  public boolean isMojang()
  {
    return this.email.length() > 0;
  }
  
  public int compareTo(Alt o)
  {
    return getUserName().compareToIgnoreCase(o.getUserName());
  }
}
