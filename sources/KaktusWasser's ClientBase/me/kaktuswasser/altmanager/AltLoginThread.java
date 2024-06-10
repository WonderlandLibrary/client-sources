// 
// Decompiled by KaktusWasser
// https://www.youtube.com/KaktusWasserReal
// 

package me.kaktuswasser.altmanager;

import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.Agent;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;

import me.kaktuswasser.client.Client;
import me.kaktuswasser.client.utilities.Alt;

import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import java.net.Proxy;
import net.minecraft.util.Session;
import net.minecraft.client.Minecraft;

public class AltLoginThread extends Thread
{
    private final Minecraft mc;
    private final String password;
    private String status;
    private final String username;
    
    public AltLoginThread(final String username, final String password) {
        super("Alt Login Thread");
        this.mc = Minecraft.getMinecraft();
        this.username = username;
        this.password = password;
        this.status = "§7Waiting...";
    }
    
    private final Session createSession(final String username, final String password) {
        final YggdrasilAuthenticationService service = new YggdrasilAuthenticationService(Proxy.NO_PROXY, "");
        final YggdrasilUserAuthentication auth = (YggdrasilUserAuthentication)service.createUserAuthentication(Agent.MINECRAFT);
        auth.setUsername(username);
        auth.setPassword(password);
        try {
            auth.logIn();
            return new Session(auth.getSelectedProfile().getName(), auth.getSelectedProfile().getId().toString(), auth.getAuthenticatedToken(), "mojang");
        }
        catch (AuthenticationException e) {
            return null;
        }
    }
    
    public String getStatus() {
        return this.status;
    }
    
    @Override
    public void run() {
        if (this.password.equals("")) {
            this.mc.session = new Session(this.username, "", "", "mojang");
            this.status = "§aLogged in. (" + this.username + " - offline name)";
            return;
        }
        this.status = "§eLogging in...";
        final Session auth = this.createSession(this.username, this.password);
        if (auth == null) {
            this.status = "§cLogin failed!";
        }
        else {
            Client.getAltManager().setLastAlt(new Alt(this.username, this.password));
            Client.getFileManager().getFileByName("lastalt").saveFile();
            this.status = "§aLogged in. (" + auth.getUsername() + ")";
            this.mc.session = auth;
        }
    }
    
    public void setStatus(final String status) {
        this.status = status;
    }
}
