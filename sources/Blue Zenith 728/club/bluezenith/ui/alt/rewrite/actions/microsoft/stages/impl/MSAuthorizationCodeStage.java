package club.bluezenith.ui.alt.rewrite.actions.microsoft.stages.impl;

import club.bluezenith.BlueZenith;
import club.bluezenith.ui.alt.rewrite.actions.GuiMicrosoftLogin;
import club.bluezenith.ui.alt.rewrite.actions.microsoft.MicrosoftAuthenticator;
import club.bluezenith.ui.alt.rewrite.actions.microsoft.stages.AuthenticationStage;
import club.bluezenith.ui.alt.rewrite.actions.microsoft.stages.AuthenticationState;
import club.bluezenith.util.client.FileUtil;
import fi.iki.elonen.NanoHTTPD;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.net.URI;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import static java.lang.String.format;

public class MSAuthorizationCodeStage extends AuthenticationStage {

    public static final String authURL = "https://login.live.com/oauth20_authorize.srf" +
            format("?client_id=%s", MicrosoftAuthenticator.getClientID()) +
            "&response_type=code" +
            "&scope=XboxLive.signin%20offline_access" +
            "&redirect_uri=http://localhost:8085/";


    private static final String loginUrl = "https://login.live.com/oauth20_authorize.srf" +
                                               "?client_id=00000000402b5328" +
                                               "&response_type=code" +
                                               "&scope=service%3A%3Auser.auth.xboxlive.com%3A%3AMBI_SSL" +
                                               "&redirect_uri=https%3A%2F%2Flogin.live.com%2Foauth20_desktop.srf";

    public MSAuthorizationCodeStage() {
        super(AuthenticationState.GETTING_AUTHORIZATION_CODE);
    }

    @Override
    public void run(MicrosoftAuthenticator microsoftAuthenticator, String... params) throws Exception {

        if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
            BlueZenith.getBlueZenith().getNotificationPublisher().postSuccess(
                    "Microsoft authenticator",
                    "Copied oauth link to your clipboard. \n Input it into your browser.",
                    3000
            );
            FileUtil.setClipboardContents(authURL);
        }
        else Desktop.getDesktop().browse(new URI(authURL));

        final AtomicReference<String> authenticationCode = new AtomicReference<>();

        microsoftAuthenticator.getServer().setResponseFunction((session) -> {
            final Map<String, String> parameters = session.getParms();
            if(!parameters.containsKey("code")) {
                error("Something went wrong: we couldn't find the authorization code. Try again.");
                return NanoHTTPD.newFixedLengthResponse("Something went wrong: we couldn't find the authorization code. Did you decline the request? Try again.");
            }
            authenticationCode.set(parameters.get("code"));

            if(Minecraft.getMinecraft().currentScreen instanceof GuiMicrosoftLogin)
                ((GuiMicrosoftLogin) Minecraft.getMinecraft().currentScreen).completed("Logging you in, hold on..");

            return NanoHTTPD.newFixedLengthResponse("All set! The client is going to log you in shortly. There'll be a notification when it does.");
        }).waitForRequest();

        nextStage(microsoftAuthenticator).run(microsoftAuthenticator, authenticationCode.get());
    }

    @Override
    public AuthenticationStage nextStage(MicrosoftAuthenticator microsoftAuthenticator) {
        return new LiveAccessTokenStage(null);
    }
}
