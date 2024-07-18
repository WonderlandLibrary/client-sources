package rip.vantage.commons.packet.impl.client.community;

import org.json.JSONObject;
import rip.vantage.commons.handler.api.IClientPacketHandler;
import rip.vantage.commons.packet.api.abstracts.ClientPacket;

public class C2SPacketIRCMessage extends ClientPacket {

    private final String message;
    private static final String productRise = "63d0f9bc46ca6bf7ad9572b7";
    public C2SPacketIRCMessage(String message) {
        super((byte) 4);
        this.message = message;
    }

    public C2SPacketIRCMessage(JSONObject json) {
        super((byte) 4);
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
        json.put("b", this.productRise);
        json.put("id", this.getId());
        return json.toString();
    }

    public String getMessage() {
        return message;
    }
}
