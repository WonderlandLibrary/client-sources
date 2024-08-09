/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viabackwards.protocol.protocol1_13to1_13_1;

import com.viaversion.viabackwards.ViaBackwards;
import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viabackwards.api.data.BackwardsMappings;
import com.viaversion.viabackwards.api.rewriters.TranslatableRewriter;
import com.viaversion.viabackwards.protocol.protocol1_13to1_13_1.data.CommandRewriter1_13_1;
import com.viaversion.viabackwards.protocol.protocol1_13to1_13_1.packets.EntityPackets1_13_1;
import com.viaversion.viabackwards.protocol.protocol1_13to1_13_1.packets.InventoryPackets1_13_1;
import com.viaversion.viabackwards.protocol.protocol1_13to1_13_1.packets.WorldPackets1_13_1;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.data.MappingData;
import com.viaversion.viaversion.api.minecraft.RegistryType;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_13Types;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.protocol.remapper.ValueTransformer;
import com.viaversion.viaversion.api.rewriter.EntityRewriter;
import com.viaversion.viaversion.api.rewriter.ItemRewriter;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.data.entity.EntityTrackerBase;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.protocols.protocol1_13_1to1_13.Protocol1_13_1To1_13;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ChatRewriter;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ClientboundPackets1_13;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ServerboundPackets1_13;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;
import com.viaversion.viaversion.rewriter.StatisticsRewriter;
import com.viaversion.viaversion.rewriter.TagRewriter;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class Protocol1_13To1_13_1
extends BackwardsProtocol<ClientboundPackets1_13, ClientboundPackets1_13, ServerboundPackets1_13, ServerboundPackets1_13> {
    public static final BackwardsMappings MAPPINGS = new BackwardsMappings("1.13.2", "1.13", Protocol1_13_1To1_13.class);
    private final EntityPackets1_13_1 entityRewriter = new EntityPackets1_13_1(this);
    private final InventoryPackets1_13_1 itemRewriter = new InventoryPackets1_13_1(this);
    private final TranslatableRewriter<ClientboundPackets1_13> translatableRewriter = new TranslatableRewriter<ClientboundPackets1_13>(this);

    public Protocol1_13To1_13_1() {
        super(ClientboundPackets1_13.class, ClientboundPackets1_13.class, ServerboundPackets1_13.class, ServerboundPackets1_13.class);
    }

    @Override
    protected void registerPackets() {
        super.registerPackets();
        WorldPackets1_13_1.register(this);
        this.translatableRewriter.registerComponentPacket(ClientboundPackets1_13.CHAT_MESSAGE);
        this.translatableRewriter.registerCombatEvent(ClientboundPackets1_13.COMBAT_EVENT);
        this.translatableRewriter.registerDisconnect(ClientboundPackets1_13.DISCONNECT);
        this.translatableRewriter.registerTabList(ClientboundPackets1_13.TAB_LIST);
        this.translatableRewriter.registerTitle(ClientboundPackets1_13.TITLE);
        this.translatableRewriter.registerPing();
        new CommandRewriter1_13_1(this).registerDeclareCommands(ClientboundPackets1_13.DECLARE_COMMANDS);
        this.registerServerbound(ServerboundPackets1_13.TAB_COMPLETE, new PacketHandlers(this){
            final Protocol1_13To1_13_1 this$0;
            {
                this.this$0 = protocol1_13To1_13_1;
            }

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.map(Type.STRING, new ValueTransformer<String, String>(this, Type.STRING){
                    final 1 this$1;
                    {
                        this.this$1 = var1_1;
                        super(type);
                    }

                    @Override
                    public String transform(PacketWrapper packetWrapper, String string) {
                        return !string.startsWith("/") ? "/" + string : string;
                    }

                    @Override
                    public Object transform(PacketWrapper packetWrapper, Object object) throws Exception {
                        return this.transform(packetWrapper, (String)object);
                    }
                });
            }
        });
        this.registerServerbound(ServerboundPackets1_13.EDIT_BOOK, new PacketHandlers(this){
            final Protocol1_13To1_13_1 this$0;
            {
                this.this$0 = protocol1_13To1_13_1;
            }

            @Override
            public void register() {
                this.map(Type.FLAT_ITEM);
                this.map(Type.BOOLEAN);
                this.handler(this::lambda$register$0);
            }

            private void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                Protocol1_13To1_13_1.access$000(this.this$0).handleItemToServer(packetWrapper.get(Type.FLAT_ITEM, 0));
                packetWrapper.write(Type.VAR_INT, 0);
            }
        });
        this.registerClientbound(ClientboundPackets1_13.OPEN_WINDOW, new PacketHandlers(this){
            final Protocol1_13To1_13_1 this$0;
            {
                this.this$0 = protocol1_13To1_13_1;
            }

            @Override
            public void register() {
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.STRING);
                this.handler(this::lambda$register$0);
            }

            private void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                JsonElement jsonElement = packetWrapper.passthrough(Type.COMPONENT);
                Protocol1_13To1_13_1.access$100(this.this$0).processText(jsonElement);
                if (ViaBackwards.getConfig().fix1_13FormattedInventoryTitle()) {
                    if (jsonElement.isJsonObject() && jsonElement.getAsJsonObject().size() == 1 && jsonElement.getAsJsonObject().has("translate")) {
                        return;
                    }
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("text", ChatRewriter.jsonToLegacyText(jsonElement.toString()));
                    packetWrapper.set(Type.COMPONENT, 0, jsonObject);
                }
            }
        });
        this.registerClientbound(ClientboundPackets1_13.TAB_COMPLETE, new PacketHandlers(this){
            final Protocol1_13To1_13_1 this$0;
            {
                this.this$0 = protocol1_13To1_13_1;
            }

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.map(Type.VAR_INT);
                this.map(Type.VAR_INT);
                this.map(Type.VAR_INT);
                this.handler(4::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                int n = packetWrapper.get(Type.VAR_INT, 1);
                packetWrapper.set(Type.VAR_INT, 1, n - 1);
                int n2 = packetWrapper.get(Type.VAR_INT, 3);
                for (int i = 0; i < n2; ++i) {
                    packetWrapper.passthrough(Type.STRING);
                    boolean bl = packetWrapper.passthrough(Type.BOOLEAN);
                    if (!bl) continue;
                    packetWrapper.passthrough(Type.STRING);
                }
            }
        });
        this.registerClientbound(ClientboundPackets1_13.BOSSBAR, new PacketHandlers(this){
            final Protocol1_13To1_13_1 this$0;
            {
                this.this$0 = protocol1_13To1_13_1;
            }

            @Override
            public void register() {
                this.map(Type.UUID);
                this.map(Type.VAR_INT);
                this.handler(this::lambda$register$0);
            }

            private void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                int n = packetWrapper.get(Type.VAR_INT, 0);
                if (n == 0 || n == 3) {
                    Protocol1_13To1_13_1.access$100(this.this$0).processText(packetWrapper.passthrough(Type.COMPONENT));
                    if (n == 0) {
                        packetWrapper.passthrough(Type.FLOAT);
                        packetWrapper.passthrough(Type.VAR_INT);
                        packetWrapper.passthrough(Type.VAR_INT);
                        short s = packetWrapper.read(Type.UNSIGNED_BYTE);
                        if ((s & 4) != 0) {
                            s = (short)(s | 2);
                        }
                        packetWrapper.write(Type.UNSIGNED_BYTE, s);
                    }
                }
            }
        });
        this.registerClientbound(ClientboundPackets1_13.ADVANCEMENTS, this::lambda$registerPackets$0);
        new TagRewriter<ClientboundPackets1_13>(this).register(ClientboundPackets1_13.TAGS, RegistryType.ITEM);
        new StatisticsRewriter<ClientboundPackets1_13>(this).register(ClientboundPackets1_13.STATISTICS);
    }

    @Override
    public void init(UserConnection userConnection) {
        userConnection.addEntityTracker(this.getClass(), new EntityTrackerBase(userConnection, Entity1_13Types.EntityType.PLAYER));
        if (!userConnection.has(ClientWorld.class)) {
            userConnection.put(new ClientWorld(userConnection));
        }
    }

    @Override
    public BackwardsMappings getMappingData() {
        return MAPPINGS;
    }

    public EntityPackets1_13_1 getEntityRewriter() {
        return this.entityRewriter;
    }

    public InventoryPackets1_13_1 getItemRewriter() {
        return this.itemRewriter;
    }

    public TranslatableRewriter<ClientboundPackets1_13> translatableRewriter() {
        return this.translatableRewriter;
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

    private void lambda$registerPackets$0(PacketWrapper packetWrapper) throws Exception {
        packetWrapper.passthrough(Type.BOOLEAN);
        int n = packetWrapper.passthrough(Type.VAR_INT);
        for (int i = 0; i < n; ++i) {
            int n2;
            packetWrapper.passthrough(Type.STRING);
            if (packetWrapper.passthrough(Type.BOOLEAN).booleanValue()) {
                packetWrapper.passthrough(Type.STRING);
            }
            if (packetWrapper.passthrough(Type.BOOLEAN).booleanValue()) {
                packetWrapper.passthrough(Type.COMPONENT);
                packetWrapper.passthrough(Type.COMPONENT);
                Item item = packetWrapper.passthrough(Type.FLAT_ITEM);
                this.itemRewriter.handleItemToClient(item);
                packetWrapper.passthrough(Type.VAR_INT);
                n2 = packetWrapper.passthrough(Type.INT);
                if ((n2 & 1) != 0) {
                    packetWrapper.passthrough(Type.STRING);
                }
                packetWrapper.passthrough(Type.FLOAT);
                packetWrapper.passthrough(Type.FLOAT);
            }
            packetWrapper.passthrough(Type.STRING_ARRAY);
            int n3 = packetWrapper.passthrough(Type.VAR_INT);
            for (n2 = 0; n2 < n3; ++n2) {
                packetWrapper.passthrough(Type.STRING_ARRAY);
            }
        }
    }

    static InventoryPackets1_13_1 access$000(Protocol1_13To1_13_1 protocol1_13To1_13_1) {
        return protocol1_13To1_13_1.itemRewriter;
    }

    static TranslatableRewriter access$100(Protocol1_13To1_13_1 protocol1_13To1_13_1) {
        return protocol1_13To1_13_1.translatableRewriter;
    }
}

