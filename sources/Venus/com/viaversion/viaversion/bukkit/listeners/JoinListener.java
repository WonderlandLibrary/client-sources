/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.player.PlayerJoinEvent
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

    private static Field findField(Class<?> clazz, String ... stringArray) throws NoSuchFieldException {
        for (Field field : clazz.getDeclaredFields()) {
            String string = field.getType().getSimpleName();
            for (String string2 : stringArray) {
                if (!string.equals(string2)) continue;
                if (!Modifier.isPublic(field.getModifiers())) {
                    field.setAccessible(false);
                }
                return field;
            }
        }
        throw new NoSuchFieldException(stringArray[0]);
    }

    private static Field findField(Class<?> clazz, Class<?> clazz2) throws NoSuchFieldException {
        for (Field field : clazz.getDeclaredFields()) {
            if (field.getType() != clazz2) continue;
            if (!Modifier.isPublic(field.getModifiers())) {
                field.setAccessible(false);
            }
            return field;
        }
        throw new NoSuchFieldException(clazz2.getSimpleName());
    }

    @EventHandler(priority=EventPriority.LOWEST)
    public void onJoin(PlayerJoinEvent playerJoinEvent) {
        Channel channel;
        if (CHANNEL == null) {
            return;
        }
        Player player = playerJoinEvent.getPlayer();
        try {
            channel = this.getChannel(player);
        } catch (Exception exception) {
            Via.getPlatform().getLogger().log(Level.WARNING, exception, () -> JoinListener.lambda$onJoin$0(player));
            return;
        }
        if (!channel.isOpen()) {
            return;
        }
        UserConnection userConnection = this.getUserConnection(channel);
        if (userConnection == null) {
            Via.getPlatform().getLogger().log(Level.WARNING, "Could not find UserConnection for logging-in player {0}", player.getUniqueId());
            return;
        }
        ProtocolInfo protocolInfo = userConnection.getProtocolInfo();
        protocolInfo.setUuid(player.getUniqueId());
        protocolInfo.setUsername(player.getName());
        Via.getManager().getConnectionManager().onLoginSuccess(userConnection);
    }

    @Nullable
    private UserConnection getUserConnection(Channel channel) {
        BukkitEncodeHandler bukkitEncodeHandler = channel.pipeline().get(BukkitEncodeHandler.class);
        return bukkitEncodeHandler != null ? bukkitEncodeHandler.connection() : null;
    }

    private Channel getChannel(Player player) throws Exception {
        Object object = GET_HANDLE.invoke(player, new Object[0]);
        Object object2 = CONNECTION.get(object);
        Object object3 = NETWORK_MANAGER.get(object2);
        return (Channel)CHANNEL.get(object3);
    }

    private static String lambda$onJoin$0(Player player) {
        return "Could not find Channel for logging-in player " + player.getUniqueId();
    }

    static {
        Method method = null;
        Field field = null;
        Field field2 = null;
        Field field3 = null;
        try {
            method = NMSUtil.obc("entity.CraftPlayer").getDeclaredMethod("getHandle", new Class[0]);
            field = JoinListener.findField(method.getReturnType(), "PlayerConnection", "ServerGamePacketListenerImpl");
            field2 = JoinListener.findField(field.getType(), "NetworkManager", "Connection");
            field3 = JoinListener.findField(field2.getType(), Class.forName("io.netty.channel.Channel"));
        } catch (ClassNotFoundException | NoSuchFieldException | NoSuchMethodException reflectiveOperationException) {
            Via.getPlatform().getLogger().log(Level.WARNING, "Couldn't find reflection methods/fields to access Channel from player.\nLogin race condition fixer will be disabled.\n Some plugins that use ViaAPI on join event may work incorrectly.", reflectiveOperationException);
        }
        GET_HANDLE = method;
        CONNECTION = field;
        NETWORK_MANAGER = field2;
        CHANNEL = field3;
    }
}

