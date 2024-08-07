package net.minecraft.network;

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
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.TimeoutException;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.GenericFutureListener;
import java.net.InetAddress;
import java.net.SocketAddress;
import java.util.Queue;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import javax.crypto.SecretKey;
import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.EnumPacketDirection;
import net.minecraft.network.INetHandler;
import net.minecraft.network.NettyCompressionDecoder;
import net.minecraft.network.NettyCompressionEncoder;
import net.minecraft.network.NettyEncryptingDecoder;
import net.minecraft.network.NettyEncryptingEncoder;
import net.minecraft.network.Packet;
import net.minecraft.network.ThreadQuickExitException;
import net.minecraft.network.NetworkManager.1;
import net.minecraft.network.NetworkManager.2;
import net.minecraft.network.NetworkManager.3;
import net.minecraft.network.NetworkManager.4;
import net.minecraft.network.NetworkManager.5;
import net.minecraft.network.NetworkManager.6;
import net.minecraft.network.NetworkManager.InboundHandlerTuplePacketListener;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.CryptManager;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ITickable;
import net.minecraft.util.LazyLoadBase;
import org.alphacentauri.management.events.EventPacketRecv;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

public class NetworkManager extends SimpleChannelInboundHandler {
   private static final Logger logger = LogManager.getLogger();
   public static final Marker logMarkerNetwork = MarkerManager.getMarker("NETWORK");
   public static final Marker logMarkerPackets = MarkerManager.getMarker("NETWORK_PACKETS", logMarkerNetwork);
   public static final AttributeKey attrKeyConnectionState = AttributeKey.valueOf("protocol");
   public static final LazyLoadBase CLIENT_NIO_EVENTLOOP = new 1();
   public static final LazyLoadBase field_181125_e = new 2();
   public static final LazyLoadBase CLIENT_LOCAL_EVENTLOOP = new 3();
   private final EnumPacketDirection direction;
   private final Queue outboundPacketsQueue = Queues.newConcurrentLinkedQueue();
   private final ReentrantReadWriteLock field_181680_j = new ReentrantReadWriteLock();
   private Channel channel;
   private SocketAddress socketAddress;
   private INetHandler packetListener;
   private IChatComponent terminationReason;
   private boolean isEncrypted;
   private boolean disconnected;

   public NetworkManager(EnumPacketDirection packetDirection) {
      this.direction = packetDirection;
   }

   // $FF: synthetic method
   static Channel access$000(NetworkManager x0) {
      return x0.channel;
   }

   public boolean isChannelOpen() {
      return this.channel != null && this.channel.isOpen();
   }

   public void processReceivedPackets() {
      this.flushOutboundQueue();
      if(this.packetListener instanceof ITickable) {
         ((ITickable)this.packetListener).update();
      }

      this.channel.flush();
   }

   public static NetworkManager provideLocalClient(SocketAddress address) {
      NetworkManager networkmanager = new NetworkManager(EnumPacketDirection.CLIENTBOUND);
      ((Bootstrap)((Bootstrap)((Bootstrap)(new Bootstrap()).group((EventLoopGroup)CLIENT_LOCAL_EVENTLOOP.getValue())).handler(new 6(networkmanager))).channel(LocalChannel.class)).connect(address).syncUninterruptibly();
      return networkmanager;
   }

   public void closeChannel(IChatComponent message) {
      if(this.channel.isOpen()) {
         this.channel.close().awaitUninterruptibly();
         this.terminationReason = message;
      }

   }

   public INetHandler getNetHandler() {
      return this.packetListener;
   }

   public void sendPacket(Packet packetIn) {
      if(this.isChannelOpen()) {
         this.flushOutboundQueue();
         this.dispatchPacket(packetIn, (GenericFutureListener[])null);
      } else {
         this.field_181680_j.writeLock().lock();

         try {
            this.outboundPacketsQueue.add(new InboundHandlerTuplePacketListener(packetIn, (GenericFutureListener[])null));
         } finally {
            this.field_181680_j.writeLock().unlock();
         }
      }

   }

   public void sendPacket(Packet packetIn, GenericFutureListener listener, GenericFutureListener... listeners) {
      if(this.isChannelOpen()) {
         this.flushOutboundQueue();
         this.dispatchPacket(packetIn, (GenericFutureListener[])((GenericFutureListener[])ArrayUtils.add(listeners, 0, listener)));
      } else {
         this.field_181680_j.writeLock().lock();

         try {
            this.outboundPacketsQueue.add(new InboundHandlerTuplePacketListener(packetIn, (GenericFutureListener[])((GenericFutureListener[])ArrayUtils.add(listeners, 0, listener))));
         } finally {
            this.field_181680_j.writeLock().unlock();
         }
      }

   }

   public void setNetHandler(INetHandler handler) {
      Validate.notNull(handler, "packetListener", new Object[0]);
      logger.debug("Set listener of {} to {}", new Object[]{this, handler});
      this.packetListener = handler;
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

   public SocketAddress getRemoteAddress() {
      return this.socketAddress;
   }

   public void channelActive(ChannelHandlerContext p_channelActive_1_) throws Exception {
      super.channelActive(p_channelActive_1_);
      this.channel = p_channelActive_1_.channel();
      this.socketAddress = this.channel.remoteAddress();

      try {
         this.setConnectionState(EnumConnectionState.HANDSHAKING);
      } catch (Throwable var3) {
         logger.fatal(var3);
      }

   }

   protected void channelRead0(ChannelHandlerContext p_channelRead0_1_, Packet p_channelRead0_2_) throws Exception {
      if(this.channel.isOpen()) {
         try {
            if(!((EventPacketRecv)(new EventPacketRecv(p_channelRead0_2_)).fire()).isCanceled()) {
               p_channelRead0_2_.processPacket(this.packetListener);
            }
         } catch (ThreadQuickExitException var4) {
            ;
         }
      }

   }

   public void channelInactive(ChannelHandlerContext p_channelInactive_1_) throws Exception {
      this.closeChannel(new ChatComponentTranslation("disconnect.endOfStream", new Object[0]));
   }

   public boolean isLocalChannel() {
      return this.channel instanceof LocalChannel || this.channel instanceof LocalServerChannel;
   }

   public boolean getIsencrypted() {
      return this.isEncrypted;
   }

   public boolean hasNoChannel() {
      return this.channel == null;
   }

   public static NetworkManager func_181124_a(InetAddress p_181124_0_, int p_181124_1_, boolean p_181124_2_) {
      NetworkManager networkmanager = new NetworkManager(EnumPacketDirection.CLIENTBOUND);
      Class<? extends SocketChannel> oclass;
      LazyLoadBase<? extends EventLoopGroup> lazyloadbase;
      if(Epoll.isAvailable() && p_181124_2_) {
         oclass = EpollSocketChannel.class;
         lazyloadbase = field_181125_e;
      } else {
         oclass = NioSocketChannel.class;
         lazyloadbase = CLIENT_NIO_EVENTLOOP;
      }

      ((Bootstrap)((Bootstrap)((Bootstrap)(new Bootstrap()).group((EventLoopGroup)lazyloadbase.getValue())).handler(new 5(networkmanager))).channel(oclass)).connect(p_181124_0_, p_181124_1_).syncUninterruptibly();
      return networkmanager;
   }

   public IChatComponent getExitMessage() {
      return this.terminationReason;
   }

   public void disableAutoRead() {
      this.channel.config().setAutoRead(false);
   }

   public void exceptionCaught(ChannelHandlerContext p_exceptionCaught_1_, Throwable p_exceptionCaught_2_) throws Exception {
      ChatComponentTranslation chatcomponenttranslation;
      if(p_exceptionCaught_2_ instanceof TimeoutException) {
         chatcomponenttranslation = new ChatComponentTranslation("disconnect.timeout", new Object[0]);
      } else {
         chatcomponenttranslation = new ChatComponentTranslation("disconnect.genericReason", new Object[]{"Internal Exception: " + p_exceptionCaught_2_});
      }

      this.closeChannel(chatcomponenttranslation);
   }

   private void dispatchPacket(Packet inPacket, GenericFutureListener[] futureListeners) {
      EnumConnectionState enumconnectionstate = EnumConnectionState.getFromPacket(inPacket);
      EnumConnectionState enumconnectionstate1 = (EnumConnectionState)this.channel.attr(attrKeyConnectionState).get();
      if(enumconnectionstate1 != enumconnectionstate) {
         logger.debug("Disabled auto read");
         this.channel.config().setAutoRead(false);
      }

      if(this.channel.eventLoop().inEventLoop()) {
         if(enumconnectionstate != enumconnectionstate1) {
            this.setConnectionState(enumconnectionstate);
         }

         ChannelFuture channelfuture = this.channel.writeAndFlush(inPacket);
         if(futureListeners != null) {
            channelfuture.addListeners(futureListeners);
         }

         channelfuture.addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
      } else {
         this.channel.eventLoop().execute(new 4(this, enumconnectionstate, enumconnectionstate1, inPacket, futureListeners));
      }

   }

   public void enableEncryption(SecretKey key) {
      this.isEncrypted = true;
      this.channel.pipeline().addBefore("splitter", "decrypt", new NettyEncryptingDecoder(CryptManager.createNetCipherInstance(2, key)));
      this.channel.pipeline().addBefore("prepender", "encrypt", new NettyEncryptingEncoder(CryptManager.createNetCipherInstance(1, key)));
   }

   public void setCompressionTreshold(int treshold) {
      if(treshold >= 0) {
         if(this.channel.pipeline().get("decompress") instanceof NettyCompressionDecoder) {
            ((NettyCompressionDecoder)this.channel.pipeline().get("decompress")).setCompressionTreshold(treshold);
         } else {
            this.channel.pipeline().addBefore("decoder", "decompress", new NettyCompressionDecoder(treshold));
         }

         if(this.channel.pipeline().get("compress") instanceof NettyCompressionEncoder) {
            ((NettyCompressionEncoder)this.channel.pipeline().get("decompress")).setCompressionTreshold(treshold);
         } else {
            this.channel.pipeline().addBefore("encoder", "compress", new NettyCompressionEncoder(treshold));
         }
      } else {
         if(this.channel.pipeline().get("decompress") instanceof NettyCompressionDecoder) {
            this.channel.pipeline().remove("decompress");
         }

         if(this.channel.pipeline().get("compress") instanceof NettyCompressionEncoder) {
            this.channel.pipeline().remove("compress");
         }
      }

   }

   public void setConnectionState(EnumConnectionState newState) {
      this.channel.attr(attrKeyConnectionState).set(newState);
      this.channel.config().setAutoRead(true);
      logger.debug("Enabled auto read");
   }

   private void flushOutboundQueue() {
      if(this.channel != null && this.channel.isOpen()) {
         this.field_181680_j.readLock().lock();

         try {
            while(!this.outboundPacketsQueue.isEmpty()) {
               InboundHandlerTuplePacketListener networkmanager$inboundhandlertuplepacketlistener = (InboundHandlerTuplePacketListener)this.outboundPacketsQueue.poll();
               this.dispatchPacket(InboundHandlerTuplePacketListener.access$100(networkmanager$inboundhandlertuplepacketlistener), InboundHandlerTuplePacketListener.access$200(networkmanager$inboundhandlertuplepacketlistener));
            }
         } finally {
            this.field_181680_j.readLock().unlock();
         }
      }

   }
}
