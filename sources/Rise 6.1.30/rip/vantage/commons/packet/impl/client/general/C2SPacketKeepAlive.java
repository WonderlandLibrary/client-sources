package rip.vantage.commons.packet.impl.client.general;

import org.json.JSONObject;
import rip.vantage.commons.handler.api.IClientPacketHandler;
import rip.vantage.commons.packet.api.abstracts.ClientPacket;

public class C2SPacketKeepAlive extends ClientPacket {

    public C2SPacketKeepAlive() {
        super((byte) 0);
    }

    @Override
    public void handle(IClientPacketHandler handler) {
        handler.handle(this);
    }

    @Override
    public String export() {
        JSONObject json = new JSONObject();
        json.put("id", this.getId());
        return json.toString();
    }
}
