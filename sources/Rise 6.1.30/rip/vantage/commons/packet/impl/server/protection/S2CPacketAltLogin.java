package rip.vantage.commons.packet.impl.server.protection;

import org.json.JSONObject;
import rip.vantage.commons.handler.api.IServerPacketHandler;
import rip.vantage.commons.packet.api.abstracts.ServerPacket;
import rip.vantage.commons.packet.impl.client.protection.C2SPacketAltLogin;

public class S2CPacketAltLogin extends ServerPacket {

    private final String username;
    private final String uuid;
    private final String accessToken;
    private final String refreshToken;
    private String altSkin;

    public S2CPacketAltLogin(String username, String uuid, String accessToken, String refreshToken) {
        super((byte) 4);
        this.username = username;
        this.uuid = uuid;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public S2CPacketAltLogin(JSONObject json) {
        super((byte) 4);
        this.username = json.getString("a");
        this.uuid = json.getString("b");
        this.accessToken = json.getString("c");
        this.refreshToken = json.getString("d");

        if (json.has("e")) {
            this.altSkin = new C2SPacketAltLogin.AltSkin(json.getString("e")).toString();
        }
    }

    @Override
    public void handle(IServerPacketHandler handler) {
        handler.handle(this);
    }

    @Override
    public String export() {
        JSONObject json = new JSONObject();
        json.put("a", this.username);
        json.put("b", this.uuid);
        json.put("c", this.accessToken);
        json.put("d", this.refreshToken);
        json.put("id", this.getId());
        return json.toString();
    }

    public String getUsername() {
        return username;
    }

    public String getUuid() {
        return uuid;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public String getAltSkin() {
        return altSkin;
    }
}
