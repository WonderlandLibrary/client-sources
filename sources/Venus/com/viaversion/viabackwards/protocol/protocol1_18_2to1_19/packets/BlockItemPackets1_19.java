/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viabackwards.protocol.protocol1_18_2to1_19.packets;

import com.viaversion.viabackwards.api.rewriters.ItemRewriter;
import com.viaversion.viabackwards.protocol.protocol1_18_2to1_19.Protocol1_18_2To1_19;
import com.viaversion.viaversion.api.data.ParticleMappings;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.minecraft.chunks.DataPalette;
import com.viaversion.viaversion.api.minecraft.chunks.PaletteType;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocols.protocol1_17to1_16_4.ServerboundPackets1_17;
import com.viaversion.viaversion.protocols.protocol1_18to1_17_1.types.Chunk1_18Type;
import com.viaversion.viaversion.protocols.protocol1_19to1_18_2.ClientboundPackets1_19;
import com.viaversion.viaversion.rewriter.BlockRewriter;
import com.viaversion.viaversion.rewriter.RecipeRewriter;
import com.viaversion.viaversion.util.MathUtil;

public final class BlockItemPackets1_19
extends ItemRewriter<ClientboundPackets1_19, ServerboundPackets1_17, Protocol1_18_2To1_19> {
    public BlockItemPackets1_19(Protocol1_18_2To1_19 protocol1_18_2To1_19) {
        super(protocol1_18_2To1_19);
    }

    @Override
    protected void registerPackets() {
        BlockRewriter<ClientboundPackets1_19> blockRewriter = new BlockRewriter<ClientboundPackets1_19>(this.protocol, Type.POSITION1_14);
        new RecipeRewriter<ClientboundPackets1_19>(this.protocol).register(ClientboundPackets1_19.DECLARE_RECIPES);
        this.registerSetCooldown(ClientboundPackets1_19.COOLDOWN);
        this.registerWindowItems1_17_1(ClientboundPackets1_19.WINDOW_ITEMS);
        this.registerSetSlot1_17_1(ClientboundPackets1_19.SET_SLOT);
        this.registerEntityEquipmentArray(ClientboundPackets1_19.ENTITY_EQUIPMENT);
        this.registerAdvancements(ClientboundPackets1_19.ADVANCEMENTS, Type.FLAT_VAR_INT_ITEM);
        this.registerClickWindow1_17_1(ServerboundPackets1_17.CLICK_WINDOW);
        blockRewriter.registerBlockAction(ClientboundPackets1_19.BLOCK_ACTION);
        blockRewriter.registerBlockChange(ClientboundPackets1_19.BLOCK_CHANGE);
        blockRewriter.registerVarLongMultiBlockChange(ClientboundPackets1_19.MULTI_BLOCK_CHANGE);
        blockRewriter.registerEffect(ClientboundPackets1_19.EFFECT, 1010, 2001);
        this.registerCreativeInvAction(ServerboundPackets1_17.CREATIVE_INVENTORY_ACTION, Type.FLAT_VAR_INT_ITEM);
        ((Protocol1_18_2To1_19)this.protocol).registerClientbound(ClientboundPackets1_19.TRADE_LIST, new PacketHandlers(this){
            final BlockItemPackets1_19 this$0;
            {
                this.this$0 = blockItemPackets1_19;
            }

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.handler(this::lambda$register$0);
            }

            private void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                int n = packetWrapper.read(Type.VAR_INT);
                packetWrapper.write(Type.UNSIGNED_BYTE, (short)n);
                for (int i = 0; i < n; ++i) {
                    this.this$0.handleItemToClient(packetWrapper.passthrough(Type.FLAT_VAR_INT_ITEM));
                    this.this$0.handleItemToClient(packetWrapper.passthrough(Type.FLAT_VAR_INT_ITEM));
                    Item item = packetWrapper.read(Type.FLAT_VAR_INT_ITEM);
                    if (item != null) {
                        this.this$0.handleItemToClient(item);
                        packetWrapper.write(Type.BOOLEAN, true);
                        packetWrapper.write(Type.FLAT_VAR_INT_ITEM, item);
                    } else {
                        packetWrapper.write(Type.BOOLEAN, false);
                    }
                    packetWrapper.passthrough(Type.BOOLEAN);
                    packetWrapper.passthrough(Type.INT);
                    packetWrapper.passthrough(Type.INT);
                    packetWrapper.passthrough(Type.INT);
                    packetWrapper.passthrough(Type.INT);
                    packetWrapper.passthrough(Type.FLOAT);
                    packetWrapper.passthrough(Type.INT);
                }
            }
        });
        this.registerWindowPropertyEnchantmentHandler(ClientboundPackets1_19.WINDOW_PROPERTY);
        ((Protocol1_18_2To1_19)this.protocol).registerClientbound(ClientboundPackets1_19.BLOCK_CHANGED_ACK, null, new PacketHandlers(this){
            final BlockItemPackets1_19 this$0;
            {
                this.this$0 = blockItemPackets1_19;
            }

            @Override
            public void register() {
                this.read(Type.VAR_INT);
                this.handler(PacketWrapper::cancel);
            }
        });
        ((Protocol1_18_2To1_19)this.protocol).registerClientbound(ClientboundPackets1_19.SPAWN_PARTICLE, new PacketHandlers(this){
            final BlockItemPackets1_19 this$0;
            {
                this.this$0 = blockItemPackets1_19;
            }

            @Override
            public void register() {
                this.map((Type)Type.VAR_INT, Type.INT);
                this.map(Type.BOOLEAN);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.INT);
                this.handler(this::lambda$register$0);
                this.handler(this.this$0.getSpawnParticleHandler(Type.FLAT_VAR_INT_ITEM));
            }

            private void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                ParticleMappings particleMappings;
                int n = packetWrapper.get(Type.INT, 0);
                if (n == (particleMappings = ((Protocol1_18_2To1_19)BlockItemPackets1_19.access$000(this.this$0)).getMappingData().getParticleMappings()).id("sculk_charge")) {
                    packetWrapper.set(Type.INT, 0, -1);
                    packetWrapper.cancel();
                } else if (n == particleMappings.id("shriek")) {
                    packetWrapper.set(Type.INT, 0, -1);
                    packetWrapper.cancel();
                } else if (n == particleMappings.id("vibration")) {
                    packetWrapper.set(Type.INT, 0, -1);
                    packetWrapper.cancel();
                }
            }
        });
        ((Protocol1_18_2To1_19)this.protocol).registerClientbound(ClientboundPackets1_19.CHUNK_DATA, this::lambda$registerPackets$0);
        ((Protocol1_18_2To1_19)this.protocol).registerServerbound(ServerboundPackets1_17.PLAYER_DIGGING, new PacketHandlers(this){
            final BlockItemPackets1_19 this$0;
            {
                this.this$0 = blockItemPackets1_19;
            }

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.map(Type.POSITION1_14);
                this.map(Type.UNSIGNED_BYTE);
                this.create(Type.VAR_INT, 0);
            }
        });
        ((Protocol1_18_2To1_19)this.protocol).registerServerbound(ServerboundPackets1_17.PLAYER_BLOCK_PLACEMENT, new PacketHandlers(this){
            final BlockItemPackets1_19 this$0;
            {
                this.this$0 = blockItemPackets1_19;
            }

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.map(Type.POSITION1_14);
                this.map(Type.VAR_INT);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.BOOLEAN);
                this.create(Type.VAR_INT, 0);
            }
        });
        ((Protocol1_18_2To1_19)this.protocol).registerServerbound(ServerboundPackets1_17.USE_ITEM, new PacketHandlers(this){
            final BlockItemPackets1_19 this$0;
            {
                this.this$0 = blockItemPackets1_19;
            }

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.create(Type.VAR_INT, 0);
            }
        });
        ((Protocol1_18_2To1_19)this.protocol).registerServerbound(ServerboundPackets1_17.SET_BEACON_EFFECT, BlockItemPackets1_19::lambda$registerPackets$1);
    }

    private static void lambda$registerPackets$1(PacketWrapper packetWrapper) throws Exception {
        int n = packetWrapper.read(Type.VAR_INT);
        if (n != -1) {
            packetWrapper.write(Type.BOOLEAN, true);
            packetWrapper.write(Type.VAR_INT, n);
        } else {
            packetWrapper.write(Type.BOOLEAN, false);
        }
        int n2 = packetWrapper.read(Type.VAR_INT);
        if (n2 != -1) {
            packetWrapper.write(Type.BOOLEAN, true);
            packetWrapper.write(Type.VAR_INT, n2);
        } else {
            packetWrapper.write(Type.BOOLEAN, false);
        }
    }

    private void lambda$registerPackets$0(PacketWrapper packetWrapper) throws Exception {
        Object e = ((Protocol1_18_2To1_19)this.protocol).getEntityRewriter().tracker(packetWrapper.user());
        Chunk1_18Type chunk1_18Type = new Chunk1_18Type(e.currentWorldSectionHeight(), MathUtil.ceilLog2(((Protocol1_18_2To1_19)this.protocol).getMappingData().getBlockStateMappings().mappedSize()), MathUtil.ceilLog2(e.biomesSent()));
        Chunk chunk = packetWrapper.passthrough(chunk1_18Type);
        for (ChunkSection chunkSection : chunk.getSections()) {
            DataPalette dataPalette = chunkSection.palette(PaletteType.BLOCKS);
            for (int i = 0; i < dataPalette.size(); ++i) {
                int n = dataPalette.idByIndex(i);
                dataPalette.setIdByIndex(i, ((Protocol1_18_2To1_19)this.protocol).getMappingData().getNewBlockStateId(n));
            }
        }
    }

    static Protocol access$000(BlockItemPackets1_19 blockItemPackets1_19) {
        return blockItemPackets1_19.protocol;
    }
}

