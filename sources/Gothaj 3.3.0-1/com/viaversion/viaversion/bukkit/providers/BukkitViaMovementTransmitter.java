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
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class BukkitViaMovementTransmitter extends MovementTransmitterProvider {
   private static boolean USE_NMS = true;
   private Object idlePacket;
   private Object idlePacket2;
   private Method getHandle;
   private Field connection;
   private Method handleFlying;

   public BukkitViaMovementTransmitter() {
      USE_NMS = Via.getConfig().isNMSPlayerTicking();

      Class<?> idlePacketClass;
      try {
         idlePacketClass = NMSUtil.nms("PacketPlayInFlying");
      } catch (ClassNotFoundException var7) {
         return;
      }

      try {
         this.idlePacket = idlePacketClass.newInstance();
         this.idlePacket2 = idlePacketClass.newInstance();
         Field flying = idlePacketClass.getDeclaredField("f");
         flying.setAccessible(true);
         flying.set(this.idlePacket2, true);
      } catch (InstantiationException | IllegalArgumentException | IllegalAccessException | NoSuchFieldException var6) {
         throw new RuntimeException("Couldn't make player idle packet, help!", var6);
      }

      if (USE_NMS) {
         try {
            this.getHandle = NMSUtil.obc("entity.CraftPlayer").getDeclaredMethod("getHandle");
         } catch (ClassNotFoundException | NoSuchMethodException var5) {
            throw new RuntimeException("Couldn't find CraftPlayer", var5);
         }

         try {
            this.connection = NMSUtil.nms("EntityPlayer").getDeclaredField("playerConnection");
         } catch (ClassNotFoundException | NoSuchFieldException var4) {
            throw new RuntimeException("Couldn't find Player Connection", var4);
         }

         try {
            this.handleFlying = NMSUtil.nms("PlayerConnection").getDeclaredMethod("a", idlePacketClass);
         } catch (ClassNotFoundException | NoSuchMethodException var3) {
            throw new RuntimeException("Couldn't find CraftPlayer", var3);
         }
      }
   }

   public Object getFlyingPacket() {
      if (this.idlePacket == null) {
         throw new NullPointerException("Could not locate flying packet");
      } else {
         return this.idlePacket;
      }
   }

   public Object getGroundPacket() {
      if (this.idlePacket == null) {
         throw new NullPointerException("Could not locate flying packet");
      } else {
         return this.idlePacket2;
      }
   }

   @Override
   public void sendPlayer(UserConnection info) {
      if (USE_NMS) {
         Player player = Bukkit.getPlayer(info.getProtocolInfo().getUuid());
         if (player != null) {
            try {
               Object entityPlayer = this.getHandle.invoke(player);
               Object pc = this.connection.get(entityPlayer);
               if (pc != null) {
                  this.handleFlying.invoke(pc, info.get(MovementTracker.class).isGround() ? this.idlePacket2 : this.idlePacket);
                  info.get(MovementTracker.class).incrementIdlePacket();
               }
            } catch (InvocationTargetException | IllegalAccessException var5) {
               var5.printStackTrace();
            }
         }
      } else {
         ChannelHandlerContext context = PipelineUtil.getContextBefore("decoder", info.getChannel().pipeline());
         if (context != null) {
            if (info.get(MovementTracker.class).isGround()) {
               context.fireChannelRead(this.getGroundPacket());
            } else {
               context.fireChannelRead(this.getFlyingPacket());
            }

            info.get(MovementTracker.class).incrementIdlePacket();
         }
      }
   }
}
