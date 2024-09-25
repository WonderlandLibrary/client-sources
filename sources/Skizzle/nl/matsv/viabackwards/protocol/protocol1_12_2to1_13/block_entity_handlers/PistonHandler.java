/*
 * Decompiled with CFR 0.150.
 */
package nl.matsv.viabackwards.protocol.protocol1_12_2to1_13.block_entity_handlers;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;
import nl.matsv.viabackwards.protocol.protocol1_12_2to1_13.Protocol1_12_2To1_13;
import nl.matsv.viabackwards.protocol.protocol1_12_2to1_13.providers.BackwardsBlockEntityProvider;
import us.myles.ViaVersion.api.Via;
import us.myles.ViaVersion.api.data.MappingDataLoader;
import us.myles.ViaVersion.api.data.UserConnection;
import us.myles.ViaVersion.protocols.protocol1_13to1_12_2.blockconnections.ConnectionData;
import us.myles.viaversion.libs.gson.JsonElement;
import us.myles.viaversion.libs.gson.JsonObject;
import us.myles.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import us.myles.viaversion.libs.opennbt.tag.builtin.IntTag;
import us.myles.viaversion.libs.opennbt.tag.builtin.StringTag;
import us.myles.viaversion.libs.opennbt.tag.builtin.Tag;

public class PistonHandler
implements BackwardsBlockEntityProvider.BackwardsBlockEntityHandler {
    private final Map<String, Integer> pistonIds = new HashMap<String, Integer>();

    public PistonHandler() {
        if (Via.getConfig().isServersideBlockConnections()) {
            Map keyToId;
            try {
                Field field = ConnectionData.class.getDeclaredField("keyToId");
                field.setAccessible(true);
                keyToId = (Map)field.get(null);
            }
            catch (IllegalAccessException | NoSuchFieldException e) {
                e.printStackTrace();
                return;
            }
            for (Map.Entry entry : keyToId.entrySet()) {
                if (!((String)entry.getKey()).contains("piston")) continue;
                this.addEntries((String)entry.getKey(), (Integer)entry.getValue());
            }
        } else {
            JsonObject mappings = MappingDataLoader.getMappingsCache().get("mapping-1.13.json").getAsJsonObject("blockstates");
            for (Map.Entry<String, JsonElement> blockState : mappings.entrySet()) {
                String key = blockState.getValue().getAsString();
                if (!key.contains("piston")) continue;
                this.addEntries(key, Integer.parseInt(blockState.getKey()));
            }
        }
    }

    private void addEntries(String data, int id) {
        id = Protocol1_12_2To1_13.MAPPINGS.getNewBlockStateId(id);
        this.pistonIds.put(data, id);
        String substring = data.substring(10);
        if (!substring.startsWith("piston") && !substring.startsWith("sticky_piston")) {
            return;
        }
        String[] split = data.substring(0, data.length() - 1).split("\\[");
        String[] properties = split[1].split(",");
        data = split[0] + "[" + properties[1] + "," + properties[0] + "]";
        this.pistonIds.put(data, id);
    }

    @Override
    public CompoundTag transform(UserConnection user, int blockId, CompoundTag tag) {
        CompoundTag blockState = (CompoundTag)tag.get("blockState");
        if (blockState == null) {
            return tag;
        }
        String dataFromTag = this.getDataFromTag(blockState);
        if (dataFromTag == null) {
            return tag;
        }
        Integer id = this.pistonIds.get(dataFromTag);
        if (id == null) {
            return tag;
        }
        tag.put(new IntTag("blockId", id >> 4));
        tag.put(new IntTag("blockData", (int)(id & 0xF)));
        return tag;
    }

    private String getDataFromTag(CompoundTag tag) {
        StringTag name = (StringTag)tag.get("Name");
        if (name == null) {
            return null;
        }
        CompoundTag properties = (CompoundTag)tag.get("Properties");
        if (properties == null) {
            return name.getValue();
        }
        StringJoiner joiner = new StringJoiner(",", name.getValue() + "[", "]");
        for (Tag property : properties) {
            if (!(property instanceof StringTag)) continue;
            joiner.add(property.getName() + "=" + ((StringTag)property).getValue());
        }
        return joiner.toString();
    }
}

