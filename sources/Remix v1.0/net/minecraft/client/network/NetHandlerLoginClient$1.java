package net.minecraft.client.network;

import javax.crypto.*;
import io.netty.util.concurrent.*;

class NetHandlerLoginClient$1 implements GenericFutureListener {
    final /* synthetic */ SecretKey val$var2;
    
    public void operationComplete(final Future p_operationComplete_1_) {
        NetHandlerLoginClient.access$000(NetHandlerLoginClient.this).enableEncryption(this.val$var2);
    }
}