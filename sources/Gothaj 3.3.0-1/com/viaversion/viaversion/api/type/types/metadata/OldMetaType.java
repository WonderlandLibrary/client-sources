package com.viaversion.viaversion.api.type.types.metadata;

import com.viaversion.viaversion.api.minecraft.metadata.MetaType;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import io.netty.buffer.ByteBuf;

public abstract class OldMetaType extends MetaTypeTemplate {
   private static final int END = 127;

   public Metadata read(ByteBuf buffer) throws Exception {
      byte index = buffer.readByte();
      if (index == 127) {
         return null;
      } else {
         MetaType type = this.getType((index & 224) >> 5);
         return new Metadata(index & 31, type, type.type().read(buffer));
      }
   }

   protected abstract MetaType getType(int var1);

   public void write(ByteBuf buffer, Metadata object) throws Exception {
      if (object == null) {
         buffer.writeByte(127);
      } else {
         int index = (object.metaType().typeId() << 5 | object.id() & 31) & 0xFF;
         buffer.writeByte(index);
         object.metaType().type().write(buffer, object.getValue());
      }
   }
}
