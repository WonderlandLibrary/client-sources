package wwe;

import net.minecraft.util.*;
import java.net.*;
import com.mojang.authlib.yggdrasil.*;
import com.mojang.authlib.*;
import wwe.utils.*;
import net.minecraft.util.text.*;
import com.mojang.authlib.exceptions.*;

public class exyI
{
    public exyI() {
        super();
    }
    
    public static Session UhdS(final String username, final String password) {
        if (username == null || username.length() <= 0 || password == null || password.length() <= 0) {
            return null;
        }
        final YggdrasilUserAuthentication yggdrasilUserAuthentication = (YggdrasilUserAuthentication)new YggdrasilAuthenticationService(Proxy.NO_PROXY, "").createUserAuthentication(Agent.MINECRAFT);
        yggdrasilUserAuthentication.setUsername(username);
        yggdrasilUserAuthentication.setPassword(password);
        try {
            yggdrasilUserAuthentication.logIn();
            if (Wrapper.mc.currentScreen instanceof NJJw) {
                ((NJJw)Wrapper.mc.currentScreen).lwDZ = TextFormatting.GREEN + "Login successful";
            }
            return new Session(yggdrasilUserAuthentication.getSelectedProfile().getName(), yggdrasilUserAuthentication.getSelectedProfile().getId().toString(), yggdrasilUserAuthentication.getAuthenticatedToken(), "LEGACY");
        }
        catch (AuthenticationException ex) {
            ex.printStackTrace();
            System.out.println("Failed login: " + username + ":" + password);
            if (Wrapper.mc.currentScreen instanceof NJJw) {
                ((NJJw)Wrapper.mc.currentScreen).lwDZ = TextFormatting.RED + "Login failed";
            }
            return null;
        }
    }
    
    public static Session cadX(final String s) {
        return new Session(s, "", "", "LEGACY");
    }
}
