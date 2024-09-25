/*
 * Decompiled with CFR 0.150.
 */
package us.myles.ViaVersion.protocols.protocol1_16to1_15_2.packets;

import java.util.UUID;
import us.myles.ViaVersion.api.Via;
import us.myles.ViaVersion.api.entities.Entity1_16Types;
import us.myles.ViaVersion.api.remapper.PacketHandler;
import us.myles.ViaVersion.api.remapper.PacketRemapper;
import us.myles.ViaVersion.api.type.Type;
import us.myles.ViaVersion.api.type.types.version.Types1_14;
import us.myles.ViaVersion.protocols.protocol1_13to1_12_2.data.MappingData;
import us.myles.ViaVersion.protocols.protocol1_15to1_14_4.ClientboundPackets1_15;
import us.myles.ViaVersion.protocols.protocol1_16to1_15_2.ClientboundPackets1_16;
import us.myles.ViaVersion.protocols.protocol1_16to1_15_2.Protocol1_16To1_15_2;
import us.myles.ViaVersion.protocols.protocol1_16to1_15_2.ServerboundPackets1_16;
import us.myles.ViaVersion.protocols.protocol1_16to1_15_2.metadata.MetadataRewriter1_16To1_15_2;
import us.myles.ViaVersion.protocols.protocol1_16to1_15_2.storage.EntityTracker1_16;
import us.myles.ViaVersion.protocols.protocol1_16to1_15_2.storage.InventoryTracker1_16;
import us.myles.viaversion.libs.opennbt.tag.builtin.ByteTag;
import us.myles.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import us.myles.viaversion.libs.opennbt.tag.builtin.FloatTag;
import us.myles.viaversion.libs.opennbt.tag.builtin.IntTag;
import us.myles.viaversion.libs.opennbt.tag.builtin.ListTag;
import us.myles.viaversion.libs.opennbt.tag.builtin.LongTag;
import us.myles.viaversion.libs.opennbt.tag.builtin.StringTag;

public class EntityPackets {
    private static final PacketHandler DIMENSION_HANDLER = wrapper -> {
        String dimensionName;
        int dimension = wrapper.read(Type.INT);
        switch (dimension) {
            case -1: {
                dimensionName = "minecraft:the_nether";
                break;
            }
            case 0: {
                dimensionName = "minecraft:overworld";
                break;
            }
            case 1: {
                dimensionName = "minecraft:the_end";
                break;
            }
            default: {
                Via.getPlatform().getLogger().warning("Invalid dimension id: " + dimension);
                dimensionName = "minecraft:overworld";
            }
        }
        wrapper.write(Type.STRING, dimensionName);
        wrapper.write(Type.STRING, dimensionName);
    };
    public static final CompoundTag DIMENSIONS_TAG = new CompoundTag("");
    private static final String[] WORLD_NAMES = new String[]{"minecraft:overworld", "minecraft:the_nether", "minecraft:the_end"};

    private static CompoundTag createOverworldEntry() {
        CompoundTag tag = new CompoundTag("");
        tag.put(new StringTag("name", "minecraft:overworld"));
        tag.put(new ByteTag("has_ceiling", 0));
        EntityPackets.addSharedOverwaldEntries(tag);
        return tag;
    }

    private static CompoundTag createOverworldCavesEntry() {
        CompoundTag tag = new CompoundTag("");
        tag.put(new StringTag("name", "minecraft:overworld_caves"));
        tag.put(new ByteTag("has_ceiling", 1));
        EntityPackets.addSharedOverwaldEntries(tag);
        return tag;
    }

    private static void addSharedOverwaldEntries(CompoundTag tag) {
        tag.put(new ByteTag("piglin_safe", 0));
        tag.put(new ByteTag("natural", 1));
        tag.put(new FloatTag("ambient_light", 0.0f));
        tag.put(new StringTag("infiniburn", "minecraft:infiniburn_overworld"));
        tag.put(new ByteTag("respawn_anchor_works", 0));
        tag.put(new ByteTag("has_skylight", 1));
        tag.put(new ByteTag("bed_works", 1));
        tag.put(new ByteTag("has_raids", 1));
        tag.put(new IntTag("logical_height", 256));
        tag.put(new ByteTag("shrunk", 0));
        tag.put(new ByteTag("ultrawarm", 0));
    }

    private static CompoundTag createNetherEntry() {
        CompoundTag tag = new CompoundTag("");
        tag.put(new ByteTag("piglin_safe", 1));
        tag.put(new ByteTag("natural", 0));
        tag.put(new FloatTag("ambient_light", 0.1f));
        tag.put(new StringTag("infiniburn", "minecraft:infiniburn_nether"));
        tag.put(new ByteTag("respawn_anchor_works", 1));
        tag.put(new ByteTag("has_skylight", 0));
        tag.put(new ByteTag("bed_works", 0));
        tag.put(new LongTag("fixed_time", 18000L));
        tag.put(new ByteTag("has_raids", 0));
        tag.put(new StringTag("name", "minecraft:the_nether"));
        tag.put(new IntTag("logical_height", 128));
        tag.put(new ByteTag("shrunk", 1));
        tag.put(new ByteTag("ultrawarm", 1));
        tag.put(new ByteTag("has_ceiling", 1));
        return tag;
    }

    private static CompoundTag createEndEntry() {
        CompoundTag tag = new CompoundTag("");
        tag.put(new ByteTag("piglin_safe", 0));
        tag.put(new ByteTag("natural", 0));
        tag.put(new FloatTag("ambient_light", 0.0f));
        tag.put(new StringTag("infiniburn", "minecraft:infiniburn_end"));
        tag.put(new ByteTag("respawn_anchor_works", 0));
        tag.put(new ByteTag("has_skylight", 0));
        tag.put(new ByteTag("bed_works", 0));
        tag.put(new LongTag("fixed_time", 6000L));
        tag.put(new ByteTag("has_raids", 1));
        tag.put(new StringTag("name", "minecraft:the_end"));
        tag.put(new IntTag("logical_height", 256));
        tag.put(new ByteTag("shrunk", 0));
        tag.put(new ByteTag("ultrawarm", 0));
        tag.put(new ByteTag("has_ceiling", 0));
        return tag;
    }

    public static void register(final Protocol1_16To1_15_2 protocol) {
        MetadataRewriter1_16To1_15_2 metadataRewriter = protocol.get(MetadataRewriter1_16To1_15_2.class);
        protocol.registerOutgoing(ClientboundPackets1_15.SPAWN_GLOBAL_ENTITY, ClientboundPackets1_16.SPAWN_ENTITY, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.handler(wrapper -> {
                    int entityId = wrapper.passthrough(Type.VAR_INT);
                    wrapper.user().get(EntityTracker1_16.class).addEntity(entityId, Entity1_16Types.EntityType.LIGHTNING_BOLT);
                    wrapper.write(Type.UUID, UUID.randomUUID());
                    wrapper.write(Type.VAR_INT, Entity1_16Types.EntityType.LIGHTNING_BOLT.getId());
                    wrapper.read(Type.BYTE);
                    wrapper.passthrough(Type.DOUBLE);
                    wrapper.passthrough(Type.DOUBLE);
                    wrapper.passthrough(Type.DOUBLE);
                    wrapper.write(Type.BYTE, (byte)0);
                    wrapper.write(Type.BYTE, (byte)0);
                    wrapper.write(Type.INT, 0);
                    wrapper.write(Type.SHORT, (short)0);
                    wrapper.write(Type.SHORT, (short)0);
                    wrapper.write(Type.SHORT, (short)0);
                });
            }
        });
        metadataRewriter.registerSpawnTrackerWithData(ClientboundPackets1_15.SPAWN_ENTITY, Entity1_16Types.EntityType.FALLING_BLOCK);
        metadataRewriter.registerTracker(ClientboundPackets1_15.SPAWN_MOB);
        metadataRewriter.registerTracker(ClientboundPackets1_15.SPAWN_PLAYER, Entity1_16Types.EntityType.PLAYER);
        metadataRewriter.registerMetadataRewriter(ClientboundPackets1_15.ENTITY_METADATA, Types1_14.METADATA_LIST);
        metadataRewriter.registerEntityDestroy(ClientboundPackets1_15.DESTROY_ENTITIES);
        protocol.registerOutgoing(ClientboundPackets1_15.RESPAWN, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.handler(DIMENSION_HANDLER);
                this.map(Type.LONG);
                this.map(Type.UNSIGNED_BYTE);
                this.handler(wrapper -> {
                    wrapper.write(Type.BYTE, (byte)-1);
                    String levelType = wrapper.read(Type.STRING);
                    wrapper.write(Type.BOOLEAN, false);
                    wrapper.write(Type.BOOLEAN, levelType.equals("flat"));
                    wrapper.write(Type.BOOLEAN, true);
                });
            }
        });
        protocol.registerOutgoing(ClientboundPackets1_15.JOIN_GAME, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.INT);
                this.map(Type.UNSIGNED_BYTE);
                this.handler(wrapper -> {
                    wrapper.write(Type.BYTE, (byte)-1);
                    wrapper.write(Type.STRING_ARRAY, WORLD_NAMES);
                    wrapper.write(Type.NBT, DIMENSIONS_TAG);
                });
                this.handler(DIMENSION_HANDLER);
                this.map(Type.LONG);
                this.map(Type.UNSIGNED_BYTE);
                this.handler(wrapper -> {
                    wrapper.user().get(EntityTracker1_16.class).addEntity(wrapper.get(Type.INT, 0), Entity1_16Types.EntityType.PLAYER);
                    String type = wrapper.read(Type.STRING);
                    wrapper.passthrough(Type.VAR_INT);
                    wrapper.passthrough(Type.BOOLEAN);
                    wrapper.passthrough(Type.BOOLEAN);
                    wrapper.write(Type.BOOLEAN, false);
                    wrapper.write(Type.BOOLEAN, type.equals("flat"));
                });
            }
        });
        protocol.registerOutgoing(ClientboundPackets1_15.ENTITY_PROPERTIES, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.handler(wrapper -> {
                    int size;
                    wrapper.passthrough(Type.VAR_INT);
                    int actualSize = size = wrapper.passthrough(Type.INT).intValue();
                    for (int i = 0; i < size; ++i) {
                        int j;
                        int modifierSize;
                        String key = wrapper.read(Type.STRING);
                        String attributeIdentifier = (String)protocol.getMappingData().getAttributeMappings().get((Object)key);
                        if (attributeIdentifier == null && !MappingData.isValid1_13Channel(attributeIdentifier = "minecraft:" + key)) {
                            if (!Via.getConfig().isSuppressConversionWarnings()) {
                                Via.getPlatform().getLogger().warning("Invalid attribute: " + key);
                            }
                            --actualSize;
                            wrapper.read(Type.DOUBLE);
                            modifierSize = wrapper.read(Type.VAR_INT);
                            for (j = 0; j < modifierSize; ++j) {
                                wrapper.read(Type.UUID);
                                wrapper.read(Type.DOUBLE);
                                wrapper.read(Type.BYTE);
                            }
                            continue;
                        }
                        wrapper.write(Type.STRING, attributeIdentifier);
                        wrapper.passthrough(Type.DOUBLE);
                        modifierSize = wrapper.passthrough(Type.VAR_INT);
                        for (j = 0; j < modifierSize; ++j) {
                            wrapper.passthrough(Type.UUID);
                            wrapper.passthrough(Type.DOUBLE);
                            wrapper.passthrough(Type.BYTE);
                        }
                    }
                    if (size != actualSize) {
                        wrapper.set(Type.INT, 0, actualSize);
                    }
                });
            }
        });
        protocol.registerIncoming(ServerboundPackets1_16.ANIMATION, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.handler(wrapper -> {
                    InventoryTracker1_16 inventoryTracker = wrapper.user().get(InventoryTracker1_16.class);
                    if (inventoryTracker.getInventory() != -1) {
                        wrapper.cancel();
                    }
                });
            }
        });
    }

    static {
        ListTag list = new ListTag("dimension", CompoundTag.class);
        list.add(EntityPackets.createOverworldEntry());
        list.add(EntityPackets.createOverworldCavesEntry());
        list.add(EntityPackets.createNetherEntry());
        list.add(EntityPackets.createEndEntry());
        DIMENSIONS_TAG.put(list);
    }
}

