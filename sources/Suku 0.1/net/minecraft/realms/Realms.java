package net.minecraft.realms;

import com.google.common.util.concurrent.ListenableFuture;
import com.mojang.authlib.GameProfile;
import com.mojang.util.UUIDTypeAdapter;
import java.net.Proxy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.Session;
import net.minecraft.world.WorldSettings;

public class Realms
{
    public static boolean isTouchScreen()
    {
        return Minecraft.getInstance().settings.touchscreen;
    }

    public static Proxy getProxy()
    {
        return Minecraft.getInstance().getProxy();
    }

    public static String sessionId()
    {
        Session session = Minecraft.getInstance().getSession();
        return session == null ? null : session.getSessionID();
    }

    public static String userName()
    {
        Session session = Minecraft.getInstance().getSession();
        return session == null ? null : session.getUsername();
    }

    public static long currentTimeMillis()
    {
        return Minecraft.getSystemTime();
    }

    public static String getSessionId()
    {
        return Minecraft.getInstance().getSession().getSessionID();
    }

    public static String getUUID()
    {
        return Minecraft.getInstance().getSession().getPlayerID();
    }

    public static String getName()
    {
        return Minecraft.getInstance().getSession().getUsername();
    }

    public static String uuidToName(String p_uuidToName_0_)
    {
        return Minecraft.getInstance().getSessionService().fillProfileProperties(new GameProfile(UUIDTypeAdapter.fromString(p_uuidToName_0_), (String)null), false).getName();
    }

    public static void setScreen(RealmsScreen p_setScreen_0_)
    {
        Minecraft.getInstance().display(p_setScreen_0_.getProxy());
    }

    public static String getGameDirectoryPath()
    {
        return Minecraft.getInstance().mcDataDir.getAbsolutePath();
    }

    public static int survivalId()
    {
        return WorldSettings.GameType.SURVIVAL.getID();
    }

    public static int creativeId()
    {
        return WorldSettings.GameType.CREATIVE.getID();
    }

    public static int adventureId()
    {
        return WorldSettings.GameType.ADVENTURE.getID();
    }

    public static int spectatorId()
    {
        return WorldSettings.GameType.SPECTATOR.getID();
    }

    public static void setConnectedToRealms(boolean p_setConnectedToRealms_0_)
    {
        Minecraft.getInstance().setConnectedToRealms(p_setConnectedToRealms_0_);
    }

    public static ListenableFuture<Object> downloadResourcePack(String p_downloadResourcePack_0_, String p_downloadResourcePack_1_)
    {
        ListenableFuture<Object> listenablefuture = Minecraft.getInstance().getResourcePackRepository().downloadResourcePack(p_downloadResourcePack_0_, p_downloadResourcePack_1_);
        return listenablefuture;
    }

    public static void clearResourcePack()
    {
        Minecraft.getInstance().getResourcePackRepository().clearResourcePack();
    }

    public static boolean getRealmsNotificationsEnabled()
    {
        return Minecraft.getInstance().settings.getOptionOrdinalValue(GameSettings.Options.REALMS_NOTIFICATIONS);
    }

    public static boolean inTitleScreen()
    {
        return Minecraft.getInstance().currentScreen != null && Minecraft.getInstance().currentScreen instanceof GuiMainMenu;
    }
}
