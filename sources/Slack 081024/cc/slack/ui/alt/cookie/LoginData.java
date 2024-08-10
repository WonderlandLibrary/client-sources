package cc.slack.ui.alt.cookie;

public class LoginData {

    public String mcToken;
    public String newRefreshToken;
    public String uuid, username;

    public LoginData(final String mcToken, final String newRefreshToken, final String uuid, final String username) {
        this.mcToken = mcToken;
        this.newRefreshToken = newRefreshToken;
        this.uuid = uuid;
        this.username = username;
    }
}