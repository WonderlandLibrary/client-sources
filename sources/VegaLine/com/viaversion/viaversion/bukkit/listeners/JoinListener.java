/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.player.PlayerJoinEvent
 *  org.jetbrains.annotations.Nullable
 */
package com.viaversion.viaversion.bukkit.listeners;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.ProtocolInfo;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.bukkit.handlers.BukkitEncodeHandler;
import com.viaversion.viaversion.bukkit.util.NMSUtil;
import io.netty.channel.Channel;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.logging.Level;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.jetbrains.annotations.Nullable;

public class JoinListener
implements Listener {
    private static final Method GET_HANDLE;
    private static final Field CONNECTION;
    private static final Field NETWORK_MANAGER;
    private static final Field CHANNEL;

    private static Field findField(boolean all, Class<?> clazz, String ... types) throws NoSuchFieldException {
        for (Field field : all ? clazz.getFields() : clazz.getDeclaredFields()) {
            String fieldTypeName = field.getType().getSimpleName();
            for (String type2 : types) {
                if (!fieldTypeName.equals(type2)) continue;
                if (!Modifier.isPublic(field.getModifiers())) {
                    field.setAccessible(true);
                }
                return field;
            }
        }
        throw new NoSuchFieldException(types[0]);
    }

    private static Field findField(Class<?> clazz, Class<?> fieldType) throws NoSuchFieldException {
        for (Field field : clazz.getDeclaredFields()) {
            if (field.getType() != fieldType) continue;
            if (!Modifier.isPublic(field.getModifiers())) {
                field.setAccessible(true);
            }
            return field;
        }
        throw new NoSuchFieldException(fieldType.getSimpleName());
    }

    @EventHandler(priority=EventPriority.LOWEST)
    public void onJoin(PlayerJoinEvent e) {
        Channel channel;
        if (CHANNEL == null) {
            return;
        }
        Player player = e.getPlayer();
        try {
            channel = this.getChannel(player);
        } catch (Exception ex) {
            Via.getPlatform().getLogger().log(Level.WARNING, ex, () -> "Could not find Channel for logging-in player " + player.getUniqueId());
            return;
        }
        if (!channel.isOpen()) {
            return;
        }
        UserConnection user = this.getUserConnection(channel);
        if (user == null) {
            Via.getPlatform().getLogger().log(Level.WARNING, "Could not find UserConnection for logging-in player {0}", player.getUniqueId());
            return;
        }
        ProtocolInfo info = user.getProtocolInfo();
        info.setUuid(player.getUniqueId());
        info.setUsername(player.getName());
        Via.getManager().getConnectionManager().onLoginSuccess(user);
    }

    @Nullable
    private UserConnection getUserConnection(Channel channel) {
        BukkitEncodeHandler encoder = channel.pipeline().get(BukkitEncodeHandler.class);
        return encoder != null ? encoder.connection() : null;
    }

    private Channel getChannel(Player player) throws Exception {
        Object entityPlayer = GET_HANDLE.invoke(player, new Object[0]);
        Object pc = CONNECTION.get(entityPlayer);
        Object nm = NETWORK_MANAGER.get(pc);
        return (Channel)CHANNEL.get(nm);
    }

    static {
        Method getHandleMethod = null;
        Field gamePacketListenerField = null;
        Field connectionField = null;
        Field channelField = null;
        try {
            getHandleMethod = NMSUtil.obc("entity.CraftPlayer").getDeclaredMethod("getHandle", new Class[0]);
            gamePacketListenerField = JoinListener.findField(false, getHandleMethod.getReturnType(), "PlayerConnection", "ServerGamePacketListenerImpl");
            connectionField = JoinListener.findField(true, gamePacketListenerField.getType(), "NetworkManager", "Connection");
            channelField = JoinListener.findField(connectionField.getType(), Class.forName("io.netty.channel.Channel"));
        } catch (ClassNotFoundException | NoSuchFieldException | NoSuchMethodException e) {
            Via.getPlatform().getLogger().log(Level.WARNING, "Couldn't find reflection methods/fields to access Channel from player.\nLogin race condition fixer will be disabled.\n Some plugins that use ViaAPI on join event may work incorrectly.", e);
        }
        GET_HANDLE = getHandleMethod;
        CONNECTION = gamePacketListenerField;
        NETWORK_MANAGER = connectionField;
        CHANNEL = channelField;
    }
}

