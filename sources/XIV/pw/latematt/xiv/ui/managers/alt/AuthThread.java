package pw.latematt.xiv.ui.managers.alt;

import com.mojang.authlib.Agent;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Session;
import org.lwjgl.input.Keyboard;
import pw.latematt.xiv.XIV;

import java.lang.reflect.Field;
import java.net.Proxy;

public class AuthThread implements Runnable {
    private final Minecraft mc = Minecraft.getMinecraft();
    private final AltAccount account;
    private String status;

    public AuthThread(AltAccount account) {
        this.account = account;
    }

    private Session createSession(String username, String password) {
        final YggdrasilAuthenticationService service = new YggdrasilAuthenticationService(Proxy.NO_PROXY, "");
        final YggdrasilUserAuthentication auth = (YggdrasilUserAuthentication) service.createUserAuthentication(Agent.MINECRAFT);
        auth.setUsername(username);
        auth.setPassword(password);
        try {
            auth.logIn();
            return new Session(auth.getSelectedProfile().getName(), auth.getSelectedProfile().getId().toString(), auth.getAuthenticatedToken(), "mojang");
        } catch (AuthenticationException e) {
            status = "\247cLogin failed.";
            return null;
        }
    }

    @Override
    public void run() {
        status = "\247e\247kLogging in...";
        Field session = null;
        for (Field field : Minecraft.class.getDeclaredFields()) {
            if (field.getName().equalsIgnoreCase("session") || field.getType() == Session.class) {
                session = field;
            }
        }

        if (session == null)
            session = Minecraft.class.getDeclaredFields()[28];

        session.setAccessible(true);

        if (account.getPassword().equals("")) {
            Session newSession = new Session(account.getUsername(), "", "", "mojang");
            try {
                session.set(mc, newSession);
                if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))
                    account.setKeyword(newSession.getUsername());
                status = "\247aLogged in as\247r " + newSession.getUsername();
            } catch (IllegalAccessException e) {
                XIV.getInstance().getLogger().warn("Failed to set session for alt login, a stacktrace has been printed.");
                e.printStackTrace();
            }
        } else {
            Session newSession = createSession(account.getUsername(), account.getPassword());
            if (newSession != null) {
                try {
                    session.set(mc, newSession);
                    if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))
                        account.setKeyword(newSession.getUsername());
                    status = "\247aLogged in as\247r " + newSession.getUsername();
                } catch (IllegalAccessException e) {
                    XIV.getInstance().getLogger().warn("Failed to set session for alt login, a stacktrace has been printed.");
                    e.printStackTrace();
                }
            }
        }
    }

    public String getStatus() {
        return status;
    }
}
