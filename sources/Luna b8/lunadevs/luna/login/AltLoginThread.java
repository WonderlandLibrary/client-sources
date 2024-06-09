package lunadevs.luna.login;

import com.mojang.authlib.Agent;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;

import lunadevs.luna.main.Luna;
import lunadevs.luna.utils.FileUtils;

import java.net.Proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.util.Session;
import org.apache.logging.log4j.core.appender.FileManager;

public class AltLoginThread
        extends Thread
{
	
    private final Minecraft mc = Minecraft.getMinecraft();
    private final String password;
    private String status;
    private final String username;

    public AltLoginThread(String username, String password)
    {
        super("Alt Login Thread");
        this.username = username;
        this.password = password;
        this.status = "\247eWaiting...";
    }

    private final Session createSession(String username, String password)
    {
        YggdrasilAuthenticationService service = new YggdrasilAuthenticationService(
                Proxy.NO_PROXY, "");
        YggdrasilUserAuthentication auth = (YggdrasilUserAuthentication)service
                .createUserAuthentication(Agent.MINECRAFT);
        auth.setUsername(username);
        auth.setPassword(password);
        try
        {
            auth.logIn();
            return new Session(auth.getSelectedProfile().getName(), auth
                    .getSelectedProfile().getId().toString(),
                    auth.getAuthenticatedToken(), "mojang");
        }
        catch (AuthenticationException e) {}
        return null;
    }

    public String getStatus()
    {
        return this.status;
    }

    public void run()
    {
        if (this.password.equals(""))
        {
            this.mc.session = new Session(this.username, "", "", "mojang");
            this.status = ("\247aLogged in. (" + this.username + " - offline name)");
            return;
        }
        this.status = "\2471Logging in...";
        Session auth = createSession(this.username, this.password);
        if (auth == null)
        {
            this.status = "\2474Login failed!";
        }
        else
        {
            Luna.getAltManager().setLastAlt(new Alt(this.username, this.password));
            FileUtils.saveLastAlt();
            this.status = ("\247aLogged in. (" + auth.getUsername() + ")");
            this.mc.session = auth;
        }
    }

    
        public void setStatus(String status)
    {
        this.status = status;
    }
}
