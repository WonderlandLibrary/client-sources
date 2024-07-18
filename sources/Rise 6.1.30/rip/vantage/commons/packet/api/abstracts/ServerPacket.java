package rip.vantage.commons.packet.api.abstracts;

import rip.vantage.commons.packet.api.interfaces.IServerPacket;

public abstract class ServerPacket implements IServerPacket {

    private final byte id;

    public ServerPacket(byte id) {
        this.id = id;
    }

    public byte getId() {
        return id;
    }
}
