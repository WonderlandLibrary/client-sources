package me.tireman.hexa.alts;

import ac.sapphire.client.Sapphire;
import ac.sapphire.client.alt.AltManager;
import ac.sapphire.client.alt.impl.MojangAlt;
import com.mojang.authlib.Agent;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.Session;

import java.net.Proxy;

public final class AltLoginThread extends Thread {
    private final Minecraft mc = Minecraft.getMinecraft();
    private final AltManager altManager = Sapphire.INSTANCE.getAltManager();
    private final String password;
    private final String username;

    private String status;

    public AltLoginThread(String username, String password) {
        super("Alt Login Thread");
        this.username = username;
        this.password = password;
        status = EnumChatFormatting.GRAY + "Waiting...";
    }

    @Override
    public void run() {
        if (password.equals("")) {
            mc.session = new Session(username, "", "", "mojang");
            status = EnumChatFormatting.GREEN + "Logged in. (" + username + " - offline name)";
            return;
        }

        status = EnumChatFormatting.YELLOW + "Logging in...";

        Session auth = createSession(username, password);
        if (auth == null) {
            status = EnumChatFormatting.RED + "Login failed!";
        } else {
            altManager.setLastAlt(new MojangAlt(username, password));
            status = EnumChatFormatting.GREEN + "Logged in. (" + auth.getUsername() + ")";
            mc.session = auth;
        }
    }

    private Session createSession(String username, String password) {
        YggdrasilAuthenticationService service = new YggdrasilAuthenticationService(Proxy.NO_PROXY, "");
        YggdrasilUserAuthentication auth = (YggdrasilUserAuthentication) service.createUserAuthentication(Agent.MINECRAFT);
        auth.setUsername(username);
        auth.setPassword(password);
        try {
            auth.logIn();
            return new Session(auth.getSelectedProfile().getName(), auth.getSelectedProfile().getId().toString(), auth.getAuthenticatedToken(), "mojang");
        } catch (AuthenticationException localAuthenticationException) {
            localAuthenticationException.printStackTrace();
            return null;
        }
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

