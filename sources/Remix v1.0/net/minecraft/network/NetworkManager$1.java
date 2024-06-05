package net.minecraft.network;

import net.minecraft.util.*;
import io.netty.channel.nio.*;
import com.google.common.util.concurrent.*;

static final class NetworkManager$1 extends LazyLoadBase {
    protected NioEventLoopGroup genericLoad() {
        return new NioEventLoopGroup(0, new ThreadFactoryBuilder().setNameFormat("Netty Client IO #%d").setDaemon(true).build());
    }
    
    @Override
    protected Object load() {
        return this.genericLoad();
    }
}