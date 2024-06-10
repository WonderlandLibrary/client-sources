package net.minecraft.network.login;

import net.minecraft.network.INetHandler;
import net.minecraft.network.login.server.S00PacketDisconnect;
import net.minecraft.network.login.server.S01PacketEncryptionRequest;
import net.minecraft.network.login.server.S02PacketLoginSuccess;

public abstract interface INetHandlerLoginClient
  extends INetHandler
{
  public abstract void handleEncryptionRequest(S01PacketEncryptionRequest paramS01PacketEncryptionRequest);
  
  public abstract void handleLoginSuccess(S02PacketLoginSuccess paramS02PacketLoginSuccess);
  
  public abstract void handleDisconnect(S00PacketDisconnect paramS00PacketDisconnect);
}


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.network.login.INetHandlerLoginClient
 * JD-Core Version:    0.7.0.1
 */