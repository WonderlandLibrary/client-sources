package com.viaversion.viaversion.libs.mcstructs.snbt.impl.v1_7;

import com.viaversion.viaversion.libs.mcstructs.snbt.ISNbtSerializer;
import com.viaversion.viaversion.libs.mcstructs.snbt.exceptions.SNbtSerializeException;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ByteTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.DoubleTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.FloatTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.IntArrayTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.IntTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.LongTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ShortTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import java.util.Map.Entry;

public class SNbtSerializer_v1_7 implements ISNbtSerializer {
   @Override
   public String serialize(Tag tag) throws SNbtSerializeException {
      if (tag instanceof ByteTag) {
         ByteTag byteTag = (ByteTag)tag;
         return byteTag.getValue() + "b";
      } else if (tag instanceof ShortTag) {
         ShortTag shortTag = (ShortTag)tag;
         return shortTag.getValue() + "s";
      } else if (tag instanceof IntTag) {
         IntTag intTag = (IntTag)tag;
         return String.valueOf(intTag.getValue());
      } else if (tag instanceof LongTag) {
         LongTag longTag = (LongTag)tag;
         return longTag.getValue() + "L";
      } else if (tag instanceof FloatTag) {
         FloatTag floatTag = (FloatTag)tag;
         return floatTag.getValue() + "f";
      } else if (tag instanceof DoubleTag) {
         DoubleTag doubleTag = (DoubleTag)tag;
         return doubleTag.getValue() + "d";
      } else if (tag instanceof StringTag) {
         StringTag stringTag = (StringTag)tag;
         return "\"" + stringTag.getValue() + "\"";
      } else if (tag instanceof ListTag) {
         ListTag listTag = (ListTag)tag;
         StringBuilder out = new StringBuilder("[");

         for (int i = 0; i < listTag.size(); i++) {
            out.append(i).append(":").append(this.serialize(listTag.get(i))).append(",");
         }

         return out.append("]").toString();
      } else if (tag instanceof CompoundTag) {
         CompoundTag compoundTag = (CompoundTag)tag;
         StringBuilder out = new StringBuilder("{");

         for (Entry<String, Tag> entry : compoundTag.getValue().entrySet()) {
            out.append(entry.getKey()).append(":").append(this.serialize(entry.getValue())).append(",");
         }

         return out.append("}").toString();
      } else if (!(tag instanceof IntArrayTag)) {
         throw new SNbtSerializeException(tag);
      } else {
         IntArrayTag intArrayTag = (IntArrayTag)tag;
         StringBuilder out = new StringBuilder("[");

         for (int i : intArrayTag.getValue()) {
            out.append(i).append(",");
         }

         return out.append("]").toString();
      }
   }
}
