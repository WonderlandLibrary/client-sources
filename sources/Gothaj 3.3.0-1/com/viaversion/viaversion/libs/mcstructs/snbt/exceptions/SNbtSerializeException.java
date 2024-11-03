package com.viaversion.viaversion.libs.mcstructs.snbt.exceptions;

import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;

public class SNbtSerializeException extends Exception {
   public SNbtSerializeException(Tag type) {
      super("Unable to serialize nbt type " + type.getClass().getSimpleName());
   }
}
