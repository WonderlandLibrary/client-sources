/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.client.account;

import com.mojang.authlib.Agent;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.UserAuthentication;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
import java.net.Proxy;
import java.util.UUID;
import me.thekirkayt.client.account.Alt;
import me.thekirkayt.client.gui.account.AccountScreen;
import me.thekirkayt.utils.ClientUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Session;

public class LoginThread
extends Thread {
    private Minecraft mc = ClientUtils.mc();
    private String pass;
    private String email;

    public LoginThread(Alt alt) {
        super("LoginThread");
        AccountScreen.getInstance().lastAlt = alt;
        this.email = alt.getEmail();
        this.pass = alt.getPassword();
        AccountScreen.getInstance().info = "\u00a7aLogging In...";
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
            AccountScreen.getInstance().info = "\u00a7eLogged In: " + this.email;
            return;
        }
        Session session = this.createSession(this.email, this.pass);
        if (session == null) {
            AccountScreen.getInstance().info = "\u00a7cLogin Failed";
        } else {
            this.mc.session = session;
            AccountScreen.getInstance().info = "\u00a7aLogged In: " + session.getUsername();
        }
    }
}

