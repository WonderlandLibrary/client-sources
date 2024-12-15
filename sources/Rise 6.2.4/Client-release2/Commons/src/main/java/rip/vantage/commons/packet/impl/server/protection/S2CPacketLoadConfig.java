package rip.vantage.commons.packet.impl.server.protection;

import org.json.JSONObject;
import rip.vantage.commons.handler.api.IServerPacketHandler;
import rip.vantage.commons.packet.api.abstracts.ServerPacket;

public class S2CPacketLoadConfig extends ServerPacket {

    private final String config;

    public S2CPacketLoadConfig(String config) {
        super((byte) 2);
        this.config = config;
    }

    public S2CPacketLoadConfig(JSONObject json) {
        super((byte) 2);
        this.config = json.getString("a");
    }

    @Override
    public void handle(IServerPacketHandler handler) {
        handler.handle(this);
    }

    @Override
    public String export() {
        JSONObject json = new JSONObject();
        json.put("a", this.config);
        json.put("id", this.getId());
        return json.toString();
    }

    public String getConfig() {
        return config;
    }
}
