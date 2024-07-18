package rip.vantage.commons.packet.impl.server.protection;

import org.json.JSONObject;
import rip.vantage.commons.handler.api.IServerPacketHandler;
import rip.vantage.commons.packet.api.abstracts.ServerPacket;

public class S2CPacketAuthenticationFinish extends ServerPacket {

    private final boolean success;
    private double b;
    private float c;
    private long d;
    private String e;

    public S2CPacketAuthenticationFinish(boolean success) {
        super((byte) 1);
        this.success = success;

    }

    public S2CPacketAuthenticationFinish(JSONObject json) {
        super((byte) 1);
        this.success = json.getBoolean("a");
        this.b = json.getDouble("b");
        this.c = json.getFloat("c");
        this.d = json.getLong("d");
        this.e = json.getString("e");
    }

    @Override
    public void handle(IServerPacketHandler handler) {
        handler.handle(this);
    }

    @Override
    public String export() {
        JSONObject json = new JSONObject();
        json.put("a", this.success);
        json.put("b", Math.PI);
        json.put("c", 90.0F);
        json.put("d", System.currentTimeMillis());
        json.put("e", this.e);
        json.put("id", this.getId());
        return json.toString();
    }

    public boolean isSuccess() {
        return success;
    }

    public double getB() {
        return b;
    }

    public float getC() {
        return c;
    }

    public long getD() {
        return d;
    }
    public String getE() {
        return this.e;
    }
}
