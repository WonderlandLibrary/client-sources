package rip.vantage.commons.packet.impl.server.protection;

import org.json.JSONArray;
import org.json.JSONObject;
import rip.vantage.commons.handler.api.IServerPacketHandler;
import rip.vantage.commons.packet.api.abstracts.ServerPacket;
import rip.vantage.commons.packet.impl.client.protection.C2SPacketFilterEntities;

import java.util.ArrayList;
import java.util.List;

public class S2CPacketEntities extends ServerPacket {

    private final List<C2SPacketFilterEntities.Entity> entityList;
    private final int uid;

    public S2CPacketEntities(List<C2SPacketFilterEntities.Entity> entityList, int uid) {
        super((byte) 7);
        this.entityList = entityList;
        this.uid = uid;
    }

    public S2CPacketEntities(JSONObject json) {
        super((byte) 7);

        JSONArray entityArray = json.getJSONArray("a");

        List<C2SPacketFilterEntities.Entity> entityList = new ArrayList<>();
        for (int i = 0; i < entityArray.length(); i++) {
            JSONObject entityObj = entityArray.getJSONObject(i);
            entityList.add(new C2SPacketFilterEntities.Entity(entityObj.getInt("a"), -1, false));
        }

        this.entityList = entityList;
        this.uid = json.getInt("b");
    }

    @Override
    public void handle(IServerPacketHandler handler) {
        handler.handle(this);
    }

    @Override
    public String export() {
        JSONObject json = new JSONObject();

        JSONArray array = new JSONArray();
        for (C2SPacketFilterEntities.Entity entity : this.entityList) {
            JSONObject entityJson = new JSONObject();
            entityJson.put("a", entity.getEntityId());
            array.put(entityJson);
        }

        json.put("a", array);
        json.put("b", this.uid);
        json.put("id", this.getId());
        return json.toString();
    }

    public List<C2SPacketFilterEntities.Entity> getEntityList() {
        return entityList;
    }

    public int getUid() {
        return uid;
    }
}