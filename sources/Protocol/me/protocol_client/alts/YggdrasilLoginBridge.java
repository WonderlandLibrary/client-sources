package me.protocol_client.alts;

import java.net.Proxy;
import java.util.UUID;

import me.protocol_client.Wrapper;
import net.minecraft.util.Session;

import com.mojang.authlib.Agent;
import com.mojang.authlib.UserAuthentication;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;

public class YggdrasilLoginBridge
{
  public static Session loginWithPassword(String username, String password)
  {
    Session session = null;
    
    UserAuthentication auth = new YggdrasilUserAuthentication(new YggdrasilAuthenticationService(Proxy.NO_PROXY, UUID.randomUUID().toString()), Agent.MINECRAFT);
    auth.setUsername(username);
    auth.setPassword(password);
    try
    {
      auth.logIn();
      String userName = auth.getSelectedProfile().getName();
      UUID playerUUID = auth.getSelectedProfile().getId();
      String accessToken = auth.getAuthenticatedToken();
      
      System.out.println(userName + "'s (UUID: '" + playerUUID.toString() + "') accessToken: " + accessToken);
      
      session = new Session(userName, playerUUID.toString(), accessToken, username.contains("@") ? "mojang" : "legacy");
      
      Wrapper.mc().setSession(session);
      
      return session;
    }
    catch (AuthenticationException e)
    {
      e.printStackTrace();
    }
    return null;
  }
  
  public static Session loginWithoutPassword(String username)
  {
    Wrapper.mc().setSession(new Session(username, "", "", "legacy"));
    
    return Wrapper.mc().getSession();
  }
  
  public static Session loginWithAlt(Alt alt)
  {
    if (alt.isPremium()) {
      return loginWithPassword(alt.isMojang() ? alt.getEmail() : alt.getUserName(), alt.getPassword());
    }
    return loginWithoutPassword(alt.getUserName());
  }
}
