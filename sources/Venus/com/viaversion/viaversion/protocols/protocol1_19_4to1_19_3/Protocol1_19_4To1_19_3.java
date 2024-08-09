/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_19_4to1_19_3;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_19_4Types;
import com.viaversion.viaversion.api.protocol.AbstractProtocol;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.rewriter.EntityRewriter;
import com.viaversion.viaversion.api.rewriter.ItemRewriter;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.minecraft.ParticleType;
import com.viaversion.viaversion.api.type.types.version.Types1_19_4;
import com.viaversion.viaversion.data.entity.EntityTrackerBase;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ChatRewriter;
import com.viaversion.viaversion.protocols.protocol1_19_3to1_19_1.ClientboundPackets1_19_3;
import com.viaversion.viaversion.protocols.protocol1_19_3to1_19_1.ServerboundPackets1_19_3;
import com.viaversion.viaversion.protocols.protocol1_19_4to1_19_3.ClientboundPackets1_19_4;
import com.viaversion.viaversion.protocols.protocol1_19_4to1_19_3.ServerboundPackets1_19_4;
import com.viaversion.viaversion.protocols.protocol1_19_4to1_19_3.data.MappingData;
import com.viaversion.viaversion.protocols.protocol1_19_4to1_19_3.packets.EntityPackets;
import com.viaversion.viaversion.protocols.protocol1_19_4to1_19_3.packets.InventoryPackets;
import com.viaversion.viaversion.protocols.protocol1_19_4to1_19_3.storage.PlayerVehicleTracker;
import com.viaversion.viaversion.rewriter.CommandRewriter;
import com.viaversion.viaversion.rewriter.SoundRewriter;
import com.viaversion.viaversion.rewriter.StatisticsRewriter;
import com.viaversion.viaversion.rewriter.TagRewriter;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class Protocol1_19_4To1_19_3
extends AbstractProtocol<ClientboundPackets1_19_3, ClientboundPackets1_19_4, ServerboundPackets1_19_3, ServerboundPackets1_19_4> {
    public static final MappingData MAPPINGS = new MappingData();
    private final EntityPackets entityRewriter = new EntityPackets(this);
    private final InventoryPackets itemRewriter = new InventoryPackets(this);

    public Protocol1_19_4To1_19_3() {
        super(ClientboundPackets1_19_3.class, ClientboundPackets1_19_4.class, ServerboundPackets1_19_3.class, ServerboundPackets1_19_4.class);
    }

    @Override
    protected void registerPackets() {
        super.registerPackets();
        new TagRewriter<ClientboundPackets1_19_3>(this).registerGeneric(ClientboundPackets1_19_3.TAGS);
        new StatisticsRewriter<ClientboundPackets1_19_3>(this).register(ClientboundPackets1_19_3.STATISTICS);
        SoundRewriter<ClientboundPackets1_19_3> soundRewriter = new SoundRewriter<ClientboundPackets1_19_3>(this);
        soundRewriter.registerSound(ClientboundPackets1_19_3.ENTITY_SOUND);
        soundRewriter.register1_19_3Sound(ClientboundPackets1_19_3.SOUND);
        new CommandRewriter<ClientboundPackets1_19_3>(this, (Protocol)this){
            final Protocol1_19_4To1_19_3 this$0;
            {
                this.this$0 = protocol1_19_4To1_19_3;
                super(protocol);
            }

            @Override
            public void handleArgument(PacketWrapper packetWrapper, String string) throws Exception {
                if (string.equals("minecraft:time")) {
                    packetWrapper.write(Type.INT, 0);
                } else {
                    super.handleArgument(packetWrapper, string);
                }
            }
        }.registerDeclareCommands1_19(ClientboundPackets1_19_3.DECLARE_COMMANDS);
        this.registerClientbound(ClientboundPackets1_19_3.SERVER_DATA, Protocol1_19_4To1_19_3::lambda$registerPackets$0);
    }

    @Override
    protected void onMappingDataLoaded() {
        super.onMappingDataLoaded();
        Entity1_19_4Types.initialize(this);
        Types1_19_4.PARTICLE.filler(this).reader("block", ParticleType.Readers.BLOCK).reader("block_marker", ParticleType.Readers.BLOCK).reader("dust", ParticleType.Readers.DUST).reader("falling_dust", ParticleType.Readers.BLOCK).reader("dust_color_transition", ParticleType.Readers.DUST_TRANSITION).reader("item", ParticleType.Readers.VAR_INT_ITEM).reader("vibration", ParticleType.Readers.VIBRATION1_19).reader("sculk_charge", ParticleType.Readers.SCULK_CHARGE).reader("shriek", ParticleType.Readers.SHRIEK);
    }

    @Override
    public void init(UserConnection userConnection) {
        this.addEntityTracker(userConnection, new EntityTrackerBase(userConnection, Entity1_19_4Types.PLAYER));
        userConnection.put(new PlayerVehicleTracker(userConnection));
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

    private static void lambda$registerPackets$0(PacketWrapper packetWrapper) throws Exception {
        JsonElement jsonElement = packetWrapper.read(Type.OPTIONAL_COMPONENT);
        if (jsonElement != null) {
            packetWrapper.write(Type.COMPONENT, jsonElement);
        } else {
            packetWrapper.write(Type.COMPONENT, ChatRewriter.emptyComponent());
        }
        String string = packetWrapper.read(Type.OPTIONAL_STRING);
        byte[] byArray = null;
        if (string != null && string.startsWith("data:image/png;base64,")) {
            byArray = Base64.getDecoder().decode(string.substring(22).getBytes(StandardCharsets.UTF_8));
        }
        packetWrapper.write(Type.OPTIONAL_BYTE_ARRAY_PRIMITIVE, byArray);
    }
}

