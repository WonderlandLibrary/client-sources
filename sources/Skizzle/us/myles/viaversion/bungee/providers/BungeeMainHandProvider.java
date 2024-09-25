/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.md_5.bungee.api.ProxyServer
 *  net.md_5.bungee.api.connection.ProxiedPlayer
 */
package us.myles.ViaVersion.bungee.providers;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import us.myles.ViaVersion.api.data.UserConnection;
import us.myles.ViaVersion.protocols.base.ProtocolInfo;
import us.myles.ViaVersion.protocols.protocol1_9to1_8.providers.MainHandProvider;

public class BungeeMainHandProvider
extends MainHandProvider {
    private static Method getSettings = null;
    private static Method setMainHand = null;

    @Override
    public void setMainHand(UserConnection user, int hand) {
        ProtocolInfo info = user.getProtocolInfo();
        if (info == null || info.getUuid() == null) {
            return;
        }
        ProxiedPlayer player = ProxyServer.getInstance().getPlayer(info.getUuid());
        if (player == null) {
            return;
        }
        try {
            Object settings = getSettings.invoke((Object)player, new Object[0]);
            if (settings != null) {
                setMainHand.invoke(settings, hand);
            }
        }
        catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    static {
        try {
            getSettings = Class.forName("net.md_5.bungee.UserConnection").getDeclaredMethod("getSettings", new Class[0]);
            setMainHand = Class.forName("net.md_5.bungee.protocol.packet.ClientSettings").getDeclaredMethod("setMainHand", Integer.TYPE);
        }
        catch (Exception exception) {
            // empty catch block
        }
    }
}

