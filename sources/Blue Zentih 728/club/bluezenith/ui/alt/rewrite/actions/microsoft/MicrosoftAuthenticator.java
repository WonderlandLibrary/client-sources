package club.bluezenith.ui.alt.rewrite.actions.microsoft;

import club.bluezenith.core.data.alt.info.AccountInfo;
import club.bluezenith.ui.alt.rewrite.actions.microsoft.stages.impl.LiveAccessTokenStage;
import club.bluezenith.util.client.OAuthServer;
import club.bluezenith.ui.alt.rewrite.actions.microsoft.stages.AuthenticationStage;
import club.bluezenith.ui.alt.rewrite.actions.microsoft.stages.impl.MSAuthorizationCodeStage;

import static club.bluezenith.BlueZenith.getBlueZenith;
import static java.lang.String.format;

public class MicrosoftAuthenticator {
    private static final String CLIENT_ID = "32a5ca9c-6a2f-41cf-bc6a-8c094ff70719";
    private final OAuthServer server;
    public AuthenticationStage currentStage;
    public long expiresIn;
    private String refreshToken;
    //true whenever this authenticator has been created to refresh the token.
    public final boolean isRefreshingToken, isDirect;
    //the account whose token is being refreshed (null if refreshingToken is false)
    public final AccountInfo refreshingAccount;

    public MicrosoftAuthenticator(OAuthServer server, String refreshToken, AccountInfo refreshingAccount, boolean isDirect) {
        this.server = server;
        this.currentStage = refreshToken == null ? new MSAuthorizationCodeStage() : new LiveAccessTokenStage(refreshToken);
        this.isRefreshingToken = refreshToken != null;
        this.refreshingAccount = refreshingAccount;
        this.isDirect = isDirect;
    }

    public OAuthServer getServer() {
        return this.server;
    }

    public MicrosoftAuthenticator start() { //todo store alts and make use of refresh token
        new Thread(() -> {
            try {
                this.currentStage.run(this); //open the auth url, which will redirect to the local server, granting the access token
            } catch (Exception exception) {
                exception.printStackTrace();
                getBlueZenith().getNotificationPublisher().postError(
                        "Microsoft Authenticator",
                        format("An error has occurred!\nError class:%s\nError message:%s", exception.getClass().getName(), exception.getMessage()),
                        5000
                );
            }
        }).start();
        return this;
    }

    public MicrosoftAuthenticator setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
        return this;
    }

    public String getRefreshToken() {
        return this.refreshToken;
    }

    public static String getClientID() {
        return CLIENT_ID;
    }
}
