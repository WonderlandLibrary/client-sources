package com.viaversion.viaversion.api.type.types.minecraft;

import com.viaversion.viaversion.api.minecraft.metadata.MetaType;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.type.types.minecraft.MetaTypeTemplate;
import io.netty.buffer.ByteBuf;
import net.nP;

public abstract class ModernMetaType extends MetaTypeTemplate {
   public Metadata read(ByteBuf var1) throws Exception {
      short var2 = var1.readUnsignedByte();
      if(var2 == 255) {
         return null;
      } else {
         MetaType var3 = this.getType(var1.readByte());
         return new Metadata(var2, var3, var3.type().read(var1));
      }
   }

   protected abstract MetaType getType(int var1);

   public void write(ByteBuf var1, Metadata var2) throws Exception {
      String var3 = nP.b();
      if(var2 == null) {
         var1.writeByte(255);
      }

      var1.writeByte(var2.id());
      MetaType var4 = var2.metaType();
      var1.writeByte(var4.typeId());
      var4.type().write(var1, var2.getValue());
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
