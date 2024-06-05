/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.mojang.authlib.Agent
 *  com.mojang.authlib.exceptions.AuthenticationException
 *  com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService
 *  com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication
 */
package digital.rbq.alt;

import com.mojang.authlib.Agent;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
import java.net.Proxy;
import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.Session;
import digital.rbq.notification.NotificationPublisher;
import digital.rbq.notification.NotificationType;

public final class AltLoginService
extends Thread {
    private final String password;
    private final String username;
    private String status;
    private final Minecraft mc = Minecraft.getMinecraft();

    public AltLoginService(String username, String password) {
        super("Alt Login Thread");
        this.username = username;
        this.password = password;
        this.status = (Object)((Object)EnumChatFormatting.GRAY) + "Waiting...";
    }

    private Session createSession(String username, String password) {
        YggdrasilAuthenticationService service = new YggdrasilAuthenticationService(Proxy.NO_PROXY, "");
        YggdrasilUserAuthentication auth = (YggdrasilUserAuthentication)service.createUserAuthentication(Agent.MINECRAFT);
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
            this.status = (Object)((Object)EnumChatFormatting.GREEN) + "Logged in as " + this.username + ".";
            return;
        }
        this.status = (Object)((Object)EnumChatFormatting.GREEN) + "Logging in...";
        Session auth = this.createSession(this.username, this.password);
        if (auth == null) {
            NotificationPublisher.queue("Login Failure", "Failed to login to account!", NotificationType.ERROR);
            this.status = (Object)((Object)EnumChatFormatting.RED) + "Failed to login!";
        } else {
            this.status = (Object)((Object)EnumChatFormatting.GREEN) + "Logged in as " + auth.getUsername() + ".";
            NotificationPublisher.queue("Login Success", "Successfully logged in as " + auth.getUsername(), NotificationType.SUCCESS);
            this.mc.session = auth;
        }
    }
}

