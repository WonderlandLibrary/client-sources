package rip.vantage.commons.packet.api.interfaces;

import rip.vantage.commons.handler.api.IClientPacketHandler;

public interface IClientPacket extends IPacket<IClientPacketHandler> {

    @Override
    void handle(IClientPacketHandler handler);
}