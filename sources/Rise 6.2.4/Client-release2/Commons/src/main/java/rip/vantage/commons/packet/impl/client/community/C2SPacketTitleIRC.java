package rip.vantage.commons.packet.impl.client.community;

import org.json.JSONObject;
import rip.vantage.commons.handler.api.IClientPacketHandler;
import rip.vantage.commons.packet.api.abstracts.ClientPacket;

public class C2SPacketTitleIRC extends ClientPacket {

    private final String message;
    private final int b;
    private final int c;
    private final int d;
    private final String e;
    private final String f;

    public C2SPacketTitleIRC(String message, int b, int c, int d, String e, String f) {
        super((byte) 9);
        this.message = message;
        this.b = b;
        this.c = c;
        this.d = d;
        this.e = e;
        this.f = f;
    }
    public C2SPacketTitleIRC(JSONObject json) {
        super((byte) 9);
        this.message = json.getString("a");
        this.b = json.getInt("b");
        this.c = json.getInt("c");
        this.d = json.getInt("d");
        this.e = json.getString("e");
        this.f = json.getString("f");
    }

    @Override
    public void handle(IClientPacketHandler handler) {
        handler.handle(this);
    }

    @Override
    public String export() {
        JSONObject json = new JSONObject();
        json.put("a", this.message);
        json.put("b", this.b);
        json.put("c", this.c);
        json.put("d", this.d);
        json.put("e", this.e);
        json.put("f", this.f);
        json.put("id", this.getId());
        return json.toString();
    }

    public String getMessage() {
        return message;
    }
}
