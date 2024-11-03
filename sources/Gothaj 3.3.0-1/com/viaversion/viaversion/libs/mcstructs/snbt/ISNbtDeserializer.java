package com.viaversion.viaversion.libs.mcstructs.snbt;

import com.viaversion.viaversion.libs.mcstructs.snbt.exceptions.SNbtDeserializeException;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;

public interface ISNbtDeserializer<T extends Tag> {
   T deserialize(String var1) throws SNbtDeserializeException;

   Tag deserializeValue(String var1) throws SNbtDeserializeException;
}
