package club.bluezenith.ui.alt.rewrite.actions.microsoft.stages;

import club.bluezenith.BlueZenith;
import club.bluezenith.core.requests.RequestExecutor;
import club.bluezenith.ui.alt.rewrite.actions.GuiMicrosoftLogin;
import club.bluezenith.ui.alt.rewrite.actions.microsoft.MicrosoftAuthenticator;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.client.Minecraft;

import java.awt.*;
import java.net.URI;

public abstract class AuthenticationStage {
    public final AuthenticationState currentState;
    public MicrosoftAuthenticator microsoftAuthenticator;
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    public AuthenticationStage(MicrosoftAuthenticator microsoftAuthenticator, AuthenticationState currentState) {
        this.microsoftAuthenticator = microsoftAuthenticator;
        this.currentState = currentState;
    }

    protected AuthenticationStage(AuthenticationState currentState) {
        this.currentState = currentState;
    }


    public abstract void run(MicrosoftAuthenticator microsoftAuthenticator, String... params) throws Exception;
    public abstract AuthenticationStage nextStage(MicrosoftAuthenticator microsoftAuthenticator) throws Exception;

    protected final void error(String msg) {
        try {
            Desktop.getDesktop().browse(new URI("http://localhost:8085/error?message=" + msg));
        } catch (Exception exception) {
            BlueZenith.getBlueZenith().getNotificationPublisher().postError("Microsoft Authenticator", msg, 3900);

            if(Minecraft.getMinecraft().currentScreen instanceof GuiMicrosoftLogin)
                ((GuiMicrosoftLogin) Minecraft.getMinecraft().currentScreen).completed(msg);
        }
    }

    protected RequestExecutor getRequestExecutor() {
        return BlueZenith.getBlueZenith().getRequestExecutor();
    }

    protected AuthenticationStage setMicrosoftAuthenticator(MicrosoftAuthenticator microsoftAuthenticator) {
        this.microsoftAuthenticator = microsoftAuthenticator;
        return this;
    }

    protected final Gson getGSON() {
        return gson;
    }
}
