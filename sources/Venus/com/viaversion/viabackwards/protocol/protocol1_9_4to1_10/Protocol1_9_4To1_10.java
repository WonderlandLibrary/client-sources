/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viabackwards.protocol.protocol1_9_4to1_10;

import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viabackwards.api.data.BackwardsMappings;
import com.viaversion.viabackwards.api.rewriters.SoundRewriter;
import com.viaversion.viabackwards.protocol.protocol1_9_4to1_10.packets.BlockItemPackets1_10;
import com.viaversion.viabackwards.protocol.protocol1_9_4to1_10.packets.EntityPackets1_10;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.data.MappingData;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_10Types;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.protocol.remapper.ValueTransformer;
import com.viaversion.viaversion.api.rewriter.EntityRewriter;
import com.viaversion.viaversion.api.rewriter.ItemRewriter;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.data.entity.EntityTrackerBase;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.ClientboundPackets1_9_3;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.ServerboundPackets1_9_3;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class Protocol1_9_4To1_10
extends BackwardsProtocol<ClientboundPackets1_9_3, ClientboundPackets1_9_3, ServerboundPackets1_9_3, ServerboundPackets1_9_3> {
    public static final BackwardsMappings MAPPINGS = new BackwardsMappings("1.10", "1.9.4");
    private static final ValueTransformer<Float, Short> TO_OLD_PITCH = new ValueTransformer<Float, Short>((Type)Type.UNSIGNED_BYTE){

        @Override
        public Short transform(PacketWrapper packetWrapper, Float f) throws Exception {
            return (short)Math.round(f.floatValue() * 63.5f);
        }

        @Override
        public Object transform(PacketWrapper packetWrapper, Object object) throws Exception {
            return this.transform(packetWrapper, (Float)object);
        }
    };
    private final EntityPackets1_10 entityPackets = new EntityPackets1_10(this);
    private final BlockItemPackets1_10 blockItemPackets = new BlockItemPackets1_10(this);

    public Protocol1_9_4To1_10() {
        super(ClientboundPackets1_9_3.class, ClientboundPackets1_9_3.class, ServerboundPackets1_9_3.class, ServerboundPackets1_9_3.class);
    }

    @Override
    protected void registerPackets() {
        this.entityPackets.register();
        this.blockItemPackets.register();
        SoundRewriter<ClientboundPackets1_9_3> soundRewriter = new SoundRewriter<ClientboundPackets1_9_3>(this);
        this.registerClientbound(ClientboundPackets1_9_3.NAMED_SOUND, new PacketHandlers(this, soundRewriter){
            final SoundRewriter val$soundRewriter;
            final Protocol1_9_4To1_10 this$0;
            {
                this.this$0 = protocol1_9_4To1_10;
                this.val$soundRewriter = soundRewriter;
            }

            @Override
            public void register() {
                this.map(Type.STRING);
                this.map(Type.VAR_INT);
                this.map(Type.INT);
                this.map(Type.INT);
                this.map(Type.INT);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT, Protocol1_9_4To1_10.access$000());
                this.handler(this.val$soundRewriter.getNamedSoundHandler());
            }
        });
        this.registerClientbound(ClientboundPackets1_9_3.SOUND, new PacketHandlers(this, soundRewriter){
            final SoundRewriter val$soundRewriter;
            final Protocol1_9_4To1_10 this$0;
            {
                this.this$0 = protocol1_9_4To1_10;
                this.val$soundRewriter = soundRewriter;
            }

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.map(Type.VAR_INT);
                this.map(Type.INT);
                this.map(Type.INT);
                this.map(Type.INT);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT, Protocol1_9_4To1_10.access$000());
                this.handler(this.val$soundRewriter.getSoundHandler());
            }
        });
        this.registerServerbound(ServerboundPackets1_9_3.RESOURCE_PACK_STATUS, new PacketHandlers(this){
            final Protocol1_9_4To1_10 this$0;
            {
                this.this$0 = protocol1_9_4To1_10;
            }

            @Override
            public void register() {
                this.map(Type.STRING, Type.NOTHING);
                this.map(Type.VAR_INT);
            }
        });
    }

    @Override
    public void init(UserConnection userConnection) {
        if (!userConnection.has(ClientWorld.class)) {
            userConnection.put(new ClientWorld(userConnection));
        }
        userConnection.addEntityTracker(this.getClass(), new EntityTrackerBase(userConnection, Entity1_10Types.EntityType.PLAYER));
    }

    @Override
    public BackwardsMappings getMappingData() {
        return MAPPINGS;
    }

    public EntityPackets1_10 getEntityRewriter() {
        return this.entityPackets;
    }

    public BlockItemPackets1_10 getItemRewriter() {
        return this.blockItemPackets;
    }

    @Override
    public boolean hasMappingDataToLoad() {
        return false;
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

    static ValueTransformer access$000() {
        return TO_OLD_PITCH;
    }
}

