/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viabackwards.protocol.protocol1_15_2to1_16;

import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viabackwards.api.rewriters.SoundRewriter;
import com.viaversion.viabackwards.protocol.protocol1_15_2to1_16.chat.TranslatableRewriter1_16;
import com.viaversion.viabackwards.protocol.protocol1_15_2to1_16.data.BackwardsMappings;
import com.viaversion.viabackwards.protocol.protocol1_15_2to1_16.data.CommandRewriter1_16;
import com.viaversion.viabackwards.protocol.protocol1_15_2to1_16.data.WorldNameTracker;
import com.viaversion.viabackwards.protocol.protocol1_15_2to1_16.packets.BlockItemPackets1_16;
import com.viaversion.viabackwards.protocol.protocol1_15_2to1_16.packets.EntityPackets1_16;
import com.viaversion.viabackwards.protocol.protocol1_15_2to1_16.storage.PlayerSneakStorage;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.RegistryType;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_16Types;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.packet.State;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.data.entity.EntityTrackerBase;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.ServerboundPackets1_14;
import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.ClientboundPackets1_15;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.ClientboundPackets1_16;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.ServerboundPackets1_16;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;
import com.viaversion.viaversion.rewriter.StatisticsRewriter;
import com.viaversion.viaversion.rewriter.TagRewriter;
import com.viaversion.viaversion.util.GsonUtil;
import java.util.UUID;

public class Protocol1_15_2To1_16
extends BackwardsProtocol<ClientboundPackets1_16, ClientboundPackets1_15, ServerboundPackets1_16, ServerboundPackets1_14> {
    public static final BackwardsMappings MAPPINGS = new BackwardsMappings();
    private final EntityPackets1_16 entityRewriter = new EntityPackets1_16(this);
    private final BlockItemPackets1_16 blockItemPackets = new BlockItemPackets1_16(this);
    private final TranslatableRewriter1_16 translatableRewriter = new TranslatableRewriter1_16(this);

    public Protocol1_15_2To1_16() {
        super(ClientboundPackets1_16.class, ClientboundPackets1_15.class, ServerboundPackets1_16.class, ServerboundPackets1_14.class);
    }

    @Override
    protected void registerPackets() {
        super.registerPackets();
        this.translatableRewriter.registerBossBar(ClientboundPackets1_16.BOSSBAR);
        this.translatableRewriter.registerCombatEvent(ClientboundPackets1_16.COMBAT_EVENT);
        this.translatableRewriter.registerDisconnect(ClientboundPackets1_16.DISCONNECT);
        this.translatableRewriter.registerTabList(ClientboundPackets1_16.TAB_LIST);
        this.translatableRewriter.registerTitle(ClientboundPackets1_16.TITLE);
        this.translatableRewriter.registerPing();
        new CommandRewriter1_16(this).registerDeclareCommands(ClientboundPackets1_16.DECLARE_COMMANDS);
        this.registerClientbound(State.STATUS, 0, 0, (PacketWrapper wrapper) -> {
            String original = wrapper.passthrough(Type.STRING);
            JsonObject object = GsonUtil.getGson().fromJson(original, JsonObject.class);
            JsonElement description = object.get("description");
            if (description == null) {
                return;
            }
            this.translatableRewriter.processText(description);
            wrapper.set(Type.STRING, 0, object.toString());
        });
        this.registerClientbound(ClientboundPackets1_16.CHAT_MESSAGE, new PacketHandlers(){

            @Override
            public void register() {
                this.handler(wrapper -> Protocol1_15_2To1_16.this.translatableRewriter.processText(wrapper.passthrough(Type.COMPONENT)));
                this.map(Type.BYTE);
                this.map(Type.UUID, Type.NOTHING);
            }
        });
        this.registerClientbound(ClientboundPackets1_16.OPEN_WINDOW, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.map(Type.VAR_INT);
                this.handler(wrapper -> Protocol1_15_2To1_16.this.translatableRewriter.processText(wrapper.passthrough(Type.COMPONENT)));
                this.handler(wrapper -> {
                    int windowType = wrapper.get(Type.VAR_INT, 1);
                    if (windowType == 20) {
                        wrapper.set(Type.VAR_INT, 1, 7);
                    } else if (windowType > 20) {
                        wrapper.set(Type.VAR_INT, 1, --windowType);
                    }
                });
            }
        });
        SoundRewriter<ClientboundPackets1_16> soundRewriter = new SoundRewriter<ClientboundPackets1_16>(this);
        soundRewriter.registerSound(ClientboundPackets1_16.SOUND);
        soundRewriter.registerSound(ClientboundPackets1_16.ENTITY_SOUND);
        soundRewriter.registerNamedSound(ClientboundPackets1_16.NAMED_SOUND);
        soundRewriter.registerStopSound(ClientboundPackets1_16.STOP_SOUND);
        this.registerClientbound(State.LOGIN, 2, 2, (PacketWrapper wrapper) -> {
            UUID uuid = wrapper.read(Type.UUID);
            wrapper.write(Type.STRING, uuid.toString());
        });
        new TagRewriter<ClientboundPackets1_16>(this).register(ClientboundPackets1_16.TAGS, RegistryType.ENTITY);
        new StatisticsRewriter<ClientboundPackets1_16>(this).register(ClientboundPackets1_16.STATISTICS);
        this.registerServerbound(ServerboundPackets1_14.ENTITY_ACTION, (PacketWrapper wrapper) -> {
            wrapper.passthrough(Type.VAR_INT);
            int action = wrapper.passthrough(Type.VAR_INT);
            if (action == 0) {
                wrapper.user().get(PlayerSneakStorage.class).setSneaking(true);
            } else if (action == 1) {
                wrapper.user().get(PlayerSneakStorage.class).setSneaking(false);
            }
        });
        this.registerServerbound(ServerboundPackets1_14.INTERACT_ENTITY, (PacketWrapper wrapper) -> {
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
            wrapper.write(Type.BOOLEAN, wrapper.user().get(PlayerSneakStorage.class).isSneaking());
        });
        this.registerServerbound(ServerboundPackets1_14.PLAYER_ABILITIES, (PacketWrapper wrapper) -> {
            byte flags = wrapper.read(Type.BYTE);
            flags = (byte)(flags & 2);
            wrapper.write(Type.BYTE, flags);
            wrapper.read(Type.FLOAT);
            wrapper.read(Type.FLOAT);
        });
        this.cancelServerbound(ServerboundPackets1_14.UPDATE_JIGSAW_BLOCK);
    }

    @Override
    public void init(UserConnection user) {
        if (!user.has(ClientWorld.class)) {
            user.put(new ClientWorld(user));
        }
        user.put(new PlayerSneakStorage());
        user.put(new WorldNameTracker());
        user.addEntityTracker(this.getClass(), new EntityTrackerBase(user, Entity1_16Types.PLAYER));
    }

    public TranslatableRewriter1_16 getTranslatableRewriter() {
        return this.translatableRewriter;
    }

    @Override
    public BackwardsMappings getMappingData() {
        return MAPPINGS;
    }

    public EntityPackets1_16 getEntityRewriter() {
        return this.entityRewriter;
    }

    public BlockItemPackets1_16 getItemRewriter() {
        return this.blockItemPackets;
    }
}

