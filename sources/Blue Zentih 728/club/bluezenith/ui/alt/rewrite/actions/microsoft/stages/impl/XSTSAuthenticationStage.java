package club.bluezenith.ui.alt.rewrite.actions.microsoft.stages.impl;

import club.bluezenith.ui.alt.rewrite.actions.microsoft.MicrosoftAuthenticator;
import club.bluezenith.ui.alt.rewrite.actions.microsoft.stages.AuthenticationStage;
import club.bluezenith.ui.alt.rewrite.actions.microsoft.stages.AuthenticationState;
import com.google.gson.*;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.nio.charset.StandardCharsets;

import static org.apache.http.entity.ContentType.APPLICATION_JSON;

public class XSTSAuthenticationStage extends AuthenticationStage {

    public XSTSAuthenticationStage() {
        super(AuthenticationState.GETTING_XSTS_TOKEN);
    }

    @Override
    public void run(MicrosoftAuthenticator microsoftAuthenticator, String... params) throws Exception {
        final String token = params[0];

        final JsonObject requestObject = new JsonObject();
        final JsonObject propertiesObject = new JsonObject();
        final JsonArray userTokens = new JsonArray();

        propertiesObject.add("SandboxId", new JsonPrimitive("RETAIL"));
        userTokens.add(new JsonPrimitive(token));
        propertiesObject.add("UserTokens", userTokens);

        requestObject.add("Properties", propertiesObject);
        requestObject.add("RelyingParty", new JsonPrimitive("rp://api.minecraftservices.com/"));
        requestObject.add("TokenType", new JsonPrimitive("JWT"));

        final String json = getGSON().toJson(requestObject);

        final HttpPost post = new HttpPost("https://xsts.auth.xboxlive.com/xsts/authorize");
        post.setHeader("Content-Type", "application/json");
        post.setHeader("Accept", "application/json");
        post.setEntity(new StringEntity(json, APPLICATION_JSON));

        final CloseableHttpClient client = HttpClients.createDefault();
        final HttpResponse response = client.execute(post);

        if(response.getStatusLine().getStatusCode() != 200) {
            error("Failed to log you in. (XSTS)");
            return;
        }

        final String jsonResponseString = IOUtils.toString(response.getEntity().getContent(), StandardCharsets.UTF_8);
        final JsonElement jsonElement = new JsonParser().parse(jsonResponseString);
        final JsonObject jsonObject = jsonElement.isJsonObject() ? jsonElement.getAsJsonObject() : null;

        client.close();

        if(jsonObject == null || !jsonObject.has("Token")) {
            error("Failed to log you in (XSTSA)");
            return;
        }

        final String newToken = jsonObject.get("Token").getAsString();
        final String userHash = jsonObject.get("DisplayClaims").getAsJsonObject().get("xui").getAsJsonArray().get(0).getAsJsonObject().get("uhs").getAsString();


        nextStage(microsoftAuthenticator).run(microsoftAuthenticator, newToken, userHash);

    }

    @Override
    public AuthenticationStage nextStage(MicrosoftAuthenticator microsoftAuthenticator) throws Exception {
        return new MinecraftAccessTokenStage();
    }

}
