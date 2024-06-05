package com.viaversion.viaversion.api.type.types.version;

import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.minecraft.metadata.types.MetaType1_8;
import com.viaversion.viaversion.api.type.types.minecraft.MetaTypeTemplate;
import io.netty.buffer.ByteBuf;
import net.aes;

public class Metadata1_8Type extends MetaTypeTemplate {
   public Metadata read(ByteBuf var1) throws Exception {
      aes.b();
      byte var3 = var1.readByte();
      if(var3 == 127) {
         return null;
      } else {
         int var4 = (var3 & 224) >> 5;
         MetaType1_8 var5 = MetaType1_8.byId(var4);
         int var6 = var3 & 31;
         return new Metadata(var6, var5, var5.type().read(var1));
      }
   }

   public void write(ByteBuf var1, Metadata var2) throws Exception {
      byte var3 = (byte)(var2.metaType().typeId() << 5 | var2.id() & 31);
      var1.writeByte(var3);
      var2.metaType().type().write(var1, var2.getValue());
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
