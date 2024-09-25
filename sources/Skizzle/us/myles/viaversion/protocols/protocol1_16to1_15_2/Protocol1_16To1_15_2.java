/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Joiner
 */
package us.myles.ViaVersion.protocols.protocol1_16to1_15_2;

import com.google.common.base.Joiner;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.UUID;
import us.myles.ViaVersion.api.Via;
import us.myles.ViaVersion.api.data.UserConnection;
import us.myles.ViaVersion.api.protocol.Protocol;
import us.myles.ViaVersion.api.remapper.PacketRemapper;
import us.myles.ViaVersion.api.rewriters.RegistryType;
import us.myles.ViaVersion.api.rewriters.SoundRewriter;
import us.myles.ViaVersion.api.rewriters.StatisticsRewriter;
import us.myles.ViaVersion.api.rewriters.TagRewriter;
import us.myles.ViaVersion.api.type.Type;
import us.myles.ViaVersion.packets.State;
import us.myles.ViaVersion.protocols.protocol1_14to1_13_2.ServerboundPackets1_14;
import us.myles.ViaVersion.protocols.protocol1_15to1_14_4.ClientboundPackets1_15;
import us.myles.ViaVersion.protocols.protocol1_16to1_15_2.ClientboundPackets1_16;
import us.myles.ViaVersion.protocols.protocol1_16to1_15_2.ServerboundPackets1_16;
import us.myles.ViaVersion.protocols.protocol1_16to1_15_2.data.MappingData;
import us.myles.ViaVersion.protocols.protocol1_16to1_15_2.data.TranslationMappings;
import us.myles.ViaVersion.protocols.protocol1_16to1_15_2.metadata.MetadataRewriter1_16To1_15_2;
import us.myles.ViaVersion.protocols.protocol1_16to1_15_2.packets.EntityPackets;
import us.myles.ViaVersion.protocols.protocol1_16to1_15_2.packets.InventoryPackets;
import us.myles.ViaVersion.protocols.protocol1_16to1_15_2.packets.WorldPackets;
import us.myles.ViaVersion.protocols.protocol1_16to1_15_2.storage.EntityTracker1_16;
import us.myles.ViaVersion.protocols.protocol1_16to1_15_2.storage.InventoryTracker1_16;
import us.myles.ViaVersion.util.GsonUtil;
import us.myles.viaversion.libs.gson.JsonArray;
import us.myles.viaversion.libs.gson.JsonElement;
import us.myles.viaversion.libs.gson.JsonObject;

public class Protocol1_16To1_15_2
extends Protocol<ClientboundPackets1_15, ClientboundPackets1_16, ServerboundPackets1_14, ServerboundPackets1_16> {
    private static final UUID ZERO_UUID = new UUID(0L, 0L);
    public static final MappingData MAPPINGS = new MappingData();
    private TagRewriter tagRewriter;

    public Protocol1_16To1_15_2() {
        super(ClientboundPackets1_15.class, ClientboundPackets1_16.class, ServerboundPackets1_14.class, ServerboundPackets1_16.class);
    }

    @Override
    protected void registerPackets() {
        MetadataRewriter1_16To1_15_2 metadataRewriter = new MetadataRewriter1_16To1_15_2(this);
        EntityPackets.register(this);
        WorldPackets.register(this);
        InventoryPackets.register(this);
        this.tagRewriter = new TagRewriter(this, metadataRewriter::getNewEntityId);
        this.tagRewriter.register(ClientboundPackets1_15.TAGS);
        new StatisticsRewriter(this, metadataRewriter::getNewEntityId).register(ClientboundPackets1_15.STATISTICS);
        this.registerOutgoing(State.LOGIN, 2, 2, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.handler(wrapper -> {
                    UUID uuid = UUID.fromString(wrapper.read(Type.STRING));
                    wrapper.write(Type.UUID_INT_ARRAY, uuid);
                });
            }
        });
        this.registerOutgoing(State.STATUS, 0, 0, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.handler(wrapper -> {
                    String original = wrapper.passthrough(Type.STRING);
                    JsonObject object = GsonUtil.getGson().fromJson(original, JsonObject.class);
                    JsonObject players = object.getAsJsonObject("players");
                    if (players == null) {
                        return;
                    }
                    JsonArray sample = players.getAsJsonArray("sample");
                    if (sample == null) {
                        return;
                    }
                    JsonArray splitSamples = new JsonArray();
                    for (JsonElement element : sample) {
                        JsonObject playerInfo = element.getAsJsonObject();
                        String name = playerInfo.getAsJsonPrimitive("name").getAsString();
                        if (name.indexOf(10) == -1) {
                            splitSamples.add(playerInfo);
                            continue;
                        }
                        String id = playerInfo.getAsJsonPrimitive("id").getAsString();
                        for (String s : name.split("\n")) {
                            JsonObject newSample = new JsonObject();
                            newSample.addProperty("name", s);
                            newSample.addProperty("id", id);
                            splitSamples.add(newSample);
                        }
                    }
                    if (splitSamples.size() != sample.size()) {
                        players.add("sample", splitSamples);
                        wrapper.set(Type.STRING, 0, object.toString());
                    }
                });
            }
        });
        final TranslationMappings componentRewriter = new TranslationMappings(this);
        this.registerOutgoing(ClientboundPackets1_15.CHAT_MESSAGE, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.COMPONENT);
                this.map(Type.BYTE);
                this.handler(wrapper -> {
                    componentRewriter.processText(wrapper.get(Type.COMPONENT, 0));
                    wrapper.write(Type.UUID, ZERO_UUID);
                });
            }
        });
        componentRewriter.registerBossBar(ClientboundPackets1_15.BOSSBAR);
        componentRewriter.registerTitle(ClientboundPackets1_15.TITLE);
        componentRewriter.registerCombatEvent(ClientboundPackets1_15.COMBAT_EVENT);
        SoundRewriter soundRewriter = new SoundRewriter(this);
        soundRewriter.registerSound(ClientboundPackets1_15.SOUND);
        soundRewriter.registerSound(ClientboundPackets1_15.ENTITY_SOUND);
        this.registerIncoming(ServerboundPackets1_16.INTERACT_ENTITY, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.handler(wrapper -> {
                    wrapper.passthrough(Type.VAR_INT);
                    int action = wrapper.passthrough(Type.VAR_INT);
                    if (action == 0 || action == 2) {
                        if (action == 2) {
                            wrapper.passthrough(Type.FLOAT);
                            wrapper.passthrough(Type.FLOAT);
                            wrapper.passthrough(Type.FLOAT);
                        }
                        wrapper.passthrough(Type.VAR_INT);
                    }
                    wrapper.read(Type.BOOLEAN);
                });
            }
        });
        if (Via.getConfig().isIgnoreLong1_16ChannelNames()) {
            this.registerIncoming(ServerboundPackets1_16.PLUGIN_MESSAGE, new PacketRemapper(){

                @Override
                public void registerMap() {
                    this.handler(wrapper -> {
                        String channel = wrapper.passthrough(Type.STRING);
                        if (channel.length() > 32) {
                            if (!Via.getConfig().isSuppressConversionWarnings()) {
                                Via.getPlatform().getLogger().warning("Ignoring incoming plugin channel, as it is longer than 32 characters: " + channel);
                            }
                            wrapper.cancel();
                        } else if (channel.equals("minecraft:register") || channel.equals("minecraft:unregister")) {
                            String[] channels = new String(wrapper.read(Type.REMAINING_BYTES), StandardCharsets.UTF_8).split("\u0000");
                            ArrayList<String> checkedChannels = new ArrayList<String>(channels.length);
                            for (String registeredChannel : channels) {
                                if (registeredChannel.length() > 32) {
                                    if (Via.getConfig().isSuppressConversionWarnings()) continue;
                                    Via.getPlatform().getLogger().warning("Ignoring incoming plugin channel register of '" + registeredChannel + "', as it is longer than 32 characters");
                                    continue;
                                }
                                checkedChannels.add(registeredChannel);
                            }
                            if (checkedChannels.isEmpty()) {
                                wrapper.cancel();
                                return;
                            }
                            wrapper.write(Type.REMAINING_BYTES, Joiner.on((char)'\u0000').join(checkedChannels).getBytes(StandardCharsets.UTF_8));
                        }
                    });
                }
            });
        }
        this.registerIncoming(ServerboundPackets1_16.PLAYER_ABILITIES, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.handler(wrapper -> {
                    wrapper.passthrough(Type.BYTE);
                    wrapper.write(Type.FLOAT, Float.valueOf(0.05f));
                    wrapper.write(Type.FLOAT, Float.valueOf(0.1f));
                });
            }
        });
        this.cancelIncoming(ServerboundPackets1_16.GENERATE_JIGSAW);
        this.cancelIncoming(ServerboundPackets1_16.UPDATE_JIGSAW_BLOCK);
    }

    @Override
    protected void onMappingDataLoaded() {
        int[] wallPostOverrideTag = new int[47];
        int arrayIndex = 0;
        wallPostOverrideTag[arrayIndex++] = 140;
        wallPostOverrideTag[arrayIndex++] = 179;
        wallPostOverrideTag[arrayIndex++] = 264;
        int i = 153;
        while (i <= 158) {
            wallPostOverrideTag[arrayIndex++] = i++;
        }
        i = 163;
        while (i <= 168) {
            wallPostOverrideTag[arrayIndex++] = i++;
        }
        i = 408;
        while (i <= 439) {
            wallPostOverrideTag[arrayIndex++] = i++;
        }
        this.tagRewriter.addTag(RegistryType.BLOCK, "minecraft:wall_post_override", wallPostOverrideTag);
        this.tagRewriter.addTag(RegistryType.BLOCK, "minecraft:beacon_base_blocks", 133, 134, 148, 265);
        this.tagRewriter.addTag(RegistryType.BLOCK, "minecraft:climbable", 160, 241, 658);
        this.tagRewriter.addTag(RegistryType.BLOCK, "minecraft:fire", 142);
        this.tagRewriter.addTag(RegistryType.BLOCK, "minecraft:campfires", 679);
        this.tagRewriter.addTag(RegistryType.BLOCK, "minecraft:fence_gates", 242, 467, 468, 469, 470, 471);
        this.tagRewriter.addTag(RegistryType.BLOCK, "minecraft:unstable_bottom_center", 242, 467, 468, 469, 470, 471);
        this.tagRewriter.addTag(RegistryType.BLOCK, "minecraft:wooden_trapdoors", 193, 194, 195, 196, 197, 198);
        this.tagRewriter.addTag(RegistryType.ITEM, "minecraft:wooden_trapdoors", 215, 216, 217, 218, 219, 220);
        this.tagRewriter.addTag(RegistryType.ITEM, "minecraft:beacon_payment_items", 529, 530, 531, 760);
        this.tagRewriter.addTag(RegistryType.ENTITY, "minecraft:impact_projectiles", 2, 72, 71, 37, 69, 79, 83, 15, 93);
        this.tagRewriter.addEmptyTag(RegistryType.BLOCK, "minecraft:guarded_by_piglins");
        this.tagRewriter.addEmptyTag(RegistryType.BLOCK, "minecraft:soul_speed_blocks");
        this.tagRewriter.addEmptyTag(RegistryType.BLOCK, "minecraft:soul_fire_base_blocks");
        this.tagRewriter.addEmptyTag(RegistryType.BLOCK, "minecraft:non_flammable_wood");
        this.tagRewriter.addEmptyTag(RegistryType.ITEM, "minecraft:non_flammable_wood");
        this.tagRewriter.addEmptyTags(RegistryType.BLOCK, "minecraft:bamboo_plantable_on", "minecraft:beds", "minecraft:bee_growables", "minecraft:beehives", "minecraft:coral_plants", "minecraft:crops", "minecraft:dragon_immune", "minecraft:flowers", "minecraft:portals", "minecraft:shulker_boxes", "minecraft:small_flowers", "minecraft:tall_flowers", "minecraft:trapdoors", "minecraft:underwater_bonemeals", "minecraft:wither_immune", "minecraft:wooden_fences", "minecraft:wooden_trapdoors");
        this.tagRewriter.addEmptyTags(RegistryType.ENTITY, "minecraft:arrows", "minecraft:beehive_inhabitors", "minecraft:raiders", "minecraft:skeletons");
        this.tagRewriter.addEmptyTags(RegistryType.ITEM, "minecraft:beds", "minecraft:coals", "minecraft:fences", "minecraft:flowers", "minecraft:lectern_books", "minecraft:music_discs", "minecraft:small_flowers", "minecraft:tall_flowers", "minecraft:trapdoors", "minecraft:walls", "minecraft:wooden_fences");
    }

    @Override
    public void init(UserConnection userConnection) {
        userConnection.put(new EntityTracker1_16(userConnection));
        userConnection.put(new InventoryTracker1_16(userConnection));
    }

    @Override
    public MappingData getMappingData() {
        return MAPPINGS;
    }
}

