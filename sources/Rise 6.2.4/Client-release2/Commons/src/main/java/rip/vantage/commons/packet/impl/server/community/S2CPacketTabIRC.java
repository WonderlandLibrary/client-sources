package rip.vantage.commons.packet.impl.server.community;

import org.json.JSONObject;
import rip.vantage.commons.handler.api.IServerPacketHandler;
import rip.vantage.commons.packet.api.abstracts.ServerPacket;

public class S2CPacketTabIRC extends ServerPacket {
    private final String message;

    public S2CPacketTabIRC(String message) {
        super((byte) 6);
        this.message = message;
    }

    public S2CPacketTabIRC(JSONObject json) {
        super((byte) 6);
        this.message = json.getString("a");
    }

    @Override
    public void handle(IServerPacketHandler handler) {
        handler.handle(this);
    }

    @Override
    public String export() {
        JSONObject json = new JSONObject();
        json.put("a", this.message);
        json.put("id", this.getId());
        return json.toString();
    }

    public String getMessage() {
        return message;
    }
}
