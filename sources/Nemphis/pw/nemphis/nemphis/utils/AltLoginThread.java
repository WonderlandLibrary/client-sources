/*
 * Decompiled with CFR 0_118.
 * 
 * Could not load the following classes:
 *  com.mojang.authlib.Agent
 *  com.mojang.authlib.GameProfile
 *  com.mojang.authlib.UserAuthentication
 *  com.mojang.authlib.exceptions.AuthenticationException
 *  com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService
 *  com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication
 */
package pw.vertexcode.nemphis.utils;

import com.mojang.authlib.Agent;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.UserAuthentication;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
import java.io.PrintStream;
import java.net.Proxy;
import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Session;

public class AltLoginThread
extends Thread {
    private final Minecraft mc = Minecraft.getMinecraft();
    private final String password;
    private String status;
    private final String username;

    public AltLoginThread(String username, String password) {
        super("Alt Login Thread");
        this.username = username;
        this.password = password;
    }

    private final Session createSession(String username, String password) {
        YggdrasilAuthenticationService service = new YggdrasilAuthenticationService(Proxy.NO_PROXY, "");
        YggdrasilUserAuthentication auth = (YggdrasilUserAuthentication)service.createUserAuthentication(Agent.MINECRAFT);
        auth.setUsername(username);
        auth.setPassword(password);
        try {
            System.out.println("YEA");
            auth.logIn();
            return new Session(auth.getSelectedProfile().getName(), auth.getSelectedProfile().getId().toString(), auth.getAuthenticatedToken(), "mojang");
        }
        catch (AuthenticationException e) {
            System.out.println("NO");
            this.status = "\u00a7cLogin failed! \u00a78[\u00a74" + e.getMessage() + "\u00a78]";
            return null;
        }
    }

    public String getStatus() {
        return this.status;
    }

    @Override
    public void run() {
        if (this.password.equals("")) {
            System.out.println("Not Logged in");
            this.mc.session = new Session(this.username, "", "", "mojang");
            this.status = "\u00a7aLogged in. \u00a78[\u00a7a" + this.username + " - offline name\u00a78]";
            return;
        }
        this.status = "\u00a75Logging in...";
        Session auth = this.createSession(this.username, this.password);
        if (auth != null) {
            System.out.println("Logged in in Account");
            this.status = "\u00a7aLogged in. \u00a78[\u00a7a" + auth.getUsername() + "\u00a78]";
            this.mc.session = auth;
        }
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

