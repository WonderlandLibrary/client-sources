package com.viaversion.viaversion.api.type.types.minecraft;

import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.minecraft.MetaListTypeTemplate;
import io.netty.buffer.ByteBuf;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.nP;

public abstract class AbstractMetaListType extends MetaListTypeTemplate {
   protected abstract Type getType();

   public List read(ByteBuf var1) throws Exception {
      nP.b();
      Type var3 = this.getType();
      ArrayList var4 = new ArrayList();
      Metadata var5 = (Metadata)var3.read(var1);
      var4.add(var5);
      return var4;
   }

   public void write(ByteBuf var1, List var2) throws Exception {
      nP.b();
      Type var4 = this.getType();
      Iterator var5 = var2.iterator();
      if(var5.hasNext()) {
         Metadata var6 = (Metadata)var5.next();
         var4.write(var1, var6);
      }

      this.writeEnd(var4, var1);
   }

   protected abstract void writeEnd(Type var1, ByteBuf var2) throws Exception;

   private static Exception a(Exception var0) {
      return var0;
   }
}
