// 
// Decompiled by Procyon v0.5.36
// 

package xyz.niggfaclient.gui.login;

import fr.litarvan.openauth.microsoft.model.response.MinecraftProfile;
import fr.litarvan.openauth.microsoft.MicrosoftAuthResult;
import fr.litarvan.openauth.microsoft.MicrosoftAuthenticationException;
import fr.litarvan.openauth.microsoft.MicrosoftAuthenticator;
import net.minecraft.util.Session;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.client.Minecraft;

public class AltLoginThread extends Thread
{
    private final Minecraft mc;
    private final String password;
    private final String username;
    private String status;
    
    public AltLoginThread(final String username, final String password) {
        super("Alt Login Thread");
        this.mc = Minecraft.getMinecraft();
        this.username = username;
        this.password = password;
        this.status = EnumChatFormatting.GRAY + "Waiting...";
    }
    
    @Override
    public void run() {
        if (this.password.equals("")) {
            this.mc.session = new Session(this.username, "", "", "mojang");
            this.status = EnumChatFormatting.GREEN + "Logged in as " + this.username + " (offline name)";
            return;
        }
        this.status = EnumChatFormatting.YELLOW + "Logging in...";
        final Session auth = this.createSession(this.username, this.password);
        if (auth == null) {
            this.status = EnumChatFormatting.RED + "Login failed!";
        }
        else {
            this.status = EnumChatFormatting.GREEN + "Logged in as " + auth.getUsername();
            this.mc.session = auth;
        }
    }
    
    private Session createSession(final String username, final String password) {
        this.status = EnumChatFormatting.YELLOW + "Logging in...";
        final MicrosoftAuthenticator authenticator = new MicrosoftAuthenticator();
        try {
            final MicrosoftAuthResult result = authenticator.loginWithCredentials(username, password);
            final MinecraftProfile profile = result.getProfile();
            return new Session(profile.getName(), profile.getId(), result.getAccessToken(), "microsoft");
        }
        catch (MicrosoftAuthenticationException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public String getStatus() {
        return this.status;
    }
    
    public void setStatus(final String status) {
        this.status = status;
    }
}
