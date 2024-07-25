package club.bluezenith.ui.alt.rewrite.actions.microsoft.stages.impl;

import club.bluezenith.ui.alt.rewrite.actions.microsoft.MicrosoftAuthenticator;
import club.bluezenith.ui.alt.rewrite.actions.microsoft.stages.AuthenticationStage;
import club.bluezenith.ui.alt.rewrite.actions.microsoft.stages.AuthenticationState;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.nio.charset.StandardCharsets;

import static java.lang.String.format;
import static org.apache.http.entity.ContentType.APPLICATION_JSON;

public class MinecraftAccessTokenStage extends AuthenticationStage {

    public MinecraftAccessTokenStage() {
        super(AuthenticationState.GETTING_MS_MC_TOKEN);
    }

    @Override
    public void run(MicrosoftAuthenticator microsoftAuthenticator, String... params) throws Exception {
        final String token = params[0]; //go second
        final String userHash = params[1];

        final String request = format("{\"identityToken\":\"XBL3.0 x=%s;%s\"}", userHash, token);

        final HttpPost post = new HttpPost("https://api.minecraftservices.com/authentication/login_with_xbox");
        post.setHeader("Content-Type", "application/json");
        post.setHeader("Accept", "application/json");
        post.setEntity(new StringEntity(request, APPLICATION_JSON));

        final CloseableHttpClient client = HttpClients.createDefault();
        final HttpResponse response = client.execute(post);

        if(response.getStatusLine().getStatusCode() != 200) {
            error("Failed to log you in. (MATS)");
            return;
        }

        final String jsonResponseString = IOUtils.toString(response.getEntity().getContent(), StandardCharsets.UTF_8);
        final JsonElement jsonElement = new JsonParser().parse(jsonResponseString);
        final JsonObject jsonObject = jsonElement.isJsonObject() ? jsonElement.getAsJsonObject() : null;

        client.close();

        if(jsonObject == null || !jsonObject.has("access_token")) {
            error("Failed to log in. (MATSJ)");
            return;
        }

        final String accessToken = jsonObject.get("access_token").getAsString();
        microsoftAuthenticator.expiresIn = jsonObject.get("expires_in").getAsLong();
        nextStage(microsoftAuthenticator).run(microsoftAuthenticator, accessToken);
    }

    @Override
    public AuthenticationStage nextStage(MicrosoftAuthenticator microsoftAuthenticator) {
        return new VerifyGameOwnershipStage();
    }
}
