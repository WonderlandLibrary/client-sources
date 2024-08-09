/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.velocitypowered.api.network.ProtocolVersion
 *  com.velocitypowered.api.proxy.ServerConnection
 */
package com.viaversion.viaversion.velocity.providers;

import com.velocitypowered.api.proxy.ServerConnection;
import com.viaversion.viaversion.VelocityPlugin;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.protocols.base.BaseVersionProvider;
import com.viaversion.viaversion.velocity.platform.VelocityViaInjector;
import io.netty.channel.ChannelHandler;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.IntStream;
import org.jetbrains.annotations.Nullable;

public class VelocityVersionProvider
extends BaseVersionProvider {
    private static final Method GET_ASSOCIATION = VelocityVersionProvider.getAssociationMethod();

    @Nullable
    private static Method getAssociationMethod() {
        try {
            return Class.forName("com.velocitypowered.proxy.connection.MinecraftConnection").getMethod("getAssociation", new Class[0]);
        } catch (ClassNotFoundException | NoSuchMethodException reflectiveOperationException) {
            reflectiveOperationException.printStackTrace();
            return null;
        }
    }

    @Override
    public int getClosestServerProtocol(UserConnection userConnection) throws Exception {
        return userConnection.isClientSide() ? this.getBackProtocol(userConnection) : this.getFrontProtocol(userConnection);
    }

    private int getBackProtocol(UserConnection userConnection) throws Exception {
        ChannelHandler channelHandler = userConnection.getChannel().pipeline().get("handler");
        ServerConnection serverConnection = (ServerConnection)GET_ASSOCIATION.invoke(channelHandler, new Object[0]);
        return Via.proxyPlatform().protocolDetectorService().serverProtocolVersion(serverConnection.getServerInfo().getName());
    }

    private int getFrontProtocol(UserConnection userConnection) throws Exception {
        int[] nArray;
        int n = userConnection.getProtocolInfo().getProtocolVersion();
        IntStream intStream = com.velocitypowered.api.network.ProtocolVersion.SUPPORTED_VERSIONS.stream().mapToInt(com.velocitypowered.api.network.ProtocolVersion::getProtocol);
        if (VelocityViaInjector.GET_PLAYER_INFO_FORWARDING_MODE != null && ((Enum)VelocityViaInjector.GET_PLAYER_INFO_FORWARDING_MODE.invoke(VelocityPlugin.PROXY.getConfiguration(), new Object[0])).name().equals("MODERN")) {
            intStream = intStream.filter(VelocityVersionProvider::lambda$getFrontProtocol$0);
        }
        if (Arrays.binarySearch(nArray = intStream.toArray(), n) >= 0) {
            return n;
        }
        if (n < nArray[0]) {
            return nArray[0];
        }
        for (int i = nArray.length - 1; i >= 0; --i) {
            int n2 = nArray[i];
            if (n <= n2 || !ProtocolVersion.isRegistered(n2)) continue;
            return n2;
        }
        Via.getPlatform().getLogger().severe("Panic, no protocol id found for " + n);
        return n;
    }

    private static boolean lambda$getFrontProtocol$0(int n) {
        return n >= ProtocolVersion.v1_13.getVersion();
    }
}

