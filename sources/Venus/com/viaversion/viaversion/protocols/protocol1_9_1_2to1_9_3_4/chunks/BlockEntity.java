/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_9_1_2to1_9_3_4.chunks;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.NumberTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import com.viaversion.viaversion.protocols.protocol1_9_1_2to1_9_3_4.Protocol1_9_1_2To1_9_3_4;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.ClientboundPackets1_9_3;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BlockEntity {
    private static final Map<String, Integer> types = new HashMap<String, Integer>();

    public static void handle(List<CompoundTag> list, UserConnection userConnection) {
        for (CompoundTag compoundTag : list) {
            try {
                if (!compoundTag.contains("id")) {
                    throw new Exception("NBT tag not handled because the id key is missing");
                }
                String string = (String)((Tag)compoundTag.get("id")).getValue();
                if (!types.containsKey(string)) {
                    throw new Exception("Not handled id: " + string);
                }
                int n = types.get(string);
                if (n == -1) continue;
                int n2 = ((NumberTag)compoundTag.get("x")).asInt();
                int n3 = ((NumberTag)compoundTag.get("y")).asInt();
                int n4 = ((NumberTag)compoundTag.get("z")).asInt();
                Position position = new Position(n2, (short)n3, n4);
                BlockEntity.updateBlockEntity(position, (short)n, compoundTag, userConnection);
            } catch (Exception exception) {
                if (!Via.getManager().isDebug()) continue;
                Via.getPlatform().getLogger().warning("Block Entity: " + exception.getMessage() + ": " + compoundTag);
            }
        }
    }

    private static void updateBlockEntity(Position position, short s, CompoundTag compoundTag, UserConnection userConnection) throws Exception {
        PacketWrapper packetWrapper = PacketWrapper.create(ClientboundPackets1_9_3.BLOCK_ENTITY_DATA, null, userConnection);
        packetWrapper.write(Type.POSITION, position);
        packetWrapper.write(Type.UNSIGNED_BYTE, s);
        packetWrapper.write(Type.NBT, compoundTag);
        packetWrapper.scheduleSend(Protocol1_9_1_2To1_9_3_4.class, false);
    }

    static {
        types.put("MobSpawner", 1);
        types.put("Control", 2);
        types.put("Beacon", 3);
        types.put("Skull", 4);
        types.put("FlowerPot", 5);
        types.put("Banner", 6);
        types.put("UNKNOWN", 7);
        types.put("EndGateway", 8);
        types.put("Sign", 9);
    }
}

