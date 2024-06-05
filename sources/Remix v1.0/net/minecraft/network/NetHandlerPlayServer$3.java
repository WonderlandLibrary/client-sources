package net.minecraft.network;

import java.util.concurrent.*;

class NetHandlerPlayServer$3 implements Callable {
    final /* synthetic */ Packet val$packetIn;
    
    public String func_180225_a() {
        return this.val$packetIn.getClass().getCanonicalName();
    }
    
    @Override
    public Object call() {
        return this.func_180225_a();
    }
}