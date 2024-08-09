/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.entity.Player
 */
package com.viaversion.viaversion.bukkit.providers;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.bukkit.util.NMSUtil;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.providers.MovementTransmitterProvider;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.storage.MovementTracker;
import com.viaversion.viaversion.util.PipelineUtil;
import io.netty.channel.ChannelHandlerContext;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class BukkitViaMovementTransmitter
extends MovementTransmitterProvider {
    private static boolean USE_NMS = true;
    private Object idlePacket;
    private Object idlePacket2;
    private Method getHandle;
    private Field connection;
    private Method handleFlying;

    public BukkitViaMovementTransmitter() {
        Class<?> clazz;
        USE_NMS = Via.getConfig().isNMSPlayerTicking();
        try {
            clazz = NMSUtil.nms("PacketPlayInFlying");
        } catch (ClassNotFoundException classNotFoundException) {
            return;
        }
        try {
            this.idlePacket = clazz.newInstance();
            this.idlePacket2 = clazz.newInstance();
            Field field = clazz.getDeclaredField("f");
            field.setAccessible(false);
            field.set(this.idlePacket2, true);
        } catch (IllegalAccessException | IllegalArgumentException | InstantiationException | NoSuchFieldException exception) {
            throw new RuntimeException("Couldn't make player idle packet, help!", exception);
        }
        if (USE_NMS) {
            try {
                this.getHandle = NMSUtil.obc("entity.CraftPlayer").getDeclaredMethod("getHandle", new Class[0]);
            } catch (ClassNotFoundException | NoSuchMethodException reflectiveOperationException) {
                throw new RuntimeException("Couldn't find CraftPlayer", reflectiveOperationException);
            }
            try {
                this.connection = NMSUtil.nms("EntityPlayer").getDeclaredField("playerConnection");
            } catch (ClassNotFoundException | NoSuchFieldException reflectiveOperationException) {
                throw new RuntimeException("Couldn't find Player Connection", reflectiveOperationException);
            }
            try {
                this.handleFlying = NMSUtil.nms("PlayerConnection").getDeclaredMethod("a", clazz);
            } catch (ClassNotFoundException | NoSuchMethodException reflectiveOperationException) {
                throw new RuntimeException("Couldn't find CraftPlayer", reflectiveOperationException);
            }
        }
    }

    public Object getFlyingPacket() {
        if (this.idlePacket == null) {
            throw new NullPointerException("Could not locate flying packet");
        }
        return this.idlePacket;
    }

    public Object getGroundPacket() {
        if (this.idlePacket == null) {
            throw new NullPointerException("Could not locate flying packet");
        }
        return this.idlePacket2;
    }

    @Override
    public void sendPlayer(UserConnection userConnection) {
        if (USE_NMS) {
            Player player = Bukkit.getPlayer((UUID)userConnection.getProtocolInfo().getUuid());
            if (player != null) {
                try {
                    Object object = this.getHandle.invoke(player, new Object[0]);
                    Object object2 = this.connection.get(object);
                    if (object2 != null) {
                        this.handleFlying.invoke(object2, userConnection.get(MovementTracker.class).isGround() ? this.idlePacket2 : this.idlePacket);
                        userConnection.get(MovementTracker.class).incrementIdlePacket();
                    }
                } catch (IllegalAccessException | InvocationTargetException reflectiveOperationException) {
                    reflectiveOperationException.printStackTrace();
                }
            }
        } else {
            ChannelHandlerContext channelHandlerContext = PipelineUtil.getContextBefore("decoder", userConnection.getChannel().pipeline());
            if (channelHandlerContext != null) {
                if (userConnection.get(MovementTracker.class).isGround()) {
                    channelHandlerContext.fireChannelRead(this.getGroundPacket());
                } else {
                    channelHandlerContext.fireChannelRead(this.getFlyingPacket());
                }
                userConnection.get(MovementTracker.class).incrementIdlePacket();
            }
        }
    }
}

