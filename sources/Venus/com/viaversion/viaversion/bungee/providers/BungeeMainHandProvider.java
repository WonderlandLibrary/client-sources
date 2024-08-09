/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  net.md_5.bungee.api.ProxyServer
 *  net.md_5.bungee.api.connection.ProxiedPlayer
 */
package com.viaversion.viaversion.bungee.providers;

import com.viaversion.viaversion.api.connection.ProtocolInfo;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.providers.MainHandProvider;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class BungeeMainHandProvider
extends MainHandProvider {
    private static Method getSettings = null;
    private static Method setMainHand = null;

    @Override
    public void setMainHand(UserConnection userConnection, int n) {
        ProtocolInfo protocolInfo = userConnection.getProtocolInfo();
        if (protocolInfo == null || protocolInfo.getUuid() == null) {
            return;
        }
        ProxiedPlayer proxiedPlayer = ProxyServer.getInstance().getPlayer(protocolInfo.getUuid());
        if (proxiedPlayer == null) {
            return;
        }
        try {
            Object object = getSettings.invoke(proxiedPlayer, new Object[0]);
            if (object != null) {
                setMainHand.invoke(object, n);
            }
        } catch (IllegalAccessException | InvocationTargetException reflectiveOperationException) {
            reflectiveOperationException.printStackTrace();
        }
    }

    static {
        try {
            getSettings = Class.forName("net.md_5.bungee.UserConnection").getDeclaredMethod("getSettings", new Class[0]);
            setMainHand = Class.forName("net.md_5.bungee.protocol.packet.ClientSettings").getDeclaredMethod("setMainHand", Integer.TYPE);
        } catch (Exception exception) {
            // empty catch block
        }
    }
}

