package com.viaversion.viaversion.libs.mcstructs.snbt.impl.v1_13;

import com.viaversion.viaversion.libs.mcstructs.snbt.exceptions.SNbtDeserializeException;
import com.viaversion.viaversion.libs.mcstructs.snbt.impl.v1_12.SNbtDeserializer_v1_12;
import com.viaversion.viaversion.libs.mcstructs.snbt.impl.v1_12.StringReader_v1_12;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;

public class SNbtDeserializer_v1_13 extends SNbtDeserializer_v1_12 {
   @Override
   protected Tag readListOrArray(StringReader_v1_12 reader) throws SNbtDeserializeException {
      return (Tag)(reader.canRead(3) && !this.isQuote(reader.charAt(1)) && reader.charAt(2) == ';' ? this.readArray(reader) : this.readList(reader));
   }
}
