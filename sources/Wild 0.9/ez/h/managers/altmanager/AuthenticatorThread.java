package ez.h.managers.altmanager;

import java.net.*;
import com.mojang.authlib.yggdrasil.*;
import com.mojang.authlib.*;
import com.mojang.authlib.exceptions.*;

public class AuthenticatorThread extends Thread
{
    private final String username;
    private final String password;
    
    private bii loadSession() {
        final YggdrasilUserAuthentication yggdrasilUserAuthentication = (YggdrasilUserAuthentication)new YggdrasilAuthenticationService(Proxy.NO_PROXY, "").createUserAuthentication(Agent.MINECRAFT);
        yggdrasilUserAuthentication.setUsername(this.username);
        yggdrasilUserAuthentication.setPassword(this.password);
        System.out.println(this.username + ":" + this.password);
        try {
            yggdrasilUserAuthentication.logIn();
            return new bii(yggdrasilUserAuthentication.getSelectedProfile().getName(), yggdrasilUserAuthentication.getSelectedProfile().getId().toString(), yggdrasilUserAuthentication.getAuthenticatedToken(), "mojang");
        }
        catch (AuthenticationException ex) {
            return null;
        }
    }
    
    @Override
    public void run() {
        AltManagerGUI.status = a.o + "Authenticating...";
        if (this.username.isEmpty()) {
            return;
        }
        if (this.password.isEmpty()) {
            bib.z().af = new bii(this.username, "", "", "mojang");
            AltManagerGUI.status = a.k + "Logged in ~ Cracked";
        }
        else {
            final bii loadSession = this.loadSession();
            if (loadSession == null) {
                AltManagerGUI.status = a.m + "Invalid login";
                return;
            }
            bib.z().af = loadSession;
            AltManagerGUI.status = a.k + "Logged in ~ " + loadSession.c();
        }
    }
    
    public AuthenticatorThread(final String username, final String password) {
        this.username = username;
        this.password = password;
    }
}
