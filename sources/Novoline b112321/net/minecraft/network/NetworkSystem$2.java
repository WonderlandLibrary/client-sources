package net.minecraft.network;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.netty.channel.epoll.EpollEventLoopGroup;
import net.minecraft.util.LazyLoadBase;

final class NetworkSystem$2 extends LazyLoadBase {
   protected EpollEventLoopGroup load() {
      return new EpollEventLoopGroup(0, (new ThreadFactoryBuilder()).setNameFormat("Netty Epoll Server IO #%d").setDaemon(true).build());
   }
}
