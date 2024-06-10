package net.minecraft.network;

import net.minecraft.util.IChatComponent;

public abstract interface INetHandler
{
  public abstract void onDisconnect(IChatComponent paramIChatComponent);
  
  public abstract void onConnectionStateTransition(EnumConnectionState paramEnumConnectionState1, EnumConnectionState paramEnumConnectionState2);
  
  public abstract void onNetworkTick();
}


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.network.INetHandler
 * JD-Core Version:    0.7.0.1
 */