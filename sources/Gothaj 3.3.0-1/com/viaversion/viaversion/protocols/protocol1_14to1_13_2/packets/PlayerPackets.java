package com.viaversion.viaversion.protocols.protocol1_14to1_13_2.packets;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ClientboundPackets1_13;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.Protocol1_14To1_13_2;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.ServerboundPackets1_14;
import java.util.Collections;

public class PlayerPackets {
   public static void register(Protocol1_14To1_13_2 protocol) {
      protocol.registerClientbound(ClientboundPackets1_13.OPEN_SIGN_EDITOR, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.POSITION1_8, Type.POSITION1_14);
         }
      });
      protocol.registerServerbound(ServerboundPackets1_14.QUERY_BLOCK_NBT, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.VAR_INT);
            this.map(Type.POSITION1_14, Type.POSITION1_8);
         }
      });
      protocol.registerServerbound(ServerboundPackets1_14.EDIT_BOOK, wrapper -> {
         Item item = wrapper.passthrough(Type.ITEM1_13_2);
         protocol.getItemRewriter().handleItemToServer(item);
         if (item != null) {
            CompoundTag tag = item.tag();
            if (tag != null) {
               Tag pages = tag.get("pages");
               if (pages == null) {
                  tag.put("pages", new ListTag(Collections.singletonList(new StringTag())));
               }

               if (Via.getConfig().isTruncate1_14Books() && pages instanceof ListTag) {
                  ListTag listTag = (ListTag)pages;
                  if (listTag.size() > 50) {
                     listTag.setValue(listTag.getValue().subList(0, 50));
                  }
               }
            }
         }
      });
      protocol.registerServerbound(ServerboundPackets1_14.PLAYER_DIGGING, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.VAR_INT);
            this.map(Type.POSITION1_14, Type.POSITION1_8);
         }
      });
      protocol.registerServerbound(ServerboundPackets1_14.RECIPE_BOOK_DATA, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.VAR_INT);
            this.handler(wrapper -> {
               int type = wrapper.get(Type.VAR_INT, 0);
               if (type == 0) {
                  wrapper.passthrough(Type.STRING);
               } else if (type == 1) {
                  wrapper.passthrough(Type.BOOLEAN);
                  wrapper.passthrough(Type.BOOLEAN);
                  wrapper.passthrough(Type.BOOLEAN);
                  wrapper.passthrough(Type.BOOLEAN);
                  wrapper.read(Type.BOOLEAN);
                  wrapper.read(Type.BOOLEAN);
                  wrapper.read(Type.BOOLEAN);
                  wrapper.read(Type.BOOLEAN);
               }
            });
         }
      });
      protocol.registerServerbound(ServerboundPackets1_14.UPDATE_COMMAND_BLOCK, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.POSITION1_14, Type.POSITION1_8);
         }
      });
      protocol.registerServerbound(ServerboundPackets1_14.UPDATE_STRUCTURE_BLOCK, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.POSITION1_14, Type.POSITION1_8);
         }
      });
      protocol.registerServerbound(ServerboundPackets1_14.UPDATE_SIGN, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.POSITION1_14, Type.POSITION1_8);
         }
      });
      protocol.registerServerbound(ServerboundPackets1_14.PLAYER_BLOCK_PLACEMENT, wrapper -> {
         int hand = wrapper.read(Type.VAR_INT);
         Position position = wrapper.read(Type.POSITION1_14);
         int face = wrapper.read(Type.VAR_INT);
         float x = wrapper.read(Type.FLOAT);
         float y = wrapper.read(Type.FLOAT);
         float z = wrapper.read(Type.FLOAT);
         wrapper.read(Type.BOOLEAN);
         wrapper.write(Type.POSITION1_8, position);
         wrapper.write(Type.VAR_INT, face);
         wrapper.write(Type.VAR_INT, hand);
         wrapper.write(Type.FLOAT, x);
         wrapper.write(Type.FLOAT, y);
         wrapper.write(Type.FLOAT, z);
      });
   }
}
