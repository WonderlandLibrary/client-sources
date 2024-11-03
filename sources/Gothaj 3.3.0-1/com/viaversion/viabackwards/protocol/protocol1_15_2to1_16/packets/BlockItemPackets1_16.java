package com.viaversion.viabackwards.protocol.protocol1_15_2to1_16.packets;

import com.viaversion.viabackwards.ViaBackwards;
import com.viaversion.viabackwards.api.rewriters.EnchantmentRewriter;
import com.viaversion.viabackwards.api.rewriters.ItemRewriter;
import com.viaversion.viabackwards.api.rewriters.MapColorRewriter;
import com.viaversion.viabackwards.protocol.protocol1_15_2to1_16.Protocol1_15_2To1_16;
import com.viaversion.viabackwards.protocol.protocol1_15_2to1_16.data.MapColorRewrites;
import com.viaversion.viabackwards.protocol.protocol1_16_1to1_16_2.storage.BiomeStorage;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.minecraft.chunks.DataPalette;
import com.viaversion.viaversion.api.minecraft.chunks.PaletteType;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.chunk.ChunkType1_15;
import com.viaversion.viaversion.api.type.types.chunk.ChunkType1_16;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.IntArrayTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.LongArrayTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.ServerboundPackets1_14;
import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.ClientboundPackets1_15;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.ClientboundPackets1_16;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.packets.InventoryPackets;
import com.viaversion.viaversion.rewriter.BlockRewriter;
import com.viaversion.viaversion.rewriter.RecipeRewriter;
import com.viaversion.viaversion.util.CompactArrayUtil;
import com.viaversion.viaversion.util.Key;
import com.viaversion.viaversion.util.UUIDUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.Map.Entry;

public class BlockItemPackets1_16 extends ItemRewriter<ClientboundPackets1_16, ServerboundPackets1_14, Protocol1_15_2To1_16> {
   private EnchantmentRewriter enchantmentRewriter;

   public BlockItemPackets1_16(Protocol1_15_2To1_16 protocol) {
      super(protocol);
   }

   @Override
   protected void registerPackets() {
      BlockRewriter<ClientboundPackets1_16> blockRewriter = BlockRewriter.for1_14(this.protocol);
      RecipeRewriter<ClientboundPackets1_16> recipeRewriter = new RecipeRewriter<>(this.protocol);
      this.protocol.registerClientbound(ClientboundPackets1_16.DECLARE_RECIPES, wrapper -> {
         int size = wrapper.passthrough(Type.VAR_INT);
         int newSize = size;

         for (int i = 0; i < size; i++) {
            String originalType = wrapper.read(Type.STRING);
            String type = Key.stripMinecraftNamespace(originalType);
            if (type.equals("smithing")) {
               newSize--;
               wrapper.read(Type.STRING);
               wrapper.read(Type.ITEM1_13_2_ARRAY);
               wrapper.read(Type.ITEM1_13_2_ARRAY);
               wrapper.read(Type.ITEM1_13_2);
            } else {
               wrapper.write(Type.STRING, originalType);
               wrapper.passthrough(Type.STRING);
               recipeRewriter.handleRecipeType(wrapper, type);
            }
         }

         wrapper.set(Type.VAR_INT, 0, newSize);
      });
      this.registerSetCooldown(ClientboundPackets1_16.COOLDOWN);
      this.registerWindowItems(ClientboundPackets1_16.WINDOW_ITEMS, Type.ITEM1_13_2_SHORT_ARRAY);
      this.registerSetSlot(ClientboundPackets1_16.SET_SLOT, Type.ITEM1_13_2);
      this.registerTradeList(ClientboundPackets1_16.TRADE_LIST);
      this.registerAdvancements(ClientboundPackets1_16.ADVANCEMENTS, Type.ITEM1_13_2);
      blockRewriter.registerAcknowledgePlayerDigging(ClientboundPackets1_16.ACKNOWLEDGE_PLAYER_DIGGING);
      blockRewriter.registerBlockAction(ClientboundPackets1_16.BLOCK_ACTION);
      blockRewriter.registerBlockChange(ClientboundPackets1_16.BLOCK_CHANGE);
      blockRewriter.registerMultiBlockChange(ClientboundPackets1_16.MULTI_BLOCK_CHANGE);
      this.protocol.registerClientbound(ClientboundPackets1_16.ENTITY_EQUIPMENT, wrapper -> {
         int entityId = wrapper.passthrough(Type.VAR_INT);
         List<BlockItemPackets1_16.EquipmentData> equipmentData = new ArrayList<>();

         byte slot;
         do {
            slot = wrapper.read(Type.BYTE);
            Item item = this.handleItemToClient(wrapper.read(Type.ITEM1_13_2));
            int rawSlot = slot & 127;
            equipmentData.add(new BlockItemPackets1_16.EquipmentData(rawSlot, item));
         } while ((slot & -128) != 0);

         BlockItemPackets1_16.EquipmentData firstData = equipmentData.get(0);
         wrapper.write(Type.VAR_INT, firstData.slot);
         wrapper.write(Type.ITEM1_13_2, firstData.item);

         for (int i = 1; i < equipmentData.size(); i++) {
            PacketWrapper equipmentPacket = wrapper.create(ClientboundPackets1_15.ENTITY_EQUIPMENT);
            BlockItemPackets1_16.EquipmentData data = equipmentData.get(i);
            equipmentPacket.write(Type.VAR_INT, entityId);
            equipmentPacket.write(Type.VAR_INT, data.slot);
            equipmentPacket.write(Type.ITEM1_13_2, data.item);
            equipmentPacket.send(Protocol1_15_2To1_16.class);
         }
      });
      this.protocol.registerClientbound(ClientboundPackets1_16.UPDATE_LIGHT, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.VAR_INT);
            this.map(Type.VAR_INT);
            this.read(Type.BOOLEAN);
         }
      });
      this.protocol.registerClientbound(ClientboundPackets1_16.CHUNK_DATA, wrapper -> {
         Chunk chunk = wrapper.read(ChunkType1_16.TYPE);
         wrapper.write(ChunkType1_15.TYPE, chunk);

         for (int i = 0; i < chunk.getSections().length; i++) {
            ChunkSection section = chunk.getSections()[i];
            if (section != null) {
               DataPalette palette = section.palette(PaletteType.BLOCKS);

               for (int j = 0; j < palette.size(); j++) {
                  int mappedBlockStateId = this.protocol.getMappingData().getNewBlockStateId(palette.idByIndex(j));
                  palette.setIdByIndex(j, mappedBlockStateId);
               }
            }
         }

         CompoundTag heightMaps = chunk.getHeightMap();

         for (Tag heightMapTag : heightMaps.values()) {
            if (heightMapTag instanceof LongArrayTag) {
               LongArrayTag heightMap = (LongArrayTag)heightMapTag;
               int[] heightMapData = new int[256];
               CompactArrayUtil.iterateCompactArrayWithPadding(9, heightMapData.length, heightMap.getValue(), (ix, v) -> heightMapData[ix] = v);
               heightMap.setValue(CompactArrayUtil.createCompactArray(9, heightMapData.length, ix -> (long)heightMapData[ix]));
            }
         }

         if (chunk.isBiomeData()) {
            if (wrapper.user().getProtocolInfo().getServerProtocolVersion() >= ProtocolVersion.v1_16_2.getVersion()) {
               BiomeStorage biomeStorage = wrapper.user().get(BiomeStorage.class);

               for (int ix = 0; ix < 1024; ix++) {
                  int biome = chunk.getBiomeData()[ix];
                  int legacyBiome = biomeStorage.legacyBiome(biome);
                  if (legacyBiome == -1) {
                     ViaBackwards.getPlatform().getLogger().warning("Biome sent that does not exist in the biome registry: " + biome);
                     legacyBiome = 1;
                  }

                  chunk.getBiomeData()[ix] = legacyBiome;
               }
            } else {
               for (int ix = 0; ix < 1024; ix++) {
                  int biome = chunk.getBiomeData()[ix];
                  switch (biome) {
                     case 170:
                     case 171:
                     case 172:
                     case 173:
                        chunk.getBiomeData()[ix] = 8;
                        break;
                  }
               }
            }
         }

         if (chunk.getBlockEntities() != null) {
            for (CompoundTag blockEntity : chunk.getBlockEntities()) {
               this.handleBlockEntity(blockEntity);
            }
         }
      });
      blockRewriter.registerEffect(ClientboundPackets1_16.EFFECT, 1010, 2001);
      this.registerSpawnParticle(ClientboundPackets1_16.SPAWN_PARTICLE, Type.ITEM1_13_2, Type.DOUBLE);
      this.protocol.registerClientbound(ClientboundPackets1_16.WINDOW_PROPERTY, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.UNSIGNED_BYTE);
            this.map(Type.SHORT);
            this.map(Type.SHORT);
            this.handler(wrapper -> {
               short property = wrapper.get(Type.SHORT, 0);
               if (property >= 4 && property <= 6) {
                  short enchantmentId = wrapper.get(Type.SHORT, 1);
                  if (enchantmentId > 11) {
                     wrapper.set(Type.SHORT, 1, --enchantmentId);
                  } else if (enchantmentId == 11) {
                     wrapper.set(Type.SHORT, 1, (short)9);
                  }
               }
            });
         }
      });
      this.protocol.registerClientbound(ClientboundPackets1_16.MAP_DATA, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.VAR_INT);
            this.map(Type.BYTE);
            this.map(Type.BOOLEAN);
            this.map(Type.BOOLEAN);
            this.handler(MapColorRewriter.getRewriteHandler(MapColorRewrites::getMappedColor));
         }
      });
      this.protocol.registerClientbound(ClientboundPackets1_16.BLOCK_ENTITY_DATA, wrapper -> {
         wrapper.passthrough(Type.POSITION1_14);
         wrapper.passthrough(Type.UNSIGNED_BYTE);
         CompoundTag tag = wrapper.passthrough(Type.NAMED_COMPOUND_TAG);
         this.handleBlockEntity(tag);
      });
      this.registerClickWindow(ServerboundPackets1_14.CLICK_WINDOW, Type.ITEM1_13_2);
      this.registerCreativeInvAction(ServerboundPackets1_14.CREATIVE_INVENTORY_ACTION, Type.ITEM1_13_2);
      this.protocol.registerServerbound(ServerboundPackets1_14.EDIT_BOOK, wrapper -> this.handleItemToServer(wrapper.passthrough(Type.ITEM1_13_2)));
   }

   private void handleBlockEntity(CompoundTag tag) {
      StringTag idTag = tag.get("id");
      if (idTag != null) {
         String id = idTag.getValue();
         if (id.equals("minecraft:conduit")) {
            Tag targetUuidTag = tag.remove("Target");
            if (!(targetUuidTag instanceof IntArrayTag)) {
               return;
            }

            UUID targetUuid = UUIDUtil.fromIntArray((int[])targetUuidTag.getValue());
            tag.put("target_uuid", new StringTag(targetUuid.toString()));
         } else if (id.equals("minecraft:skull")) {
            Tag skullOwnerTag = tag.remove("SkullOwner");
            if (!(skullOwnerTag instanceof CompoundTag)) {
               return;
            }

            CompoundTag skullOwnerCompoundTag = (CompoundTag)skullOwnerTag;
            Tag ownerUuidTag = skullOwnerCompoundTag.remove("Id");
            if (ownerUuidTag instanceof IntArrayTag) {
               UUID ownerUuid = UUIDUtil.fromIntArray((int[])ownerUuidTag.getValue());
               skullOwnerCompoundTag.put("Id", new StringTag(ownerUuid.toString()));
            }

            CompoundTag ownerTag = new CompoundTag();

            for (Entry<String, Tag> entry : skullOwnerCompoundTag) {
               ownerTag.put(entry.getKey(), entry.getValue());
            }

            tag.put("Owner", ownerTag);
         }
      }
   }

   @Override
   protected void registerRewrites() {
      this.enchantmentRewriter = new EnchantmentRewriter(this);
      this.enchantmentRewriter.registerEnchantment("minecraft:soul_speed", "§7Soul Speed");
   }

   @Override
   public Item handleItemToClient(Item item) {
      if (item == null) {
         return null;
      } else {
         super.handleItemToClient(item);
         CompoundTag tag = item.tag();
         if (item.identifier() == 771 && tag != null) {
            Tag ownerTag = tag.get("SkullOwner");
            if (ownerTag instanceof CompoundTag) {
               CompoundTag ownerCompundTag = (CompoundTag)ownerTag;
               Tag idTag = ownerCompundTag.get("Id");
               if (idTag instanceof IntArrayTag) {
                  UUID ownerUuid = UUIDUtil.fromIntArray((int[])idTag.getValue());
                  ownerCompundTag.put("Id", new StringTag(ownerUuid.toString()));
               }
            }
         }

         if ((item.identifier() == 758 || item.identifier() == 759) && tag != null) {
            Tag pagesTag = tag.get("pages");
            if (pagesTag instanceof ListTag) {
               for (Tag page : (ListTag)pagesTag) {
                  if (page instanceof StringTag) {
                     StringTag pageTag = (StringTag)page;
                     JsonElement jsonElement = this.protocol.getTranslatableRewriter().processText(pageTag.getValue());
                     pageTag.setValue(jsonElement.toString());
                  }
               }
            }
         }

         InventoryPackets.newToOldAttributes(item);
         this.enchantmentRewriter.handleToClient(item);
         return item;
      }
   }

   @Override
   public Item handleItemToServer(Item item) {
      if (item == null) {
         return null;
      } else {
         int identifier = item.identifier();
         super.handleItemToServer(item);
         CompoundTag tag = item.tag();
         if (identifier == 771 && tag != null) {
            Tag ownerTag = tag.get("SkullOwner");
            if (ownerTag instanceof CompoundTag) {
               CompoundTag ownerCompundTag = (CompoundTag)ownerTag;
               Tag idTag = ownerCompundTag.get("Id");
               if (idTag instanceof StringTag) {
                  UUID ownerUuid = UUID.fromString((String)idTag.getValue());
                  ownerCompundTag.put("Id", new IntArrayTag(UUIDUtil.toIntArray(ownerUuid)));
               }
            }
         }

         InventoryPackets.oldToNewAttributes(item);
         this.enchantmentRewriter.handleToServer(item);
         return item;
      }
   }

   private static final class EquipmentData {
      private final int slot;
      private final Item item;

      private EquipmentData(int slot, Item item) {
         this.slot = slot;
         this.item = item;
      }
   }
}
