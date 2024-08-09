/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viabackwards.protocol.protocol1_16_4to1_17;

import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viabackwards.api.data.BackwardsMappings;
import com.viaversion.viabackwards.api.rewriters.SoundRewriter;
import com.viaversion.viabackwards.api.rewriters.TranslatableRewriter;
import com.viaversion.viabackwards.protocol.protocol1_16_4to1_17.packets.BlockItemPackets1_17;
import com.viaversion.viabackwards.protocol.protocol1_16_4to1_17.packets.EntityPackets1_17;
import com.viaversion.viabackwards.protocol.protocol1_16_4to1_17.storage.PingRequests;
import com.viaversion.viabackwards.protocol.protocol1_16_4to1_17.storage.PlayerLastCursorItem;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.data.MappingData;
import com.viaversion.viaversion.api.minecraft.RegistryType;
import com.viaversion.viaversion.api.minecraft.TagData;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_17Types;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.rewriter.EntityRewriter;
import com.viaversion.viaversion.api.rewriter.ItemRewriter;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.data.entity.EntityTrackerBase;
import com.viaversion.viaversion.libs.fastutil.ints.IntArrayList;
import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.ClientboundPackets1_16_2;
import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.ServerboundPackets1_16_2;
import com.viaversion.viaversion.protocols.protocol1_17to1_16_4.ClientboundPackets1_17;
import com.viaversion.viaversion.protocols.protocol1_17to1_16_4.Protocol1_17To1_16_4;
import com.viaversion.viaversion.protocols.protocol1_17to1_16_4.ServerboundPackets1_17;
import com.viaversion.viaversion.rewriter.StatisticsRewriter;
import com.viaversion.viaversion.rewriter.TagRewriter;
import com.viaversion.viaversion.util.Key;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class Protocol1_16_4To1_17
extends BackwardsProtocol<ClientboundPackets1_17, ClientboundPackets1_16_2, ServerboundPackets1_17, ServerboundPackets1_16_2> {
    public static final BackwardsMappings MAPPINGS = new BackwardsMappings("1.17", "1.16.2", Protocol1_17To1_16_4.class);
    private static final RegistryType[] TAG_REGISTRY_TYPES = new RegistryType[]{RegistryType.BLOCK, RegistryType.ITEM, RegistryType.FLUID, RegistryType.ENTITY};
    private static final int[] EMPTY_ARRAY = new int[0];
    private final EntityPackets1_17 entityRewriter = new EntityPackets1_17(this);
    private final BlockItemPackets1_17 blockItemPackets = new BlockItemPackets1_17(this);
    private final TranslatableRewriter<ClientboundPackets1_17> translatableRewriter = new TranslatableRewriter<ClientboundPackets1_17>(this);

    public Protocol1_16_4To1_17() {
        super(ClientboundPackets1_17.class, ClientboundPackets1_16_2.class, ServerboundPackets1_17.class, ServerboundPackets1_16_2.class);
    }

    @Override
    protected void registerPackets() {
        super.registerPackets();
        this.translatableRewriter.registerComponentPacket(ClientboundPackets1_17.CHAT_MESSAGE);
        this.translatableRewriter.registerBossBar(ClientboundPackets1_17.BOSSBAR);
        this.translatableRewriter.registerDisconnect(ClientboundPackets1_17.DISCONNECT);
        this.translatableRewriter.registerTabList(ClientboundPackets1_17.TAB_LIST);
        this.translatableRewriter.registerOpenWindow(ClientboundPackets1_17.OPEN_WINDOW);
        this.translatableRewriter.registerPing();
        SoundRewriter<ClientboundPackets1_17> soundRewriter = new SoundRewriter<ClientboundPackets1_17>(this);
        soundRewriter.registerSound(ClientboundPackets1_17.SOUND);
        soundRewriter.registerSound(ClientboundPackets1_17.ENTITY_SOUND);
        soundRewriter.registerNamedSound(ClientboundPackets1_17.NAMED_SOUND);
        soundRewriter.registerStopSound(ClientboundPackets1_17.STOP_SOUND);
        TagRewriter<ClientboundPackets1_17> tagRewriter = new TagRewriter<ClientboundPackets1_17>(this);
        this.registerClientbound(ClientboundPackets1_17.TAGS, arg_0 -> Protocol1_16_4To1_17.lambda$registerPackets$0(tagRewriter, arg_0));
        new StatisticsRewriter<ClientboundPackets1_17>(this).register(ClientboundPackets1_17.STATISTICS);
        this.registerClientbound(ClientboundPackets1_17.RESOURCE_PACK, Protocol1_16_4To1_17::lambda$registerPackets$1);
        this.registerClientbound(ClientboundPackets1_17.EXPLOSION, new PacketHandlers(this){
            final Protocol1_16_4To1_17 this$0;
            {
                this.this$0 = protocol1_16_4To1_17;
            }

            @Override
            public void register() {
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.handler(1::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                packetWrapper.write(Type.INT, packetWrapper.read(Type.VAR_INT));
            }
        });
        this.registerClientbound(ClientboundPackets1_17.SPAWN_POSITION, new PacketHandlers(this){
            final Protocol1_16_4To1_17 this$0;
            {
                this.this$0 = protocol1_16_4To1_17;
            }

            @Override
            public void register() {
                this.map(Type.POSITION1_14);
                this.handler(2::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                packetWrapper.read(Type.FLOAT);
            }
        });
        this.registerClientbound(ClientboundPackets1_17.PING, ClientboundPackets1_16_2.PLUGIN_MESSAGE, new PacketHandlers(this){
            final Protocol1_16_4To1_17 this$0;
            {
                this.this$0 = protocol1_16_4To1_17;
            }

            @Override
            public void register() {
                this.handler(3::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                int n = packetWrapper.read(Type.INT);
                packetWrapper.write(Type.STRING, "custom_ping_packet");
                packetWrapper.write(Type.INT, n);
            }
        });
        this.registerServerbound(ServerboundPackets1_16_2.PLUGIN_MESSAGE, Protocol1_16_4To1_17::lambda$registerPackets$2);
        this.registerServerbound(ServerboundPackets1_16_2.CLIENT_SETTINGS, new PacketHandlers(this){
            final Protocol1_16_4To1_17 this$0;
            {
                this.this$0 = protocol1_16_4To1_17;
            }

            @Override
            public void register() {
                this.map(Type.STRING);
                this.map(Type.BYTE);
                this.map(Type.VAR_INT);
                this.map(Type.BOOLEAN);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.VAR_INT);
                this.handler(4::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                packetWrapper.write(Type.BOOLEAN, false);
            }
        });
        this.mergePacket(ClientboundPackets1_17.TITLE_TEXT, ClientboundPackets1_16_2.TITLE, 0);
        this.mergePacket(ClientboundPackets1_17.TITLE_SUBTITLE, ClientboundPackets1_16_2.TITLE, 1);
        this.mergePacket(ClientboundPackets1_17.ACTIONBAR, ClientboundPackets1_16_2.TITLE, 2);
        this.mergePacket(ClientboundPackets1_17.TITLE_TIMES, ClientboundPackets1_16_2.TITLE, 3);
        this.registerClientbound(ClientboundPackets1_17.CLEAR_TITLES, ClientboundPackets1_16_2.TITLE, Protocol1_16_4To1_17::lambda$registerPackets$3);
        this.cancelClientbound(ClientboundPackets1_17.ADD_VIBRATION_SIGNAL);
    }

    @Override
    public void init(UserConnection userConnection) {
        this.addEntityTracker(userConnection, new EntityTrackerBase(userConnection, Entity1_17Types.PLAYER));
        userConnection.put(new PingRequests());
        userConnection.put(new PlayerLastCursorItem());
    }

    @Override
    public BackwardsMappings getMappingData() {
        return MAPPINGS;
    }

    @Override
    public TranslatableRewriter<ClientboundPackets1_17> getTranslatableRewriter() {
        return this.translatableRewriter;
    }

    public void mergePacket(ClientboundPackets1_17 clientboundPackets1_17, ClientboundPackets1_16_2 clientboundPackets1_16_2, int n) {
        this.registerClientbound(clientboundPackets1_17, clientboundPackets1_16_2, arg_0 -> Protocol1_16_4To1_17.lambda$mergePacket$4(n, arg_0));
    }

    public EntityPackets1_17 getEntityRewriter() {
        return this.entityRewriter;
    }

    public BlockItemPackets1_17 getItemRewriter() {
        return this.blockItemPackets;
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
    public MappingData getMappingData() {
        return this.getMappingData();
    }

    private static void lambda$mergePacket$4(int n, PacketWrapper packetWrapper) throws Exception {
        packetWrapper.write(Type.VAR_INT, n);
    }

    private static void lambda$registerPackets$3(PacketWrapper packetWrapper) throws Exception {
        if (packetWrapper.read(Type.BOOLEAN).booleanValue()) {
            packetWrapper.write(Type.VAR_INT, 5);
        } else {
            packetWrapper.write(Type.VAR_INT, 4);
        }
    }

    private static void lambda$registerPackets$2(PacketWrapper packetWrapper) throws Exception {
        String string = packetWrapper.passthrough(Type.STRING);
        if (string.equals("minecraft:custom_ping_packet")) {
            int n = packetWrapper.read(Type.INT);
            packetWrapper.clearPacket();
            packetWrapper.setPacketType(ServerboundPackets1_17.PONG);
            packetWrapper.write(Type.INT, n);
        }
    }

    private static void lambda$registerPackets$1(PacketWrapper packetWrapper) throws Exception {
        packetWrapper.passthrough(Type.STRING);
        packetWrapper.passthrough(Type.STRING);
        packetWrapper.read(Type.BOOLEAN);
        packetWrapper.read(Type.OPTIONAL_COMPONENT);
    }

    private static void lambda$registerPackets$0(TagRewriter tagRewriter, PacketWrapper packetWrapper) throws Exception {
        Object object;
        HashMap hashMap = new HashMap();
        int n = packetWrapper.read(Type.VAR_INT);
        for (int i = 0; i < n; ++i) {
            String string = Key.stripMinecraftNamespace(packetWrapper.read(Type.STRING));
            ArrayList<TagData> arrayList = new ArrayList<TagData>();
            hashMap.put(string, arrayList);
            int n2 = packetWrapper.read(Type.VAR_INT);
            for (int j = 0; j < n2; ++j) {
                object = packetWrapper.read(Type.STRING);
                Object object2 = packetWrapper.read(Type.VAR_INT_ARRAY_PRIMITIVE);
                arrayList.add(new TagData((String)object, (int[])object2));
            }
        }
        for (RegistryType registryType : TAG_REGISTRY_TYPES) {
            List list = (List)hashMap.get(registryType.resourceLocation());
            if (list == null) {
                packetWrapper.write(Type.VAR_INT, 0);
                continue;
            }
            object = tagRewriter.getRewriter(registryType);
            packetWrapper.write(Type.VAR_INT, list.size());
            for (TagData tagData : list) {
                int[] nArray = tagData.entries();
                if (object != null) {
                    IntArrayList intArrayList = new IntArrayList(nArray.length);
                    for (int n3 : nArray) {
                        int n4 = object.rewrite(n3);
                        if (n4 == -1) continue;
                        intArrayList.add(n4);
                    }
                    nArray = intArrayList.toArray(EMPTY_ARRAY);
                }
                packetWrapper.write(Type.STRING, tagData.identifier());
                packetWrapper.write(Type.VAR_INT_ARRAY_PRIMITIVE, nArray);
            }
        }
    }
}

