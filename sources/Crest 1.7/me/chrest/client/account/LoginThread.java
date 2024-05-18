// 
// Decompiled by Procyon v0.5.30
// 

package me.chrest.client.account;

import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.Agent;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import java.net.Proxy;
import net.minecraft.util.Session;
import me.chrest.client.gui.account.AccountScreen;
import net.minecraft.client.Minecraft;

public class LoginThread extends Thread
{
    private Minecraft mc;
    private String pass;
    private String email;
    
    public LoginThread(final String email, final String pass) {
        super("LoginThread");
        this.mc = Minecraft.getMinecraft();
        this.email = email;
        this.pass = pass;
        AccountScreen.info = "§aLogging In...";
    }
    
    private Session createSession(final String email, final String pass) {
        final YggdrasilAuthenticationService service = new YggdrasilAuthenticationService(Proxy.NO_PROXY, "");
        final YggdrasilUserAuthentication authentication = (YggdrasilUserAuthentication)service.createUserAuthentication(Agent.MINECRAFT);
        authentication.setUsername(email);
        authentication.setPassword(pass);
        try {
            authentication.logIn();
            return new Session(authentication.getSelectedProfile().getName(), authentication.getSelectedProfile().getId().toString(), authentication.getAuthenticatedToken(), "legacy");
        }
        catch (AuthenticationException e) {
            return null;
        }
    }
    
    @Override
    public void run() {
        if (this.pass.equals("")) {
            this.mc.session = new Session(this.email, "", "", "legacy");
            AccountScreen.info = "§eLogged In: " + this.email;
            return;
        }
        final Session session = this.createSession(this.email, this.pass);
        if (session == null) {
            AccountScreen.info = "§cLogin Failed";
        }
        else {
            this.mc.session = session;
            AccountScreen.info = "§aLogged In: " + session.getUsername();
        }
    }
}
