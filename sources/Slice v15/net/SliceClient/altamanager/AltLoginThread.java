package net.SliceClient.altamanager;

import com.mojang.authlib.Agent;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
import java.net.Proxy;
import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Session;

public class AltLoginThread
  extends Thread
{
  private final Minecraft mc = Minecraft.getMinecraft();
  private final String password;
  private String status;
  private final String username;
  
  public AltLoginThread(String username, String password)
  {
    super("Alt Login Thread");
    this.username = username;
    this.password = password;
    status = "§7Waiting...";
  }
  
  private final Session createSession(String username, String password)
  {
    YggdrasilAuthenticationService service = new YggdrasilAuthenticationService(
      Proxy.NO_PROXY, "");
    YggdrasilUserAuthentication auth = (YggdrasilUserAuthentication)service
      .createUserAuthentication(Agent.MINECRAFT);
    auth.setUsername(username);
    auth.setPassword(password);
    try
    {
      auth.logIn();
      return new Session(auth.getSelectedProfile().getName(), auth
        .getSelectedProfile().getId().toString(), 
        auth.getAuthenticatedToken(), "mojang");
    } catch (AuthenticationException localAuthenticationException) {}
    return null;
  }
  
  public String getStatus()
  {
    return status;
  }
  
  public void run() {
    if (username.equals("")) {
      status = "§cUsername/E-Mail cannot be blank!";
      return;
    }
    if (password.equals("")) {
      mc.session = new Session(username, "", "", "mojang");
      status = ("§aLogged in. (" + username + "§a - offline name)");
      return;
    }
    status = "§eLogging in...";
    Session auth = createSession(username, password);
    if (auth == null)
    {
      status = "§cLogin failed!";
    }
    else
    {
      status = ("§aLogged in! (" + auth.getUsername() + "§a)");
      mc.session = auth;
    }
  }
  
  public void setStatus(String status)
  {
    this.status = status;
  }
}
