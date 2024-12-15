package rip.vantage.commons.packet.impl.server.community;

import org.json.JSONArray;
import org.json.JSONObject;
import rip.vantage.commons.handler.api.IServerPacketHandler;
import rip.vantage.commons.packet.api.abstracts.ServerPacket;

public class S2CPacketCommunityInfo extends ServerPacket  {
    private JSONArray configs;
    private JSONArray scripts;
    private final String type;

    public S2CPacketCommunityInfo(JSONObject json) {
        super((byte) 11);
        // if no data
        try {
            this.configs = json.getJSONArray("a");
        } catch (Exception e) {
            this.configs = new JSONArray();
        }
        try {
            this.scripts = json.getJSONArray("b");
        } catch (Exception e) {
            this.scripts = new JSONArray();
        }
        this.type = json.getString("c");
    }

    @Override
    public void handle(IServerPacketHandler handler) {
        handler.handle(this);
    }

    @Override
    public String export() {
        JSONObject json = new JSONObject();
        json.put("a", this.configs);
        json.put("b", this.scripts);
        json.put("c", this.type);
        json.put("id", this.getId());
        return json.toString();
    }

    public JSONArray getConfigs() {
        return configs;
    }

    public JSONArray getScripts() {
        return scripts;
    }

    public String getType() {
        return type;
    }
}
