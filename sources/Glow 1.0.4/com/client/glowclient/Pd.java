package com.client.glowclient;

import io.netty.buffer.*;
import net.minecraftforge.fml.common.network.simpleimpl.*;

public class pd implements IMessage, IMessageHandler<pd, IMessage>
{
    public boolean A;
    public boolean B;
    public boolean b;
    
    public pd() {
        final boolean b = false;
        this(b, b, b);
    }
    
    public void fromBytes(final ByteBuf byteBuf) {
        this.A = byteBuf.readBoolean();
        this.B = byteBuf.readBoolean();
        this.b = byteBuf.readBoolean();
    }
    
    public IMessage onMessage(final IMessage message, final MessageContext messageContext) {
        return this.onMessage((pd)message, messageContext);
    }
    
    public IMessage onMessage(final pd pd, final MessageContext messageContext) {
        wA.b.M(pd.A);
        kB.b.B = pd.B;
        kB.b.b = pd.b;
        ld.H.info("Server capabilities{printer={}, save={}, load={}}", (Object)pd.A, (Object)pd.B, (Object)pd.b);
        return null;
    }
    
    public pd(final boolean a, final boolean b, final boolean b2) {
        super();
        this.A = a;
        this.B = b;
        this.b = b2;
    }
    
    public void toBytes(final ByteBuf byteBuf) {
        byteBuf.writeBoolean(this.A);
        byteBuf.writeBoolean(this.B);
        byteBuf.writeBoolean(this.b);
    }
}
