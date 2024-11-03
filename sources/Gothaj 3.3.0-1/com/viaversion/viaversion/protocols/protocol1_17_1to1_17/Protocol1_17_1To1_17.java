package com.viaversion.viaversion.protocols.protocol1_17_1to1_17;

import com.viaversion.viaversion.api.minecraft.item.DataItem;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.AbstractProtocol;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.StringType;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.protocols.protocol1_17to1_16_4.ClientboundPackets1_17;
import com.viaversion.viaversion.protocols.protocol1_17to1_16_4.ServerboundPackets1_17;

public final class Protocol1_17_1To1_17
   extends AbstractProtocol<ClientboundPackets1_17, ClientboundPackets1_17_1, ServerboundPackets1_17, ServerboundPackets1_17> {
   private static final StringType PAGE_STRING_TYPE = new StringType(8192);
   private static final StringType TITLE_STRING_TYPE = new StringType(128);

   public Protocol1_17_1To1_17() {
      super(ClientboundPackets1_17.class, ClientboundPackets1_17_1.class, ServerboundPackets1_17.class, ServerboundPackets1_17.class);
   }

   @Override
   protected void registerPackets() {
      this.registerClientbound(ClientboundPackets1_17.REMOVE_ENTITY, ClientboundPackets1_17_1.REMOVE_ENTITIES, wrapper -> {
         int entityId = wrapper.read(Type.VAR_INT);
         wrapper.write(Type.VAR_INT_ARRAY_PRIMITIVE, new int[]{entityId});
      });
      this.registerClientbound(ClientboundPackets1_17.SET_SLOT, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.UNSIGNED_BYTE);
            this.create(Type.VAR_INT, Integer.valueOf(0));
         }
      });
      this.registerClientbound(ClientboundPackets1_17.WINDOW_ITEMS, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.UNSIGNED_BYTE);
            this.create(Type.VAR_INT, Integer.valueOf(0));
            this.handler(wrapper -> {
               wrapper.write(Type.ITEM1_13_2_ARRAY, wrapper.read(Type.ITEM1_13_2_SHORT_ARRAY));
               wrapper.write(Type.ITEM1_13_2, null);
            });
         }
      });
      this.registerServerbound(ServerboundPackets1_17.CLICK_WINDOW, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.UNSIGNED_BYTE);
            this.read(Type.VAR_INT);
         }
      });
      this.registerServerbound(ServerboundPackets1_17.EDIT_BOOK, wrapper -> {
         CompoundTag tag = new CompoundTag();
         Item item = new DataItem(942, (byte)1, (short)0, tag);
         wrapper.write(Type.ITEM1_13_2, item);
         int slot = wrapper.read(Type.VAR_INT);
         int pages = wrapper.read(Type.VAR_INT);
         ListTag pagesTag = new ListTag(StringTag.class);

         for (int i = 0; i < pages; i++) {
            String page = wrapper.read(PAGE_STRING_TYPE);
            pagesTag.add(new StringTag(page));
         }

         if (pagesTag.size() == 0) {
            pagesTag.add(new StringTag(""));
         }

         tag.put("pages", pagesTag);
         if (wrapper.read(Type.BOOLEAN)) {
            String title = wrapper.read(TITLE_STRING_TYPE);
            tag.put("title", new StringTag(title));
            tag.put("author", new StringTag(wrapper.user().getProtocolInfo().getUsername()));
            wrapper.write(Type.BOOLEAN, true);
         } else {
            wrapper.write(Type.BOOLEAN, false);
         }

         wrapper.write(Type.VAR_INT, slot);
      });
   }
}
