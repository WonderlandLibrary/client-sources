package club.bluezenith.ui.alt.rewrite.actions.microsoft.stages.impl;

import club.bluezenith.ui.alt.rewrite.actions.microsoft.MicrosoftAuthenticator;
import club.bluezenith.ui.alt.rewrite.actions.microsoft.stages.AuthenticationStage;
import club.bluezenith.ui.alt.rewrite.actions.microsoft.stages.AuthenticationState;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.nio.charset.StandardCharsets;

import static org.apache.http.entity.ContentType.APPLICATION_JSON;

public class XBLAuthenticationStage extends AuthenticationStage {

    protected XBLAuthenticationStage() {
        super(AuthenticationState.GETTING_XBL_TOKEN);
    }

    @Override
    public void run(MicrosoftAuthenticator microsoftAuthenticator, String... params) throws Exception {
        final JsonObject requestObject = new JsonObject();
        final JsonObject propertiesObject = new JsonObject();

        propertiesObject.add("AuthMethod", new JsonPrimitive("RPS"));
        propertiesObject.add("SiteName", new JsonPrimitive("user.auth.xboxlive.com"));
        propertiesObject.add("RpsTicket", new JsonPrimitive("d=" + params[0]));

        requestObject.add("Properties", propertiesObject);
        requestObject.add("RelyingParty", new JsonPrimitive("http://auth.xboxlive.com"));
        requestObject.add("TokenType", new JsonPrimitive("JWT"));
        final String json = getGSON().toJson(requestObject);

     /*   System.out.println("\n\n");
        System.out.println(json);
        System.out.println("\n\n");*/

        final HttpPost post = new HttpPost("https://user.auth.xboxlive.com/user/authenticate");
        post.setHeader("Content-Type", "application/json");
        post.setHeader("Accept", "application/json");
        post.setEntity(new StringEntity(json, APPLICATION_JSON));
//        System.out.println(json);

        final CloseableHttpClient client = HttpClients.createDefault();
        final HttpResponse response = client.execute(post);

        if(response.getStatusLine().getStatusCode() != 200) {
            error("Failed to log you in. (XBL) code: " + response.getStatusLine().getStatusCode());
            return;
        }
        final String jsonResponseString = IOUtils.toString(response.getEntity().getContent(), StandardCharsets.UTF_8);
        final JsonElement jsonElement = new JsonParser().parse(jsonResponseString);
        final JsonObject jsonObject = jsonElement.isJsonObject() ? jsonElement.getAsJsonObject() : null;

        client.close();

        if(jsonObject == null || !jsonObject.has("Token")) {
            error("Failed to log you in (XBLA)");
            return;
        }
        final String token = jsonObject.get("Token").getAsString();
        final String userHash = jsonObject.get("DisplayClaims").getAsJsonObject().get("xui").getAsJsonArray().get(0).getAsJsonObject().get("uhs").getAsString();

        nextStage(microsoftAuthenticator).run(microsoftAuthenticator, token, userHash);
    }

    @Override
    public AuthenticationStage nextStage(MicrosoftAuthenticator microsoftAuthenticator) throws Exception {
        return new XSTSAuthenticationStage();
    }
}
