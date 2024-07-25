package club.bluezenith.ui.alt.rewrite.actions.microsoft.stages.impl;

import club.bluezenith.BlueZenith;
import club.bluezenith.core.data.alt.AccountLogin;
import club.bluezenith.core.data.alt.info.AccountInfo;
import club.bluezenith.ui.alt.rewrite.actions.GuiMicrosoftLogin;
import club.bluezenith.ui.alt.rewrite.actions.microsoft.MicrosoftAuthenticator;
import club.bluezenith.ui.alt.rewrite.actions.microsoft.stages.AuthenticationStage;
import club.bluezenith.ui.alt.rewrite.actions.microsoft.stages.AuthenticationState;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Session;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;

import static java.util.concurrent.CompletableFuture.supplyAsync;

public class GetGameProfileStage extends AuthenticationStage {

    public GetGameProfileStage() {
        super(AuthenticationState.GETTING_GAME_PROFILE);
    }

    @Override
    public void run(MicrosoftAuthenticator microsoftAuthenticator, String... params) throws Exception {
        final String accessToken = params[0];

        final HttpGet get = new HttpGet("https://api.minecraftservices.com/minecraft/profile");
        get.setHeader("Authorization", "Bearer " + accessToken);
        final CloseableHttpClient client = HttpClients.createDefault();
        final HttpResponse response = client.execute(get);

        if (response.getStatusLine().getStatusCode() != 200) {
            error("Failed to retrieve game profile. Make sure you create one at minecraft.net/msaprofile");
            return;
        }

        final String jsonResponseString = IOUtils.toString(response.getEntity().getContent(), StandardCharsets.UTF_8);
        final JsonElement jsonElement = new JsonParser().parse(jsonResponseString);
        final JsonObject jsonObject = jsonElement.isJsonObject() ? jsonElement.getAsJsonObject() : null;

        if(jsonObject == null || !jsonObject.has("name")) {
            error("Failed to parse game profile.");
            return;
        }

        final String name = jsonObject.get("name").getAsString();
        final String uuid = jsonObject.get("id").getAsString();
        final String uuidFixed = uuid.replaceFirst("(\\p{XDigit}{8})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}+)", "$1-$2-$3-$4-$5");

        if(microsoftAuthenticator.isRefreshingToken) {
            microsoftAuthenticator.refreshingAccount.setMicrosoftTokens(accessToken, microsoftAuthenticator.getRefreshToken(), microsoftAuthenticator.expiresIn);
            microsoftAuthenticator.refreshingAccount.setUsername(name); //update username just in case
            BlueZenith.getBlueZenith().getNotificationPublisher().postSuccess("Account manager", "Successfully refreshed access token.", 2500);

            if (Minecraft.getMinecraft().currentScreen instanceof GuiMicrosoftLogin)
                ((GuiMicrosoftLogin) Minecraft.getMinecraft().currentScreen).completed("Successfully refreshed access token. You can now close this screen.");
        } else {
            final AccountInfo accountInfo = new AccountInfo(name, uuidFixed, accessToken, microsoftAuthenticator.getRefreshToken(), microsoftAuthenticator.expiresIn);

            if(!microsoftAuthenticator.isDirect) {
                BlueZenith.getBlueZenith().getAccountRepository().addAccount(accountInfo);
                BlueZenith.getBlueZenith().getNotificationPublisher().postSuccess("Microsoft Authenticator", "Successfully addded a Microsoft account.", 3500);
            } else {
                BlueZenith.getBlueZenith().getAccountRepository().setOngoingLogin(accountInfo, supplyAsync(() -> {
                    final Session microsoftSession = new Session(accountInfo.getEffectiveUsername(), accountInfo.getUUID(), accountInfo.getMsAccessToken(),  "mojang");
                    return new AccountLogin(microsoftSession, accountInfo);
                }));
                BlueZenith.getBlueZenith().getNotificationPublisher().postSuccess("Microsoft Authenticator", "Successfully logged into a Microsoft account.", 3500);
            }
            if (Minecraft.getMinecraft().currentScreen instanceof GuiMicrosoftLogin)
                if(!microsoftAuthenticator.isDirect) {
                    ((GuiMicrosoftLogin) Minecraft.getMinecraft().currentScreen).completed("Your account has been added. You can now close this screen.");
                } else {
                    ((GuiMicrosoftLogin) Minecraft.getMinecraft().currentScreen).completed("You've been logged in. You can now close this screen.");
                }
        }

    }

    @Override
    public AuthenticationStage nextStage(MicrosoftAuthenticator microsoftAuthenticator) {
        return null;
    }
}
