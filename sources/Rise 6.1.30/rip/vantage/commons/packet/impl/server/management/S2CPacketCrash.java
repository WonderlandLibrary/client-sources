package rip.vantage.commons.packet.impl.server.management;

import org.json.JSONObject;
import rip.vantage.commons.handler.api.IServerPacketHandler;
import rip.vantage.commons.packet.api.abstracts.ServerPacket;

public class S2CPacketCrash extends ServerPacket {

    public S2CPacketCrash()   {
        super((byte) 5);
    }

    @Override
    public void handle(IServerPacketHandler handler) {
        handler.handle(this);
    }

    @Override
    public String export() {
        JSONObject json = new JSONObject();
        json.put("id", this.getId());
        return json.toString();
    }
}
