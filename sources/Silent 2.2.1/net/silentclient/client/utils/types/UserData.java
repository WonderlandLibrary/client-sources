package net.silentclient.client.utils.types;

public class UserData {
    public String access_token;
    public int server_port;

    public UserData() {
        this.access_token = "";
    }

    public UserData(String token) {
        this.access_token = token;
    }

    public String getAccessToken() {
        return access_token;
    }

    public void setAccessToken(String access_token) {
        this.access_token = access_token;
    }
}
