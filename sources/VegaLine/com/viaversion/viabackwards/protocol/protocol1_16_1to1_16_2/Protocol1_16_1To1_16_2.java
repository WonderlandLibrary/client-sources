/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viabackwards.protocol.protocol1_16_1to1_16_2;

import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viabackwards.api.data.BackwardsMappings;
import com.viaversion.viabackwards.api.rewriters.SoundRewriter;
import com.viaversion.viabackwards.api.rewriters.TranslatableRewriter;
import com.viaversion.viabackwards.protocol.protocol1_16_1to1_16_2.data.CommandRewriter1_16_2;
import com.viaversion.viabackwards.protocol.protocol1_16_1to1_16_2.packets.BlockItemPackets1_16_2;
import com.viaversion.viabackwards.protocol.protocol1_16_1to1_16_2.packets.EntityPackets1_16_2;
import com.viaversion.viabackwards.protocol.protocol1_16_1to1_16_2.storage.BiomeStorage;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.RegistryType;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_16_2Types;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.data.entity.EntityTrackerBase;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.ClientboundPackets1_16_2;
import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.Protocol1_16_2To1_16_1;
import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.ServerboundPackets1_16_2;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.ClientboundPackets1_16;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.ServerboundPackets1_16;
import com.viaversion.viaversion.rewriter.StatisticsRewriter;
import com.viaversion.viaversion.rewriter.TagRewriter;

public class Protocol1_16_1To1_16_2
extends BackwardsProtocol<ClientboundPackets1_16_2, ClientboundPackets1_16, ServerboundPackets1_16_2, ServerboundPackets1_16> {
    public static final BackwardsMappings MAPPINGS = new BackwardsMappings("1.16.2", "1.16", Protocol1_16_2To1_16_1.class);
    private final EntityPackets1_16_2 entityRewriter = new EntityPackets1_16_2(this);
    private final BlockItemPackets1_16_2 blockItemPackets = new BlockItemPackets1_16_2(this);
    private final TranslatableRewriter<ClientboundPackets1_16_2> translatableRewriter = new TranslatableRewriter<ClientboundPackets1_16_2>(this);

    public Protocol1_16_1To1_16_2() {
        super(ClientboundPackets1_16_2.class, ClientboundPackets1_16.class, ServerboundPackets1_16_2.class, ServerboundPackets1_16.class);
    }

    @Override
    protected void registerPackets() {
        super.registerPackets();
        this.translatableRewriter.registerBossBar(ClientboundPackets1_16_2.BOSSBAR);
        this.translatableRewriter.registerCombatEvent(ClientboundPackets1_16_2.COMBAT_EVENT);
        this.translatableRewriter.registerDisconnect(ClientboundPackets1_16_2.DISCONNECT);
        this.translatableRewriter.registerTabList(ClientboundPackets1_16_2.TAB_LIST);
        this.translatableRewriter.registerTitle(ClientboundPackets1_16_2.TITLE);
        this.translatableRewriter.registerOpenWindow(ClientboundPackets1_16_2.OPEN_WINDOW);
        this.translatableRewriter.registerPing();
        new CommandRewriter1_16_2(this).registerDeclareCommands(ClientboundPackets1_16_2.DECLARE_COMMANDS);
        SoundRewriter<ClientboundPackets1_16_2> soundRewriter = new SoundRewriter<ClientboundPackets1_16_2>(this);
        soundRewriter.registerSound(ClientboundPackets1_16_2.SOUND);
        soundRewriter.registerSound(ClientboundPackets1_16_2.ENTITY_SOUND);
        soundRewriter.registerNamedSound(ClientboundPackets1_16_2.NAMED_SOUND);
        soundRewriter.registerStopSound(ClientboundPackets1_16_2.STOP_SOUND);
        this.registerClientbound(ClientboundPackets1_16_2.CHAT_MESSAGE, (PacketWrapper wrapper) -> {
            JsonElement message = wrapper.passthrough(Type.COMPONENT);
            this.translatableRewriter.processText(message);
            byte position = wrapper.passthrough(Type.BYTE);
            if (position == 2) {
                wrapper.clearPacket();
                wrapper.setPacketType(ClientboundPackets1_16.TITLE);
                wrapper.write(Type.VAR_INT, 2);
                wrapper.write(Type.COMPONENT, message);
            }
        });
        this.registerServerbound(ServerboundPackets1_16.RECIPE_BOOK_DATA, (PacketWrapper wrapper) -> {
            int type2 = wrapper.read(Type.VAR_INT);
            if (type2 == 0) {
                wrapper.passthrough(Type.STRING);
                wrapper.setPacketType(ServerboundPackets1_16_2.SEEN_RECIPE);
            } else {
                wrapper.cancel();
                for (int i = 0; i < 3; ++i) {
                    Protocol1_16_1To1_16_2.sendSeenRecipePacket(i, wrapper);
                }
            }
        });
        new TagRewriter<ClientboundPackets1_16_2>(this).register(ClientboundPackets1_16_2.TAGS, RegistryType.ENTITY);
        new StatisticsRewriter<ClientboundPackets1_16_2>(this).register(ClientboundPackets1_16_2.STATISTICS);
    }

    private static void sendSeenRecipePacket(int recipeType, PacketWrapper wrapper) throws Exception {
        boolean open = wrapper.read(Type.BOOLEAN);
        boolean filter = wrapper.read(Type.BOOLEAN);
        PacketWrapper newPacket = wrapper.create(ServerboundPackets1_16_2.RECIPE_BOOK_DATA);
        newPacket.write(Type.VAR_INT, recipeType);
        newPacket.write(Type.BOOLEAN, open);
        newPacket.write(Type.BOOLEAN, filter);
        newPacket.sendToServer(Protocol1_16_1To1_16_2.class);
    }

    @Override
    public void init(UserConnection user) {
        user.put(new BiomeStorage());
        user.addEntityTracker(this.getClass(), new EntityTrackerBase(user, Entity1_16_2Types.PLAYER));
    }

    @Override
    public TranslatableRewriter<ClientboundPackets1_16_2> getTranslatableRewriter() {
        return this.translatableRewriter;
    }

    @Override
    public BackwardsMappings getMappingData() {
        return MAPPINGS;
    }

    public EntityPackets1_16_2 getEntityRewriter() {
        return this.entityRewriter;
    }

    public BlockItemPackets1_16_2 getItemRewriter() {
        return this.blockItemPackets;
    }
}

