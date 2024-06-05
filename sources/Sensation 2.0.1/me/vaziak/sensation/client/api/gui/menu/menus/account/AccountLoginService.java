package me.vaziak.sensation.client.api.gui.menu.menus.account;

import com.mojang.authlib.Agent;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;

import me.vaziak.sensation.Sensation;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Session;

import java.net.Proxy;

public class AccountLoginService {

   private String status = "Log in please";

   public void attemptLogin(Account account) {
       try {
           Sensation.instance.altService.switchService(account.getService());

           if (account.getPassword().equals("")) {
               Minecraft.getMinecraft().session = new Session(account.getUsername(), "", "", "legacy");
               status = "Created session... logged in as " + Minecraft.getMinecraft().session.getUsername();
               return;
           }

           YggdrasilUserAuthentication authentication = (YggdrasilUserAuthentication) new YggdrasilAuthenticationService(Proxy.NO_PROXY, "")
                   .createUserAuthentication(Agent.MINECRAFT);
           authentication.setUsername(account.getUsername());
           authentication.setPassword(account.getPassword());
           authentication.logIn();

           if (authentication.getSelectedProfile() == null) {
               status = "failed to login. (Demo account?)";
               return;
           }
           Minecraft.getMinecraft().session = new Session(authentication.getSelectedProfile().getName(), authentication.getSelectedProfile().getId().toString(),
                   authentication.getAuthenticatedToken(), "mojang");
           status = "Created session... logged in as " + Minecraft.getMinecraft().session.getUsername();

           return;
       } catch (NoSuchFieldException | IllegalAccessException e) {
           e.printStackTrace();
       } catch (AuthenticationException e) {
           e.printStackTrace();
       }

       status = "Login failed.";
   }

   public String getStatus() {
       return status;
   }

	public void setStatus(String status) {
		this.status = status;
	}
}