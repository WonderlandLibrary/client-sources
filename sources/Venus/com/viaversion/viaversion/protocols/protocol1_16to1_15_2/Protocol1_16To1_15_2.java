/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_16to1_15_2;

import com.google.common.base.Joiner;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.RegistryType;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_16Types;
import com.viaversion.viaversion.api.platform.providers.ViaProviders;
import com.viaversion.viaversion.api.protocol.AbstractProtocol;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.packet.State;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.rewriter.EntityRewriter;
import com.viaversion.viaversion.api.rewriter.ItemRewriter;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.minecraft.ParticleType;
import com.viaversion.viaversion.api.type.types.version.Types1_16;
import com.viaversion.viaversion.data.entity.EntityTrackerBase;
import com.viaversion.viaversion.libs.gson.JsonArray;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.ServerboundPackets1_14;
import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.ClientboundPackets1_15;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.ClientboundPackets1_16;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.ServerboundPackets1_16;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.data.MappingData;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.data.TranslationMappings;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.metadata.MetadataRewriter1_16To1_15_2;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.packets.EntityPackets;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.packets.InventoryPackets;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.packets.WorldPackets;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.provider.PlayerAbilitiesProvider;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.storage.InventoryTracker1_16;
import com.viaversion.viaversion.rewriter.SoundRewriter;
import com.viaversion.viaversion.rewriter.StatisticsRewriter;
import com.viaversion.viaversion.rewriter.TagRewriter;
import com.viaversion.viaversion.util.GsonUtil;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.UUID;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class Protocol1_16To1_15_2
extends AbstractProtocol<ClientboundPackets1_15, ClientboundPackets1_16, ServerboundPackets1_14, ServerboundPackets1_16> {
    private static final UUID ZERO_UUID = new UUID(0L, 0L);
    public static final MappingData MAPPINGS = new MappingData();
    private final MetadataRewriter1_16To1_15_2 metadataRewriter = new MetadataRewriter1_16To1_15_2(this);
    private final InventoryPackets itemRewriter = new InventoryPackets(this);
    private final TranslationMappings componentRewriter = new TranslationMappings(this);
    private TagRewriter<ClientboundPackets1_15> tagRewriter;

    public Protocol1_16To1_15_2() {
        super(ClientboundPackets1_15.class, ClientboundPackets1_16.class, ServerboundPackets1_14.class, ServerboundPackets1_16.class);
    }

    @Override
    protected void registerPackets() {
        this.metadataRewriter.register();
        this.itemRewriter.register();
        EntityPackets.register(this);
        WorldPackets.register(this);
        this.tagRewriter = new TagRewriter<ClientboundPackets1_15>(this);
        this.tagRewriter.register(ClientboundPackets1_15.TAGS, RegistryType.ENTITY);
        new StatisticsRewriter<ClientboundPackets1_15>(this).register(ClientboundPackets1_15.STATISTICS);
        this.registerClientbound(State.LOGIN, 2, 2, Protocol1_16To1_15_2::lambda$registerPackets$0);
        this.registerClientbound(State.STATUS, 0, 0, Protocol1_16To1_15_2::lambda$registerPackets$1);
        this.registerClientbound(ClientboundPackets1_15.CHAT_MESSAGE, new PacketHandlers(this){
            final Protocol1_16To1_15_2 this$0;
            {
                this.this$0 = protocol1_16To1_15_2;
            }

            @Override
            public void register() {
                this.map(Type.COMPONENT);
                this.map(Type.BYTE);
                this.handler(this::lambda$register$0);
            }

            private void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                Protocol1_16To1_15_2.access$000(this.this$0).processText(packetWrapper.get(Type.COMPONENT, 0));
                packetWrapper.write(Type.UUID, Protocol1_16To1_15_2.access$100());
            }
        });
        this.componentRewriter.registerBossBar(ClientboundPackets1_15.BOSSBAR);
        this.componentRewriter.registerTitle(ClientboundPackets1_15.TITLE);
        this.componentRewriter.registerCombatEvent(ClientboundPackets1_15.COMBAT_EVENT);
        SoundRewriter<ClientboundPackets1_15> soundRewriter = new SoundRewriter<ClientboundPackets1_15>(this);
        soundRewriter.registerSound(ClientboundPackets1_15.SOUND);
        soundRewriter.registerSound(ClientboundPackets1_15.ENTITY_SOUND);
        this.registerServerbound(ServerboundPackets1_16.INTERACT_ENTITY, Protocol1_16To1_15_2::lambda$registerPackets$2);
        if (Via.getConfig().isIgnoreLong1_16ChannelNames()) {
            this.registerServerbound(ServerboundPackets1_16.PLUGIN_MESSAGE, new PacketHandlers(this){
                final Protocol1_16To1_15_2 this$0;
                {
                    this.this$0 = protocol1_16To1_15_2;
                }

                @Override
                public void register() {
                    this.handler(2::lambda$register$0);
                }

                private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                    String string = packetWrapper.passthrough(Type.STRING);
                    if (string.length() > 32) {
                        if (!Via.getConfig().isSuppressConversionWarnings()) {
                            Via.getPlatform().getLogger().warning("Ignoring incoming plugin channel, as it is longer than 32 characters: " + string);
                        }
                        packetWrapper.cancel();
                    } else if (string.equals("minecraft:register") || string.equals("minecraft:unregister")) {
                        String[] stringArray = new String(packetWrapper.read(Type.REMAINING_BYTES), StandardCharsets.UTF_8).split("\u0000");
                        ArrayList<String> arrayList = new ArrayList<String>(stringArray.length);
                        for (String string2 : stringArray) {
                            if (string2.length() > 32) {
                                if (Via.getConfig().isSuppressConversionWarnings()) continue;
                                Via.getPlatform().getLogger().warning("Ignoring incoming plugin channel register of '" + string2 + "', as it is longer than 32 characters");
                                continue;
                            }
                            arrayList.add(string2);
                        }
                        if (arrayList.isEmpty()) {
                            packetWrapper.cancel();
                            return;
                        }
                        packetWrapper.write(Type.REMAINING_BYTES, Joiner.on('\u0000').join(arrayList).getBytes(StandardCharsets.UTF_8));
                    }
                }
            });
        }
        this.registerServerbound(ServerboundPackets1_16.PLAYER_ABILITIES, Protocol1_16To1_15_2::lambda$registerPackets$3);
        this.cancelServerbound(ServerboundPackets1_16.GENERATE_JIGSAW);
        this.cancelServerbound(ServerboundPackets1_16.UPDATE_JIGSAW_BLOCK);
    }

    @Override
    protected void onMappingDataLoaded() {
        int[] nArray = new int[47];
        int n = 0;
        nArray[n++] = 140;
        nArray[n++] = 179;
        nArray[n++] = 264;
        int n2 = 153;
        while (n2 <= 158) {
            nArray[n++] = n2++;
        }
        n2 = 163;
        while (n2 <= 168) {
            nArray[n++] = n2++;
        }
        n2 = 408;
        while (n2 <= 439) {
            nArray[n++] = n2++;
        }
        this.tagRewriter.addTag(RegistryType.BLOCK, "minecraft:wall_post_override", nArray);
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
        Types1_16.PARTICLE.filler(this).reader("block", ParticleType.Readers.BLOCK).reader("dust", ParticleType.Readers.DUST).reader("falling_dust", ParticleType.Readers.BLOCK).reader("item", ParticleType.Readers.VAR_INT_ITEM);
    }

    @Override
    public void register(ViaProviders viaProviders) {
        viaProviders.register(PlayerAbilitiesProvider.class, new PlayerAbilitiesProvider());
    }

    @Override
    public void init(UserConnection userConnection) {
        userConnection.addEntityTracker(this.getClass(), new EntityTrackerBase(userConnection, Entity1_16Types.PLAYER));
        userConnection.put(new InventoryTracker1_16());
    }

    @Override
    public MappingData getMappingData() {
        return MAPPINGS;
    }

    public MetadataRewriter1_16To1_15_2 getEntityRewriter() {
        return this.metadataRewriter;
    }

    public InventoryPackets getItemRewriter() {
        return this.itemRewriter;
    }

    public TranslationMappings getComponentRewriter() {
        return this.componentRewriter;
    }

    @Override
    public ItemRewriter getItemRewriter() {
        return this.getItemRewriter();
    }

    @Override
    public EntityRewriter getEntityRewriter() {
        return this.getEntityRewriter();
    }

    @Override
    public com.viaversion.viaversion.api.data.MappingData getMappingData() {
        return this.getMappingData();
    }

    private static void lambda$registerPackets$3(PacketWrapper packetWrapper) throws Exception {
        packetWrapper.passthrough(Type.BYTE);
        PlayerAbilitiesProvider playerAbilitiesProvider = Via.getManager().getProviders().get(PlayerAbilitiesProvider.class);
        packetWrapper.write(Type.FLOAT, Float.valueOf(playerAbilitiesProvider.getFlyingSpeed(packetWrapper.user())));
        packetWrapper.write(Type.FLOAT, Float.valueOf(playerAbilitiesProvider.getWalkingSpeed(packetWrapper.user())));
    }

    private static void lambda$registerPackets$2(PacketWrapper packetWrapper) throws Exception {
        packetWrapper.passthrough(Type.VAR_INT);
        int n = packetWrapper.passthrough(Type.VAR_INT);
        if (n == 0 || n == 2) {
            if (n == 2) {
                packetWrapper.passthrough(Type.FLOAT);
                packetWrapper.passthrough(Type.FLOAT);
                packetWrapper.passthrough(Type.FLOAT);
            }
            packetWrapper.passthrough(Type.VAR_INT);
        }
        packetWrapper.read(Type.BOOLEAN);
    }

    private static void lambda$registerPackets$1(PacketWrapper packetWrapper) throws Exception {
        String string = packetWrapper.passthrough(Type.STRING);
        JsonObject jsonObject = GsonUtil.getGson().fromJson(string, JsonObject.class);
        JsonObject jsonObject2 = jsonObject.getAsJsonObject("players");
        if (jsonObject2 == null) {
            return;
        }
        JsonArray jsonArray = jsonObject2.getAsJsonArray("sample");
        if (jsonArray == null) {
            return;
        }
        JsonArray jsonArray2 = new JsonArray();
        for (JsonElement jsonElement : jsonArray) {
            JsonObject jsonObject3 = jsonElement.getAsJsonObject();
            String string2 = jsonObject3.getAsJsonPrimitive("name").getAsString();
            if (string2.indexOf(10) == -1) {
                jsonArray2.add(jsonObject3);
                continue;
            }
            String string3 = jsonObject3.getAsJsonPrimitive("id").getAsString();
            for (String string4 : string2.split("\n")) {
                JsonObject jsonObject4 = new JsonObject();
                jsonObject4.addProperty("name", string4);
                jsonObject4.addProperty("id", string3);
                jsonArray2.add(jsonObject4);
            }
        }
        if (jsonArray2.size() != jsonArray.size()) {
            jsonObject2.add("sample", jsonArray2);
            packetWrapper.set(Type.STRING, 0, jsonObject.toString());
        }
    }

    private static void lambda$registerPackets$0(PacketWrapper packetWrapper) throws Exception {
        UUID uUID = UUID.fromString(packetWrapper.read(Type.STRING));
        packetWrapper.write(Type.UUID, uUID);
    }

    static TranslationMappings access$000(Protocol1_16To1_15_2 protocol1_16To1_15_2) {
        return protocol1_16To1_15_2.componentRewriter;
    }

    static UUID access$100() {
        return ZERO_UUID;
    }
}

