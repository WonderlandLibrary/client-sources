package rip.vantage.commons.packet.api.interfaces;

import rip.vantage.commons.handler.api.IServerPacketHandler;

public interface IServerPacket extends IPacket<IServerPacketHandler> {

    @Override
    void handle(IServerPacketHandler handler);
}
