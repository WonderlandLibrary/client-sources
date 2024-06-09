package HORIZON-6-0-SKIDPROTECTION;

import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.Agent;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import java.net.Proxy;

public class AltLoginThread extends Thread
{
    private final Minecraft HorizonCode_Horizon_È;
    private final String Â;
    private String Ý;
    private final String Ø­áŒŠá;
    
    public AltLoginThread(final String username, final String password) {
        super("Alt Login Thread");
        this.HorizonCode_Horizon_È = Minecraft.áŒŠà();
        this.Ø­áŒŠá = username;
        this.Â = password;
        this.Ý = "§7Waiting...";
    }
    
    private final Session HorizonCode_Horizon_È(final String username, final String password) {
        final YggdrasilAuthenticationService service = new YggdrasilAuthenticationService(Proxy.NO_PROXY, "");
        final YggdrasilUserAuthentication auth = (YggdrasilUserAuthentication)service.createUserAuthentication(Agent.MINECRAFT);
        auth.setUsername(username);
        auth.setPassword(password);
        try {
            auth.logIn();
            return new Session(auth.getSelectedProfile().getName(), auth.getSelectedProfile().getId().toString(), auth.getAuthenticatedToken(), "mojang");
        }
        catch (AuthenticationException e) {
            this.Ý = "§cLogin failed! §8[§c" + e.getMessage() + "§8]";
            return null;
        }
    }
    
    public String HorizonCode_Horizon_È() {
        return this.Ý;
    }
    
    @Override
    public void run() {
        if (this.Ø­áŒŠá.equals("")) {
            this.Ý = "§cUsername/E-Mail cannot be blank!";
            return;
        }
        if (this.Â.equals("")) {
            this.HorizonCode_Horizon_È.£à = new Session(this.Ø­áŒŠá, "", "", "mojang");
            this.Ý = "§aLogged in. (" + this.Ø­áŒŠá + "§a - offline name)";
            return;
        }
        this.Ý = "§eLogging in...";
        final Session auth = this.HorizonCode_Horizon_È(this.Ø­áŒŠá, this.Â);
        if (auth != null) {
            this.Ý = "§aLogged in. §8[§2" + auth.Ý() + "§8]";
            this.HorizonCode_Horizon_È.£à = auth;
        }
    }
    
    public void HorizonCode_Horizon_È(final String status) {
        this.Ý = status;
    }
}
