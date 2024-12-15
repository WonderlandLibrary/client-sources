package rip.vantage.commons.packet.api.interfaces;

import rip.vantage.commons.handler.api.IHandler;

public interface IPacket<T extends IHandler> {

    void handle(T handler);

    String export();
}
