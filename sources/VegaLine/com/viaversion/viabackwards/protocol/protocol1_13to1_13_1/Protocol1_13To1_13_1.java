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
import com.viaversion.viaversion.api.minecraft.RegistryType;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_13Types;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.protocol.remapper.ValueTransformer;
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
        this.registerServerbound(ServerboundPackets1_13.TAB_COMPLETE, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.map(Type.STRING, new ValueTransformer<String, String>(Type.STRING){

                    @Override
                    public String transform(PacketWrapper wrapper, String inputValue) {
                        return !inputValue.startsWith("/") ? "/" + inputValue : inputValue;
                    }
                });
            }
        });
        this.registerServerbound(ServerboundPackets1_13.EDIT_BOOK, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.FLAT_ITEM);
                this.map(Type.BOOLEAN);
                this.handler(wrapper -> {
                    Protocol1_13To1_13_1.this.itemRewriter.handleItemToServer(wrapper.get(Type.FLAT_ITEM, 0));
                    wrapper.write(Type.VAR_INT, 0);
                });
            }
        });
        this.registerClientbound(ClientboundPackets1_13.OPEN_WINDOW, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.STRING);
                this.handler(wrapper -> {
                    JsonElement title = wrapper.passthrough(Type.COMPONENT);
                    Protocol1_13To1_13_1.this.translatableRewriter.processText(title);
                    if (ViaBackwards.getConfig().fix1_13FormattedInventoryTitle()) {
                        if (title.isJsonObject() && title.getAsJsonObject().size() == 1 && title.getAsJsonObject().has("translate")) {
                            return;
                        }
                        JsonObject legacyComponent = new JsonObject();
                        legacyComponent.addProperty("text", ChatRewriter.jsonToLegacyText(title.toString()));
                        wrapper.set(Type.COMPONENT, 0, legacyComponent);
                    }
                });
            }
        });
        this.registerClientbound(ClientboundPackets1_13.TAB_COMPLETE, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.map(Type.VAR_INT);
                this.map(Type.VAR_INT);
                this.map(Type.VAR_INT);
                this.handler(wrapper -> {
                    int start = wrapper.get(Type.VAR_INT, 1);
                    wrapper.set(Type.VAR_INT, 1, start - 1);
                    int count = wrapper.get(Type.VAR_INT, 3);
                    for (int i = 0; i < count; ++i) {
                        wrapper.passthrough(Type.STRING);
                        boolean hasTooltip = wrapper.passthrough(Type.BOOLEAN);
                        if (!hasTooltip) continue;
                        wrapper.passthrough(Type.STRING);
                    }
                });
            }
        });
        this.registerClientbound(ClientboundPackets1_13.BOSSBAR, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.UUID);
                this.map(Type.VAR_INT);
                this.handler(wrapper -> {
                    int action = wrapper.get(Type.VAR_INT, 0);
                    if (action == 0 || action == 3) {
                        Protocol1_13To1_13_1.this.translatableRewriter.processText(wrapper.passthrough(Type.COMPONENT));
                        if (action == 0) {
                            wrapper.passthrough(Type.FLOAT);
                            wrapper.passthrough(Type.VAR_INT);
                            wrapper.passthrough(Type.VAR_INT);
                            short flags = wrapper.read(Type.UNSIGNED_BYTE);
                            if ((flags & 4) != 0) {
                                flags = (short)(flags | 2);
                            }
                            wrapper.write(Type.UNSIGNED_BYTE, flags);
                        }
                    }
                });
            }
        });
        this.registerClientbound(ClientboundPackets1_13.ADVANCEMENTS, (PacketWrapper wrapper) -> {
            wrapper.passthrough(Type.BOOLEAN);
            int size = wrapper.passthrough(Type.VAR_INT);
            for (int i = 0; i < size; ++i) {
                wrapper.passthrough(Type.STRING);
                if (wrapper.passthrough(Type.BOOLEAN).booleanValue()) {
                    wrapper.passthrough(Type.STRING);
                }
                if (wrapper.passthrough(Type.BOOLEAN).booleanValue()) {
                    wrapper.passthrough(Type.COMPONENT);
                    wrapper.passthrough(Type.COMPONENT);
                    Item icon = wrapper.passthrough(Type.FLAT_ITEM);
                    this.itemRewriter.handleItemToClient(icon);
                    wrapper.passthrough(Type.VAR_INT);
                    int flags = wrapper.passthrough(Type.INT);
                    if ((flags & 1) != 0) {
                        wrapper.passthrough(Type.STRING);
                    }
                    wrapper.passthrough(Type.FLOAT);
                    wrapper.passthrough(Type.FLOAT);
                }
                wrapper.passthrough(Type.STRING_ARRAY);
                int arrayLength = wrapper.passthrough(Type.VAR_INT);
                for (int array = 0; array < arrayLength; ++array) {
                    wrapper.passthrough(Type.STRING_ARRAY);
                }
            }
        });
        new TagRewriter<ClientboundPackets1_13>(this).register(ClientboundPackets1_13.TAGS, RegistryType.ITEM);
        new StatisticsRewriter<ClientboundPackets1_13>(this).register(ClientboundPackets1_13.STATISTICS);
    }

    @Override
    public void init(UserConnection user) {
        user.addEntityTracker(this.getClass(), new EntityTrackerBase(user, Entity1_13Types.EntityType.PLAYER));
        if (!user.has(ClientWorld.class)) {
            user.put(new ClientWorld(user));
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
}

