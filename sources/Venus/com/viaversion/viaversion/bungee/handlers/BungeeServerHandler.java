/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  net.md_5.bungee.api.connection.ProxiedPlayer
 *  net.md_5.bungee.api.event.ServerConnectEvent
 *  net.md_5.bungee.api.event.ServerConnectedEvent
 *  net.md_5.bungee.api.event.ServerSwitchEvent
 *  net.md_5.bungee.api.plugin.Listener
 *  net.md_5.bungee.event.EventHandler
 *  net.md_5.bungee.protocol.packet.PluginMessage
 */
package com.viaversion.viaversion.bungee.handlers;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.ProtocolInfo;
import com.viaversion.viaversion.api.connection.StorableObject;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.data.entity.ClientEntityIdChangeListener;
import com.viaversion.viaversion.api.data.entity.EntityTracker;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.ProtocolPathEntry;
import com.viaversion.viaversion.api.protocol.ProtocolPipeline;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.bungee.storage.BungeeStorage;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.packets.InventoryPackets;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.ClientboundPackets1_9;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.Protocol1_9To1_8;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.providers.EntityIdProvider;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.storage.EntityTracker1_9;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.event.ServerConnectedEvent;
import net.md_5.bungee.api.event.ServerSwitchEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.protocol.packet.PluginMessage;

public class BungeeServerHandler
implements Listener {
    private static Method getHandshake;
    private static Method getRegisteredChannels;
    private static Method getBrandMessage;
    private static Method setProtocol;
    private static Method getEntityMap;
    private static Method setVersion;
    private static Field entityRewrite;
    private static Field channelWrapper;

    @EventHandler(priority=120)
    public void onServerConnect(ServerConnectEvent serverConnectEvent) {
        if (serverConnectEvent.isCancelled()) {
            return;
        }
        UserConnection userConnection = Via.getManager().getConnectionManager().getConnectedClient(serverConnectEvent.getPlayer().getUniqueId());
        if (userConnection == null) {
            return;
        }
        if (!userConnection.has(BungeeStorage.class)) {
            userConnection.put(new BungeeStorage(serverConnectEvent.getPlayer()));
        }
        int n = Via.proxyPlatform().protocolDetectorService().serverProtocolVersion(serverConnectEvent.getTarget().getName());
        List<ProtocolPathEntry> list = Via.getManager().getProtocolManager().getProtocolPath(userConnection.getProtocolInfo().getProtocolVersion(), n);
        try {
            Object object = getHandshake.invoke(serverConnectEvent.getPlayer().getPendingConnection(), new Object[0]);
            setProtocol.invoke(object, list == null ? userConnection.getProtocolInfo().getProtocolVersion() : n);
        } catch (IllegalAccessException | InvocationTargetException reflectiveOperationException) {
            reflectiveOperationException.printStackTrace();
        }
    }

    @EventHandler(priority=-120)
    public void onServerConnected(ServerConnectedEvent serverConnectedEvent) {
        try {
            this.checkServerChange(serverConnectedEvent, Via.getManager().getConnectionManager().getConnectedClient(serverConnectedEvent.getPlayer().getUniqueId()));
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @EventHandler(priority=-120)
    public void onServerSwitch(ServerSwitchEvent serverSwitchEvent) {
        int n;
        UserConnection userConnection = Via.getManager().getConnectionManager().getConnectedClient(serverSwitchEvent.getPlayer().getUniqueId());
        if (userConnection == null) {
            return;
        }
        try {
            n = Via.getManager().getProviders().get(EntityIdProvider.class).getEntityId(userConnection);
        } catch (Exception exception) {
            return;
        }
        for (EntityTracker object : userConnection.getEntityTrackers()) {
            object.setClientEntityId(n);
        }
        for (StorableObject storableObject : userConnection.getStoredObjects().values()) {
            if (!(storableObject instanceof ClientEntityIdChangeListener)) continue;
            ((ClientEntityIdChangeListener)((Object)storableObject)).setClientEntityId(n);
        }
    }

    /*
     * WARNING - void declaration
     */
    public void checkServerChange(ServerConnectedEvent serverConnectedEvent, UserConnection userConnection) throws Exception {
        if (userConnection == null) {
            return;
        }
        if (userConnection.has(BungeeStorage.class)) {
            BungeeStorage bungeeStorage = userConnection.get(BungeeStorage.class);
            ProxiedPlayer proxiedPlayer = bungeeStorage.getPlayer();
            if (serverConnectedEvent.getServer() != null && !serverConnectedEvent.getServer().getInfo().getName().equals(bungeeStorage.getCurrentServer())) {
                Object object;
                Object object3;
                boolean bl;
                Object object4;
                EntityTracker1_9 entityTracker1_9 = (EntityTracker1_9)userConnection.getEntityTracker(Protocol1_9To1_8.class);
                if (entityTracker1_9 != null && entityTracker1_9.isAutoTeam() && entityTracker1_9.isTeamExists()) {
                    entityTracker1_9.sendTeamPacket(false, false);
                }
                String string = serverConnectedEvent.getServer().getInfo().getName();
                bungeeStorage.setCurrentServer(string);
                int n = Via.proxyPlatform().protocolDetectorService().serverProtocolVersion(string);
                if (n <= ProtocolVersion.v1_8.getVersion() && bungeeStorage.getBossbar() != null) {
                    if (userConnection.getProtocolInfo().getPipeline().contains(Protocol1_9To1_8.class)) {
                        for (UUID uUID : bungeeStorage.getBossbar()) {
                            object4 = PacketWrapper.create(ClientboundPackets1_9.BOSSBAR, null, userConnection);
                            object4.write(Type.UUID, uUID);
                            object4.write(Type.VAR_INT, 1);
                            object4.send(Protocol1_9To1_8.class);
                        }
                    }
                    bungeeStorage.getBossbar().clear();
                }
                ProtocolInfo protocolInfo = userConnection.getProtocolInfo();
                int n2 = protocolInfo.getServerProtocolVersion();
                object4 = Via.getManager().getProtocolManager().getProtocolPath(protocolInfo.getProtocolVersion(), n);
                ProtocolPipeline protocolPipeline = userConnection.getProtocolInfo().getPipeline();
                userConnection.clearStoredObjects(true);
                protocolPipeline.cleanPipes();
                if (object4 == null) {
                    n = protocolInfo.getProtocolVersion();
                } else {
                    ArrayList<Protocol> arrayList = new ArrayList<Protocol>(object4.size());
                    Iterator iterator2 = object4.iterator();
                    while (iterator2.hasNext()) {
                        ProtocolPathEntry protocolPathEntry = (ProtocolPathEntry)iterator2.next();
                        arrayList.add(protocolPathEntry.protocol());
                    }
                    protocolPipeline.add(arrayList);
                }
                protocolInfo.setServerProtocolVersion(n);
                protocolPipeline.add(Via.getManager().getProtocolManager().getBaseProtocol(n));
                int n3 = ProtocolVersion.v1_13.getVersion();
                boolean bl2 = n2 < n3 && n >= n3;
                boolean bl3 = bl = n2 >= n3 && n < n3;
                if (n2 != -1 && (bl2 || bl)) {
                    PluginMessage pluginMessage;
                    object3 = (Collection)getRegisteredChannels.invoke(serverConnectedEvent.getPlayer().getPendingConnection(), new Object[0]);
                    if (!object3.isEmpty()) {
                        HashSet<String> object22 = new HashSet<String>();
                        object = object3.iterator();
                        while (object.hasNext()) {
                            String string2;
                            String string3 = string2 = (String)object.next();
                            string2 = bl2 ? InventoryPackets.getNewPluginChannelId(string2) : InventoryPackets.getOldPluginChannelId(string2);
                            if (string2 == null) {
                                object.remove();
                                continue;
                            }
                            if (string3.equals(string2)) continue;
                            object.remove();
                            object22.add(string2);
                        }
                        object3.addAll(object22);
                    }
                    if ((pluginMessage = (PluginMessage)getBrandMessage.invoke(serverConnectedEvent.getPlayer().getPendingConnection(), new Object[0])) != null) {
                        object = pluginMessage.getTag();
                        object = bl2 ? InventoryPackets.getNewPluginChannelId((String)object) : InventoryPackets.getOldPluginChannelId((String)object);
                        if (object != null) {
                            pluginMessage.setTag((String)object);
                        }
                    }
                }
                userConnection.put(bungeeStorage);
                userConnection.setActive(object4 != null);
                for (Protocol protocol : protocolPipeline.pipes()) {
                    protocol.init(userConnection);
                }
                object3 = (EntityTracker1_9)userConnection.getEntityTracker(Protocol1_9To1_8.class);
                if (object3 != null && Via.getConfig().isAutoTeam()) {
                    void var16_25;
                    Object var16_24 = null;
                    for (String string2 : proxiedPlayer.getScoreboard().getTeams()) {
                        if (!string2.getPlayers().contains(protocolInfo.getUsername())) continue;
                        String string4 = string2.getName();
                    }
                    ((EntityTracker1_9)object3).setAutoTeam(false);
                    if (var16_25 == null) {
                        ((EntityTracker1_9)object3).sendTeamPacket(true, false);
                        ((EntityTracker1_9)object3).setCurrentTeam("viaversion");
                    } else {
                        ((EntityTracker1_9)object3).setAutoTeam(Via.getConfig().isAutoTeam());
                        ((EntityTracker1_9)object3).setCurrentTeam((String)var16_25);
                    }
                }
                Object object2 = channelWrapper.get(proxiedPlayer);
                setVersion.invoke(object2, n);
                object = getEntityMap.invoke(null, n);
                entityRewrite.set(proxiedPlayer, object);
            }
        }
    }

    static {
        getEntityMap = null;
        setVersion = null;
        entityRewrite = null;
        channelWrapper = null;
        try {
            getHandshake = Class.forName("net.md_5.bungee.connection.InitialHandler").getDeclaredMethod("getHandshake", new Class[0]);
            getRegisteredChannels = Class.forName("net.md_5.bungee.connection.InitialHandler").getDeclaredMethod("getRegisteredChannels", new Class[0]);
            getBrandMessage = Class.forName("net.md_5.bungee.connection.InitialHandler").getDeclaredMethod("getBrandMessage", new Class[0]);
            setProtocol = Class.forName("net.md_5.bungee.protocol.packet.Handshake").getDeclaredMethod("setProtocolVersion", Integer.TYPE);
            getEntityMap = Class.forName("net.md_5.bungee.entitymap.EntityMap").getDeclaredMethod("getEntityMap", Integer.TYPE);
            setVersion = Class.forName("net.md_5.bungee.netty.ChannelWrapper").getDeclaredMethod("setVersion", Integer.TYPE);
            channelWrapper = Class.forName("net.md_5.bungee.UserConnection").getDeclaredField("ch");
            channelWrapper.setAccessible(false);
            entityRewrite = Class.forName("net.md_5.bungee.UserConnection").getDeclaredField("entityRewrite");
            entityRewrite.setAccessible(false);
        } catch (Exception exception) {
            Via.getPlatform().getLogger().severe("Error initializing BungeeServerHandler, try updating BungeeCord or ViaVersion!");
            exception.printStackTrace();
        }
    }
}

