package net.minecraft.network;

import io.netty.util.concurrent.*;
import net.minecraft.util.*;

class NetworkSystem$6 implements GenericFutureListener {
    final /* synthetic */ NetworkManager val$var3;
    final /* synthetic */ ChatComponentText val$var5;
    
    public void operationComplete(final Future p_operationComplete_1_) {
        this.val$var3.closeChannel(this.val$var5);
    }
}