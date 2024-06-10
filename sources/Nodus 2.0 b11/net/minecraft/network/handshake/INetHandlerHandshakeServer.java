package net.minecraft.network.handshake;

import net.minecraft.network.INetHandler;
import net.minecraft.network.handshake.client.C00Handshake;

public abstract interface INetHandlerHandshakeServer
  extends INetHandler
{
  public abstract void processHandshake(C00Handshake paramC00Handshake);
}


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.network.handshake.INetHandlerHandshakeServer
 * JD-Core Version:    0.7.0.1
 */