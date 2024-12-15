package rip.vantage.commons.packet.impl.server.protection;

import org.json.JSONObject;
import rip.vantage.commons.handler.api.IServerPacketHandler;
import rip.vantage.commons.packet.api.abstracts.ServerPacket;

public class S2CPacketJoinServer extends ServerPacket {

    private final String ip;
    private final int port;

    public S2CPacketJoinServer(String ip, int port) {
        super((byte) 3);
        this.ip = ip;
        this.port = port;
    }

    public S2CPacketJoinServer(JSONObject json) {
        super((byte) 3);
        this.ip = json.getString("a");
        this.port = json.getInt("b");
    }

    @Override
    public void handle(IServerPacketHandler handler) {
        handler.handle(this);
    }

    @Override
    public String export() {
        JSONObject json = new JSONObject();
        json.put("a", this.ip);
        json.put("b", this.port);
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
