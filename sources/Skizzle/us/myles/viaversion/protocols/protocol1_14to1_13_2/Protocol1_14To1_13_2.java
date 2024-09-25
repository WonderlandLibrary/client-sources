/*
 * Decompiled with CFR 0.150.
 */
package us.myles.ViaVersion.protocols.protocol1_14to1_13_2;

import us.myles.ViaVersion.api.PacketWrapper;
import us.myles.ViaVersion.api.data.UserConnection;
import us.myles.ViaVersion.api.protocol.Protocol;
import us.myles.ViaVersion.api.remapper.PacketHandler;
import us.myles.ViaVersion.api.remapper.PacketRemapper;
import us.myles.ViaVersion.api.rewriters.SoundRewriter;
import us.myles.ViaVersion.api.rewriters.StatisticsRewriter;
import us.myles.ViaVersion.api.type.Type;
import us.myles.ViaVersion.protocols.protocol1_13to1_12_2.ClientboundPackets1_13;
import us.myles.ViaVersion.protocols.protocol1_13to1_12_2.ServerboundPackets1_13;
import us.myles.ViaVersion.protocols.protocol1_14to1_13_2.ClientboundPackets1_14;
import us.myles.ViaVersion.protocols.protocol1_14to1_13_2.ServerboundPackets1_14;
import us.myles.ViaVersion.protocols.protocol1_14to1_13_2.data.ComponentRewriter1_14;
import us.myles.ViaVersion.protocols.protocol1_14to1_13_2.data.MappingData;
import us.myles.ViaVersion.protocols.protocol1_14to1_13_2.metadata.MetadataRewriter1_14To1_13_2;
import us.myles.ViaVersion.protocols.protocol1_14to1_13_2.packets.EntityPackets;
import us.myles.ViaVersion.protocols.protocol1_14to1_13_2.packets.InventoryPackets;
import us.myles.ViaVersion.protocols.protocol1_14to1_13_2.packets.PlayerPackets;
import us.myles.ViaVersion.protocols.protocol1_14to1_13_2.packets.WorldPackets;
import us.myles.ViaVersion.protocols.protocol1_14to1_13_2.storage.EntityTracker1_14;
import us.myles.ViaVersion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;

public class Protocol1_14To1_13_2
extends Protocol<ClientboundPackets1_13, ClientboundPackets1_14, ServerboundPackets1_13, ServerboundPackets1_14> {
    public static final MappingData MAPPINGS = new MappingData();

    public Protocol1_14To1_13_2() {
        super(ClientboundPackets1_13.class, ClientboundPackets1_14.class, ServerboundPackets1_13.class, ServerboundPackets1_14.class);
    }

    @Override
    protected void registerPackets() {
        MetadataRewriter1_14To1_13_2 metadataRewriter = new MetadataRewriter1_14To1_13_2(this);
        InventoryPackets.register(this);
        EntityPackets.register(this);
        WorldPackets.register(this);
        PlayerPackets.register(this);
        new SoundRewriter(this).registerSound(ClientboundPackets1_13.SOUND);
        new StatisticsRewriter(this, metadataRewriter::getNewEntityId).register(ClientboundPackets1_13.STATISTICS);
        ComponentRewriter1_14 componentRewriter = new ComponentRewriter1_14(this);
        componentRewriter.registerChatMessage(ClientboundPackets1_13.CHAT_MESSAGE);
        this.registerOutgoing(ClientboundPackets1_13.TAGS, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper wrapper) throws Exception {
                        int blockTagsSize = wrapper.read(Type.VAR_INT);
                        wrapper.write(Type.VAR_INT, blockTagsSize + 6);
                        for (int i = 0; i < blockTagsSize; ++i) {
                            wrapper.passthrough(Type.STRING);
                            int[] blockIds = wrapper.passthrough(Type.VAR_INT_ARRAY_PRIMITIVE);
                            for (int j = 0; j < blockIds.length; ++j) {
                                blockIds[j] = Protocol1_14To1_13_2.this.getMappingData().getNewBlockId(blockIds[j]);
                            }
                        }
                        wrapper.write(Type.STRING, "minecraft:signs");
                        wrapper.write(Type.VAR_INT_ARRAY_PRIMITIVE, new int[]{Protocol1_14To1_13_2.this.getMappingData().getNewBlockId(150), Protocol1_14To1_13_2.this.getMappingData().getNewBlockId(155)});
                        wrapper.write(Type.STRING, "minecraft:wall_signs");
                        wrapper.write(Type.VAR_INT_ARRAY_PRIMITIVE, new int[]{Protocol1_14To1_13_2.this.getMappingData().getNewBlockId(155)});
                        wrapper.write(Type.STRING, "minecraft:standing_signs");
                        wrapper.write(Type.VAR_INT_ARRAY_PRIMITIVE, new int[]{Protocol1_14To1_13_2.this.getMappingData().getNewBlockId(150)});
                        wrapper.write(Type.STRING, "minecraft:fences");
                        wrapper.write(Type.VAR_INT_ARRAY_PRIMITIVE, new int[]{189, 248, 472, 473, 474, 475});
                        wrapper.write(Type.STRING, "minecraft:walls");
                        wrapper.write(Type.VAR_INT_ARRAY_PRIMITIVE, new int[]{271, 272});
                        wrapper.write(Type.STRING, "minecraft:wooden_fences");
                        wrapper.write(Type.VAR_INT_ARRAY_PRIMITIVE, new int[]{189, 472, 473, 474, 475});
                        int itemTagsSize = wrapper.read(Type.VAR_INT);
                        wrapper.write(Type.VAR_INT, itemTagsSize + 2);
                        for (int i = 0; i < itemTagsSize; ++i) {
                            wrapper.passthrough(Type.STRING);
                            int[] itemIds = wrapper.passthrough(Type.VAR_INT_ARRAY_PRIMITIVE);
                            for (int j = 0; j < itemIds.length; ++j) {
                                itemIds[j] = Protocol1_14To1_13_2.this.getMappingData().getNewItemId(itemIds[j]);
                            }
                        }
                        wrapper.write(Type.STRING, "minecraft:signs");
                        wrapper.write(Type.VAR_INT_ARRAY_PRIMITIVE, new int[]{Protocol1_14To1_13_2.this.getMappingData().getNewItemId(541)});
                        wrapper.write(Type.STRING, "minecraft:arrows");
                        wrapper.write(Type.VAR_INT_ARRAY_PRIMITIVE, new int[]{526, 825, 826});
                        int fluidTagsSize = wrapper.passthrough(Type.VAR_INT);
                        for (int i = 0; i < fluidTagsSize; ++i) {
                            wrapper.passthrough(Type.STRING);
                            wrapper.passthrough(Type.VAR_INT_ARRAY_PRIMITIVE);
                        }
                        wrapper.write(Type.VAR_INT, 0);
                    }
                });
            }
        });
        this.cancelIncoming(ServerboundPackets1_14.SET_DIFFICULTY);
        this.cancelIncoming(ServerboundPackets1_14.LOCK_DIFFICULTY);
        this.cancelIncoming(ServerboundPackets1_14.UPDATE_JIGSAW_BLOCK);
    }

    @Override
    protected void onMappingDataLoaded() {
        WorldPackets.air = this.getMappingData().getBlockStateMappings().getNewId(0);
        WorldPackets.voidAir = this.getMappingData().getBlockStateMappings().getNewId(8591);
        WorldPackets.caveAir = this.getMappingData().getBlockStateMappings().getNewId(8592);
    }

    @Override
    public void init(UserConnection userConnection) {
        userConnection.put(new EntityTracker1_14(userConnection));
        if (!userConnection.has(ClientWorld.class)) {
            userConnection.put(new ClientWorld(userConnection));
        }
    }

    @Override
    public MappingData getMappingData() {
        return MAPPINGS;
    }
}

