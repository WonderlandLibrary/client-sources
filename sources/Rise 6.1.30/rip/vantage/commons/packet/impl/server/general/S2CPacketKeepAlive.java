package rip.vantage.commons.packet.impl.server.general;

import org.json.JSONObject;
import rip.vantage.commons.handler.api.IServerPacketHandler;
import rip.vantage.commons.packet.api.abstracts.ServerPacket;

public class S2CPacketKeepAlive extends ServerPacket {
    long a;

    public S2CPacketKeepAlive() {
        super((byte) 0);
        a = System.currentTimeMillis();
    }

    public S2CPacketKeepAlive(JSONObject json) {
        super((byte) 0);
        this.a = json.getLong("a");
    }

    @Override
    public void handle(IServerPacketHandler handler) {
    }

    @Override
    public String export() {
        JSONObject json = new JSONObject();
        json.put("id", this.getId());
        json.put("a", a);
        return json.toString();
    }

    public long getA() {
        return a;
    }
}
