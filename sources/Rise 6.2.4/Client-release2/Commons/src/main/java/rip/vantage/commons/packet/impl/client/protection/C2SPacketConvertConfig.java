package rip.vantage.commons.packet.impl.client.protection;

import org.json.JSONObject;
import rip.vantage.commons.handler.api.IClientPacketHandler;
import rip.vantage.commons.packet.api.abstracts.ClientPacket;

public class C2SPacketConvertConfig extends ClientPacket {

    private final String config;

    public C2SPacketConvertConfig(String config) {
        super((byte) 2);
        this.config = config;
    }

    public C2SPacketConvertConfig(JSONObject json) {
        super((byte) 2);
        this.config = json.getString("a");
    }

    @Override
    public void handle(IClientPacketHandler handler) {
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
