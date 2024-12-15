package rip.vantage.commons.packet.impl.server.community;

import org.json.JSONObject;
import rip.vantage.commons.handler.api.IServerPacketHandler;
import rip.vantage.commons.packet.api.abstracts.ServerPacket;

public class S2CPacketTitleIRC extends ServerPacket {
    private final String message;
    private final int b;
    private final int c;
    private final int d;
    private final String e;

    public S2CPacketTitleIRC(String message, int b, int c, int d, String e) {
        super((byte) 9);
        this.message = message;
        this.b = b;
        this.c = c;
        this.d = d;
        this.e = e;
    }

    public S2CPacketTitleIRC(JSONObject json) {
        super((byte) 9);
        this.message = json.getString("a");
        this.b = json.getInt("b");
        this.c = json.getInt("c");
        this.d = json.getInt("d");
        this.e = json.getString("e");
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

    public int getFadeInTime() {
        return b;
    }
    public int getDisplayTime() {
        return c;
    }
    public int getFadeOutTime() {
        return d;
    }

    public String getColor() {
        return e;
    }
}
