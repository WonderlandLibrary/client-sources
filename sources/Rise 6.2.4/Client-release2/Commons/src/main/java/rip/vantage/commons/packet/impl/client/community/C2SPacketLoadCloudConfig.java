package rip.vantage.commons.packet.impl.client.community;

import org.json.JSONObject;
import rip.vantage.commons.handler.api.IClientPacketHandler;
import rip.vantage.commons.packet.api.abstracts.ClientPacket;

public class C2SPacketLoadCloudConfig extends ClientPacket {

    private final String configID;

    public C2SPacketLoadCloudConfig(String configID) {
        super((byte) 10);
        this.configID = configID;
    }

    public C2SPacketLoadCloudConfig(JSONObject json) {
        super((byte) 10);
        this.configID = json.getString("a");
    }

    @Override
    public void handle(IClientPacketHandler handler) {
        handler.handle(this);
    }

    @Override
    public String export() {
        JSONObject json = new JSONObject();
        json.put("a", this.configID);
        json.put("id", this.getId());
        return json.toString();
    }

    public String getConfigID() {
        return configID;
    }
}
