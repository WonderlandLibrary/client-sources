/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  net.md_5.bungee.api.ProxyServer
 */
package com.viaversion.viaversion.bungee.providers;

import com.google.common.collect.Lists;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.ProtocolInfo;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.protocols.base.BaseVersionProvider;
import com.viaversion.viaversion.util.ReflectionUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.md_5.bungee.api.ProxyServer;

public class BungeeVersionProvider
extends BaseVersionProvider {
    private static Class<?> ref;

    @Override
    public int getClosestServerProtocol(UserConnection userConnection) throws Exception {
        if (ref == null) {
            return super.getClosestServerProtocol(userConnection);
        }
        List list = ReflectionUtil.getStatic(ref, "SUPPORTED_VERSION_IDS", List.class);
        ArrayList arrayList = new ArrayList(list);
        Collections.sort(arrayList);
        ProtocolInfo protocolInfo = userConnection.getProtocolInfo();
        if (arrayList.contains(protocolInfo.getProtocolVersion())) {
            return protocolInfo.getProtocolVersion();
        }
        if (protocolInfo.getProtocolVersion() < (Integer)arrayList.get(0)) {
            return BungeeVersionProvider.getLowestSupportedVersion();
        }
        for (Integer n : Lists.reverse(arrayList)) {
            if (protocolInfo.getProtocolVersion() <= n || !ProtocolVersion.isRegistered(n)) continue;
            return n;
        }
        Via.getPlatform().getLogger().severe("Panic, no protocol id found for " + protocolInfo.getProtocolVersion());
        return protocolInfo.getProtocolVersion();
    }

    public static int getLowestSupportedVersion() {
        try {
            List list = ReflectionUtil.getStatic(ref, "SUPPORTED_VERSION_IDS", List.class);
            return (Integer)list.get(0);
        } catch (IllegalAccessException | NoSuchFieldException reflectiveOperationException) {
            reflectiveOperationException.printStackTrace();
            return ProxyServer.getInstance().getProtocolVersion();
        }
    }

    static {
        try {
            ref = Class.forName("net.md_5.bungee.protocol.ProtocolConstants");
        } catch (Exception exception) {
            Via.getPlatform().getLogger().severe("Could not detect the ProtocolConstants class");
            exception.printStackTrace();
        }
    }
}

