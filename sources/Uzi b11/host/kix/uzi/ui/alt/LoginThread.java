package host.kix.uzi.ui.alt;

import java.net.Proxy;
import com.mojang.authlib.Agent;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
import host.kix.uzi.Uzi;
import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.Session;

public class LoginThread extends Thread {
    private final Minecraft mc = Minecraft.getMinecraft();
    private String status;
    private final String username;
    private final String password;

    public LoginThread(final String username, final String password) {
        super("Login Thread");
        this.username = username;
        this.password = password;
        status = "Idle...";
    }

    private final Session createSession(String username, String password) {
        final YggdrasilAuthenticationService service = new YggdrasilAuthenticationService(Proxy.NO_PROXY, "");
        final YggdrasilUserAuthentication auth = (YggdrasilUserAuthentication) service
                .createUserAuthentication(Agent.MINECRAFT);
        auth.setUsername(username);
        auth.setPassword(password);
        try {
            auth.logIn();
            return new Session(auth.getSelectedProfile().getName(), auth.getSelectedProfile().getId().toString(),
                    auth.getAuthenticatedToken(), "mojang");
        } catch (final Exception e) {
            return null;
        }
    }

    public String getStatus() {
        return status;
    }

    @Override
    public void run() {
        if (password.equals("")) {
            mc.session = new Session(username, "", "", "mojang");
            status = EnumChatFormatting.GREEN + "Logged in. (" + username + " - offline name)";
            return;
        }
        status = EnumChatFormatting.YELLOW + "Logging in...";
        final Session auth = createSession(username, password);
        if (auth == null) {
            status = EnumChatFormatting.RED + "Login failed!";
        } else {
            Uzi.getInstance().getAltManager().setLastAlt(new Alt(username, password));
            status = EnumChatFormatting.GREEN + "Logged in. (" + auth.getUsername() + ")";
            mc.session = auth;
        }
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
