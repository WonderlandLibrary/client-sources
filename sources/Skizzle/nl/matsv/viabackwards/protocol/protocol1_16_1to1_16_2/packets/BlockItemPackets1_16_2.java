/*
 * Decompiled with CFR 0.150.
 */
package nl.matsv.viabackwards.protocol.protocol1_16_1to1_16_2.packets;

import nl.matsv.viabackwards.api.rewriters.ItemRewriter;
import nl.matsv.viabackwards.api.rewriters.TranslatableRewriter;
import nl.matsv.viabackwards.protocol.protocol1_16_1to1_16_2.Protocol1_16_1To1_16_2;
import us.myles.ViaVersion.api.minecraft.BlockChangeRecord;
import us.myles.ViaVersion.api.minecraft.BlockChangeRecord1_8;
import us.myles.ViaVersion.api.minecraft.Position;
import us.myles.ViaVersion.api.minecraft.chunks.Chunk;
import us.myles.ViaVersion.api.minecraft.chunks.ChunkSection;
import us.myles.ViaVersion.api.remapper.PacketRemapper;
import us.myles.ViaVersion.api.rewriters.BlockRewriter;
import us.myles.ViaVersion.api.type.Type;
import us.myles.ViaVersion.protocols.protocol1_16_2to1_16_1.ClientboundPackets1_16_2;
import us.myles.ViaVersion.protocols.protocol1_16_2to1_16_1.types.Chunk1_16_2Type;
import us.myles.ViaVersion.protocols.protocol1_16to1_15_2.ServerboundPackets1_16;
import us.myles.ViaVersion.protocols.protocol1_16to1_15_2.data.RecipeRewriter1_16;
import us.myles.ViaVersion.protocols.protocol1_16to1_15_2.types.Chunk1_16Type;
import us.myles.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import us.myles.viaversion.libs.opennbt.tag.builtin.IntArrayTag;
import us.myles.viaversion.libs.opennbt.tag.builtin.IntTag;
import us.myles.viaversion.libs.opennbt.tag.builtin.ListTag;
import us.myles.viaversion.libs.opennbt.tag.builtin.StringTag;
import us.myles.viaversion.libs.opennbt.tag.builtin.Tag;

public class BlockItemPackets1_16_2
extends ItemRewriter<Protocol1_16_1To1_16_2> {
    public BlockItemPackets1_16_2(Protocol1_16_1To1_16_2 protocol, TranslatableRewriter translatableRewriter) {
        super(protocol, translatableRewriter);
    }

    @Override
    protected void registerPackets() {
        us.myles.ViaVersion.api.rewriters.ItemRewriter itemRewriter = new us.myles.ViaVersion.api.rewriters.ItemRewriter(this.protocol, this::handleItemToClient, this::handleItemToServer);
        BlockRewriter blockRewriter = new BlockRewriter(this.protocol, Type.POSITION1_14);
        new RecipeRewriter1_16(this.protocol, this::handleItemToClient).registerDefaultHandler(ClientboundPackets1_16_2.DECLARE_RECIPES);
        itemRewriter.registerSetCooldown(ClientboundPackets1_16_2.COOLDOWN);
        itemRewriter.registerWindowItems(ClientboundPackets1_16_2.WINDOW_ITEMS, Type.FLAT_VAR_INT_ITEM_ARRAY);
        itemRewriter.registerSetSlot(ClientboundPackets1_16_2.SET_SLOT, Type.FLAT_VAR_INT_ITEM);
        itemRewriter.registerEntityEquipmentArray(ClientboundPackets1_16_2.ENTITY_EQUIPMENT, Type.FLAT_VAR_INT_ITEM);
        itemRewriter.registerTradeList(ClientboundPackets1_16_2.TRADE_LIST, Type.FLAT_VAR_INT_ITEM);
        itemRewriter.registerAdvancements(ClientboundPackets1_16_2.ADVANCEMENTS, Type.FLAT_VAR_INT_ITEM);
        ((Protocol1_16_1To1_16_2)this.protocol).registerOutgoing(ClientboundPackets1_16_2.UNLOCK_RECIPES, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.handler(wrapper -> {
                    wrapper.passthrough(Type.VAR_INT);
                    wrapper.passthrough(Type.BOOLEAN);
                    wrapper.passthrough(Type.BOOLEAN);
                    wrapper.passthrough(Type.BOOLEAN);
                    wrapper.passthrough(Type.BOOLEAN);
                    wrapper.read(Type.BOOLEAN);
                    wrapper.read(Type.BOOLEAN);
                    wrapper.read(Type.BOOLEAN);
                    wrapper.read(Type.BOOLEAN);
                });
            }
        });
        blockRewriter.registerAcknowledgePlayerDigging(ClientboundPackets1_16_2.ACKNOWLEDGE_PLAYER_DIGGING);
        blockRewriter.registerBlockAction(ClientboundPackets1_16_2.BLOCK_ACTION);
        blockRewriter.registerBlockChange(ClientboundPackets1_16_2.BLOCK_CHANGE);
        ((Protocol1_16_1To1_16_2)this.protocol).registerOutgoing(ClientboundPackets1_16_2.CHUNK_DATA, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.handler(wrapper -> {
                    Chunk chunk = wrapper.read(new Chunk1_16_2Type());
                    wrapper.write(new Chunk1_16Type(), chunk);
                    chunk.setIgnoreOldLightData(true);
                    for (int i = 0; i < chunk.getSections().length; ++i) {
                        ChunkSection section = chunk.getSections()[i];
                        if (section == null) continue;
                        for (int j = 0; j < section.getPaletteSize(); ++j) {
                            int old = section.getPaletteEntry(j);
                            section.setPaletteEntry(j, ((Protocol1_16_1To1_16_2)BlockItemPackets1_16_2.this.protocol).getMappingData().getNewBlockStateId(old));
                        }
                    }
                    for (CompoundTag blockEntity : chunk.getBlockEntities()) {
                        if (blockEntity == null) continue;
                        IntTag x = (IntTag)blockEntity.get("x");
                        IntTag y = (IntTag)blockEntity.get("y");
                        IntTag z = (IntTag)blockEntity.get("z");
                        if (x == null || y == null || z == null) continue;
                        BlockItemPackets1_16_2.this.handleBlockEntity(blockEntity, new Position(x.getValue(), y.getValue().shortValue(), z.getValue()));
                    }
                });
            }
        });
        ((Protocol1_16_1To1_16_2)this.protocol).registerOutgoing(ClientboundPackets1_16_2.BLOCK_ENTITY_DATA, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.handler(wrapper -> {
                    Position position = wrapper.passthrough(Type.POSITION1_14);
                    wrapper.passthrough(Type.UNSIGNED_BYTE);
                    BlockItemPackets1_16_2.this.handleBlockEntity(wrapper.passthrough(Type.NBT), position);
                });
            }
        });
        ((Protocol1_16_1To1_16_2)this.protocol).registerOutgoing(ClientboundPackets1_16_2.MULTI_BLOCK_CHANGE, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.handler(wrapper -> {
                    long chunkPosition = wrapper.read(Type.LONG);
                    wrapper.read(Type.BOOLEAN);
                    int chunkX = (int)(chunkPosition >> 42);
                    int chunkY = (int)(chunkPosition << 44 >> 44);
                    int chunkZ = (int)(chunkPosition << 22 >> 42);
                    wrapper.write(Type.INT, chunkX);
                    wrapper.write(Type.INT, chunkZ);
                    BlockChangeRecord[] blockChangeRecord = wrapper.read(Type.VAR_LONG_BLOCK_CHANGE_RECORD_ARRAY);
                    wrapper.write(Type.BLOCK_CHANGE_RECORD_ARRAY, blockChangeRecord);
                    for (int i = 0; i < blockChangeRecord.length; ++i) {
                        BlockChangeRecord record = blockChangeRecord[i];
                        int blockId = ((Protocol1_16_1To1_16_2)BlockItemPackets1_16_2.this.protocol).getMappingData().getNewBlockStateId(record.getBlockId());
                        blockChangeRecord[i] = new BlockChangeRecord1_8(record.getSectionX(), record.getY(chunkY), record.getSectionZ(), blockId);
                    }
                });
            }
        });
        blockRewriter.registerEffect(ClientboundPackets1_16_2.EFFECT, 1010, 2001);
        itemRewriter.registerSpawnParticle(ClientboundPackets1_16_2.SPAWN_PARTICLE, Type.FLAT_VAR_INT_ITEM, Type.DOUBLE);
        itemRewriter.registerClickWindow(ServerboundPackets1_16.CLICK_WINDOW, Type.FLAT_VAR_INT_ITEM);
        itemRewriter.registerCreativeInvAction(ServerboundPackets1_16.CREATIVE_INVENTORY_ACTION, Type.FLAT_VAR_INT_ITEM);
        ((Protocol1_16_1To1_16_2)this.protocol).registerIncoming(ServerboundPackets1_16.EDIT_BOOK, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.handler(wrapper -> BlockItemPackets1_16_2.this.handleItemToServer(wrapper.passthrough(Type.FLAT_VAR_INT_ITEM)));
            }
        });
    }

    private void handleBlockEntity(CompoundTag tag, Position position) {
        StringTag idTag = (StringTag)tag.get("id");
        if (idTag == null) {
            return;
        }
        if (idTag.getValue().equals("minecraft:skull")) {
            CompoundTag first;
            Object skullOwnerTag = tag.get("SkullOwner");
            if (!(skullOwnerTag instanceof CompoundTag)) {
                return;
            }
            CompoundTag skullOwnerCompoundTag = (CompoundTag)skullOwnerTag;
            if (!skullOwnerCompoundTag.contains("Id")) {
                return;
            }
            CompoundTag properties = (CompoundTag)skullOwnerCompoundTag.get("Properties");
            if (properties == null) {
                return;
            }
            ListTag textures = (ListTag)properties.get("textures");
            if (textures == null) {
                return;
            }
            CompoundTag compoundTag = first = textures.size() > 0 ? (CompoundTag)textures.get(0) : null;
            if (first == null) {
                return;
            }
            int hashCode = ((Tag)first.get("Value")).getValue().hashCode();
            int[] uuidIntArray = new int[]{hashCode, 0, 0, 0};
            skullOwnerCompoundTag.put(new IntArrayTag("Id", uuidIntArray));
        }
    }
}

