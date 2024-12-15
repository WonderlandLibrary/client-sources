package rip.vantage.commons.packet.api.abstracts;

import rip.vantage.commons.packet.api.interfaces.IClientPacket;

public abstract class ClientPacket implements IClientPacket {

    private final byte id;

    public ClientPacket(byte id) {
        this.id = id;
    }

    public byte getId() {
        return id;
    }
}
