/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viabackwards.protocol.protocol1_14_4to1_15;

import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viabackwards.api.data.BackwardsMappings;
import com.viaversion.viabackwards.api.rewriters.SoundRewriter;
import com.viaversion.viabackwards.api.rewriters.TranslatableRewriter;
import com.viaversion.viabackwards.protocol.protocol1_14_4to1_15.data.ImmediateRespawn;
import com.viaversion.viabackwards.protocol.protocol1_14_4to1_15.packets.BlockItemPackets1_15;
import com.viaversion.viabackwards.protocol.protocol1_14_4to1_15.packets.EntityPackets1_15;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.data.MappingData;
import com.viaversion.viaversion.api.minecraft.RegistryType;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_15Types;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.rewriter.EntityRewriter;
import com.viaversion.viaversion.api.rewriter.ItemRewriter;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.data.entity.EntityTrackerBase;
import com.viaversion.viaversion.protocols.protocol1_14_4to1_14_3.ClientboundPackets1_14_4;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.ServerboundPackets1_14;
import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.ClientboundPackets1_15;
import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.Protocol1_15To1_14_4;
import com.viaversion.viaversion.rewriter.StatisticsRewriter;
import com.viaversion.viaversion.rewriter.TagRewriter;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class Protocol1_14_4To1_15
extends BackwardsProtocol<ClientboundPackets1_15, ClientboundPackets1_14_4, ServerboundPackets1_14, ServerboundPackets1_14> {
    public static final BackwardsMappings MAPPINGS = new BackwardsMappings("1.15", "1.14", Protocol1_15To1_14_4.class);
    private final EntityPackets1_15 entityRewriter = new EntityPackets1_15(this);
    private final BlockItemPackets1_15 blockItemPackets = new BlockItemPackets1_15(this);
    private final TranslatableRewriter<ClientboundPackets1_15> translatableRewriter = new TranslatableRewriter<ClientboundPackets1_15>(this);

    public Protocol1_14_4To1_15() {
        super(ClientboundPackets1_15.class, ClientboundPackets1_14_4.class, ServerboundPackets1_14.class, ServerboundPackets1_14.class);
    }

    @Override
    protected void registerPackets() {
        super.registerPackets();
        this.translatableRewriter.registerBossBar(ClientboundPackets1_15.BOSSBAR);
        this.translatableRewriter.registerComponentPacket(ClientboundPackets1_15.CHAT_MESSAGE);
        this.translatableRewriter.registerCombatEvent(ClientboundPackets1_15.COMBAT_EVENT);
        this.translatableRewriter.registerDisconnect(ClientboundPackets1_15.DISCONNECT);
        this.translatableRewriter.registerOpenWindow(ClientboundPackets1_15.OPEN_WINDOW);
        this.translatableRewriter.registerTabList(ClientboundPackets1_15.TAB_LIST);
        this.translatableRewriter.registerTitle(ClientboundPackets1_15.TITLE);
        this.translatableRewriter.registerPing();
        SoundRewriter<ClientboundPackets1_15> soundRewriter = new SoundRewriter<ClientboundPackets1_15>(this);
        soundRewriter.registerSound(ClientboundPackets1_15.SOUND);
        soundRewriter.registerSound(ClientboundPackets1_15.ENTITY_SOUND);
        soundRewriter.registerNamedSound(ClientboundPackets1_15.NAMED_SOUND);
        soundRewriter.registerStopSound(ClientboundPackets1_15.STOP_SOUND);
        this.registerClientbound(ClientboundPackets1_15.EXPLOSION, new PacketHandlers(this){
            final Protocol1_14_4To1_15 this$0;
            {
                this.this$0 = protocol1_14_4To1_15;
            }

            @Override
            public void register() {
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.handler(this::lambda$register$0);
            }

            private int toEffectCoordinate(float f) {
                return (int)(f * 8.0f);
            }

            private void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                PacketWrapper packetWrapper2 = packetWrapper.create(ClientboundPackets1_14_4.SOUND);
                packetWrapper2.write(Type.VAR_INT, 243);
                packetWrapper2.write(Type.VAR_INT, 4);
                packetWrapper2.write(Type.INT, this.toEffectCoordinate(packetWrapper.get(Type.FLOAT, 0).floatValue()));
                packetWrapper2.write(Type.INT, this.toEffectCoordinate(packetWrapper.get(Type.FLOAT, 1).floatValue()));
                packetWrapper2.write(Type.INT, this.toEffectCoordinate(packetWrapper.get(Type.FLOAT, 2).floatValue()));
                packetWrapper2.write(Type.FLOAT, Float.valueOf(4.0f));
                packetWrapper2.write(Type.FLOAT, Float.valueOf(1.0f));
                packetWrapper2.send(Protocol1_14_4To1_15.class);
            }
        });
        new TagRewriter<ClientboundPackets1_15>(this).register(ClientboundPackets1_15.TAGS, RegistryType.ENTITY);
        new StatisticsRewriter<ClientboundPackets1_15>(this).register(ClientboundPackets1_15.STATISTICS);
    }

    @Override
    public void init(UserConnection userConnection) {
        userConnection.put(new ImmediateRespawn());
        userConnection.addEntityTracker(this.getClass(), new EntityTrackerBase(userConnection, Entity1_15Types.PLAYER));
    }

    @Override
    public BackwardsMappings getMappingData() {
        return MAPPINGS;
    }

    public EntityPackets1_15 getEntityRewriter() {
        return this.entityRewriter;
    }

    public BlockItemPackets1_15 getItemRewriter() {
        return this.blockItemPackets;
    }

    @Override
    public TranslatableRewriter<ClientboundPackets1_15> getTranslatableRewriter() {
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
}

