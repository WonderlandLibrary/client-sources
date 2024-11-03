package com.viaversion.viaversion.protocols.protocol1_15to1_14_4;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.data.MappingData;
import com.viaversion.viaversion.api.data.MappingDataBase;
import com.viaversion.viaversion.api.minecraft.RegistryType;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_15;
import com.viaversion.viaversion.api.protocol.AbstractProtocol;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.data.entity.EntityTrackerBase;
import com.viaversion.viaversion.protocols.protocol1_14_4to1_14_3.ClientboundPackets1_14_4;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.ServerboundPackets1_14;
import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.metadata.MetadataRewriter1_15To1_14_4;
import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.packets.EntityPackets;
import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.packets.InventoryPackets;
import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.packets.WorldPackets;
import com.viaversion.viaversion.rewriter.SoundRewriter;
import com.viaversion.viaversion.rewriter.StatisticsRewriter;
import com.viaversion.viaversion.rewriter.TagRewriter;

public class Protocol1_15To1_14_4 extends AbstractProtocol<ClientboundPackets1_14_4, ClientboundPackets1_15, ServerboundPackets1_14, ServerboundPackets1_14> {
   public static final MappingData MAPPINGS = new MappingDataBase("1.14", "1.15");
   private final MetadataRewriter1_15To1_14_4 metadataRewriter = new MetadataRewriter1_15To1_14_4(this);
   private final InventoryPackets itemRewriter = new InventoryPackets(this);
   private TagRewriter<ClientboundPackets1_14_4> tagRewriter;

   public Protocol1_15To1_14_4() {
      super(ClientboundPackets1_14_4.class, ClientboundPackets1_15.class, ServerboundPackets1_14.class, ServerboundPackets1_14.class);
   }

   @Override
   protected void registerPackets() {
      this.metadataRewriter.register();
      this.itemRewriter.register();
      EntityPackets.register(this);
      WorldPackets.register(this);
      SoundRewriter<ClientboundPackets1_14_4> soundRewriter = new SoundRewriter<>(this);
      soundRewriter.registerSound(ClientboundPackets1_14_4.ENTITY_SOUND);
      soundRewriter.registerSound(ClientboundPackets1_14_4.SOUND);
      new StatisticsRewriter<>(this).register(ClientboundPackets1_14_4.STATISTICS);
      this.registerServerbound(ServerboundPackets1_14.EDIT_BOOK, wrapper -> this.itemRewriter.handleItemToServer(wrapper.passthrough(Type.ITEM1_13_2)));
      this.tagRewriter = new TagRewriter<>(this);
      this.tagRewriter.register(ClientboundPackets1_14_4.TAGS, RegistryType.ENTITY);
   }

   @Override
   protected void onMappingDataLoaded() {
      int[] shulkerBoxes = new int[17];
      int shulkerBoxOffset = 501;

      for (int i = 0; i < 17; i++) {
         shulkerBoxes[i] = shulkerBoxOffset + i;
      }

      this.tagRewriter.addTag(RegistryType.BLOCK, "minecraft:shulker_boxes", shulkerBoxes);
   }

   @Override
   public void init(UserConnection connection) {
      this.addEntityTracker(connection, new EntityTrackerBase(connection, EntityTypes1_15.PLAYER));
   }

   @Override
   public MappingData getMappingData() {
      return MAPPINGS;
   }

   public MetadataRewriter1_15To1_14_4 getEntityRewriter() {
      return this.metadataRewriter;
   }

   public InventoryPackets getItemRewriter() {
      return this.itemRewriter;
   }
}
