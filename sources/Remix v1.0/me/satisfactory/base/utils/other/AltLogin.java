package me.satisfactory.base.utils.other;

import net.minecraft.client.*;
import net.minecraft.util.*;
import java.net.*;
import com.mojang.authlib.yggdrasil.*;
import com.mojang.authlib.*;
import com.mojang.authlib.exceptions.*;

public class AltLogin extends Thread
{
    private final String password;
    private final String username;
    private String status;
    private Minecraft mc;
    
    public AltLogin(final String username, final String password) {
        super("Alt Login Thread");
        this.mc = Minecraft.getMinecraft();
        this.username = username;
        this.password = password;
        this.status = EnumChatFormatting.GRAY + "Waiting...";
    }
    
    private Session createSession(final String username, final String password) {
        final YggdrasilAuthenticationService service = new YggdrasilAuthenticationService(Proxy.NO_PROXY, "");
        final YggdrasilUserAuthentication auth = (YggdrasilUserAuthentication)service.createUserAuthentication(Agent.MINECRAFT);
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
    
    public void setStatus(final String status) {
        this.status = status;
    }
    
    @Override
    public void run() {
        if (this.password.equals("")) {
            this.mc.session = new Session(this.username, "", "", "mojang");
            this.status = EnumChatFormatting.GREEN + "Logged in. (" + this.username + " - offline name)";
            return;
        }
        this.status = EnumChatFormatting.AQUA + "Logging in...";
        final Session auth = this.createSession(this.username, this.password);
        if (auth == null) {
            this.status = EnumChatFormatting.RED + "Login failed!";
        }
        else {
            this.status = EnumChatFormatting.GREEN + "Logged in. (" + auth.getUsername() + ")";
            this.mc.session = auth;
        }
    }
}
