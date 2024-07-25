package club.bluezenith.ui.alt.rewrite.actions.microsoft.stages.impl;

import club.bluezenith.core.requests.Request;
import club.bluezenith.core.requests.data.ContentType;
import club.bluezenith.core.requests.data.RequestOption;
import club.bluezenith.ui.alt.rewrite.actions.microsoft.MicrosoftAuthenticator;
import club.bluezenith.ui.alt.rewrite.actions.microsoft.stages.AuthenticationStage;
import club.bluezenith.ui.alt.rewrite.actions.microsoft.stages.AuthenticationState;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class LiveAccessTokenStage extends AuthenticationStage {

    private String refreshToken;
    private boolean refresh;

    public LiveAccessTokenStage(String refreshTokenIfAny) {
        super(AuthenticationState.GETTING_LIVE_ACCESS_TOKEN);
        this.refreshToken = refreshTokenIfAny;
        refresh = refreshTokenIfAny != null;
    }

    @Override
    public void run(MicrosoftAuthenticator microsoftAuthenticator, String... params) {
        final String code = refresh ? null : params[0];

        Request.post("https://login.live.com/oauth20_token.srf",
                getRequestExecutor(), ContentType.APPLICATION_ENCODED,
                RequestOption.postOf("client_id", MicrosoftAuthenticator.getClientID()),
                        RequestOption.postOf(refresh? "refresh_token" : "code", refresh ? refreshToken : code),
                        RequestOption.postOf("grant_type", refresh ? "refresh_token" : "authorization_code"),
                        RequestOption.postOf("redirect_uri", "http://localhost:8085/"),
                        RequestOption.headerOf("Content-Type", "application/x-www-form-urlencoded"))
                .appendCallback((response -> {
                    final String json = response.textResponse;
                    final JsonElement parsed = new JsonParser().parse(json);
                    final JsonObject object = parsed.isJsonObject() ? parsed.getAsJsonObject() : null;

                    if(object == null || object.has("error") || !object.has("access_token")) {
                        error("Failed to log in. (LATS)");
                        if(object != null && object.has("error")) {
                            System.err.println("MS Login: " + object.get("error"));
                        }
                        return;
                    }

                    final String token = object.get("access_token").getAsString();
                    final String refreshToken = object.get("refresh_token").getAsString();

                    microsoftAuthenticator.setRefreshToken(refreshToken);

                    try {
                        nextStage(microsoftAuthenticator).run(microsoftAuthenticator, token, refreshToken);
                    } catch (Exception e) {
                        error("Failed to log you in. (LATS)");
                        e.printStackTrace();
                    }
                })).run().blockThread();
    }

    @Override
    public AuthenticationStage nextStage(MicrosoftAuthenticator microsoftAuthenticator) {
        return new XBLAuthenticationStage();
    }
}
