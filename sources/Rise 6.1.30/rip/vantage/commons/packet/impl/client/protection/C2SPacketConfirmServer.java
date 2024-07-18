package rip.vantage.commons.packet.impl.client.protection;

import org.json.JSONObject;
import rip.vantage.commons.handler.api.IClientPacketHandler;
import rip.vantage.commons.packet.api.abstracts.ClientPacket;

public class C2SPacketConfirmServer extends ClientPacket {

    private final String ip;
    private final int port;
    private final String username;

    public C2SPacketConfirmServer(String ip, int port, String username) {
        super((byte) 3);
        this.ip = ip;
        this.port = port;
        this.username = username;
    }

    public C2SPacketConfirmServer(JSONObject json) {
        super((byte) 3);
        this.ip = json.getString("a");
        this.port = json.getInt("b");
        this.username = json.getString("c");
    }

    @Override
    public void handle(IClientPacketHandler handler) {
        handler.handle(this);
    }

    @Override
    public String export() {
        JSONObject json = new JSONObject();
        json.put("a", this.ip);
        json.put("b", this.port);
        json.put("c", this.username);
        json.put("id", this.getId());
        return json.toString();
    }

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }
}
