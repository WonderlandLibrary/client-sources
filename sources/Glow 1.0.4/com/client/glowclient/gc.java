package com.client.glowclient;

import io.netty.buffer.*;
import net.minecraftforge.fml.common.network.simpleimpl.*;

public class GC implements IMessage, IMessageHandler<GC, IMessage>
{
    public GC() {
        super();
    }
    
    public void fromBytes(final ByteBuf byteBuf) {
    }
    
    public void toBytes(final ByteBuf byteBuf) {
    }
    
    public IMessage onMessage(final GC gc, final MessageContext messageContext) {
        final PC pc;
        if ((pc = QC.B.b.get(messageContext.getServerHandler().player)) != null) {
            pc.M(FA.G);
        }
        return null;
    }
    
    public IMessage onMessage(final IMessage message, final MessageContext messageContext) {
        return this.onMessage((GC)message, messageContext);
    }
}
