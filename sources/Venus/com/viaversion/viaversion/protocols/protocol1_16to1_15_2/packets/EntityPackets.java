/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_16to1_15_2.packets;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.minecraft.WorldIdentifiers;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_16Types;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.version.Types1_14;
import com.viaversion.viaversion.api.type.types.version.Types1_16;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ByteTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.FloatTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.IntTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.LongTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data.MappingData;
import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.ClientboundPackets1_15;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.ClientboundPackets1_16;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.Protocol1_16To1_15_2;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.ServerboundPackets1_16;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.metadata.MetadataRewriter1_16To1_15_2;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.storage.InventoryTracker1_16;
import java.util.Arrays;
import java.util.UUID;

public class EntityPackets {
    private static final PacketHandler DIMENSION_HANDLER = EntityPackets::lambda$static$0;
    public static final CompoundTag DIMENSIONS_TAG = new CompoundTag();
    private static final String[] WORLD_NAMES = new String[]{"minecraft:overworld", "minecraft:the_nether", "minecraft:the_end"};

    private static CompoundTag createOverworldEntry() {
        CompoundTag compoundTag = new CompoundTag();
        compoundTag.put("name", new StringTag("minecraft:overworld"));
        compoundTag.put("has_ceiling", new ByteTag(0));
        EntityPackets.addSharedOverwaldEntries(compoundTag);
        return compoundTag;
    }

    private static CompoundTag createOverworldCavesEntry() {
        CompoundTag compoundTag = new CompoundTag();
        compoundTag.put("name", new StringTag("minecraft:overworld_caves"));
        compoundTag.put("has_ceiling", new ByteTag(1));
        EntityPackets.addSharedOverwaldEntries(compoundTag);
        return compoundTag;
    }

    private static void addSharedOverwaldEntries(CompoundTag compoundTag) {
        compoundTag.put("piglin_safe", new ByteTag(0));
        compoundTag.put("natural", new ByteTag(1));
        compoundTag.put("ambient_light", new FloatTag(0.0f));
        compoundTag.put("infiniburn", new StringTag("minecraft:infiniburn_overworld"));
        compoundTag.put("respawn_anchor_works", new ByteTag(0));
        compoundTag.put("has_skylight", new ByteTag(1));
        compoundTag.put("bed_works", new ByteTag(1));
        compoundTag.put("has_raids", new ByteTag(1));
        compoundTag.put("logical_height", new IntTag(256));
        compoundTag.put("shrunk", new ByteTag(0));
        compoundTag.put("ultrawarm", new ByteTag(0));
    }

    private static CompoundTag createNetherEntry() {
        CompoundTag compoundTag = new CompoundTag();
        compoundTag.put("piglin_safe", new ByteTag(1));
        compoundTag.put("natural", new ByteTag(0));
        compoundTag.put("ambient_light", new FloatTag(0.1f));
        compoundTag.put("infiniburn", new StringTag("minecraft:infiniburn_nether"));
        compoundTag.put("respawn_anchor_works", new ByteTag(1));
        compoundTag.put("has_skylight", new ByteTag(0));
        compoundTag.put("bed_works", new ByteTag(0));
        compoundTag.put("fixed_time", new LongTag(18000L));
        compoundTag.put("has_raids", new ByteTag(0));
        compoundTag.put("name", new StringTag("minecraft:the_nether"));
        compoundTag.put("logical_height", new IntTag(128));
        compoundTag.put("shrunk", new ByteTag(1));
        compoundTag.put("ultrawarm", new ByteTag(1));
        compoundTag.put("has_ceiling", new ByteTag(1));
        return compoundTag;
    }

    private static CompoundTag createEndEntry() {
        CompoundTag compoundTag = new CompoundTag();
        compoundTag.put("piglin_safe", new ByteTag(0));
        compoundTag.put("natural", new ByteTag(0));
        compoundTag.put("ambient_light", new FloatTag(0.0f));
        compoundTag.put("infiniburn", new StringTag("minecraft:infiniburn_end"));
        compoundTag.put("respawn_anchor_works", new ByteTag(0));
        compoundTag.put("has_skylight", new ByteTag(0));
        compoundTag.put("bed_works", new ByteTag(0));
        compoundTag.put("fixed_time", new LongTag(6000L));
        compoundTag.put("has_raids", new ByteTag(1));
        compoundTag.put("name", new StringTag("minecraft:the_end"));
        compoundTag.put("logical_height", new IntTag(256));
        compoundTag.put("shrunk", new ByteTag(0));
        compoundTag.put("ultrawarm", new ByteTag(0));
        compoundTag.put("has_ceiling", new ByteTag(0));
        return compoundTag;
    }

    public static void register(Protocol1_16To1_15_2 protocol1_16To1_15_2) {
        MetadataRewriter1_16To1_15_2 metadataRewriter1_16To1_15_2 = protocol1_16To1_15_2.get(MetadataRewriter1_16To1_15_2.class);
        protocol1_16To1_15_2.registerClientbound(ClientboundPackets1_15.SPAWN_GLOBAL_ENTITY, ClientboundPackets1_16.SPAWN_ENTITY, EntityPackets::lambda$register$1);
        metadataRewriter1_16To1_15_2.registerTrackerWithData(ClientboundPackets1_15.SPAWN_ENTITY, Entity1_16Types.FALLING_BLOCK);
        metadataRewriter1_16To1_15_2.registerTracker(ClientboundPackets1_15.SPAWN_MOB);
        metadataRewriter1_16To1_15_2.registerTracker(ClientboundPackets1_15.SPAWN_PLAYER, Entity1_16Types.PLAYER);
        metadataRewriter1_16To1_15_2.registerMetadataRewriter(ClientboundPackets1_15.ENTITY_METADATA, Types1_14.METADATA_LIST, Types1_16.METADATA_LIST);
        metadataRewriter1_16To1_15_2.registerRemoveEntities(ClientboundPackets1_15.DESTROY_ENTITIES);
        protocol1_16To1_15_2.registerClientbound(ClientboundPackets1_15.RESPAWN, new PacketHandlers(){

            @Override
            public void register() {
                this.handler(EntityPackets.access$000());
                this.map(Type.LONG);
                this.map(Type.UNSIGNED_BYTE);
                this.handler(1::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                packetWrapper.write(Type.BYTE, (byte)-1);
                String string = packetWrapper.read(Type.STRING);
                packetWrapper.write(Type.BOOLEAN, false);
                packetWrapper.write(Type.BOOLEAN, string.equals("flat"));
                packetWrapper.write(Type.BOOLEAN, true);
            }
        });
        protocol1_16To1_15_2.registerClientbound(ClientboundPackets1_15.JOIN_GAME, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.INT);
                this.map(Type.UNSIGNED_BYTE);
                this.handler(2::lambda$register$0);
                this.handler(EntityPackets.access$000());
                this.map(Type.LONG);
                this.map(Type.UNSIGNED_BYTE);
                this.handler(2::lambda$register$1);
            }

            private static void lambda$register$1(PacketWrapper packetWrapper) throws Exception {
                packetWrapper.user().getEntityTracker(Protocol1_16To1_15_2.class).addEntity(packetWrapper.get(Type.INT, 0), Entity1_16Types.PLAYER);
                String string = packetWrapper.read(Type.STRING);
                packetWrapper.passthrough(Type.VAR_INT);
                packetWrapper.passthrough(Type.BOOLEAN);
                packetWrapper.passthrough(Type.BOOLEAN);
                packetWrapper.write(Type.BOOLEAN, false);
                packetWrapper.write(Type.BOOLEAN, string.equals("flat"));
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                packetWrapper.write(Type.BYTE, (byte)-1);
                packetWrapper.write(Type.STRING_ARRAY, Arrays.copyOf(EntityPackets.access$100(), EntityPackets.access$100().length));
                packetWrapper.write(Type.NBT, DIMENSIONS_TAG.clone());
            }
        });
        protocol1_16To1_15_2.registerClientbound(ClientboundPackets1_15.ENTITY_PROPERTIES, arg_0 -> EntityPackets.lambda$register$2(protocol1_16To1_15_2, arg_0));
        protocol1_16To1_15_2.registerServerbound(ServerboundPackets1_16.ANIMATION, EntityPackets::lambda$register$3);
    }

    private static void lambda$register$3(PacketWrapper packetWrapper) throws Exception {
        InventoryTracker1_16 inventoryTracker1_16 = packetWrapper.user().get(InventoryTracker1_16.class);
        if (inventoryTracker1_16.isInventoryOpen()) {
            packetWrapper.cancel();
        }
    }

    private static void lambda$register$2(Protocol1_16To1_15_2 protocol1_16To1_15_2, PacketWrapper packetWrapper) throws Exception {
        int n;
        packetWrapper.passthrough(Type.VAR_INT);
        int n2 = n = packetWrapper.passthrough(Type.INT).intValue();
        for (int i = 0; i < n; ++i) {
            int n3;
            int n4;
            String string = packetWrapper.read(Type.STRING);
            String string2 = (String)protocol1_16To1_15_2.getMappingData().getAttributeMappings().get(string);
            if (string2 == null && !MappingData.isValid1_13Channel(string2 = "minecraft:" + string)) {
                if (!Via.getConfig().isSuppressConversionWarnings()) {
                    Via.getPlatform().getLogger().warning("Invalid attribute: " + string);
                }
                --n2;
                packetWrapper.read(Type.DOUBLE);
                n4 = packetWrapper.read(Type.VAR_INT);
                for (n3 = 0; n3 < n4; ++n3) {
                    packetWrapper.read(Type.UUID);
                    packetWrapper.read(Type.DOUBLE);
                    packetWrapper.read(Type.BYTE);
                }
                continue;
            }
            packetWrapper.write(Type.STRING, string2);
            packetWrapper.passthrough(Type.DOUBLE);
            n4 = packetWrapper.passthrough(Type.VAR_INT);
            for (n3 = 0; n3 < n4; ++n3) {
                packetWrapper.passthrough(Type.UUID);
                packetWrapper.passthrough(Type.DOUBLE);
                packetWrapper.passthrough(Type.BYTE);
            }
        }
        if (n != n2) {
            packetWrapper.set(Type.INT, 0, n2);
        }
    }

    private static void lambda$register$1(PacketWrapper packetWrapper) throws Exception {
        int n = packetWrapper.passthrough(Type.VAR_INT);
        byte by = packetWrapper.read(Type.BYTE);
        if (by != 1) {
            packetWrapper.cancel();
            return;
        }
        packetWrapper.user().getEntityTracker(Protocol1_16To1_15_2.class).addEntity(n, Entity1_16Types.LIGHTNING_BOLT);
        packetWrapper.write(Type.UUID, UUID.randomUUID());
        packetWrapper.write(Type.VAR_INT, Entity1_16Types.LIGHTNING_BOLT.getId());
        packetWrapper.passthrough(Type.DOUBLE);
        packetWrapper.passthrough(Type.DOUBLE);
        packetWrapper.passthrough(Type.DOUBLE);
        packetWrapper.write(Type.BYTE, (byte)0);
        packetWrapper.write(Type.BYTE, (byte)0);
        packetWrapper.write(Type.INT, 0);
        packetWrapper.write(Type.SHORT, (short)0);
        packetWrapper.write(Type.SHORT, (short)0);
        packetWrapper.write(Type.SHORT, (short)0);
    }

    private static void lambda$static$0(PacketWrapper packetWrapper) throws Exception {
        String string;
        String string2;
        WorldIdentifiers worldIdentifiers = Via.getConfig().get1_16WorldNamesMap();
        WorldIdentifiers worldIdentifiers2 = packetWrapper.user().get(WorldIdentifiers.class);
        if (worldIdentifiers2 != null) {
            worldIdentifiers = worldIdentifiers2;
        }
        int n = packetWrapper.read(Type.INT);
        switch (n) {
            case -1: {
                string2 = "minecraft:the_nether";
                string = worldIdentifiers.nether();
                break;
            }
            case 0: {
                string2 = "minecraft:overworld";
                string = worldIdentifiers.overworld();
                break;
            }
            case 1: {
                string2 = "minecraft:the_end";
                string = worldIdentifiers.end();
                break;
            }
            default: {
                Via.getPlatform().getLogger().warning("Invalid dimension id: " + n);
                string2 = "minecraft:overworld";
                string = worldIdentifiers.overworld();
            }
        }
        packetWrapper.write(Type.STRING, string2);
        packetWrapper.write(Type.STRING, string);
    }

    static PacketHandler access$000() {
        return DIMENSION_HANDLER;
    }

    static String[] access$100() {
        return WORLD_NAMES;
    }

    static {
        ListTag listTag = new ListTag(CompoundTag.class);
        listTag.add(EntityPackets.createOverworldEntry());
        listTag.add(EntityPackets.createOverworldCavesEntry());
        listTag.add(EntityPackets.createNetherEntry());
        listTag.add(EntityPackets.createEndEntry());
        DIMENSIONS_TAG.put("dimension", listTag);
    }
}

