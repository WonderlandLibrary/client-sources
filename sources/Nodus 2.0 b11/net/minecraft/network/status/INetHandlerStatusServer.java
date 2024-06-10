package net.minecraft.network.status;

import net.minecraft.network.INetHandler;
import net.minecraft.network.status.client.C00PacketServerQuery;
import net.minecraft.network.status.client.C01PacketPing;

public abstract interface INetHandlerStatusServer
  extends INetHandler
{
  public abstract void processPing(C01PacketPing paramC01PacketPing);
  
  public abstract void processServerQuery(C00PacketServerQuery paramC00PacketServerQuery);
}


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.network.status.INetHandlerStatusServer
 * JD-Core Version:    0.7.0.1
 */