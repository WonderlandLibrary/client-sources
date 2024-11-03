package com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.rewriter;

import com.viaversion.viarewind.ViaRewind;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.Protocol1_7_6_10To1_8;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.model.MetaIndex1_7_6_10To1_8;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.types.metadata.MetaType1_7_6_10;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_10;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.minecraft.metadata.types.MetaType1_8;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class MetadataRewriter {
   private final Protocol1_7_6_10To1_8 protocol;

   public MetadataRewriter(Protocol1_7_6_10To1_8 protocol) {
      this.protocol = protocol;
   }

   public void transform(EntityTypes1_10.EntityType type, List<Metadata> list) {
      for (Metadata entry : new ArrayList<>(list)) {
         MetaIndex1_7_6_10To1_8 metaIndex = MetaIndex1_7_6_10To1_8.searchIndex(type, entry.id());

         try {
            if (metaIndex == null) {
               ViaRewind.getPlatform().getLogger().warning("Could not find 1.8 metadata for " + type + " with index " + entry.id());
               list.remove(entry);
            } else if (metaIndex.getOldType() == MetaType1_7_6_10.NonExistent) {
               list.remove(entry);
            } else {
               Object value = entry.getValue();
               entry.setTypeAndValue(metaIndex.getNewType(), value);
               entry.setMetaTypeUnsafe(metaIndex.getOldType());
               entry.setId(metaIndex.getIndex());
               switch (metaIndex.getOldType()) {
                  case Int:
                     if (metaIndex.getNewType() == MetaType1_8.Byte) {
                        entry.setValue(((Byte)value).intValue());
                        if (metaIndex == MetaIndex1_7_6_10To1_8.ENTITY_AGEABLE_AGE && (Integer)entry.getValue() < 0) {
                           entry.setValue(-25000);
                        }
                     }

                     if (metaIndex.getNewType() == MetaType1_8.Short) {
                        entry.setValue(((Short)value).intValue());
                     }

                     if (metaIndex.getNewType() == MetaType1_8.Int) {
                        entry.setValue(value);
                     }
                     break;
                  case Byte:
                     if (metaIndex.getNewType() == MetaType1_8.Int) {
                        entry.setValue(((Integer)value).byteValue());
                     }

                     if (metaIndex.getNewType() == MetaType1_8.Byte) {
                        if (metaIndex == MetaIndex1_7_6_10To1_8.ITEM_FRAME_ROTATION) {
                           entry.setValue(Integer.valueOf((Byte)value % 4).byteValue());
                        } else {
                           entry.setValue(value);
                        }
                     }

                     if (metaIndex == MetaIndex1_7_6_10To1_8.HUMAN_SKIN_FLAGS) {
                        byte flags = (Byte)value;
                        boolean cape = (flags & 1) != 0;
                        flags = (byte)(cape ? 0 : 2);
                        entry.setValue(flags);
                     }
                     break;
                  case Slot:
                     entry.setValue(this.protocol.getItemRewriter().handleItemToClient((Item)value));
                  case Float:
                  case String:
                  case Short:
                  case Position:
                     break;
                  default:
                     ViaRewind.getPlatform()
                        .getLogger()
                        .warning("Could not transform metadata for " + type + " with index " + entry.id() + " and type " + metaIndex.getOldType());
                     list.remove(entry);
               }
            }
         } catch (Exception var9) {
            ViaRewind.getPlatform().getLogger().log(Level.WARNING, "Unable to transform metadata", (Throwable)var9);
            list.remove(entry);
         }
      }
   }
}
