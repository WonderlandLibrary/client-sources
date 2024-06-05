/*
 * Decompiled with CFR 0_132.
 */
package gq.vapu.czfclient.UI.Login;

import com.mojang.authlib.Agent;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
import gq.vapu.czfclient.Client;
import gq.vapu.czfclient.Manager.FileManager;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Session;

import java.net.Proxy;

public class AltLoginThread extends Thread {
    private final Minecraft mc = Minecraft.getMinecraft();
    private final String password;
    private final String username;
    private String status;

    public AltLoginThread(String username, String password) {
        super("Alt Login Thread");
        this.username = username;
        this.password = password;
        this.status = "\u00a7eWaiting...";
    }

    private final Session createSession(String username, String password) {
        YggdrasilAuthenticationService service = new YggdrasilAuthenticationService(Proxy.NO_PROXY, "");
        YggdrasilUserAuthentication auth = (YggdrasilUserAuthentication) service
                .createUserAuthentication(Agent.MINECRAFT);
        auth.setUsername(username);
        auth.setPassword(password);
        try {
            auth.logIn();
            return new Session(auth.getSelectedProfile().getName(), auth.getSelectedProfile().getId().toString(),
                    auth.getAuthenticatedToken(), "mojang");
        } catch (AuthenticationException authenticationException) {
            return null;
        }
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public void run() {
        if (this.password.equals("")) {
            this.mc.session = new Session(this.username, "", "", "mojang");
            this.status = "\u00a7aLogged in. (" + this.username + " - offline name)";
            return;
        }
        this.status = "\u00a7eLogging in...";
        Session auth = this.createSession(this.username, this.password);
        if (auth == null) {
            this.status = "\u00a7cLogin failed!";
        } else {
            Client.instance.getAltManager().setLastAlt(new Alt(this.username, this.password));
            FileManager.saveLastAlt();
            this.status = "\u00a7aLogged in. (" + auth.getUsername() + ")";
            this.mc.session = auth;
        }
    }
}
