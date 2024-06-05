package net.minecraft.network;

import cc.novoline.Novoline;
import cc.novoline.events.EventManager;
import cc.novoline.events.events.PacketDirection;
import cc.novoline.events.events.PacketEvent;
import cc.novoline.viaversion.utils.Util;
import com.google.common.collect.Queues;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollSocketChannel;
import io.netty.channel.local.LocalChannel;
import io.netty.channel.local.LocalServerChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.TimeoutException;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.GenericFutureListener;
import java.net.InetAddress;
import java.net.SocketAddress;
import java.util.Queue;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;
import javax.crypto.SecretKey;
import net.QD;
import net.Qn;
import net.Qo;
import net.a29;
import net.aAm;
import net.a_d;
import net.axv;
import net.x_;
import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.EnumPacketDirection;
import net.minecraft.network.INetHandler;
import net.minecraft.network.NettyCompressionEncoder;
import net.minecraft.network.NettyEncryptingDecoder;
import net.minecraft.network.NettyEncryptingEncoder;
import net.minecraft.network.NetworkManager$InboundHandlerTuplePacketListener;
import net.minecraft.network.Packet;
import net.minecraft.network.ThreadQuickExitException;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.CryptManager;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ITickable;
import net.minecraft.util.LazyLoadBase;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.jetbrains.annotations.NotNull;
import viaversion.viafabric.ViaFabric;

public class NetworkManager extends SimpleChannelInboundHandler {
   private static final Logger logger = LogManager.getLogger();
   public static final Marker logMarkerNetwork = MarkerManager.getMarker("NETWORK");
   public static final Marker logMarkerPackets = MarkerManager.getMarker("NETWORK_PACKETS", logMarkerNetwork);
   public static final AttributeKey attrKeyConnectionState = AttributeKey.valueOf("protocol");
   public static final LazyLoadBase CLIENT_NIO_EVENT_LOOP = new Qo();
   public static final LazyLoadBase CLIENT_EPOLL_EVENT_LOOP = new Qn();
   public static final LazyLoadBase CLIENT_LOCAL_EVENT_LOOP = new QD();
   private final EnumPacketDirection d;
   private final Queue outboundPacketsQueue = Queues.newConcurrentLinkedQueue();
   private final ReentrantReadWriteLock field_181680_j = new ReentrantReadWriteLock();
   private Channel channel;
   private SocketAddress socketAddress;
   private INetHandler packetListener;
   private IChatComponent terminationReason;
   private boolean isEncrypted;
   private boolean disconnected;

   public NetworkManager(EnumPacketDirection var1) {
      this.d = var1;
   }

   public void channelActive(ChannelHandlerContext var1) throws Exception {
      super.channelActive(var1);
      this.channel = var1.channel();
      this.socketAddress = this.channel.remoteAddress();
      NetworkManager var10000 = this;

      try {
         var10000.setConnectionState(EnumConnectionState.HANDSHAKING);
      } catch (Throwable var3) {
         logger.fatal(var3);
      }

   }

   public void setConnectionState(EnumConnectionState var1) {
      this.channel.attr(attrKeyConnectionState).set(var1);
      this.channel.config().setAutoRead(true);
      logger.debug("Enabled auto read");
   }

   public void channelInactive(ChannelHandlerContext var1) {
      this.closeChannel(new ChatComponentTranslation("disconnect.endOfStream", new Object[0]));
   }

   public void exceptionCaught(ChannelHandlerContext var1, Throwable var2) {
      ChatComponentTranslation var3;
      if(var2 instanceof TimeoutException) {
         var3 = new ChatComponentTranslation("disconnect.timeout", new Object[0]);
      } else {
         var3 = new ChatComponentTranslation("disconnect.genericReason", new Object[]{"Internal Exception: " + var2});
      }

      this.closeChannel(var3);
   }

   protected void channelRead0(ChannelHandlerContext var1, Packet var2) {
      PacketEvent var3 = new PacketEvent(var2, PacketDirection.INCOMING);
      EventManager.call(var3);
      if(!var3.isCancelled()) {
         if(this.channel.isOpen()) {
            try {
               var2.processPacket(this.packetListener);
            } catch (ThreadQuickExitException var5) {
               ;
            }
         }

      }
   }

   public void sendPacket(Packet var1) {
      PacketEvent var2 = new PacketEvent(var1, PacketDirection.OUTGOING);
      EventManager.call(var2);
      if(!var2.isCancelled()) {
         if(this.isChannelOpen()) {
            this.flushOutboundQueue();
            this.dispatchPacket(var1, (GenericFutureListener[])null);
         } else {
            WriteLock var3 = this.field_181680_j.writeLock();
            var3.lock();
            NetworkManager var10000 = this;

            try {
               var10000.outboundPacketsQueue.add(new NetworkManager$InboundHandlerTuplePacketListener(var1, (GenericFutureListener[])null));
            } finally {
               var3.unlock();
            }
         }

      }
   }

   public void sendPacketNoEvent(Packet var1) {
      if(this.isChannelOpen()) {
         this.flushOutboundQueue();
         this.dispatchPacket(var1, (GenericFutureListener[])null);
      } else {
         WriteLock var2 = this.field_181680_j.writeLock();
         var2.lock();
         NetworkManager var10000 = this;

         try {
            var10000.outboundPacketsQueue.add(new NetworkManager$InboundHandlerTuplePacketListener(var1, (GenericFutureListener[])null));
         } finally {
            var2.unlock();
         }
      }

   }

   @SafeVarargs
   public final void sendPacket(Packet var1, GenericFutureListener var2, GenericFutureListener... var3) {
      if(this.isChannelOpen()) {
         this.flushOutboundQueue();
         this.dispatchPacket(var1, (GenericFutureListener[])ArrayUtils.add(var3, 0, var2));
      } else {
         WriteLock var4 = this.field_181680_j.writeLock();
         var4.lock();
         NetworkManager var10000 = this;

         try {
            var10000.outboundPacketsQueue.add(new NetworkManager$InboundHandlerTuplePacketListener(var1, (GenericFutureListener[])ArrayUtils.add(var3, 0, var2)));
         } finally {
            var4.unlock();
         }
      }

   }

   private void dispatchPacket(Packet var1, GenericFutureListener[] var2) {
      Channel var3 = this.channel;
      EnumConnectionState var4 = EnumConnectionState.getFromPacket(var1);
      EnumConnectionState var5 = (EnumConnectionState)var3.attr(attrKeyConnectionState).get();
      if(var5 != var4) {
         logger.debug("Disabled auto read");
         var3.config().setAutoRead(false);
      }

      if(var3.eventLoop().inEventLoop()) {
         this.writeUndFlash(var1, var2, var4, var5);
      } else {
         var3.eventLoop().execute(this::lambda$dispatchPacket$0);
      }

   }

   private void writeUndFlash(Packet var1, GenericFutureListener[] var2, EnumConnectionState var3, EnumConnectionState var4) {
      if(var3 != var4) {
         this.setConnectionState(var3);
      }

      ChannelFuture var5 = this.channel.writeAndFlush(var1);
      var5.addListeners(var2);
      var5.addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
   }

   private void flushOutboundQueue() {
      if(this.channel != null && this.channel.isOpen()) {
         ReadLock var1 = this.field_181680_j.readLock();
         var1.lock();

         while(true) {
            NetworkManager var10000 = this;
            boolean var5 = false;

            try {
               var5 = true;
               if(var10000.outboundPacketsQueue.isEmpty()) {
                  var5 = false;
                  break;
               }

               NetworkManager$InboundHandlerTuplePacketListener var2 = (NetworkManager$InboundHandlerTuplePacketListener)this.outboundPacketsQueue.poll();
               this.dispatchPacket(NetworkManager$InboundHandlerTuplePacketListener.access$000(var2), NetworkManager$InboundHandlerTuplePacketListener.access$100(var2));
               var5 = false;
            } finally {
               if(var5) {
                  var1.unlock();
               }
            }
         }

         var1.unlock();
      }

   }

   public void processReceivedPackets() {
      this.flushOutboundQueue();
      INetHandler var1 = this.packetListener;
      if(var1 instanceof ITickable) {
         ((ITickable)var1).update();
      }

      this.channel.flush();
   }

   public SocketAddress getRemoteAddress() {
      return this.socketAddress;
   }

   public void closeChannel(IChatComponent var1) {
      Channel var2 = this.channel;
      if(var2.isOpen()) {
         var2.close().awaitUninterruptibly();
         this.terminationReason = var1;
      }

   }

   public boolean isLocalChannel() {
      return this.channel instanceof LocalChannel || this.channel instanceof LocalServerChannel;
   }

   public static NetworkManager createNetworkManagerAndConnect(InetAddress var0, int var1, boolean var2) {
      return ViaFabric.clientSideVersion == Novoline.getInstance().viaVersion()?linkingVia(var0, var1, var2):linking(var0, var1, var2);
   }

   @NotNull
   private static NetworkManager linking(InetAddress var0, int var1, boolean var2) {
      NetworkManager var3 = new NetworkManager(EnumPacketDirection.CLIENTBOUND);
      LazyLoadBase var4;
      Class var5;
      if(Epoll.isAvailable()) {
         var5 = EpollSocketChannel.class;
         var4 = CLIENT_EPOLL_EVENT_LOOP;
      } else {
         var5 = NioSocketChannel.class;
         var4 = CLIENT_NIO_EVENT_LOOP;
      }

      ((Bootstrap)((Bootstrap)((Bootstrap)(new Bootstrap()).group((EventLoopGroup)var4.getValue())).handler(new a_d(var3))).channel(var5)).connect(var0, var1).syncUninterruptibly();
      return var3;
   }

   @NotNull
   private static NetworkManager linkingVia(InetAddress var0, int var1, boolean var2) {
      Class var3;
      LazyLoadBase var4;
      if(Epoll.isAvailable()) {
         var3 = EpollSocketChannel.class;
         var4 = CLIENT_EPOLL_EVENT_LOOP;
      } else {
         var3 = NioSocketChannel.class;
         var4 = CLIENT_NIO_EVENT_LOOP;
      }

      a29 var5 = a29.a(EnumPacketDirection.CLIENTBOUND);
      ((Bootstrap)((Bootstrap)((Bootstrap)(new Bootstrap()).group((EventLoopGroup)var4.getValue())).handler(new axv(var5))).channel(var3)).connect(var0, var1).syncUninterruptibly();
      return var5;
   }

   @NotNull
   public static NetworkManager provideLocalClient(@NotNull SocketAddress var0) {
      NetworkManager var1 = new NetworkManager(EnumPacketDirection.CLIENTBOUND);
      ((Bootstrap)((Bootstrap)((Bootstrap)(new Bootstrap()).group((EventLoopGroup)CLIENT_LOCAL_EVENT_LOOP.getValue())).handler(new aAm(var1))).channel(LocalChannel.class)).connect(var0).syncUninterruptibly();
      return var1;
   }

   public void enableEncryption(SecretKey var1) {
      this.isEncrypted = true;
      this.channel.pipeline().addBefore("splitter", "decrypt", new NettyEncryptingDecoder(CryptManager.createNetCipherInstance(2, var1)));
      this.channel.pipeline().addBefore("prepender", "encrypt", new NettyEncryptingEncoder(CryptManager.createNetCipherInstance(1, var1)));
   }

   public boolean getIsencrypted() {
      return this.isEncrypted;
   }

   public boolean isChannelOpen() {
      return this.channel != null && this.channel.isOpen();
   }

   public boolean hasNoChannel() {
      return this.channel == null;
   }

   public INetHandler getNetHandler() {
      return this.packetListener;
   }

   public void setNetHandler(INetHandler var1) {
      Validate.notNull(var1, "packetListener", new Object[0]);
      logger.debug("Set listener of {} to {}", new Object[]{this, var1});
      this.packetListener = var1;
   }

   public IChatComponent getExitMessage() {
      return this.terminationReason;
   }

   public void disableAutoRead() {
      this.channel.config().setAutoRead(false);
   }

   public void setCompressionTreshold(int var1) {
      if(ViaFabric.clientSideVersion == Novoline.getInstance().viaVersion()) {
         this.setCompressionTresholdVia(var1);
      } else {
         this.setCompressionTresholdVanilla(var1);
      }

   }

   public void setCompressionTresholdVanilla(int var1) {
      if(this.channel.pipeline().get("decompress") instanceof x_) {
         ((x_)this.channel.pipeline().get("decompress")).a(var1);
      } else {
         this.channel.pipeline().addBefore("decoder", "decompress", new x_(var1));
      }

      if(this.channel.pipeline().get("compress") instanceof NettyCompressionEncoder) {
         ((NettyCompressionEncoder)this.channel.pipeline().get("decompress")).setCompressionTreshold(var1);
      } else {
         this.channel.pipeline().addBefore("encoder", "compress", new NettyCompressionEncoder(var1));
      }

   }

   public void setCompressionTresholdVia(int var1) {
      if(this.channel.pipeline().get("decompress") instanceof x_) {
         ((x_)this.channel.pipeline().get("decompress")).a(var1);
      } else {
         Util.decodeEncodePlacement(this.channel.pipeline(), "decoder", "decompress", new x_(var1));
      }

      if(this.channel.pipeline().get("compress") instanceof NettyCompressionEncoder) {
         ((NettyCompressionEncoder)this.channel.pipeline().get("decompress")).setCompressionTreshold(var1);
      } else {
         Util.decodeEncodePlacement(this.channel.pipeline(), "encoder", "compress", new NettyCompressionEncoder(var1));
      }

   }

   public void checkDisconnected() {
      if(this.channel != null && !this.channel.isOpen()) {
         if(!this.disconnected) {
            this.disconnected = true;
            if(this.getExitMessage() != null) {
               this.getNetHandler().onDisconnect(this.getExitMessage());
            } else if(this.getNetHandler() != null) {
               this.getNetHandler().onDisconnect(new ChatComponentText("Disconnected"));
            }
         } else {
            logger.warn("handleDisconnection() called twice");
         }
      }

   }

   private void lambda$dispatchPacket$0(Packet var1, GenericFutureListener[] var2, EnumConnectionState var3, EnumConnectionState var4) {
      this.writeUndFlash(var1, var2, var3, var4);
   }

   private static RuntimeException a(RuntimeException var0) {
      return var0;
   }
}
