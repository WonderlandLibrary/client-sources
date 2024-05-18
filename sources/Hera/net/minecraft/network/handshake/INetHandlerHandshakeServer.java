package net.minecraft.network.handshake;

import net.minecraft.network.INetHandler;
import net.minecraft.network.handshake.client.C00Handshake;

public interface INetHandlerHandshakeServer extends INetHandler {
  void processHandshake(C00Handshake paramC00Handshake);
}


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\network\handshake\INetHandlerHandshakeServer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */