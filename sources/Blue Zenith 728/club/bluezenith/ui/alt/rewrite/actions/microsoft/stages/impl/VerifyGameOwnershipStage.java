package club.bluezenith.ui.alt.rewrite.actions.microsoft.stages.impl;

import club.bluezenith.ui.alt.rewrite.actions.microsoft.MicrosoftAuthenticator;
import club.bluezenith.ui.alt.rewrite.actions.microsoft.stages.AuthenticationStage;
import club.bluezenith.ui.alt.rewrite.actions.microsoft.stages.AuthenticationState;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.nio.charset.StandardCharsets;

public class VerifyGameOwnershipStage extends AuthenticationStage {

    public VerifyGameOwnershipStage() {
        super(AuthenticationState.VERIFYING_GAME_OWNERSHIP);
    }

    @Override
    public void run(MicrosoftAuthenticator microsoftAuthenticator, String... params) throws Exception {
        final String bearerToken = params[0];

        final HttpGet get = new HttpGet("https://api.minecraftservices.com/entitlements/mcstore");
        get.setHeader("Authorization", "Bearer " + bearerToken);
        final CloseableHttpClient client = HttpClients.createDefault();
        final HttpResponse response = client.execute(get);

        if (response.getStatusLine().getStatusCode() != 200) {
            error("Failed to verify game ownership.");
            return;
        }


        final String jsonResponseString = IOUtils.toString(response.getEntity().getContent(), StandardCharsets.UTF_8);
        final JsonElement jsonElement = new JsonParser().parse(jsonResponseString);
        final JsonObject jsonObject = jsonElement.isJsonObject() ? jsonElement.getAsJsonObject() : null;

        client.close();

        if(jsonObject == null || !jsonObject.has("items")) {
            error("Failed to log in. (VGOS)");
            return;
        }

        if(jsonObject.get("items").getAsJsonArray().size() <= 0 || !checkProducts(jsonObject)) {
            error("The account doesn't own Minecraft.");
            return;
        }

        nextStage(microsoftAuthenticator).run(microsoftAuthenticator, bearerToken);
    }

    @Override
    public AuthenticationStage nextStage(MicrosoftAuthenticator microsoftAuthenticator) {
        return new GetGameProfileStage();
    }

    private boolean checkProducts(JsonObject jsonObject) {
        for (JsonElement jsonElement : jsonObject.get("items").getAsJsonArray()) {
            if (jsonElement.isJsonObject()) {
                if (jsonElement.getAsJsonObject().get("name").getAsString().contains("minecraft")) {
                    return true;
                }
            }
        }
        return false;
    }
}
