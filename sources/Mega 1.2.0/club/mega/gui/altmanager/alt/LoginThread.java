package club.mega.gui.altmanager.alt;

import club.mega.interfaces.MinecraftInterface;
import com.mojang.authlib.Agent;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
import net.minecraft.util.Session;

import java.net.Proxy;

public class LoginThread extends Thread implements MinecraftInterface {

    private final String name;
    private final String password;

    public LoginThread(final String name, final String password) {
        this.name = name;
        this.password = password;
    }
    
    private Session createSession(final String username, final String password) {
        final YggdrasilAuthenticationService service = new YggdrasilAuthenticationService(Proxy.NO_PROXY, "");
        final YggdrasilUserAuthentication auth = (YggdrasilUserAuthentication)service.createUserAuthentication(Agent.MINECRAFT);
        auth.setUsername(username);
        auth.setPassword(password);
        try {
            auth.logIn();
            return new Session(auth.getSelectedProfile().getName(), auth.getSelectedProfile().getId().toString(), auth.getAuthenticatedToken(), "mojang");
        }
        catch (AuthenticationException localAuthenticationException) {
            localAuthenticationException.printStackTrace();
            return null;
        }
    }

    @Override
    public void run() {
        if (this.password.equals("")) {
            MC.session = new Session(this.name, "", "", "mojang");
            return;
        }
        Session auth = this.createSession(this.name, this.password);
        if (auth == null) {
        } else {
            this.MC.session = auth;
        }
    }

}
