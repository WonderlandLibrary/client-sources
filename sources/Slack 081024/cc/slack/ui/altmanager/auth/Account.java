package cc.slack.ui.altmanager.auth;

public class Account {
  private String refreshToken;
  private String accessToken;
  private String username;
  private long timestamp;

  public Account(String refreshToken, String accessToken, String username, long timestamp) {
    this.accessToken = accessToken;
    this.refreshToken = refreshToken;
    this.username = username;
    this.timestamp = timestamp;
  }

  public String getRefreshToken() {
    return refreshToken;
  }

  public String getAccessToken() {
    return accessToken;
  }

  public String getUsername() {
    return username;
  }

  public long getTimestamp() {
    return timestamp;
  }

  public void setRefreshToken(String refreshToken) {
    this.refreshToken = refreshToken;
  }

  public void setAccessToken(String accessToken) {
    this.accessToken = accessToken;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public void setTimestamp(long timestamp) {
    this.timestamp = timestamp;
  }
}
