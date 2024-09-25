/*
 * Decompiled with CFR 0.150.
 */
package us.myles.ViaVersion.protocols.protocol1_14to1_13_2.data;

import java.util.HashMap;
import java.util.Map;
import us.myles.ViaVersion.api.Via;
import us.myles.ViaVersion.api.data.MappingDataLoader;
import us.myles.viaversion.libs.fastutil.ints.IntOpenHashSet;
import us.myles.viaversion.libs.fastutil.ints.IntSet;
import us.myles.viaversion.libs.gson.JsonArray;
import us.myles.viaversion.libs.gson.JsonElement;
import us.myles.viaversion.libs.gson.JsonObject;

public class MappingData
extends us.myles.ViaVersion.api.data.MappingData {
    private IntSet motionBlocking;
    private IntSet nonFullBlocks;

    public MappingData() {
        super("1.13.2", "1.14");
    }

    @Override
    public void loadExtras(JsonObject oldMappings, JsonObject newMappings, JsonObject diffMappings) {
        JsonObject blockStates = newMappings.getAsJsonObject("blockstates");
        HashMap<String, Integer> blockStateMap = new HashMap<String, Integer>(blockStates.entrySet().size());
        for (Map.Entry<String, JsonElement> entry : blockStates.entrySet()) {
            blockStateMap.put(entry.getValue().getAsString(), Integer.parseInt(entry.getKey()));
        }
        JsonObject heightMapData = MappingDataLoader.loadData("heightMapData-1.14.json");
        JsonArray motionBlocking = heightMapData.getAsJsonArray("MOTION_BLOCKING");
        this.motionBlocking = new IntOpenHashSet(motionBlocking.size(), 1.0f);
        for (JsonElement jsonElement : motionBlocking) {
            String key = jsonElement.getAsString();
            Integer id = (Integer)blockStateMap.get(key);
            if (id == null) {
                Via.getPlatform().getLogger().warning("Unknown blockstate " + key + " :(");
                continue;
            }
            this.motionBlocking.add((int)id);
        }
        if (Via.getConfig().isNonFullBlockLightFix()) {
            this.nonFullBlocks = new IntOpenHashSet(1611, 1.0f);
            for (Map.Entry entry : oldMappings.getAsJsonObject("blockstates").entrySet()) {
                String state = ((JsonElement)entry.getValue()).getAsString();
                if (!state.contains("_slab") && !state.contains("_stairs") && !state.contains("_wall[")) continue;
                this.nonFullBlocks.add(this.blockStateMappings.getNewId(Integer.parseInt((String)entry.getKey())));
            }
            this.nonFullBlocks.add(this.blockStateMappings.getNewId(8163));
            for (int i = 3060; i <= 3067; ++i) {
                this.nonFullBlocks.add(this.blockStateMappings.getNewId(i));
            }
        }
    }

    public IntSet getMotionBlocking() {
        return this.motionBlocking;
    }

    public IntSet getNonFullBlocks() {
        return this.nonFullBlocks;
    }
}

