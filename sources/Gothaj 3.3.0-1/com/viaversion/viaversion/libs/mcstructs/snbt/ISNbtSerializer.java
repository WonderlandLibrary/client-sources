package com.viaversion.viaversion.libs.mcstructs.snbt;

import com.viaversion.viaversion.libs.mcstructs.snbt.exceptions.SNbtSerializeException;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;

public interface ISNbtSerializer {
   String serialize(Tag var1) throws SNbtSerializeException;
}
