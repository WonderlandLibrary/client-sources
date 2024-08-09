/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.block_entity_handlers;

import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.Protocol1_12_2To1_13;
import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.providers.BackwardsBlockEntityProvider;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.data.MappingDataLoader;
import com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.IntTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.ConnectionData;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

public class PistonHandler
implements BackwardsBlockEntityProvider.BackwardsBlockEntityHandler {
    private final Map<String, Integer> pistonIds = new HashMap<String, Integer>();

    public PistonHandler() {
        if (Via.getConfig().isServersideBlockConnections()) {
            Object2IntMap<String> object2IntMap = ConnectionData.getKeyToId();
            for (Map.Entry entry : object2IntMap.entrySet()) {
                if (!((String)entry.getKey()).contains("piston")) continue;
                this.addEntries((String)entry.getKey(), (Integer)entry.getValue());
            }
        } else {
            ListTag listTag = (ListTag)MappingDataLoader.loadNBT("blockstates-1.13.nbt").get("blockstates");
            for (int i = 0; i < listTag.size(); ++i) {
                StringTag stringTag = (StringTag)listTag.get(i);
                String string = stringTag.getValue();
                if (!string.contains("piston")) continue;
                this.addEntries(string, i);
            }
        }
    }

    private void addEntries(String string, int n) {
        n = Protocol1_12_2To1_13.MAPPINGS.getNewBlockStateId(n);
        this.pistonIds.put(string, n);
        String string2 = string.substring(10);
        if (!string2.startsWith("piston") && !string2.startsWith("sticky_piston")) {
            return;
        }
        String[] stringArray = string.substring(0, string.length() - 1).split("\\[");
        String[] stringArray2 = stringArray[0].split(",");
        string = stringArray[5] + "[" + stringArray2[0] + "," + stringArray2[5] + "]";
        this.pistonIds.put(string, n);
    }

    @Override
    public CompoundTag transform(UserConnection userConnection, int n, CompoundTag compoundTag) {
        CompoundTag compoundTag2 = (CompoundTag)compoundTag.get("blockState");
        if (compoundTag2 == null) {
            return compoundTag;
        }
        String string = this.getDataFromTag(compoundTag2);
        if (string == null) {
            return compoundTag;
        }
        Integer n2 = this.pistonIds.get(string);
        if (n2 == null) {
            return compoundTag;
        }
        compoundTag.put("blockId", new IntTag(n2 >> 4));
        compoundTag.put("blockData", new IntTag((int)(n2 & 0xF)));
        return compoundTag;
    }

    private String getDataFromTag(CompoundTag compoundTag) {
        StringTag stringTag = (StringTag)compoundTag.get("Name");
        if (stringTag == null) {
            return null;
        }
        CompoundTag compoundTag2 = (CompoundTag)compoundTag.get("Properties");
        if (compoundTag2 == null) {
            return stringTag.getValue();
        }
        StringJoiner stringJoiner = new StringJoiner(",", stringTag.getValue() + "[", "]");
        for (Map.Entry<String, Tag> entry : compoundTag2) {
            if (!(entry.getValue() instanceof StringTag)) continue;
            stringJoiner.add(entry.getKey() + "=" + ((StringTag)entry.getValue()).getValue());
        }
        return stringJoiner.toString();
    }
}

