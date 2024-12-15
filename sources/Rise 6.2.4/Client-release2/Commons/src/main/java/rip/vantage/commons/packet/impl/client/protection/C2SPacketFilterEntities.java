package rip.vantage.commons.packet.impl.client.protection;

import org.json.JSONArray;
import org.json.JSONObject;
import rip.vantage.commons.handler.api.IClientPacketHandler;
import rip.vantage.commons.packet.api.abstracts.ClientPacket;

import java.util.ArrayList;
import java.util.List;

public class C2SPacketFilterEntities extends ClientPacket {

    private final List<Entity> entityList;
    private final boolean players;
    private final boolean invisibles;
    private final boolean animals;
    private final boolean mobs;
    private final int uid;

    public C2SPacketFilterEntities(List<Entity> entityList, boolean players, boolean invisibles, boolean animals, boolean mobs, int uid) {
        super((byte) 8);
        this.entityList = entityList;
        this.players = players;
        this.invisibles = invisibles;
        this.animals = animals;
        this.mobs = mobs;
        this.uid = uid;
    }

    public C2SPacketFilterEntities(JSONObject json) {
        super((byte) 8);

        JSONArray entityArray = json.getJSONArray("a");

        List<Entity> entityList = new ArrayList<>();
        for (int i = 0; i < entityArray.length(); i++) {
            JSONObject entityObj = entityArray.getJSONObject(i);
            entityList.add(new Entity(entityObj.getInt("a"), entityObj.getInt("b"), entityObj.getBoolean("c")));
        }

        this.entityList = entityList;
        this.players = json.getBoolean("b");
        this.invisibles = json.getBoolean("c");
        this.animals = json.getBoolean("d");
        this.mobs = json.getBoolean("e");
        this.uid = json.getInt("f");
    }

    @Override
    public void handle(IClientPacketHandler handler) {
        handler.handle(this);
    }

    @Override
    public String export() {
        JSONObject json = new JSONObject();

        JSONArray array = new JSONArray();
        for (C2SPacketFilterEntities.Entity entity : this.entityList) {
            JSONObject entityJson = new JSONObject();
            entityJson.put("a", entity.getEntityId());
            entityJson.put("b", entity.getType());
            entityJson.put("c", entity.isInvisible());
            array.put(entityJson);
        }

        json.put("a", array);
        json.put("b", this.players);
        json.put("c", this.invisibles);
        json.put("d", this.animals);
        json.put("e", this.mobs);
        json.put("f", this.uid);
        json.put("id", this.getId());
        return json.toString();
    }

    public List<Entity> getEntityList() {
        return entityList;
    }

    public boolean isPlayers() {
        return players;
    }

    public boolean isInvisibles() {
        return invisibles;
    }

    public boolean isAnimals() {
        return animals;
    }

    public boolean isMobs() {
        return mobs;
    }

    public int getUid() {
        return uid;
    }

    public static class Entity {

        private final int entityId;
        private final int type; // 0=player, 1=animal, 2=mob
        private final boolean invisible;

        public Entity(int entityId, int type, boolean invisible) {
            this.entityId = entityId;
            this.type = type;
            this.invisible = invisible;
        }

        public int getEntityId() {
            return entityId;
        }

        public int getType() {
            return type;
        }

        public boolean isInvisible() {
            return invisible;
        }
    }
}
