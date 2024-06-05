package ClassSub;

import net.minecraft.client.*;
import net.minecraft.util.*;
import java.net.*;
import com.mojang.authlib.yggdrasil.*;
import com.mojang.authlib.*;
import com.mojang.authlib.exceptions.*;
import cn.Hanabi.injection.interfaces.*;
import cn.Hanabi.*;

public final class Class82 extends Thread
{
    private Class309 alt;
    private String status;
    private Minecraft mc;
    
    
    public Class82(final Class309 alt) {
        super("Alt Login Thread");
        this.mc = Minecraft.getMinecraft();
        this.alt = alt;
        this.status = EnumChatFormatting.GRAY + "Waiting...";
    }
    
    private Session createSession(final String username, final String password) {
        final YggdrasilUserAuthentication yggdrasilUserAuthentication = (YggdrasilUserAuthentication)new YggdrasilAuthenticationService(Proxy.NO_PROXY, "").createUserAuthentication(Agent.MINECRAFT);
        yggdrasilUserAuthentication.setUsername(username);
        yggdrasilUserAuthentication.setPassword(password);
        try {
            yggdrasilUserAuthentication.logIn();
            return new Session(yggdrasilUserAuthentication.getSelectedProfile().getName(), yggdrasilUserAuthentication.getSelectedProfile().getId().toString(), yggdrasilUserAuthentication.getAuthenticatedToken(), "mojang");
        }
        catch (AuthenticationException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public String getStatus() {
        return this.status;
    }
    
    @Override
    public void run() {
        if (this.alt.getPassword().equals("")) {
            ((IMinecraft)Minecraft.getMinecraft()).setSession(new Session(this.alt.getUsername(), "", "", "mojang"));
            this.status = EnumChatFormatting.GREEN + "Logged in. (" + this.alt.getUsername() + " - offline name)";
            return;
        }
        this.status = EnumChatFormatting.AQUA + "Logging in...";
        final Session session = this.createSession(this.alt.getUsername(), this.alt.getPassword());
        if (session == null) {
            this.status = EnumChatFormatting.RED + "Login failed!";
        }
        else {
            Class206.lastAlt = new Class309(this.alt.getUsername(), this.alt.getPassword());
            this.status = EnumChatFormatting.GREEN + "Logged in. (" + session.getUsername() + ")";
            this.alt.setMask(session.getUsername());
            ((IMinecraft)Minecraft.getMinecraft()).setSession(session);
            try {
                Hanabi.INSTANCE.altFileMgr.getFile(Class85.class).saveFile();
            }
            catch (Exception ex) {}
        }
    }
    
    public void setStatus(final String status) {
        this.status = status;
    }
}
