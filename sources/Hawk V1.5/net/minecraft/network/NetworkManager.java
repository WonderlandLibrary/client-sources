package net.minecraft.network;

import com.google.common.collect.Queues;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import hawk.Client;
import hawk.events.EventDirection;
import hawk.events.listeners.EventPacket;
import hawk.events.listeners.EventReceivePacket;
import hawk.events.listeners.EventSendPacket;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.local.LocalChannel;
import io.netty.channel.local.LocalEventLoopGroup;
import io.netty.channel.local.LocalServerChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.GenericFutureListener;
import java.net.InetAddress;
import java.net.SocketAddress;
import java.util.Queue;
import javax.crypto.SecretKey;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.CryptManager;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.LazyLoadBase;
import net.minecraft.util.MessageDeserializer;
import net.minecraft.util.MessageDeserializer2;
import net.minecraft.util.MessageSerializer;
import net.minecraft.util.MessageSerializer2;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

public class NetworkManager extends SimpleChannelInboundHandler {
   private static final String __OBFID = "CL_00001240";
   public static final Marker logMarkerNetwork = MarkerManager.getMarker("NETWORK");
   private final EnumPacketDirection direction;
   public static final LazyLoadBase CLIENT_LOCAL_EVENTLOOP;
   private static final Logger logger = LogManager.getLogger();
   public static final Marker logMarkerPackets;
   public static final LazyLoadBase CLIENT_NIO_EVENTLOOP;
   private IChatComponent terminationReason;
   public static final AttributeKey attrKeyConnectionState;
   private Channel channel;
   private INetHandler packetListener;
   private SocketAddress socketAddress;
   private final Queue outboundPacketsQueue = Queues.newConcurrentLinkedQueue();
   private boolean disconnected;
   private boolean isEncrypted;

   public void exceptionCaught(ChannelHandlerContext var1, Throwable var2) {
      logger.debug(String.valueOf((new StringBuilder("Disconnecting ")).append(this.getRemoteAddress())), var2);
      this.closeChannel(new ChatComponentTranslation("disconnect.genericReason", new Object[]{String.valueOf((new StringBuilder("Internal Exception: ")).append(var2))}));
   }

   protected void channelRead0(ChannelHandlerContext var1, Object var2) {
      EventReceivePacket var3 = new EventReceivePacket((Packet)var2);
      Client.onEvent(var3);
      if (!var3.isCancelled()) {
         this.channelRead0(var1, (Packet)var2);
      }
   }

   public NetworkManager(EnumPacketDirection var1) {
      this.direction = var1;
   }

   public void setNetHandler(INetHandler var1) {
      Validate.notNull(var1, "packetListener", new Object[0]);
      logger.debug("Set listener of {} to {}", new Object[]{this, var1});
      this.packetListener = var1;
   }

   protected void channelRead0(ChannelHandlerContext var1, Packet var2) {
      EventPacket var3 = new EventPacket(var2);
      var3.setDirection(EventDirection.INCOMING);
      Client.onEvent(var3);
      if (this.channel.isOpen()) {
         try {
            if (!var3.isCancelled()) {
               var2.processPacket(this.packetListener);
            }
         } catch (ThreadQuickExitException var5) {
         }
      }

   }

   public boolean func_179292_f() {
      return this.isEncrypted;
   }

   public void setCompressionTreshold(int var1) {
      if (var1 >= 0) {
         if (this.channel.pipeline().get("decompress") instanceof NettyCompressionDecoder) {
            ((NettyCompressionDecoder)this.channel.pipeline().get("decompress")).setCompressionTreshold(var1);
         } else {
            this.channel.pipeline().addBefore("decoder", "decompress", new NettyCompressionDecoder(var1));
         }

         if (this.channel.pipeline().get("compress") instanceof NettyCompressionEncoder) {
            ((NettyCompressionEncoder)this.channel.pipeline().get("decompress")).setCompressionTreshold(var1);
         } else {
            this.channel.pipeline().addBefore("encoder", "compress", new NettyCompressionEncoder(var1));
         }
      } else {
         if (this.channel.pipeline().get("decompress") instanceof NettyCompressionDecoder) {
            this.channel.pipeline().remove("decompress");
         }

         if (this.channel.pipeline().get("compress") instanceof NettyCompressionEncoder) {
            this.channel.pipeline().remove("compress");
         }
      }

   }

   public boolean isChannelOpen() {
      return this.channel != null && this.channel.isOpen();
   }

   public SocketAddress getRemoteAddress() {
      return this.socketAddress;
   }

   public static NetworkManager provideLocalClient(SocketAddress var0) {
      NetworkManager var1 = new NetworkManager(EnumPacketDirection.CLIENTBOUND);
      ((Bootstrap)((Bootstrap)((Bootstrap)(new Bootstrap()).group((EventLoopGroup)CLIENT_LOCAL_EVENTLOOP.getValue())).handler(new ChannelInitializer(var1) {
         private final NetworkManager val$var1;
         private static final String __OBFID = "CL_00002311";

         protected void initChannel(Channel var1) {
            var1.pipeline().addLast("packet_handler", this.val$var1);
         }

         {
            this.val$var1 = var1;
         }
      })).channel(LocalChannel.class)).connect(var0).syncUninterruptibly();
      return var1;
   }

   public void setConnectionState(EnumConnectionState var1) {
      this.channel.attr(attrKeyConnectionState).set(var1);
      this.channel.config().setAutoRead(true);
      logger.debug("Enabled auto read");
   }

   public void channelInactive(ChannelHandlerContext var1) {
      this.closeChannel(new ChatComponentTranslation("disconnect.endOfStream", new Object[0]));
   }

   private void flushOutboundQueue() {
      if (this.channel != null && this.channel.isOpen()) {
         while(!this.outboundPacketsQueue.isEmpty()) {
            NetworkManager.InboundHandlerTuplePacketListener var1 = (NetworkManager.InboundHandlerTuplePacketListener)this.outboundPacketsQueue.poll();
            this.dispatchPacket(NetworkManager.InboundHandlerTuplePacketListener.access$0(var1), NetworkManager.InboundHandlerTuplePacketListener.access$1(var1));
         }
      }

   }

   public void checkDisconnected() {
      if (!this.hasNoChannel() && !this.isChannelOpen() && !this.disconnected) {
         this.disconnected = true;
         if (this.getExitMessage() != null) {
            this.getNetHandler().onDisconnect(this.getExitMessage());
         } else if (this.getNetHandler() != null) {
            this.getNetHandler().onDisconnect(new ChatComponentText("Disconnected"));
         }
      }

   }

   public void sendPacket(Packet var1, GenericFutureListener var2, GenericFutureListener... var3) {
      if (this.channel != null && this.channel.isOpen()) {
         this.flushOutboundQueue();
         this.dispatchPacket(var1, (GenericFutureListener[])ArrayUtils.add(var3, 0, var2));
      } else {
         this.outboundPacketsQueue.add(new NetworkManager.InboundHandlerTuplePacketListener(var1, (GenericFutureListener[])ArrayUtils.add(var3, 0, var2)));
      }

   }

   public IChatComponent getExitMessage() {
      return this.terminationReason;
   }

   public void sendPacket(Packet var1) {
      EventSendPacket var2 = new EventSendPacket(var1);
      Client.onEvent(var2);
      if (!var2.isCancelled()) {
         if (this.channel != null && this.channel.isOpen()) {
            this.flushOutboundQueue();
            if (!var2.isCancelled()) {
               this.dispatchPacket(var1, (GenericFutureListener[])null);
            }
         } else {
            this.outboundPacketsQueue.add(new NetworkManager.InboundHandlerTuplePacketListener(var1, (GenericFutureListener[])null));
         }

      }
   }

   static Channel access$0(NetworkManager var0) {
      return var0.channel;
   }

   public boolean hasNoChannel() {
      return this.channel == null;
   }

   public static NetworkManager provideLanClient(InetAddress var0, int var1) {
      NetworkManager var2 = new NetworkManager(EnumPacketDirection.CLIENTBOUND);
      ((Bootstrap)((Bootstrap)((Bootstrap)(new Bootstrap()).group((EventLoopGroup)CLIENT_NIO_EVENTLOOP.getValue())).handler(new ChannelInitializer(var2) {
         private static final String __OBFID = "CL_00002312";
         private final NetworkManager val$var2;

         protected void initChannel(Channel var1) {
            try {
               var1.config().setOption(ChannelOption.IP_TOS, 24);
            } catch (ChannelException var4) {
            }

            try {
               var1.config().setOption(ChannelOption.TCP_NODELAY, false);
            } catch (ChannelException var3) {
            }

            var1.pipeline().addLast("timeout", new ReadTimeoutHandler(20)).addLast("splitter", new MessageDeserializer2()).addLast("decoder", new MessageDeserializer(EnumPacketDirection.CLIENTBOUND)).addLast("prepender", new MessageSerializer2()).addLast("encoder", new MessageSerializer(EnumPacketDirection.SERVERBOUND)).addLast("packet_handler", this.val$var2);
         }

         {
            this.val$var2 = var1;
         }
      })).channel(NioSocketChannel.class)).connect(var0, var1).syncUninterruptibly();
      return var2;
   }

   public INetHandler getNetHandler() {
      return this.packetListener;
   }

   private void dispatchPacket(Packet var1, GenericFutureListener[] var2) {
      EnumConnectionState var3 = EnumConnectionState.getFromPacket(var1);
      EnumConnectionState var4 = (EnumConnectionState)this.channel.attr(attrKeyConnectionState).get();
      if (var4 != var3) {
         logger.debug("Disabled auto read");
         this.channel.config().setAutoRead(false);
      }

      if (this.channel.eventLoop().inEventLoop()) {
         if (var3 != var4) {
            this.setConnectionState(var3);
         }

         ChannelFuture var5 = this.channel.writeAndFlush(var1);
         if (var2 != null) {
            var5.addListeners(var2);
         }

         var5.addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
      } else {
         this.channel.eventLoop().execute(new Runnable(this, var3, var4, var1, var2) {
            private final Packet val$inPacket;
            private final GenericFutureListener[] val$futureListeners;
            private static final String __OBFID = "CL_00001243";
            private final EnumConnectionState val$var4;
            final NetworkManager this$0;
            private final EnumConnectionState val$var3;

            {
               this.this$0 = var1;
               this.val$var3 = var2;
               this.val$var4 = var3;
               this.val$inPacket = var4;
               this.val$futureListeners = var5;
            }

            public void run() {
               if (this.val$var3 != this.val$var4) {
                  this.this$0.setConnectionState(this.val$var3);
               }

               ChannelFuture var1 = NetworkManager.access$0(this.this$0).writeAndFlush(this.val$inPacket);
               if (this.val$futureListeners != null) {
                  var1.addListeners(this.val$futureListeners);
               }

               var1.addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
            }
         });
      }

   }

   public void enableEncryption(SecretKey var1) {
      this.isEncrypted = true;
      this.channel.pipeline().addBefore("splitter", "decrypt", new NettyEncryptingDecoder(CryptManager.func_151229_a(2, var1)));
      this.channel.pipeline().addBefore("prepender", "encrypt", new NettyEncryptingEncoder(CryptManager.func_151229_a(1, var1)));
   }

   public void closeChannel(IChatComponent var1) {
      if (this.channel.isOpen()) {
         this.channel.close().awaitUninterruptibly();
         this.terminationReason = var1;
      }

   }

   public void processReceivedPackets() {
      this.flushOutboundQueue();
      if (this.packetListener instanceof IUpdatePlayerListBox) {
         ((IUpdatePlayerListBox)this.packetListener).update();
      }

      this.channel.flush();
   }

   public void channelActive(ChannelHandlerContext var1) throws Exception {
      super.channelActive(var1);
      this.channel = var1.channel();
      this.socketAddress = this.channel.remoteAddress();

      try {
         this.setConnectionState(EnumConnectionState.HANDSHAKING);
      } catch (Throwable var3) {
         logger.fatal(var3);
      }

   }

   static {
      logMarkerPackets = MarkerManager.getMarker("NETWORK_PACKETS", logMarkerNetwork);
      attrKeyConnectionState = AttributeKey.valueOf("protocol");
      CLIENT_NIO_EVENTLOOP = new LazyLoadBase() {
         private static final String __OBFID = "CL_00001241";

         protected Object load() {
            return this.genericLoad();
         }

         protected NioEventLoopGroup genericLoad() {
            return new NioEventLoopGroup(0, (new ThreadFactoryBuilder()).setNameFormat("Netty Client IO #%d").setDaemon(true).build());
         }
      };
      CLIENT_LOCAL_EVENTLOOP = new LazyLoadBase() {
         private static final String __OBFID = "CL_00001242";

         protected Object load() {
            return this.genericLoad();
         }

         protected LocalEventLoopGroup genericLoad() {
            return new LocalEventLoopGroup(0, (new ThreadFactoryBuilder()).setNameFormat("Netty Local Client IO #%d").setDaemon(true).build());
         }
      };
   }

   public void disableAutoRead() {
      this.channel.config().setAutoRead(false);
   }

   public boolean isLocalChannel() {
      return this.channel instanceof LocalChannel || this.channel instanceof LocalServerChannel;
   }

   static class InboundHandlerTuplePacketListener {
      private static final String __OBFID = "CL_00001244";
      private final Packet packet;
      private final GenericFutureListener[] futureListeners;

      public InboundHandlerTuplePacketListener(Packet var1, GenericFutureListener... var2) {
         this.packet = var1;
         this.futureListeners = var2;
      }

      static Packet access$0(NetworkManager.InboundHandlerTuplePacketListener var0) {
         return var0.packet;
      }

      static GenericFutureListener[] access$1(NetworkManager.InboundHandlerTuplePacketListener var0) {
         return var0.futureListeners;
      }
   }
}
