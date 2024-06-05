package zamorozka.ui;

import com.mojang.authlib.Agent;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
import net.minecraft.util.Session;

import java.net.Proxy;

public class LoginUtils {
    public static Session createSession(String username, String password, Proxy proxy) throws AuthenticationException {
        YggdrasilAuthenticationService service = new YggdrasilAuthenticationService(proxy, "");
        YggdrasilUserAuthentication auth = (YggdrasilUserAuthentication) service
                .createUserAuthentication(Agent.MINECRAFT);

        auth.setUsername(username);
        auth.setPassword(password);

        auth.logIn();
        return new Session(auth.getSelectedProfile().getName(), auth.getSelectedProfile().getId().toString(),
                auth.getAuthenticatedToken(), "mojang");
    }
}
