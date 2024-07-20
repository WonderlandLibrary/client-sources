/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_17to1_16_4;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.data.MappingData;
import com.viaversion.viaversion.api.data.MappingDataBase;
import com.viaversion.viaversion.api.minecraft.RegistryType;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_17Types;
import com.viaversion.viaversion.api.protocol.AbstractProtocol;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.minecraft.ParticleType;
import com.viaversion.viaversion.api.type.types.version.Types1_17;
import com.viaversion.viaversion.data.entity.EntityTrackerBase;
import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.ClientboundPackets1_16_2;
import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.ServerboundPackets1_16_2;
import com.viaversion.viaversion.protocols.protocol1_17to1_16_4.ClientboundPackets1_17;
import com.viaversion.viaversion.protocols.protocol1_17to1_16_4.ServerboundPackets1_17;
import com.viaversion.viaversion.protocols.protocol1_17to1_16_4.packets.EntityPackets;
import com.viaversion.viaversion.protocols.protocol1_17to1_16_4.packets.InventoryPackets;
import com.viaversion.viaversion.protocols.protocol1_17to1_16_4.packets.WorldPackets;
import com.viaversion.viaversion.protocols.protocol1_17to1_16_4.storage.InventoryAcknowledgements;
import com.viaversion.viaversion.rewriter.SoundRewriter;
import com.viaversion.viaversion.rewriter.StatisticsRewriter;
import com.viaversion.viaversion.rewriter.TagRewriter;

public final class Protocol1_17To1_16_4
extends AbstractProtocol<ClientboundPackets1_16_2, ClientboundPackets1_17, ServerboundPackets1_16_2, ServerboundPackets1_17> {
    public static final MappingData MAPPINGS = new MappingDataBase("1.16.2", "1.17");
    private static final String[] NEW_GAME_EVENT_TAGS = new String[]{"minecraft:ignore_vibrations_sneaking", "minecraft:vibrations"};
    private final EntityPackets entityRewriter = new EntityPackets(this);
    private final InventoryPackets itemRewriter = new InventoryPackets(this);
    private final TagRewriter<ClientboundPackets1_16_2> tagRewriter = new TagRewriter<ClientboundPackets1_16_2>(this);

    public Protocol1_17To1_16_4() {
        super(ClientboundPackets1_16_2.class, ClientboundPackets1_17.class, ServerboundPackets1_16_2.class, ServerboundPackets1_17.class);
    }

    @Override
    protected void registerPackets() {
        this.entityRewriter.register();
        this.itemRewriter.register();
        WorldPackets.register(this);
        this.registerClientbound(ClientboundPackets1_16_2.TAGS, (PacketWrapper wrapper) -> {
            wrapper.write(Type.VAR_INT, 5);
            for (RegistryType registryType : RegistryType.getValues()) {
                wrapper.write(Type.STRING, registryType.resourceLocation());
                this.tagRewriter.handle(wrapper, this.tagRewriter.getRewriter(registryType), this.tagRewriter.getNewTags(registryType));
                if (registryType == RegistryType.ENTITY) break;
            }
            wrapper.write(Type.STRING, RegistryType.GAME_EVENT.resourceLocation());
            wrapper.write(Type.VAR_INT, NEW_GAME_EVENT_TAGS.length);
            for (String string : NEW_GAME_EVENT_TAGS) {
                wrapper.write(Type.STRING, string);
                wrapper.write(Type.VAR_INT_ARRAY_PRIMITIVE, new int[0]);
            }
        });
        new StatisticsRewriter<ClientboundPackets1_16_2>(this).register(ClientboundPackets1_16_2.STATISTICS);
        SoundRewriter<ClientboundPackets1_16_2> soundRewriter = new SoundRewriter<ClientboundPackets1_16_2>(this);
        soundRewriter.registerSound(ClientboundPackets1_16_2.SOUND);
        soundRewriter.registerSound(ClientboundPackets1_16_2.ENTITY_SOUND);
        this.registerClientbound(ClientboundPackets1_16_2.RESOURCE_PACK, (PacketWrapper wrapper) -> {
            wrapper.passthrough(Type.STRING);
            wrapper.passthrough(Type.STRING);
            wrapper.write(Type.BOOLEAN, Via.getConfig().isForcedUse1_17ResourcePack());
            wrapper.write(Type.OPTIONAL_COMPONENT, Via.getConfig().get1_17ResourcePackPrompt());
        });
        this.registerClientbound(ClientboundPackets1_16_2.MAP_DATA, (PacketWrapper wrapper) -> {
            wrapper.passthrough(Type.VAR_INT);
            wrapper.passthrough(Type.BYTE);
            wrapper.read(Type.BOOLEAN);
            wrapper.passthrough(Type.BOOLEAN);
            int size = wrapper.read(Type.VAR_INT);
            if (size != 0) {
                wrapper.write(Type.BOOLEAN, true);
                wrapper.write(Type.VAR_INT, size);
            } else {
                wrapper.write(Type.BOOLEAN, false);
            }
        });
        this.registerClientbound(ClientboundPackets1_16_2.TITLE, null, (PacketWrapper wrapper) -> {
            ClientboundPackets1_17 packetType;
            int type2 = wrapper.read(Type.VAR_INT);
            switch (type2) {
                case 0: {
                    packetType = ClientboundPackets1_17.TITLE_TEXT;
                    break;
                }
                case 1: {
                    packetType = ClientboundPackets1_17.TITLE_SUBTITLE;
                    break;
                }
                case 2: {
                    packetType = ClientboundPackets1_17.ACTIONBAR;
                    break;
                }
                case 3: {
                    packetType = ClientboundPackets1_17.TITLE_TIMES;
                    break;
                }
                case 4: {
                    packetType = ClientboundPackets1_17.CLEAR_TITLES;
                    wrapper.write(Type.BOOLEAN, false);
                    break;
                }
                case 5: {
                    packetType = ClientboundPackets1_17.CLEAR_TITLES;
                    wrapper.write(Type.BOOLEAN, true);
                    break;
                }
                default: {
                    throw new IllegalArgumentException("Invalid title type received: " + type2);
                }
            }
            wrapper.setPacketType(packetType);
        });
        this.registerClientbound(ClientboundPackets1_16_2.EXPLOSION, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.handler(wrapper -> wrapper.write(Type.VAR_INT, wrapper.read(Type.INT)));
            }
        });
        this.registerClientbound(ClientboundPackets1_16_2.SPAWN_POSITION, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.POSITION1_14);
                this.handler(wrapper -> wrapper.write(Type.FLOAT, Float.valueOf(0.0f)));
            }
        });
        this.registerServerbound(ServerboundPackets1_17.CLIENT_SETTINGS, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.STRING);
                this.map(Type.BYTE);
                this.map(Type.VAR_INT);
                this.map(Type.BOOLEAN);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.VAR_INT);
                this.handler(wrapper -> wrapper.read(Type.BOOLEAN));
            }
        });
    }

    @Override
    protected void onMappingDataLoaded() {
        this.tagRewriter.loadFromMappingData();
        this.tagRewriter.addEmptyTags(RegistryType.ITEM, "minecraft:candles", "minecraft:ignored_by_piglin_babies", "minecraft:piglin_food", "minecraft:freeze_immune_wearables", "minecraft:axolotl_tempt_items", "minecraft:occludes_vibration_signals", "minecraft:fox_food", "minecraft:diamond_ores", "minecraft:iron_ores", "minecraft:lapis_ores", "minecraft:redstone_ores", "minecraft:coal_ores", "minecraft:copper_ores", "minecraft:emerald_ores", "minecraft:cluster_max_harvestables");
        this.tagRewriter.addEmptyTags(RegistryType.BLOCK, "minecraft:crystal_sound_blocks", "minecraft:candle_cakes", "minecraft:candles", "minecraft:snow_step_sound_blocks", "minecraft:inside_step_sound_blocks", "minecraft:occludes_vibration_signals", "minecraft:dripstone_replaceable_blocks", "minecraft:cave_vines", "minecraft:moss_replaceable", "minecraft:deepslate_ore_replaceables", "minecraft:lush_ground_replaceable", "minecraft:diamond_ores", "minecraft:iron_ores", "minecraft:lapis_ores", "minecraft:redstone_ores", "minecraft:stone_ore_replaceables", "minecraft:coal_ores", "minecraft:copper_ores", "minecraft:emerald_ores", "minecraft:snow", "minecraft:small_dripleaf_placeable", "minecraft:features_cannot_replace", "minecraft:lava_pool_stone_replaceables", "minecraft:geode_invalid_blocks");
        this.tagRewriter.addEmptyTags(RegistryType.ENTITY, "minecraft:powder_snow_walkable_mobs", "minecraft:axolotl_always_hostiles", "minecraft:axolotl_tempted_hostiles", "minecraft:axolotl_hunt_targets", "minecraft:freeze_hurts_extra_types", "minecraft:freeze_immune_entity_types");
        Types1_17.PARTICLE.filler(this).reader("block", ParticleType.Readers.BLOCK).reader("dust", ParticleType.Readers.DUST).reader("falling_dust", ParticleType.Readers.BLOCK).reader("dust_color_transition", ParticleType.Readers.DUST_TRANSITION).reader("item", ParticleType.Readers.VAR_INT_ITEM).reader("vibration", ParticleType.Readers.VIBRATION);
    }

    @Override
    public void init(UserConnection user) {
        this.addEntityTracker(user, new EntityTrackerBase(user, Entity1_17Types.PLAYER));
        user.put(new InventoryAcknowledgements());
    }

    @Override
    public MappingData getMappingData() {
        return MAPPINGS;
    }

    public EntityPackets getEntityRewriter() {
        return this.entityRewriter;
    }

    public InventoryPackets getItemRewriter() {
        return this.itemRewriter;
    }
}

