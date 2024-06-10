package net.minecraft.network.status;

import net.minecraft.network.INetHandler;
import net.minecraft.network.status.server.S00PacketServerInfo;
import net.minecraft.network.status.server.S01PacketPong;

public abstract interface INetHandlerStatusClient
  extends INetHandler
{
  public abstract void handleServerInfo(S00PacketServerInfo paramS00PacketServerInfo);
  
  public abstract void handlePong(S01PacketPong paramS01PacketPong);
}


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.network.status.INetHandlerStatusClient
 * JD-Core Version:    0.7.0.1
 */