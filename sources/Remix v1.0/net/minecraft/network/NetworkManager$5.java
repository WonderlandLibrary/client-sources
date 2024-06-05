package net.minecraft.network;

import io.netty.util.concurrent.*;
import io.netty.channel.*;

class NetworkManager$5 implements Runnable {
    final /* synthetic */ EnumConnectionState val$var3;
    final /* synthetic */ EnumConnectionState val$var4;
    final /* synthetic */ Packet val$inPacket;
    final /* synthetic */ GenericFutureListener[] val$futureListeners;
    
    @Override
    public void run() {
        if (this.val$var3 != this.val$var4) {
            NetworkManager.this.setConnectionState(this.val$var3);
        }
        final ChannelFuture var1 = NetworkManager.access$000(NetworkManager.this).writeAndFlush((Object)this.val$inPacket);
        if (this.val$futureListeners != null) {
            var1.addListeners(this.val$futureListeners);
        }
        var1.addListener((GenericFutureListener)ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
    }
}