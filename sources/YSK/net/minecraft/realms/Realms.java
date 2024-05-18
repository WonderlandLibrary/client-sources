package net.minecraft.realms;

import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import java.net.*;
import net.minecraft.util.*;
import com.google.common.util.concurrent.*;
import com.mojang.util.*;
import com.mojang.authlib.*;
import net.minecraft.world.*;

public class Realms
{
    public static void setScreen(final RealmsScreen realmsScreen) {
        Minecraft.getMinecraft().displayGuiScreen(realmsScreen.getProxy());
    }
    
    public static String getUUID() {
        return Minecraft.getMinecraft().getSession().getPlayerID();
    }
    
    public static Proxy getProxy() {
        return Minecraft.getMinecraft().getProxy();
    }
    
    public static String sessionId() {
        final Session session = Minecraft.getMinecraft().getSession();
        String sessionID;
        if (session == null) {
            sessionID = null;
            "".length();
            if (-1 >= 0) {
                throw null;
            }
        }
        else {
            sessionID = session.getSessionID();
        }
        return sessionID;
    }
    
    public static ListenableFuture<Object> downloadResourcePack(final String s, final String s2) {
        return Minecraft.getMinecraft().getResourcePackRepository().downloadResourcePack(s, s2);
    }
    
    public static String getName() {
        return Minecraft.getMinecraft().getSession().getUsername();
    }
    
    public static String getGameDirectoryPath() {
        return Minecraft.getMinecraft().mcDataDir.getAbsolutePath();
    }
    
    public static String uuidToName(final String s) {
        return Minecraft.getMinecraft().getSessionService().fillProfileProperties(new GameProfile(UUIDTypeAdapter.fromString(s), (String)null), (boolean)("".length() != 0)).getName();
    }
    
    public static long currentTimeMillis() {
        return Minecraft.getSystemTime();
    }
    
    public static int creativeId() {
        return WorldSettings.GameType.CREATIVE.getID();
    }
    
    public static void clearResourcePack() {
        Minecraft.getMinecraft().getResourcePackRepository().func_148529_f();
    }
    
    public static int adventureId() {
        return WorldSettings.GameType.ADVENTURE.getID();
    }
    
    public static String getSessionId() {
        return Minecraft.getMinecraft().getSession().getSessionID();
    }
    
    public static String userName() {
        final Session session = Minecraft.getMinecraft().getSession();
        String username;
        if (session == null) {
            username = null;
            "".length();
            if (-1 != -1) {
                throw null;
            }
        }
        else {
            username = session.getUsername();
        }
        return username;
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (3 <= -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public static int spectatorId() {
        return WorldSettings.GameType.SPECTATOR.getID();
    }
    
    public static boolean isTouchScreen() {
        return Minecraft.getMinecraft().gameSettings.touchscreen;
    }
    
    public static int survivalId() {
        return WorldSettings.GameType.SURVIVAL.getID();
    }
    
    public static void setConnectedToRealms(final boolean b) {
        Minecraft.getMinecraft().func_181537_a(b);
    }
}
