package net.minecraft.network;

import net.minecraft.util.*;
import io.netty.channel.local.*;
import com.google.common.util.concurrent.*;

static final class NetworkManager$2 extends LazyLoadBase {
    protected LocalEventLoopGroup genericLoad() {
        return new LocalEventLoopGroup(0, new ThreadFactoryBuilder().setNameFormat("Netty Local Client IO #%d").setDaemon(true).build());
    }
    
    @Override
    protected Object load() {
        return this.genericLoad();
    }
}