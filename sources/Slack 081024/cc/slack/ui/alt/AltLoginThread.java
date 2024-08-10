package cc.slack.ui.alt;

import fr.litarvan.openauth.microsoft.MicrosoftAuthResult;
import fr.litarvan.openauth.microsoft.MicrosoftAuthenticationException;
import fr.litarvan.openauth.microsoft.MicrosoftAuthenticator;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatFormatting;
import net.minecraft.util.Session;

public final class AltLoginThread extends Thread {
    private final String password;
    private String status;
    private final String username;

    public AltLoginThread(String username, String password) {
        super("Alt Login Thread");
        this.username = username;
        this.password = password;
        this.status = ChatFormatting.GRAY + "Waiting...";
    }

    private Session createSession(String username, String password) {
        try {
            final MicrosoftAuthResult result = (new MicrosoftAuthenticator()).loginWithCredentials(username, password);
            return new Session(result.getProfile().getName(), result.getProfile().getId(), result.getAccessToken(), "microsoft");
        } catch (MicrosoftAuthenticationException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getStatus() {
        return this.status;
    }

    @Override
    public void run() {
        if (this.password.isEmpty()) {
            Minecraft.getMinecraft().session = new Session(this.username, "", "", "mojang");
            this.status = ChatFormatting.GREEN + "Logged in. (" + this.username + " - offline name)";
            return;
        }

        this.status = ChatFormatting.YELLOW + "Logging in...";
        final Session auth = createSession(username, password);

        if (auth == null)
            this.status = ChatFormatting.RED + "Login failed!";
        else {
            this.status = ChatFormatting.GREEN + "Logged in as " + auth.getUsername();
            Minecraft.getMinecraft().session = auth;
        }
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

