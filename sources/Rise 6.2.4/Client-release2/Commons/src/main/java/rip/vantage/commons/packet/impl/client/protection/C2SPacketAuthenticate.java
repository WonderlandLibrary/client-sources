package rip.vantage.commons.packet.impl.client.protection;

import org.json.JSONObject;
import rip.vantage.commons.handler.api.IClientPacketHandler;
import rip.vantage.commons.packet.api.abstracts.ClientPacket;

public class C2SPacketAuthenticate extends ClientPacket {

    private final String username;

    public String getHazeid() {
        return hazeid;
    }

    private final String hazeid;
    private final String hwid;
    private final int product;

    public C2SPacketAuthenticate(String username, String hwid, String hazeid, int product) {
        super((byte) 1);
        this.username = username;
        this.hwid = hwid;
        this.hazeid = hazeid;
        this.product = product;
    }

    public C2SPacketAuthenticate(JSONObject json) {
        super((byte) 1);
        this.username = json.getString("a");
        this.hwid = json.getString("b");
        this.product = json.getInt("c");
        this.hazeid = json.getString("d");
    }

    @Override
    public void handle(IClientPacketHandler handler) {
        handler.handle(this);
    }

    @Override
    public String export() {
        JSONObject json = new JSONObject();
        json.put("a", this.username);
        json.put("b", this.hwid);
        json.put("c", this.product);
        json.put("d", this.hazeid);
        json.put("id", this.getId());
        return json.toString();
    }

    public String getUsername() {
        return username;
    }

    public String getHwid() {
        return hwid;
    }

    public int getProduct() {
        return product;
    }
}
