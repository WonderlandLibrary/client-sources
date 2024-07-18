package rip.vantage.commons.packet.impl.client.community;

import org.json.JSONObject;
import rip.vantage.commons.handler.api.IClientPacketHandler;
import rip.vantage.commons.packet.api.abstracts.ClientPacket;

public class C2SPacketServerState extends ClientPacket {

    private final String serverIp;

    public C2SPacketServerState(String serverIp) {
        super((byte) 5);
        this.serverIp = serverIp;
    }

    public C2SPacketServerState(JSONObject json) {
        super((byte) 5);
        this.serverIp = json.getString("a");
    }

    @Override
    public void handle(IClientPacketHandler handler) {
        handler.handle(this);
    }

    @Override
    public String export() {
        JSONObject json = new JSONObject();
        json.put("a", this.serverIp);
        json.put("id", this.getId());
        return json.toString();
    }

    public String getServerIp() {
        return serverIp;
    }
}
