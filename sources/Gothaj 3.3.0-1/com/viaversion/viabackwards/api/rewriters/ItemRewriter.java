package com.viaversion.viabackwards.api.rewriters;

import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viabackwards.api.data.MappedItem;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ByteTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.IntTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import org.checkerframework.checker.nullness.qual.Nullable;

public class ItemRewriter<C extends ClientboundPacketType, S extends ServerboundPacketType, T extends BackwardsProtocol<C, ?, ?, S>>
   extends ItemRewriterBase<C, S, T> {
   public ItemRewriter(T protocol) {
      super(protocol, true);
   }

   public ItemRewriter(T protocol, Type<Item> itemType, Type<Item[]> itemArrayType) {
      super(protocol, itemType, itemArrayType, true);
   }

   @Nullable
   @Override
   public Item handleItemToClient(@Nullable Item item) {
      if (item == null) {
         return null;
      } else {
         CompoundTag display = item.tag() != null ? item.tag().get("display") : null;
         if (this.protocol.getTranslatableRewriter() != null && display != null) {
            Tag name = display.get("Name");
            if (name instanceof StringTag) {
               StringTag nameStringTag = (StringTag)name;
               String newValue = this.protocol.getTranslatableRewriter().processText(nameStringTag.getValue()).toString();
               if (!newValue.equals(name.getValue())) {
                  this.saveStringTag(display, nameStringTag, "Name");
               }

               nameStringTag.setValue(newValue);
            }

            Tag lore = display.get("Lore");
            if (lore instanceof ListTag) {
               ListTag loreListTag = (ListTag)lore;
               boolean changed = false;

               for (Tag loreEntryTag : loreListTag) {
                  if (loreEntryTag instanceof StringTag) {
                     StringTag loreEntry = (StringTag)loreEntryTag;
                     String newValue = this.protocol.getTranslatableRewriter().processText(loreEntry.getValue()).toString();
                     if (!changed && !newValue.equals(loreEntry.getValue())) {
                        changed = true;
                        this.saveListTag(display, loreListTag, "Lore");
                     }

                     loreEntry.setValue(newValue);
                  }
               }
            }
         }

         MappedItem data = this.protocol.getMappingData() != null ? this.protocol.getMappingData().getMappedItem(item.identifier()) : null;
         if (data == null) {
            return super.handleItemToClient(item);
         } else {
            if (item.tag() == null) {
               item.setTag(new CompoundTag());
            }

            item.tag().put(this.nbtTagName + "|id", new IntTag(item.identifier()));
            item.setIdentifier(data.getId());
            if (data.customModelData() != null && !item.tag().contains("CustomModelData")) {
               item.tag().put("CustomModelData", new IntTag(data.customModelData()));
            }

            if (display == null) {
               item.tag().put("display", display = new CompoundTag());
            }

            if (!display.contains("Name")) {
               display.put("Name", new StringTag(data.getJsonName()));
               display.put(this.nbtTagName + "|customName", new ByteTag());
            }

            return item;
         }
      }
   }

   @Nullable
   @Override
   public Item handleItemToServer(@Nullable Item item) {
      if (item == null) {
         return null;
      } else {
         super.handleItemToServer(item);
         if (item.tag() != null) {
            IntTag originalId = item.tag().remove(this.nbtTagName + "|id");
            if (originalId != null) {
               item.setIdentifier(originalId.asInt());
            }
         }

         return item;
      }
   }

   @Override
   public void registerAdvancements(C packetType, final Type<Item> type) {
      this.protocol.registerClientbound(packetType, new PacketHandlers() {
         @Override
         public void register() {
            this.handler(wrapper -> {
               wrapper.passthrough(Type.BOOLEAN);
               int size = wrapper.passthrough(Type.VAR_INT);

               for (int i = 0; i < size; i++) {
                  wrapper.passthrough(Type.STRING);
                  if (wrapper.passthrough(Type.BOOLEAN)) {
                     wrapper.passthrough(Type.STRING);
                  }

                  if (wrapper.passthrough(Type.BOOLEAN)) {
                     JsonElement title = wrapper.passthrough(Type.COMPONENT);
                     JsonElement description = wrapper.passthrough(Type.COMPONENT);
                     TranslatableRewriter<C> translatableRewriter = ItemRewriter.this.protocol.getTranslatableRewriter();
                     if (translatableRewriter != null) {
                        translatableRewriter.processText(title);
                        translatableRewriter.processText(description);
                     }

                     ItemRewriter.this.handleItemToClient(wrapper.passthrough(type));
                     wrapper.passthrough(Type.VAR_INT);
                     int flags = wrapper.passthrough(Type.INT);
                     if ((flags & 1) != 0) {
                        wrapper.passthrough(Type.STRING);
                     }

                     wrapper.passthrough(Type.FLOAT);
                     wrapper.passthrough(Type.FLOAT);
                  }

                  wrapper.passthrough(Type.STRING_ARRAY);
                  int arrayLength = wrapper.passthrough(Type.VAR_INT);

                  for (int array = 0; array < arrayLength; array++) {
                     wrapper.passthrough(Type.STRING_ARRAY);
                  }
               }
            });
         }
      });
   }

   @Override
   public void registerAdvancements1_20_3(C packetType) {
      this.protocol.registerClientbound(packetType, wrapper -> {
         wrapper.passthrough(Type.BOOLEAN);
         int size = wrapper.passthrough(Type.VAR_INT);

         for (int i = 0; i < size; i++) {
            wrapper.passthrough(Type.STRING);
            if (wrapper.passthrough(Type.BOOLEAN)) {
               wrapper.passthrough(Type.STRING);
            }

            if (wrapper.passthrough(Type.BOOLEAN)) {
               Tag title = wrapper.passthrough(Type.TAG);
               Tag description = wrapper.passthrough(Type.TAG);
               TranslatableRewriter<C> translatableRewriter = this.protocol.getTranslatableRewriter();
               if (translatableRewriter != null) {
                  translatableRewriter.processTag(title);
                  translatableRewriter.processTag(description);
               }

               this.handleItemToClient(wrapper.passthrough(Type.ITEM1_20_2));
               wrapper.passthrough(Type.VAR_INT);
               int flags = wrapper.passthrough(Type.INT);
               if ((flags & 1) != 0) {
                  wrapper.passthrough(Type.STRING);
               }

               wrapper.passthrough(Type.FLOAT);
               wrapper.passthrough(Type.FLOAT);
            }

            int requirements = wrapper.passthrough(Type.VAR_INT);

            for (int array = 0; array < requirements; array++) {
               wrapper.passthrough(Type.STRING_ARRAY);
            }

            wrapper.passthrough(Type.BOOLEAN);
         }
      });
   }
}
