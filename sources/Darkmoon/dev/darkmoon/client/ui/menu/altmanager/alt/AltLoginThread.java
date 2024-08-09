package dev.darkmoon.client.ui.menu.altmanager.alt;

import com.mojang.authlib.Agent;
import com.mojang.authlib.UserAuthentication;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Session;
import net.minecraft.util.text.TextFormatting;

import java.net.Proxy;

public class AltLoginThread extends Thread {

    private final Alt alt;
    private final Minecraft mc = Minecraft.getMinecraft();
    private String status;

    public AltLoginThread(Alt alt) {
        this.alt = alt;
        this.status = "§7Waiting...";
    }

    public Session login(String name, String password) {
        YggdrasilAuthenticationService service = new YggdrasilAuthenticationService(Proxy.NO_PROXY, "");
        UserAuthentication authentication = new YggdrasilUserAuthentication(service, Agent.MINECRAFT);
        YggdrasilUserAuthentication auth = (YggdrasilUserAuthentication) authentication;
        auth.setUsername(name);
        auth.setPassword(password);

        try {
            auth.logIn();
            return new Session(auth.getSelectedProfile().getName(), auth.getSelectedProfile().getId().toString(),
                    auth.getAuthenticatedToken(), "mojang");
        } catch (AuthenticationException exception) {
            return null;
        }
    }


    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void run() {
        if (this.alt.getPassword().equals("")) {
            this.mc.session = new Session(this.alt.getUsername(), "", "", "mojang");
            this.status = TextFormatting.GREEN + "Logged in - " + TextFormatting.WHITE + alt.getUsername() + TextFormatting.BOLD + TextFormatting.RED + " (Not License)";
        } else {
            this.status = "Logging in...";
            Session auth = login(this.alt.getUsername(), this.alt.getPassword());
            if (auth == null) {
                this.status = "Connect failed!";
                if (this.alt.getStatus().equals(Alt.Status.Unchecked)) {
                    this.alt.setStatus(Alt.Status.NotWorking);
                }
            } else {
                this.status = TextFormatting.GREEN + "Logged in - " + TextFormatting.WHITE + auth.getUsername() + TextFormatting.BOLD + TextFormatting.RED + " (License)";
                this.alt.setMask(auth.getUsername());
                this.mc.session = auth;
            }
        }
    }
}
