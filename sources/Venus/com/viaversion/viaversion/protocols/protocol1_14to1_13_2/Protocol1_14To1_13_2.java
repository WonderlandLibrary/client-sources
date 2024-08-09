/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_14to1_13_2;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.AbstractProtocol;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.rewriter.EntityRewriter;
import com.viaversion.viaversion.api.rewriter.ItemRewriter;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.minecraft.ParticleType;
import com.viaversion.viaversion.api.type.types.version.Types1_13_2;
import com.viaversion.viaversion.api.type.types.version.Types1_14;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ClientboundPackets1_13;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ServerboundPackets1_13;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.ClientboundPackets1_14;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.ServerboundPackets1_14;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.data.ComponentRewriter1_14;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.data.MappingData;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.metadata.MetadataRewriter1_14To1_13_2;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.packets.EntityPackets;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.packets.InventoryPackets;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.packets.PlayerPackets;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.packets.WorldPackets;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.storage.EntityTracker1_14;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;
import com.viaversion.viaversion.rewriter.CommandRewriter;
import com.viaversion.viaversion.rewriter.SoundRewriter;
import com.viaversion.viaversion.rewriter.StatisticsRewriter;
import org.checkerframework.checker.nullness.qual.Nullable;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class Protocol1_14To1_13_2
extends AbstractProtocol<ClientboundPackets1_13, ClientboundPackets1_14, ServerboundPackets1_13, ServerboundPackets1_14> {
    public static final MappingData MAPPINGS = new MappingData();
    private final MetadataRewriter1_14To1_13_2 metadataRewriter = new MetadataRewriter1_14To1_13_2(this);
    private final InventoryPackets itemRewriter = new InventoryPackets(this);

    public Protocol1_14To1_13_2() {
        super(ClientboundPackets1_13.class, ClientboundPackets1_14.class, ServerboundPackets1_13.class, ServerboundPackets1_14.class);
    }

    @Override
    protected void registerPackets() {
        this.metadataRewriter.register();
        this.itemRewriter.register();
        EntityPackets.register(this);
        WorldPackets.register(this);
        PlayerPackets.register(this);
        new SoundRewriter<ClientboundPackets1_13>(this).registerSound(ClientboundPackets1_13.SOUND);
        new StatisticsRewriter<ClientboundPackets1_13>(this).register(ClientboundPackets1_13.STATISTICS);
        ComponentRewriter1_14<ClientboundPackets1_13> componentRewriter1_14 = new ComponentRewriter1_14<ClientboundPackets1_13>(this);
        componentRewriter1_14.registerComponentPacket(ClientboundPackets1_13.CHAT_MESSAGE);
        CommandRewriter<ClientboundPackets1_13> commandRewriter = new CommandRewriter<ClientboundPackets1_13>(this, (Protocol)this){
            final Protocol1_14To1_13_2 this$0;
            {
                this.this$0 = protocol1_14To1_13_2;
                super(protocol);
            }

            @Override
            public @Nullable String handleArgumentType(String string) {
                if (string.equals("minecraft:nbt")) {
                    return "minecraft:nbt_compound_tag";
                }
                return super.handleArgumentType(string);
            }
        };
        commandRewriter.registerDeclareCommands(ClientboundPackets1_13.DECLARE_COMMANDS);
        this.registerClientbound(ClientboundPackets1_13.TAGS, Protocol1_14To1_13_2::lambda$registerPackets$0);
        this.cancelServerbound(ServerboundPackets1_14.SET_DIFFICULTY);
        this.cancelServerbound(ServerboundPackets1_14.LOCK_DIFFICULTY);
        this.cancelServerbound(ServerboundPackets1_14.UPDATE_JIGSAW_BLOCK);
    }

    @Override
    protected void onMappingDataLoaded() {
        WorldPackets.air = MAPPINGS.getBlockStateMappings().getNewId(0);
        WorldPackets.voidAir = MAPPINGS.getBlockStateMappings().getNewId(8591);
        WorldPackets.caveAir = MAPPINGS.getBlockStateMappings().getNewId(8592);
        Types1_13_2.PARTICLE.filler(this, true).reader("block", ParticleType.Readers.BLOCK).reader("dust", ParticleType.Readers.DUST).reader("falling_dust", ParticleType.Readers.BLOCK).reader("item", ParticleType.Readers.VAR_INT_ITEM);
        Types1_14.PARTICLE.filler(this).reader("block", ParticleType.Readers.BLOCK).reader("dust", ParticleType.Readers.DUST).reader("falling_dust", ParticleType.Readers.BLOCK).reader("item", ParticleType.Readers.VAR_INT_ITEM);
    }

    @Override
    public void init(UserConnection userConnection) {
        userConnection.addEntityTracker(this.getClass(), new EntityTracker1_14(userConnection));
        if (!userConnection.has(ClientWorld.class)) {
            userConnection.put(new ClientWorld(userConnection));
        }
    }

    @Override
    public MappingData getMappingData() {
        return MAPPINGS;
    }

    public MetadataRewriter1_14To1_13_2 getEntityRewriter() {
        return this.metadataRewriter;
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
        int n;
        int n2;
        int n3 = packetWrapper.read(Type.VAR_INT);
        packetWrapper.write(Type.VAR_INT, n3 + 6);
        for (n2 = 0; n2 < n3; ++n2) {
            packetWrapper.passthrough(Type.STRING);
            int[] nArray = packetWrapper.passthrough(Type.VAR_INT_ARRAY_PRIMITIVE);
            for (int i = 0; i < nArray.length; ++i) {
                nArray[i] = MAPPINGS.getNewBlockId(nArray[i]);
            }
        }
        packetWrapper.write(Type.STRING, "minecraft:signs");
        packetWrapper.write(Type.VAR_INT_ARRAY_PRIMITIVE, new int[]{MAPPINGS.getNewBlockId(150), MAPPINGS.getNewBlockId(155)});
        packetWrapper.write(Type.STRING, "minecraft:wall_signs");
        packetWrapper.write(Type.VAR_INT_ARRAY_PRIMITIVE, new int[]{MAPPINGS.getNewBlockId(155)});
        packetWrapper.write(Type.STRING, "minecraft:standing_signs");
        packetWrapper.write(Type.VAR_INT_ARRAY_PRIMITIVE, new int[]{MAPPINGS.getNewBlockId(150)});
        packetWrapper.write(Type.STRING, "minecraft:fences");
        packetWrapper.write(Type.VAR_INT_ARRAY_PRIMITIVE, new int[]{189, 248, 472, 473, 474, 475});
        packetWrapper.write(Type.STRING, "minecraft:walls");
        packetWrapper.write(Type.VAR_INT_ARRAY_PRIMITIVE, new int[]{271, 272});
        packetWrapper.write(Type.STRING, "minecraft:wooden_fences");
        packetWrapper.write(Type.VAR_INT_ARRAY_PRIMITIVE, new int[]{189, 472, 473, 474, 475});
        n2 = packetWrapper.read(Type.VAR_INT);
        packetWrapper.write(Type.VAR_INT, n2 + 2);
        for (n = 0; n < n2; ++n) {
            packetWrapper.passthrough(Type.STRING);
            int[] nArray = packetWrapper.passthrough(Type.VAR_INT_ARRAY_PRIMITIVE);
            for (int i = 0; i < nArray.length; ++i) {
                nArray[i] = MAPPINGS.getNewItemId(nArray[i]);
            }
        }
        packetWrapper.write(Type.STRING, "minecraft:signs");
        packetWrapper.write(Type.VAR_INT_ARRAY_PRIMITIVE, new int[]{MAPPINGS.getNewItemId(541)});
        packetWrapper.write(Type.STRING, "minecraft:arrows");
        packetWrapper.write(Type.VAR_INT_ARRAY_PRIMITIVE, new int[]{526, 825, 826});
        n = packetWrapper.passthrough(Type.VAR_INT);
        for (int i = 0; i < n; ++i) {
            packetWrapper.passthrough(Type.STRING);
            packetWrapper.passthrough(Type.VAR_INT_ARRAY_PRIMITIVE);
        }
        packetWrapper.write(Type.VAR_INT, 0);
    }
}

