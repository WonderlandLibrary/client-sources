package net.minecraft.network;

import io.netty.util.concurrent.*;
import net.minecraft.util.*;

class NetHandlerPlayServer$1 implements GenericFutureListener {
    final /* synthetic */ ChatComponentText val$var2;
    
    public void operationComplete(final Future p_operationComplete_1_) {
        NetHandlerPlayServer.this.netManager.closeChannel(this.val$var2);
    }
}