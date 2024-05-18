// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.client.triton.management.account;

import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.Agent;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import java.net.Proxy;
import net.minecraft.util.Session;
import net.minecraft.client.Minecraft;
import net.minecraft.client.triton.ui.account.AccountScreen;
import net.minecraft.client.triton.utils.ClientUtils;

public class LoginThread
extends Thread {
    private Minecraft mc = Minecraft.getMinecraft();
    private String pass;
    private String email;

    public LoginThread(String email, String pass) {
        super("LoginThread");
        this.email = email;
        this.pass = pass;
        AccountScreen.info = "\u00a7aLogging In...";
    }

    private Session createSession(String email, String pass) {
        YggdrasilAuthenticationService service = new YggdrasilAuthenticationService(Proxy.NO_PROXY, "");
        YggdrasilUserAuthentication authentication = (YggdrasilUserAuthentication)service.createUserAuthentication(Agent.MINECRAFT);
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
            AccountScreen.info = "\u00a7eLogged In: " + this.email;
            return;
        }
        Session session = this.createSession(this.email, this.pass);
        if (session == null) {
            AccountScreen.info = "\u00a7cLogin Failed";
        } else {
            this.mc.session = session;
            AccountScreen.info = "\u00a7aLogged In: " + session.getUsername();
        }
    }
}

