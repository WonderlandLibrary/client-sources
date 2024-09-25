/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  net.md_5.bungee.api.ProxyServer
 */
package us.myles.ViaVersion.bungee.providers;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.md_5.bungee.api.ProxyServer;
import us.myles.ViaVersion.api.Via;
import us.myles.ViaVersion.api.data.UserConnection;
import us.myles.ViaVersion.api.protocol.ProtocolVersion;
import us.myles.ViaVersion.protocols.base.ProtocolInfo;
import us.myles.ViaVersion.protocols.base.VersionProvider;
import us.myles.ViaVersion.util.ReflectionUtil;

public class BungeeVersionProvider
extends VersionProvider {
    private static Class<?> ref;

    @Override
    public int getServerProtocol(UserConnection user) throws Exception {
        if (ref == null) {
            return super.getServerProtocol(user);
        }
        List list = ReflectionUtil.getStatic(ref, "SUPPORTED_VERSION_IDS", List.class);
        ArrayList sorted = new ArrayList(list);
        Collections.sort(sorted);
        ProtocolInfo info = user.getProtocolInfo();
        if (sorted.contains(info.getProtocolVersion())) {
            return info.getProtocolVersion();
        }
        if (info.getProtocolVersion() < (Integer)sorted.get(0)) {
            return BungeeVersionProvider.getLowestSupportedVersion();
        }
        for (Integer protocol : Lists.reverse(sorted)) {
            if (info.getProtocolVersion() <= protocol || !ProtocolVersion.isRegistered(protocol)) continue;
            return protocol;
        }
        Via.getPlatform().getLogger().severe("Panic, no protocol id found for " + info.getProtocolVersion());
        return info.getProtocolVersion();
    }

    public static int getLowestSupportedVersion() {
        try {
            List list = ReflectionUtil.getStatic(ref, "SUPPORTED_VERSION_IDS", List.class);
            return (Integer)list.get(0);
        }
        catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return ProxyServer.getInstance().getProtocolVersion();
    }

    static {
        try {
            ref = Class.forName("net.md_5.bungee.protocol.ProtocolConstants");
        }
        catch (Exception e) {
            Via.getPlatform().getLogger().severe("Could not detect the ProtocolConstants class");
            e.printStackTrace();
        }
    }
}

