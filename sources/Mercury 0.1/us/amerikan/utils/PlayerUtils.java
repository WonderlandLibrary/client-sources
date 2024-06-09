/*
 * Decompiled with CFR 0.145.
 */
package us.amerikan.utils;

import com.mojang.authlib.Agent;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.UserAuthentication;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
import java.net.Proxy;
import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.Session;

public class PlayerUtils {
    public static boolean aacdamage = false;
    public static double aacdamagevalue;

    public static void damagePlayer(double value) {
        aacdamage = true;
        aacdamagevalue = value + 2.85;
        Minecraft.getMinecraft();
        Minecraft.thePlayer.moveForward += 1.0f;
        Minecraft.getMinecraft();
        Minecraft.thePlayer.moveForward -= 1.0f;
        Minecraft.getMinecraft();
        Minecraft.thePlayer.moveStrafing -= 1.0f;
        Minecraft.getMinecraft();
        Minecraft.thePlayer.moveStrafing += 1.0f;
        Minecraft.getMinecraft();
        Minecraft.thePlayer.jump();
    }

    public static Session createSession(String username, String password, Proxy proxy) throws Exception {
        YggdrasilAuthenticationService service = new YggdrasilAuthenticationService(proxy, "");
        YggdrasilUserAuthentication auth = (YggdrasilUserAuthentication)service.createUserAuthentication(Agent.MINECRAFT);
        auth.setUsername(username);
        auth.setPassword(password);
        auth.logIn();
        return new Session(auth.getSelectedProfile().getName(), auth.getSelectedProfile().getId().toString(), auth.getAuthenticatedToken(), "mojang");
    }
}

