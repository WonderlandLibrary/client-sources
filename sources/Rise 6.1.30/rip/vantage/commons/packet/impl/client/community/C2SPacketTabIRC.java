package rip.vantage.commons.packet.impl.client.community;

import org.json.JSONObject;
import rip.vantage.commons.handler.api.IClientPacketHandler;
import rip.vantage.commons.packet.api.abstracts.ClientPacket;

public class C2SPacketTabIRC extends ClientPacket {

    private final String message;
    public C2SPacketTabIRC(String message) {
        super((byte) 6);
        this.message = message;
    }
    public C2SPacketTabIRC(JSONObject json) {
        super((byte) 6);
        this.message = json.getString("a");
    }

    @Override
    public void handle(IClientPacketHandler handler) {
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
