/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.md_5.bungee.api.connection.ProxiedPlayer
 *  net.md_5.bungee.api.event.ServerConnectEvent
 *  net.md_5.bungee.api.event.ServerConnectedEvent
 *  net.md_5.bungee.api.event.ServerSwitchEvent
 *  net.md_5.bungee.api.plugin.Listener
 *  net.md_5.bungee.api.score.Team
 *  net.md_5.bungee.event.EventHandler
 *  net.md_5.bungee.protocol.packet.PluginMessage
 */
package us.myles.ViaVersion.bungee.handlers;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.event.ServerConnectedEvent;
import net.md_5.bungee.api.event.ServerSwitchEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.score.Team;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.protocol.packet.PluginMessage;
import us.myles.ViaVersion.api.PacketWrapper;
import us.myles.ViaVersion.api.Pair;
import us.myles.ViaVersion.api.Via;
import us.myles.ViaVersion.api.data.ExternalJoinGameListener;
import us.myles.ViaVersion.api.data.StoredObject;
import us.myles.ViaVersion.api.data.UserConnection;
import us.myles.ViaVersion.api.protocol.Protocol;
import us.myles.ViaVersion.api.protocol.ProtocolPipeline;
import us.myles.ViaVersion.api.protocol.ProtocolRegistry;
import us.myles.ViaVersion.api.protocol.ProtocolVersion;
import us.myles.ViaVersion.api.type.Type;
import us.myles.ViaVersion.bungee.service.ProtocolDetectorService;
import us.myles.ViaVersion.bungee.storage.BungeeStorage;
import us.myles.ViaVersion.protocols.base.ProtocolInfo;
import us.myles.ViaVersion.protocols.protocol1_13to1_12_2.packets.InventoryPackets;
import us.myles.ViaVersion.protocols.protocol1_9to1_8.Protocol1_9To1_8;
import us.myles.ViaVersion.protocols.protocol1_9to1_8.providers.EntityIdProvider;
import us.myles.ViaVersion.protocols.protocol1_9to1_8.storage.EntityTracker1_9;

public class BungeeServerHandler
implements Listener {
    private static Method getHandshake;
    private static Method getRelayMessages;
    private static Method setProtocol;
    private static Method getEntityMap;
    private static Method setVersion;
    private static Field entityRewrite;
    private static Field channelWrapper;

    @EventHandler(priority=120)
    public void onServerConnect(ServerConnectEvent e) {
        if (e.isCancelled()) {
            return;
        }
        UserConnection user = Via.getManager().getConnection(e.getPlayer().getUniqueId());
        if (user == null) {
            return;
        }
        if (!user.has(BungeeStorage.class)) {
            user.put(new BungeeStorage(user, e.getPlayer()));
        }
        int protocolId = ProtocolDetectorService.getProtocolId(e.getTarget().getName());
        List<Pair<Integer, Protocol>> protocols = ProtocolRegistry.getProtocolPath(user.getProtocolInfo().getProtocolVersion(), protocolId);
        try {
            Object handshake = getHandshake.invoke((Object)e.getPlayer().getPendingConnection(), new Object[0]);
            setProtocol.invoke(handshake, protocols == null ? user.getProtocolInfo().getProtocolVersion() : protocolId);
        }
        catch (IllegalAccessException | InvocationTargetException e1) {
            e1.printStackTrace();
        }
    }

    @EventHandler(priority=-120)
    public void onServerConnected(ServerConnectedEvent e) {
        try {
            this.checkServerChange(e, Via.getManager().getConnection(e.getPlayer().getUniqueId()));
        }
        catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    @EventHandler(priority=-120)
    public void onServerSwitch(ServerSwitchEvent e) {
        int playerId;
        UserConnection userConnection = Via.getManager().getConnection(e.getPlayer().getUniqueId());
        if (userConnection == null) {
            return;
        }
        try {
            playerId = Via.getManager().getProviders().get(EntityIdProvider.class).getEntityId(userConnection);
        }
        catch (Exception ex) {
            return;
        }
        for (StoredObject storedObject : userConnection.getStoredObjects().values()) {
            if (!(storedObject instanceof ExternalJoinGameListener)) continue;
            ((ExternalJoinGameListener)((Object)storedObject)).onExternalJoinGame(playerId);
        }
    }

    public void checkServerChange(ServerConnectedEvent e, UserConnection user) throws Exception {
        if (user == null) {
            return;
        }
        if (user.has(BungeeStorage.class)) {
            BungeeStorage storage = user.get(BungeeStorage.class);
            ProxiedPlayer player = storage.getPlayer();
            if (e.getServer() != null && !e.getServer().getInfo().getName().equals(storage.getCurrentServer())) {
                EntityTracker1_9 oldEntityTracker = user.get(EntityTracker1_9.class);
                if (oldEntityTracker != null && oldEntityTracker.isAutoTeam() && oldEntityTracker.isTeamExists()) {
                    oldEntityTracker.sendTeamPacket(false, true);
                }
                String serverName = e.getServer().getInfo().getName();
                storage.setCurrentServer(serverName);
                int protocolId = ProtocolDetectorService.getProtocolId(serverName);
                if (protocolId <= ProtocolVersion.v1_8.getVersion() && storage.getBossbar() != null) {
                    if (user.getProtocolInfo().getPipeline().contains(Protocol1_9To1_8.class)) {
                        for (UUID uuid : storage.getBossbar()) {
                            PacketWrapper wrapper = new PacketWrapper(12, null, user);
                            wrapper.write(Type.UUID, uuid);
                            wrapper.write(Type.VAR_INT, 1);
                            wrapper.send(Protocol1_9To1_8.class, true, true);
                        }
                    }
                    storage.getBossbar().clear();
                }
                ProtocolInfo info = user.getProtocolInfo();
                int previousServerProtocol = info.getServerProtocolVersion();
                List<Pair<Integer, Protocol>> protocols = ProtocolRegistry.getProtocolPath(info.getProtocolVersion(), protocolId);
                ProtocolPipeline pipeline = user.getProtocolInfo().getPipeline();
                user.clearStoredObjects();
                pipeline.cleanPipes();
                if (protocols == null) {
                    protocolId = info.getProtocolVersion();
                } else {
                    for (Pair<Integer, Protocol> pair : protocols) {
                        pipeline.add(pair.getValue());
                    }
                }
                info.setServerProtocolVersion(protocolId);
                pipeline.add(ProtocolRegistry.getBaseProtocol(protocolId));
                Object relayMessages = getRelayMessages.invoke((Object)e.getPlayer().getPendingConnection(), new Object[0]);
                for (Object message : (List)relayMessages) {
                    PluginMessage plMsg = (PluginMessage)message;
                    String channel = plMsg.getTag();
                    int id1_13 = ProtocolVersion.v1_13.getVersion();
                    if (previousServerProtocol != -1) {
                        String oldChannel = channel;
                        if (previousServerProtocol < id1_13 && protocolId >= id1_13) {
                            if ((channel = InventoryPackets.getNewPluginChannelId(channel)) == null) {
                                throw new RuntimeException(oldChannel + " found in relayMessages");
                            }
                            if (channel.equals("minecraft:register")) {
                                plMsg.setData(Arrays.stream(new String(plMsg.getData(), StandardCharsets.UTF_8).split("\u0000")).map(InventoryPackets::getNewPluginChannelId).filter(Objects::nonNull).collect(Collectors.joining("\u0000")).getBytes(StandardCharsets.UTF_8));
                            }
                        } else if (previousServerProtocol >= id1_13 && protocolId < id1_13) {
                            if ((channel = InventoryPackets.getOldPluginChannelId(channel)) == null) {
                                throw new RuntimeException(oldChannel + " found in relayMessages");
                            }
                            if (channel.equals("REGISTER")) {
                                plMsg.setData(Arrays.stream(new String(plMsg.getData(), StandardCharsets.UTF_8).split("\u0000")).map(InventoryPackets::getOldPluginChannelId).filter(Objects::nonNull).collect(Collectors.joining("\u0000")).getBytes(StandardCharsets.UTF_8));
                            }
                        }
                    }
                    plMsg.setTag(channel);
                }
                user.put(info);
                user.put(storage);
                user.setActive(protocols != null);
                for (Protocol protocol : pipeline.pipes()) {
                    protocol.init(user);
                }
                EntityTracker1_9 entityTracker1_9 = user.get(EntityTracker1_9.class);
                if (entityTracker1_9 != null && Via.getConfig().isAutoTeam()) {
                    String currentTeam = null;
                    for (Team team : player.getScoreboard().getTeams()) {
                        if (!team.getPlayers().contains(info.getUsername())) continue;
                        currentTeam = team.getName();
                    }
                    entityTracker1_9.setAutoTeam(true);
                    if (currentTeam == null) {
                        entityTracker1_9.sendTeamPacket(true, true);
                        entityTracker1_9.setCurrentTeam("viaversion");
                    } else {
                        entityTracker1_9.setAutoTeam(Via.getConfig().isAutoTeam());
                        entityTracker1_9.setCurrentTeam(currentTeam);
                    }
                }
                Object wrapper = channelWrapper.get((Object)player);
                setVersion.invoke(wrapper, protocolId);
                Object entityMap = getEntityMap.invoke(null, protocolId);
                entityRewrite.set((Object)player, entityMap);
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
            getRelayMessages = Class.forName("net.md_5.bungee.connection.InitialHandler").getDeclaredMethod("getRelayMessages", new Class[0]);
            setProtocol = Class.forName("net.md_5.bungee.protocol.packet.Handshake").getDeclaredMethod("setProtocolVersion", Integer.TYPE);
            getEntityMap = Class.forName("net.md_5.bungee.entitymap.EntityMap").getDeclaredMethod("getEntityMap", Integer.TYPE);
            setVersion = Class.forName("net.md_5.bungee.netty.ChannelWrapper").getDeclaredMethod("setVersion", Integer.TYPE);
            channelWrapper = Class.forName("net.md_5.bungee.UserConnection").getDeclaredField("ch");
            channelWrapper.setAccessible(true);
            entityRewrite = Class.forName("net.md_5.bungee.UserConnection").getDeclaredField("entityRewrite");
            entityRewrite.setAccessible(true);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}

