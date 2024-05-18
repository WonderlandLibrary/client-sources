package me.swezedcode.client.gui.alts;

import java.net.Proxy;

import com.mojang.authlib.Agent;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;

import net.minecraft.client.Minecraft;
import net.minecraft.util.Session;

public class PLoginUtils {
	
	private static Minecraft mc = Minecraft.getMinecraft();
	
	public static void login(String username, String password) throws Exception{
		YggdrasilAuthenticationService authService = new YggdrasilAuthenticationService(Proxy.NO_PROXY, "");
		YggdrasilUserAuthentication userAuth = (YggdrasilUserAuthentication) authService.createUserAuthentication(Agent.MINECRAFT);
		userAuth.setUsername(username);
		userAuth.setPassword(password);
		userAuth.logIn();
		mc.session = new Session(userAuth.getSelectedProfile().getName(), userAuth.getSelectedProfile().getId().toString(), userAuth.getAuthenticatedToken(), "mojang");
	}
}